package org.comp2211.group6.Model;

public class RunwayParameters {

  /**
      Create a new set of runway parameters

      @param  tora  The take off run available
      @param  toda  The take off distance available
      @param  asda  The accelerate stop distance available
      @param  lda   The landing distance available
   */
  public RunwayParameters(double tora, double toda,
                          double asda, double lda) {
	  if(tora <= 0) 
		  throw new IllegalArgumentException(
				  "Error. Invalid TORA. Only positive numbers are allowed.");
	  if (toda <= 0)
		  throw new IllegalArgumentException(
				  "Error. Invalid TODA. Only positive numbers are allowed.");
	  if (asda <= 0)
		  throw new IllegalArgumentException(
				  "Error. Invalid ASDA. Only positive numbers are allowed.");
	  if (lda <= 0)
		  throw new IllegalArgumentException(
				  "Error. Invalid LDA. Only positive numbers are allowed.");
		  
		  takeOffRunAvailable = tora;
		  takeOffDistanceAvailable = toda;
		  accelerateStopDistanceAvailable = asda;
		  landingDistanceAvailable = lda;
  }

  /**
      Public Methods
   */
  public double getTORA() { return takeOffRunAvailable; }
  public double getTODA() { return takeOffDistanceAvailable; }
  public double getASDA() { return accelerateStopDistanceAvailable; }
  public double getLDA() { return landingDistanceAvailable; }

  /**
     Private Properties
   */
  private final double takeOffRunAvailable;
  private final double takeOffDistanceAvailable;
  private final double accelerateStopDistanceAvailable;
  private final double landingDistanceAvailable;

}
