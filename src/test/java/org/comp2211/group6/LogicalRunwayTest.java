package org.comp2211.group6;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.comp2211.group6.Model.LogicalRunway;
import org.comp2211.group6.Model.RunwayParameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;


@RunWith(JUnitParamsRunner.class)
public class LogicalRunwayTest{
	LogicalRunway logicalRunway;
	RunwayParameters sampleParams = new RunwayParameters(3660, 3660, 3660, 3660);
	
	/**
	 * msgH: error message for invalid heading.
	 * msgDT: error message for invalid displaced threshold.
	 * msgP: error message for invalid position.
	 * msgRP: error message for getRecalculatedParameters(), no recalculation is performed.
	 * msgSRP: error message for setRecalculatedParameter(String, double), invalid parameter name.
	 */
	private String msgH = "Error. Invalid heading, which should be an Integer between 1 and 36.";
	private String msgDT = "Error. Invalid displaced threshold, which should be a positive number.";
	private String msgP = "Error. Invalid position, which can be 'L' or 'R' or 'C'.";
	private String msgRP = "Error. No recalculated runway parameters can be returned.";
	private String msgSRP = "Failed to set recalculated parameter. Invalid name of parameter.";
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	/**
	 * TEST METHODS BELOW. AND THE RELATED TEST DATA IS ASSIGNED (method = "testData").
	 */
	
	/** Test the validity of input parameters of Constructor with Displaced Threshold */
	@Test
	@Parameters(method = "testDataForExceptionWithThreshold")
	public void testExceptionWithThreshold(int heading, double displacedThreshold, char position, 
											Class errorClass, String errorMsg) {
		if(errorClass != null)
			exception.expect(errorClass);
		if(errorMsg != null)
			exception.expectMessage(errorMsg);
		
		logicalRunway = new LogicalRunway(heading, displacedThreshold, position, sampleParams);
	}
	
	/** Test the validity of input parameters of Constructor without Displaced Threshold */
	@Test
	@Parameters(method = "testDataForExceptionWithoutThreshold")
	public void testExceptionWithoutThreshold(int heading, char position, 
												Class errorClass, String errorMsg) {
		if(errorClass != null)
			exception.expect(errorClass);
		if(errorMsg != null)
			exception.expectMessage(errorMsg);
		
		logicalRunway = new LogicalRunway(heading, position, sampleParams);
	}
	
	/** Test getIdentifier() returns proper combination of heading and position */
	@Test
	@Parameters(method = "testDataForGetIdentifier")
	public void testGetIdentifier(int heading, char position, String expectedID) {
		logicalRunway = new LogicalRunway(heading, position, sampleParams);
		assertEquals("getIdentifier() Test Failed. Expected: " + expectedID 
				+ " Actual: " + logicalRunway.getIdentifier(), 
					expectedID, logicalRunway.getIdentifier());
	}
	
	/** Test getParameters() returns correct RunwayParameters (the original one) */
	@Test
	@Parameters(method = "testDataForGetParameters")
	public void testGetParameters(int heading, char position, RunwayParameters params) {
		logicalRunway = new LogicalRunway(heading, position, params);
		
		assertEquals("getParameters() Test Failed. Expected: " + printParameters(params)
					+ " Actual: " + printParameters(logicalRunway.getParameters()), 
					params, logicalRunway.getParameters());
	}
	
	/**
	 * Test setRecalculatedParameters(p) & getRecalculatedParameters() 
	 * if both are called sequentially returns the object p passed in;
	 * if get method is called without set, returns null;
	 */
	@Test
	@Parameters(method = "testDataForRecalculations")
	public void testRecalculatedParameters(LogicalRunway logicalRunway, 
											RunwayParameters paramsRecalculated) {
		assertNull("Unset RecalculatedParameters Returns Null Test Failed.", logicalRunway.getRecalculatedParameters());
		
		logicalRunway.setRecalculatedParameters(paramsRecalculated);
		
		assertEquals("RecalculatedParameters() Test Failed, including setter and getter."
				+ "Expected: " + printParameters(paramsRecalculated)
				+ " Actual: " + printParameters(logicalRunway.getRecalculatedParameters()), 
				paramsRecalculated, logicalRunway.getRecalculatedParameters());
	}
	
