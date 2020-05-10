package org.comp2211.group6.view;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import org.comp2211.group6.Model.Airport;
import org.comp2211.group6.Model.ColourScheme;
import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Model.Runway;
import org.comp2211.group6.Controller.Calculator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.MenuItem;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.comp2211.group6.XMLHandler;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;

public class MainView extends GridPane implements Initializable {

    /*
     * FXML Components
     */
    private SimpleObjectProperty<Node> currentView = new SimpleObjectProperty<Node>();

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
    private ObstacleView obstacleView;
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


    /*
     * Properties
     */
    private SimpleListProperty<Obstacle> obstacles = new SimpleListProperty<Obstacle>(
        FXCollections.observableArrayList(new ArrayList<Obstacle>()));
    private SimpleObjectProperty<Airport> currentAirport = new SimpleObjectProperty<Airport>();
    private SimpleObjectProperty<Runway> currentRunway = new SimpleObjectProperty<Runway>();
    private SimpleObjectProperty<LogicalRunway> currentLogicalRunway =
        new SimpleObjectProperty<LogicalRunway>();
    private SimpleObjectProperty<Obstacle> currentObstacle = new SimpleObjectProperty<Obstacle>();
    private SimpleObjectProperty<Calculator> currentCalculator =
        new SimpleObjectProperty<Calculator>();
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

