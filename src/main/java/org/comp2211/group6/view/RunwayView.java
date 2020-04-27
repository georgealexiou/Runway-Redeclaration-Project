package org.comp2211.group6.view;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Model.Runway;
import org.comp2211.group6.Model.RunwayParameters;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;
import javafx.util.StringConverter;

public abstract class RunwayView extends GridPane implements Initializable {

    /*
     * Data
     */
    protected Runway runway;
    protected Obstacle currentObstacle;
    protected LogicalRunway currentLogicalRunway;

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

    protected final Color paramColor = Color.BLACK;
    protected final Color resaColor = Color.CADETBLUE;
    protected final Color seColor = Color.DODGERBLUE;
    protected final Color blastColor = Color.ORANGE;
    protected final Color slopeColor = Color.CRIMSON;

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
    protected Label viewTitle;

    /*
     * Construct a new RunwayView
     */
    public RunwayView() {
        logicalRunwayPicker = new ComboBox<LogicalRunway>();
        setupRunwayPicker(FXCollections.observableArrayList());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        runwayCanvas.widthProperty().bind(this.widthProperty().subtract(100));
        runwayCanvas.heightProperty().bind(this.heightProperty().subtract(150));
        runwayCanvas.widthProperty().addListener(observable -> redraw());
        runwayCanvas.heightProperty().addListener(observable -> redraw());
    }

    /*
     * Sets up the runway logical runway combo box with new values
     * 
     * @param data The list of logical runways to have in the combo picker
     */
    private void setupRunwayPicker(ObservableList<LogicalRunway> data) {
        // Update the values
        currentLogicalRunway = null;
        logicalRunwayPicker.setItems(data);
        logicalRunwayPicker.getSelectionModel().selectFirst();
        // Re-create the event listener and string coverter
        logicalRunwayPicker.valueProperty().addListener((obs, oldVal, newVal) -> {
            this.currentLogicalRunway = newVal;
            if (newVal != null)
                this.redrawRunway();
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
        currentLogicalRunway = logicalRunwayPicker.getValue();
    }

    /*
     * Returns the runway currently in use
     */
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
        if (runway != null) {
            // Update the combo box
            setupRunwayPicker(FXCollections.observableArrayList(this.runway.getLogicalRunways()));
        }
        // Re draw the runway
        redrawRunway();
    }

    /*
     * Returns the logical runway currently focused on
     */
    public LogicalRunway getCurrentLogicalRunway() {
        return this.currentLogicalRunway;
    }

    public void setCurrentLogicalRunway(LogicalRunway runway) {
        this.logicalRunwayPicker.getSelectionModel().select(runway);
    }

    /*
     * Returns the currently displayed obstacle
     */
    public Obstacle getCurrentObstacle() {
        return this.currentObstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.currentObstacle = obstacle;
        if (this.runway != null) {
            this.runway.setObstacle(obstacle);
            redrawRunway();
        }
    }

    /*
     * This method is called when the runway or logical runway is changed, or if the obstacle is
     * updated Override this to add more functionality
     */
    private void redrawRunway() {
        // Handle takeoff landing direction arrow
        if (currentLogicalRunway != null) {
            if (currentLogicalRunway.getHeading() <= 18) {
                this.runwayDirectionArrow.setRotate(0);
            } else {
                this.runwayDirectionArrow.setRotate(180);
            }
            recalculateDataValues();
            redraw();
        }

    }

    protected void redraw() {
        GraphicsContext gc = runwayCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, runwayCanvas.getWidth(), runwayCanvas.getHeight());
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
        if (this.runway == null)
            return;
        // Clear all values
        this.rightClearway = 0;
        this.leftClearway = 0;
        this.rightStopway = 0;
        this.leftStopway = 0;
        this.runwayLength = 0;
        this.totalLength = 0;
        this.obstacleLeft = 0;
        double addToObstaceLeft = 0;
        Iterator<LogicalRunway> lrs = this.runway.getLogicalRunways().iterator();
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
        gc.setFill(Color.GREY);
        gc.fillRect(scale(leftOffset, runwayCanvas.getWidth()), canvasMiddleY - (runwayWidth / 2),
                        scale(runwayLength, runwayCanvas.getWidth()), runwayWidth);

        // Draw the clearway and stopway
        gc.setFill(Color.RED);
        gc.fillRect(scale(leftOffset - leftClearway, runwayCanvas.getWidth()),
                        canvasMiddleY - (runwayWidth / 2),
                        scale(leftClearway, runwayCanvas.getWidth()), runwayWidth);
        gc.fillRect(scale(leftOffset + runwayLength, runwayCanvas.getWidth()),
                        canvasMiddleY - (runwayWidth / 2),
                        scale(rightClearway, runwayCanvas.getWidth()), runwayWidth);
        gc.setFill(Color.BLUE);
        gc.fillRect(scale(leftOffset - leftStopway, runwayCanvas.getWidth()),
                        canvasMiddleY - (runwayWidth / 2),
                        scale(leftStopway, runwayCanvas.getWidth()), runwayWidth);
        gc.fillRect(scale(leftOffset + runwayLength, runwayCanvas.getWidth()),
                        canvasMiddleY - (runwayWidth / 2),
                        scale(rightStopway, runwayCanvas.getWidth()), runwayWidth);
    }

