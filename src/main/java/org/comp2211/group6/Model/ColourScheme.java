package org.comp2211.group6.Model;

import java.util.ArrayList;
import java.util.List;
import org.comp2211.group6.view.IStyleable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;

public class ColourScheme {

    private static List<IStyleable> styleables = new ArrayList<IStyleable>();

    private static ColourScheme instance;

    public static ColourScheme getInstance(IStyleable styleable) {
        if (instance == null) {
            instance = new ColourScheme();
        }
        if (!styleables.contains(styleable))
            styleables.add(styleable);
        styleable.applyStyles();
        return instance;
    }

    /**
     * The colour for the runway end safety area
     */
    private SimpleObjectProperty<Color> RESA_colour = new SimpleObjectProperty<Color>();

    /**
     * The colour for the strip end
     */
    private SimpleObjectProperty<Color> SE_colour = new SimpleObjectProperty<Color>();

    /**
     * The colour for the blast distance
     */
    private SimpleObjectProperty<Color> BLAST_colour = new SimpleObjectProperty<Color>();

    /**
     * The colour for the slope calculation
     */
    private SimpleObjectProperty<Color> SLOPE_colour = new SimpleObjectProperty<Color>();

    /**
     * The colour for the tarmac on the runway
     */
    private SimpleObjectProperty<Color> RUNWAY_colour = new SimpleObjectProperty<Color>();

    /**
     * The colour for the runway centre line
     */
    private SimpleObjectProperty<Color> CENTERLINE_colour = new SimpleObjectProperty<Color>();

    /**
     * The colour for the highlighted threshold text
     */
    private SimpleObjectProperty<Color> THRESHOLD_H_colour = new SimpleObjectProperty<Color>();

    /**
     * The colour for the highlighted threshold background
     */
    private SimpleObjectProperty<Color> THRESHOLD_H_B_colour = new SimpleObjectProperty<Color>();

    /**
     * The colour for the text
     */
    private SimpleObjectProperty<Color> TEXT_colour = new SimpleObjectProperty<Color>();

    /**
     * The colour for the obstacle
     */
    private SimpleObjectProperty<Color> OBSTACLE_colour = new SimpleObjectProperty<Color>();

    /**
     * The colour for the stopway
     */
    private SimpleObjectProperty<Color> STOPWAY_colour = new SimpleObjectProperty<Color>();

    /**
     * The colour for the clearway
     */
    private SimpleObjectProperty<Color> CLEARWAY_colour = new SimpleObjectProperty<Color>();

    /**
     * The colour for the cleared and graded area
     */
    private SimpleObjectProperty<Color> CLEAREDAREA_colour = new SimpleObjectProperty<Color>();

    /**
     * The colour for the displaced threshold
     */
    private SimpleObjectProperty<Color> DT_colour = new SimpleObjectProperty<Color>();

    /**
     * The colour of the background
     */
    private SimpleObjectProperty<Color> BACKGROUND_colour = new SimpleObjectProperty<Color>();

    /**
     * Schemes Available
     */
    public ColourScheme() {
        setDefaultColourScheme();
        this.BLAST_colour.addListener(propertyChangeListener);
        this.CENTERLINE_colour.addListener(propertyChangeListener);
        this.CLEAREDAREA_colour.addListener(propertyChangeListener);
        this.CLEARWAY_colour.addListener(propertyChangeListener);
        this.DT_colour.addListener(propertyChangeListener);
        this.OBSTACLE_colour.addListener(propertyChangeListener);
        this.RESA_colour.addListener(propertyChangeListener);
        this.RUNWAY_colour.addListener(propertyChangeListener);
        this.SE_colour.addListener(propertyChangeListener);
        this.SLOPE_colour.addListener(propertyChangeListener);
        this.STOPWAY_colour.addListener(propertyChangeListener);
        this.TEXT_colour.addListener(propertyChangeListener);
        this.THRESHOLD_H_B_colour.addListener(propertyChangeListener);
        this.THRESHOLD_H_colour.addListener(propertyChangeListener);
        this.BACKGROUND_colour.addListener(propertyChangeListener);
    }

    public void setDefaultColourScheme() {
        this.BLAST_colour.set(Color.ORANGE);
        this.CENTERLINE_colour.set(Color.WHITE);
        this.CLEAREDAREA_colour.set(Color.LIGHTGREY);
        this.CLEARWAY_colour.set(Color.RED);
        this.DT_colour.set(Color.LIGHTGREEN);
        this.OBSTACLE_colour.set(Color.YELLOW);
        this.RESA_colour.set(Color.CADETBLUE);
        this.RUNWAY_colour.set(Color.GRAY);
        this.SE_colour.set(Color.DODGERBLUE);
        this.SLOPE_colour.set(Color.CRIMSON);
        this.STOPWAY_colour.set(Color.BLUE);
        this.TEXT_colour.set(Color.BLACK);
        this.THRESHOLD_H_B_colour.set(Color.ORANGE);
        this.THRESHOLD_H_colour.set(Color.WHITE);
        this.BACKGROUND_colour.set(Color.WHITE);
    }

