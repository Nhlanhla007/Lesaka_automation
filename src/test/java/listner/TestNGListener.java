package listner;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.mail.MessagingException;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IExecutionListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import Logger.Log;
import customreports.Output;
import customreports.Reporter;
import extentReports.ExtentManager;
import io.qameta.allure.Attachment;
import pageddef.PageDef;
import tests.BaseTest;
import utils.*;

public class TestNGListener implements IExecutionListener, ITestListener {

	public static String xmlTestCaseName;
	public static String currentDate;
	Logger logger = Log.getLogData(this.getClass().getSimpleName());

	private static ExtentReports extent;
	public static ExtentTest extentLogger;
	private static ThreadLocal parentTest = new ThreadLocal();
	private static ThreadLocal childTest = new ThreadLocal();

	static {
		currentDate = new SimpleDateFormat("dd-MM-yyyy_HH_mm_ss").format(new Date());
		System.setProperty("current.date.time", currentDate);
	}

	private static String getTestMethodName(ITestResult iTestResult) {
		return iTestResult.getMethod().getConstructorOrMethod().getName();
	}

	/*
	 * @Attachment(value = "Page screenshot", type = "image/png") public byte[]
	 * saveScreenshotPNG(WebDriver driver) { Log.info("in allure page screenshot");
	 * return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES); }
	 */

	// Text attachments for Allure
	@Attachment(value = "{0}", type = "text/plain")
	public static String saveTextLog(String message) {
		return message;
	}

	// HTML attachments for Allure
	@Attachment(value = "{0}", type = "text/html")
	public static String attachHtml(String html) {
		return html;
	}

	@Override
	public synchronized void onStart(ITestContext context) {
		extent = ExtentManager.getReporter();

		/*
		 * ExtentTest parent =
		 * extent.createTest(context.getCurrentXmlTest().getSuite().getName());
		 * parentTest.set(parent);
		 */

	}

	@Override
	public synchronized void onFinish(ITestContext context) {
		extent.flush();

	}

	@Override
	public synchronized void onTestStart(ITestResult result) {
		// ASSIGNED INSTANCE OF EXTENT TO THE EXTENTFACTORY THREADLOCAL
//		extentLogger = extent.createTest(result.getMethod().getMethodName());
//		ExtentFactory.getInstance().setExtent(extentLogger);
		// String appURL= result.getTestContext().getAttribute("appURL").toString();
		/*
		 * ExtentTest child =((ExtentTest)
		 * parentTest.get()).createNode(result.getMethod().getMethodName());
		 * childTest.set(child);
		 */
	}

	@Override
	public synchronized void onTestSuccess(ITestResult result) {

		logger.info(result.getName() + " ----> Success END OF TEST");
		/*
		 * Object testClass = result.getInstance(); WebDriver driver = ((BaseTest)
		 * testClass).getDriver(); saveScreenshotPNG(driver);
		 * 
		 * try { Values.screenshotPath = GetScreenShot.capture(driver,
		 * getTestMethodName(result)); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 *//*
			 * Reporter.report(Values.actual, Values.expected, result,
			 * Output.PASSED,result.getMethod().getMethodName(),result.getMethod().
			 * getDescription()); ((ExtentTest) childTest.get()).pass("Test passed");
			 */
	}

	@Override
	public synchronized void onTestFailure(ITestResult result) {

		// ((ExtentTest) childTest.get()).fail(result.getThrowable());
		logger.error("Exception" + result.getThrowable().getMessage());

		/*
		 * Object testClass = result.getInstance(); WebDriver driver = ((BaseTest)
		 * testClass).getDriver(); //Get driver from BaseTest and assign to local
		 * webdriver variable.
		 * 
		 * try { if (driver instanceof WebDriver) {
		 * logger.info("Screenshot captured for test case:" +
		 * getTestMethodName(result)); saveScreenshotPNG(driver); }
		 * Values.screenshotPath = GetScreenShot.capture(driver,
		 * getTestMethodName(result));
		 * Log.info("screenshot path"+Values.screenshotPath);
		 * 
		 * 
		 * ((ExtentTest) childTest.get()).fail("Screenshot")
		 * .addScreenCaptureFromPath(Values.screenshotPath);
		 * 
		 * } catch (Exception e) {
		 * logger.error("Error in capturing screenshot by chrome driver.Time out error"
		 * ); e.printStackTrace();
		 * Values.screenshotPath="Error in capturing screenshot"; }
		 */
		logger.info(result.getName() + " ----> Failed");
		// Reporter.report(Values.actual, Values.expected, result,
		// Output.FAILED,result.getMethod().getMethodName(),result.getMethod().getDescription());
	}

	@Override
	public synchronized void onTestSkipped(ITestResult result) {

		logger.info(result.getName() + " ----> Skipped");
		// Reporter.report(Values.actual, Values.expected, result,
		// Output.SKIPPED,result.getMethod().getMethodName(),result.getMethod().getDescription());

	}

	@Override
	public synchronized void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onExecutionStart() {
		logger.info("date" + System.getProperty("current.date.time"));
		Values.extentReportPath = Values.EXTENT_REPORT_PATH + "extentreport_" + currentDate + ".html";

		logger.info("Initializing locators...");
		try {
			String filePath = System.getProperty("user.dir") + "\\src\\test\\resources\\pagedefs\\CommonLocators.json";
			PageDef.initializeLocators(filePath);
		} catch (Exception e) {
			logger.error("Error in initializing the locators JSON file");
			e.printStackTrace();
		}

		logger.info("Initializing the test data sheet");
		DataTable2.initializeTestDataWorkbook();

		logger.info("Initializing configuration properties");
		ConfigFileReader.loadData();

		logger.info("Deleting files of allure-results directory");
		JavaUtils.deleteFiles("allure-results");

		logger.info("Deleting files of allure-report directory");
		try {
			JavaUtils.deletDirectory("allure-report");
		} catch (IOException e) {
			logger.error("Directory not found");
		}

		// Reporter.initialize();
		Values.initializeStartTime();
		logger.info("Starting Test Execution");

	}

	@Override
	public void onExecutionFinish() {
		logger.info("Ending Execution...");
		logger.info("Writing Results and Logger to file...");

		try {

			Values.IsreportGenerated = true;
		} catch (Exception e) {

		}

	}

}
