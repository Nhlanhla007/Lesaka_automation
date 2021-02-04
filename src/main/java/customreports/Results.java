package customreports;

import org.testng.ITestResult;

import utils.Values;

/**
 * <copyright file="Results.java" company="Modi's Consulting Group"> Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic,
 * mechanical or otherwise, is prohibited without the prior written permission
 * of the copyright owner. </copyright>
 * 
 * @author 
 */


public class Results {

	private ITestResult tr;
	private String suiteName;
	private String testCaseName;
	private String methodName;
	private String result;
	private String resultText;
	private String screenshotText;
	private long time;

	
	/* ===============================
	 	@param tr
	  		The testng object that stores various details about the test step executed.
	 	@param result
	        The result text. Will hold either of Passed, Failed, or Skipped.
	  	@param resultText
	 		The result text based of the result of the test step.
		===============================*/
	public Results(ITestResult tr, String result, String resultText,String xmlTestCaseName) {
		String className = tr.getMethod().getTestClass().getName();
		suiteName = className.substring(className.lastIndexOf('.') + 1);
		testCaseName=tr.getMethod().getMethodName();
		methodName = xmlTestCaseName;		
		time = tr.getEndMillis() - tr.getStartMillis();
		this.tr = tr;
		this.result = result;
		this.resultText = resultText;
		screenshotText = "None";
	}

	public void setScreenshotPath(String path) {
		screenshotText = "<div class=\"grow pic\"><a "
				+ "rel=\"lightbox-tc1it1\" href=\"" + path
				+ "\"><img style=\"border:0;\" src=\"" + path
				+ "\" width = 150 height = 90 " + "alt=\"" + path.substring(path.lastIndexOf('\\') + 1)
				+ "\"></a></div>";
	}
	
	public String getScreenshotText() {
		return screenshotText;
	}

	/* ===============================
	  @return Get the result of the test step.
		===============================*/
	public String getResult() {
		return this.result;
	}

	/* ===============================
	  @return Get the result text of the test step.
		===============================*/
	public String getResultText() {
		return this.resultText;
	}

	/* ===============================
	  @return Get the ITestResult object of the test step.
		===============================*/
	public ITestResult getITestResult() {
		return tr;
	}

	/* ===============================
	  @return Get the name of the suite to which this test step belongs.
		===============================*/
	public String getSuiteName() {
		return suiteName;
	}

	/* ===============================
	  @return Get the name of the test case to which this test step belongs.
		===============================*/
	public String getTestCaseName() {
		return testCaseName;
	}

	/* ===============================
	  @return Get the test method name of this step.
		===============================*/
	public String getMethodName() {
		return methodName;
	}

	/* ===============================
	  @return Get the time taken in milliseconds to execute the test step.
		===============================*/
	public long getTime() {
		return time;
	}
	
	/* ===============================
	  @return Get the application URL for different domain
		===============================*/
	public String getApplicationURL() {
		return Values.appURLList.get(tr.getMethod().getTestClass().getName());
	}
	
	/* ===============================
	  @return Get the bowser name for different domain
		===============================*/
	
	public String getBrowser() {
		return Values.browserList.get(tr.getMethod().getTestClass().getName());
	}

}