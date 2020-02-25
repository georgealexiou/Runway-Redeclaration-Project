package org.comp2211.group6.Controller;

import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Model.Runway;
import org.comp2211.group6.Model.RunwayParameters;

public class Calculator {

  /** Public Properties */

  public static final double RunwayEndSafetyThreshold = 240;
  public static final double StripEndThreshold = 60;
  public static final double CentreLineThreshold = 75;

  /** Public Methods */

  /**
    Constructor for Calculator

    @param runway The runway whose parameters are going to be recalculated
   */
  public Calculator(Runway runway){
    this.runway = runway;
    this.obstacle = runway.getObstacle();
  }

    /**
    Second constructor for Calculator

    @param runway The runway whose parameters are going to be recalculated
    @param displayCalculations Indicates wether a string of calculations will be displayed
   */
  public Calculator(Runway runway, Boolean displayCalculations){
    this.runway = runway;
    this.obstacle = runway.getObstacle();
    this.displayCalculations = displayCalculations;
  }

  /**
    Recalculates the runway parameters for the attached runway
   */
  public void recalculateRunwayParameters(){

  }


  /** Private Methods */

  /**
    Recalculates the LDA when a plane attempts to land over an obstacle
    Generates a string of the calculations performed

    RLDA = Distance from Threshold - RESA - Strip End
   */
  private void landingOver() {
    }

  /**
    Recalculates the LDA when a plane attempts to land towards an obstacle

    RLDA = LDA - Distance from Threshold - Strip End - Slope Calculation
   */
  private void landingTowards() {
  }

  /**
    Recalculates the TORA, TODA and ASDA when an aircraft is taking off towards the obstacle

    RTORA = TORA - Blast Protection - Distance from Threshold - Displaced Threshold
    RASDA = RTORA + STOPWAY
    RTODA = RTORA + CLEARWAY
   */
  private void takeOffAway() {

  }

    /**
      Recalculates the TORA, TODA and ASDA when an aircraft is taking off away from an obstacle

      @param RTORA = Distance from Threshold - Slope Calculation
      @param RASDA = RTORA
      @param RTODA = RTORA

   */
  private void takeOffTowards() {

  }
  
  /** Private Properties */
  private final Runway runway;
  private final Obstacle obstacle;
  private boolean displayCalculations = false;
}
