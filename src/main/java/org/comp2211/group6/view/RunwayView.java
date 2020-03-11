package org.comp2211.group6.view;

import java.io.IOException;
import java.net.URL;

import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Model.Runway;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

public abstract class RunwayView extends GridPane implements Initializable {

    /*
     * Data
     */
    protected Runway runway;
    protected Obstacle currentObstacle;
    protected LogicalRunway currentLogicalRunway;

    /*
     * View Components
     */
    @FXML
    protected ComboBox<LogicalRunway> logicalRunwayPicker;

    /*
     * Construct a new RunwayView
     */
    public RunwayView() {
        setupRunwayPicker();
    }

    private void setupRunwayPicker() {
        logicalRunwayPicker = new ComboBox<LogicalRunway>();
        logicalRunwayPicker.setItems(FXCollections.observableArrayList());
        logicalRunwayPicker.getSelectionModel().selectFirst();
        logicalRunwayPicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            this.currentLogicalRunway = newVal;
            this.redrawRunway();
        });
    }

    public Runway getRunway() {
        return this.runway;
    }

    /*
     * Sets the runway to display
     * 
     * @param runway the runway to set
     */
    public void setRunway(Runway runway) {
        // Store the runway
        this.runway = runway;
        // Update the combo box
        logicalRunwayPicker.setItems(
                        FXCollections.observableArrayList(this.runway.getLogicalRunways()));
    }

    public LogicalRunway getCurrentLogicalRunway() {
        return this.currentLogicalRunway;
    }

    public Obstacle getCurrentObstacle() {
        return this.currentObstacle;
    }

    /*
     * This method is called when the runway or logical runway is changed, or if the obstacle is
     * updated
     */
    protected abstract void redrawRunway();

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
}
