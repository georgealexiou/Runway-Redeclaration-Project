package org.comp2211.group6.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.comp2211.group6.Model.Airport;
import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Model.Runway;
import org.comp2211.group6.Model.RunwayParameters;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class MainView extends GridPane implements Initializable {

    /*
     * FXML Components
     */
    private Node currentView;

    @FXML
    private TopDownView topDownView;
    @FXML
    private SideOnView sideOnView;

    @FXML
    private VBox splashScreen;

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
    private List<Obstacle> obstacles = new ArrayList<Obstacle>();
    private Obstacle currentObstacle;

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
        this.currentView = splashScreen;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /*
     * Set the airport currently displayed
     */
    private void setAirport(Airport airport) {
        this.currentView.setVisible(false);
        this.currentView = this.topDownView;
        this.currentView.setVisible(true);
        this.currentAirport = airport;
        this.topDownView.setRunway((Runway) airport.getRunways().toArray()[0]);
        this.updateAirportFields();
    }

    /*
     * Updates the fields in the side panel
     */
    private void updateAirportFields() {
        this.airportBox.setVisible(false);
        if (this.currentAirport != null) {
            this.currentAirportName.setText(this.currentAirport.getName());
            this.updateRunwayPicker();
            this.airportBox.setVisible(true);
        }
        this.updateObstaclePicker();
    }

    private void updateRunwayPicker() {
        this.runwayPicker.setItems(
                        FXCollections.observableArrayList(this.currentAirport.getRunways()));
        this.runwayPicker.getSelectionModel().selectFirst();
        this.runwayPicker.valueProperty().addListener((e, oldVal, newVal) -> {
            this.currentRunway = newVal;
            // Clear current obstacle
            this.currentObstacle = null;
            this.obstaclePicker.getSelectionModel().clearSelection();
            // Update child views
            this.updateChildViews();
        });
        this.runwayPicker.setConverter(new StringConverter<Runway>() {
            @Override
            public String toString(Runway object) {
                if (object == null)
                    return null;
                return object.getName();
            }

            @Override
            public Runway fromString(String string) {
                return runwayPicker.getItems().stream().filter(x -> x.getName().equals(string))
                                .findFirst().orElse(null);
            }
        });
    }

    private void updateObstaclePicker() {
        this.obstaclePicker.setItems(FXCollections.observableArrayList(obstacles));
        this.obstaclePicker.getSelectionModel().selectFirst();
        this.obstaclePicker.valueProperty().addListener((e, oldVal, newVal) -> {
            this.currentObstacle = newVal;
            this.updateChildViews();
        });
        this.obstaclePicker.setConverter(new StringConverter<Obstacle>() {

            @Override
            public String toString(Obstacle object) {
                if (object == null)
                    return null;
                return object.getName();
            }

            @Override
            public Obstacle fromString(String string) {
                return obstaclePicker.getItems().stream().filter(x -> x.getName().equals(string))
                                .findFirst().orElse(null);
            }
        });
    }

    private void updateChildViews() {
        // TODO: Update Side on View
        if (this.currentRunway != null) {
            this.topDownView.setRunway(this.currentRunway);
        }
        if (this.currentObstacle != null) {
            this.topDownView.setObstacle(this.currentObstacle);
        }
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

        Obstacle ob1 = new Obstacle("Plane Crash", "", 20, 10, 12, 0, 1000, 1800);
        Obstacle ob2 = new Obstacle("Debris", "", 20, 10, 12, 0, 1000, 1800);
        this.obstacles.add(ob1);
        this.obstacles.add(ob2);
        this.updateAirportFields();
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
        // If the view is not a visualisation then do nothing - shouldn't be able to reach here as
        // the button will be unavailable
        if (this.currentView != topDownView && this.currentView != sideOnView) {
            return;
        }
        // TODO: Update all data in currently hidden view
        RunwayView changeToView;
        if (this.currentView == topDownView) {
            changeToView = sideOnView;
        } else {
            changeToView = topDownView;
        }
        changeToView.setRunway(((RunwayView) this.currentView).getRunway());
        changeToView.setObstacle(((RunwayView) this.currentView).getCurrentObstacle());
        changeToView.setCurrentLogicalRunway(
                        ((RunwayView) this.currentView).getCurrentLogicalRunway());
        this.currentView.setVisible(false);
        this.currentView = changeToView;
        this.currentView.setVisible(true);
    }
}
