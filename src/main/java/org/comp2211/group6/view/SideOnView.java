package org.comp2211.group6.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;

public class SideOnView extends RunwayView {

    public SideOnView() {
        super();
        loadFxml(getClass().getResource("/runway_view.fxml"), this);
        this.viewTitle.setText("Side on View");
        this.runwayWidth = 10;
        this.runwayArrowPadding = 40;
    }

    @Override
    protected void redraw() {
        super.redraw();
        GraphicsContext gc = runwayCanvas.getGraphicsContext2D();
        gc.setLineWidth(2);
        if (this.runway != null) {
            drawRunwayStrip(gc);
            drawDisplacedThreshold(gc);
            drawThresholdMarkers(gc);
            drawRunwayParams(gc, true);
            if (this.currentObstacle != null) {
                drawObstacle(gc);
                if (this.currentLogicalRunway.getRecalculatedParameters().getTORA() > 0) {
                    drawRunwayParams(gc, false);
                    drawSlope(gc);
                }
            }
        }
    }

    private void drawObstacle(GraphicsContext gc) {
        gc.setFill(Color.RED);
        double startX = scale(obstacleLeft + currentObstacle.distanceFromLeftThreshold,
                        runwayCanvas.getWidth());
        double y = runwayCanvas.getHeight() / 2 - runwayWidth / 2;
        double xs[] = {startX - 5, startX, startX + 5};
        double ys[] = {y, y - 2 * currentObstacle.getHeight(), y};
        gc.fillPolygon(xs, ys, 3);
        gc.setFill(Color.RED);
        gc.setStroke(Color.RED);
        gc.strokeLine(startX, runwayCanvas.getHeight() / 2 + (runwayArrowPadding) * 8, startX,
                        y - 2 * currentObstacle.getHeight() + 5);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(currentObstacle.getName(), startX,
                        runwayCanvas.getHeight() / 2 + (runwayArrowPadding) * 8 + 10);
    }

    /**
     * @param over Is this a landing over or towards situation
     */
    private void drawSlope(GraphicsContext gc) {
        boolean over = this.currentLogicalRunway.breakdown.getDirection();
        double startY, endY, startX, endX;
        if (over) {
            startY = runwayCanvas.getHeight() / 2 - runwayWidth / 2;
            endY = startY - 2 * currentObstacle.getHeight();
            endX = scale(obstacleLeft + currentObstacle.distanceFromLeftThreshold,
                            runwayCanvas.getWidth());
            startX = endX + scale(50 * currentObstacle.getHeight(), runwayCanvas.getWidth());
        } else {
            endY = runwayCanvas.getHeight() / 2 - runwayWidth / 2;
            startY = endY - 2 * currentObstacle.getHeight();
            startX = scale(obstacleLeft + currentObstacle.distanceFromLeftThreshold,
                            runwayCanvas.getWidth());
            endX = startX + scale(50 * currentObstacle.getHeight(), runwayCanvas.getWidth());
        }
        drawArrow(gc, startX, startY, endX, endY, slopeColor);
    }

}
