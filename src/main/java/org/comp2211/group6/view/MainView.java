package org.comp2211.group6.view;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import org.comp2211.group6.Model.Airport;
import org.comp2211.group6.Model.ColourScheme;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Controller.Calculator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.comp2211.group6.XMLHandler;

public class MainView extends GridPane implements Initializable {

    /*
     * FXML Components
     */
    private Node currentView;

    @FXML
    private RunwayView runwayView;
    @FXML
    private BreakdownView breakdownView;
    @FXML
    private EditAnObstacleView editAnObstacleView;
    @FXML
    private CreateAnObstacleView createAnObstacleView;
    @FXML
    private LoadAnObstacleView loadAnObstacleView;
    @FXML
    private FileView fileView;

    @FXML
    private MenuItem returnToRunwayViewButton;
    @FXML
    private MenuItem loadAirportButton;
    @FXML
    private MenuItem createAirportButton;
    @FXML
    private MenuItem loadObstacleButton;
    @FXML
    private MenuItem createObstacleButton;
    @FXML
    private MenuItem editAirportButton;
    @FXML
    private MenuItem editObstacleButton;
    @FXML
    private MenuItem viewCalculationsButton;
    @FXML
    private MenuItem toggleViewButton;


    @FXML
    private VBox splashScreen;


    @FXML
    private Label notificationLabel;
    /*
     * Properties
     */
    private Airport currentAirport;
    private List<Obstacle> obstacles = new ArrayList<Obstacle>();
    private ColourScheme colourScheme = ColourScheme.getInstance();

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
        colourScheme.invertColourScheme();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileView.cancelButton.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fileView.reset();
                if (currentAirport != null) {
                    changeView(runwayView);
                } else {
                    changeView(splashScreen);
                }
            }
        });
    }

    private void changeView(Node newView) {
        if (this.currentView != null) {
            this.currentView.setVisible(false);
        }
        this.currentView = newView;
        this.currentView.setVisible(true);
        updateAirportFields();
    }

    @FXML
    private void invertColourScheme() {
        colourScheme.invertColourScheme();
    }

    /*
     * Set the airport currently displayed
     */
    private void setAirport(Airport airport) {
        this.currentAirport = airport;
        runwayView.setRunway(airport.getRunways().stream().findFirst().orElse(null));
        changeView(runwayView);
        updateButtons();
    }

    /*
     * Updates the fields in the side panel
     */
    private void updateAirportFields() {
        if (this.currentAirport != null) {
            this.runwayView.currentAirportName.setText(this.currentAirport.getName());
            this.runwayView.updateRunwayPicker(
                            currentAirport.getRunways().stream().collect(Collectors.toList()));
            this.runwayView.updateObstaclePicker(obstacles);
        }
        updateButtons();
    }



    @FXML
    private void loadAirport(ActionEvent e) {
        fileView.setValidator(airportLoadHandler);
        changeView(fileView);
    }

    private EventHandler<ActionEvent> airportLoadHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            XMLHandler handler = new XMLHandler();
            Airport airport = handler.readAirportXML(fileView.filePath.get());
            setAirport(airport);
            fileView.reset();
        }
    };

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
                    obstacles.remove(runwayView.currentObstacle);
                    runwayView.setObstacle(obstacleView.getNewObstacle());
                }
                changeView(runwayView);

                obstacleView.obstacleName.clear();
                obstacleView.obstacleDescription.clear();
                obstacleView.obstacleLength.clear();
                obstacleView.obstacleWidth.clear();
                obstacleView.obstacleHeight.clear();
                obstacleView.obstacleDistanceFromCentreLine.clear();
                obstacleView.obstacleDistanceFromLeft.clear();
                obstacleView.obstacleDistanceFromRight.clear();
                notificationLabel.setText("Obstacle successfully saved");
                event.consume();
            }
        };

        return saveButtonHandler;
    }

    private EventHandler<ActionEvent> obstacleExportButtonAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            fileView.setTitle("Choose a location to export the obstacle");
            fileView.setLoading(false);
            fileView.setValidator(obstacleExportValidateAction);
            changeView(fileView);
            event.consume();
        }
    };
    private EventHandler<ActionEvent> obstacleExportValidateAction =
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            XMLHandler xml = new XMLHandler();
                            xml.saveObstacleToXML(fileView.filePath.get(),
                                            runwayView.currentObstacle);
                            event.consume();
                        }
                    };

    @FXML
    private void loadObstacle(ActionEvent e) {
        fileView.setTitle("Choose an obstacle to load");
        fileView.setValidator(loadObstacleHandler);
        changeView(fileView);
    }

    private EventHandler<ActionEvent> loadObstacleHandler = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            XMLHandler xml = new XMLHandler();
            loadAnObstacleView.loadPredefinedObstacle(xml.readObstacleXML(fileView.filePath.get()));
            loadAnObstacleView.obstacleSaveButton
                            .setOnAction(obstacleSaveButtonAction(loadAnObstacleView));
            loadAnObstacleView.obstacleExportButton.setOnAction(obstacleExportButtonAction);
            changeView(loadAnObstacleView);
            fileView.reset();
        }
    };

    @FXML
    private void createObstacle(ActionEvent e) {
        createAnObstacleView.obstacleSaveButton
                        .setOnAction(obstacleSaveButtonAction(createAnObstacleView));
        loadAnObstacleView.obstacleExportButton.setOnAction(obstacleExportButtonAction);
        changeView(createAnObstacleView);
    }

    @FXML
    private void editObstacle(ActionEvent e) {
        if (runwayView.currentObstacle == null) {
            throw new NullPointerException("No obstacle can be edited.");
        } else {
            this.returnToRunwayViewButton.setVisible(true);
            editAnObstacleView.loadCurrentObstacle(runwayView.currentObstacle);
            editAnObstacleView.obstacleSaveButton
                            .setOnAction(obstacleSaveButtonAction(editAnObstacleView));
            loadAnObstacleView.obstacleExportButton.setOnAction(obstacleExportButtonAction);
            changeView(editAnObstacleView);
        }
    }

    @FXML
    private void viewCalculations(ActionEvent e) {
        Calculator calc = this.runwayView.getCalculator();
        if (calc.getAllBreakdowns().size() > 0) {
            this.breakdownView.setAvailableBreakdowns(calc.getAllBreakdowns());
            changeView(breakdownView);
        } else {
            return;
        }
    }

    @FXML
    private void toggleView(ActionEvent e) {
        // If the view is not a visualisation then do nothing - shouldn't be able to reach here as
        // the button will be unavailable
        if (this.currentView != runwayView) {
            return;
        }
        runwayView.toggleRunwayView();
    }

    @FXML
    private void returnToRunwayView(ActionEvent e) {
        this.returnToRunwayViewButton.setVisible(false);
        changeView(runwayView);
    }

    private void updateButtons() {
        if (currentView == runwayView) {
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
                if (runwayView.currentObstacle != null) {
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
            if (this.currentAirport == null || runwayView.currentObstacle == null
                            || currentView == splashScreen) {
                returnToRunwayViewButton.setDisable(true);
            } else {
                returnToRunwayViewButton.setDisable(false);
            }
        }
    }

}
