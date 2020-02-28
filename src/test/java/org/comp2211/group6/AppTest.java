package org.comp2211.group6;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.comp2211.group6.Model.*;
import org.comp2211.group6.Controller.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;


/**
 * Unit test for simple App. Based on the Heathrow Scenarios pdf.
 */
public class AppTest {

  /** Testing Take Off Away, Landing Over, Take Off Towards, Landing Towards */
  @Test(timeout = 5000)
  @DisplayName("Test Scenario 1 from Heathrow_Scenarios.pdf")
  public void test1() throws Exception {

    // Creating the logical runways with their appropriate runway parameters.
    RunwayParameters runwayParameters1 = new RunwayParameters(3902, 3902, 3902, 3595);
    LogicalRunway logicalRunway1 = new LogicalRunway(9, 306, 'L', runwayParameters1);
    RunwayParameters runwayParameters2 = new RunwayParameters(3884, 3962, 3884, 3884);
    LogicalRunway logicalRunway2 = new LogicalRunway(27, 0, 'R', runwayParameters2);

    // Creating the obstacle, runway and calculator.
    Obstacle obstacle1 =
        new Obstacle("TestObstacle1", "For testing", 0.0, 0.0, 12.0, 0.0, -50.0, 3646.0);
    Runway runway1 = new Runway("TestRunway1");
    runway1.setObstacle(obstacle1);
    runway1.addRunway(logicalRunway1);
    runway1.addRunway(logicalRunway2);
    Calculator calculatorTest1 = new Calculator(runway1);

    // Recalculating parameters.
    calculatorTest1.recalculateRunwayParameters();

    // Testing results.
    System.out.println("09L (Take Off Away, Landing Over)");
    // Checking TORA.
    assertEquals(3346, logicalRunway1.getRecalculatedParameters().getTORA(), 0.1);
    // Checking TODA.
    assertEquals(3346, logicalRunway1.getRecalculatedParameters().getTODA(), 0.1);
    // Checking ASDA.
    assertEquals(3346, logicalRunway1.getRecalculatedParameters().getASDA(), 0.1);
    // Checking LDA.
    assertEquals(2985, logicalRunway1.getRecalculatedParameters().getLDA(), 0.1);

    System.out.println("27R (Take Off Towards, Landing Towards)");
    // Checking TORA.
    assertEquals(2986, logicalRunway2.getRecalculatedParameters().getTORA(), 0.1);
    // Checking TODA.
    assertEquals(2986, logicalRunway2.getRecalculatedParameters().getTODA(), 0.1);
    // Checking ASDA.
    assertEquals(2986, logicalRunway2.getRecalculatedParameters().getASDA(), 0.1);
    // Checking LDA.
    assertEquals(3346, logicalRunway2.getRecalculatedParameters().getLDA(), 0.1);

  }

  /** Take Off Towards, Landing Towards, Take off Away, Landing Over */
  @Test(timeout = 5000)
  @DisplayName("Test Scenario 2 from Heathrow_Scenarios.pdf")
  public void test2() throws Exception {
    // Creating the logical runways with their appropriate runway parameters.
    RunwayParameters runwayParameters1 = new RunwayParameters(3660, 3660, 3660, 3353);
    LogicalRunway logicalRunway1 = new LogicalRunway(9, 307, 'R', runwayParameters1);
    RunwayParameters runwayParameters2 = new RunwayParameters(3660, 3660, 3660, 3660);
    LogicalRunway logicalRunway2 = new LogicalRunway(27, 0, 'L', runwayParameters2);

    // Creating the obstacle, runway and calculator.
    Obstacle obstacle1 =
        new Obstacle("TestObstacle1", "For testing", 0.0, 0.0, 25.0, -20.0, 500.0, 2853.0);
    Runway runway1 = new Runway("TestRunway1");
    runway1.setObstacle(obstacle1);
    runway1.addRunway(logicalRunway1);
    runway1.addRunway(logicalRunway2);
    Calculator calculatorTest1 = new Calculator(runway1);

    // Recalculating parameters.
    calculatorTest1.recalculateRunwayParameters();

    // Testing results.
    System.out.println("09R (Take Off Towards, Landing Towards)");
    // Checking TORA.
    assertEquals(1850, logicalRunway1.getRecalculatedParameters().getTORA(), 0.1);
    // Checking TODA.
    assertEquals(1850, logicalRunway1.getRecalculatedParameters().getTODA(), 0.1);
    // Checking ASDA.
    assertEquals(1850, logicalRunway1.getRecalculatedParameters().getASDA(), 0.1);
    // Checking LDA.
    assertEquals(2553, logicalRunway1.getRecalculatedParameters().getLDA(), 0.1);

    System.out.println("27L (Take off Away, Landing Over)");
    // Checking TORA.
    assertEquals(2860, logicalRunway2.getRecalculatedParameters().getTORA(), 0.1);
    // Checking TODA.
    assertEquals(2860, logicalRunway2.getRecalculatedParameters().getTODA(), 0.1);
    // Checking ASDA.
    assertEquals(2860, logicalRunway2.getRecalculatedParameters().getASDA(), 0.1);
    // Checking LDA.
    assertEquals(1850, logicalRunway2.getRecalculatedParameters().getLDA(), 0.1);

  }

