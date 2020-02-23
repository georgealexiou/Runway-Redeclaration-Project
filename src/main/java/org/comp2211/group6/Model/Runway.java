package org.comp2211.group6.Model;

import java.util.List;

import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;

public class Runway {

  /**
     Public Methods
   */
  public Runway(string name){}

  public Obstacle getObstacle() { return null; }
  public void setObstacle(Obstacle obstacle) {}
  public void addRunway(LogicalRunway runway) {}
  public List<LogicalRunway> getLogicalRunways() {return null;}
  public LogicalRunway getLogicalRunway(int id) {return null;}

  /**
     Private Methods
   */

  /**
     Private Properties
   */
  private final string name;
  private List<LogicalRunway> logicalRunways;
  private Obstacle obstacle;

}
