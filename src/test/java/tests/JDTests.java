package tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import KeywordManager.JDGKeyManager;
import base.TestCaseBase;
import com.aventstack.extentreports.ExtentTest;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;


import utils.*;

public class JDTests extends BaseTest {

	public String currentSuite;
	public String currentKeyWord;
	HashMap<String, Integer> occCount = null;
	int testcaseID;
	JDGKeyManager km = null;
	JavaUtils utils;
	ExtentReportGenerator reportJD;


	@Test
	public void suiteExecutor() throws Exception {
		refreshScreenshotFolder();
		dataTable2 = new DataTable2();
		//Please update you module name here and copy jdgroupMAIN.xlsx to jdgroupTA104.xlsx
		dataTable2.setPath("MAIN");
		dataMap2 = dataTable2.getExcelData();
		km = new JDGKeyManager(driver, dataTable2, dataMap2);
		LinkedHashMap<String, ArrayList<String>> suites = dataMap2.get("Suites");
		int numberOfSuits = suites.get("Execute").size();
		for (int i = 0; i < numberOfSuits; i++) {
			if (suites.get("Execute").get(i).toLowerCase().equals("yes")) {
				currentSuite = suites.get("testSuitName").get(i);
				reportJD = new ExtentReportGenerator(currentSuite);
				runSuite(dataMap2.get(currentSuite));
				reportJD.endReport();

			}
		}
	}

	public void refreshScreenshotFolder() throws IOException {
		JavaUtils.deleteFiles("./Reports/screenshots");

	}

	public void runSuite(HashMap<String, ArrayList<String>> singleSuiteData) throws Exception {

		int numberOfTestCases = singleSuiteData.get("Execute").size();
		for (int i = 0; i < numberOfTestCases; i++) {

			occCount = new HashMap<String, Integer>();
			String execute = singleSuiteData.get("Execute").get(i);
			if (execute.toLowerCase().equals("yes")) {
				System.out.println("TestCaseNumber:" + i);
				String testCaseDescription = singleSuiteData.get("testCaseDescription").get(i);
				testcaseID = Integer.parseInt(singleSuiteData.get("TestCaseID").get(i));
				dataTable2.setTestCaseID(testcaseID);
				ExtentTest test = reportJD.createTest(testcaseID + " : " + testCaseDescription);
				startBrowserSession();
				try {
					System.out.println("-------------------------------------------------------");
					System.out.println("testCaseDescription: " + testcaseID + "_" + testCaseDescription);
					for (int j = 0; j < 40; j++) {
						String actionToRunLable = "Action" + (j + 1);
						String actionToRun = "";
						try {
							actionToRun = singleSuiteData.get(actionToRunLable).get(i);
						} catch (Exception e) {

						}
						currentKeyWord = actionToRun;

						if (!currentKeyWord.equals("")) {
							System.out.println("actionToRunLable:" + actionToRunLable);
							System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxx:currentKeyWord:" + currentKeyWord);
							if (!occCount.containsKey(currentKeyWord)) {
								occCount.put(currentKeyWord, 0);
							} else {
								int occNum = occCount.get(currentKeyWord);
								occNum++;
								occCount.put(currentKeyWord, occNum);
							}
							// dataTable2.setTestCaseID(actionToRun);
							dataTable2.setOccurenceCount(occCount.get(currentKeyWord));
							dataTable2.setModule(actionToRun);
							// runKeyWord(actionToRun, test);
							// if(!test.getStatus().toString().toLowerCase().equals("pass"))
							// throw new Exception("failing");
							km.runKeyWord(actionToRun, testcaseID, occCount, test);
							writeToExcel(createFile());
							reportJD.endReport();

						}
					}
				} catch (Exception e) {
					logger.info(e.getMessage());
					e.printStackTrace();
					e.getCause();
					System.out.println(e.getMessage());
					String screenShot = GenerateScreenShot.getScreenShot(driver);
					ExtentTest node = test.createNode("Exception");
					node.fail(e.getMessage() + node.addScreenCaptureFromPath(screenShot));
				}
				endBrowserSession();
			}

		}

	}

	public void startBrowserSession() {
		driver = null;
		if (driver == null) {
			configFileReader = new ConfigFileReader();
			logger.info("Initializing the driver");
			browserName = System.getProperty("BrowserType");
			if (browserName == null) {
				logger.info("System property returned Null browser type. So getting data from Config file");

				browserName = ConfigFileReader.getPropertyVal("BrowserType");

			}

			driver = TestCaseBase.initializeTestBaseSetup(browserName);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);

			navigateURL = System.getProperty("URL");
			if (navigateURL == null) {
				logger.info("System property returned Null URL. So getting data from Config file");
				navigateURL = ConfigFileReader.getPropertyVal("URL");
			}
			navigateURL = ConfigFileReader.getPropertyVal("URL");
			logger.info("Navigate to URL");
			// driver.navigate().to(navigateURL);
			driver.manage().window().maximize();
			driver.navigate().refresh();
			/*try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			logger.info("Browser name is " + browserName);

			logger.info("App URL: " + navigateURL);
			Values.app = navigateURL;
			Values.browser = browserName;
		}
		km.setDriver(driver);
	}

	public void endBrowserSession() throws IOException {
		driver.close();
		//driver.quit();
	}

	public void writeToExcel(File filePath) throws IOException {
		XSSFWorkbook myWorkBook = new XSSFWorkbook();
		int numberSheets = dataMap2.size();
		Object[] keys = dataMap2.keySet().toArray();
		for (int i = 0; i < numberSheets; i++) {
			Sheet sheet1 = myWorkBook.createSheet(keys[i].toString());
			int numCell = dataMap2.get(keys[i].toString()).size();
			Object[] colList = dataMap2.get(keys[i].toString()).keySet().toArray();
			int rownum = dataMap2.get(keys[i].toString()).get(colList[0].toString()).size();
			for (int j = 0; j <= rownum; j++) {
				Row row = sheet1.createRow(j);
				if (j == 0) {
					for (int z = 0; z < numCell; z++) {
						Cell cell = row.createCell(z);
						cell.setCellValue((String) colList[z]);
					}
				} else {
					for (int z = 0; z < numCell; z++) {
						Cell cell = row.createCell(z);
						cell.setCellValue((String) dataMap2.get(keys[i].toString()).get(colList[z]).get(j - 1));
					}
				}
			}
		}
		FileOutputStream os = new FileOutputStream(createFile());
		myWorkBook.write(os);
		os.close();
		myWorkBook.close();

	}

	public File createFile() throws IOException {
		File myObj = new File(System.getProperty("user.dir") + "\\reports\\Datasheet.xlsx");
		return myObj;
	}





}
