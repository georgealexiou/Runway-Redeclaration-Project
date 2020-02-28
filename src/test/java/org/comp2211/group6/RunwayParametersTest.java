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
  private double setTora;
  private double setToda;
  private double setAsda;
  private double setLda;

  static String msgTORA = ("Error. Invalid TORA. Only positive numbers are allowed.");
  static String msgTODA = ("Error. Invalid TODA. Only positive numbers are allowed.");
  static String msgASDA = ("Error. Invalid ASDA. Only positive numbers are allowed.");
  static String msgLDA = ("Error. Invalid LDA. Only positive numbers are allowed.");

  public RunwayParametersTest(double tora, double toda, double asda, double lda, Class errorClass,
      String errorMsg, double setTora, double setToda, double setAsda, double setLda) {
    this.tora = tora;
    this.toda = toda;
    this.asda = asda;
    this.lda = lda;
    this.errorMsg = errorMsg;
    this.errorClass = errorClass;
    this.setTora = setTora;
    this.setToda = setToda;
    this.setAsda = setAsda;
    this.setLda = setLda;
  }

  /**
   * Data for testing TORA, TODA, ASDA, LDA, expected exception, expected error message
   */
  @Parameters(
      name = "{index}: Config (tora:{0} toda:{1} asda:{2} lda:{3}) throws {4} with message {5} Test data for setters: {6},{7},{8},{9}")
  public static Collection<Object[]> testData() {
    return Arrays
        .asList(new Object[][] {{0, 0, 0, 0, IllegalArgumentException.class, msgTORA, 1, 2, 3, 4},
            {-1, 0, 0, 0, IllegalArgumentException.class, msgTORA, 4, 5, 6, 7},
            {1, 0, 1, 1, IllegalArgumentException.class, msgTODA, 2, 4, 6, 8},
            {1, -1, 1, 1, IllegalArgumentException.class, msgTODA, 11, 22, 33, 44},
            {1, 1, 0, 5, IllegalArgumentException.class, msgASDA, 12, 23, 34, 56},
            {1, 1, -1, 0, IllegalArgumentException.class, msgASDA, 14, 41, 23, 32},
            {1, 1, 1, 0, IllegalArgumentException.class, msgLDA, 111, 222, 333, 444},
            {1, 1, 1, -1, IllegalArgumentException.class, msgLDA, 122, 223, 332, 334},
            {-1, -1, -1, -1, IllegalArgumentException.class, msgTORA, 131, 232, 343, 454},
            {1, 1, 1, 1, null, null, 3333, 2222, 1111, 4444},
            {3660, 3660, 3660, 3660, null, null, 234, 267, 567, 9764},
            {3902, 3902, 3902, 3595, null, null, 1234, 2345, 4567, 5657},
            {2577, 7473, 2356, 4567, null, null, 2578, 6251, 3678, 2578}});
  }

  @Rule
  public ExpectedException exception = ExpectedException.none();

  /** Test appropriate exception is threw and give exact error message */
  @Test
  public void testExceptionThrow() {
    if (errorClass != null)
      exception.expect(errorClass);
    if (errorMsg != null)
      exception.expectMessage(errorMsg);

    runwayParam = new RunwayParameters(tora, toda, asda, lda);
  }

  /** Test getParameters() outputs correct value. */
  @Test
  public void testGetParameters() {
    if (errorClass == null) {
      runwayParam = new RunwayParameters(tora, toda, asda, lda);
      assertTrue(printTrace("TORA", "Getter", tora, runwayParam.getTORA()),
          tora - runwayParam.getTORA() == 0);
      assertTrue(printTrace("TODA", "Getter", toda, runwayParam.getTODA()),
          toda - runwayParam.getTODA() == 0);
      assertTrue(printTrace("ASDA", "Getter", asda, runwayParam.getASDA()),
          asda - runwayParam.getASDA() == 0);
      assertTrue(printTrace("LDA", "Getter", lda, runwayParam.getLDA()),
          lda - runwayParam.getLDA() == 0);
    }
  }

  /** Test Individual parameter setters */
  @Test
  public void testSetParameters() {
    if (errorClass == null) {
      runwayParam = new RunwayParameters(tora, toda, asda, lda);
      runwayParam.setTORA(setTora);
      runwayParam.setTODA(setToda);
      runwayParam.setASDA(setAsda);
      runwayParam.setLDA(setLda);
      assertTrue(printTrace("TORA", "Setter", setTora, runwayParam.getTORA()),
          setTora - runwayParam.getTORA() == 0);
      assertTrue(printTrace("TODA", "Setter", setToda, runwayParam.getTODA()),
          setToda - runwayParam.getTODA() == 0);
      assertTrue(printTrace("ASDA", "Setter", setAsda, runwayParam.getASDA()),
          setAsda - runwayParam.getASDA() == 0);
      assertTrue(printTrace("LDA", "Setter", setLda, runwayParam.getLDA()),
          setLda - runwayParam.getLDA() == 0);
    }
  }

  /** pretty showing test failure trace */
  public String printTrace(String params, String testName, double expected, double actual) {
    return params + " " + testName + " Test Failed. Expected: " + String.valueOf(expected)
        + " Actual: " + String.valueOf(actual);
  }

}