	/**
	 * Test set individual recalculated parameter method: setRecalculatedParameter(String, double)
	 * @param logicalRunway to set recalculated parameters
	 * @param name: name of parameter to set recalculated value
	 * @param params: value to assign
	 */
	@Test
	@Parameters(method = "testDataForSetIndividualRecalculatedParameter")
	public void testSetIndividualRecalculatedParameter(LogicalRunway logicalRunway, String name, 
														double params, RunwayParameters expected,
														Class errorClass, String errorMsg) {
		if(errorClass != null) 
			exception.expect(IllegalArgumentException.class);
		if(errorMsg != null)
			exception.expectMessage(msgSRP);
		
		logicalRunway.setRecalculatedParameter(name, params);
		
		if(errorClass != null)
			assertNull("setRecalculatedParameter(String, double) exception Test Failed."
					+ "Expected: null"  
					+ " Actual: " + printParameters(logicalRunway.getRecalculatedParameters()), 
					logicalRunway.getRecalculatedParameters());
		
		if(errorClass == null)
			assertTrue("setRecalculatedParameter(String, double) Test Failed."
						+ "Expected: " + printParameters(expected)
						+ " Actual: " + printParameters(logicalRunway.getRecalculatedParameters()), 
						equalRunwayParameters(expected, logicalRunway.getRecalculatedParameters()));
	}
	
	/**	method to check expected RunwayParameters and actual one has equal parameters */
	public boolean equalRunwayParameters(RunwayParameters expected, RunwayParameters actual) {
		if(expected.getTORA() == actual.getTORA() 
		&& expected.getTODA() == actual.getTODA()
		&& expected.getASDA() == actual.getASDA()
		&& expected.getLDA() == actual.getLDA())
			return true;
		
		return false;
	}
	
	/** @return a string containing values of TORA, TODA, ASDA, LDA */
	  public String printParameters(RunwayParameters params) {
	    if(params != null)
	      return "TORA: " + String.valueOf(params.getTORA()) 
	      + ", TODA: " + String.valueOf(params.getTODA()) 
	      + ", ASDA: " + String.valueOf(params.getASDA())
	      + ", LDA: " + String.valueOf(params.getLDA());
	  
	    return null;
	  }
	
	/**-------------------------- TEST DATA BELOW --------------------------------------*/
	
	private Object[] testDataForExceptionWithThreshold(){
		return new Object[] {
			new Object[] {0, 307, 'L', IllegalArgumentException.class, msgH},
			new Object[] {-1, 307, 'R', IllegalArgumentException.class, msgH},
			new Object[] {37, 456, 'N', IllegalArgumentException.class, msgH},
			new Object[] {1, 0, 'L', IllegalArgumentException.class, msgDT},
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
			new Object[] {36, 55, 'C', null, null}
			};
	}
	
	private Object[] testDataForExceptionWithoutThreshold(){
		return new Object[][] {
			new Object[] {0, 'L', IllegalArgumentException.class, msgH},
			new Object[] {-1, 'R', IllegalArgumentException.class, msgH},
			new Object[] {37, 'N', IllegalArgumentException.class, msgH},
			new Object[] {1, 'L', null, null},
			new Object[] {10, 'L', null, null},
			new Object[] {36,'A', IllegalArgumentException.class, msgP},
			new Object[] {36, 'B', IllegalArgumentException.class, msgP},
			new Object[] {36, 'C', null, null},
			new Object[] {36, 'D', IllegalArgumentException.class, msgP},
			new Object[] {36, 'E', IllegalArgumentException.class, msgP},
			new Object[] {36, 'F', IllegalArgumentException.class, msgP},
			new Object[] {36, 'G', IllegalArgumentException.class, msgP},
			new Object[] {36, 'H', IllegalArgumentException.class, msgP},
			new Object[] {36, 'I', IllegalArgumentException.class, msgP},
			new Object[] {36, 'J', IllegalArgumentException.class, msgP},
			new Object[] {36, 'K', IllegalArgumentException.class, msgP},
			new Object[] {36, 'L', null, null},
			new Object[] {36, 'M', IllegalArgumentException.class, msgP},
			new Object[] {36, 'N', IllegalArgumentException.class, msgP},
			new Object[] {36, 'O', IllegalArgumentException.class, msgP},
			new Object[] {36, 'P', IllegalArgumentException.class, msgP},
			new Object[] {36, 'Q', IllegalArgumentException.class, msgP},
			new Object[] {36, 'R', null, null},
			new Object[] {36, 'S', IllegalArgumentException.class, msgP},
			new Object[] {36, 'T', IllegalArgumentException.class, msgP},
			new Object[] {36, 'U', IllegalArgumentException.class, msgP},
			new Object[] {36, 'V', IllegalArgumentException.class, msgP},
			new Object[] {36, 'W', IllegalArgumentException.class, msgP},
			new Object[] {36, 'X', IllegalArgumentException.class, msgP},
			new Object[] {36, 'Y', IllegalArgumentException.class, msgP},
			new Object[] {36, 'Z', IllegalArgumentException.class, msgP},
			new Object[] {1, 'L', null, null},
			new Object[] {36, 'C', null, null}
			};
	}
	
