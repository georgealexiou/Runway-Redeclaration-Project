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
                System.out.println("Obstacle: " + currentObstacle.getName());
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
        double startX;
        int mult;
        if (currentLogicalRunway.getHeading() <= 18) {
            startX = scale(leftOffset, runwayCanvas.getWidth());
            mult = 1;
        } else {
            startX = scale(leftOffset + runwayLength, runwayCanvas.getWidth());
            mult = -1;
        }
        boolean towardstowards = true;
        // TODO: Replace this with the value from a breakdown calss
        if (!original && currentObstacle != null) {
            if (currentObstacle.distanceFromLeftThreshold < currentObstacle.distanceFromRightThreshold) { //
                if (currentLogicalRunway.getHeading() <= 18) {
                    towardstowards = false;
                }
            } else {
                if (currentLogicalRunway.getHeading() > 18) {
                    towardstowards = false;
                }
            }
        }
        drawRunwayParameters(gc, params, towardstowards, original, mult, 2, startX);
    }

    /*
     * Draws the TORA, TODA and ASDA
     */
    private void drawRunwayParameters(GraphicsContext gc, RunwayParameters params, boolean towards,
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
            drawDistanceArrow(gc, startX, endX, (runwayWidth / 4) * pos++, original, Color.BLACK,
                            toraLabel);
            endX = startX + (mult * scale(toda, runwayCanvas.getWidth()));
            drawDistanceArrow(gc, startX, endX, (runwayWidth / 4) * pos++, original, Color.BLACK,
                            todaLabel);
            endX = startX + (mult * scale(asda, runwayCanvas.getWidth()));
            drawDistanceArrow(gc, startX, endX, (runwayWidth / 4) * pos++, original, Color.BLACK,
                            asdaLabel);
            startX += (mult * scale(currentLogicalRunway.getDisplacedThreshold(),
                            runwayCanvas.getWidth()));
            endX = startX + (mult * scale(lda, runwayCanvas.getWidth()));
            drawDistanceArrow(gc, startX, endX, (runwayWidth / 4) * pos++, original, Color.BLACK,
                            ldaLabel);
        } else {
            toraLabel = "(R)" + toraLabel;
            todaLabel = "(R)" + todaLabel;
            asdaLabel = "(R)" + asdaLabel;
            ldaLabel = "(R)" + ldaLabel;
            if (towards) {
                double endX = startX + (mult * scale(tora, runwayCanvas.getWidth()));
                drawDistanceArrow(gc, startX, endX, (runwayWidth / 4) * pos++, original,
                                Color.BLACK, toraLabel);
                endX = startX + (mult * scale(toda, runwayCanvas.getWidth()));
                drawDistanceArrow(gc, startX, endX, (runwayWidth / 4) * pos++, original,
                                Color.BLACK, todaLabel);
                endX = startX + (mult * scale(asda, runwayCanvas.getWidth()));
                drawDistanceArrow(gc, startX, endX, (runwayWidth / 4) * pos++, original,
                                Color.BLACK, asdaLabel);
                // TODO: Add in slope calculation and strip end
                // TODO: Add in RESA
                startX += scale(currentLogicalRunway.getDisplacedThreshold() * mult,
                                runwayCanvas.getWidth());
                endX = startX + (mult * scale(lda, runwayCanvas.getWidth()));
                drawDistanceArrow(gc, startX, endX, (runwayWidth / 4) * pos++, original,
                                Color.BLACK, ldaLabel);
            } else {
                double originalStartx = startX;
                double endX = originalStartx
                                + scale((mult * runwayLength), runwayCanvas.getWidth());
                startX = endX - scale(mult * tora, runwayCanvas.getWidth());
                drawDistanceArrow(gc, startX, endX, (runwayWidth / 4) * pos++, original,
                                Color.BLACK, toraLabel);
                endX = originalStartx + scale(
                                (mult * runwayLength + calculateClearway(currentLogicalRunway)),
                                runwayCanvas.getWidth());
                startX = endX - scale(mult * toda, runwayCanvas.getWidth());
                drawDistanceArrow(gc, startX, endX, (runwayWidth / 4) * pos++, original,
                                Color.BLACK, todaLabel);
                endX = originalStartx + scale(
                                (mult * runwayLength + calculateStopway(currentLogicalRunway)),
                                runwayCanvas.getWidth());
                startX = endX - scale(mult * asda, runwayCanvas.getWidth());
                drawDistanceArrow(gc, startX, endX, (runwayWidth / 4) * pos++, original,
                                Color.BLACK, asdaLabel);
                // TODO: Add in Blast Protection
                // TODO: Add in strip end, slope calculation
                startX = endX - scale(mult * lda, runwayCanvas.getWidth());
                drawDistanceArrow(gc, startX, endX, (runwayWidth / 4) * pos++, original,
                                Color.BLACK, ldaLabel);
            }
        }
    }

    private void drawObstacle(GraphicsContext gc) {
        gc.setFill(Color.RED);
        double startX = scale(obstacleLeft + currentObstacle.distanceFromLeftThreshold,
                        runwayCanvas.getWidth());
        double y = runwayCanvas.getHeight() / 2 - currentObstacle.distanceToCentreLine
                        - currentObstacle.getWidth() / 2;
        gc.fillRect(startX - 5, y, 10, currentObstacle.getWidth());
        drawArrow(gc, startX, runwayCanvas.getHeight() / 2 + (runwayWidth / 4) * 8, startX, y + 5,
                        Color.RED);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(currentObstacle.getName(), startX,
                        runwayCanvas.getHeight() / 2 + (runwayWidth / 4) * 8 + 10);
    }

}
