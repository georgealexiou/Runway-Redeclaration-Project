package org.comp2211.group6.view;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import org.comp2211.group6.Model.Airport;
import org.comp2211.group6.Model.ColourScheme;
import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Controller.Calculator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.comp2211.group6.XMLHandler;
import javafx.embed.swing.SwingFXUtils;

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
    private AirportConfigView airportConfigView;
    @FXML
    private FileView fileView;
    @FXML
    private FolderView folderView;
    @FXML
    private SaveBreakdown saveBreakdown;
    @FXML
    public Scale u;
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
    private MenuItem exportImageButton;
    @FXML
    private MenuItem exportBreakdownButton;


    @FXML
    private VBox splashScreen;
    @FXML
    private BorderPane notificationPane;
    @FXML
    private ScrollPane scrollPane;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

        folderView.cancelButton.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                folderView.filePath.set("No directory selected");
                changeView(runwayView);
            }
        });

        saveBreakdown.cancelButton.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                saveBreakdown.filePath.set("No directory selected");
                changeView(breakdownView);
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
        this.returnToRunwayViewButton.setVisible(true);
        changeView(airportConfigView);
    };

    /*
     * listener for Save button in obstacle views. cater for different obstacle views(create, edit,
     * load)
     * 
     */
    private EventHandler<ActionEvent> obstacleSaveButtonAction(ObstacleView obstacleView) {
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
                runwayView.strList.add(df.format(System.currentTimeMillis())+": Obstacle "+ obstacles.get(obstacles.size()-1).getName() +" successfully saved");
                runwayView.notificationList.setItems(runwayView.strList);
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
        this.returnToRunwayViewButton.setVisible(true);
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
    
    private EventHandler<ActionEvent> imageExportValidateAction =
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    final double currentScale = runwayView.getCurrentViewScale();
                    runwayView.setCurrentViewScale(1);
                    runwayView.redraw();
                    
                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");  
                    LocalDateTime now = LocalDateTime.now();
                    String currentTime = dtf.format(now);
                    String viewName = runwayView.viewTitle.getText().replaceAll("\\s+","");
                    
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
                    String path = folderView.filePath.get();
                    File file = new File(path + "\\" + viewName + "-" +currentTime + ".png");
                    
                    int width = (int) Math.round(runwayView.runwayCanvas.getWidth());
                    int height = (int) Math.round(runwayView.runwayCanvas.getHeight());
                    
                    WritableImage image = new WritableImage(width, height);
                    SnapshotParameters sp = new SnapshotParameters();
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(runwayView.runwayCanvas.snapshot(sp, image), null), "png", file);
                    } catch (IOException ex) {
                        System.out.println("Failed to export runway view as an image.");
                        ex.printStackTrace();
                    }
                    folderView.filePath.set("No directory selected");
                    runwayView.setCurrentViewScale(currentScale);
                    runwayView.redraw();
                    changeView(runwayView);
                    event.consume();
                }
            };

    private EventHandler<ActionEvent> breakdownExportValidateAction =
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Calculator calc = runwayView.getCalculator();
                    if (calc.getAllBreakdowns().size() > 0) {
                        Map<LogicalRunway, String> breakdownMap = calc.getAllBreakdowns();
                        String breakdowns = "";
                        for (Map.Entry<LogicalRunway, String> entry : breakdownMap.entrySet()) {
                            LogicalRunway key = entry.getKey();
                            String value = entry.getValue();
                            breakdowns = breakdowns +
                                    "========================================";
                            breakdowns = breakdowns + " " + key.getIdentifier() +
                                                    " ========================================";
                            breakdowns = breakdowns + "\n";
                            breakdowns = breakdowns + value;
                            breakdowns = breakdowns + "\n";
                        }

                        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
                        LocalDateTime now = LocalDateTime.now();
                        String currentTime = dtf.format(now);

                        String path = saveBreakdown.filePath.get();

                        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                                new FileOutputStream(path + "\\" + "Calc_Breakdowns" + "-" + currentTime + ".txt"), "utf-8"))) {
                            writer.write(breakdowns);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    saveBreakdown.filePath.set("No directory selected");
                    changeView(saveBreakdown);
                    event.consume();
                }
            };
    
    @FXML
    private void exportImage(ActionEvent e) {
        changeView(folderView);
        folderView.setValidator(imageExportValidateAction);
    }

    @FXML
    private void exportBreakdown(ActionEvent e) {
        changeView(saveBreakdown);
        Calculator calc = this.runwayView.getCalculator();
        if (calc.getAllBreakdowns().size() > 0) {
            this.breakdownView.setAvailableBreakdowns(calc.getAllBreakdowns());
        }
        saveBreakdown.setValidator(breakdownExportValidateAction);
    }

    private void updateButtons() {
        if (currentView == runwayView) {
            // Load and Create Airport always available here
            loadAirportButton.setDisable(false);
            createAirportButton.setDisable(false);
            exportImageButton.setDisable(false);
            // Buttons you can press if an airport is loaded
            if (this.currentAirport != null) {
                editAirportButton.setDisable(false);
                loadObstacleButton.setDisable(false);
                createObstacleButton.setDisable(false);
                toggleViewButton.setDisable(false);
                exportImageButton.setDisable(false);
                // Buttons you can press if an obstacle is also loaded
                if (runwayView.currentObstacle != null) {
                    editObstacleButton.setDisable(false);
                    viewCalculationsButton.setDisable(false);
                    exportBreakdownButton.setDisable(false);
                } else {
                    editObstacleButton.setDisable(true);
                    viewCalculationsButton.setDisable(true);
                    exportBreakdownButton.setDisable(true);
                }
            } else {
                editAirportButton.setDisable(true);
                loadObstacleButton.setDisable(true);
                createObstacleButton.setDisable(true);
                toggleViewButton.setDisable(true);
                exportImageButton.setDisable(true);
            }
        } else {
            // Deal with splash screen button
            if (this.currentView != splashScreen) {
                loadAirportButton.setDisable(true);
                createAirportButton.setDisable(true);
            }
            else {
                loadAirportButton.setDisable(false);
                createAirportButton.setDisable(false);
            }

            if (this.currentView == breakdownView){
                editAirportButton.setDisable(true);
                loadObstacleButton.setDisable(true);
                createObstacleButton.setDisable(true);
                editObstacleButton.setDisable(true);
                viewCalculationsButton.setDisable(true);
                exportBreakdownButton.setDisable(false);
                toggleViewButton.setDisable(true);
                exportImageButton.setDisable(true);
            }
            else {
                // Hide every button
                editAirportButton.setDisable(true);
                loadObstacleButton.setDisable(true);
                createObstacleButton.setDisable(true);
                editObstacleButton.setDisable(true);
                viewCalculationsButton.setDisable(true);
                exportBreakdownButton.setDisable(true);
                toggleViewButton.setDisable(true);
                exportImageButton.setDisable(true);
            }
            // Deal with runway view button
            if (this.currentAirport == null || runwayView.currentObstacle == null
                            || currentView == splashScreen) {
                returnToRunwayViewButton.setDisable(true);
            } else {
                returnToRunwayViewButton.setDisable(false);
            }
        }
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }
}