    public void invertColourScheme() {
        this.BLAST_colour.set(this.BLAST_colour.get().invert());
        this.CENTERLINE_colour.set(this.CENTERLINE_colour.get().invert());
        this.CLEAREDAREA_colour.set(this.CLEAREDAREA_colour.get().invert());
        this.CLEARWAY_colour.set(this.CLEARWAY_colour.get().invert());
        this.DT_colour.set(this.DT_colour.get().invert());
        this.OBSTACLE_colour.set(this.OBSTACLE_colour.get().invert());
        this.RESA_colour.set(this.RESA_colour.get().invert());
        this.RUNWAY_colour.set(this.RUNWAY_colour.get().invert());
        this.SE_colour.set(this.SE_colour.get().invert());
        this.SLOPE_colour.set(this.SLOPE_colour.get().invert());
        this.STOPWAY_colour.set(this.STOPWAY_colour.get().invert());
        this.TEXT_colour.set(this.TEXT_colour.get().invert());
        this.THRESHOLD_H_B_colour.set(this.THRESHOLD_H_B_colour.get().invert());
        this.THRESHOLD_H_colour.set(this.THRESHOLD_H_colour.get().invert());
        this.BACKGROUND_colour.set(this.BACKGROUND_colour.get().invert());

    }

    public Color getBlastDistanceColour() {
        return this.BLAST_colour.get();
    }

    public Color getCenterLineColour() {
        return this.CENTERLINE_colour.get();
    }

    public Color getClearedAndGradedAreaColour() {
        return this.CLEAREDAREA_colour.get();
    }

    public Color getClearwayColour() {
        return this.CLEARWAY_colour.get();
    }

    public Color getDisplacedThresholdColour() {
        return this.DT_colour.get();
    }

    public Color getObstacleColour() {
        return this.OBSTACLE_colour.get();
    }

    public Color getResaColour() {
        return this.RESA_colour.get();
    }

    public Color getRunwayColour() {
        return this.RUNWAY_colour.get();
    }

    public Color getStripEndColour() {
        return this.SE_colour.get();
    }

    public Color getSlopeCalculationColour() {
        return this.SLOPE_colour.get();
    }

    public Color getStopwayColour() {
        return this.STOPWAY_colour.get();
    }

    public Color getTextColour() {
        return this.TEXT_colour.get();
    }

    public Color getHighlightedThresholdTextColour() {
        return this.THRESHOLD_H_colour.get();
    }

    public Color getHighlightedThresholdBackgroundColour() {
        return this.THRESHOLD_H_B_colour.get();
    }

    public Color getBackgroundColour() {
        return this.BACKGROUND_colour.get();
    }

    public SimpleObjectProperty<Color> getBlastDistanceColourProperty() {
        return this.BLAST_colour;
    }

    public SimpleObjectProperty<Color> getCenterLineColourProperty() {
        return this.CENTERLINE_colour;
    }

    public SimpleObjectProperty<Color> getClearedAndGradedAreaColourProperty() {
        return this.CLEAREDAREA_colour;
    }

    public SimpleObjectProperty<Color> getClearwayColourProperty() {
        return this.CLEARWAY_colour;
    }

    public SimpleObjectProperty<Color> getDisplacedThresholdColourProperty() {
        return this.DT_colour;
    }

    public SimpleObjectProperty<Color> getObstacleColourProperty() {
        return this.OBSTACLE_colour;
    }

    public SimpleObjectProperty<Color> getResaColourProperty() {
        return this.RESA_colour;
    }

    public SimpleObjectProperty<Color> getRunwayColourProperty() {
        return this.RUNWAY_colour;
    }

    public SimpleObjectProperty<Color> getStripEndColourProperty() {
        return this.SE_colour;
    }

    public SimpleObjectProperty<Color> getSlopeCalculationColourProperty() {
        return this.SLOPE_colour;
    }

    public SimpleObjectProperty<Color> getStopwayColourProperty() {
        return this.STOPWAY_colour;
    }

    public SimpleObjectProperty<Color> getTextColourProperty() {
        return this.TEXT_colour;
    }

    public SimpleObjectProperty<Color> getHighlightedThresholdTextColourProperty() {
        return this.THRESHOLD_H_colour;
    }

    public SimpleObjectProperty<Color> getHighlightedThresholdBackgroundColourProperty() {
        return this.THRESHOLD_H_B_colour;
    }

    public SimpleObjectProperty<Color> getBackgroundColourProperty() {
        return this.BACKGROUND_colour;
    }

    private ChangeListener<Color> propertyChangeListener = new ChangeListener<Color>() {
        @Override
        public void changed(ObservableValue<? extends Color> observable, Color oldValue,
                        Color newValue) {
            for (IStyleable styleable : styleables) {
                styleable.applyStyles();
            }
        }
    };

}
