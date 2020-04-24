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

}
