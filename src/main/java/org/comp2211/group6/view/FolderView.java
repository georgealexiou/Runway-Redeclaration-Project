package org.comp2211.group6.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class FolderView extends GridPane implements Initializable {

    /*
     * View Components
     */
    @FXML
    private Button browseButton;
    @FXML
    private Button validateButton;
    @FXML
    public Button cancelButton;
    @FXML
    private TextField pathField;
    @FXML
    private Label title;

    private EventHandler<ActionEvent> currentValidationHandler;
    private DirectoryChooser chooser;

    public SimpleStringProperty filePath = new SimpleStringProperty();

    public FolderView() {
        loadFxml(getClass().getResource("/file_view.fxml"), this);
        title.setText("Choose a directory to save the image of runway view");
        filePath.set("No directory selected");
        validateButton.setText("Export");
    }

    public FolderView(String savetype) {
        loadFxml(getClass().getResource("/file_view.fxml"), this);
        title.setText("Choose a directory to save the " + savetype);
        filePath.set("No directory selected");
        validateButton.setText("Export");
    }


    private static void loadFxml(URL fxmlFile, Object rootController) {
        FXMLLoader loader = new FXMLLoader(fxmlFile);
        loader.setController(rootController);
        loader.setRoot(rootController);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chooser = new DirectoryChooser();
        pathField.textProperty().bind(filePath);
        validateButton.disableProperty()
                        .bind(pathField.textProperty().isEqualTo("No directory selected"));

        filePath.set("No directory selected");
    }

    public void setValidator(EventHandler<ActionEvent> eventHandler) {
        if (this.currentValidationHandler != null) {
            this.validateButton.removeEventHandler(ActionEvent.ANY, currentValidationHandler);
        }
        this.validateButton.addEventHandler(ActionEvent.ANY, eventHandler);
        this.currentValidationHandler = eventHandler;
    }

    public void setTitle(String title) {
        this.title.setText(title);
    }

    @FXML
    private void openFileChooser() {
        Stage stage = new Stage();
        stage.setTitle("Select a Directory");
        File file = chooser.showDialog(stage);
        if (file != null) {
            filePath.set(file.getAbsolutePath());
        }
    }

}
