package org.comp2211.group6.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.text.*;
import javafx.scene.Group;
import javafx.scene.shape.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.*;
import java.io.*;
import javafx.stage.FileChooser;
import org.comp2211.group6.Model.Airport;
import org.comp2211.group6.XMLHandler;

public class FileLoader {

    String filePath;
    String fileContents;
    File file;
    Airport airport;

    public FileLoader() {
        this.filePath = "";
        this.fileContents = ".";
        this.run();
    }

    public void run() {

        try {
            Stage stage = new Stage();
            stage.setTitle("FileChooser");

            FileChooser chooser = new FileChooser();
            chooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("XML Files", ".xml")
            );

            Label label = new Label("No files selected");
            Button button = new Button("Select File");

            EventHandler<ActionEvent> event = e -> {
                file = chooser.showOpenDialog(stage);

                if (file != null) {
                    filePath = file.getAbsolutePath();
                    label.setText("You selected " + filePath);
                } else
                    label.setText("No File Selected");
            };

            button.setOnAction(event);
            Label label1 = new Label("File not ready to be loaded.");
            Button button1 = new Button("Load XML Configuration");

            EventHandler<ActionEvent>event1 = e -> {
                if (filePath != null) {

                    StringBuilder builder = new StringBuilder();
                    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                        String current = "";
                        while ((current = br.readLine()) != null) {
                            builder.append(current).append("\n");
                        }

                        fileContents = builder.toString();
                    } catch (IOException io) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Error when loading the xml file");
                        alert.setContentText("Error Messsage: " + io.getMessage());

                        alert.showAndWait();
                    }

                    if (fileContents == ""){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Warning");
                        alert.setHeaderText("The file you are trying to load is empty");
                        alert.setContentText("Using this file may cause errors when loading the configuration");

                        alert.showAndWait();
                    } else {
                        XMLHandler xml = new XMLHandler();
                        airport = xml.readAirportXML(filePath);
                    }
                }

            };

            button1.setOnAction(event1);

            VBox vbox = new VBox(30, label, button, button1);
            vbox.setAlignment(Pos.CENTER);

            Scene scene = new Scene(vbox, 800, 500);
            stage.setScene(scene);
            stage.show();

        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There was a problem running the XML Loader");
            alert.setContentText("Error Message" + e.getMessage());

            alert.showAndWait();
        }
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileContents() {
        return fileContents;
    }

    public Airport getAirport() {
        return airport;
    }
}
