package org.comp2211.group6;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.Obstacle;
import org.comp2211.group6.Model.Runway;
import org.comp2211.group6.Model.RunwayParameters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class RunwayTest {
    Runway runway;

    private String name;
    private Obstacle obstacle;
    private LogicalRunway logical1;
    private LogicalRunway logical2;
    private LogicalRunway logical3;
    private LogicalRunway logicalEx;
    private String errorMsg;
    private static String errorMsg1 =
                    "Error. Logical runway cannot be added to this runway, which can have three logical runways at most.";
    private static String errorMsg2 =
                    "Error. Invalid logical runway to be added to runway, cannot be null.";
    private static String errorMsg3 = "Invalid name for logical runway - must be unique";

    public RunwayTest(String name, Obstacle obstacle, LogicalRunway logical1,
                    LogicalRunway logical2, LogicalRunway logical3, LogicalRunway logicalEx,
                    String errorMsg) {
        this.name = name;
        this.logical1 = logical1;
        this.logical2 = logical2;
        this.logical3 = logical3;
        this.logicalEx = logicalEx;
        this.errorMsg = errorMsg;
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();


    @Parameters(name = "{index}: Test RunwayWay Config (name:{0} obstacle:{1} logicalRunways:{2}, {3}, {4}, {5}(may throw exception)) error message {6}.")
    public static Collection<Object[]> testData() {
        RunwayParameters params = new RunwayParameters(1111, 2222, 3333, 4444);
        return Arrays.asList(new Object[][] {
                        {"Runway1", new Obstacle("Obs1", "A380", 333, 555, 50, 123, 747, 145),
                                        new LogicalRunway(9, 111, 'L', params),
                                        new LogicalRunway(27, 111, 'R', params),
                                        new LogicalRunway(9, 111, 'C', params),
                                        new LogicalRunway(27, 111, 'C', params), errorMsg3},
                        {"Runway1", new Obstacle("Obs1", "A350", 566, 267, 2356, 256, 168, 444),
                                        new LogicalRunway(9, 111, 'L', params),
                                        new LogicalRunway(27, 111, 'R', params),
                                        new LogicalRunway(27, 111, 'C', params), null, null},
                        {"Runway1", new Obstacle("Obs3", "A310", 4442, 3522, 3523, 2727, 572, 2711),
                                        new LogicalRunway(9, 111, 'R', params),
                                        new LogicalRunway(27, 111, 'L', params), null, null,
                                        errorMsg2},
                        {"Runway1", new Obstacle("Obs4", "A320", 3311, 555, 50, 123, 747, 3795),
                                        new LogicalRunway(9, 111, 'R', params),
                                        new LogicalRunway(27, 111, 'L', params),
                                        new LogicalRunway(9, 111, 'C', params), null, null},
                        {"Runway1", new Obstacle("Obs5", "A319", 632, 555, 50, 123, 747, 589), null,
                                        new LogicalRunway(27, 111, 'R', params),
                                        new LogicalRunway(9, 111, 'C', params), null, errorMsg2},
                        {"Runway1", new Obstacle("Obs6", "A321", 779, 555, 50, 123, 747, 689),
                                        new LogicalRunway(9, 111, 'L', params), null,
                                        new LogicalRunway(9, 111, 'C', params), null, errorMsg2},
                        {"Runway1", new Obstacle("Obs7", "A340", 346, 555, 50, 123, 747, 367),
                                        new LogicalRunway(9, 111, 'L', params), null, null, null,
                                        errorMsg2},
                        {"Runway1", new Obstacle("Obs8", "BOEING787", 33, 555, 50, 123, 747, 690),
                                        new LogicalRunway(9, 111, 'L', params),
                                        new LogicalRunway(27, 111, 'R', params), null, null,
                                        errorMsg2},
                        {"Runway1", new Obstacle("Obs9", "BOEING737-200", 89, 54, 503, 4123, 334,
                                        2678), null, new LogicalRunway(27, 111, 'R', params),
                                        new LogicalRunway(9, 111, 'C', params), null, errorMsg2},
                        {"Runway1", new Obstacle("Obs10", "BOEING737-700", 37, 456, 520, 6893, 422,
                                        3578), new LogicalRunway(9, 111, 'R', params),
                                        new LogicalRunway(27, 111, 'L', params),
                                        new LogicalRunway(9, 111, 'C', params), null, null}});
    }

    @Before
    public void constructRunway() {
        runway = new Runway(name);
    }

    @Test
    public void testObstacle() {
        assertNull("getObstacle() should return null when no obstacle is set.",
                        runway.getObstacle());

        runway.setObstacle(obstacle);
        assertEquals("Runway Obstacle Test Failed." + "Expected: " + printObstacle(obstacle)
                        + " Actual: " + printObstacle(runway.getObstacle()), obstacle,
                        runway.getObstacle());

        runway.setObstacle(null);
        assertNull("getObstacle() should return null after resetting obstacle to null.",
                        runway.getObstacle());
    }

    /** test for addRunway() and getLogicalRunways() */
    @Test
    public void testConfigLogicalRunway() throws Exception {
        assertTrue("Runway should have no logical runway when none has been added.",
                        runway.getLogicalRunways().isEmpty());

        if (logical1 == null | logical2 == null | logical3 == null)
            exception.expect(IllegalArgumentException.class);

        if (errorMsg != null)
            exception.expectMessage(errorMsg);

        runway.addRunway(logical1);
        assertTrue("Runway should contain logical runway: ",
                        runway.getLogicalRunways().contains(logical1));
        runway.addRunway(logical2);
        assertTrue("Runway should contain logical runway: ",
                        runway.getLogicalRunways().contains(logical2));
        runway.addRunway(logical3);
        assertTrue("Runway should contain logical runway: ",
                        runway.getLogicalRunways().contains(logical3));

        List<LogicalRunway> logicalRunways =
                        new ArrayList<LogicalRunway>(Arrays.asList(logical1, logical2, logical3));
        assertTrue("addRunway() Test Failed.", logicalRunways.equals(runway.getLogicalRunways()));

        if (logicalEx != null)
            runway.addRunway(logicalEx);
    }

    public String printObstacle(Obstacle obstacle) {
        if (obstacle != null)
            return "name: " + obstacle.getName() + " description: " + obstacle.getDescription()
                            + " length: " + obstacle.getLength() + " width: " + obstacle.getWidth()
                            + " height: " + obstacle.getHeight() + " distanceToCL: "
                            + obstacle.getDistanceToCentreLine() + " distanceFromLeft: "
                            + obstacle.getDistanceFromLeft() + " distanceFromRight: "
                            + obstacle.getDistanceFromRight();

        return null;
    }

}
