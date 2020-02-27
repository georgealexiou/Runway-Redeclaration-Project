package org.comp2211.group6;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.comp2211.group6.Model.*;
import org.comp2211.group6.Controller.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;


/**
 * Unit test for simple App. Based on the Heathrow Scenarios pdf.
 */
public class AppTest {


    /** Private Properties for test 1 */
    private RunwayParameters runwayParameters1;
    private LogicalRunway logicalRunway1;
    private RunwayParameters runwayParameters2;
    private LogicalRunway logicalRunway2;
    private Obstacle obstacle1;
    private Runway runway1;
    private Calculator calculatorTest1;
    
    /**
     * Test suite for all the basic configurations, including: Airport, Runway, LogicalRunway, RunwayParameters
     */
    @Test
    public void testConfig() {
      Result configTestResult = JUnitCore.runClasses(ConfigurationTestSuite.class);
      for(Failure failure: configTestResult.getFailures()) 
        System.out.println(failure.toString());
      
      System.out.println(configTestResult.wasSuccessful());
    }


    /** Testing Take Off Away, Landing Over, Take Off Towards, Landing Towards 
     * @throws Exception */
    @Test(timeout = 5000)
    @DisplayName("Test Scenario 1 from Heathrow_Scenarios.pdf")
    public void test1() throws Exception {

        // Creating the logical runways with their appropriate runway prameters.
        this.runwayParameters1 = new RunwayParameters(3902,3902,3902,3595);
        this.logicalRunway1 = new LogicalRunway(9, 306,'L',runwayParameters1);
        this.runwayParameters2 = new RunwayParameters(3884,3962,3884,3884);
        this.logicalRunway2 = new LogicalRunway(27, 0, 'R',runwayParameters2);

        // Creating the obstacle, runway and calculator.
        this.obstacle1 = new Obstacle("TestObstacle1", "For testing", 0.0,0.0,12.0, 0.0, 50.0, 3646.0);
        this.runway1 = new Runway("TestRunway1");
        this.runway1.setObstacle(this.obstacle1);
        this.runway1.addRunway(this.logicalRunway1);
        this.runway1.addRunway(this.logicalRunway2);
        this.calculatorTest1 = new Calculator(this.runway1);
        this.calculatorTest1.recalculateRunwayParameters();

        System.out.println("09L (Take Off Away, Landing Over)");
        // Checking TORA.
        assertEquals(3346,this.runwayParameters1.getTORA(), 0.1);
        // Checking TODA.
        assertEquals(3346,this.runwayParameters1.getTODA(), 0.1);
        // Checking ASDA.
        assertEquals(3346,this.runwayParameters1.getASDA(), 0.1);
        // Checking LDA.
        assertEquals(2985,this.runwayParameters1.getLDA(), 0.1);

        System.out.println("27R (Take Off Towards, Landing Towards)");
        // Checking TORA.
        assertEquals(2986,this.runwayParameters1.getTORA(), 0.1);
        // Checking TODA.
        assertEquals(2986,this.runwayParameters1.getTODA(), 0.1);
        // Checking ASDA.
        assertEquals(2986,this.runwayParameters1.getASDA(), 0.1);
        // Checking LDA.
        assertEquals(3346,this.runwayParameters1.getLDA(), 0.1);

    }

}