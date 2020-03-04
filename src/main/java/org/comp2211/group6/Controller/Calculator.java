package org.comp2211.group6.Controller;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Model.Runway;



public class Calculator {

    /** Public Properties */
    public final double RESA = 240;
    public final double StripEnd = 60;
    public final double CentreLine = 75;
    public final double BlastDistance = 300;


    /** Private Properties */
    private final Runway runway;
    private final Obstacle obstacle;
    private HashMap<String, String> outputMap = new HashMap<String, String>();

    /** Public Methods */

    /**
     * Constructor for Calculator
     * 
     * @param runway The runway whose parameters are going to be recalculated
     */
    public Calculator(Runway runway) {
        this.runway = runway;
        this.obstacle = runway.getObstacle();
    }

    public HashMap<String, String> getOutputMap() {
        return outputMap;
    }

    /**
     * Returns the breakdown of a specific calculation as specified by the key.
     */
    public String getBreakDown(String key) {
        return outputMap.get(key);
    }

    /**
     * Returns an arraylist of all the calculation breakdowns
     */
    public ArrayList<String> getLogicalRunwayBreakDown() {
        ArrayList<String> outputs = new ArrayList<>();
        HashMap<String, String> temp = outputMap;
        Iterator iter = temp.entrySet().iterator();

        while (iter.hasNext()) {
            Map.Entry pair = (Map.Entry) iter.next();

            outputs.add(pair.getKey() + "\n" + pair.getValue());
            iter.remove(); // avoids a ConcurrentModificationException
        }

        return outputs;
    }

    /**
     * Recalculates the runway parameters for the attached runway
     */
    public void recalculateRunwayParameters() {
        Iterator<LogicalRunway> iterator = runway.getLogicalRunways().iterator();

        /**
         * EDGE CASES: Absolute distance from centreline must be equal or less than 75, the
         * CentreLine value. Distance from either the left or right threshold cannot be less than
         * -60, the negative of the StripEnd.
         */
        if (obstacle.getDistanceFromLeft() < -StripEnd
                        || obstacle.getDistanceFromRight() < -StripEnd
                        || Math.abs(obstacle.distanceToCentreLine) > CentreLine) {
            while (iterator.hasNext()) {
                LogicalRunway logicalRunway = iterator.next();
                logicalRunway.getRecalculatedParameters()
                                .setTORA(logicalRunway.getParameters().getTORA());
                logicalRunway.getRecalculatedParameters()
                                .setTODA(logicalRunway.getParameters().getTODA());
                logicalRunway.getRecalculatedParameters()
                                .setASDA(logicalRunway.getParameters().getASDA());
                logicalRunway.getRecalculatedParameters()
                                .setLDA(logicalRunway.getParameters().getLDA());
            }
            return;
        }

        while (iterator.hasNext()) {
            recalculate((LogicalRunway) iterator.next());
        }
    }

    /** Private Methods */

    /**
     * Determines the type of calculation to be performed and performs it on a logical runway
     * 
     * @param logicalRunway The logical runway whose parameters are going to be recalculated
     */
    private void recalculate(LogicalRunway logicalRunway) {
        if (logicalRunway.getHeading() <= 18) {
            if (obstacle.getDistanceFromLeft() < obstacle.getDistanceFromRight()) { // left side
                landingOver(logicalRunway, obstacle.getDistanceFromLeft());
                takeOffAway(logicalRunway, obstacle.getDistanceFromLeft());
            } else if (obstacle.getDistanceFromLeft() > obstacle.getDistanceFromRight()) { // right
                                                                                           // side
                landingTowards(logicalRunway, obstacle.getDistanceFromLeft());
                takeOffTowards(logicalRunway, obstacle.getDistanceFromLeft());
            }
        } else if (logicalRunway.getHeading() > 18) {
            if (obstacle.getDistanceFromLeft() < obstacle.getDistanceFromRight()) { // left side
                landingTowards(logicalRunway, obstacle.getDistanceFromRight());
                takeOffTowards(logicalRunway, obstacle.getDistanceFromRight());
            } else if (obstacle.getDistanceFromLeft() > obstacle.getDistanceFromRight()) { // right
                                                                                           // side
                landingOver(logicalRunway, obstacle.getDistanceFromRight());
                takeOffAway(logicalRunway, obstacle.getDistanceFromRight());
            }
        }
    }

    /**
     * Recalculates the LDA when a plane attempts to land over an obstacle
     *
     * Format: RLDA = LDA - Distance from Threshold - Strip End - Slope Calculation = xxx - xxx -
     * xxx - xxx = xxx
     *
     * @param logicalRunway The logical runway whose parameters are going to be recalculated
     * @param thresholdDistance The distance of the obstacle from the closest threshold
     */
    private void landingOver(LogicalRunway logicalRunway, double thresholdDistance) {
        double RLDA = logicalRunway.getParameters().getLDA() - thresholdDistance - StripEnd
                        - (obstacle.getHeight() * 50);

        logicalRunway.getRecalculatedParameters().setLDA(RLDA);

        String output = "RLDA = LDA - Distance from Threshold - Strip End - Slope Calculation";
        output = output.concat("\n     = " + logicalRunway.getParameters().getLDA() + " - "
                        + StripEnd + " - (" + obstacle.getHeight() + "*" + 50 + ")");
        output = output.concat("\n     = " + RLDA);

        outputMap.put(logicalRunway.getIdentifier() + "_LO", output);

    }

