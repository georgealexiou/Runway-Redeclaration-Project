package org.comp2211.group6.Controller;

import org.comp2211.group6.Model.Runway;

public class Calculator {

  /** Public Properties */
  public static final double RunwayEndSafetyThreshold = 240;
  public static final double StripEndThreshold = 60;
  public static final double CentreLineThreshold = 75;

  /** Public Methods */
  public Calculator(Runway runway) { }

  /**
     Recalculates the runway parameters for the attached runway

     @param direction The direction to recalculate for
   */
  public void recalculateRunwayParameters(String direction) { }

  /** Private Methods */

  /** Private Properties */
  private final Runway runway;

}
