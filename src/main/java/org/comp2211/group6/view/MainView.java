package org.comp2211.group6.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.Alert;
import org.comp2211.group6.Model.Airport;
import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Model.Runway;
import org.comp2211.group6.Model.RunwayParameters;
import org.comp2211.group6.Controller.Calculator;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
    private BreakdownView breakdownView;
    @FXML
    private EditAnObstacleView editAnObstacleView;
    @FXML
    private CreateAnObstacleView createAnObstacleView;
    @FXML
    private LoadAnObstacleView loadAnObstacleView;

    @FXML
    private Button returnToRunwayViewButton;

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

    @FXML
    private Label notificationLabel;
    /*
     * Properties
     */
    private Airport currentAirport;
    private Runway currentRunway;
    private List<Obstacle> obstacles = new ArrayList<Obstacle>();
    private Obstacle currentObstacle;
    private Calculator calculator;

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
        this.currentRunway = (Runway) airport.getRunways().toArray()[0];
        this.updateAirportFields();
        this.updateChildObstacles();
        this.updateChildRunways();
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
            this.updateChildRunways();
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
            this.updateChildObstacles();
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

    private void updateChildRunways() {
        this.topDownView.setRunway(this.currentRunway);
        this.sideOnView.setRunway(this.currentRunway);
    }

    private void updateChildObstacles() {
        this.topDownView.setObstacle(this.currentObstacle);
        this.sideOnView.setObstacle(this.currentObstacle);
        if (this.currentObstacle != null && this.currentRunway != null) {
            this.calculator = new Calculator(this.currentRunway);
            this.calculator.recalculateRunwayParameters();
        }
    }

    @FXML
    private void loadAirport(ActionEvent e) {
        // TODO: Implement actual function

        FileLoader fileLoader = new FileLoader();
        Airport airport = null;

        while(airport == null){
            airport = fileLoader.getAirport();
        }

        currentAirport = airport;
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

    /*
     * listener for Save button in obstacle views. cater for different obstacle views(create, edit,
     * load)
     * 
     */
    private EventHandler<ActionEvent> obstacleSaveButtonAction(ObstacleView obstacleView) {
        notificationLabel.setText(" ");
        EventHandler<ActionEvent> saveButtonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentView.setVisible(false);
                obstacles.add(obstacleView.getNewObstacle());
                // If the current obstacle view is NOT the Create view, remove the original obstacle
                // from the list.
                if (!(obstacleView instanceof CreateAnObstacleView)) {
                    obstacles.remove(currentObstacle);
                }
                updateAirportFields();
                currentView = topDownView;
                currentView.setVisible(true);
                notificationLabel.setText("Obstacle successfully saved");
                event.consume();
            }
        };

        return saveButtonHandler;
    }

    @FXML
    private void loadObstacle(ActionEvent e) {
        this.currentView.setVisible(false);
        this.loadAnObstacleView.loadPredefinedObstacle("Scenario 1 Obstacle",
                        "Obstacle from Scenario 1 of the Heathrow Example", 53.5, 70.3, 12);
        loadAnObstacleView.obstacleSaveButton
                        .setOnAction(obstacleSaveButtonAction(loadAnObstacleView));
        this.currentView = this.loadAnObstacleView;
        this.currentView.setVisible(true);
    }

    @FXML
    private void createObstacle(ActionEvent e) {
        this.returnToRunwayViewButton.setVisible(true);
        this.currentView.setVisible(false);
        createAnObstacleView.obstacleSaveButton
                        .setOnAction(obstacleSaveButtonAction(createAnObstacleView));
        this.currentView = this.createAnObstacleView;
        this.currentView.setVisible(true);
    }

    @FXML
    private void editObstacle(ActionEvent e) {
        if (currentObstacle == null) {
            throw new NullPointerException("No obstacle can be edited.");
        } else {
            this.returnToRunwayViewButton.setVisible(true);
            this.currentView.setVisible(false);
            editAnObstacleView.loadCurrentObstacle(currentObstacle);
            editAnObstacleView.obstacleSaveButton
                            .setOnAction(obstacleSaveButtonAction(editAnObstacleView));
            this.currentView = this.editAnObstacleView;
            this.currentView.setVisible(true);
        }
    }

    @FXML
    private void viewCalculations(ActionEvent e) {
        this.returnToRunwayViewButton.setVisible(true);
        if (this.calculator.getAllBreakdowns().size() > 0) {
            this.currentView.setVisible(false);
            this.currentView = this.breakdownView;
            this.breakdownView.setAvailableBreakdowns(this.calculator.getAllBreakdowns());
            this.currentView.setVisible(true);
        } else {
            return;
        }
    }

    @FXML
    private void toggleView(ActionEvent e) {
        // If the view is not a visualisation then do nothing - shouldn't be able to reach here as
        // the button will be unavailable
        if (this.currentView != topDownView && this.currentView != sideOnView) {
            return;
        }
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

    @FXML
    private void returnToRunwayView(ActionEvent e) {
        this.returnToRunwayViewButton.setVisible(false);
        this.currentView.setVisible(false);
        this.currentView = topDownView;
        this.currentView.setVisible(true);
    }
}