    /**
     * Recalculates the LDA when a plane attempts to land towards an obstacle
     *
     * Format: RLDA = Distance from Threshold - RESA - Strip End = xxx - xxx = xxx
     *
     * @param logicalRunway The logical runway whose parameters are going to be recalculated
     * @param thresholdDistance The distance of the obstacle from the closest threshold
     */
    private void landingTowards(LogicalRunway logicalRunway, double thresholdDistance) {
        double RLDA = thresholdDistance - RESA - StripEnd;

        logicalRunway.getRecalculatedParameters().setLDA(RLDA);

        String output = "RLDA = Distance from Threshold - RESA - Strip End";
        output = output.concat("\n     = " + thresholdDistance + " - " + RESA + " - " + StripEnd);
        output = output.concat("\n     = " + RLDA);

        outputMap.put(logicalRunway.getIdentifier() + "_LT", output);
    }

    /**
     * Recalculates the TORA, TODA and ASDA when an aircraft is taking off towards the obstacle
     *
     * Format: RTORA = TORA - Blast Protection - Distance from Threshold - Displaced Threshold = xxx
     * - xxx - xxx - xxx = xxx RASDA = RTORA + STOPWAY = xxx RTODA = RTORA = xxx
     * 
     * @param logicalRunway The logical runway whose parameters are going to be recalculated
     * @param thresholdDistance The distance of the obstacle from the closest threshold
     */
    private void takeOffAway(LogicalRunway logicalRunway, double thresholdDistance) {
        // Clearway = TODA - TORA
        // Stopway = ASDA - TORA
        double stopway = (logicalRunway.getParameters().getASDA()
                        - logicalRunway.getParameters().getTORA());
        double clearway = (logicalRunway.getParameters().getTODA()
                        - logicalRunway.getParameters().getTORA());
        double RTORA = logicalRunway.getParameters().getTORA() - BlastDistance - thresholdDistance
                        - logicalRunway.getDisplacedThreshold();
        double RASDA = RTORA + stopway;
        double RTODA = RTORA + clearway;

        logicalRunway.getRecalculatedParameters().setTORA(RTORA);
        logicalRunway.getRecalculatedParameters().setASDA(RASDA);
        logicalRunway.getRecalculatedParameters().setTODA(RTODA);

        String output = "RTORA = TORA - Blast Protection - Distance from Threshold - Displaced Threshold";
        output = output.concat("\n      = " + logicalRunway.getParameters().getTORA() + " - "
                        + BlastDistance + " - " + thresholdDistance + " - "
                        + logicalRunway.getDisplacedThreshold());
        output = output.concat("\n      = " + RTORA + "\n");

        output = output.concat("RASDA = RTORA + STOPWAY");
        output = output.concat("      = " + RASDA + " + " + stopway + "\n");

        output = output.concat("RTODA = RTORA + CLEARWAY");
        output = output.concat("      = " + RTODA + " + " + clearway);

        outputMap.put(logicalRunway.getIdentifier() + "_TA", output);
    }

    /**
     * Recalculates the TORA, TODA and ASDA when an aircraft is taking off away from an obstacle
     * RTORA = Distance from Threshold - Slope Calculation = xxx - xxx = xxx RASDA = RTORA = xxx
     * RTODA = RTORA = xxx
     * 
     * @param logicalRunway The logical runway whose parameters are going to be recalculated
     * @param thresholdDistance The distance of the obstacle from the closest threshold
     */
    private void takeOffTowards(LogicalRunway logicalRunway, double thresholdDistance) {
        double RTORA = thresholdDistance + logicalRunway.getDisplacedThreshold()
                        - (obstacle.getHeight() * 50) - StripEnd;
        double RASDA = RTORA;
        double RTODA = RTORA;

        logicalRunway.getRecalculatedParameters().setTORA(RTORA);
        logicalRunway.getRecalculatedParameters().setASDA(RASDA);
        logicalRunway.getRecalculatedParameters().setTODA(RTODA);

        String output = "RTORA = Distance from Threshold + Displaced Threshold - Slope Calculation - Strip End";
        output = output.concat("\n      = " + thresholdDistance + " + "
                        + logicalRunway.getDisplacedThreshold() + " - (" + obstacle.getHeight()
                        + "*" + 50 + ")" + " - " + StripEnd);
        output = output.concat("\n      = " + RTORA + "\n");

        output = output.concat("RASDA = RTORA");
        output = output.concat("      = " + RASDA + "\n");

        output = output.concat("RTODA = RTORA");
        output = output.concat("      = " + RTODA);

        outputMap.put(logicalRunway.getIdentifier() + "_TT", output);
    }

}
