package org.comp2211.group6.Model;

import java.util.*;
import java.util.stream.Collectors;

public class Airport {

    /**
     * Private Properties
     */
    private final String name;
    private Set<Runway> runways = new HashSet<Runway>();

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

    public Set<Runway> getRunways() {
        return runways;
    }

    public ArrayList<String> getRunwayNames(){
        return (ArrayList<String>) runways.stream().map(Runway::getName).collect(Collectors.toList());
    }

    public Runway getRunwayFromName (String name){
        Iterator iter = runways.iterator();
        while (iter.hasNext()){
            Runway runway = (Runway) iter.next();
            if(runway.getName().equals(name)){
                return runway;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