    protected void drawDisplacedThreshold(GraphicsContext gc) {
        if (currentLogicalRunway == null)
            return;
        double threshold = currentLogicalRunway.getDisplacedThreshold();
        double canvasMiddleY = runwayCanvas.getHeight() / 2;
        if (threshold == 0)
            return;
        gc.setStroke(Color.LIGHTGREEN);
        double endX, startX;
        if (currentLogicalRunway.getHeading() <= 18) {
            endX = scale(leftOffset + threshold, runwayCanvas.getWidth());
            startX = scale(leftOffset, runwayCanvas.getWidth());
        } else {
            endX = scale(leftOffset + runwayLength - threshold, runwayCanvas.getWidth());
            startX = scale(leftOffset + runwayLength, runwayCanvas.getWidth());
        }
        gc.setStroke(Color.LIGHTGREEN);
        gc.setLineDashes();
        gc.strokeLine(endX, canvasMiddleY + (runwayWidth / 2), endX,
                        canvasMiddleY - (runwayWidth / 2));
        drawDistanceArrow(gc, startX, endX, (runwayArrowPadding), true, paramColor,
                        "DT: " + currentLogicalRunway.getDisplacedThreshold() + "m");

    }

    protected void drawThresholdMarkers(GraphicsContext gc) {
        Iterator<LogicalRunway> lrs = this.runway.getLogicalRunways().iterator();
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

            if (lr == this.currentLogicalRunway) {
                gc.setFill(Color.ORANGE);
                gc.fillRect(x - 15, y - 15, 30, 35);
                gc.setFill(Color.BLACK);
            } else {
                gc.setFill(Color.GREY);
                gc.fillRect(x - 15, y - 15, 30, 35);
                gc.setFill(Color.WHITE);
            }
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText(heading + "\n" + pos, x, y);
        }
    }

    protected void drawRunwayParams(GraphicsContext gc, boolean original) {
        RunwayParameters params = original ? currentLogicalRunway.getParameters()
                        : currentLogicalRunway.getRecalculatedParameters();
        double startX;
        int mult;
        if (currentLogicalRunway.getHeading() <= 18) {
            startX = scale(leftOffset, runwayCanvas.getWidth());
            mult = 1;
        } else {
            startX = scale(leftOffset + runwayLength, runwayCanvas.getWidth());
            mult = -1;
        }
        boolean towardstowards = this.currentLogicalRunway.breakdown.getDirection();
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
            drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original, paramColor,
                            toraLabel);
            endX = startX + (mult * scale(toda, runwayCanvas.getWidth()));
            drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original, paramColor,
                            todaLabel);
            endX = startX + (mult * scale(asda, runwayCanvas.getWidth()));
            drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original, paramColor,
                            asdaLabel);
            startX += (mult * scale(currentLogicalRunway.getDisplacedThreshold(),
                            runwayCanvas.getWidth()));
            endX = startX + (mult * scale(lda, runwayCanvas.getWidth()));
            drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original, paramColor,
                            ldaLabel);
        } else {
            toraLabel = "(R)" + toraLabel;
            todaLabel = "(R)" + todaLabel;
            asdaLabel = "(R)" + asdaLabel;
            ldaLabel = "(R)" + ldaLabel;
            if (towards) {
                double endX = startX + (mult * scale(tora, runwayCanvas.getWidth()));
                drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original,
                                paramColor, toraLabel);
                endX = startX + (mult * scale(toda, runwayCanvas.getWidth()));
                drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original,
                                paramColor, todaLabel);
                endX = startX + (mult * scale(asda, runwayCanvas.getWidth()));
                drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original,
                                paramColor, asdaLabel);
                startX += scale(currentLogicalRunway.getDisplacedThreshold() * mult,
                                runwayCanvas.getWidth());
                endX = startX + (mult * scale(lda, runwayCanvas.getWidth()));
                drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos, original,
                                paramColor, ldaLabel);
                // Draw the strip end and resa for the landing component
                double landingStartX = endX;
                endX = landingStartX
                                + (mult * scale(this.currentLogicalRunway.breakdown.getStripEnd(),
                                                runwayCanvas.getWidth()));
                drawDistanceArrow(gc, landingStartX, endX, runwayArrowPadding * pos, original,
                                seColor, this.currentLogicalRunway.breakdown.getStripEnd() + "m",
                                false);
                landingStartX = endX;
                endX = landingStartX + (mult * scale(this.currentLogicalRunway.breakdown.getResa(),
                                runwayCanvas.getWidth()));
                drawDistanceArrow(gc, landingStartX, endX, runwayArrowPadding * pos, original,
                                resaColor, this.currentLogicalRunway.breakdown.getResa() + "m",
                                false);

                // When taking off we have a strip end and then the RESA or slope calculation
                double takeoffStartX = startX + (mult * scale(asda, runwayCanvas.getWidth()));
                endX = takeoffStartX
                                + (mult * scale(this.currentLogicalRunway.breakdown.getStripEnd(),
                                                runwayCanvas.getWidth()));
                drawDistanceArrow(gc, takeoffStartX, endX, runwayArrowPadding * (pos - 2), original,
                                seColor, this.currentLogicalRunway.breakdown.getStripEnd() + "m",
                                false);

                takeoffStartX = endX;
                if (this.currentLogicalRunway.breakdown
                                .getResa() > this.currentLogicalRunway.breakdown
                                                .getSlopeCalculation()) {
                    endX = takeoffStartX
                                    + (mult * scale(this.currentLogicalRunway.breakdown.getResa(),
                                                    runwayCanvas.getWidth()));
                    drawDistanceArrow(gc, takeoffStartX, endX, runwayArrowPadding * (pos - 2),
                                    original, resaColor,
                                    this.currentLogicalRunway.breakdown.getResa() + "m", false);
                } else {
                    endX = takeoffStartX + (mult * scale(
                                    this.currentLogicalRunway.breakdown.getSlopeCalculation(),
                                    runwayCanvas.getWidth()));
                    drawDistanceArrow(gc, takeoffStartX, endX, runwayArrowPadding * (pos - 2),
                                    original, slopeColor,
                                    this.currentLogicalRunway.breakdown.getSlopeCalculation() + "m",
                                    false);
                }
            } else {
                double originalStartx = startX;
                double endX = originalStartx
                                + scale((mult * runwayLength), runwayCanvas.getWidth());
                startX = endX - scale(mult * tora, runwayCanvas.getWidth());
                drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original,
                                paramColor, toraLabel);
                endX = originalStartx + scale(
                                (mult * runwayLength + calculateClearway(currentLogicalRunway)),
                                runwayCanvas.getWidth());
                startX = endX - scale(mult * toda, runwayCanvas.getWidth());
                drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original,
                                paramColor, todaLabel);
                endX = originalStartx + scale(
                                (mult * runwayLength + calculateStopway(currentLogicalRunway)),
                                runwayCanvas.getWidth());
                startX = endX - scale(mult * asda, runwayCanvas.getWidth());
                drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos++, original,
                                paramColor, asdaLabel);
                startX = endX - scale(mult * lda, runwayCanvas.getWidth());
                drawDistanceArrow(gc, startX, endX, (runwayArrowPadding) * pos, original,
                                paramColor, ldaLabel);

                double takeOffEndX = originalStartx + scale(
                                (mult * runwayLength + calculateStopway(currentLogicalRunway)),
                                runwayCanvas.getWidth())
                                - scale(mult * asda, runwayCanvas.getWidth());
                startX = takeOffEndX - (mult
                                * scale(this.currentLogicalRunway.breakdown.getBlastProtection(),
                                                runwayCanvas.getWidth()));
                drawDistanceArrow(gc, startX, takeOffEndX, runwayArrowPadding * (pos - 2), original,
                                blastColor,
                                this.currentLogicalRunway.breakdown.getBlastProtection() + "m",
                                false);

                takeOffEndX = originalStartx + scale(
                                (mult * runwayLength + calculateStopway(currentLogicalRunway)),
                                runwayCanvas.getWidth())
                                - scale(mult * lda, runwayCanvas.getWidth());

                startX = takeOffEndX
                                - (mult * scale(this.currentLogicalRunway.breakdown.getStripEnd(),
                                                runwayCanvas.getWidth()));
                drawDistanceArrow(gc, startX, takeOffEndX, runwayArrowPadding * (pos), original,
                                seColor, this.currentLogicalRunway.breakdown.getStripEnd() + "m",
                                false);
                takeOffEndX = startX;
                if (this.currentLogicalRunway.breakdown
                                .getResa() > this.currentLogicalRunway.breakdown
                                                .getSlopeCalculation()) {
                    startX = takeOffEndX
                                    - (mult * scale(this.currentLogicalRunway.breakdown.getResa(),
                                                    runwayCanvas.getWidth()));
                    drawDistanceArrow(gc, startX, takeOffEndX, runwayArrowPadding * (pos), original,
                                    resaColor, this.currentLogicalRunway.breakdown.getResa() + "m",
                                    false);
                } else {
                    startX = takeOffEndX - (mult * scale(
                                    this.currentLogicalRunway.breakdown.getSlopeCalculation(),
                                    runwayCanvas.getWidth()));
                    drawDistanceArrow(gc, startX, takeOffEndX, runwayArrowPadding * (pos), original,
                                    slopeColor,
                                    this.currentLogicalRunway.breakdown.getSlopeCalculation() + "m",
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
