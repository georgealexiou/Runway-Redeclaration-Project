package org.comp2211.group6.Model;

import java.util.Set;
import java.util.HashSet;

public class Airport {

  /**
     Public Methods
   */
  public Airport(String name) {
    this.name = name;
  }

  public void addRunway(Runway runway) { runways.add(runway); }
  public void removeRunway(Runway runway) { runways.remove(runway); }
  public Set<Runway> getRunways() { return runways; }

  /**
     Private Methods
   */

  /**
     Private Properties
   */
  private final String name;
  private Set<Runway> runways = new HashSet<Runway>();

}
