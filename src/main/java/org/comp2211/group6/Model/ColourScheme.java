package org.comp2211.group6.Model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.paint.Color;
import javafx.stage.Window;

public class ColourScheme {

    private static ColourScheme instance;

    public static ColourScheme getInstance() {
        if (instance == null) {
            instance = new ColourScheme();
        }
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
    private SimpleObjectProperty<Color> ACCENT_colour = new SimpleObjectProperty<Color>();
    private SimpleObjectProperty<Color> BORDER_colour = new SimpleObjectProperty<Color>();

    private SimpleObjectProperty<ThemeConfig> CURRENT_THEME =
                    new SimpleObjectProperty<ThemeConfig>();

    /**
     * Schemes Available
     */
    private final List<ThemeConfig> availableThemes = new ArrayList<ThemeConfig>(
                    Arrays.asList(new ThemeConfig("Default", Color.ORANGE, Color.WHITE,
                                    Color.LIGHTGREY, Color.RED, Color.LIGHTGREEN, Color.YELLOW,
                                    Color.CADETBLUE, Color.GRAY, Color.DODGERBLUE, Color.CRIMSON,
                                    Color.BLUE, Color.BLACK, Color.ORANGE, Color.WHITE, Color.WHITE,
                                    Color.LIGHTBLUE, Color.BLACK)));


    public ColourScheme() {
        this.CURRENT_THEME.addListener(themeChangedListener);
        setDefaultColourScheme();

        Window.getWindows().addListener(new ListChangeListener<Window>() {
            @Override
            public void onChanged(Change<? extends Window> change) {
                change.next();
                if (change.wasAdded() || change.wasUpdated()) {
                    change.getList().forEach(w -> {
                        w.getScene().getStylesheets().add(
                                        getClass().getResource("/css/base.css").toExternalForm());
                    });
                }
            }
        });
    }

    public void setDefaultColourScheme() {
        System.out.println(this.availableThemes.stream()
                        .filter(t -> t.ThemeIdentifier.equals("Default")).findFirst().orElse(null));
        this.CURRENT_THEME.set(this.availableThemes.stream()
                        .filter(t -> t.ThemeIdentifier.equals("Default")).findFirst().orElse(null));
    }

    /**
     * Inverts the current colour scheme
     */
    public void invertColourScheme() {
        if (this.CURRENT_THEME.get().ThemeIdentifier.contains("inverted")) {
            this.CURRENT_THEME.set(this.availableThemes.stream()
                            .filter(t -> t.ThemeIdentifier.equals(
                                            this.CURRENT_THEME.get().ThemeIdentifier.split("_")[0]))
                            .findFirst().orElse(null));
        } else {
            this.CURRENT_THEME.set(this.CURRENT_THEME.get().invert());
        }
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

    public Color getAccentColour() {
        return this.ACCENT_colour.get();
    }

    public Color getBorderColour() {
        return this.BORDER_colour.get();
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

    public SimpleObjectProperty<Color> getAccentColourProperty() {
        return this.ACCENT_colour;
    }

    public SimpleObjectProperty<Color> getBorderColourProperty() {
        return this.BORDER_colour;
    }

    private ChangeListener<ThemeConfig> themeChangedListener = new ChangeListener<ThemeConfig>() {
        @Override
        public void changed(ObservableValue<? extends ThemeConfig> observable, ThemeConfig oldValue,
                        ThemeConfig newValue) {
            if (newValue != null) {
                BLAST_colour.set(newValue.BLAST_colour);
                CENTERLINE_colour.set(newValue.CENTERLINE_colour);
                CLEAREDAREA_colour.set(newValue.CLEAREDAREA_colour);
                CLEARWAY_colour.set(newValue.CLEARWAY_colour);
                DT_colour.set(newValue.DT_colour);
                OBSTACLE_colour.set(newValue.OBSTACLE_colour);
                RESA_colour.set(newValue.RESA_colour);
                RUNWAY_colour.set(newValue.RUNWAY_colour);
                SE_colour.set(newValue.SE_colour);
                SLOPE_colour.set(newValue.SLOPE_colour);
                STOPWAY_colour.set(newValue.STOPWAY_colour);
                TEXT_colour.set(newValue.TEXT_colour);
                THRESHOLD_H_B_colour.set(newValue.THRESHOLD_H_B_colour);
                THRESHOLD_H_colour.set(newValue.THRESHOLD_H_colour);
                BACKGROUND_colour.set(newValue.BACKGROUND_colour);
                BORDER_colour.set(newValue.BORDER_colour);
                ACCENT_colour.set(newValue.ACCENT_colour);
                generateRootStyleString(Window.getWindows());
            }
        }
    };

    public void generateRootStyleString(List<Window> ws) {
        StringBuilder builder = new StringBuilder();

        builder.append(".root {");

        builder.append(String.format("-fx-base: #%s; ",
                        this.BACKGROUND_colour.get().toString().substring(2, 8)));
        builder.append(String.format("-fx-background: #%s; ",
                        this.BACKGROUND_colour.get().toString().substring(2, 8)));
        builder.append(String.format("-fx-control-inner-background: derive(#%s,80%%); ",
                        this.BACKGROUND_colour.get().toString().substring(2, 8)));
        builder.append(String.format("-fx-control-inner-background-alt: derive(#%s,-2%%); ",
                        this.BACKGROUND_colour.get().toString().substring(2, 8)));
        builder.append(String.format("-fx-text-background-color: #%s; ",
                        this.TEXT_colour.get().toString().substring(2, 8)));
        builder.append(String.format("-fx-accent: #%s; ",
                        this.ACCENT_colour.get().toString().substring(2, 8)));
        builder.append(String.format("-fx-focus-color: #%s; ",
                        this.ACCENT_colour.get().toString().substring(2, 8)));
        builder.append(String.format("-fx-color: #%s; ",
                        this.BACKGROUND_colour.get().toString().substring(2, 8)));
        builder.append(String.format("-fx-box-border: #%s; ",
                        this.BORDER_colour.get().toString().substring(2, 8)));
        builder.append(String.format("-fx-text-box-border: #%s; ",
                        this.BORDER_colour.get().toString().substring(2, 8)));
        builder.append(String.format("-fx-outer-border: #%s; ",
                        this.BORDER_colour.get().toString().substring(2, 8)));
        builder.append(String.format("-fx-inner-border: #%s; ",
                        this.BORDER_colour.get().toString().substring(2, 8)));
        builder.append(String.format("-fx-body-color: derive(#%s, 8%%); ",
                        this.BACKGROUND_colour.get().toString().substring(2, 8)));
        builder.append(String.format("-fx-text-base-color: #%s; ",
                        this.TEXT_colour.get().toString().substring(2, 8)));
        builder.append(String.format("-fx-text-inner-color: #%s; ",
                        this.TEXT_colour.get().toString().substring(2, 8)));
        builder.append(String.format("-fx-mark-color: #%s; ",
                        this.TEXT_colour.get().toString().substring(2, 8)));
        builder.append(String.format("-fx-selection-bar: #%s; ",
                        this.ACCENT_colour.get().toString().substring(2, 8)));
        builder.append(String.format("-fx-background-color: #%s; ",
                        this.BACKGROUND_colour.get().toString().substring(2, 8)));

        builder.append("}");
        try {
            File temp = File.createTempFile("temp", ".css");
            temp.deleteOnExit();

            try (PrintWriter printWriter = new PrintWriter(temp)) {
                printWriter.println(builder.toString());
            }

            ws.forEach(w -> w.getScene().getStylesheets().add(temp.toURI().toString()));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public class ThemeConfig {
        public final Color BLAST_colour;
        public final Color CENTERLINE_colour;
        public final Color CLEAREDAREA_colour;
        public final Color CLEARWAY_colour;
        public final Color DT_colour;
        public final Color OBSTACLE_colour;
        public final Color RESA_colour;
        public final Color RUNWAY_colour;
        public final Color SE_colour;
        public final Color SLOPE_colour;
        public final Color STOPWAY_colour;
        public final Color TEXT_colour;
        public final Color THRESHOLD_H_B_colour;
        public final Color THRESHOLD_H_colour;
        public final Color BACKGROUND_colour;
        public final Color ACCENT_colour;
        public final Color BORDER_colour;

        public final String ThemeIdentifier;

        public ThemeConfig(String name, Color BLAST, Color CENTERLINE, Color CLEAREDAREA,
                        Color CLEARWAY, Color DT, Color OBSTACLE, Color RESA, Color RUNWAY,
                        Color SE, Color SLOPE, Color STOPWAY, Color TEXT, Color THRESHOLD_H_B,
                        Color THRESHOLD_H, Color BACKGROUND, Color ACCENT, Color BORDER) {
            this.ThemeIdentifier = name;
            this.BLAST_colour = BLAST;
            this.CENTERLINE_colour = CENTERLINE;
            this.CLEAREDAREA_colour = CLEAREDAREA;
            this.CLEARWAY_colour = CLEARWAY;
            this.DT_colour = DT;
            this.OBSTACLE_colour = OBSTACLE;
            this.RESA_colour = RESA;
            this.RUNWAY_colour = RUNWAY;
            this.SE_colour = SE;
            this.SLOPE_colour = SLOPE;
            this.STOPWAY_colour = STOPWAY;
            this.TEXT_colour = TEXT;
            this.THRESHOLD_H_B_colour = THRESHOLD_H_B;
            this.THRESHOLD_H_colour = THRESHOLD_H;
            this.BACKGROUND_colour = BACKGROUND;
            this.ACCENT_colour = ACCENT;
            this.BORDER_colour = BORDER;
        }

        public ThemeConfig invert() {
            return new ThemeConfig(ThemeIdentifier + "_inverted", BLAST_colour.invert(),
                            CENTERLINE_colour.invert(), CLEAREDAREA_colour.invert(),
                            CLEARWAY_colour.invert(), DT_colour.invert(), OBSTACLE_colour.invert(),
                            RESA_colour.invert(), RUNWAY_colour.invert(), SE_colour.invert(),
                            SLOPE_colour.invert(), STOPWAY_colour.invert(), TEXT_colour.invert(),
                            THRESHOLD_H_B_colour.invert(), THRESHOLD_H_B_colour.invert(),
                            BACKGROUND_colour.invert(), ACCENT_colour.invert(),
                            BORDER_colour.invert());
        }

    }

    public SimpleObjectProperty<ThemeConfig> getCurrentThemeProperty() {
        return this.CURRENT_THEME;
    }
}
