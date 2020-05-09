package org.comp2211.group6.view;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.ListView;
import org.comp2211.group6.Controller.Calculator;
import org.comp2211.group6.Model.Airport;
import org.comp2211.group6.Model.ColourScheme;
import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Model.Runway;
import org.comp2211.group6.Model.RunwayParameters;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import jGPath;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;
import javafx.util.StringConverter;

public class RunwayView extends GridPane implements Initializable {

    /*
     * Data
     */
    public SimpleObjectProperty<Airport> airport = new SimpleObjectProperty<Airport>();
    public SimpleObjectProperty<Runway> runway = new SimpleObjectProperty<Runway>();
    public SimpleObjectProperty<Obstacle> currentObstacle = new SimpleObjectProperty<Obstacle>();
    public SimpleObjectProperty<LogicalRunway> currentLogicalRunway =
                    new SimpleObjectProperty<LogicalRunway>();
    public SimpleListProperty<Obstacle> allObstacles = new SimpleListProperty<Obstacle>();
    public SimpleObjectProperty<Calculator> calculator = new SimpleObjectProperty<Calculator>();

    protected double runwayWidth = 100;
    protected double runwayArrowPadding = 25;

    /*
     * Distances needs for drawing top down view - 20 px Padding Left - n px Strip End - n px
     * Clearway (Includes stopway) - n px Runway (Largest TORA) - n px Clearway (Includes stopway) -
     * n px Strip End - 20 px Padding Right
     */
    protected double padding = 20;
    protected double stripEnd = 60;
    protected double leftClearway;
    protected double leftStopway;
    protected double leftOffset;
    protected double rightClearway;
    protected double rightStopway;
    protected double rightOffset;
    protected double runwayLength; // Maximum TORA

    protected double obstacleLeft;
    protected double totalLength;

    /**
     * Colour for runway view components
     */
    private ColourScheme colours = ColourScheme.getInstance();

    /*
     * View Components
     */
    @FXML
    protected ComboBox<LogicalRunway> logicalRunwayPicker;
    @FXML
    protected SVGPath runwayDirectionArrow;
    @FXML
    protected Canvas runwayCanvas;
    @FXML
    private ScrollPane canvasContainer;
    @FXML
    protected Label viewTitle;

    @FXML
    public Label currentAirportName;
    @FXML
    public ComboBox<Runway> runwayPicker;
    @FXML
    public ComboBox<Obstacle> obstaclePicker;

    @FXML
    private Slider zoomSlider;
    @FXML
    private Label zoomLabel;

    @FXML
    private Rectangle blastDistanceKey;
    @FXML
    private Rectangle slopeCalcKey;
    @FXML
    private Rectangle stripEndKey;
    @FXML
    private Rectangle resaKey;
    @FXML
    protected ListView notificationList;

    protected ObservableList<String> strList = FXCollections.observableArrayList();

    private boolean topDownViewActive = true;

    // 1 is the base zoom
    private double currentViewScale = 1;

    /**
     * Used for panning
     */
    private double pressedX;
    private double pressedY;

    /*
     * Construct a new RunwayView
     */
    public RunwayView() {
        loadFxml(getClass().getResource("/runway_view.fxml"), this);
        if (topDownViewActive) {
            this.viewTitle.setText("Top Down View");
            this.runwayWidth = 100;
            this.runwayArrowPadding = 25;
        } else {
            this.viewTitle.setText("Side On View");
            this.runwayWidth = 10;
            this.runwayArrowPadding = 40;
        }
        setupLogicalRunwayPicker(FXCollections.observableArrayList());
        updateObstaclePicker(FXCollections.observableArrayList());
        updateRunwayPicker(FXCollections.observableArrayList());
        setupZoomSlider();
        colours.getCurrentThemeProperty().addListener((o, orig, newVal) -> {
            if (this.currentLogicalRunway.get() != null) {
                this.redrawRunway();
            }
        });
    }

    public void toggleRunwayView() {
        topDownViewActive = !topDownViewActive;
        if (topDownViewActive) {
            this.viewTitle.setText("Top Down View");
            this.runwayWidth = 100;
            this.runwayArrowPadding = 25;
        } else {
            this.viewTitle.setText("Side On View");
            this.runwayWidth = 10;
            this.runwayArrowPadding = 40;
        }
        this.redrawRunway();
    }

    public double getCurrentViewScale() {
        return currentViewScale;
    }

