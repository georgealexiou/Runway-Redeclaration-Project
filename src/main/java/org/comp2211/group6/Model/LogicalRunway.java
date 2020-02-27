package org.comp2211.group6.Model;

import org.comp2211.group6.Model.RunwayParameters;

public class LogicalRunway {

  /**
      Create a new logical runway

      @param  heading  The heading of the runway 1-36
      @param  displacedThreshold  The displaced threshold of the runway
      @param  position  The position of the runway L,R,C
      @param  params  The base runway parameters
   */
  public LogicalRunway(int heading, double displacedThreshold,
                       char position, RunwayParameters params) {
	if(heading < 1 | heading > 36)
		  throw new IllegalArgumentException("Error. Invalid heading, which should be an Integer between 1 and 36.");
	  if(displacedThreshold <= 0)
		  throw new IllegalArgumentException("Error. Invalid displaced threshold, which should be a positive number.");
	  if(position != 'L' && position != 'R' && position != 'C')
		  throw new IllegalArgumentException("Error. Invalid position, which can be 'L' or 'R' or 'C'.");
		  
	  this.heading = heading;
	  this.displacedThreshold = displacedThreshold;
	  this.position = position;
	  this.originalParameters = params;
  }

  /** Overloadded Construtor with no displaced threshold. */
  public LogicalRunway(int heading, char position, RunwayParameters params) {
	if(heading < 1 | heading > 36)
		  throw new IllegalArgumentException("Error. Invalid heading, which should be an Integer between 1 and 36.");
	  if(position != 'L' && position != 'R' && position != 'C')
		  throw new IllegalArgumentException("Error. Invalid position, which can be 'L' or 'R' or 'C'.");

	  this.heading = heading;
	  this.position = position;
	  this.originalParameters = params;
}  
  
  /**
      Public Methods
  */
  
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
  public void setRecalculatedParameters(RunwayParameters runwayParameters) { recalculatedParameters = runwayParameters; }
  
  /** set individual runway parameters */
  public void setRecalculatedParameter(String name, double params) {
	  RunwayParameters runwayParameters = new RunwayParameters(
			  originalParameters.getTORA(), originalParameters.getTODA(),
			  originalParameters.getASDA(), originalParameters.getLDA());
	  
	  if(name == "RTORA") {
		  runwayParameters.setTORA(params);
	  }else if(name == "RTODA") {
		  runwayParameters.setTODA(params);
	  }else if(name == "RTORA") {
		  runwayParameters.setASDA(params);
	  }else if(name == "RLDA") {
		  runwayParameters.setLDA(params);
	  }else {
		  throw new IllegalArgumentException("Failed to set recalculated parameter. Invalid name of parameter.");
	  }
	  
	  setRecalculatedParameters(runwayParameters);
  }

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

  private final RunwayParameters originalParameters;
  private RunwayParameters recalculatedParameters;
}
