package org.comp2211.group6.Controller;

import org.comp2211.group6.Model.Runway;

public class Calculator {

  /** Public Properties */
  public final double RunwayEndSafetyThreshold = 240;
  public final double StripEndThreshold = 60;
  public final double CentreLineThreshold = 75;
  public boolean displayCalculations = False;

  /** Public Methods */
  public Calculator(Runway runway) {
    this.runway = runway;
  }

  public Calculator(Runway runway, Boolean displayCalculations) {
    this.runway = runway;
    this.displayCalculations = displayCalculations;
  }

  /**
    Recalculates the runway parameters for the attached runway

    @param direction The direction to recalculate for
   */
  public void recalculateRunwayParameters(String direction) {}


  /** Private Methods */

    /**
      Recalculates the LDA when a plane attempts to land over an obstacle

      @param RLDA = Distance from Threshold - RESA - Strip End
   */
  private void landingOver() {
  }

    /**
      Recalculates the LDA when a plane attempts to land towards an obstacle

      @param RLDA = LDA - Distance from Threshold - Strip End - Slope Calculation
   */
  private void landingTowards() {

  }

    /**
      Recalculates the TORA, TODA and ASDA when an aircraft is taking off towards the obstacle

      @param RTORA = TORA - Blast Protection - Distance from Threshold - Displaced Threshold
      @param RASDA = RTORA + STOPWAY
      @param RTODA = RTORA + CLEARWAY
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

}
