package org.comp2211.group6.Model;

import java.util.HashSet;
import java.util.Set;

/**
 * A runway can have three logical runways at most, for example, 09L, 09C, 27R.
 */
public class Runway {

    /**
     * Private Properties
     */
    private final String name;
    private Set<LogicalRunway> logicalRunways = new HashSet<LogicalRunway>();
    private Obstacle obstacle;

    /**
     * Public Methods
     */
    public Runway(String name) {
        this.name = name;
    }

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public String getName() { return name; }

    public void addRunway(LogicalRunway runway) throws Exception {
        if (logicalRunways.size() < 3)
            logicalRunways.add(runway);
        else
            throw new Exception("Error. Logical runway cannot be added to this runway, which can have three logical runways at most.");


        if (runway == null)
            throw new IllegalArgumentException("Error. Invalid logical runway to be added to runway, cannot be null.");
    }

    public Set<LogicalRunway> getLogicalRunways() {
        return logicalRunways;
    }

}
