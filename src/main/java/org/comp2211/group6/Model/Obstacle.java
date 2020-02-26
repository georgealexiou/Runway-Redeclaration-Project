package org.comp2211.group6.Model;

public class Obstacle {

  /**
   * Private Properties
   */
  private String name;
  private String description;
  private double length;
  private double height;
  private double width;

  /**
      Distance of the obstacle from the centre line of the runway

      Positive if above centre line
      Negative if below centre line
      Zero if on the centre line
   */
  public double distanceToCentreLine() {
    return 0;
  };

  /**
      Distance from the left threshold in metres
   */
  public double distanceFromLeftThreshold;

  /**
      Distance from the left threshold in metres
   */
  public double distanceFromRightThreshold;

  /**
   * Public Methods
   */

  /**
      Create a new obstacle
      @param  name  The short name of the obstacle
      @param  description  The longer description of the obstacle
      @param  length     The length - inline with the runway
      @param  width     The width - perpendicular to runway
      @param  height     The height
   */
  public Obstacle(String name, String description, double length, double width, double height) {
    this.name = name;
    this.description = description;
    this.length = length;
    this.width = width;
    this.height = height;
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
      Update the dimensions of the obstacle
      @param  l    The length - inline with the runway
      @param  w    The width - perpendicular to runway
      @param  h    The height
   */
  public void setDimensions(double l, double w, double h) {}

  public double getDistanceToCentreLine() {
    return 0; }
  public double getDistanceFromLeft() { return 0; }
  public double getDistanceFromRight() { return 0; }

  /**
   * Private Methods
   */




}
