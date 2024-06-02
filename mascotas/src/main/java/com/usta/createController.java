package com.usta;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

public class createController {

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


    public void ventana(String name){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Éxito!");
        alert.setHeaderText("La mascota "+name+" se ha agregado correctamente");
        alert.setContentText("Puedes verificar en la sección de mirar.");

        // Mostrar la ventana emergente y esperar a que el usuario la cierre
        alert.showAndWait();
    }


    public void initialize(){
        List<String> dogList = Arrays.asList("Pastor Aleman","Husky Siberiano","Golden Retriever","Bulldog Francés","Chihuahua","Pit bull terrier","Beagle","Poodle","Dóberman","Rottweiler");
        ObservableList<String> oLDog = FXCollections.observableArrayList(dogList);
        dogCBX.setItems(oLDog);

        List<String> catList = Arrays.asList("Persa","Siamés","Ragdoll","Siberiano","Esfinge","Exótico","Himalayo","Cartujo","Azul Ruso","Común Europeo");
        ObservableList<String> oLCat = FXCollections.observableArrayList(catList);
        catCBX.setItems(oLCat);

        List<String> typeList = Arrays.asList("Puppy","Adult","Senior");
        ObservableList<String> oLType = FXCollections.observableArrayList(typeList);
        typeCBX.setItems(oLType);
        
        List<String> colorList = Arrays.asList("Blanco","Negro","Gris","Amarillo","Manchado");
        ObservableList<String> oLColor = FXCollections.observableArrayList(colorList);
        colorCBX.setItems(oLColor);

        List<String> hairList = Arrays.asList("Duro","Rizado","Corto","Largo","Sin Pelo");
        ObservableList<String> oLHair = FXCollections.observableArrayList(hairList);
        hairCBX.setItems(oLHair);

        List<String> animalList = Arrays.asList("Dog","Cat","Other Species");
        ObservableList<String> oLAnimal = FXCollections.observableArrayList(animalList);
        animalCBX.setItems(oLAnimal);

        List<String> sexList = Arrays.asList("Female","Male");
        ObservableList<String> oLSex = FXCollections.observableArrayList(sexList);
        sexCBX.setItems(oLSex);

    }

        public void agregarMascota() {
        String animal = animalCBX.getValue();
        String raza="";
        String type = typeCBX.getValue();
        String name = nameTxt.getText();
        String age = ageDtP.getValue().toString();
        String sex = sexCBX.getValue();
        String color = colorCBX.getValue();
        String hair = hairCBX.getValue();
    
            if (dogRB.isSelected()) {
                raza  = dogCBX.getValue();
            } else if (catRB.isSelected()) {
                raza  = catCBX.getValue();
            } else if (otherRB.isSelected()) {
                raza  = otherspeciesTxt.getText();
            }
        
        

        Mascota nuevaMascota = new Mascota(animal, raza, type, name, age, sex, color, hair);
        
        
        String directoryPath = "src\\main\\resources\\com\\usta\\data";
        String filePath = directoryPath + File.separator + "pets.txt";

        
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(nuevaMascota.toString());
            writer.newLine();
            ventana(name);
        } catch (IOException e) {
            e.printStackTrace();
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
    private void switchTopreview() throws IOException {
        App.setRoot("preview");
    }
}