    public void setCurrentViewScale(double currentViewScale) {
        this.currentViewScale = currentViewScale;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        canvasContainer.setVbarPolicy(ScrollBarPolicy.NEVER);
        canvasContainer.setHbarPolicy(ScrollBarPolicy.NEVER);
        canvasContainer.widthProperty().addListener(observable -> redraw());
        canvasContainer.heightProperty().addListener(observable -> redraw());
        // Add event handlers for panning and zooming
        runwayCanvas.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                pressedX = event.getX();
                pressedY = event.getY();
            }
        });
        runwayCanvas.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                runwayCanvas.setTranslateX(runwayCanvas.getTranslateX() + event.getX() - pressedX);
                runwayCanvas.setTranslateY(runwayCanvas.getTranslateY() + event.getY() - pressedY);

                event.consume();
            }
        });
        canvasContainer.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    runwayCanvas.setTranslateX(0);
                    runwayCanvas.setTranslateY(0);
                    scaleView();
                }
                event.consume();
            }
        });
        runwayCanvas.addEventFilter(ScrollEvent.ANY, new EventHandler<ScrollEvent>() {
            public void handle(ScrollEvent event) {
                if (event.getDeltaY() < 0) {
                    scaleView(currentViewScale - 0.1);
                }
                if (event.getDeltaY() > 0) {
                    scaleView(currentViewScale + 0.1);
                }
            }
        });
        blastDistanceKey.fillProperty().bind(this.colours.getBlastDistanceColourProperty());
        stripEndKey.fillProperty().bind(this.colours.getStripEndColourProperty());
        resaKey.fillProperty().bind(this.colours.getResaColourProperty());
        runwayDirectionArrow.fillProperty().bind(colours.getTextColourProperty());

        this.airport.addListener((e, origVal, newVal) -> {
            this.updateRunwayPicker(newVal.getRunways());
        });
        this.runway.addListener((e, origVal, newVal) -> {
            if (newVal != null) {
                this.setupLogicalRunwayPicker(
                                FXCollections.observableArrayList(newVal.getLogicalRunways()));
                this.currentLogicalRunway.set(newVal.getLogicalRunways().stream().sorted()
                                .findFirst().orElse(null));
                this.redrawRunway();
            }
        });
        this.currentLogicalRunway.addListener((e, origVal, newVal) -> {
            if (newVal != null) {
                this.redrawRunway();
            }
        });
        this.allObstacles.addListener((e, origVal, newVal) -> {
            if (newVal.size() > 0) {
                updateObstaclePicker(newVal);
            }
        });
        this.currentObstacle.addListener((e, origVal, newVal) -> {
            if (newVal != null) {
                this.runway.get().setObstacle(newVal);
                Calculator calc = new Calculator(this.runway.get());
                calc.recalculateRunwayParameters();
                this.calculator.set(calc);
            }
            this.redrawRunway();
        });
    }

    /*
     * Sets up the runway logical runway combo box with new values
     * 
     * @param data The list of logical runways to have in the combo picker
     */
    private void setupLogicalRunwayPicker(ObservableList<LogicalRunway> data) {
        // Update the values
        currentLogicalRunway.set(null);
        logicalRunwayPicker.setItems(data);
        logicalRunwayPicker.getSelectionModel().selectFirst();
        // Re-create the event listener and string coverter
        logicalRunwayPicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            this.currentLogicalRunway.set(newVal);
        });
        logicalRunwayPicker.setConverter(new StringConverter<LogicalRunway>() {
            @Override
            public String toString(LogicalRunway object) {
                if (object == null)
                    return null;
                return object.getIdentifier();
            }

            @Override
            public LogicalRunway fromString(String string) {
                return logicalRunwayPicker.getItems().stream()
                                .filter(ap -> ap.getIdentifier().equals(string)).findFirst()
                                .orElse(null);
            }
        });
        // Update the current logical runway
        currentLogicalRunway.set(logicalRunwayPicker.getValue());
    }

    public void updateRunwayPicker(List<Runway> runways) {
        runwayPicker.setItems(FXCollections.observableArrayList(runways));
        runwayPicker.getSelectionModel().selectFirst();
        runwayPicker.valueProperty().addListener((e, oldVal, newVal) -> {
            runway.set(newVal);
            // Clear current obstacle
            obstaclePicker.getSelectionModel().clearSelection();
        });
        runwayPicker.setConverter(new StringConverter<Runway>() {
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

    public void updateObstaclePicker(List<Obstacle> obstacles) {
        obstaclePicker.setItems(FXCollections.observableArrayList(obstacles));
        obstaclePicker.getSelectionModel().selectFirst();
        obstaclePicker.valueProperty().addListener((e, oldVal, newVal) -> {
            this.currentObstacle.set(newVal);
        });
        obstaclePicker.setConverter(new StringConverter<Obstacle>() {
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

    private void setupZoomSlider() {
        zoomSlider.valueProperty().addListener((e, oldVal, newVal) -> {
            scaleView(newVal.doubleValue());
            zoomLabel.setText(String.format("%.2f%%", newVal.doubleValue() * 100));
        });
        zoomLabel.setText(String.format("%.2f%%", currentViewScale * 100));
    }

    @FXML
    private void scaleView() {
        scaleView(1);
    }

    private void scaleView(double scale) {
        if (scale < zoomSlider.getMin() || scale > zoomSlider.getMax()) {
            return;
        }
        if (zoomSlider.getValue() != scale) {
            zoomSlider.setValue(scale);
        }
        currentViewScale = scale;
        redrawRunway();
    }

    /*
     * Returns the runway currently in use
     */
    public Runway getRunway() {
        return this.runway.get();
    }

    /*
     * This method is called when the runway or logical runway is changed, or if the obstacle is
     * updated Override this to add more functionality
     */
    private void redrawRunway() {
        // Handle takeoff landing direction arrow
        if (currentLogicalRunway.get() != null) {
            if (currentLogicalRunway.get().getHeading() <= 18) {
                this.runwayDirectionArrow.setRotate(0);
            } else {
                this.runwayDirectionArrow.setRotate(180);
            }
            recalculateDataValues();
            redraw();
        }
    }

    protected void redraw() {
        double canvasMinWidth = 800 * currentViewScale;
        double canvasMinHeight = 600 * currentViewScale;
        runwayCanvas.setHeight(
                        canvasContainer.getHeight() > canvasMinHeight ? canvasContainer.getHeight()
                                        : canvasMinHeight);
        runwayCanvas.setWidth(
                        canvasContainer.getWidth() > canvasMinWidth ? canvasContainer.getWidth()
                                        : canvasMinWidth);
        runwayCanvas.setScaleX(currentViewScale);
        runwayCanvas.setScaleY(currentViewScale);
        GraphicsContext gc = runwayCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, runwayCanvas.getWidth(), runwayCanvas.getHeight());
        if (this.topDownViewActive) {
            if (this.runway.get() != null) {
                drawClearedAndGraded(gc);
                drawTopDownStrip(gc);
                drawThresholdMarkers(gc);
                drawDisplacedThreshold(gc);
                drawRunwayParams(gc, true);
                if (currentObstacle.get() != null) {
                    drawTopDownObstacle(gc);
                    if (currentLogicalRunway.get().getRecalculatedParameters() != null) {
                        drawRunwayParams(gc, false);
                    }
                }
            }
        } else {
            gc.setLineWidth(2);
            if (this.runway.get() != null) {
                drawRunwayStrip(gc);
                drawDisplacedThreshold(gc);
                drawThresholdMarkers(gc);
                drawRunwayParams(gc, true);
                if (this.currentObstacle.get() != null) {
                    drawSideOnObstacle(gc);
                    if (this.currentLogicalRunway.get().getRecalculatedParameters() != null) {
                        drawRunwayParams(gc, false);
                        drawSlope(gc);
                    }
                }
            }
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


    protected double calculateClearway(LogicalRunway lr) {
        return lr.getParameters().getTODA() - lr.getParameters().getTORA();
    }

    protected double calculateStopway(LogicalRunway lr) {
        return lr.getParameters().getASDA() - lr.getParameters().getTORA();
    }

    protected double calculateRunwayEndSize(LogicalRunway lr) {
        return Math.max(calculateClearway(lr), calculateStopway(lr));
    }

    /*
     * Recalculates the data values for the current runway
     */
    protected void recalculateDataValues() {
        // Clear all values
        this.rightClearway = 0;
        this.leftClearway = 0;
        this.rightStopway = 0;
        this.leftStopway = 0;
        this.runwayLength = 0;
        this.totalLength = 0;
        this.obstacleLeft = 0;
        double addToObstaceLeft = 0;
        Iterator<LogicalRunway> lrs = this.runway.get().getLogicalRunways().iterator();
        while (lrs.hasNext()) {
            LogicalRunway lr = lrs.next();
            double clearway = calculateClearway(lr);
            double stopway = calculateClearway(lr);
            if (lr.getHeading() <= 18) { // On left so set right stopway and clearway
                this.rightClearway = clearway > this.rightClearway ? clearway : this.rightClearway;
                this.rightStopway = stopway > this.rightStopway ? stopway : this.rightStopway;
                addToObstaceLeft = lr.getDisplacedThreshold();
            } else {
                this.leftClearway = clearway > this.leftStopway ? clearway : this.leftClearway;
                this.leftStopway = stopway > this.leftStopway ? stopway : this.leftStopway;
            }
            if (lr.getParameters().getTORA() > this.runwayLength) {
                this.runwayLength = lr.getParameters().getTORA();
            }
        }
        this.leftOffset =
                        padding + Math.max(stripEnd, Math.max(this.leftClearway, this.leftStopway));
        this.rightOffset = padding
                        + Math.max(stripEnd, Math.max(this.rightClearway, this.rightStopway));
        this.obstacleLeft = this.leftOffset + addToObstaceLeft;
        this.totalLength = this.leftOffset + this.runwayLength + this.rightOffset;

    }

    protected void drawRunwayStrip(GraphicsContext gc) {
        double canvasMiddleY = runwayCanvas.getHeight() / 2;

        // Draw the tarmac
        gc.setFill(colours.getRunwayColour());
        gc.fillRect(scale(leftOffset, runwayCanvas.getWidth()), canvasMiddleY - (runwayWidth / 2),
                        scale(runwayLength, runwayCanvas.getWidth()), runwayWidth);

        // Draw the clearway and stopway
        gc.setFill(colours.getClearwayColour());
        gc.fillRect(scale(leftOffset - leftClearway, runwayCanvas.getWidth()),
                        canvasMiddleY - (runwayWidth / 2),
                        scale(leftClearway, runwayCanvas.getWidth()), runwayWidth);
        gc.fillRect(scale(leftOffset + runwayLength, runwayCanvas.getWidth()),
                        canvasMiddleY - (runwayWidth / 2),
                        scale(rightClearway, runwayCanvas.getWidth()), runwayWidth);
        gc.setFill(colours.getStopwayColour());
        gc.fillRect(scale(leftOffset - leftStopway, runwayCanvas.getWidth()),
                        canvasMiddleY - (runwayWidth / 2),
                        scale(leftStopway, runwayCanvas.getWidth()), runwayWidth);
        gc.fillRect(scale(leftOffset + runwayLength, runwayCanvas.getWidth()),
                        canvasMiddleY - (runwayWidth / 2),
                        scale(rightStopway, runwayCanvas.getWidth()), runwayWidth);
    }

    private void drawTopDownStrip(GraphicsContext gc) {
        drawRunwayStrip(gc);
        double canvasMiddleY = runwayCanvas.getHeight() / 2; // Draw the centre line
        gc.setStroke(colours.getCenterLineColour());
        gc.setLineWidth(2);
        gc.setLineDashes(50);
        gc.strokeLine(scale(leftOffset, runwayCanvas.getWidth()), canvasMiddleY,
                        scale(runwayLength, runwayCanvas.getWidth()), canvasMiddleY);

    }

    private void drawTopDownObstacle(GraphicsContext gc) {
        gc.setFill(colours.getObstacleColour());
        double startX = scale(obstacleLeft + currentObstacle.get().distanceFromLeftThreshold,
                        runwayCanvas.getWidth());
        double y = runwayCanvas.getHeight() / 2 - currentObstacle.get().distanceToCentreLine
                        - currentObstacle.get().getWidth() / 2;
        gc.fillRect(startX - 5, y, 10, currentObstacle.get().getWidth());
        drawArrow(gc, startX, runwayCanvas.getHeight() / 2 + (runwayArrowPadding) * 8, startX,
                        y + 5, colours.getTextColour());
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(currentObstacle.get().getName(), startX,
                        runwayCanvas.getHeight() / 2 + (runwayArrowPadding) * 8 + 10);
    }

    private void drawSideOnObstacle(GraphicsContext gc) {
        gc.setFill(colours.getObstacleColour());
        double startX = scale(obstacleLeft + currentObstacle.get().distanceFromLeftThreshold,
                        runwayCanvas.getWidth());
        double y = runwayCanvas.getHeight() / 2 - runwayWidth / 2;
        double xs[] = {startX - 5, startX, startX + 5};
        double ys[] = {y, y - 2 * currentObstacle.get().getHeight(), y};
        gc.fillPolygon(xs, ys, 3);
        gc.setFill(colours.getTextColour());
        gc.setStroke(colours.getTextColour());
        gc.strokeLine(startX, runwayCanvas.getHeight() / 2 + (runwayArrowPadding) * 8, startX,
                        y - 2 * currentObstacle.get().getHeight() + 5);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(currentObstacle.get().getName(), startX,
                        runwayCanvas.getHeight() / 2 + (runwayArrowPadding) * 8 + 10);
    }

    /**
     * @param over Is this a landing over or towards situation
     */
    private void drawSlope(GraphicsContext gc) {
        boolean over = this.currentLogicalRunway.get().breakdown.getDirection();
        double startY, endY, startX, endX;
        if (over) {
            startY = runwayCanvas.getHeight() / 2 - runwayWidth / 2;
            endY = startY - 2 * currentObstacle.get().getHeight();
            endX = scale(obstacleLeft + currentObstacle.get().distanceFromLeftThreshold,
                            runwayCanvas.getWidth());
            startX = endX + scale(50 * currentObstacle.get().getHeight(), runwayCanvas.getWidth());
        } else {
            endY = runwayCanvas.getHeight() / 2 - runwayWidth / 2;
            startY = endY - 2 * currentObstacle.get().getHeight();
            startX = scale(obstacleLeft + currentObstacle.get().distanceFromLeftThreshold,
                            runwayCanvas.getWidth());
            endX = startX + scale(50 * currentObstacle.get().getHeight(), runwayCanvas.getWidth());
        }
        drawArrow(gc, startX, startY, endX, endY, this.colours.getSlopeCalculationColour());
    }

    private void drawClearedAndGraded(GraphicsContext gc) {
        gc.setFill(this.colours.getClearedAndGradedAreaColour());
        double size = runwayCanvas.getWidth();
        // Points clockwise round image
        double xpoints[] = {scale(padding, size), scale(padding + 60 + 150, size),
                        scale(padding + 60 + 300, size),
                        scale(leftOffset + runwayLength - 300, size),
                        scale(leftOffset + runwayLength - 150, size), size - scale(padding, size),
                        size - scale(padding, size), scale(leftOffset + runwayLength - 150, size),
                        scale(leftOffset + runwayLength - 300, size),
                        scale(padding + 60 + 300, size), scale(padding + 60 + 150, size),
                        scale(padding, size)};

        double centre = runwayCanvas.getHeight() / 2;
        double ypoints[] = {centre - 75, centre - 75, centre - 105, centre - 105, centre - 75,
                        centre - 75, centre + 75, centre + 75, centre + 105, centre + 105,
                        centre + 75, centre + 75};
        gc.fillPolygon(xpoints, ypoints, 12);
    }

    protected void drawDisplacedThreshold(GraphicsContext gc) {
        if (currentLogicalRunway == null)
            return;
        double threshold = currentLogicalRunway.get().getDisplacedThreshold();
        double canvasMiddleY = runwayCanvas.getHeight() / 2;
        if (threshold == 0)
            return;
        gc.setStroke(colours.getDisplacedThresholdColour());
        double endX, startX;
        if (currentLogicalRunway.get().getHeading() <= 18) {
            endX = scale(leftOffset + threshold, runwayCanvas.getWidth());
            startX = scale(leftOffset, runwayCanvas.getWidth());
        } else {
            endX = scale(leftOffset + runwayLength - threshold, runwayCanvas.getWidth());
            startX = scale(leftOffset + runwayLength, runwayCanvas.getWidth());
        }
        gc.setStroke(colours.getDisplacedThresholdColour());
        gc.setLineDashes();
        gc.strokeLine(endX, canvasMiddleY + (runwayWidth / 2), endX,
                        canvasMiddleY - (runwayWidth / 2));
        drawDistanceArrow(gc, startX, endX, (runwayArrowPadding), true, colours.getTextColour(),
                        "DT: " + currentLogicalRunway.get().getDisplacedThreshold() + "m");

    }

    protected void drawThresholdMarkers(GraphicsContext gc) {
        Iterator<LogicalRunway> lrs = this.runway.get().getLogicalRunways().iterator();
        double canvasMiddleY = runwayCanvas.getHeight() / 2;
        while (lrs.hasNext()) {
            LogicalRunway lr = lrs.next();
            double x, y;
            char pos = lr.getIdentifier().toCharArray()[2];
            String heading = lr.getIdentifier().substring(0, 2);
            if (pos == 'L') {
                y = canvasMiddleY - (runwayWidth / 3);
            } else if (pos == 'C') {
                y = canvasMiddleY;
            } else {
                y = canvasMiddleY + (runwayWidth / 3) - 5;
            }
            if (lr.getHeading() <= 18) { // If on left put on left
                x = scale(leftOffset, runwayCanvas.getWidth()) + 20;
            } else {
                x = scale(leftOffset + runwayLength, runwayCanvas.getWidth()) - 20;
            }

            if (lr == this.currentLogicalRunway.get()) {
                gc.setFill(this.colours.getHighlightedThresholdBackgroundColour());
                gc.fillRect(x - 15, y - 15, 30, 35);
                gc.setFill(this.colours.getTextColour());
            } else {
                gc.setFill(this.colours.getRunwayColour());
                gc.fillRect(x - 15, y - 15, 30, 35);
                gc.setFill(this.colours.getHighlightedThresholdTextColour());
            }
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText(heading + "\n" + pos, x, y);
        }
    }

    protected void drawRunwayParams(GraphicsContext gc, boolean original) {
        RunwayParameters params = original ? currentLogicalRunway.get().getParameters()
                        : currentLogicalRunway.get().getRecalculatedParameters();
        double startX;
        int mult;
        if (currentLogicalRunway.get().getHeading() <= 18) {
            startX = scale(leftOffset, runwayCanvas.getWidth());
            mult = 1;
        } else {
            startX = scale(leftOffset + runwayLength, runwayCanvas.getWidth());
            mult = -1;
        }
        boolean towardstowards = this.currentLogicalRunway.get().breakdown.getDirection();
        paramsHelper(gc, params, towardstowards, original, mult, 2, startX);
    }

    /*
     * Draws the TORA, TODA and ASDA
     */
    private void paramsHelper(GraphicsContext gc, RunwayParameters params, boolean towards,
                    boolean original, int mult, int pos, double startX) {
        String toraLabel = "TORA: " + params.getTORA() + "m";
        double tora = params.getTORA();
        String todaLabel = "TODA: " + params.getTODA() + "m";
        double toda = params.getTODA();
        String asdaLabel = "ASDA: " + params.getASDA() + "m";
        double asda = params.getASDA();
        String ldaLabel = "LDA: " + params.getLDA() + "m";
        double lda = params.getLDA();
        if (original) {
            double endX = startX + (mult * scale(tora, runwayCanvas.getWidth()));
            drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original,
                            colours.getTextColour(), toraLabel);
            endX = startX + (mult * scale(toda, runwayCanvas.getWidth()));
            drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original,
                            colours.getTextColour(), todaLabel);
            endX = startX + (mult * scale(asda, runwayCanvas.getWidth()));
            drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original,
                            colours.getTextColour(), asdaLabel);
            startX += (mult * scale(currentLogicalRunway.get().getDisplacedThreshold(),
                            runwayCanvas.getWidth()));
            endX = startX + (mult * scale(lda, runwayCanvas.getWidth()));
            drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original,
                            colours.getTextColour(), ldaLabel);
        } else {
            toraLabel = "(R)" + toraLabel;
            todaLabel = "(R)" + todaLabel;
            asdaLabel = "(R)" + asdaLabel;
            ldaLabel = "(R)" + ldaLabel;
            if (towards) {
                double endX = startX + (mult * scale(tora, runwayCanvas.getWidth()));
                drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original,
                                colours.getTextColour(), toraLabel);
                endX = startX + (mult * scale(toda, runwayCanvas.getWidth()));
                drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original,
                                colours.getTextColour(), todaLabel);
                endX = startX + (mult * scale(asda, runwayCanvas.getWidth()));
                drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original,
                                colours.getTextColour(), asdaLabel);
                startX += scale(currentLogicalRunway.get().getDisplacedThreshold() * mult,
                                runwayCanvas.getWidth());
                endX = startX + (mult * scale(lda, runwayCanvas.getWidth()));
                drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos, original,
                                colours.getTextColour(), ldaLabel);
                // Draw the strip end and resa for the landing component
                double landingStartX = endX;
                endX = landingStartX + (mult
                                * scale(this.currentLogicalRunway.get().breakdown.getStripEnd(),
                                                runwayCanvas.getWidth()));
                drawDistanceArrow(gc, landingStartX, endX, runwayArrowPadding * pos, original,
                                colours.getStripEndColour(),
                                this.currentLogicalRunway.get().breakdown.getStripEnd() + "m",
                                false);
                landingStartX = endX;
                endX = landingStartX
                                + (mult * scale(this.currentLogicalRunway.get().breakdown.getResa(),
                                                runwayCanvas.getWidth()));
                drawDistanceArrow(gc, landingStartX, endX, runwayArrowPadding * pos, original,
                                colours.getResaColour(),
                                this.currentLogicalRunway.get().breakdown.getResa() + "m", false);

                // When taking off we have a strip end and then the RESA or slope calculation
                double takeoffStartX = startX + (mult * scale(asda, runwayCanvas.getWidth()));
                endX = takeoffStartX + (mult
                                * scale(this.currentLogicalRunway.get().breakdown.getStripEnd(),
                                                runwayCanvas.getWidth()));
                drawDistanceArrow(gc, takeoffStartX, endX, runwayArrowPadding * (pos - 2), original,
                                colours.getStripEndColour(),
                                this.currentLogicalRunway.get().breakdown.getStripEnd() + "m",
                                false);

                takeoffStartX = endX;
                if (this.currentLogicalRunway.get().breakdown
                                .getResa() > this.currentLogicalRunway.get().breakdown
                                                .getSlopeCalculation()) {
                    endX = takeoffStartX + (mult
                                    * scale(this.currentLogicalRunway.get().breakdown.getResa(),
                                                    runwayCanvas.getWidth()));
                    drawDistanceArrow(gc, takeoffStartX, endX, runwayArrowPadding * (pos - 2),
                                    original, colours.getResaColour(),
                                    this.currentLogicalRunway.get().breakdown.getResa() + "m",
                                    false);
                } else {
                    endX = takeoffStartX + (mult * scale(
                                    this.currentLogicalRunway.get().breakdown.getSlopeCalculation(),
                                    runwayCanvas.getWidth()));
                    drawDistanceArrow(gc, takeoffStartX, endX, runwayArrowPadding * (pos - 2),
                                    original, colours.getSlopeCalculationColour(),
                                    this.currentLogicalRunway.get().breakdown.getSlopeCalculation()
                                                    + "m",
                                    false);
                }
            } else {
                double originalStartx = startX;
                double endX = originalStartx
                                + scale((mult * runwayLength), runwayCanvas.getWidth());
                startX = endX - scale(mult * tora, runwayCanvas.getWidth());
                drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original,
                                colours.getTextColour(), toraLabel);
                endX = originalStartx + scale(
                                (mult * runwayLength
                                                + calculateClearway(currentLogicalRunway.get())),
                                runwayCanvas.getWidth());
                startX = endX - scale(mult * toda, runwayCanvas.getWidth());
                drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original,
                                colours.getTextColour(), todaLabel);
                endX = originalStartx + scale(
                                (mult * runwayLength
                                                + calculateStopway(currentLogicalRunway.get())),
                                runwayCanvas.getWidth());
                startX = endX - scale(mult * asda, runwayCanvas.getWidth());
                drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original,
                                colours.getTextColour(), asdaLabel);
                startX = endX - scale(mult * lda, runwayCanvas.getWidth());
                drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos, original,
                                colours.getTextColour(), ldaLabel);

                double takeOffEndX = originalStartx
                                + scale((mult * runwayLength
                                                + calculateStopway(currentLogicalRunway.get())),
                                                runwayCanvas.getWidth())
                                - scale(mult * asda, runwayCanvas.getWidth());
                startX = takeOffEndX - (mult * scale(
                                this.currentLogicalRunway.get().breakdown.getBlastProtection(),
                                runwayCanvas.getWidth()));
                drawDistanceArrow(gc, startX, takeOffEndX, runwayArrowPadding * (pos - 2), original,
                                colours.getBlastDistanceColour(),
                                this.currentLogicalRunway.get().breakdown.getBlastProtection()
                                                + "m",
                                false);

                takeOffEndX = originalStartx
                                + scale((mult * runwayLength
                                                + calculateStopway(currentLogicalRunway.get())),
                                                runwayCanvas.getWidth())
                                - scale(mult * lda, runwayCanvas.getWidth());

                startX = takeOffEndX - (mult
                                * scale(this.currentLogicalRunway.get().breakdown.getStripEnd(),
                                                runwayCanvas.getWidth()));
                drawDistanceArrow(gc, startX, takeOffEndX, runwayArrowPadding * (pos), original,
                                colours.getStripEndColour(),
                                this.currentLogicalRunway.get().breakdown.getStripEnd() + "m",
                                false);
                takeOffEndX = startX;
                if (this.currentLogicalRunway.get().breakdown
                                .getResa() > this.currentLogicalRunway.get().breakdown
                                                .getSlopeCalculation()) {
                    startX = takeOffEndX - (mult
                                    * scale(this.currentLogicalRunway.get().breakdown.getResa(),
                                                    runwayCanvas.getWidth()));
                    drawDistanceArrow(gc, startX, takeOffEndX, runwayArrowPadding * (pos), original,
                                    colours.getResaColour(),
                                    this.currentLogicalRunway.get().breakdown.getResa() + "m",
                                    false);
                } else {
                    startX = takeOffEndX - (mult * scale(
                                    this.currentLogicalRunway.get().breakdown.getSlopeCalculation(),
                                    runwayCanvas.getWidth()));
                    drawDistanceArrow(gc, startX, takeOffEndX, runwayArrowPadding * (pos), original,
                                    colours.getSlopeCalculationColour(),
                                    this.currentLogicalRunway.get().breakdown.getSlopeCalculation()
                                                    + "m",
                                    false);
                }
            }
        }
    }

    /*
     * Scales a value in meters to the canvas size
     * 
     * @param val The value to scale
     * 
     * @param canvas The canvas height or width
     *
     * @return The scaled value
     */
    protected double scale(double val, double canvas) {
        return (val / this.totalLength) * canvas;

    }

    protected void drawDistanceArrow(GraphicsContext gc, double startX, double endX,
                    double distanceFromRunway, Boolean above, Paint color, String label) {
        drawDistanceArrow(gc, startX, endX, distanceFromRunway, above, color, label, true);
    }

    protected void drawDistanceArrow(GraphicsContext gc, double startX, double endX,
                    double distanceFromRunway, Boolean above, Paint color, String label,
                    Boolean linesToMeet) {
        // calculate position
        double runwayCentreLine = runwayCanvas.getHeight() / 2;
        double runwayTop = runwayCentreLine - (runwayWidth / 2);
        double runwayBottom = runwayCentreLine + (runwayWidth / 2);
        double y, runwayEdge;
        if (above) { // Above
            y = runwayTop - distanceFromRunway;
            runwayEdge = runwayTop;
        } else {
            y = runwayBottom + distanceFromRunway;
            runwayEdge = runwayBottom;
        }
        // Draw arrow
        gc.setStroke(color);
        gc.setFill(color);
        gc.setLineDashes();
        drawArrow(gc, startX, y, endX, y, color);
        if (linesToMeet) {
            // Draw lines to meet arrow
            gc.setStroke(color);
            gc.strokeLine(endX, y, endX, runwayEdge);
            gc.strokeLine(startX, y, startX, runwayEdge);
        } else {
            drawArrow(gc, endX, y, startX, y, color);
        }
        // Draw label
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(label, (startX + endX) / 2, y - 5);
    }

    /*
     * Draws and arrow on the canvas
     * 
     * @param gc the canvas graphics context
     * 
     * @param x1 the starting x
     * 
     * @param y1 the starting y
     * 
     * @param x2 the ending x
     * 
     * @param y2 the ending y
     * 
     * @param color the color to draw the arrow
     */
    protected void drawArrow(GraphicsContext gc, double x1, double y1, double x2, double y2,
                    Paint color) {
        double arrowSize = 4;
        gc.setStroke(color);
        gc.setFill(color);
        double dx = x2 - x1, dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        double len = Math.sqrt(dx * dx + dy * dy);

        Transform transform = Transform.translate(x1, y1);
        transform = transform.createConcatenation(Transform.rotate(Math.toDegrees(angle), 0, 0));
        gc.setTransform(new Affine(transform));

        gc.strokeLine(0, 0, len, 0);
        gc.fillPolygon(new double[] {len, len - arrowSize, len - arrowSize, len},
                        new double[] {0, -arrowSize, arrowSize, 0}, 4);
        transform = Transform.translate(0, 0);
        gc.setTransform(new Affine(transform));
    }

}

