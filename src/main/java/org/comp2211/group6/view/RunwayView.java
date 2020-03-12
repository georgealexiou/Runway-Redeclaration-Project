package org.comp2211.group6.view;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;

import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Model.Runway;

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
import javafx.scene.layout.HBox;
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

    protected double totalLength;

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
        // Update the combo box
        setupRunwayPicker(FXCollections.observableArrayList(this.runway.getLogicalRunways()));
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
        redrawRunway();
    }

    /*
     * This method is called when the runway or logical runway is changed, or if the obstacle is
     * updated Override this to add more functionality
     */
    private void redrawRunway() {
        // Handle takeoff landing direction arrow
        if (currentLogicalRunway.getHeading() <= 18) {
            this.runwayDirectionArrow.setRotate(0);
        } else {
            this.runwayDirectionArrow.setRotate(180);
        }
        if (currentLogicalRunway != null) {
            recalculateDataValues();
            redraw();
        }

    }

    protected abstract void redraw();

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
     * Reclaulcates the data values for the current runway
     */
    protected void recalculateDataValues() {
        // Clear all values
        this.rightClearway = 0;
        this.leftClearway = 0;
        this.rightStopway = 0;
        this.leftStopway = 0;
        this.runwayLength = 0;
        this.totalLength = 0;
        Iterator<LogicalRunway> lrs = this.runway.getLogicalRunways().iterator();
        while (lrs.hasNext()) {
            LogicalRunway lr = lrs.next();
            double clearway = calculateClearway(lr);
            double stopway = calculateClearway(lr);
            if (lr.getHeading() <= 18) { // On left so set right stopway and clearway
                this.rightClearway = clearway > this.rightClearway ? clearway : this.rightClearway;
                this.rightStopway = stopway > this.rightStopway ? stopway : this.rightStopway;
            } else {
                this.leftClearway = clearway > this.leftStopway ? clearway : this.leftClearway;
                this.leftStopway = stopway > this.leftStopway ? stopway : this.leftStopway;
            }
            if (lr.getParameters().getTORA() > this.runwayLength) {
                this.runwayLength = lr.getParameters().getTORA();
            }
        }
        this.leftOffset = Math.max(stripEnd, Math.max(this.leftClearway, this.leftStopway));
        this.rightOffset = Math.max(stripEnd, Math.max(this.rightClearway, this.rightStopway));
        this.totalLength = this.padding + this.leftOffset + this.runwayLength + this.rightOffset
                        + this.padding;
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
                    double distanceFromRunway, double yPosition, Paint color, String label) {
        // calculate position
        double runwayCentreLine = runwayCanvas.getHeight() / 2;
        double runwayTop = runwayCentreLine - (runwayWidth / 2);
        double runwayBottom = runwayCentreLine + (runwayWidth / 2);
        double y;
        if (yPosition < runwayCentreLine) { // Above
            y = runwayTop - distanceFromRunway;
        } else {
            y = runwayBottom + distanceFromRunway;
        }
        // Draw arrow
        gc.setStroke(color);
        gc.setFill(color);
        gc.setLineDashes();
        drawArrow(gc, startX, y, endX, y, color);
        // Draw lines to meet arrow
        gc.setStroke(color);
        gc.strokeLine(startX, y, startX, yPosition);
        gc.strokeLine(endX, y, endX, yPosition);
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
    private void drawArrow(GraphicsContext gc, double x1, double y1, double x2, double y2,
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
