package org.comp2211.group6;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.comp2211.group6.Model.RunwayParameters;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
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
	private static String msg1 = ("Error. Invalid TORA. Only positive numbers are allowed.");
	private static String msg2 = ("Error. Invalid TODA. Only positive numbers are allowed.");
	private static String msg3 = ("Error. Invalid ASDA. Only positive numbers are allowed.");
	private static String msg4 = ("Error. Invalid LDA. Only positive numbers are allowed.");
	
	public RunwayParametersTest(
			double tora, 
			double toda, 
			double asda, 
			double lda, 
			Class errorClass, 
			String errorMsg) {
		this.tora = tora;
		this.toda = toda;
		this.asda = asda;
		this.lda = lda;
		this.errorMsg = errorMsg;
		this.errorClass = errorClass;
	}
	
	@Parameters
	public static Collection testData() {
		return Arrays.asList(new Object[][] {
			{0,0,0,0,IllegalArgumentException.class,msg1},
			{-1,0,0,0,IllegalArgumentException.class,msg1},
			{1,-1,1,1,IllegalArgumentException.class,msg2},
			{1,1,-1,0,IllegalArgumentException.class,msg3},
			{1,1,1,-1,IllegalArgumentException.class,msg4},
			{-1,-1,-1,-1,IllegalArgumentException.class,msg1},
			{1,1,1,1,null,null},
			{3660,3660,3660,3660,null,null},
			{3902,3902,3902,3595,null,null}});
	}
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testExceptionThrow() {
		if(errorClass != null)
			exception.expect(errorClass);
		if(errorMsg != null)
			exception.expectMessage(errorMsg);
		
		runwayParam = new RunwayParameters(tora,toda,asda,lda);
	}
	
	@Test
	public void testGetParameters() {
		if(errorMsg == null) {
			runwayParam = new RunwayParameters(tora,toda,asda,lda);
			assertTrue(tora - runwayParam.getTORA() == 0);
			assertTrue(toda - runwayParam.getTODA() == 0);
			assertTrue(asda - runwayParam.getASDA() == 0);
			assertTrue(lda - runwayParam.getLDA() == 0);
		}
		
	}
}
