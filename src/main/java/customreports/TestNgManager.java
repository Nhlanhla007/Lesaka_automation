package customreports;

/**
 * <copyright file="TestNgManager.java" company="Modi's Consulting Group"> Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic,
 * mechanical or otherwise, is prohibited without the prior written permission
 * of the copyright owner. </copyright>
 * 
 * @author 
 */

import org.apache.log4j.Logger;
import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import Logger.Log;
import pageddef.PageDef;
import utils.Action;
import utils.Values;

public class TestNgManager extends TestListenerAdapter implements IExecutionListener {
	public String xmlTestcaseName="";
	static Logger logger = Log.getLogData(Action.class.getSimpleName());
	
	@Override
    public void onStart(ITestContext testContext) {
		logger.info("Test case name--->"+testContext.getName());
		xmlTestcaseName=testContext.getName();
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		logger.info(tr.getThrowable().toString());
		logger.info(tr.getName() + " ----> Failed");
		Reporter.report(Values.actual, Values.expected, tr, Output.FAILED,tr.getMethod().getMethodName(),tr.getMethod().getDescription());
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		logger.info(tr.getName() + " ----> Skipped");
		Reporter.report(Values.actual, Values.expected, tr, Output.SKIPPED,tr.getMethod().getMethodName(),tr.getMethod().getDescription());
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		logger.info(tr.getName() + " ----> Success");
		Reporter.report(Values.actual, Values.expected, tr, Output.PASSED,tr.getMethod().getMethodName(),tr.getMethod().getDescription());
	}

	@Override
	public void onExecutionStart() {
		logger.info("Starting Test Execution");
		
		
		try {
			String filePath=System.getProperty("user.dir") + "\\src\\test\\resources\\pagedefs\\CommonLocators.json";
			PageDef.initializeLocators(filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Reporter.initialize();
		Values.initializeStartTime();
	}

	@Override
	public void onExecutionFinish() {
		logger.info("Ending Execution...");
		logger.info("Trying to close Webdriver...");		
		try {			
			logger.info("Selenium Webdriver closed successfully!");
		} catch (Exception e) {
			logger.info("An error occured while trying to close selenium web driver!");
		}
		logger.info("Writing Results and Logger to file...");
	
		//	Reporter.writeResults();

	
	}
}