package org.comp2211.group6.Model;

import java.util.List;
import java.util.ArrayList;

public class Airport {

  /**
     Public Methods
   */
  public Airport(String name) {
    this.name = name;
  }

  public void addRunway(Runway runway) {}
  public void removeRunway(Runway runway) {}
  public List<Runway> getRunways() { return new ArrayList<Runway>(); }

  /**
     Private Methods
   */

  /**
     Private Properties
   */
  private final String name;
  private List<Runway> runways;

}
