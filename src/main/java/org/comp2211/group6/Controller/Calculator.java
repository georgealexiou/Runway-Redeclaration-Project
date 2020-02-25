package org.comp2211.group6.Controller;

import java.util.Iterator;

import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Model.Runway;
import org.comp2211.group6.Model.RunwayParameters;


public class Calculator {

    /** Public Properties */
    public final double RunwayEndSafetyThreshold = 240;
    public final double StripEndThreshold = 60;
    public final double CentreLineThreshold = 75;


    /** Public Method
    
    /**
     * Constructor for Calculator
     * @param runway The runway whose parameters are going to be recalculated
    */
    public Calculator(Runway runway){
        this.runway = runway;
        this.obstacle = runway.getObstacle();
    }

    /**
     * Second constructor for Calculator
     * @param runway The runway whose parameters are going to be recalculated
     * @param generateString Indicates wether a string of calculations will be generated
    */
    public Calculator(Runway runway, Boolean generateString){
        this.runway = runway;
        this.obstacle = runway.getObstacle();
        this.displayCalculations = displayCalculations;
    }

    /**
     * Recalculates the runway parameters for the attached runway
    */
    public void recalculateRunwayParameters(){
        Iterator<E> iterator = runway.getLogicalRunways().iterator();

        while(iterator.hasNext()){
            recalculate(iterator.next());
        }
    }

    /** Private Methods */

    /**
     * Determines the type of calculation to be performed and performs it on a logical runway
     * @param logicalRunway The logical runway on which the calculations will be performed on
    */
    private String recalculate(LogicalRunway logicalRunway){
        if (obstacle.distanceFromLeftThreshold > obstacle.distanceFromRightThreshold){
            if (logicalRunway.getIdentifier() == 'L'){
                String takeOffTowardsOutput = takeOffTowards();
                String landTowardsOutput = landingTowards();
            }
            
            else if (logicalRunway.getIdentifier() == 'R'){
                String takeOffAwayOutput = takeOffAway();
                String landTowardsOutput = landingTowards();
            }
        }

        else if (obstacle.distanceFromLeftThreshold < obstacle.distanceFromRightThreshold){
            if (logicalRunway.getIdentifier() == 'L'){

            }

            else if (logicalRunway.getIdentifier() == 'R'){

            }
        }
    }

    /**
     * Recalculates the LDA when a plane attempts to land over an obstacle
     * Generates a string of the calculations performed
     * 
     * RLDA = Distance from Threshold - RESA - Strip End
    */
    private String landingOver() {
        return "";
    }

    /**
     * Recalculates the LDA when a plane attempts to land towards an obstacle
     * 
     * RLDA = LDA - Distance from Threshold - Strip End - Slope Calculation
    */
    private String landingTowards() {
        return "";
    }

    /**
     * Recalculates the TORA, TODA and ASDA when an aircraft is taking off towards the obstacle
     * 
     * RTORA = TORA - Blast Protection - Distance from Threshold - Displaced Threshold
     * RASDA = RTORA + STOPWAY
     * RTODA = RTORA + CLEARWAY
    */
    private String takeOffAway() {
        return "";
    }

    /**
     * Recalculates the TORA, TODA and ASDA when an aircraft is taking off away from an obstacle
     * 
     * RTORA = Distance from Threshold - Slope Calculation
     * RASDA = RTORA
     * RTODA = RTORA
    */
    private String takeOffTowards() {
        return "";
    }

    /** Private Properties */
    private final Runway runway;
    private final Obstacle obstacle;
    private boolean generateString = false;
    private CalculationOutput calculationOutput = null;
}