  /** Take Off Away, Landing Over, Take Off Towards, Landing Towards */
  @Test(timeout = 5000)
  @DisplayName("Test Scenario 3 from Heathrow_Scenarios.pdf")
  public void test3() throws Exception {
    // Creating the logical runways with their appropriate runway parameters.
    RunwayParameters runwayParameters1 = new RunwayParameters(3660, 3660, 3660, 3353);
    LogicalRunway logicalRunway1 = new LogicalRunway(9, 307, 'R', runwayParameters1);
    RunwayParameters runwayParameters2 = new RunwayParameters(3660, 3660, 3660, 3660);
    LogicalRunway logicalRunway2 = new LogicalRunway(27, 0, 'L', runwayParameters2);

    // Creating the obstacle, runway and calculator.
    Obstacle obstacle1 =
        new Obstacle("TestObstacle1", "For testing", 0.0, 0.0, 15.0, 60.0, 3203.0, 150.0);
    Runway runway1 = new Runway("TestRunway1");
    runway1.setObstacle(obstacle1);
    runway1.addRunway(logicalRunway1);
    runway1.addRunway(logicalRunway2);
    Calculator calculatorTest1 = new Calculator(runway1);

    // Recalculating parameters.
    calculatorTest1.recalculateRunwayParameters();

    // Testing results.
    System.out.println("09R (Take Off Away, Landing Over)");
    // Checking TORA.
    assertEquals(2903, logicalRunway1.getRecalculatedParameters().getTORA(), 0.1);
    // Checking TODA.
    assertEquals(2903, logicalRunway1.getRecalculatedParameters().getTODA(), 0.1);
    // Checking ASDA.
    assertEquals(2903, logicalRunway1.getRecalculatedParameters().getASDA(), 0.1);
    // Checking LDA.
    assertEquals(2393, logicalRunway1.getRecalculatedParameters().getLDA(), 0.1);

    System.out.println("27L (Take Off Towards, Landing Towards)");
    // Checking TORA.
    assertEquals(2393, logicalRunway2.getRecalculatedParameters().getTORA(), 0.1);
    // Checking TODA.
    assertEquals(2393, logicalRunway2.getRecalculatedParameters().getTODA(), 0.1);
    // Checking ASDA.
    assertEquals(2393, logicalRunway2.getRecalculatedParameters().getASDA(), 0.1);
    // Checking LDA.
    assertEquals(2903, logicalRunway2.getRecalculatedParameters().getLDA(), 0.1);

  }

  /** Take Off Towards, Landing Towards, Take off Away, Landing Over */
  @Test(timeout = 5000)
  @DisplayName("Test Scenario 4 from Heathrow_Scenarios.pdf")
  public void test4() throws Exception {

    // Creating the logical runways with their appropriate runway parameters.
    RunwayParameters runwayParameters1 = new RunwayParameters(3902, 3902, 3902, 3595);
    LogicalRunway logicalRunway1 = new LogicalRunway(9, 306, 'L', runwayParameters1);
    RunwayParameters runwayParameters2 = new RunwayParameters(3884, 3962, 3884, 3884);
    LogicalRunway logicalRunway2 = new LogicalRunway(27, 0, 'R', runwayParameters2);

    // Creating the obstacle, runway and calculator.
    Obstacle obstacle1 =
        new Obstacle("TestObstacle1", "For testing", 0.0, 0.0, 20.0, 20.0, 3546.0, 50.0);
    Runway runway1 = new Runway("TestRunway1");
    runway1.setObstacle(obstacle1);
    runway1.addRunway(logicalRunway1);
    runway1.addRunway(logicalRunway2);
    Calculator calculatorTest1 = new Calculator(runway1);

    // Recalculating parameters.
    calculatorTest1.recalculateRunwayParameters();

    // Testing results.
    System.out.println("09L (Take Off Towards, Landing Towards)");
    // Checking TORA.
    assertEquals(2792, logicalRunway1.getRecalculatedParameters().getTORA(), 0.1);
    // Checking TODA.
    assertEquals(2792, logicalRunway1.getRecalculatedParameters().getTODA(), 0.1);
    // Checking ASDA.
    assertEquals(2792, logicalRunway1.getRecalculatedParameters().getASDA(), 0.1);
    // Checking LDA.
    assertEquals(3246, logicalRunway1.getRecalculatedParameters().getLDA(), 0.1);

    System.out.println("27R (Take off Away, Landing Over)");
    // Checking TORA.
    assertEquals(3534, logicalRunway2.getRecalculatedParameters().getTORA(), 0.1);
    // Checking TODA.
    assertEquals(3612, logicalRunway2.getRecalculatedParameters().getTODA(), 0.1);
    // Checking ASDA.
    assertEquals(3534, logicalRunway2.getRecalculatedParameters().getASDA(), 0.1);
    // Checking LDA.
    assertEquals(2774, logicalRunway2.getRecalculatedParameters().getLDA(), 0.1);

  }
}
