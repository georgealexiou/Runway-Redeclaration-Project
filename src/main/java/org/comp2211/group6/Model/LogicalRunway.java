package org.comp2211.group6.Model;

public class LogicalRunway implements Comparable {

    /**
     * Private Properties
     */
    private final int heading;
    private final char position;
    private double displacedThreshold;

    private RunwayParameters originalParameters;
    private RunwayParameters recalculatedParameters;

    public Breakdown breakdown;

    /*
     *
     * 
     * Constructor for logical runway class
     * 
     * @param heading The heading of the runway 1-36
     * 
     * @param displacedThreshold The displaced threshold of the runway
     * 
     * @param position The position of the runway L,R,C
     * 
     * @param params The base runway parameters
     */
    public LogicalRunway(int heading, double displacedThreshold, char position,
                    RunwayParameters params) {
        if (heading < 1 | heading > 36)
            throw new IllegalArgumentException("Invalid heading. Should be int 1-36");
        if (displacedThreshold < 0)
            throw new IllegalArgumentException("Displaced Threashold can't be negative");
        if (position != 'L' && position != 'R' && position != 'C')
            throw new IllegalArgumentException("Invalid position. Can be 'L','R','C'");

        this.heading = heading;
        this.displacedThreshold = displacedThreshold;
        this.position = position;
        this.originalParameters = params;
        this.recalculatedParameters = new RunwayParameters();
        this.breakdown = new Breakdown();
    }

    /** Public Methods */

    /** Returns the position **/
    public char getPosition() {
        return this.position;
    }

    /** Returns an identifier e.g. 09L */
    public String getIdentifier() {
        if (heading < 10)
            return "0" + Integer.toString(heading) + position;

        return Integer.toString(heading) + position;
    }

    /** Returns the heading of the identifier e.g. 9, 27 etc. */
    public int getHeading() {
        return heading;
    }

    /** Returns the original RunwayParameters Object */
    public RunwayParameters getParameters() {
        return originalParameters;
    }

    /** @param recalculatedParameters: Object that filled in with recalculated parameters */
    public void setRecalculatedParameters(RunwayParameters recalculatedParameters) {
        this.recalculatedParameters = recalculatedParameters;
    }

    /** Returns the displaced threshold */
    public double getDisplacedThreshold() {
        return displacedThreshold;
    }

    /**
     * Returns the recalculated runway parameters or null if no calculations performed
     */
    public RunwayParameters getRecalculatedParameters() {
        return recalculatedParameters;
    }

    @Override
    public int compareTo(Object obj) {
        return (this.getHeading() < ((LogicalRunway) obj).getHeading() ? -1
                        : (this.getHeading() == ((LogicalRunway) obj).getHeading() ? 0 : 1));
    }


}
