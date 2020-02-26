package org.comp2211.group6.Controller;

import java.util.HashMap;
import java.util.Iterator;

import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Model.Runway;
import org.comp2211.group6.Model.RunwayParameters;


public class Calculator {

    /** Public Properties */
    public final double RESA = 240;
    public final double StripEnd = 60;
    public final double CentreLine = 75;
    public RedeclarationMap outputMap;

    /** Public Methods */

    /**
     * Constructor for Calculator
     * @param runway The runway whose parameters are going to be recalculated
    */
    public Calculator(Runway runway){
        this.runway = runway;
        this.obstacle = runway.getObstacle();
        this.outputMap = new RedeclarationMap();
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
    private void recalculate(LogicalRunway logicalRunway){

        if (obstacle.distanceFromLeftThreshold > obstacle.distanceFromRightThreshold){
            if (logicalRunway.getIdentifier().contains("L")){
                outputMap.put(logicalRunway.getIdentifier() + "_TT",takeOffTowards(logicalRunway, 'R'));
                outputMap.put(logicalRunway.getIdentifier() + "_LT", landingTowards(logicalRunway, 'R'));
            }
            
            else if (logicalRunway.getIdentifier().contains("R")){
                outputMap.put(logicalRunway.getIdentifier() + "_TA", takeOffAway(logicalRunway, 'R'));
                outputMap.put(logicalRunway.getIdentifier() + "_LT", landingOver(logicalRunway, 'R'));
            }
        }

        else if (obstacle.distanceFromLeftThreshold < obstacle.distanceFromRightThreshold){
            if (logicalRunway.getIdentifier().contains("L")){

            }

            else if (logicalRunway.getIdentifier().contains("R")){

            }
        }
    }

    /**
     * Recalculates the LDA when a plane attempts to land over an obstacle
     * Generates a string of the calculations performed
     * 
     * RLDA = Distance from Threshold - RESA - Strip End
    */
    private void landingOver(LogicalRunway logicalRunway, char direction) {

        int distanceFromThreshold = 0;
        if(direction == 'L')
            distanceFromThreshold = obstacle.distanceFromLeftThreshold;
        else if(direction == 'R')
            distanceFromThreshold = obstacle.distanceFromRightThreshold;

        int RLDA = distanceFromThreshold - RESA - StripEnd;
        
    }

    /**
     * Recalculates the LDA when a plane attempts to land towards an obstacle
     * 
     * RLDA = LDA - Distance from Threshold - Strip End - Slope Calculation
    */
    private void landingTowards(LogicalRunway logicalRunway, char direction) {
    }

    /**
     * Recalculates the TORA, TODA and ASDA when an aircraft is taking off towards the obstacle
     * 
     * RTORA = TORA - Blast Protection - Distance from Threshold - Displaced Threshold
     * RASDA = RTORA + STOPWAY
     * RTODA = RTORA + CLEARWAY
    */
    private void takeOffAway(LogicalRunway logicalRunway, char direction) {

    }

    /**
     * Recalculates the TORA, TODA and ASDA when an aircraft is taking off away from an obstacle
     * 
     * RTORA = Distance from Threshold - Slope Calculation
     * RASDA = RTORA
     * RTODA = RTORA
    */
    private void takeOffTowards(LogicalRunway logicalRunway, char direction) {

    }

    /** Private Properties */
    private final Runway runway;
    private final Obstacle obstacle;
    private boolean generateString = false;
    private CalculationOutput calculationOutput = null;
}