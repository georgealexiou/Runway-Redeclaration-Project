package org.comp2211.group6.view;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SideOnView extends RunwayView {

    public SideOnView() {
        super();
        loadFxml(getClass().getResource("/runway_view.fxml"), this);
        this.viewTitle.setText("Side on View");
        this.runwayWidth = 10;
    }

    @Override
    protected void redraw() {
        super.redraw();
        GraphicsContext gc = runwayCanvas.getGraphicsContext2D();
        gc.setLineWidth(2);
        if (this.runway != null) {
            drawRunwayStrip(gc);
            drawDisplacedThreshold(gc);
            drawRunwayParams(gc, true);
            if (this.currentObstacle != null) {
                drawRunwayParams(gc, false);
            }
        }
    }

    private void drawRunwayStrip(GraphicsContext gc) {
        double runwayCentreLine = runwayCanvas.getHeight() / 2;

        // Draw the tarmac
        gc.setFill(Color.GREY);
        gc.fillRect(scale(leftOffset, runwayCanvas.getWidth()),
                        runwayCentreLine - (runwayWidth / 2),
                        scale(runwayLength, runwayCanvas.getWidth()), runwayWidth);
    }

}
