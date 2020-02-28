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
    private double length;
    private double height;
    private double width;

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
     */
    public Obstacle(String name, String description, double length, double width, double height,
                    double distanceToCentreLine, double distanceFromLeftThreshold,
                    double distanceFromRightThreshold) {
        this.name = name;
        this.description = description;
        this.length = length;
        this.width = width;
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

    public double getLength() {
        return this.length;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    /**
     * Update the dimensions of the obstacle
     * 
     * @param length The length - inline with the runway
     * @param width The width - perpendicular to runway
     * @param height The height
     */
    public void setDimensions(double length, double width, double height) {
        this.length = length;
        this.width = width;
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
