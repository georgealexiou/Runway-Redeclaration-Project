package org.comp2211.group6.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * A runway can have three logical runways at most, for example, 09L, 09C, 27R.
 */
public class Runway implements Comparable {

    /**
     * Private Properties
     */
    private String name;
    private List<LogicalRunway> logicalRunways = new ArrayList<LogicalRunway>();
    private Obstacle obstacle;

    /**
     * Public Methods
     */
    public Runway(String name) {
        this.name = name;
    }

    public Runway() {}

    public Obstacle getObstacle() {
        return obstacle;
    }

    public void setObstacle(Obstacle obstacle) {
        this.obstacle = obstacle;
    }

    public String getName() {
        return name;
    }

    public void addRunway(LogicalRunway runway) throws Exception {
        if (runway == null)
            throw new IllegalArgumentException(
                            "Error. Invalid logical runway to be added to runway, cannot be null.");
        if (logicalRunways.size() < 3) {
            logicalRunways.add(runway);

            StringBuilder nameBuilder = new StringBuilder("");
            for (Iterator<LogicalRunway> it = this.logicalRunways.iterator(); it.hasNext();) {
                LogicalRunway temp = it.next();
                nameBuilder.append(temp.getIdentifier());
            }
            this.name = nameBuilder.toString();
        } else
            throw new Exception(
                            "Error. Logical runway cannot be added to this runway, which can have three logical runways at most.");


    }

    public List<LogicalRunway> getLogicalRunways() {
        return logicalRunways;
    }

    @Override
    public int compareTo(Object obj) {
        return this.getName().compareTo(((Runway) obj).getName());
    }

}
