package org.comp2211.group6.Model;

public class Breakdown {

    private boolean towards = true;

    private double resa = 0;
    private double stripEnd = 0;
    private double slopeCalculation = 0;
    private double blastProtection = 0;

    private double obstacleHeight = 0;
    private double thresholdDistance = 0;

    private double stopway = 0;
    private double clearway = 0;

    public String getBreakdownString(LogicalRunway lr) {
        String tora, toda, asda, lda;
        if (towards) {
            lda = "RLDA = Distance from Threshold - RESA - Strip End";
            lda = lda.concat("\n     = " + thresholdDistance + " - " + resa + " - " + stripEnd);
            lda = lda.concat("\n     = " + lr.getRecalculatedParameters().getLDA() + "\n");

            tora = "RTORA = Distance from Threshold + Displaced Threshold - Slope Calculation - Strip End";
            tora = tora.concat("\n      = " + thresholdDistance + " + " + lr.getDisplacedThreshold()
                            + " - (" + obstacleHeight + "*" + 50 + ")" + " - " + stripEnd);
            tora = tora.concat("\n      = " + lr.getRecalculatedParameters().getTORA() + "\n");

            toda = "RTODA = RTORA";
            toda = toda.concat("\n     = " + lr.getRecalculatedParameters().getTODA() + "\n");

            asda = "RASDA = RTORA";
            asda = asda.concat("\n     = " + lr.getRecalculatedParameters().getASDA() + "\n");
        } else {
            lda = "RLDA = LDA - Distance from Threshold - Strip End - Slope Calculation";
            lda = lda.concat("\n     = " + lr.getParameters().getLDA() + " - " + stripEnd + " - ("
                            + obstacleHeight + "*" + 50 + ")");
            lda = lda.concat("\n     = " + lr.getRecalculatedParameters().getLDA() + "\n");

            tora = "RTORA = TORA - Blast Protection - Distance from Threshold - Displaced Threshold";
            tora = tora.concat("\n      = " + lr.getParameters().getTORA() + " - " + blastProtection
                            + " - " + thresholdDistance + " - " + lr.getDisplacedThreshold());
            tora = tora.concat("\n      = " + lr.getRecalculatedParameters().getTORA() + "\n");

            asda = "RASDA = RTORA + STOPWAY";
            asda = asda.concat("\n      = " + lr.getRecalculatedParameters().getTORA() + " + "
                            + stopway + "\n");
            asda = asda.concat("      = " + lr.getRecalculatedParameters().getASDA() + "\n");

            toda = "RTODA = RTORA + CLEARWAY";
            toda = toda.concat("\n      = " + lr.getRecalculatedParameters().getTORA() + " + "
                            + clearway);
            toda = toda.concat("      = " + lr.getRecalculatedParameters().getTODA() + "\n");
        }

        return lda + "\n" + tora + "\n" + asda + "\n" + toda;
    }

    public double getStopway() {
        return stopway;
    }

    public void setStopway(double stopway) {
        this.stopway = stopway;
    }

    public double getClearway() {
        return clearway;
    }

    public void setClearway(double clearway) {
        this.clearway = clearway;
    }

    public double getBlastProtection() {
        return blastProtection;
    }

    public void setBlastProtection(double blastProtection) {
        this.blastProtection = blastProtection;
    }

    public double getSlopeCalculation() {
        return slopeCalculation;
    }

    public void setSlopeCalculation(double slopeCalculation) {
        this.slopeCalculation = slopeCalculation;
    }

    public double getStripEnd() {
        return stripEnd;
    }

    public void setStripEnd(double stripEnd) {
        this.stripEnd = stripEnd;
    }

    public double getResa() {
        return resa;
    }

    public void setResa(double resa) {
        this.resa = resa;
    }

    /**
     * Takeoff and Landing Direction If true, landing towards and takeoff towards Else landing over
     * and takeoff away
     */
    public boolean getDirection() {
        return towards;
    }

    /**
     * @param val Whether it is land towards, takeoff towards or not
     */
    public void setDirection(boolean val) {
        towards = val;
    }

    public void setObstacleHeight(double height) {
        obstacleHeight = height;
    }

    public void setThresholdDistance(double thresholdDistance) {
        this.thresholdDistance = thresholdDistance;
    }

    public double getObstacleHeight() {
        return obstacleHeight;
    }

    public double getThresholdDistance() {
        return thresholdDistance;
    }


}
