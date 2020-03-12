package org.comp2211.group6.view;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

import org.comp2211.group6.Model.LogicalRunway;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;

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
        drawDistanceArrow(gc, startX, endX, (runwayWidth / 4), canvasMiddleY - (runwayWidth / 2),
                        Color.BLACK, "DT: " + currentLogicalRunway.getDisplacedThreshold() + "m");

    }

    // TODO: Draw Original Values
    // TODO: Draw Reclaculated Values
}
