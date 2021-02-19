package tests;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import base.TestCaseBase;
import ic_MagentoPageObjects.MagentoOrderStatusPage;
import ic_MagentoPageObjects.MagentoRetrieveCustomerDetailsPage;
import ic_MagentoPageObjects.ic_Magento_Login;
import ic_MagentoPageObjects.ic_MagentoOrderSAPnumber;
import com.aventstack.extentreports.ExtentTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import JDGroupPageObjects.*;
import utils.*;

public class JDTests extends BaseTest {
	ExtentReportJD reportJD;

	public String currentSuite;
	public String currentKeyWord;
	HashMap<String, Integer> occCount=null;
	int testcaseID;
	
	@BeforeClass(alwaysRun = true)
	public void setUp() {

	}
	//Login to Opera Cloud

	@Test
	public void suiteExecutor() throws Exception {
		dataTable2= new DataTable2();
		dataMap2=dataTable2.getExcelData();
		int numberOfSuits=dataMap2.size();
		for(int i=0;i<numberOfSuits;i++){
			Object Key = dataMap2.keySet().toArray()[i];
			if(!Key.toString().contains("++")&&!Key.toString().contains("Suites")&&!Key.toString().contains("inputData")) {
				currentSuite=Key.toString();
				HashMap<String, ArrayList<String>> singleSuiteData = dataMap2.get(Key);
				System.out.println("currentSuite:"+currentSuite);
				reportJD=new ExtentReportJD(currentSuite);
				runSuite(singleSuiteData);
				reportJD.endReport();
			}
		}
	}



	public void runSuite(HashMap<String, ArrayList<String>> singleSuiteData) throws IOException, InterruptedException {

		int numberOfTestCases =singleSuiteData.get("Execute").size();
		for(int i=0;i<numberOfTestCases;i++){
			System.out.println("TestCaseNumber:"+i);
			occCount=new HashMap<String, Integer>();
			String execute=singleSuiteData.get("Execute").get(i);
			if(execute.toLowerCase().equals("yes")){
				String testCaseDescription=singleSuiteData.get("testCaseDescription").get(i);
				testcaseID=Integer.parseInt(singleSuiteData.get("TestCaseID").get(i)) ;
				ExtentTest test=reportJD.createTest(testcaseID+" : "+testCaseDescription);
				startBrowserSession();
				configFileReader.setPropertyVal("sequence","true");
				for(int j=0;j<10;j++){
					String actionToRunLable="Action"+(j+1);
					String actionToRun=singleSuiteData.get(actionToRunLable).get(i);
					currentKeyWord=actionToRun;
					System.out.println("actionToRunLable:"+actionToRunLable);
					System.out.println("currentKeyWord:"+currentKeyWord);
					try {
						if(!currentKeyWord.equals("")){
							if(!occCount.containsKey(currentKeyWord)){
								occCount.put(currentKeyWord,0);
							}else{
								int occNum=occCount.get(currentKeyWord);
								occNum++;
								occCount.put(currentKeyWord,occNum);
							}
							if(configFileReader.getPropertySavedVal("sequence").equals("true")){
								runKeyWord(actionToRun,test);
							}
						}
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				endBrowserSession();
			}

		}
	}

	public void runKeyWord(String actionToRun,ExtentTest test) throws IOException, InterruptedException {
		String moduleToRun=actionToRun;
		IConnection ic=new IConnection(driver);
		ic_PaymentOption Payopt=new ic_PaymentOption(driver);
		ic_PayUPayment  PayU = new ic_PayUPayment(driver);
		Ic_Products products = new Ic_Products(driver);
		IC_Cart icCart=new IC_Cart(driver);
		ICDelivery icDelivery=new ICDelivery(driver);
		ic_Magento_Login icMagento = new ic_Magento_Login(driver);
		MagentoOrderStatusPage orderStatus = new MagentoOrderStatusPage(driver);
		ic_MagentoOrderSAPnumber icOrderSAPnumber = new ic_MagentoOrderSAPnumber(driver);
		ic_NewAccountCreation newAcc = new ic_NewAccountCreation(driver);
		MagentoRetrieveCustomerDetailsPage custDetails = new MagentoRetrieveCustomerDetailsPage(driver,dataMap2);
		ExtentTest test1=test.createNode(moduleToRun);
		int rowNumber=-1;
		if(dataMap2.containsKey(currentKeyWord+"++")) {
			rowNumber = findRowToRun(dataMap2.get(currentKeyWord + "++"), occCount.get(currentKeyWord), testcaseID);
		}
		int i = 0;
		switch (moduleToRun) {
			case "Login":
				ic.login(dataMap2.get(currentKeyWord+"++"),test1,rowNumber);
				break;
			case "Logout":
				ic.logout(test1);
				break;
			case "CheckoutpaymentOption":
				Payopt.CheckoutpaymentOption(dataMap2.get(currentKeyWord+"++"),test1,rowNumber);
				break;
			case "PayUPagePayment":
				PayU.PayUPagePayment(dataMap2.get(currentKeyWord+"++"),test1,rowNumber);
				break;
			case "ProductSearch":
				products.searchType(dataMap2.get(currentKeyWord+"++"), test1, rowNumber);
				break;
			case "iCcartVerification":
				icCart.iCcartVerification(test1);
				break;
			case "deliveryPopulation":
				icDelivery.deliveryPopulation(dataMap2.get(currentKeyWord+"++"), test1, rowNumber);
				break;
			case "Login_magento":
				icMagento.Login_magento(dataMap2.get(currentKeyWord+"++"), test1, rowNumber);
				break;
			case"OrderStatusSearch":
				orderStatus.navigateToOrderPage(dataMap2.get(currentKeyWord+"++"),test1,rowNumber);
				break;
			case "GenerateOrderSAPnumber":
				icOrderSAPnumber.GenerateOrderSAPnumber(test1);
			case "EnterNewUserDetails":
				//newAcc.EnterNewUserDetails(dataMap2.get(currentKeyWord+"++"),test1,rowNumber);
				break;
			case "accountCreation":
				newAcc.accountCreation(dataMap2.get(currentKeyWord+"++"), test1, rowNumber);
				break;	

		}
	}

	public int findRowToRun(HashMap<String, ArrayList<String>> input,int occCount,int testcaseID){
		int numberRows=input.get("TCID").size();
		int rowNumber=-1;
		occCount=occCount+1;
		for(int i=0;i<numberRows;i++){
			if(input.get("TCID").get(i).equals(Integer.toString(testcaseID))&&input.get("occurence").get(i).equals(Integer.toString(occCount))){
				rowNumber=i;
			}
		}
		return rowNumber;
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
			driver.navigate().refresh();
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
