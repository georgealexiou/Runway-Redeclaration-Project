package org.comp2211.group6.view;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.comp2211.group6.Controller.Calculator;
import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.RunwayParameters;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class TopDownView extends RunwayView {

    /*
     * Data
     */
    public TopDownView() {
        super();
        loadFxml(getClass().getResource("/runway_view.fxml"), this);
        this.viewTitle.setText("Top Down View");
    }

    protected void redraw() {
        if (this.runway != null) {
            GraphicsContext gc = runwayCanvas.getGraphicsContext2D();
            gc.clearRect(0, 0, runwayCanvas.getWidth(), runwayCanvas.getHeight());
            drawRunwayStrip(gc);
            drawThresholdMarkers(gc);
            drawDisplacedThreshold(gc);
            newDrawRunwayParams(gc, true);
            if (currentObstacle != null) {
                drawObstacle(gc);
            }
            if (currentLogicalRunway.getRecalculatedParameters().getTORA() != 0) {
                System.out.println("REACHED C");
                newDrawRunwayParams(gc, false);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        runwayCanvas.widthProperty().bind(this.widthProperty().subtract(100));
        runwayCanvas.heightProperty().bind(this.heightProperty().subtract(150));
        runwayCanvas.widthProperty().addListener(observable -> redraw());
        runwayCanvas.heightProperty().addListener(observable -> redraw());
    }

    private void drawRunwayStrip(GraphicsContext gc) {
        double canvasMiddleY = runwayCanvas.getHeight() / 2;

        // Draw the tarmac
        gc.setFill(Color.GREY);
        gc.fillRect(scale(leftOffset, runwayCanvas.getWidth()), canvasMiddleY - (runwayWidth / 2),
                        scale(runwayLength, runwayCanvas.getWidth()), runwayWidth);

        // Draw the centre line
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.setLineDashes(50);
        gc.strokeLine(scale(leftOffset, runwayCanvas.getWidth()), canvasMiddleY,
                        scale(runwayLength, runwayCanvas.getWidth()), canvasMiddleY);

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

    private void drawThresholdMarkers(GraphicsContext gc) {
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
                gc.setFill(Color.WHITE);
            }
            gc.setTextAlign(TextAlignment.CENTER);
            gc.fillText(heading + "\n" + pos, x, y);
        }
    }

    // TODO: Draw Displaced Threshold
    private void drawDisplacedThreshold(GraphicsContext gc) {
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
        drawDistanceArrow(gc, startX, endX, (runwayWidth / 4), true, Color.BLACK,
                        "DT: " + currentLogicalRunway.getDisplacedThreshold() + "m");

    }

    // TODO: Draw Reclaculated Values
    // private void drawRunwayValues(GraphicsContext gc, Boolean original) {
    // RunwayParameters params;
    // double startX;
    // double displacedThresholdScaled = scale(currentLogicalRunway.getDisplacedThreshold(),
    // runwayCanvas.getWidth());
    // double mult;

    // if (original) {
    // params = currentLogicalRunway.getParameters();
    // } else {
    // params = currentLogicalRunway.getRecalculatedParameters();
    // }

    // int i = 2;

    // // Draw LDA - Smallest
    // double endX = startX + (mult * (scale(params.getLDA(), runwayCanvas.getWidth())
    // + displacedThresholdScaled));
    // drawDistanceArrow(gc, startX + (mult * displacedThresholdScaled), endX,
    // (runwayWidth / 4) * i++, original, Color.BLACK,
    // "LDA: " + params.getLDA() + "m");

    // // Draw TORA
    // endX = startX + (mult * scale(params.getTORA(), runwayCanvas.getWidth()));
    // drawDistanceArrow(gc, startX, endX, (runwayWidth / 4) * i++, original, Color.BLACK,
    // "TORA: " + params.getTORA() + "m");

    // if (params.getASDA() > params.getTODA()) {
    // // Draw TODA
    // endX = startX + (mult * scale(params.getTODA(), runwayCanvas.getWidth()));
    // drawDistanceArrow(gc, startX, endX, (runwayWidth / 4) * i++, original, Color.BLACK,
    // "TODA: " + params.getTODA() + "m");
    // }

    // // Draw ASDA
    // endX = startX + (mult * scale(params.getASDA(), runwayCanvas.getWidth()));
    // drawDistanceArrow(gc, startX, endX, (runwayWidth / 4) * i++, original, Color.BLACK,
    // "ASDA: " + params.getASDA() + "m");

    // if (params.getTODA() >= params.getASDA()) {
    // // Draw TODA
    // endX = startX + (mult * scale(params.getTODA(), runwayCanvas.getWidth()));
    // drawDistanceArrow(gc, startX, endX, (runwayWidth / 4) * i++, original, Color.BLACK,
    // "TODA: " + params.getTODA() + "m");
    // }

    // }

    private void newDrawRunwayParams(GraphicsContext gc, boolean original) {
        RunwayParameters params = original ? currentLogicalRunway.getParameters()
                        : currentLogicalRunway.getRecalculatedParameters();
        double toraOffset, todaOffset, ldaOffset, asdaOffset;
        double startX;
        int mult;
        if (currentLogicalRunway.getHeading() <= 18) {
            startX = scale(leftOffset, runwayCanvas.getWidth());
            mult = 1;
        } else {
            startX = scale(leftOffset + runwayLength, runwayCanvas.getWidth());
            mult = -1;
        }
        if (!original && currentObstacle != null) {
            if (currentObstacle.distanceFromLeftThreshold < currentObstacle.distanceFromRightThreshold) { //
                if (currentLogicalRunway.getHeading() <= 18) { // Take off Away, Landing Over
                } else { // Take off towards, landing towards
                    System.out.println("Reached A");
                    drawTORA(gc, params.getTORA(), true, original, mult, 2, startX);
                }
            } else {
                if (currentLogicalRunway.getHeading() <= 18) { // Take off Towards, Landing Towards

                    System.out.println("Reached B");
                    drawTORA(gc, params.getTORA(), true, original, mult, 2, startX);
                } else { // Take off Away, Landing Over

                }
            }
        } else {
            drawTORA(gc, params.getTORA(), false, original, mult, 2, startX);
        }
    }

    /*
     * Draws the TORA
     * 
     * @param gc The graphics context
     * 
     * @param value The TORA value
     * 
     * @param towards Is landing towards or over
     * 
     * @param original Is this an original or recalculated
     * 
     * @param mult Multiplier for swapping direction
     * 
     * @param pos Position of line in (runwayWidth / 4)'s from edge of runway
     * 
     * @param startX The start of the threshold
     */
    private void drawTORA(GraphicsContext gc, double value, boolean towards, boolean original,
                    int mult, int pos, double startX) {
        String label = "TORA: " + value + "m";
        if (original) {
            double endX = startX + (mult * scale(value, runwayCanvas.getWidth()));
            drawDistanceArrow(gc, startX, endX, (runwayWidth / 4) * pos, original, Color.BLACK,
                            label);
        } else {
            if (towards) {
                double endX = startX + (mult * scale(value, runwayCanvas.getWidth()));
                drawDistanceArrow(gc, startX, endX, (runwayWidth / 4) * pos, original, Color.BLACK,
                                label);
                // TODO: Use real values
                drawDistanceArrow(gc, endX,
                                (endX + (mult * scale((currentObstacle.getHeight() * 50),
                                                runwayCanvas.getWidth()))),
                                (runwayWidth / 4) * pos, original, Color.BLACK, "Slope");
            }
        }
    }

    private void drawObstacle(GraphicsContext gc) {
        gc.setFill(Color.RED);
        gc.fillRect(scale(obstacleLeft + currentObstacle.distanceFromLeftThreshold,
                        runwayCanvas.getWidth()),
                        runwayCanvas.getHeight() / 2 - currentObstacle.distanceToCentreLine
                                        - currentObstacle.getWidth() / 2,
                        scale(runwayLength - (currentObstacle.distanceFromLeftThreshold
                                        + currentObstacle.distanceFromRightThreshold),
                                        runwayCanvas.getWidth()),
                        currentObstacle.getWidth());
    }

}
