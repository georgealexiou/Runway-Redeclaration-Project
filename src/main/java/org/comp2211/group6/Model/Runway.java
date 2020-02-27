package org.comp2211.group6.Model;

import java.util.List;
import java.util.ArrayList;

import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;

public class Runway {

  /**
      Public Methods
   */
  public Runway(String name){
    this.name = name;
  }

  public Obstacle getObstacle() { return obstacle; }
  public void setObstacle(Obstacle obstacle) {}
  public void addRunway(LogicalRunway runway) {}
  public List<LogicalRunway> getLogicalRunways() {return new ArrayList<LogicalRunway>();}
  public LogicalRunway getLogicalRunway(int id) {return new LogicalRunway(0, 0, 'C', new RunwayParameters(0, 0, 0, 0));}

  /**
      Private Methods
   */

  /**
      Private Properties
   */
  private final String name;
  private List<LogicalRunway> logicalRunways;
  private Obstacle obstacle;

}
