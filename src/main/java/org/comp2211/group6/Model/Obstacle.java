package org.comp2211.group6.Model;

public class Obstacle {

  /**
   * Public Properties
   */

  /**
      Distance of the obstacle from the centre line of the runway

      Positive if above centre line
      Negative if below centre line
      Zero if on the centre line
   */
  public double distanceToCentreLine;

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
      @param  desc  The longer description of the obstacle
      @param  l     The length - inline with the runway
      @param  w     The width - perpendicular to runway
      @param  h     The height
   */
  public Obstacle(String name, String desc, double l, double w, double h) {
  }

  public String getName() { return ""; }
  public void setName(String name) {}

  public String getDescription() { return ""; }
  public void setDescription(String desc) {}

  public double getLength() { return 0; }
  public double getWidth() { return 0; }
  public double getHeigth() { return 0; }

  /**
      Update the dimensions of the obstacle
      @param  l    The length - inline with the runway
      @param  w    The width - perpendicular to runway
      @param  h    The height
   */
  public void setDimensions(double l, double w, double h) {}

  public double getDistanceToCentreLine() { return 0; }
  public double getDistanceFromLeft() { return 0; }
  public double getDistanceFromRight() { return 0; }

  /**
   * Private Methods
   */


  /**
   * Private Properties
   */

  private String name;
  private String description;
  private double length;
  private double height;
  private double width;

}
