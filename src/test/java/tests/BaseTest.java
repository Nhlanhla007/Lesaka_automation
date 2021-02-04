package tests;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import Logger.Log;

import base.TestCaseBase;
import utils.*;

public class BaseTest {

	public WebDriver driver;
	protected DataTable dataTable = null;
	protected ConfigFileReader configFileReader;
	protected String browserName;
	protected String navigateURL;
	protected DataTable2 dataTable2 = null;
	protected HashMap<String, HashMap<String, ArrayList<String>>> dataMap2 = new HashMap<String, HashMap<String, ArrayList<String>>>();
	
	Logger logger = Log.getLogData(this.getClass().getSimpleName());
	
	public WebDriver getDriver() {
		return driver;
	}

	@BeforeTest(alwaysRun = true,description = "Test Level Setup!")



	@AfterTest (alwaysRun = true,description = "Tear Down")
	public void tearDown()
	{

	}
	
	
}
