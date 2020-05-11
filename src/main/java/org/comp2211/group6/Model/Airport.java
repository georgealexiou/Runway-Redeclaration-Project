package org.comp2211.group6.Model;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

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
        if (runway != null) {
            runways.add(runway);
            runways = runways.stream().sorted().collect(Collectors.toList());
        } else
            throw new IllegalArgumentException(
                            "Error. Invalid runway to be added to airport, cannot be null.");
    }


    public void removeRunway(Runway runway) {
        runways.remove(runway);
    }

    public Runway getRunway(String identifier) {
        Iterator<Runway> iter = runways.iterator();
        while (iter.hasNext()) {
            Runway runway = iter.next();
            if (runway.getIdentifier().equals(identifier))
                return runway;
        }

        return null;
    }

    public List<Runway> getRunways() {
        return runways;
    }

    public ArrayList<String> getRunwayNames() {
        return (ArrayList<String>) runways.stream().map(Runway::getName)
                        .collect(Collectors.toList());
    }

    public Runway getRunwayFromName(String name) {
        Iterator iter = runways.iterator();
        while (iter.hasNext()) {
            Runway runway = (Runway) iter.next();
            if (runway.getName().equals(name)) {
                return runway;
            }
        }
        return null;
    }

    public Airport getNewInstance(String name) {
        if (name == null) {
            Airport airport = new Airport(this.name);
            Iterator<Runway> iter = runways.iterator();
            while (iter.hasNext()) {
                airport.addRunway(iter.next());
            }

            return airport;

        } else {
            Airport airport = new Airport(name);
            Iterator<Runway> iter = runways.iterator();
            while (iter.hasNext()) {
                airport.addRunway(iter.next());
            }
            return airport;
        }

    }

    public String getName() {
        return name;
    }

}
