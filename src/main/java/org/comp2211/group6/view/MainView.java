package org.comp2211.group6.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.comp2211.group6.Model.Airport;
import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Model.Runway;
import org.comp2211.group6.Model.RunwayParameters;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MainView extends GridPane implements Initializable {

    /*
     * FXML Components
     */

    @FXML
    private TopDownView topDownView;

    @FXML
    private VBox airportBox;
    @FXML
    private Label currentAirportName;

    @FXML
    private ComboBox<Runway> runwayPicker;

    @FXML
    private ComboBox<Obstacle> obstaclePicker;

    /*
     * Properties
     */
    private Airport currentAirport;
    private Runway currentRunway;

    public MainView() {
        super();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/main_view.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO: Initially only show the splash screen
        // TODO: Initially only show the button bar
        this.airportBox.setVisible(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /*
     * Set the airport currently displayed
     */
    private void setAirport(Airport airport) {
        this.currentAirport = airport;
        this.currentAirportName.setText(airport.getName());
        this.topDownView.setRunway((Runway) airport.getRunways().toArray()[0]);
        this.updateFields();
    }

    /*
     * Updates the fields in the side panel
     */
    private void updateFields() {
        this.airportBox.setVisible(true);
    }

    @FXML
    private void loadAirport(ActionEvent e) {
        // TODO: Implement actual function
        Airport airport;
        if (this.currentAirportName.getText().equals("")) {
            airport = new Airport("Heathrow");
        } else {
            airport = new Airport("Gatwick");
        }
        Runway runway = new Runway("09L27R");
        LogicalRunway runway1 = new LogicalRunway(9, 306, 'L',
                        new RunwayParameters(3902, 3902, 3902, 3595));
        LogicalRunway runway2 =
                        new LogicalRunway(27, 0, 'R', new RunwayParameters(3884, 3962, 3884, 3884));
        Runway runway3 = new Runway("09R27L");
        LogicalRunway runway4 = new LogicalRunway(9, 307, 'R',
                        new RunwayParameters(3902, 3902, 3902, 3595));
        LogicalRunway runway5 =
                        new LogicalRunway(27, 0, 'L', new RunwayParameters(3884, 3962, 3884, 3884));
        try {
            runway.addRunway(runway1);
            runway.addRunway(runway2);
            runway3.addRunway(runway4);
            runway3.addRunway(runway5);
            airport.addRunway(runway);
            airport.addRunway(runway3);
            this.setAirport(airport);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void createAirport(ActionEvent e) {
        // TODO: Load the create airport dialog
        // TODO: Set the newly created airport
    }

    @FXML
    private void editAirport(ActionEvent e) {
        // TODO: Load the edit airport dialog
        // TODO: Set the newly edited airport
    }

    @FXML
    private void loadObstacle(ActionEvent e) {
        // TODO: Load the obstacle loading dialog
        // TODO: Set the obstacle
    }

    @FXML
    private void createObstacle(ActionEvent e) {
        // TODO: Load the obstacle creation dialog
        // TODO: Set the obstacle
    }

    @FXML
    private void editObstacle(ActionEvent e) {
        // TODO: Load the edit obstacle screen
        // TODO: Set the obstacle
    }

    @FXML
    private void viewCalculations(ActionEvent e) {
        // TODO: Load the Breakdown View
    }

    @FXML
    private void toggleView(ActionEvent e) {
        // TODO: Update all data in currently hidden view
        // TODO: Hide displayed view
        // TODO: Load currently hidden view
    }
}
