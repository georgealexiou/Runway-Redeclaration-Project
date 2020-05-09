package org.comp2211.group6.Model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    private String identifier;

    /**
     * Public Methods
     */
    public Runway(String name) {
        this.name = name;
        this.identifier = "";
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

            StringBuilder identifierBuilder = new StringBuilder("");
            for (Iterator<LogicalRunway> it = this.logicalRunways.iterator(); it.hasNext(); ) {
                LogicalRunway temp = it.next();
                identifierBuilder.append(temp.getIdentifier());
            }
            this.identifier = identifierBuilder.toString();
        } else
            throw new Exception(
                    "Error. Logical runway cannot be added to this runway, which can have three logical runways at most.");
    }

    public List<LogicalRunway> getLogicalRunways() {
        return logicalRunways;
    }

    public ArrayList<String> getLogicalRunwayNames(){
        return (ArrayList<String>) logicalRunways.stream().map(LogicalRunway::getIdentifier).collect(Collectors.toList());
    }

    public LogicalRunway getLogicalRunwayFromName (String identifier){
        Iterator iter = logicalRunways.iterator();
        while (iter.hasNext()){
            LogicalRunway logicalRunway = (LogicalRunway) iter.next();
            if(logicalRunway.getIdentifier().equals(identifier)){
                return logicalRunway;
            }
        }
        return null;
    }

    public String getIdentifier() {
        return identifier;
    }
    @Override
    public int compareTo(Object obj) {
        return this.getName().compareTo(((Runway) obj).getName());
    }

}
