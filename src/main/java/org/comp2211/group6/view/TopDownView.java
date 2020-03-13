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

    @Override
    protected void redraw() {
        super.redraw();
        if (this.runway != null) {
            GraphicsContext gc = runwayCanvas.getGraphicsContext2D();
            drawClearedAndGraded(gc);
            drawRunwayStrip(gc);
            drawThresholdMarkers(gc);
            drawDisplacedThreshold(gc);
            drawRunwayParams(gc, true);
            if (currentObstacle != null) {
                drawObstacle(gc);
            }
            if (currentLogicalRunway.getRecalculatedParameters().getTORA() != 0) {
                drawRunwayParams(gc, false);
            }
        }
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

    // private void drawDisplacedThreshold(GraphicsContext gc) {
    // double threshold = currentLogicalRunway.getDisplacedThreshold();
    // double canvasMiddleY = runwayCanvas.getHeight() / 2;
    // if (threshold == 0)
    // return;
    // gc.setStroke(Color.LIGHTGREEN);
    // double endX, startX;
    // if (currentLogicalRunway.getHeading() <= 18) {
    // endX = scale(leftOffset + threshold, runwayCanvas.getWidth());
    // startX = scale(leftOffset, runwayCanvas.getWidth());
    // } else {
    // endX = scale(leftOffset + runwayLength - threshold, runwayCanvas.getWidth());
    // startX = scale(leftOffset + runwayLength, runwayCanvas.getWidth());
    // }
    // gc.setStroke(Color.LIGHTGREEN);
    // gc.setLineDashes();
    // gc.strokeLine(endX, canvasMiddleY + (runwayWidth / 2), endX,
    // canvasMiddleY - (runwayWidth / 2));
    // drawDistanceArrow(gc, startX, endX, (runwayWidth / 4), true, Color.BLACK,
    // "DT: " + currentLogicalRunway.getDisplacedThreshold() + "m");

    // }

    private void drawObstacle(GraphicsContext gc) {
        gc.setFill(Color.RED);
        double startX = scale(obstacleLeft + currentObstacle.distanceFromLeftThreshold,
                        runwayCanvas.getWidth());
        double y = runwayCanvas.getHeight() / 2 - currentObstacle.distanceToCentreLine
                        - currentObstacle.getWidth() / 2;
        gc.fillRect(startX - 5, y, 10, currentObstacle.getWidth());
        drawArrow(gc, startX, runwayCanvas.getHeight() / 2 + (runwayArrowPadding) * 8, startX,
                        y + 5, Color.RED);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(currentObstacle.getName(), startX,
                        runwayCanvas.getHeight() / 2 + (runwayArrowPadding) * 8 + 10);
    }

    private void drawClearedAndGraded(GraphicsContext gc) {
        gc.setFill(Color.LIGHTGRAY);
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

}
