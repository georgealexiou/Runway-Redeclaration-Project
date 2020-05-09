package org.comp2211.group6.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class FileView extends GridPane implements Initializable {

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
    private FileChooser chooser;

    public SimpleStringProperty filePath = new SimpleStringProperty();
    private SimpleBooleanProperty isLoading = new SimpleBooleanProperty();

    public FileView() {
        loadFxml(getClass().getResource("/file_view.fxml"), this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        pathField.textProperty().bind(filePath);
        validateButton.disableProperty()
                        .bind(pathField.textProperty().isEqualTo("No file selected"));
        isLoading.addListener((o, origVal, newVal) -> {
            if (newVal) {
                validateButton.setText("Validate and Load");
            } else {
                validateButton.setText("Export");
            }
        });
        isLoading.set(true);
        reset();
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

    /**
     * Is this a loading or saving file dialog
     */
    public void setLoading(boolean isLoading) {
        this.isLoading.set(isLoading);
    }

    @FXML
    private void openFileChooser() {
        Stage stage = new Stage();
        stage.setTitle("Select XML File");
        if (isLoading.get()) {
            File file = chooser.showOpenDialog(stage);
            if (file != null) {
                if (file.length() == 0) {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Empty File", ButtonType.OK);
                    alert.showAndWait();
                    filePath.set("No file selected");
                } else {
                    filePath.set(file.getAbsolutePath());
                }
            }
        } else {
            File file = chooser.showSaveDialog(stage);
            if (file != null) {
                filePath.set(file.getAbsolutePath());
            }
        }
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

    public void reset() {
        filePath.set("No file selected");
        title.setText("Choose a file!");
    }

}
