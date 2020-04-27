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

    @Override
    protected void drawRunwayStrip(GraphicsContext gc) {
        super.drawRunwayStrip(gc);
        double canvasMiddleY = runwayCanvas.getHeight() / 2; // Draw the centre line
        gc.setStroke(Color.WHITE);
        gc.setLineWidth(2);
        gc.setLineDashes(50);
        gc.strokeLine(scale(leftOffset, runwayCanvas.getWidth()), canvasMiddleY,
                        scale(runwayLength, runwayCanvas.getWidth()), canvasMiddleY);
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
