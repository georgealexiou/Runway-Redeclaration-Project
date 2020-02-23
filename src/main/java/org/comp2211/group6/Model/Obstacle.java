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
  public Obstacle(string name, string desc, double l, double w, double h) {
  }

  public string getName() { return null; }
  public void setName(string name) {}

  public string getDescription() { return null; }
  public void setDescription(string desc) {}

  public double getLength() { return null; }
  public double getWidth() { return null; }
  public double getHeigth() { return null; }

  /**
     Update the dimensions of the obstacle
     @param  l    The length - inline with the runway
     @param  w    The width - perpendicular to runway
     @param  h    The height
   */
  public void setDimensions(double l, double w, double h) {}

  public double getDistanceToCentreLine() { return null; }
  public double getDistanceFromLeft() { return null; }
  public double getDistanceFromRight() { return null; }

  /**
   * Private Methods
   */


  /**
   * Private Properties
   */

  private final string name;
  private final string description;
  private double length;
  private double height;
  private double width;

}
