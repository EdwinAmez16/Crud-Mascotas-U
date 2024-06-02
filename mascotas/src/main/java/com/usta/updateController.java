package com.usta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class updateController {

    private String filePath = "hola//src/main/resources/com/usta/data/pets.txt";

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

    @FXML
    private ComboBox<String> animalCBX;
    @FXML
    private ComboBox<String> typeCBX;
    @FXML
    private TextField nameTxt;
    @FXML
    private DatePicker ageDtP;
    @FXML
    private ComboBox<String> sexCBX;
    @FXML
    private ComboBox<String> colorCBX;
    @FXML
    private ComboBox<String> hairCBX;
    @FXML
    private ComboBox<String> dogCBX;
    @FXML
    private ComboBox<String> catCBX;
    @FXML
    private TextField otherspeciesTxt;


    @FXML
    private RadioButton dogRB;
    @FXML
    private RadioButton catRB;
    @FXML
    private RadioButton otherRB;

    private Mascota mascotaAEditar;

    public void ventana(String name) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito!");
        alert.setHeaderText("La mascota " + name + " se ha agregado correctamente");
        alert.setContentText("Puedes verificar en la sección de listar.");

        // Mostrar la ventana emergente y esperar a que el usuario la cierre
        alert.showAndWait();
        mascotaOL.setAll(mascotaList);
        petsTable.setItems(mascotaOL);
    }

    public void initialize() {
        List<String> dogList = Arrays.asList("Pastor Aleman", "Husky Siberiano", "Golden Retriever", "Bulldog Francés",
                "Chihuahua", "Pit bull terrier", "Beagle", "Poodle", "Dóberman", "Rottweiler");
        ObservableList<String> oLDog = FXCollections.observableArrayList(dogList);
        dogCBX.setItems(oLDog);

        List<String> catList = Arrays.asList("Persa", "Siamés", "Ragdoll", "Siberiano", "Esfinge", "Exótico",
                "Himalayo", "Cartujo", "Azul Ruso", "Común Europeo");
        ObservableList<String> oLCat = FXCollections.observableArrayList(catList);
        catCBX.setItems(oLCat);

        List<String> typeList = Arrays.asList("Puppy", "Adult", "Senior");
        ObservableList<String> oLType = FXCollections.observableArrayList(typeList);
        typeCBX.setItems(oLType);

        List<String> colorList = Arrays.asList("Blanco", "Negro", "Gris", "Amarillo", "Manchado");
        ObservableList<String> oLColor = FXCollections.observableArrayList(colorList);
        colorCBX.setItems(oLColor);

        List<String> hairList = Arrays.asList("Duro", "Rizado", "Corto", "Largo", "Sin Pelo");
        ObservableList<String> oLHair = FXCollections.observableArrayList(hairList);
        hairCBX.setItems(oLHair);

        List<String> animalList = Arrays.asList("Dog", "Cat", "Other Species");
        ObservableList<String> oLAnimal = FXCollections.observableArrayList(animalList);
        animalCBX.setItems(oLAnimal);

        List<String> sexList = Arrays.asList("Female", "Male");
        ObservableList<String> oLSex = FXCollections.observableArrayList(sexList);
        sexCBX.setItems(oLSex);

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

    public void setmascotaAEditar() {
        this.mascotaAEditar = petsTable.getSelectionModel().getSelectedItem();
        cargarDatosMascota();
    }

    private void cargarDatosMascota() {
        boolean flag = false;
        if (mascotaAEditar != null) {
            animalCBX.setValue(mascotaAEditar.getAnimal());
            typeCBX.setValue(mascotaAEditar.getType());
            nameTxt.setText(mascotaAEditar.getName());
            LocalDate localDate = LocalDate.parse(mascotaAEditar.getAge().toString());
            ageDtP.setValue(localDate);
            sexCBX.setValue(mascotaAEditar.getSex());
            colorCBX.setValue(mascotaAEditar.getColor());
            hairCBX.setValue(mascotaAEditar.getHair());
            catCBX.setValue(mascotaAEditar.getRaza());
            dogCBX.setValue(mascotaAEditar.getRaza());
            otherspeciesTxt.setText(mascotaAEditar.getRaza());
    
            for (String cat : catCBX.getItems()) {
                if (cat.equals(mascotaAEditar.getRaza())) {
                    catCBX.setValue(mascotaAEditar.getRaza());
                    catRB.setSelected(true);
                    selectSpecie();
                    flag = true;
                }
            }
            for (String dog : dogCBX.getItems()) {
                if (dog.equals(mascotaAEditar.getRaza())) {
                    catCBX.setValue(mascotaAEditar.getRaza());
                    catRB.setSelected(true);
                    selectSpecie();
                    flag = true;
                }
            }
            if (!flag) {
                otherspeciesTxt.setText(mascotaAEditar.getRaza());
                otherRB.setSelected(true);
                selectSpecie();
            }

        }
    }

    @FXML
    public void selectSpecie() {

        if (dogRB.isSelected()) {
            dogCBX.setVisible(true);
            catCBX.setVisible(false);
            otherspeciesTxt.setVisible(false);
        } else if (catRB.isSelected()) {
            dogCBX.setVisible(false);
            catCBX.setVisible(true);
            otherspeciesTxt.setVisible(false);
        } else if (otherRB.isSelected()) {
            dogCBX.setVisible(false);
            catCBX.setVisible(false);
            otherspeciesTxt.setVisible(true);
        }
    }

    @FXML
    public void editarMascota() throws IOException {
        if (mascotaAEditar != null) {
            // Actualizar los datos del libro con los valores de los campos
            mascotaAEditar.setAnimal(animalCBX.getValue());
            mascotaAEditar.setType(typeCBX.getValue());
            mascotaAEditar.setName(nameTxt.getText());
            LocalDate localDate = LocalDate.parse(mascotaAEditar.getAge().toString());
            ageDtP.setValue(localDate);    
            mascotaAEditar.setSex(sexCBX.getValue());
            mascotaAEditar.setColor(colorCBX.getValue());
            mascotaAEditar.setHair(hairCBX.getValue());
            mascotaAEditar.setRaza(razaCol.getCellData(mascotaAEditar));


            // Guardar los cambios en el archivo
            actualizarArchivo();

        }
        switchTopreview();
    }

    private void actualizarArchivo() {

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
                BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + ".tmp"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 8 && parts[7].equals(mascotaAEditar.getHair())) {
                    writer.write(mascotaAEditar.getAnimal() + "|"  + mascotaAEditar.getRaza() + "|" +
                                    mascotaAEditar.getType() + "|" + mascotaAEditar.getName() + "|" +
                                    mascotaAEditar.getAge() + "|" + mascotaAEditar.getSex() + "|" +
                                    mascotaAEditar.getColor() + "|" + mascotaAEditar.getHair());
                    writer.newLine();
                } else {
                    writer.write(line);
                    writer.newLine();
                }
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
                // Mostrar ventana de éxito
                ventana("La edición de la mascota " + mascotaAEditar.getName() + " se ha guardado correctamente.");
            } else {
                System.out.println("No se pudo renombrar el archivo temporal.");
            }
        } else {
            System.out.println("No se pudo eliminar el archivo original.");
        }
        if (tempFile.renameTo(file)) {
            // Mostrar ventana de éxito
            ventana("La edición de la mascota " + mascotaAEditar.getName() + " se ha guardado correctamente.");
        } else {
            System.out.println("No se pudo reemplazar el archivo original con el temporal.");
        }
    }

    @FXML
    private void switchTopreview() throws IOException {
        App.setRoot("preview");
    }
}