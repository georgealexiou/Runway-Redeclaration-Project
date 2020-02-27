package org.comp2211.group6.Model;

import java.util.HashSet;
import java.util.Set;

import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;

/**
 * A runway can have three logical runways at most, for example, 09L, 09C, 27R.
 */
public class Runway {

  /**
      Public Methods
   */
  public Runway(String name){
    this.name = name;
  }

  public Obstacle getObstacle() { return obstacle; }
  public void setObstacle(Obstacle obstacle) { this.obstacle = obstacle; }
  public void addRunway(LogicalRunway runway) throws Exception { 
	  if(logicalRunways.size() < 3) {
		  logicalRunways.add(runway);   
	  }else {
		  throw new Exception("Error. Logical runway cannot be added to this runway, which can have three logical runways at most.");
	  }
	  
	  if(runway == null)
	    throw new IllegalArgumentException("Error. Invalid logical runway to add to runway, cannot be null.");
  }
  
  public Set<LogicalRunway> getLogicalRunways() { return logicalRunways; }

  /**
      Private Methods
   */

  /**
      Private Properties
   */
  private final String name;
  private Set<LogicalRunway> logicalRunways = new HashSet<LogicalRunway>();
  private Obstacle obstacle;

}
