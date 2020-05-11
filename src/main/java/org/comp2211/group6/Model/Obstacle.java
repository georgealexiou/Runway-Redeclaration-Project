package org.comp2211.group6.Model;

public class Obstacle {


    /**
     * Public Properties
     */
    /**
     * Distance of the obstacle from the centre line of the runway Positive if above centre line
     * Negative if below centre line Zero if on the centre line
     */
    public double distanceToCentreLine;

    /**
     * Distance from the left and right threshold in metres
     */
    public double distanceFromLeftThreshold;
    public double distanceFromRightThreshold;

    /**
     * Private Properties
     */
    private String name;
    private String description;
    private double height;

    /**
     * Public Methods
     */

    /**
     * Create a new obstacle
     * 
     * @param name The short name of the obstacle
     * @param description The longer description of the obstacle
     * @param length The length - inline with the runway
     * @param width The width - perpendicular to runway
     * @param height The height
     * @param distanceToCentreLine
     * @param distanceFromLeftThreshold
     * @param distanceFromRightThreshold
     */
    public Obstacle(String name, String description, double height, double distanceToCentreLine,
                    double distanceFromLeftThreshold, double distanceFromRightThreshold) {
        this.name = name;
        this.description = description;
        this.height = height;
        this.distanceToCentreLine = distanceToCentreLine;
        this.distanceFromLeftThreshold = distanceFromLeftThreshold;
        this.distanceFromRightThreshold = distanceFromRightThreshold;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getHeight() {
        return this.height;
    }

    /**
     * Update the dimensions of the obstacle
     * 
     * @param height The height
     */
    public void setDimensions(double height) {
        this.height = height;
    }

    public double getDistanceToCentreLine() {
        return distanceToCentreLine;
    }

    public double getDistanceFromLeft() {
        return distanceFromLeftThreshold;
    }

    public double getDistanceFromRight() {
        return distanceFromRightThreshold;
    }

}
