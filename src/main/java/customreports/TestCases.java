package customreports;

import java.util.ArrayList;


/**
 * <copyright file="TestCases.java" company="Modi's Consulting Group"> Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic,
 * mechanical or otherwise, is prohibited without the prior written permission
 * of the copyright owner. </copyright>
 * 
 * @author 
 */

public class TestCases {
	private ArrayList<Results> steps = new ArrayList<>();
	private boolean passed = true;
	private boolean skipped = false;
	private String name;
	private String description;

	/* ===============================
	  @param name
	     The name of the testcase.
		===============================*/
	public TestCases(String name, String description) {
		this.name = name;
		this.description = description;
	}

	/* ===============================
	  @param step
	             The object of a test result.
		===============================*/
	public void addStep(Results step) {
		if (step.getResult().equals(Reporter.FAIL_TEXT))
			passed = false;
		else if(step.getResult().equals(Reporter.SKIP_TEXT))
			skipped=true;
			
		steps.add(step);
	}

	/* ===============================
	  @param i
	      The index, starting from 0, for which the Result object is needed.
	  @return The Result object at ith index of the testcase.
		===============================*/
	public Results getResultAt(int i) {
		return steps.get(i);
	}

	/* ===============================
	  @return The number of steps in the testcase.
		===============================*/
	public int getNoOfSteps() {
		return steps.size();
	}

	/* ===============================
	  @return The result of the entire testcase. The test case fails if any one
	          of the test steps fails.
		===============================*/
	public boolean isPassed() {
		return passed;
	}

	public boolean isSkipped() {
		return skipped;
	}

	
	/* ===============================
	  @return The name of the testcase.
		===============================*/
	public String getName() {
		return name;
	}

	/* ===============================
	  @return Get the total time taken to execute the entire testcase.
		===============================*/
	public long getTimeTaken() {
		long timeTaken = 0;
		for (Results r : steps) {
			timeTaken += r.getTime();
		}
		return timeTaken;
	}

	/* ===============================
	  @return Returns the description of the test case
		===============================*/
	public String getDescription() {
		return description;
	}
	
	/* ===============================
	  @return sets the description of the test case 
		===============================*/
	public void setDescription(String description) {
		this.description=description;
	}
	
	/* ===============================
	  @return get the application name of the test case 
		===============================*/
	
	public String getApplicationName()
	{
		return steps.get(0).getApplicationURL();
	}
	
	/* ===============================
	  @return get the browser name of the test case 
		===============================*/
	
	public String getBrowserName()
	{
		return steps.get(0).getBrowser();
	}
}