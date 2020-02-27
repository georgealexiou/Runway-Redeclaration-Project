package org.comp2211.group6.Controller;

import java.util.HashMap;
import java.util.Iterator;

import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Model.Runway;



public class Calculator {

    /** Public Properties */
    public final double RESA = 240;
    public final double StripEnd = 60;
    public final double CentreLine = 75;

    /** Public Methods */

    /**
     * Constructor for Calculator
     * @param runway The runway whose parameters are going to be recalculated
    */
    public Calculator(Runway runway){
        this.runway = runway;
        this.obstacle = runway.getObstacle();
    }

    /**
     * Recalculates the runway parameters for the attached runway
    */
    public void recalculateRunwayParameters(){
        Iterator iterator = runway.getLogicalRunways().iterator();

        while (iterator.hasNext()){
            recalculate((LogicalRunway) iterator.next());
        }
    }

    /** Private Methods */

    /**
     * Determines the type of calculation to be performed and performs it on a logical runway
     * @param logicalRunway The logical runway whose parameters are going to be recalculated
    */
    private void recalculate(LogicalRunway logicalRunway){
        if (logicalRunway.getHeading() < 18){
            if (obstacle.getDistanceFromLeft() < obstacle.getDistanceFromRight()){ //left side
                landingOver(logicalRunway, obstacle.getDistanceFromLeft());
                takeOffAway(logicalRunway, obstacle.getDistanceFromLeft());
            }
            else if (obstacle.getDistanceFromLeft() > obstacle.getDistanceFromRight()){ //right side
                landingTowards(logicalRunway, obstacle.getDistanceFromRight());
                takeOffTowards(logicalRunway, obstacle.getDistanceFromRight());
            }
        } else if (logicalRunway.getHeading() > 18){
            if (obstacle.getDistanceFromLeft() < obstacle.getDistanceFromRight()){ //left side
                landingTowards(logicalRunway, obstacle.getDistanceFromLeft());
                takeOffTowards(logicalRunway, obstacle.getDistanceFromLeft());
            }
            else if (obstacle.getDistanceFromLeft() > obstacle.getDistanceFromRight()){ //right side
                landingOver(logicalRunway, obstacle.getDistanceFromRight());
                takeOffAway(logicalRunway, obstacle.getDistanceFromRight());
            }
        }
    }

    /**
     * Recalculates the LDA when a plane attempts to land over an obstacle
     * RLDA = Distance from Threshold - RESA - Strip End
     * 
     * @param logicalRunway The logical runway whose parameters are going to be recalculated
     * @param thresholdDistance The distance of the obstacle from the closest threshold
    */
    private void landingOver(LogicalRunway logicalRunway, double thresholdDistance) {
        double RLDA = thresholdDistance - RESA - StripEnd;

        logicalRunway.setRecalculatedParameter("RLDA", RLDA);

        String output = "RLDA = Distance from Threshold - RESA - Strip End";
        output.concat("\n     = " + thresholdDistance + " - " + RESA + " - " + StripEnd);
        output.concat("\n     = " + RLDA);

        outputMap.put(logicalRunway.getIdentifier() + "_LO", output);
    }

    /**
     * Recalculates the LDA when a plane attempts to land towards an obstacle
     * RLDA = LDA - Distance from Threshold - Strip End - Slope Calculation
     * 
     * @param logicalRunway The logical runway whose parameters are going to be recalculated
     * @param thresholdDistance The distance of the obstacle from the closest threshold
    */
    private void landingTowards(LogicalRunway logicalRunway, double thresholdDistance) {
        double RLDA = logicalRunway.getParameters().getLDA() - thresholdDistance - StripEnd - (obstacle.getHeight() * 50);

        logicalRunway.setRecalculatedParameter("RLDA", RLDA);

        String output = "RLDA = Distance from Threshold - Strip End - Slope Calculation";
        output.concat("\n     = " + logicalRunway.getParameters().getLDA() + " - " + StripEnd + " - (" + obstacle.getHeight() + "*" + 50 + ")");
        output.concat("\n     = " + RLDA);
        
        outputMap.put(logicalRunway.getIdentifier() + "_LT", output);
    }

    /**
     * Recalculates the TORA, TODA and ASDA when an aircraft is taking off towards the obstacle
     * RTORA = TORA - Blast Protection - Distance from Threshold - Displaced Threshold
     * RASDA = RTORA + STOPWAY
     * RTODA = RTORA + CLEARWAY
     * 
     * @param logicalRunway The logical runway whose parameters are going to be recalculated
     * @param thresholdDistance The distance of the obstacle from the closest threshold
    */
    private void takeOffAway(LogicalRunway logicalRunway, double thresholdDistance) {
        double RTORA = logicalRunway.getParameters().getTORA() - 300 - thresholdDistance - logicalRunway.getDisplacedThreshold();
        double RASDA = RTORA;
        double RTODA = RTORA;

        logicalRunway.setRecalculatedParameter("RTORA", RTORA);
        logicalRunway.setRecalculatedParameter("RASDA", RASDA);
        logicalRunway.setRecalculatedParameter("RTODA", RTODA);

        String output = "RTORA = TORA - Blast Protection - Distance from Threshold - Displaced Threshold";
        output.concat("\n      = " + logicalRunway.getParameters().getTORA() + " - " + 300 + " - " + thresholdDistance + " - " + logicalRunway.getDisplacedThreshold());
        output.concat("\n      = " + RTORA);

        output.concat("RASDA = RTORA + STOPWAY");
        output.concat("      = " + RASDA + "\n\n");

        output.concat("RTODA = RTORA + CLEARWAY");
        output.concat("      = " + RTODA);

        outputMap.put(logicalRunway.getIdentifier() + "_TA", output);
    }

    /**
     * Recalculates the TORA, TODA and ASDA when an aircraft is taking off away from an obstacle
     * RTORA = Distance from Threshold - Slope Calculation
     * RASDA = RTORA
     * RTODA = RTORA
     * 
     * @param logicalRunway The logical runway whose parameters are going to be recalculated
     * @param thresholdDistance The distance of the obstacle from the closest threshold
    */
    private void takeOffTowards(LogicalRunway logicalRunway, double thresholdDistance) {
        double RTORA = thresholdDistance - (obstacle.getHeight() * 50);
        double RASDA = RTORA;
        double RTODA = RTORA;

        logicalRunway.setRecalculatedParameter("RTORA", RTORA);
        logicalRunway.setRecalculatedParameter("RASDA", RASDA);
        logicalRunway.setRecalculatedParameter("RTODA", RTODA);

        String output = "RTORA = Distance from Threshold - Slope Calculation";
        output.concat("\n      = " + thresholdDistance + " - (" + obstacle.getHeight() + "*" + 50 + ")");
        output.concat("\n      = " + RTORA + "\n\n");

        output.concat("RASDA = RTORA");
        output.concat("      = " + RASDA + "\n\n");

        output.concat("RTODA = RTORA");
        output.concat("      = " + RTODA);

        outputMap.put(logicalRunway.getIdentifier() + "_TT", output);
    }

    /** Private Properties */
    private final Runway runway;
    private final Obstacle obstacle;
    private HashMap<String,String> outputMap = new HashMap<String,String>();
}
