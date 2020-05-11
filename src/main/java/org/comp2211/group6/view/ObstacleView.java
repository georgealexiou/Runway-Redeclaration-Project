package org.comp2211.group6.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.comp2211.group6.Model.Obstacle;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;


public abstract class ObstacleView extends GridPane implements Initializable {
    /*
     * DATA
     */

    protected SimpleObjectProperty<Obstacle> currentObstacle = new SimpleObjectProperty<Obstacle>();
    BooleanBinding disableBindings;

    /*
     * View Components
     */
    @FXML
    protected Label obstacleViewTitle;

    @FXML
    protected Button obstacleSaveButton;

    @FXML
    protected Button obstacleExportButton;

    @FXML
    private GridPane entryGridPane;

    @FXML
    protected TextField obstacleName;

    @FXML
    protected TextField obstacleDescription;

    @FXML
    protected TextField obstacleHeight;

    @FXML
    protected TextField obstacleDistanceFromCentreLine;

    @FXML
    protected TextField obstacleDistanceFromLeft;

    @FXML
    protected TextField obstacleDistanceFromRight;

    /*
     * Actions/Listeners for Save button and Export button
     */

    /**
     * Needed to be implemented
     */
    @FXML
    void exportAction(ActionEvent event) {

    }

    public ObstacleView() {}

    /*
     * Listener to enable save button and export button when all TextFields(except description) have
     * been filled.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        disableBindings = obstacleName.textProperty().isEmpty().or(obstacleHeight.textProperty()
                        .isEmpty()
                        .or(obstacleDistanceFromCentreLine.textProperty().isEmpty()
                                        .or(obstacleDistanceFromLeft.textProperty().isEmpty()
                                                        .or(obstacleDistanceFromRight.textProperty()
                                                                        .isEmpty()))));
        obstacleSaveButton.disableProperty().bind(disableBindings);
        obstacleExportButton.disableProperty().bind(disableBindings);
    }

    /*
     * Method to load the view from fxml file
     */
    protected static void loadFxml(URL fxmlFile, Object rootController) {
        FXMLLoader loader = new FXMLLoader(fxmlFile);
        loader.setController(rootController);
        loader.setRoot(rootController);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * get the new obstacle forming by entered values in the textfields
     */
    protected Obstacle getNewObstacle() {
        return new Obstacle(obstacleName.getText(), obstacleDescription.getText(),
                        Double.parseDouble(obstacleHeight.getText()),
                        Double.parseDouble(obstacleDistanceFromCentreLine.getText()),
                        Double.parseDouble(obstacleDistanceFromLeft.getText()),
                        Double.parseDouble(obstacleDistanceFromRight.getText()));
    }
}
