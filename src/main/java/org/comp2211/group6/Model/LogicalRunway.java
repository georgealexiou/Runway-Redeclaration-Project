package org.comp2211.group6.Model;

import org.comp2211.group6.Model.RunwayParameters;

public class LogicalRunway {

  /**
     Public Methods
  */

  /**
     Create a new logical runway

     @param  heading  The heading of the runway 1-36
     @param  displacedThreshold  The displaed threshold of the runway
     @param  position  The position of the runway L,R,C
     @param  params  The base runway parameters
   */
  public LogicalRunway(int heading, double displacedThreshold,
                       char position, RunwayParameters params) {}

  /** Returns an identifier e.g. 09L */
  public string getIdentifier() { return null; }
  public RunwayParameters getParameters() { return null; }
  public void setRecalculatedParameters() {}
  public RunwayParameters getRecalculatedParameters() { return null;}

  /**
     Private Methods
   */

  /**
     Private Properties
   */
  private final int heading;
  private final char position;
  private final double displacedThreshold;

  private final RunwayParameters originalParameters;
  private RunwayParameters recalculatedParameters;
}
