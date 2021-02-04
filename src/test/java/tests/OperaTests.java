package tests;

import java.io.EOFException;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

import base.TestCaseBase;
import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import operaCloudPageObjects.*;
import utils.*;

public class OperaTests extends BaseTest {
	ExtentReportJD reportJD;

	public String currentSuite;
	public String currentKeyWord;
	HashMap<String, Integer> occCount=null;
	
	@BeforeClass(alwaysRun = true)
	public void setUp() {

	}
	//Login to Opera Cloud
	@Test
	public void Login_OperaCloud() throws Exception {
		System.out.println("one testing Leverch from eclipse to branch TA-3");
		System.out.println("two testing Leverch from eclipse to branch TA-3");
		System.out.println("three testing Leverch from eclipse to branch TA-3");
		dataTable2= new DataTable2();
		dataMap2=dataTable2.getExcelData();
		int numberOfSuits=dataMap2.size();
		for(int i=0;i<numberOfSuits;i++){
			Object Key = dataMap2.keySet().toArray()[i];
			if(!Key.toString().contains("++")&&!Key.toString().contains("Suites")&&!Key.toString().contains("inputData")) {
				currentSuite=Key.toString();
				HashMap<String, ArrayList<String>> singleSuiteData = dataMap2.get(Key);
				System.out.println("Key:"+Key);
				reportJD=new ExtentReportJD(currentSuite);
				runSuite(singleSuiteData);
				reportJD.endReport();
			}
		}
	}



	public void runSuite(HashMap<String, ArrayList<String>> singleSuiteData) throws IOException {

		int numberOfTestCases =singleSuiteData.get("Execute").size();
		for(int i=0;i<numberOfTestCases;i++){
			System.out.println("TestCaseNumber:"+i);
			occCount=new HashMap<String, Integer>();

			String execute=singleSuiteData.get("Execute").get(i);
			String testCaseDescription=singleSuiteData.get("testCaseDescription").get(i);
			ExtentTest test=reportJD.createTest(testCaseDescription);
			if(execute.toLowerCase().equals("yes")){
				startBrowserSession();
				for(int j=0;j<10;j++){
					String actionToRunLable="Action"+(j+1);
					String actionToRun=singleSuiteData.get(actionToRunLable).get(i);
					currentKeyWord=actionToRun;
					System.out.println("currentKeyWord:"+currentKeyWord);
					if(!currentKeyWord.equals("")){
						if(!occCount.containsKey(currentKeyWord)){
							occCount.put(currentKeyWord,0);
						}else{
							int occNum=occCount.get(currentKeyWord);
							occNum++;
							occCount.put(currentKeyWord,occNum);
						}
						runKeyWord(actionToRun,test);
					}
				}
				endBrowserSession();
			}

		}
	}

	public void runKeyWord(String actionToRun,ExtentTest test){
		String moduleToRun=actionToRun;
		IConnection ic=new IConnection(driver);
		ExtentTest test1=test.createNode(moduleToRun);
		switch (moduleToRun) {
			case "Login":
				ic.login(dataMap2.get(currentKeyWord+"++"),test1,occCount.get(currentKeyWord));
				break;

		}
	}

	public void startBrowserSession()
	{
		driver=null;
		if(driver == null){
			configFileReader = new ConfigFileReader();
			logger.info("Initializing the driver");


			browserName = System.getProperty("BrowserType");
			if(browserName==null){
				logger.info("System property returned Null browser type. So getting data from Config file");
				Report.info("System property returned Null browser type. So getting data from Config file");
				browserName=ConfigFileReader.getPropertyVal("BrowserType");
			}

			driver = TestCaseBase.initializeTestBaseSetup(browserName);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

			navigateURL = System.getProperty("URL");
			if(navigateURL==null){
				logger.info("System property returned Null URL. So getting data from Config file");
				Report.info("System property returned Null URL. So getting data from Config file");
				navigateURL = ConfigFileReader.getPropertyVal("URL");
			}

			logger.info("Navigate to URL");
			Report.info("Navigating to URL: "+navigateURL);

			driver.navigate().to(navigateURL);
			driver.manage().window().maximize();
			logger.info("Browser name is "+browserName);
			Report.info("Browser name is "+browserName);
			logger.info("App URL: "+ navigateURL);
			Values.app= navigateURL;
			Values.browser=browserName;
		}
	}
	public void endBrowserSession(){
		driver.close();
	}




}