        saveBreakdown.cancelButton.addEventHandler(ActionEvent.ANY,
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    saveBreakdown.filePath.set("No directory selected");
                    changeView(breakdownView);
                }
            });

        // Bind the properties to the relevant views
        this.runwayView.airport.bindBidirectional(currentAirport);
        this.runwayView.runway.bindBidirectional(currentRunway);
        this.runwayView.currentObstacle.bindBidirectional(currentObstacle);
        this.runwayView.currentLogicalRunway.bindBidirectional(currentLogicalRunway);
        this.runwayView.allObstacles.bind(obstacles);
        this.runwayView.calculator.bindBidirectional(currentCalculator);
        this.breakdownView.calculator.bindBidirectional(currentCalculator);
        this.breakdownView.currentLogicalRunway.bindBidirectional(currentLogicalRunway);
        this.breakdownView.currentRunway.bindBidirectional(currentRunway);

        // Handler updates
        currentAirport.addListener((e, origVal, newVal) -> {
            if (newVal != null) {
                this.currentRunway.set(
                    newVal.getRunways().stream().sorted().findFirst().orElse(null));
                this.currentLogicalRunway.set(this.currentRunway.get().getLogicalRunways().stream()
                    .sorted().findFirst().orElse(null));
                this.currentObstacle.set(null);
                // Change the view and update buttons
                changeView(runwayView);
                setupButtons();
            }
        });


        // Button disable bindings
        setupButtons();
    }


    private void changeView(Node newView) {
        if (this.currentView.get() != null) {
            this.currentView.get().setVisible(false);
        }
        this.currentView.set(newView);
        this.currentView.get().setVisible(true);
    }

    @FXML
    private void invertColourScheme() {
        colourScheme.invertColourScheme();
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
            currentAirport.set(airport);
            fileView.reset();
            notifyUpdate("Airport", "loaded");
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
        airportConfigView.loadAirport(currentAirport);
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
                Obstacle newObstacle = obstacleView.getNewObstacle();
                obstacles.add(newObstacle);
                // If the current obstacle view is NOT the Create view, remove the original obstacle
                // from the list.
                if (!(obstacleView instanceof CreateAnObstacleView)) {
                    obstacles.remove(runwayView.currentObstacle.get());
                    currentObstacle.set(newObstacle);
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
                event.consume();

                notifyUpdate("Obstacle", "saved");
            }
        };

        return saveButtonHandler;
    }

    private void notifyUpdate(String type,String action){
        StringBuffer message = new StringBuffer();
        message.append(type).append(" ").append("successfully ").append(action);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        runwayView.strList.add(df.format(System.currentTimeMillis())+": "+message.toString());
        runwayView.notificationList.setItems(runwayView.strList);
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
                    runwayView.currentObstacle.get());
                changeView(runwayView);
                event.consume();
                notifyUpdate("Obstacle", "exported");
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
            notifyUpdate("Obstacle", "loaded");
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
            editAnObstacleView.obstacleSaveButton
                .setOnAction(obstacleSaveButtonAction(editAnObstacleView));
            loadAnObstacleView.obstacleExportButton.setOnAction(obstacleExportButtonAction);
            changeView(editAnObstacleView);
        }
    }

    @FXML
    private void viewCalculations(ActionEvent e) {
        if (this.currentCalculator.get().getAllBreakdowns().size() > 0)
            changeView(breakdownView);
    }

    @FXML
    private void toggleView(ActionEvent e) {
        // If the view is not a visualisation then do nothing - shouldn't be able to reach here as
        // the button will be unavailable
        runwayView.toggleRunwayView();
    }

    @FXML
    private void returnToRunwayView(ActionEvent e) {
        changeView(runwayView);
    }

    private EventHandler<ActionEvent> imageExportValidateAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            final double currentScale = runwayView.getCurrentViewScale();
            runwayView.setCurrentViewScale(1);
            runwayView.redraw();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            LocalDateTime now = LocalDateTime.now();
            String currentTime = dtf.format(now);
            String viewName = runwayView.viewTitle.getText().replaceAll("\\s+", "");

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
            String path = folderView.filePath.get();
            File file = new File(path + File.separator + viewName + "-" + currentTime + ".png");

            int width = (int) Math.round(runwayView.runwayCanvas.getWidth());
            int height = (int) Math.round(runwayView.runwayCanvas.getHeight());

            WritableImage image = new WritableImage(width, height);
            SnapshotParameters sp = new SnapshotParameters();
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(runwayView.runwayCanvas.snapshot(sp, image),
                    null), "png", file);
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
                if (currentCalculator.get().getAllBreakdowns().size() > 0) {
                    Map<LogicalRunway, String> breakdownMap =
                        currentCalculator.get().getAllBreakdowns();
                    String breakdowns = "";
                    for (Map.Entry<LogicalRunway, String> entry : breakdownMap
                        .entrySet()) {
                        LogicalRunway key = entry.getKey();
                        String value = entry.getValue();
                        breakdowns = breakdowns
                            + "========================================";
                        breakdowns = breakdowns + " " + key.getIdentifier()
                            + " ========================================";
                        breakdowns = breakdowns + "\n";
                        breakdowns = breakdowns + value;
                        breakdowns = breakdowns + "\n";
                    }

                    DateTimeFormatter dtf =
                        DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
                    LocalDateTime now = LocalDateTime.now();
                    String currentTime = dtf.format(now);

                    String path = saveBreakdown.filePath.get();

                    try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(path + File.separator
                            + "Calc_Breakdowns" + "-"
                            + currentTime + ".txt"),
                        "utf-8"))) {
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
        if (currentCalculator.get().getAllBreakdowns().size() > 0) {
            saveBreakdown.setValidator(breakdownExportValidateAction);
            changeView(saveBreakdown);
        }
    }

    private void setupButtons() {
        loadAirportButton.disableProperty().bind(currentView.isNotEqualTo(runwayView)
            .and(currentView.isNotEqualTo(splashScreen)));
        createAirportButton.disableProperty().bind(currentView.isNotEqualTo(runwayView)
            .and(currentView.isNotEqualTo(splashScreen)));
        exportImageButton.disableProperty().bind(currentView.isNotEqualTo(runwayView));
        editAirportButton.disableProperty().bind(currentView.isEqualTo(breakdownView));
        loadObstacleButton.disableProperty().bind(currentView.isEqualTo(breakdownView));
        createObstacleButton.disableProperty().bind(currentView.isEqualTo(breakdownView));
        editObstacleButton.disableProperty().bind(currentView.isEqualTo(breakdownView));
        viewCalculationsButton.disableProperty().bind(currentView.isEqualTo(breakdownView));
        exportBreakdownButton.disableProperty().bind(currentView.isEqualTo(breakdownView));
        toggleViewButton.disableProperty().bind(currentView.isEqualTo(breakdownView));
        exportImageButton.disableProperty().bind(currentView.isEqualTo(breakdownView));
        returnToRunwayViewButton.disableProperty()
            .bind(currentAirport.isNull().or(currentView.isEqualTo(splashScreen))
                .or(currentView.isEqualTo(runwayView)));

        editAirportButton.disableProperty().bind(currentAirport.isNull());
        loadObstacleButton.disableProperty().bind(currentAirport.isNull());
        createObstacleButton.disableProperty().bind(currentAirport.isNull());
        toggleViewButton.disableProperty().bind(currentAirport.isNull());
        exportImageButton.disableProperty().bind(currentAirport.isNull());
        editObstacleButton.disableProperty().bind(currentObstacle.isNull());
        viewCalculationsButton.disableProperty().bind(currentObstacle.isNull());
        exportBreakdownButton.disableProperty().bind(currentObstacle.isNull());

    }

}