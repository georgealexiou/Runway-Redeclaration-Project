package org.comp2211.group6.Model;

import java.util.Set;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Airport {

    /**
     * Private Properties
     */
    private final String name;
    private List<Runway> runways = new ArrayList<Runway>();

    /**
     * Public Methods
     */
    public Airport(String name) {
        this.name = name;
    }

    public void addRunway(Runway runway) {
        if (runway != null)
            runways.add(runway);
        else
            throw new IllegalArgumentException(
                            "Error. Invalid runway to be added to airport, cannot be null.");
    }

    public void removeRunway(Runway runway) {
        runways.remove(runway);
    }

    public List<Runway> getRunways() {
        return runways;
    }

    public String getName() {
        return name;
    }
}
