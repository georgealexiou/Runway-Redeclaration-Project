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
                          double asda, double lda) {
    takeOffRunAvailable = tora;
    takeOffDistanceAvailable = toda;
    accelerateStopDistanceAvailable = asda;
    landingDistanceAvailable = lda;
  }

  public double getTORA() { return 0; }
  public double getTODA() { return 0; }
  public double getASDA() { return 0; }
  public double getLDA() { return 0; }

  /**
     Private Properties
   */
  private final double takeOffRunAvailable;
  private final double takeOffDistanceAvailable;
  private final double accelerateStopDistanceAvailable;
  private final double landingDistanceAvailable;

}