	private Object[] testDataForGetIdentifier(){
		return new Object[] {
			new Object[] {1,'L', "01L"},
			new Object[] {9,'C', "09C"},
			new Object[] {10,'L', "10L"},
			new Object[]{11,'C', "11C"},
			new Object[]{36,'R', "36R"}
			};
	}
	
	private Object[] testDataForGetParameters(){
		return new Object[] {
			new Object[] {1,'L', new RunwayParameters(1, 1, 1, 1)},
			new Object[] {9,'C', new RunwayParameters(3660, 3660, 3660, 3660)},
			new Object[] {10,'L', new RunwayParameters(1234, 1111, 4321, 2222)},
			new Object[] {11,'C', new RunwayParameters(1111, 2222, 3333, 4444)},
			new Object[] {36,'R', new RunwayParameters(123, 234, 345, 567)}
			};
	}
	
	private Object[] testDataForRecalculations(){
		return new Object[] {
			new Object[] {new LogicalRunway(1,'L', new RunwayParameters(1, 1, 1, 1)), new RunwayParameters(3660, 3660, 3660, 3660)},
			new Object[] {new LogicalRunway(9,'C', new RunwayParameters(3660, 3660, 3660, 3660)), new RunwayParameters(34, 43, 56, 65)},
			new Object[] {new LogicalRunway(10,'L', new RunwayParameters(1234, 1111, 4321, 2222)), new RunwayParameters(789, 425, 5657, 5655)},
			new Object[] {new LogicalRunway(11,'C', new RunwayParameters(1111, 2222, 3333, 4444)), new RunwayParameters(1524, 6352, 8643, 2652)},
			new Object[] {new LogicalRunway(36,'R', new RunwayParameters(123, 234, 345, 567)), new RunwayParameters(4143, 7357, 3155, 3357)}
			};
	}
	
	private Object[] testDataForSetIndividualRecalculatedParameter(){
		return new Object[] {
			new Object[] {new LogicalRunway(1,'L', new RunwayParameters(1, 1, 1, 1)), "RTORA", 3660, new RunwayParameters(3660, 1, 1, 1), null, null},
			new Object[] {new LogicalRunway(9,'C', new RunwayParameters(3660, 3660, 3660, 3660)), "RTODA", 5678, new RunwayParameters(3660, 5678, 3660, 3660), null, null},
			new Object[] {new LogicalRunway(10,'L', new RunwayParameters(1234, 1111, 4321, 2222)), "RASDA", 888, new RunwayParameters(1234, 1111, 888, 2222), null ,null},
			new Object[] {new LogicalRunway(11,'C', new RunwayParameters(1111, 2222, 3333, 4444)), "RLDA", 9090, new RunwayParameters(1111, 2222, 3333, 9090), null, null},
			new Object[] {new LogicalRunway(36,'R', new RunwayParameters(123, 234, 345, 567)), "INVALID", 777, null, IllegalArgumentException.class, msgSRP}
			};
	}
}
