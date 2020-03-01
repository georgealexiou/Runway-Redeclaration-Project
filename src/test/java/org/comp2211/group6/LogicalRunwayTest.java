package org.comp2211.group6;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.RunwayParameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;


@RunWith(JUnitParamsRunner.class)
public class LogicalRunwayTest {
    LogicalRunway logicalRunway;
    RunwayParameters sampleParams = new RunwayParameters(3660, 3660, 3660, 3660);

    /**
     * msgH: error message for invalid heading. msgDT: error message for invalid displaced
     * threshold.m msgP: error message for invalid position.
     */

    private String msgH = "Invalid heading. Should be int 1-36";
    private String msgDT = "Displaced Threashold can't be negative";
    private String msgP = "Invalid position. Can be 'L','R','C'";

    @Rule
    public ExpectedException exception = ExpectedException.none();

    /**
     * TEST METHODS BELOW. AND THE RELATED TEST DATA IS ASSIGNED (method = "testData").
     */

    /** Test the validity of input parameters of Constructor with Displaced Threshold */
    @Test
    @Parameters(method = "testDataForException")
    public void testException(int heading, double displacedThreshold, char position,
                    Class errorClass, String errorMsg) {
        if (errorClass != null)
            exception.expect(errorClass);
        if (errorMsg != null)
            exception.expectMessage(errorMsg);

        logicalRunway = new LogicalRunway(heading, displacedThreshold, position, sampleParams);
    }

    /** Test getIdentifier() returns proper combination of heading and position */
    @Test
    @Parameters(method = "testDataForGetIdentifier")
    public void testGetIdentifier(int heading, double threshold, char position, String expectedID) {
        logicalRunway = new LogicalRunway(heading, threshold, position, sampleParams);
        assertEquals("getIdentifier() Test Failed. Expected: " + expectedID + " Actual: "
                        + logicalRunway.getIdentifier(), expectedID, logicalRunway.getIdentifier());
    }

    /** Test getParameters() returns correct RunwayParameters (the original one) */
    @Test
    @Parameters(method = "testDataForGetParameters")
    public void testGetParameters(int heading, double threshold, char position,
                    RunwayParameters params) {
        logicalRunway = new LogicalRunway(heading, threshold, position, params);
        assertEquals("getParameters() Test Failed. Expected: " + printParameters(params)
                        + " Actual: " + printParameters(logicalRunway.getParameters()), params,
                        logicalRunway.getParameters());
    }

    /**
     * Test setRecalculatedParameters(p) & getRecalculatedParameters() if both are called
     * sequentially returns the object p passed in; if get method is called without set, returns
     * null;
     */
    @Test
    @Parameters(method = "testDataForRecalculations")
    public void testRecalculatedParameters(LogicalRunway logicalRunway,
                    RunwayParameters paramsRecalculated) {

        logicalRunway.setRecalculatedParameters(paramsRecalculated);

        assertEquals("RecalculatedParameters() Test Failed, including setter and getter."
                        + "Expected: " + printParameters(paramsRecalculated) + " Actual: "
                        + printParameters(logicalRunway.getRecalculatedParameters()),
                        paramsRecalculated, logicalRunway.getRecalculatedParameters());
    }

    /**
     * Test set individual recalculated parameter method: setRecalculatedParameter(String, double)
     * 
     * @param logicalRunway to set recalculated parameters
     * @param name: name of parameter to set recalculated value
     * @param params: value to assign
     */
    @Test
    @Parameters(method = "testDataForSetIndividualRecalculatedParameter")
    public void testSetIndividualRecalculatedParameter(LogicalRunway logicalRunway, double tora,
                    double toda, double asda, double lda, RunwayParameters expected) {
        logicalRunway.getRecalculatedParameters().setTORA(tora);
        logicalRunway.getRecalculatedParameters().setTODA(toda);
        logicalRunway.getRecalculatedParameters().setASDA(asda);
        logicalRunway.getRecalculatedParameters().setLDA(lda);

        assertTrue("Set Individual Recalculated Parameter Test Failed." + "Expected: "
                        + printParameters(expected) + " Actual: "
                        + printParameters(logicalRunway.getRecalculatedParameters()),
                        equalRunwayParameters(expected, logicalRunway.getRecalculatedParameters()));
    }

    /** method to check expected RunwayParameters and actual one has equal parameters */
    public boolean equalRunwayParameters(RunwayParameters expected, RunwayParameters actual) {
        if (expected.getTORA() == actual.getTORA() && expected.getTODA() == actual.getTODA()
                        && expected.getASDA() == actual.getASDA()
                        && expected.getLDA() == actual.getLDA())
            return true;

        return false;
    }

