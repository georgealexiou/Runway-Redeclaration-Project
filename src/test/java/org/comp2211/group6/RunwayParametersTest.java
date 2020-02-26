package org.comp2211.group6;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.comp2211.group6.Model.RunwayParameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class RunwayParametersTest {
	RunwayParameters runwayParam;
	
	private double tora;
	private double toda;
	private double asda;
	private double lda;
	private String errorMsg;
	private Class errorClass;
	
	static String msgTORA = ("Error. Invalid TORA. Only positive numbers are allowed.");
	static String msgTODA = ("Error. Invalid TODA. Only positive numbers are allowed.");
	static String msgASDA = ("Error. Invalid ASDA. Only positive numbers are allowed.");
	static String msgLDA = ("Error. Invalid LDA. Only positive numbers are allowed.");
	
	public RunwayParametersTest(double tora, double toda, 
								double asda, double lda, 
								Class errorClass, String errorMsg) {
		this.tora = tora;
		this.toda = toda;
		this.asda = asda;
		this.lda = lda;
		this.errorMsg = errorMsg;
		this.errorClass = errorClass;

	}
	
	/**
	 * Data for testing
	 * TORA, TODA, ASDA, LDA, expected exception, expected error message
	 */
	@Parameters(name = "{index}: Test Runway Parameters Validity (tora:{0} toda:{1} asda:{2} lda:{3}) throws {4} with message {5}")
	public static Collection<Object[]> testData() {
		return Arrays.asList(new Object[][] {
			{0, 0, 0, 0, IllegalArgumentException.class, msgTORA},
			{-1, 0, 0, 0, IllegalArgumentException.class, msgTORA},
			{1, -1, 1, 1, IllegalArgumentException.class, msgTODA},
			{1, 1, -1, 0, IllegalArgumentException.class, msgASDA},
			{1, 1, 1, -1, IllegalArgumentException.class, msgLDA},
			{-1, -1, -1, -1, IllegalArgumentException.class, msgTORA},
			{1, 1, 1, 1, null, null},
			{3660, 3660, 3660, 3660, null, null},
			{3902, 3902, 3902, 3595, null, null}
			});
	}
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	/** Test appropriate exception is threw and give exact error message */
	@Test
	public void testExceptionThrow() {
		if(errorClass != null)
			exception.expect(errorClass);
		if(errorMsg != null)
			exception.expectMessage(errorMsg);
		
		runwayParam = new RunwayParameters(tora, toda, asda, lda);
	}
	
	/** Test getParameters() outputs correct value. */
	@Test
	public void testGetParameters() {
		if(errorClass == null) {
			runwayParam = new RunwayParameters(tora,toda,asda,lda);
			assertTrue(printTrace("TORA", tora, runwayParam.getTORA()), tora - runwayParam.getTORA() == 0);
			assertTrue(printTrace("TODA", toda, runwayParam.getTODA()), toda - runwayParam.getTODA() == 0);
			assertTrue(printTrace("ASDA", asda, runwayParam.getASDA()), asda - runwayParam.getASDA() == 0);
			assertTrue(printTrace("LDA", lda, runwayParam.getLDA()), lda - runwayParam.getLDA() == 0);
		}
	}
	
	/** pretty showing test failure trace */
	public String printTrace(String p, double expected, double actual) {
		return p + " Getter Test Failed. Expecting: " + String.valueOf(expected) + " Actual: " + String.valueOf(actual);
	}
}
