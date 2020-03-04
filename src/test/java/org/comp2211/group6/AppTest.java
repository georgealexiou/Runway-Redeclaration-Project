package org.comp2211.group6;

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
        Obstacle obstacle1 = new Obstacle("TestObstacle1", "For testing", 0.0, 0.0, 12.0, 0.0,
                        -50.0, 3646.0);
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
        assertEquals("TORA - 09L", 3346, logicalRunway1.getRecalculatedParameters().getTORA(), 0.1);
        // Checking TODA.
        assertEquals("TODA - 09L", 3346, logicalRunway1.getRecalculatedParameters().getTODA(), 0.1);
        // Checking ASDA.
        assertEquals("ASDA - 09L", 3346, logicalRunway1.getRecalculatedParameters().getASDA(), 0.1);
        // Checking LDA.
        assertEquals("LDA - 09L", 2985, logicalRunway1.getRecalculatedParameters().getLDA(), 0.1);

        System.out.println("27R (Take Off Towards, Landing Towards)");
        // Checking TORA.
        assertEquals("TORA - 27R", 2986, logicalRunway2.getRecalculatedParameters().getTORA(), 0.1);
        // Checking TODA.
        assertEquals("TODA - 27R", 2986, logicalRunway2.getRecalculatedParameters().getTODA(), 0.1);
        // Checking ASDA.
        assertEquals("ASDA - 27R", 2986, logicalRunway2.getRecalculatedParameters().getASDA(), 0.1);
        // Checking LDA.
        assertEquals("LDA - 27R", 3346, logicalRunway2.getRecalculatedParameters().getLDA(), 0.1);

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
        Obstacle obstacle1 = new Obstacle("TestObstacle1", "For testing", 0.0, 0.0, 25.0, -20.0,
                        2853.0, 500.0);
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
        assertEquals("TORA - 09R", 1850, logicalRunway1.getRecalculatedParameters().getTORA(), 0.1);
        // Checking TODA.
        assertEquals("TODA - 09R", 1850, logicalRunway1.getRecalculatedParameters().getTODA(), 0.1);
        // Checking ASDA.
        assertEquals("ASDA - 09R", 1850, logicalRunway1.getRecalculatedParameters().getASDA(), 0.1);
        // Checking LDA.
        assertEquals("LDA - 09R", 2553, logicalRunway1.getRecalculatedParameters().getLDA(), 0.1);

        System.out.println("27L (Take off Away, Landing Over)");
        // Checking TORA.
        assertEquals("TORA - 27L", 2860, logicalRunway2.getRecalculatedParameters().getTORA(), 0.1);
        // Checking TODA.
        assertEquals("TODA - 27L", 2860, logicalRunway2.getRecalculatedParameters().getTODA(), 0.1);
        // Checking ASDA.
        assertEquals("ASDA - 27L", 2860, logicalRunway2.getRecalculatedParameters().getASDA(), 0.1);
        // Checking LDA.
        assertEquals("LDA - 27L", 1850, logicalRunway2.getRecalculatedParameters().getLDA(), 0.1);

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
        Obstacle obstacle1 = new Obstacle("TestObstacle1", "For testing", 0.0, 0.0, 15.0, 60.0,
                        150.0, 3203.0);
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
        assertEquals("TORA - 09R", 2903, logicalRunway1.getRecalculatedParameters().getTORA(), 0.1);
        // Checking TODA.
        assertEquals("TODA - 09R", 2903, logicalRunway1.getRecalculatedParameters().getTODA(), 0.1);
        // Checking ASDA.
        assertEquals("ASDA - 09R", 2903, logicalRunway1.getRecalculatedParameters().getASDA(), 0.1);
        // Checking LDA.
        assertEquals("LDA - 09R", 2393, logicalRunway1.getRecalculatedParameters().getLDA(), 0.1);

        System.out.println("27L (Take Off Towards, Landing Towards)");
        // Checking TORA.
        assertEquals("TORA - 27L", 2393, logicalRunway2.getRecalculatedParameters().getTORA(), 0.1);
        // Checking TODA.
        assertEquals("TODA - 27L", 2393, logicalRunway2.getRecalculatedParameters().getTODA(), 0.1);
        // Checking ASDA.
        assertEquals("ASDA - 27L", 2393, logicalRunway2.getRecalculatedParameters().getASDA(), 0.1);
        // Checking LDA.
        assertEquals("LDA - 27L", 2903, logicalRunway2.getRecalculatedParameters().getLDA(), 0.1);

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
        Obstacle obstacle1 = new Obstacle("TestObstacle1", "For testing", 0.0, 0.0, 20.0, 20.0,
                3546.0, 50.0);
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
        assertEquals("TORA - 09L", 2792, logicalRunway1.getRecalculatedParameters().getTORA(), 0.1);
        // Checking TODA.
        assertEquals("TODA - 09L", 2792, logicalRunway1.getRecalculatedParameters().getTODA(), 0.1);
        // Checking ASDA.
        assertEquals("ASDA - 09L", 2792, logicalRunway1.getRecalculatedParameters().getASDA(), 0.1);
        // Checking LDA.
        assertEquals("LDA - 09L", 3246, logicalRunway1.getRecalculatedParameters().getLDA(), 0.1);

        System.out.println("27R (Take off Away, Landing Over)");
        // Checking TORA.
        assertEquals("TORA - 27R", 3534, logicalRunway2.getRecalculatedParameters().getTORA(), 0.1);
        // Checking TODA.
        assertEquals("TODA - 27R", 3612, logicalRunway2.getRecalculatedParameters().getTODA(), 0.1);
        // Checking ASDA.
        assertEquals("ASDA - 27R", 3534, logicalRunway2.getRecalculatedParameters().getASDA(), 0.1);
        // Checking LDA.
        assertEquals("LDA - 27R", 2774, logicalRunway2.getRecalculatedParameters().getLDA(), 0.1);

    }

    /** Testing edge case 1: Obstacle is outside the 75m limit from the centreline (80m) */
    @Test(timeout = 5000)
    @DisplayName("Test Scenario 5 for obstacle far away from centreline")
    public void test5() throws Exception {

        // Creating the logical runways with their appropriate runway parameters.
        RunwayParameters runwayParameters1 = new RunwayParameters(3902, 3902, 3902, 3595);
        LogicalRunway logicalRunway1 = new LogicalRunway(9, 306, 'L', runwayParameters1);
        RunwayParameters runwayParameters2 = new RunwayParameters(3884, 3962, 3884, 3884);
        LogicalRunway logicalRunway2 = new LogicalRunway(27, 0, 'R', runwayParameters2);

        // Creating the obstacle, runway and calculator.
        Obstacle obstacle1 = new Obstacle("TestObstacle1", "For testing", 0.0, 0.0, 20.0, 80.0,
                3546.0, 50.0);
        Runway runway1 = new Runway("TestRunway1");
        runway1.setObstacle(obstacle1);
        runway1.addRunway(logicalRunway1);
        runway1.addRunway(logicalRunway2);
        Calculator calculatorTest1 = new Calculator(runway1);

        // Recalculating parameters.
        calculatorTest1.recalculateRunwayParameters();

        // Testing results.
        System.out.println("Edge case: 09L (Take Off Towards, Landing Towards)");
        // Checking TORA.
        assertEquals("TORA - 09L", 3902, logicalRunway1.getRecalculatedParameters().getTORA(), 0.1);
        // Checking TODA.
        assertEquals("TODA - 09L", 3902, logicalRunway1.getRecalculatedParameters().getTODA(), 0.1);
        // Checking ASDA.
        assertEquals("ASDA - 09L", 3902, logicalRunway1.getRecalculatedParameters().getASDA(), 0.1);
        // Checking LDA.
        assertEquals("LDA - 09L", 3595, logicalRunway1.getRecalculatedParameters().getLDA(), 0.1);

        System.out.println("Edge case: 27R (Take off Away, Landing Over)");
        // Checking TORA.
        assertEquals("TORA - 27R", 3884, logicalRunway2.getRecalculatedParameters().getTORA(), 0.1);
        // Checking TODA.
        assertEquals("TODA - 27R", 3962, logicalRunway2.getRecalculatedParameters().getTODA(), 0.1);
        // Checking ASDA.
        assertEquals("ASDA - 27R", 3884, logicalRunway2.getRecalculatedParameters().getASDA(), 0.1);
        // Checking LDA.
        assertEquals("LDA - 27R", 3884, logicalRunway2.getRecalculatedParameters().getLDA(), 0.1);

    }

    /** Testing edge case 2: Obstacle is outside the 75m limit from the centreline (-80m) */
    @Test(timeout = 5000)
    @DisplayName("Test Scenario 6 for obstacle far away from centreline")
    public void test6() throws Exception {

        // Creating the logical runways with their appropriate runway parameters.
        RunwayParameters runwayParameters1 = new RunwayParameters(3902, 3902, 3902, 3595);
        LogicalRunway logicalRunway1 = new LogicalRunway(9, 306, 'L', runwayParameters1);
        RunwayParameters runwayParameters2 = new RunwayParameters(3884, 3962, 3884, 3884);
        LogicalRunway logicalRunway2 = new LogicalRunway(27, 0, 'R', runwayParameters2);

        // Creating the obstacle, runway and calculator.
        Obstacle obstacle1 = new Obstacle("TestObstacle1", "For testing", 0.0, 0.0, 20.0, -80.0,
                3546.0, 50.0);
        Runway runway1 = new Runway("TestRunway1");
        runway1.setObstacle(obstacle1);
        runway1.addRunway(logicalRunway1);
        runway1.addRunway(logicalRunway2);
        Calculator calculatorTest1 = new Calculator(runway1);

        // Recalculating parameters.
        calculatorTest1.recalculateRunwayParameters();

        // Testing results.
        System.out.println("Edge case: 09L (Take Off Towards, Landing Towards)");
        // Checking TORA.
        assertEquals("TORA - 09L", 3902, logicalRunway1.getRecalculatedParameters().getTORA(), 0.1);
        // Checking TODA.
        assertEquals("TODA - 09L", 3902, logicalRunway1.getRecalculatedParameters().getTODA(), 0.1);
        // Checking ASDA.
        assertEquals("ASDA - 09L", 3902, logicalRunway1.getRecalculatedParameters().getASDA(), 0.1);
        // Checking LDA.
        assertEquals("LDA - 09L", 3595, logicalRunway1.getRecalculatedParameters().getLDA(), 0.1);

        System.out.println("Edge case: 27R (Take off Away, Landing Over)");
        // Checking TORA.
        assertEquals("TORA - 27R", 3884, logicalRunway2.getRecalculatedParameters().getTORA(), 0.1);
        // Checking TODA.
        assertEquals("TODA - 27R", 3962, logicalRunway2.getRecalculatedParameters().getTODA(), 0.1);
        // Checking ASDA.
        assertEquals("ASDA - 27R", 3884, logicalRunway2.getRecalculatedParameters().getASDA(), 0.1);
        // Checking LDA.
        assertEquals("LDA - 27R", 3884, logicalRunway2.getRecalculatedParameters().getLDA(), 0.1);

    }

    /** Testing edge case 3: Obstacle is outside the 60m limit from the runway (70m) */
    @Test(timeout = 5000)
    @DisplayName("Test Scenario 7 for obstacle far away from runway")
    public void test7() throws Exception {

        // Creating the logical runways with their appropriate runway parameters.
        RunwayParameters runwayParameters1 = new RunwayParameters(3902, 3902, 3902, 3595);
        LogicalRunway logicalRunway1 = new LogicalRunway(9, 306, 'L', runwayParameters1);
        RunwayParameters runwayParameters2 = new RunwayParameters(3884, 3962, 3884, 3884);
        LogicalRunway logicalRunway2 = new LogicalRunway(27, 0, 'R', runwayParameters2);

        // Creating the obstacle, runway and calculator.
        Obstacle obstacle1 = new Obstacle("TestObstacle1", "For testing", 0.0, 0.0, 20.0, 20.0,
                3546.0, -70.0);
        Runway runway1 = new Runway("TestRunway1");
        runway1.setObstacle(obstacle1);
        runway1.addRunway(logicalRunway1);
        runway1.addRunway(logicalRunway2);
        Calculator calculatorTest1 = new Calculator(runway1);

        // Recalculating parameters.
        calculatorTest1.recalculateRunwayParameters();

        // Testing results.
        System.out.println("Edge case: 09L (Take Off Towards, Landing Towards)");
        // Checking TORA.
        assertEquals("TORA - 09L", 3902, logicalRunway1.getRecalculatedParameters().getTORA(), 0.1);
        // Checking TODA.
        assertEquals("TODA - 09L", 3902, logicalRunway1.getRecalculatedParameters().getTODA(), 0.1);
        // Checking ASDA.
        assertEquals("ASDA - 09L", 3902, logicalRunway1.getRecalculatedParameters().getASDA(), 0.1);
        // Checking LDA.
        assertEquals("LDA - 09L", 3595, logicalRunway1.getRecalculatedParameters().getLDA(), 0.1);

        System.out.println("Edge case: 27R (Take off Away, Landing Over)");
        // Checking TORA.
        assertEquals("TORA - 27R", 3884, logicalRunway2.getRecalculatedParameters().getTORA(), 0.1);
        // Checking TODA.
        assertEquals("TODA - 27R", 3962, logicalRunway2.getRecalculatedParameters().getTODA(), 0.1);
        // Checking ASDA.
        assertEquals("ASDA - 27R", 3884, logicalRunway2.getRecalculatedParameters().getASDA(), 0.1);
        // Checking LDA.
        assertEquals("LDA - 27R", 3884, logicalRunway2.getRecalculatedParameters().getLDA(), 0.1);

    }

    /** Testing edge case 4: Obstacle is outside the 60m limit from the runway (70m) */
    @Test(timeout = 5000)
    @DisplayName("Test Scenario 8 for obstacle far away from runway")
    public void test8() throws Exception {

        // Creating the logical runways with their appropriate runway parameters.
        RunwayParameters runwayParameters1 = new RunwayParameters(3902, 3902, 3902, 3595);
        LogicalRunway logicalRunway1 = new LogicalRunway(9, 306, 'L', runwayParameters1);
        RunwayParameters runwayParameters2 = new RunwayParameters(3884, 3962, 3884, 3884);
        LogicalRunway logicalRunway2 = new LogicalRunway(27, 0, 'R', runwayParameters2);

        // Creating the obstacle, runway and calculator.
        Obstacle obstacle1 = new Obstacle("TestObstacle1", "For testing", 0.0, 0.0, 20.0, 20.0,
                -70.0, 3546.0);
        Runway runway1 = new Runway("TestRunway1");
        runway1.setObstacle(obstacle1);
        runway1.addRunway(logicalRunway1);
        runway1.addRunway(logicalRunway2);
        Calculator calculatorTest1 = new Calculator(runway1);

        // Recalculating parameters.
        calculatorTest1.recalculateRunwayParameters();

        // Testing results.
        System.out.println("Edge case: 09L (Take Off Towards, Landing Towards)");
        // Checking TORA.
        assertEquals("TORA - 09L", 3902, logicalRunway1.getRecalculatedParameters().getTORA(), 0.1);
        // Checking TODA.
        assertEquals("TODA - 09L", 3902, logicalRunway1.getRecalculatedParameters().getTODA(), 0.1);
        // Checking ASDA.
        assertEquals("ASDA - 09L", 3902, logicalRunway1.getRecalculatedParameters().getASDA(), 0.1);
        // Checking LDA.
        assertEquals("LDA - 09L", 3595, logicalRunway1.getRecalculatedParameters().getLDA(), 0.1);

        System.out.println("Edge case: 27R (Take off Away, Landing Over)");
        // Checking TORA.
        assertEquals("TORA - 27R", 3884, logicalRunway2.getRecalculatedParameters().getTORA(), 0.1);
        // Checking TODA.
        assertEquals("TODA - 27R", 3962, logicalRunway2.getRecalculatedParameters().getTODA(), 0.1);
        // Checking ASDA.
        assertEquals("ASDA - 27R", 3884, logicalRunway2.getRecalculatedParameters().getASDA(), 0.1);
        // Checking LDA.
        assertEquals("LDA - 27R", 3884, logicalRunway2.getRecalculatedParameters().getLDA(), 0.1);

    }

    /** Testing the breakdown of: Take Off Away, Landing Over, Take Off Towards, Landing Towards */
    @Test(timeout = 5000)
    @DisplayName("Testing the breakdown from test Scenario 1 from Heathrow_Scenarios.pdf")
    public void test9() throws Exception {

        // Creating the logical runways with their appropriate runway parameters.
        RunwayParameters runwayParameters1 = new RunwayParameters(3902, 3902, 3902, 3595);
        LogicalRunway logicalRunway1 = new LogicalRunway(9, 306, 'L', runwayParameters1);
        RunwayParameters runwayParameters2 = new RunwayParameters(3884, 3962, 3884, 3884);
        LogicalRunway logicalRunway2 = new LogicalRunway(27, 0, 'R', runwayParameters2);

        // Creating the obstacle, runway and calculator.
        Obstacle obstacle1 = new Obstacle("TestObstacle1", "For testing", 0.0, 0.0, 12.0, 0.0,
                -50.0, 3646.0);
        Runway runway1 = new Runway("TestRunway1");
        runway1.setObstacle(obstacle1);
        runway1.addRunway(logicalRunway1);
        runway1.addRunway(logicalRunway2);
        Calculator calculatorTest1 = new Calculator(runway1);

        // Recalculating parameters.
        calculatorTest1.recalculateRunwayParameters();

        // Testing results.
        System.out.println("Breakdown of 09L (Take Off Away, Landing Over)");
        // Checking Landing Over for 09L.
        assertEquals("09L_LO",
                "RLDA=LDA-DistancefromThreshold-StripEnd-SlopeCalculation=3595.0-60.0-(12.0*50)=2985.0",
                calculatorTest1.getBreakDown("09L_LO").replaceAll("\\s+",""));

        // Checking Take Away for 09L.
        assertEquals("09L_TA",
                "RTORA=TORA-BlastProtection-DistancefromThreshold-DisplacedThreshold=" +
                        "3902.0-300.0--50.0-306.0=3346.0RASDA=RTORA+STOPWAY=3346.0+0.0RTODA=RTORA+CLEARWAY=3346.0+0.0",
                calculatorTest1.getBreakDown("09L_TA").replaceAll("\\s+",""));

        System.out.println("Breakdown of 27R (Take Off Towards, Landing Towards)");

        // Checking Take Off Towards for 27R.
        assertEquals("27R_TT",
                "RTORA=DistancefromThreshold+DisplacedThreshold-SlopeCalculation-StripEnd" +
                        "=3646.0+0.0-(12.0*50)-60.0=2986.0RASDA=RTORA=2986.0RTODA=RTORA=2986.0",
                calculatorTest1.getBreakDown("27R_TT").replaceAll("\\s+",""));

        // Checking Landing Towards for 27R.
        assertEquals("27R_LT",
                "RLDA=DistancefromThreshold-RESA-StripEnd=3646.0-240.0-60.0=3346.0",
                calculatorTest1.getBreakDown("27R_LT").replaceAll("\\s+",""));

    }
}
