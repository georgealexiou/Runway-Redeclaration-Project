package org.comp2211.group6.view;

import java.io.IOException;
import java.net.URL;

import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Model.Runway;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.SVGPath;
import javafx.util.StringConverter;

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
    @FXML
    protected SVGPath runwayDirectionArrow;
    @FXML
    protected Canvas runwayCanvas;

    /*
     * Construct a new RunwayView
     */
    public RunwayView() {
        logicalRunwayPicker = new ComboBox<LogicalRunway>();
        setupRunwayPicker(FXCollections.observableArrayList());
    }

    private void setupRunwayPicker(ObservableList<LogicalRunway> data) {
        logicalRunwayPicker.setItems(data);
        logicalRunwayPicker.getSelectionModel().selectFirst();
        logicalRunwayPicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            this.currentLogicalRunway = newVal;
            this.redrawRunway();
        });
        logicalRunwayPicker.setConverter(new StringConverter<LogicalRunway>() {
            @Override
            public String toString(LogicalRunway object) {
                return object.getIdentifier();
            }

            @Override
            public LogicalRunway fromString(String string) {
                return logicalRunwayPicker.getItems().stream()
                                .filter(ap -> ap.getIdentifier().equals(string)).findFirst()
                                .orElse(null);
            }
        });
        currentLogicalRunway = logicalRunwayPicker.getValue();
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
        setupRunwayPicker(FXCollections.observableArrayList(this.runway.getLogicalRunways()));
        // Re draw the runway
        redrawRunway();
    }

    public LogicalRunway getCurrentLogicalRunway() {
        return this.currentLogicalRunway;
    }

    public Obstacle getCurrentObstacle() {
        return this.currentObstacle;
    }

    /*
     * This method is called when the runway or logical runway is changed, or if the obstacle is
     * updated Override this to add more functionality
     */
    protected void redrawRunway() {
        // Handle takeoff landing direction arrow
        if (currentLogicalRunway.getHeading() <= 18) {
            this.runwayDirectionArrow.setRotate(0);
        } else {
            this.runwayDirectionArrow.setRotate(180);
        }
    };

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
