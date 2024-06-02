package com.usta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class deleteController {

    private String filePath = "src/main/resources/com/usta/data/pets.txt";
    public ObservableList<Mascota> mascotaOL = FXCollections.observableArrayList();
    public List<Mascota> mascotaList = new ArrayList<>();

    @FXML
    private TableView<Mascota> petsTable;
    @FXML
    private TableColumn<Mascota, String> animalCol;
    @FXML
    private TableColumn<Mascota, String> razaCol;
    @FXML
    private TableColumn<Mascota, String> typeCol;
    @FXML
    private TableColumn<Mascota, String> nameCol;
    @FXML
    private TableColumn<Mascota, String> ageCol;
    @FXML
    private TableColumn<Mascota, String> sexCol;
    @FXML
    private TableColumn<Mascota, String> colorCol;
    @FXML
    private TableColumn<Mascota, String> hairCol;

    private Mascota mascotaAEliminar;


    public void initialize(){
        animalCol.setCellValueFactory(new PropertyValueFactory<>("animal"));
        razaCol.setCellValueFactory(new PropertyValueFactory<>("raza"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        sexCol.setCellValueFactory(new PropertyValueFactory<>("sex"));
        colorCol.setCellValueFactory(new PropertyValueFactory<>("color"));
        hairCol.setCellValueFactory(new PropertyValueFactory<>("hair"));
        cargarMascota();

        mascotaOL.setAll(mascotaList);
        petsTable.setItems(mascotaOL);
        
    }

    public void setMascotaAEditar() {
        this.mascotaAEliminar = petsTable.getSelectionModel().getSelectedItem();
    }

        @FXML
    public void ventanaEliminar() throws IOException {
        setMascotaAEditar();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("¿Estás seguro de que deseas eliminar la mascota " + mascotaAEliminar.getName() + "?");
        alert.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Aquí puedes incluir la lógica para eliminar el libro si el usuario confirma
            // Por ejemplo, puedes llamar a un método para eliminar el libro de la lista y
            // actualizar la tabla
            eliminarMascota();
        }
    }
    public void ventanaExito(String name) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito!");
        alert.setHeaderText(name);
        alert.setContentText("Puedes verificar en la sección de mirar.");

        // Mostrar la ventana emergente y esperar a que el usuario la cierre
        alert.showAndWait();
        mascotaOL.setAll(mascotaList);
        petsTable.setItems(mascotaOL);
    }

    public void eliminarMascota() throws IOException {
        // Archivo original y archivo temporal para escribir los cambios
       
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + ".tmp"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // Dividir la línea en partes
                String[] parts = line.split("\\|");
                // Si el ISBN de la línea coincide con el ISBN del libro a eliminar, no escribas
                // esa línea en el archivo temporal
                if (parts.length == 8 && parts[7].equals(mascotaAEliminar.getHair())) {
                    continue;
                }
                // Escribe la línea en el archivo temporal si no coincide con el ISBN del libro
                // a eliminar
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

       
        // Reemplazar el archivo original con el temporal
        File file = new File(filePath);
        File tempFile = new File(filePath + ".tmp");
        if (file.delete()) {
            // Intentar renombrar el archivo temporal
            if (tempFile.renameTo(file)) {
                ventanaExito("La eliminación de la mascota " + mascotaAEliminar.getName() + " se ha realizado correctamente.");
                switchTodelete();
            } else {
                System.out.println("No se pudo renombrar el archivo temporal.");
            }
        } else {
            System.out.println("No se pudo eliminar el archivo original.");
        }
    }

    private void cargarMascota() {
        String directoryPath = "src\\main\\resources\\com\\usta\\data";
        String filePath = directoryPath + File.separator + "pets.txt";
        File file = new File(filePath);

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("\\|");
                    if (parts.length == 8) {
                        Mascota mascota = new Mascota(parts[0], parts[1], parts[2], parts[3],
                        parts[4], parts[5], parts[6], parts[7]);
                        mascotaList.add(mascota);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found: " + filePath);
        }
    }

    @FXML
    private void switchTopreview() throws IOException {
        App.setRoot("preview");
    }
    @FXML
    private void switchTodelete() throws IOException {
        App.setRoot("delete");
    }

}