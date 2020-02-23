package org.comp2211.group6.Model;

public class RunwayParameters {

  /**
     Public Methods
   */

  /**
     Create a new set of runway parameters

     @param  tora  The take off run available
     @param  toda  The take off distance available
     @param  asda  The accelerate stop distance available
     @param  lda   The landing distance available
   */
  public RunwayParameters(double tora, double toda,
                          double asda, double lda) {}

  public double getTORA() { return null; }
  public double getTODA() { return null; }
  public double getASDA() { return null; }
  public double getLDA() { return null; }

  /**
     Private Properties
   */
  private final double takeOffRunAvailable;
  private final double takeOffDistanceAvailable;
  private final double accelerateStopDistanceAvailable;
  private final double landingDistanceAvailable;

}