    /** @return a string containing values of TORA, TODA, ASDA, LDA */
    public String printParameters(RunwayParameters params) {
        if (params != null)
            return "TORA: " + String.valueOf(params.getTORA()) + ", TODA: "
                            + String.valueOf(params.getTODA()) + ", ASDA: "
                            + String.valueOf(params.getASDA()) + ", LDA: "
                            + String.valueOf(params.getLDA());

        return null;
    }

    /** -------------------------- TEST DATA BELOW -------------------------------------- */

    private Object[] testDataForException() {
        return new Object[] {new Object[] {0, 307, 'L', IllegalArgumentException.class, msgH},
                        new Object[] {-1, 307, 'R', IllegalArgumentException.class, msgH},
                        new Object[] {37, 456, 'N', IllegalArgumentException.class, msgH},
                        new Object[] {1, 0, 'L', null, null},
                        new Object[] {10, -1, 'L', IllegalArgumentException.class, msgDT},
                        new Object[] {36, 1, 'A', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'B', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'C', null, null},
                        new Object[] {36, 1, 'D', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'E', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'F', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'G', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'H', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'I', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'J', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'K', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'L', null, null},
                        new Object[] {36, 1, 'M', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'N', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'O', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'P', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'Q', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'R', null, null},
                        new Object[] {36, 1, 'S', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'T', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'U', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'V', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'W', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'X', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'Y', IllegalArgumentException.class, msgP},
                        new Object[] {36, 1, 'Z', IllegalArgumentException.class, msgP},
                        new Object[] {1, 1, 'L', null, null},
                        new Object[] {36, 55, 'C', null, null}};
    }

    private Object[] testDataForGetIdentifier() {
        return new Object[] {new Object[] {1, 454, 'L', "01L"}, new Object[] {9, 789, 'C', "09C"},
                        new Object[] {10, 568, 'L', "10L"}, new Object[] {11, 88, 'C', "11C"},
                        new Object[] {36, 99, 'R', "36R"}};
    }

    private Object[] testDataForGetParameters() {
        return new Object[] {new Object[] {1, 78, 'L', new RunwayParameters(1, 1, 1, 1)},
                        new Object[] {9, 90, 'C', new RunwayParameters(3660, 3660, 3660, 3660)},
                        new Object[] {10, 789, 'L', new RunwayParameters(1234, 1111, 4321, 2222)},
                        new Object[] {11, 981, 'C', new RunwayParameters(1111, 2222, 3333, 4444)},
                        new Object[] {36, 475, 'R', new RunwayParameters(123, 234, 345, 567)}};
    }

    private Object[] testDataForRecalculations() {
        return new Object[] {
                        new Object[] {new LogicalRunway(1, 377, 'L',
                                        new RunwayParameters(1, 1, 1, 1)),
                                        new RunwayParameters(3660, 3660, 3660, 3660)},
                        new Object[] {new LogicalRunway(9, 444, 'C',
                                        new RunwayParameters(3660, 3660, 3660, 3660)),
                                        new RunwayParameters(34, 43, 56, 65)},
                        new Object[] {new LogicalRunway(10, 111, 'L',
                                        new RunwayParameters(1234, 1111, 4321, 2222)),
                                        new RunwayParameters(789, 425, 5657, 5655)},
                        new Object[] {new LogicalRunway(11, 333, 'C',
                                        new RunwayParameters(1111, 2222, 3333, 4444)),
                                        new RunwayParameters(1524, 6352, 8643, 2652)},
                        new Object[] {new LogicalRunway(36, 454, 'R',
                                        new RunwayParameters(123, 234, 345, 567)),
                                        new RunwayParameters(4143, 7357, 3155, 3357)}};
    }

    private Object[] testDataForSetIndividualRecalculatedParameter() {
        return new Object[] {
                        new Object[] {new LogicalRunway(1, 434, 'L',
                                        new RunwayParameters(1, 1, 1, 1)), 3660, 3661, 3662, 3663,
                                        new RunwayParameters(3660, 3661, 3662, 3663)},
                        new Object[] {new LogicalRunway(9, 555, 'C',
                                        new RunwayParameters(3660, 3660, 3660, 3660)), 1001, 5678,
                                        3344, 5454, new RunwayParameters(1001, 5678, 3344, 5454)},
                        new Object[] {new LogicalRunway(10, 856, 'L',
                                        new RunwayParameters(1234, 1111, 4321, 2222)), 5621, 1321,
                                        888, 4091, new RunwayParameters(5621, 1321, 888, 4091)},
                        new Object[] {new LogicalRunway(11, 74, 'C',
                                        new RunwayParameters(1111, 2222, 3333, 4444)), 253, 990,
                                        890, 2131, new RunwayParameters(253, 990, 890, 2131)},};
    }
}
