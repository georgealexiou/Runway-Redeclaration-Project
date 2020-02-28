package org.comp2211.group6.Model;

public class LogicalRunway {

  /**
      Constructor for logical runway class

      @param  heading  The heading of the runway 1-36
      @param  displacedThreshold  The displaced threshold of the runway
      @param  position  The position of the runway L,R,C
      @param  params  The base runway parameters
   */
  public LogicalRunway(int heading, double displacedThreshold, char position, RunwayParameters params) {
      if(heading < 1 | heading > 36)
          throw new IllegalArgumentException("Invalid heading. Should be int 1-36");
      if(displacedThreshold < 0)
          throw new IllegalArgumentException("Displaced Threashold can't be negative");
      if(position != 'L' && position != 'R' && position != 'C')
		  throw new IllegalArgumentException("Invalid position. Can be 'L','R','C'");

	  this.heading = heading;
	  this.displacedThreshold = displacedThreshold;
	  this.position = position;
	  this.originalParameters = params;
	  this.recalculatedParameters = new RunwayParameters();
  }

  /** Public Methods */

  /** Returns an identifier e.g. 09L */
  public String getIdentifier() {
    if(heading < 10)
      return "0" + Integer.toString(heading) + position;

	  return Integer.toString(heading) + position;
  }

  /** Returns the heading of the identifier e.g. 9, 27 etc. */
  public int getHeading(){ return heading; }

  /** @return The original RunwayParameters Object */
  public RunwayParameters getParameters() { return originalParameters; }

  /** @param runwayParameters: Object that filled in with recalculated parameters */
  public void setRecalculatedParameters(RunwayParameters recalculatedParameters) { this.recalculatedParameters = recalculatedParameters; }

  /** Returns the displaced threshold */
  public double getDisplacedThreshold() { return displacedThreshold; }

  /**
   * @return if no recalculation is performed, return null (member variable object default null)
   *         otherwise return the RunwayParameters Object containing recalculated parameters
   */
  public RunwayParameters getRecalculatedParameters() { return recalculatedParameters; }

  /**
      Private Methods
   */

  /**
      Private Properties
   */
  private final int heading;
  private final char position;
  private double displacedThreshold;

  private RunwayParameters originalParameters;
  private RunwayParameters recalculatedParameters;
}
