package org.comp2211.group6.view;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.comp2211.group6.Model.Airport;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Model.Runway;
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
import org.comp2211.group6.XMLHandler;

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
    private Button loadAirportButton;
    @FXML
    private Button createAirportButton;
    @FXML
    private Button loadObstacleButton;
    @FXML
    private Button createObstacleButton;
    @FXML
    private Button editAirportButton;
    @FXML
    private Button editObstacleButton;
    @FXML
    private Button viewCalculationsButton;
    @FXML
    private Button toggleViewButton;


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
        changeView(splashScreen);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void changeView(Node newView) {
        if (this.currentView != null) {
            this.currentView.setVisible(false);
        }
        this.currentView = newView;
        this.updateButtons();
        this.currentView.setVisible(true);
        updateObstaclePicker();
        updateAirportFields();
        updateChildObstacles();
        updateChildRunways();
    }

    /*
     * Set the airport currently displayed
     */
    private void setAirport(Airport airport) {
        this.currentAirport = airport;
        this.currentRunway = (Runway) airport.getRunways().toArray()[0];
        changeView(topDownView);
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
        updateButtons();
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

        updateButtons();
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
        updateButtons();
    }

    private void updateChildRunways() {
        this.topDownView.setRunway(this.currentRunway);
        this.sideOnView.setRunway(this.currentRunway);
        updateButtons();
    }

    private void updateChildObstacles() {
        this.topDownView.setObstacle(this.currentObstacle);
        this.sideOnView.setObstacle(this.currentObstacle);
        if (this.currentObstacle != null && this.currentRunway != null) {
            this.calculator = new Calculator(this.currentRunway);
            this.calculator.recalculateRunwayParameters();
        }
        updateButtons();
    }

    @FXML
    private void loadAirport(ActionEvent e) {
        Stage stage = new Stage();

        stage.setTitle("Choose an Airport to load");

        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"));

        Label label = new Label("No files selected");
        Button button = new Button("Select File");

        EventHandler<ActionEvent> event = event1 -> {
            File file = chooser.showOpenDialog(stage);
            String filePath = "";

            if (file != null) {
                filePath = file.getAbsolutePath();
                label.setText("You selected " + filePath);

            } else
                label.setText("No File Selected");

            if (file.length() == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("The file you are trying to load is empty");
                alert.setContentText("Using this file may cause errors when loading the configuration");

                alert.showAndWait();
            } else {
                XMLHandler xml = new XMLHandler();
                setAirport(xml.readAirportXML(filePath));
                stage.close();
            }

        };

        button.setOnAction(event);
        VBox vbox = new VBox(30, label, button);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 800, 500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();
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
                obstacles.add(obstacleView.getNewObstacle());
                // If the current obstacle view is NOT the Create view, remove the original obstacle
                // from the list.
                if (!(obstacleView instanceof CreateAnObstacleView)) {
                    obstacles.remove(currentObstacle);
                }
                changeView(topDownView);
                notificationLabel.setText("Obstacle successfully saved");
                event.consume();
            }
        };

        return saveButtonHandler;
    }

    @FXML
    private void loadObstacle(ActionEvent e) {

        Stage stage = new Stage();

        stage.setTitle("Choose an Obstacle to load");

        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("XML Files", "*.xml"));

        Label label = new Label("No files selected");
        Button button = new Button("Select File");

        EventHandler<ActionEvent> event = event1 -> {
            File file = chooser.showOpenDialog(stage);
            String filePath = "";

            if (file != null) {
                filePath = file.getAbsolutePath();
                label.setText("You selected " + filePath);

            } else
                label.setText("No File Selected");

            if (file.length() == 0) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("The file you are trying to load is empty");
                alert.setContentText("Using this file may cause errors when loading the configuration");

                alert.showAndWait();
            } else {
                XMLHandler xml = new XMLHandler();
                obstacles.addAll(xml.readObstaclesXML(filePath));
                stage.close();
            }

        };

        button.setOnAction(event);
        VBox vbox = new VBox(30, label, button);
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 800, 500);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.showAndWait();

        /*
        this.loadAnObstacleView.loadPredefinedObstacle("Scenario 1 Obstacle",
                        "Obstacle from Scenario 1 of the Heathrow Example", 53.5, 70.3, 12);
        loadAnObstacleView.obstacleSaveButton
                        .setOnAction(obstacleSaveButtonAction(loadAnObstacleView));
        changeView(loadAnObstacleView);
        */
    }

    @FXML
    private void createObstacle(ActionEvent e) {
        createAnObstacleView.obstacleSaveButton
                        .setOnAction(obstacleSaveButtonAction(createAnObstacleView));
        changeView(createAnObstacleView);
    }

    @FXML
    private void editObstacle(ActionEvent e) {
        if (currentObstacle == null) {
            throw new NullPointerException("No obstacle can be edited.");
        } else {
            this.returnToRunwayViewButton.setVisible(true);
            editAnObstacleView.loadCurrentObstacle(currentObstacle);
            editAnObstacleView.obstacleSaveButton
                            .setOnAction(obstacleSaveButtonAction(editAnObstacleView));
            changeView(editAnObstacleView);
        }
    }

    @FXML
    private void viewCalculations(ActionEvent e) {
        this.returnToRunwayViewButton.setVisible(true);
        if (this.calculator.getAllBreakdowns().size() > 0) {
            this.breakdownView.setAvailableBreakdowns(this.calculator.getAllBreakdowns());
            changeView(breakdownView);
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
        changeView(changeToView);
    }

    @FXML
    private void returnToRunwayView(ActionEvent e) {
        this.returnToRunwayViewButton.setVisible(false);
        changeView(topDownView);
    }

    private void updateButtons() {
        if (currentView == topDownView || currentView == sideOnView) {
            // Load and Create Airport always available here
            loadAirportButton.setDisable(false);
            createAirportButton.setDisable(false);
            // Buttons you can press if an airport is loaded
            if (this.currentAirport != null) {
                editAirportButton.setDisable(false);
                loadObstacleButton.setDisable(false);
                createObstacleButton.setDisable(false);
                toggleViewButton.setDisable(false);
                // Buttons you can press if an obstacle is also loaded
                if (this.currentObstacle != null) {
                    editObstacleButton.setDisable(false);
                    viewCalculationsButton.setDisable(false);
                } else {
                    editObstacleButton.setDisable(true);
                    viewCalculationsButton.setDisable(true);
                }
            } else {
                editAirportButton.setDisable(true);
                loadObstacleButton.setDisable(true);
                createObstacleButton.setDisable(true);
                toggleViewButton.setDisable(true);
            }
        } else {
            // Deal with splash screen button
            if (this.currentView != splashScreen) {
                loadAirportButton.setDisable(true);
                createAirportButton.setDisable(true);
            } else {
                loadAirportButton.setDisable(false);
                createAirportButton.setDisable(false);
            }
            // Hide every button
            editAirportButton.setDisable(true);
            loadObstacleButton.setDisable(true);
            createObstacleButton.setDisable(true);
            editObstacleButton.setDisable(true);
            viewCalculationsButton.setDisable(true);
            toggleViewButton.setDisable(true);
            // Deal with runway view button
            if (this.currentAirport == null || this.currentObstacle == null
                            || currentView == splashScreen) {
                returnToRunwayViewButton.setDisable(true);
            } else {
                returnToRunwayViewButton.setDisable(false);
            }
        }
    }
}
