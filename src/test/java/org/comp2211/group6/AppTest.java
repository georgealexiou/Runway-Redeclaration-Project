package org.comp2211.group6;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import org.comp2211.group6.Model.*;
import org.comp2211.group6.Controller.*;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

    private RunwayParameters runwayParameters1;
    private LogicalRunway logicalRunway1;
    private Obstacle obstacle1;
    private Runway runway1;
    private Calculator calculatorTest1;

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue(true);
    }

    @Test
    public void test1()
    {
        this.runwayParameters1 = new RunwayParameters(3660,3660,3660,3353);
        this.logicalRunway1 = new LogicalRunway(9, 307,'R',runwayParameters1);
        this.obstacle1 = new Obstacle("TestObstacle1", "For testing", 0.0,0.0,12.0, 0.0, 0.0, 0.0);
        this.runway1 = new Runway("TestRunway1");
        this.runway1.setObstacle(this.obstacle1);
        this.runway1.addRunway(this.logicalRunway1);
        this.calculatorTest1 = new Calculator(this.runway1);
        this.calculatorTest1.recalculateRunwayParameters();

        // Checking TORA.
        assertEquals(3346,this.runwayParameters1.getTORA(), 0.1);
        // Checking TODA.
        assertEquals(3346,this.runwayParameters1.getTODA(), 0.1);
        // Checking ASDA.
        assertEquals(3346,this.runwayParameters1.getASDA(), 0.1);
        // Checking LDA.
        assertEquals(2985,this.runwayParameters1.getLDA(), 0.1);
    }
}
