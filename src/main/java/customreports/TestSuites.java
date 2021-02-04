package customreports;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * <copyright file="TestSuites.java" company="Modi's Consulting Group"> Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic,
 * mechanical or otherwise, is prohibited without the prior written permission
 * of the copyright owner. </copyright>
 * 
 * @author 
 */

public class TestSuites {

	private HashMap<String, TestCases> testCases = new HashMap<>();
	private ArrayList<String> testCaseNames = new ArrayList<>();
	private String name;

	/* ===============================
	  @param name
	       The name of the test suite.
		===============================*/
	public TestSuites(String name) {
		this.name = name;
	}

	/* ===============================
	  @param name
	      The name of the testcase to be added.
	  @param testCase
	      The testcase object to be added.
		===============================*/
	public void addTestCase(String name, TestCases testCase) {
		testCases.put(name, testCase);
		testCaseNames.add(name);
	}

	/* ===============================
	  @param name
	      The name of testcase.
	  @return Returns true if the provided test case exists in the test suite.
		===============================*/
	public boolean testCaseExists(String name) {
		return testCases.containsKey(name);
	}

	/* ===============================
	  @param name
	     The name of testcase.
	  @return The object of the testcase corresponting to the provided name.
		===============================*/
	public TestCases getTestCase(String name) {
		return testCases.get(name);
	}

	/* ===============================
	 @param index
	     The index of testcase in the suite.
	 @return The object of the testcase corresponting to the provided index.
		===============================*/
	public TestCases getTestCaseAt(int index) {
		return testCases.get(testCaseNames.get(index));
	}

	/* ===============================
	 @return Returns the number of test cases in the test suite.
		===============================*/
	public int getNoOfTestCases() {
		return testCaseNames.size();
	}

	/* ===============================
	 @return Returns the number of test cases that have passed in the suite.
		===============================*/
	public int getNoOfPassedTestCases() {
		int ctr = 0;
		for (String testCase : testCaseNames) {
			if( (testCases.get(testCase).isPassed()) && (!(testCases.get(testCase).isSkipped())))
				ctr++;
		}
		return ctr;
	}
	
	public int getNoOfSkippedTestCases() {
		int ctr = 0;
		for (String testCase : testCaseNames) {
			if (testCases.get(testCase).isSkipped())
				ctr++;
		}
		return ctr;
	}


	/* ===============================
	 @return Returns the number of test cases that have failed in the suite.
		===============================*/
	public int getNoOfFailedTestCases() {
		int ctr = 0;
		for (String testCase : testCaseNames) {
			if (!testCases.get(testCase).isPassed())
				ctr++;
		}
		return ctr;
	}

	/* ===============================
	 @return Returns the name of the test suite.
		===============================*/
	public String getName() {
		return name;
	}

	/* ===============================
	  @return Returns true if the entire test suite has passed. The test suite fails if any one of the test cases fails.
		===============================*/
	public boolean isPassed() {
		for (String testCase : testCaseNames) {
			if (testCases.get(testCase).isPassed())
				continue;
			else
				return false;
		}
		return true;
	}

	
	
	public boolean isSkipped() {
		for (String testCase : testCaseNames) {
			if (testCases.get(testCase).isSkipped())
				continue;
			else
				return false;
		}
		return true;
	}
	/* ===============================
	  @return Returns the total time taken to execute the test suite.
		===============================*/
	public long getTimeTaken() {
		long timeTaken = 0;
		for (String testCase : testCaseNames) {
			TestCases tc = testCases.get(testCase);
			timeTaken += tc.getTimeTaken();
		}
		return timeTaken;
	}
	
	public String getApplicationURL()
	{
		return testCases.get(testCaseNames.get(0)).getApplicationName();
	}
	
	public String getBrowserName()
	{
		return testCases.get(testCaseNames.get(0)).getBrowserName();
	}
}
