package org.comp2211.group6.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.comp2211.group6.Model.Breakdown;
import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Model.Runway;
import org.comp2211.group6.Model.RunwayParameters;



public class Calculator {

    /** Public Properties */
    public final double RESA = 240;
    public final double StripEnd = 60;
    public final double CentreLine = 75;
    public final double BlastDistance = 300;


    /** Private Properties */
    private final Runway runway;
    private final Obstacle obstacle;

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
                logicalRunway.setRecalculatedParameters(new RunwayParameters());
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
        logicalRunway.setRecalculatedParameters(new RunwayParameters());
        logicalRunway.breakdown = new Breakdown();
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
        logicalRunway.breakdown.setThresholdDistance(thresholdDistance);
        logicalRunway.breakdown.setStripEnd(StripEnd);
        logicalRunway.breakdown.setObstacleHeight(obstacle.getHeight());
        logicalRunway.breakdown.setSlopeCalculation(obstacle.getHeight() * 50);
        logicalRunway.breakdown.setDirection(false);
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
        logicalRunway.breakdown.setResa(RESA);
        logicalRunway.breakdown.setStripEnd(StripEnd);
        logicalRunway.breakdown.setThresholdDistance(thresholdDistance);
        logicalRunway.breakdown.setDirection(true);
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

        logicalRunway.breakdown.setBlastProtection(BlastDistance);
        logicalRunway.breakdown.setThresholdDistance(thresholdDistance);
        logicalRunway.breakdown.setStopway(stopway);
        logicalRunway.breakdown.setClearway(clearway);
        logicalRunway.breakdown.setDirection(false);
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
        logicalRunway.breakdown.setObstacleHeight(obstacle.getHeight());
        logicalRunway.breakdown.setSlopeCalculation(obstacle.getHeight() * 50);
        logicalRunway.breakdown.setStripEnd(StripEnd);
        logicalRunway.breakdown.setDirection(true);
    }

    public Map<LogicalRunway, String> getAllBreakdowns() {
        HashMap<LogicalRunway, String> breakdowns = new HashMap<LogicalRunway, String>();
        for (LogicalRunway lr : this.runway.getLogicalRunways()) {
            breakdowns.put(lr, lr.breakdown.getBreakdownString(lr));
        }
        return breakdowns;
    }

}
