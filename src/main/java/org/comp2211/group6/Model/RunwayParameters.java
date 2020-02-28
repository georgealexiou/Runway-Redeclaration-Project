package org.comp2211.group6.Model;

public class RunwayParameters {


  /**
   * Private Properties
   */
  private double takeOffRunAvailable;
  private double takeOffDistanceAvailable;
  private double accelerateStopDistanceAvailable;
  private double landingDistanceAvailable;

  /**
   * Create a new set of runway parameters
   * 
   * @param tora The take off run available
   * @param toda The take off distance available
   * @param asda The accelerate stop distance available
   * @param lda The landing distance available
   */
  public RunwayParameters(double tora, double toda, double asda, double lda) {

    takeOffRunAvailable = checkValidity("TORA", tora);
    takeOffDistanceAvailable = checkValidity("TODA", toda);
    accelerateStopDistanceAvailable = checkValidity("ASDA", asda);
    landingDistanceAvailable = checkValidity("LDA", lda);
  }

  /**
   * Second constructor that takes no parameters
   */
  public RunwayParameters() {}

  /**
   * Public Methods
   */
  public void setTORA(double tora) {
    takeOffRunAvailable = checkValidity("TORA", tora);
  }

  public void setTODA(double toda) {
    takeOffDistanceAvailable = checkValidity("TODA", toda);
  }

  public void setASDA(double asda) {
    accelerateStopDistanceAvailable = checkValidity("ASDA", asda);
  }

  public void setLDA(double lda) {
    landingDistanceAvailable = checkValidity("LDA", lda);
  }

  public double getTORA() {
    return takeOffRunAvailable;
  }

  public double getTODA() {
    return takeOffDistanceAvailable;
  }

  public double getASDA() {
    return accelerateStopDistanceAvailable;
  }

  public double getLDA() {
    return landingDistanceAvailable;
  }

  /** check validity of parameters and throw exception for invalid ones. */
  private double checkValidity(String name, double params) {
    if (params <= 0)
      throw new IllegalArgumentException(
          "Error. Invalid " + name + ". Only positive numbers are allowed.");

    return params;
  }

}
