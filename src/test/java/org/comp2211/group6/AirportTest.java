package org.comp2211.group6;

import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.comp2211.group6.Model.Airport;
import org.comp2211.group6.Model.Runway;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class AirportTest {
  Airport airport;
  private String errorMsg = "Error. Invalid runway to be added to airport, cannot be null.";
  
  @Rule
  public ExpectedException exception = ExpectedException.none();
  
  @Before
  public void constructAirport() {
    airport = new Airport("Heathrow");
  }
  
  @Test
  @Parameters(method = "testDataForConfigRunway")
  public void testAddRemoveGetRunway(Set<Runway> runwaysToAdd, Set<Runway> expected1, Set<Runway> runwaysToRemove, Set<Runway> expected2, Class errorClass, String errorMsg) {
    if(errorClass != null)
      exception.expect(errorClass);
    if(errorMsg != null)
      exception.expectMessage(errorMsg);
    
    for(Runway runway: runwaysToAdd)
      airport.addRunway(runway);
    
    assertEquals("addRunway Test Failed.", expected1, airport.getRunways());
    
    for(Runway runway: runwaysToRemove)
      airport.removeRunway(runway);
    
    assertEquals("removeRunway Test Failed.", expected2, airport.getRunways());
  }
  
  private Object[] testDataForConfigRunway(){
    Runway r1 = new Runway("Runway1");
    Runway r2 = new Runway("Runway2");
    Runway r3 = new Runway("Runway3");
    Runway r4 = new Runway("Runway4");
    Runway r5 = new Runway("Runway5");
    Runway r6 = new Runway("Runway6");
    
    return new Object[] {
        new Object[] {
            new HashSet<Runway> (Arrays.asList(r1, r2, r3, r4, r5)), 
            new HashSet<Runway> (Arrays.asList(r1, r2, r3, r4, r5)), 
            new HashSet<Runway> (Arrays.asList(r1, r2, r3, r6)), 
            new HashSet<Runway> (Arrays.asList(r4, r5)),
            null,
            null
        },
        new Object[] {
            new HashSet<Runway> (Arrays.asList(r1, r5)), 
            new HashSet<Runway> (Arrays.asList(r1, r5)), 
            new HashSet<Runway> (Arrays.asList(r1)), 
            new HashSet<Runway> (Arrays.asList(r5)),
            null,
            null
        },
        new Object[] {
            new HashSet<Runway> (Arrays.asList(r3, r5, r4, r1, r2)), 
            new HashSet<Runway> (Arrays.asList(r1, r2, r3, r4, r5)), 
            new HashSet<Runway> (Arrays.asList(r2, r5, null)), 
            new HashSet<Runway> (Arrays.asList(r1, r3, r4)),
            null,
            null
        },
        new Object[] {
            new HashSet<Runway> (Arrays.asList(r1, r2, null, r5)), 
            new HashSet<Runway> (Arrays.asList(r1, r2, r3, r4, r5)), 
            new HashSet<Runway> (Arrays.asList(r1, r2, r3, null)), 
            new HashSet<Runway> (Arrays.asList(r4, r5)),
            IllegalArgumentException.class,
            errorMsg
        },
        new Object[] {
            new HashSet<Runway> (Arrays.asList(r1, r2, r3, r4, r5)), 
            new HashSet<Runway> (Arrays.asList(r1, r2, r3, r4, r5)), 
            new HashSet<Runway> (Arrays.asList(r6)), 
            new HashSet<Runway> (Arrays.asList(r4, r5, r3, r1, r2)),
            null,
            null
        }
    };
  }
  
}
