package tests;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;

import base.TestCaseBase;
import emailverification.ICGiftCardVerification;
import ic_MagentoPageObjects.*;
import ic_MagentoPageObjects.MagentoOrderStatusPage;
import ic_MagentoPageObjects.Magento_UserInfoVerification;
import ic_MagentoPageObjects.MagentoRegisterNewUser;
import ic_MagentoPageObjects.admin_UserUpdate;
import ic_MagentoPageObjects.ic_Magento_Login;
import ic_MagentoPageObjects.MagentoOrderStatusPage;
import ic_MagentoPageObjects.ic_MagentoOrderSAPnumber;
import ic_MagentoPageObjects.ic_Magento_Login;
import com.aventstack.extentreports.ExtentTest;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


import JDGroupPageObjects.*;
import SAP_HanaDB.SAPCustomerRelated;
import SAP_HanaDB.SAPorderRelated;
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
		LinkedHashMap<String, ArrayList<String>> suites=dataMap2.get("Suites");
		int numberOfSuits=suites.get("Execute").size();
		for(int i=0;i<numberOfSuits;i++){
			if(suites.get("Execute").get(i).toLowerCase().equals("yes")) {
				currentSuite=suites.get("testSuitName").get(i);
				System.out.println("currentSuite:"+currentSuite);
				reportJD=new ExtentReportJD(currentSuite);
				runSuite(dataMap2.get(currentSuite));
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
				dataTable2.setTestCaseID(testcaseID);
				ExtentTest test=reportJD.createTest(testcaseID+" : "+testCaseDescription);
				startBrowserSession();
				configFileReader.setPropertyVal("sequence","true");
				try {
					for(int j=0;j<11;j++){
						String actionToRunLable="Action"+(j+1);
						String actionToRun= "";
						try {
							actionToRun = singleSuiteData.get(actionToRunLable).get(i);
						}catch (Exception e){

						}
						currentKeyWord=actionToRun;
						System.out.println("actionToRunLable:"+actionToRunLable);
						System.out.println("currentKeyWord:"+currentKeyWord);
							if(!currentKeyWord.equals("")){
								if(!occCount.containsKey(currentKeyWord)){
									occCount.put(currentKeyWord,0);
								}else{
									int occNum=occCount.get(currentKeyWord);
									occNum++;
									occCount.put(currentKeyWord,occNum);
								}
								dataTable2.setTestCaseID(actionToRun);
								dataTable2.setOccurenceCount(occCount.get(currentKeyWord));
								runKeyWord(actionToRun,test);
								writeToExcel(new File(dataTable2.filePath()));
								writeToExcel(createFile());
							}
					}
				} catch (Exception e) {
					logger.info(e.getMessage());
					e.printStackTrace();
					String screenShot=GenerateScreenShot.getScreenShot(driver);
					ExtentTest node = test.createNode("Exception");
					node.fail(e.getMessage()+node.addScreenCaptureFromPath(screenShot));
				}
				endBrowserSession();
			}

		}
	}

	public void runKeyWord(String actionToRun,ExtentTest test) throws Exception {
		String moduleToRun=actionToRun;
		IConnection ic=new IConnection(driver,dataTable2);
		Magento_UserInfoVerification Magentoverify = new Magento_UserInfoVerification(driver,dataTable2);
		ic_PaymentOption Payopt=new ic_PaymentOption(driver,dataTable2);
		ic_PayUPayment  PayU = new ic_PayUPayment(driver,dataTable2);
		Ic_Products products = new Ic_Products(driver,dataTable2);
		IC_Cart icCart=new IC_Cart(driver,dataTable2);
		ic_AccountConfirmation icAccountConfirmation = new ic_AccountConfirmation(driver,dataTable2);
		ICDelivery icDelivery=new ICDelivery(driver,dataTable2);
		ic_Magento_Login icMagento = new ic_Magento_Login(driver,dataTable2);
		MagentoOrderStatusPage orderStatus = new MagentoOrderStatusPage(driver,dataTable2);
		ic_MagentoOrderSAPnumber icOrderSAPnumber = new ic_MagentoOrderSAPnumber(driver,dataTable2);
		ic_AccountInformation verifyAcc = new ic_AccountInformation(driver,dataMap2);
		ic_NewAccountCreation newAcc = new ic_NewAccountCreation(driver,dataTable2);
		MagentoRetrieveCustomerDetailsPage custDetails = new MagentoRetrieveCustomerDetailsPage(driver,dataTable2);
		MagentoAccountInformation MagentoCustDetail = new MagentoAccountInformation(driver,dataTable2);
		
		MagentoRegisterNewUser MagentonewUser = new MagentoRegisterNewUser(driver,dataTable2);
		ICUpdateCustomer icUpdateUser = new ICUpdateCustomer(driver,dataTable2);
		ic_GiftCardPurchase icGiftCardPurchase = new ic_GiftCardPurchase(driver,dataTable2);
		admin_UserUpdate adminUserUpdate = new admin_UserUpdate(driver,dataTable2);
		customerValidationUpdates customerVerifyEdits = new customerValidationUpdates(driver,dataTable2);
		ic_Login ic_login = new ic_Login(driver,dataTable2);
		ic_CashDepositPayment ic_cashDepositPayment =new ic_CashDepositPayment(driver,dataTable2);
		SAPorderRelated SaporderRelated = new SAPorderRelated(driver,dataMap2);
		ICGiftCardVerification icGiftCardVerification = new ICGiftCardVerification(driver,dataTable2);
    	ic_GiftCardUsability GiftCardUsability = new ic_GiftCardUsability(driver,dataTable2);
		ic_existingAddress icExistingAddress = new ic_existingAddress(driver,dataTable2);
		ic_RedeemGiftCard icRedeemGiftCard = new ic_RedeemGiftCard(driver,dataTable2);
		SAPCustomerRelated customerDB = new SAPCustomerRelated(driver,dataMap2,dataTable2);
		IC_RetriveOrderID ic_RetriveOrderID= new IC_RetriveOrderID(driver,dataTable2);
        admin_GiftCardReport giftCardReport = new admin_GiftCardReport(driver,dataTable2);
        Magento_CancelSalerOrderCreditMemo CancelSalerOrderCreditMemo = new Magento_CancelSalerOrderCreditMemo(driver,dataTable2);
        Magento_CancelSalesorderVerification CancelSalesorderVerification =new Magento_CancelSalesorderVerification(driver,dataTable2);
        Magento_CreditApp_NavigateFilter CreditApp_NavigateFilter = new Magento_CreditApp_NavigateFilter(driver,dataTable2);
        Magento_CreditStatusVerification CreditStatusVerification = new Magento_CreditStatusVerification(driver,dataTable2);
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
			case "ic_login":
				ic_login.Login_ic(dataMap2.get(currentKeyWord+"++"),test1,rowNumber);
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
				products.ic_SelectProductAndAddToCart(dataMap2.get(currentKeyWord+"++"), test1, rowNumber);
				break;
			/*
			 * case "iCcartVerification": icCart.iCcartVerification(test1); break;
			 */
			case "deliveryPopulation":
				icDelivery.deliveryPopulation(dataMap2.get(currentKeyWord+"++"), test1, rowNumber);
				break;
			case "deliveryPopulationGiftCard":
				rowNumber = findRowToRun(dataMap2.get("deliveryPopulation++"), 0, testcaseID);
				icDelivery.deliveryPopulationGiftCard(dataMap2.get("deliveryPopulation++"), test1, rowNumber);
				break;
			case "Login_magento":
				icMagento.Login_magento(dataMap2.get(currentKeyWord+"++"), test1, rowNumber);
				break;
			case"OrderStatusSearch":
				orderStatus.navigateToOrderPage(dataMap2.get(currentKeyWord+"++"),test1,rowNumber);
				break;
			case "GenerateOrderSAPnumber":
				icOrderSAPnumber.GenerateOrderSAPnumber(dataMap2.get(currentKeyWord+"++"),test1,rowNumber);
				break;
			case "EnterNewUserDetails":
				//newAcc.EnterNewUserDetails(dataMap2.get(currentKeyWord+"++"),test1,rowNumber);
				break;
//			case "Verify_Acount_Information":
//				verifyAcc.Verify_Acount_Information(dataMap2.get(currentKeyWord+"++"),test1,rowNumber);
//				break;
			case "accountCreation":
				newAcc.accountCreation(dataMap2.get(currentKeyWord+"++"), test1, rowNumber);
				break;	
			case "icAccountConfirmation":
				icAccountConfirmation.AccountCreationConfirmation(dataMap2.get(currentKeyWord+"++"), test1, rowNumber);
				break;
			case"RetrieveCustomerDetails":
				custDetails.retrieveCustomerDetails(dataMap2.get(currentKeyWord+"++"), test1, rowNumber);
				break;
			case "VadidateCustomerInfo_backend":
				rowNumber = findRowToRun(dataMap2.get("accountCreation++"), 0, testcaseID);
				MagentoCustDetail.VadidateCustomerInfo_backend(dataMap2.get("accountCreation++"), test1, rowNumber);
				break;
			case "Magento_UserInfoVerification":
    			rowNumber = findRowToRun(dataMap2.get("accountCreation++"), 0, testcaseID);
				Magentoverify.Validate_UserInfobackend(dataMap2.get("accountCreation" + "++"),test1,rowNumber);
				break;
			case "CreateaccountBackend":
				MagentonewUser.CreateAccount_validateInfo_Backend(dataMap2.get(currentKeyWord+"++"),test1,rowNumber);
				break;
			case "ICUpdateUser":
				ArrayList<HashMap<String, ArrayList<String>>> mySheets=new ArrayList<HashMap<String, ArrayList<String>>>();
				mySheets.add(dataMap2.get(currentKeyWord+"++"));
				mySheets.add(dataMap2.get("ic_login++"));
				icUpdateUser.updateAccount(mySheets,test1,testcaseID);
				break;
			case "customerValidationUpdates":
				ArrayList<HashMap<String, ArrayList<String>>> adminsheets=new ArrayList<HashMap<String, ArrayList<String>>>();
				adminsheets.add(dataMap2.get(currentKeyWord+"++"));
				//rowNumber = findRowToRun(dataMap2.get("accountCreation++"), 0, testcaseID);
				customerVerifyEdits.VerifyCustomerinfoUpadtes(adminsheets,test1,testcaseID);
				break;
			case "adminUserUpdate":
				ArrayList<HashMap<String, ArrayList<String>>> adminSheets=new ArrayList<HashMap<String, ArrayList<String>>>();
				adminSheets.add(dataMap2.get(currentKeyWord+"++"));
				adminUserUpdate.editCustomerDetails(adminSheets,test1,testcaseID);
				break;
			case "ic_CashDepositPayment":
				ArrayList<HashMap<String, ArrayList<String>>> RequiredSheets = new ArrayList<HashMap<String, ArrayList<String>>>();
				RequiredSheets.add(dataMap2.get(currentKeyWord+"++"));
				RequiredSheets.add(dataMap2.get("Login_magento++"));
				ic_cashDepositPayment.InvoiceCashDeposit(RequiredSheets,test1,testcaseID);
			case "icGiftCardPurchase":
				icGiftCardPurchase.purchaseGiftCard(dataMap2.get(currentKeyWord+"++"), test1, rowNumber);	
				break;
			case "SAP_OrderRelated":
				SaporderRelated.SAP_OrderDetailVadidation(dataMap2.get(currentKeyWord+"++"),test1,rowNumber);
				break;
			case "icGiftCardVerificationSender":
				icGiftCardVerification.icGiftCardVerificationSender(dataMap2.get(currentKeyWord+"++"),test1,rowNumber);
				break;
			case "getnumberOfEmails":
				rowNumber = findRowToRun(dataMap2.get("icGiftCardVerificationSender++"), 0, testcaseID);
				icGiftCardVerification.clearEmail(dataMap2.get("icGiftCardVerificationSender++"),test1,rowNumber);
				break;
            case "giftCardReport":
                ArrayList<HashMap<String, ArrayList<String>>> mySheet=new ArrayList<HashMap<String, ArrayList<String>>>();
                mySheet.add(dataMap2.get(currentKeyWord+"++"));
                giftCardReport.giftCardReports(mySheet,test1,testcaseID);
                break;
			case "VeriyGiftcardUsableity":
				GiftCardUsability.VeriyGiftcardUsableity(dataMap2.get(currentKeyWord+"++"),test1,rowNumber);
				break;
			case "icRedeemGiftCard":
				icRedeemGiftCard.redeemGiftCard(dataMap2.get(currentKeyWord+"++"), test1, rowNumber);
				break;
			case "icExistingAddress":
				icExistingAddress.AddressThere(test1);
				break;
			case "ic_RetriveOrderID":
				ic_RetriveOrderID.RetriveOrderID(test1);
				break;
			case "SapCustomer":
				ArrayList<HashMap<String, ArrayList<String>>> sheets = new ArrayList<HashMap<String, ArrayList<String>>>();
				sheets.add(dataMap2.get("accountCreation++"));
				sheets.add(dataMap2.get("deliveryPopulation++"));
				//sheets.add(dataMap2.get("SapCustomer++"));//Falls away
				sheets.add(dataMap2.get("ICUpdateUser++"));
				sheets.add(dataMap2.get("CreateaccountBackend++"));
				sheets.add(dataMap2.get("adminUserUpdate++"));
				customerDB.sapDbTests(dataMap2.get(currentKeyWord+"++"),sheets, test1, testcaseID,rowNumber);
				break;
			case "CancelSalerOrderCreditMemo":
				CancelSalerOrderCreditMemo.magento_CancelSalesOrder(test1, rowNumber);
				break;
			case "CancelSalesorderVerification":
				CancelSalesorderVerification.verifyCancelOrderdetails_commentHistory(test1, rowNumber);
				break;
		
			case "CreditApp_NavigateFilter":
				CreditApp_NavigateFilter.VerifyCreditAppSelection(dataMap2.get(currentKeyWord+"++"), test1, rowNumber);
				break;
			case "CreditStatusVerification":
				CreditStatusVerification.VerifyCreditAppStatus(dataMap2.get(currentKeyWord+"++"), test1, rowNumber);
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

				navigateURL = ConfigFileReader.getPropertyVal("URL");
			}
			logger.info("Navigate to URL");
			Report.info("Navigating to URL: "+navigateURL);

			driver.navigate().to(navigateURL);
			driver.manage().window().maximize();
			driver.navigate().refresh();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			logger.info("Browser name is "+browserName);

			logger.info("App URL: "+ navigateURL);
			Values.app= navigateURL;
			Values.browser=browserName;
		}

	public void endBrowserSession() throws IOException {
		driver.close();
	}
	public void writeToSingleSheet(File filePath,String sheetToUpdate) throws IOException, InvalidFormatException {
		FileOutputStream outputStream = new FileOutputStream(filePath);
		FileInputStream fis = new FileInputStream(filePath);
		XSSFWorkbook workbook2 = new XSSFWorkbook(fis);
		XSSFWorkbook workbook= new XSSFWorkbook();
		XSSFSheet sheet;
		for(int i=0;i<dataMap2.size() ;i++) {
			Object[] keys = dataMap2.keySet().toArray();
			if (!keys[i].toString().toLowerCase().equals("suits") && !keys[i].toString().toLowerCase().equals("ic")) {
				if((sheetToUpdate+"++").equals(keys[i].toString())){
					sheet = workbook2.createSheet(sheetToUpdate);
				}else{
					sheet = workbook.getSheet(keys[i].toString());
				}
				int numCol = dataMap2.get(keys[i]).size();
				Object[] colArray = dataMap2.get(keys[i]).keySet().toArray();
				int rowNum = dataMap2.get(keys[i]).get(colArray[0]).size();
				for (int j = 0; j <= rowNum; j++) {
					Row row = sheet.createRow(j);
					if (j == 0) {
						for (int z = 0; z < numCol; z++) {
							Cell cell = row.createCell(z);
							cell.setCellValue(colArray[z].toString());
						}
					} else {

						for (int z = 0; z < numCol; z++) {
							Cell cell = row.createCell(z);
							cell.setCellValue((String) dataMap2.get(keys[i]).get(colArray[z]).get(j - 1));
						}
					}
				}
			}
		}
		workbook.write(outputStream);
		workbook.close();
	}
	public void writeToExcel(File filePath) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(filePath);
		XSSFWorkbook workbook= new XSSFWorkbook();;
		XSSFSheet sheet;
		for(int i=0;i<dataMap2.size() ;i++) {
			Object[] keys = dataMap2.keySet().toArray();
			if (!keys[i].toString().toLowerCase().equals("suits") && !keys[i].toString().toLowerCase().equals("ic")) {
				sheet = workbook.createSheet(keys[i].toString());
				workbook.getSheet(keys[i].toString());
				int numCol = dataMap2.get(keys[i]).size();
				Object[] colArray = dataMap2.get(keys[i]).keySet().toArray();
				int rowNum = dataMap2.get(keys[i]).get(colArray[0]).size();
				for (int j = 0; j <= rowNum; j++) {
					Row row = sheet.createRow(j);
					if (j == 0) {
						for (int z = 0; z < numCol; z++) {
							Cell cell = row.createCell(z);
							cell.setCellValue(colArray[z].toString());
						}
					} else {

						for (int z = 0; z < numCol; z++) {
							Cell cell = row.createCell(z);
							cell.setCellValue((String) dataMap2.get(keys[i]).get(colArray[z]).get(j - 1));
						}
					}
				}
			}
		}

		workbook.write(outputStream);
		workbook.close();
	}

	public File createFile() throws IOException {
		File myObj = new File(System.getProperty("user.dir")+"\\reports\\Datasheet.xlsx");
		return myObj;
	}




}
