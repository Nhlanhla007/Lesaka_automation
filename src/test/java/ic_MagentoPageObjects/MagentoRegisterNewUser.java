package ic_MagentoPageObjects;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.DataTable2;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MagentoRegisterNewUser {
	WebDriver driver;
	Action action;
	MagentoRetrieveCustomerDetailsPage RetriveCust;
	DataTable2 dataTable2;

	int ajaxTimeOutInSeconds = ic_Magento_Login.ajaxTimeOutInSeconds;
	public MagentoRegisterNewUser(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		RetriveCust =new MagentoRetrieveCustomerDetailsPage(driver, dataTable2);
		this.dataTable2 = dataTable2;
	}
	//navigate to all customer
	@FindBy(xpath = "//*[@class=\"admin__menu\"]/ul[@id='nav']/li[@id=\"menu-magento-customer-customer\"]/a/span[contains(text(),\"Customers\")]")
	WebElement customerTab;
	@FindBy(xpath = "//li[@role=\"menu-item\"]/a/span[contains(text(),'All Customers')]")
	WebElement allCustomerTab;

	@FindBy(xpath = "//button[@title='Add New Customer']")
	WebElement Add_Customer;

	@FindBy(xpath = "//*[@id='tab_customer']/span[1]")
	WebElement Account_Information;

	@FindBy(xpath = "//select[@name='customer[website_id]']")
	WebElement AssociatedWebsite_ele;

	@FindBy(xpath = "//input[@name='customer[firstname]']")
	WebElement Cust_Firstname;

	@FindBy(xpath = "//input[@name='customer[lastname]']")
	WebElement Cust_Lastname;

	@FindBy(xpath = "//input[@name='customer[email]']")
	WebElement Cust_Email;

	@FindBy(xpath = "//input[@name='customer[identity_number]']")
	WebElement Cust_ID;
	
	@FindBy(name = "customer[passport_number]")
	WebElement passport;

	@FindBy(name = "customer[cellphone_number]")
	WebElement cellPhone;

	//save new customer
	@FindBy(xpath = "//span[contains(text(),'Save Customer')]")
	WebElement Save_Customer;

	@FindBy(xpath = "//div[contains(text(),'You saved the customer.')]")
	WebElement Save_Customer_success;

	@FindBy(xpath = "//input[@name='customer[partner_number]']")
	WebElement BPnumber;

	public void CreateAccount_validateInfo_Backend(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws Exception {
		String AssociatedWebsite=dataTable2.getValueOnCurrentModule("Website");//input.get("Website").get(rowNumber);;
		String Firstname = dataTable2.getValueOnCurrentModule("Firstname");//input.get("Firstname").get(rowNumber);
		String Lastname = dataTable2.getValueOnCurrentModule("Lastname");//input.get("Lastname").get(rowNumber);
		String Email = dataTable2.getValueOnCurrentModule("Email");//input.get("Email").get(rowNumber);
		String IDType =dataTable2.getValueOnCurrentModule("Identitynumber/passport");// input.get("Identitynumber/passport").get(rowNumber);
		String cellPhoneNumber = dataTable2.getValueOnCurrentModule("Cellphone");//input.get("Cellphone").get(rowNumber);
		String IDNumber = dataTable2.getValueOnCurrentModule("SAID");//input.get("SAID").get(rowNumber);
		String expPassport = dataTable2.getValueOnCurrentModule("Passport");//input.get("Passport").get(rowNumber);
		int waitforelement = Integer.parseInt(dataTable2.getValueOnCurrentModule("DelayforElements")/* input.get("DelayforElements").get(rowNumber) */);
		
		String resBPnumber = null;
		boolean ExpCustomerCreateSuccess = true;
		//Navigate to all customer
		navigateToCustomer(test);

		action.click(Add_Customer, "Add new Customer", test);
		action.waitForPageLoaded(ajaxTimeOutInSeconds);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		action.explicitWait(10000);
		boolean resAccountinfo = action.waitUntilElementIsDisplayed(Account_Information, waitforelement);

		if(resAccountinfo==true){
			action.dropDownselectbyvisibletext(AssociatedWebsite_ele, AssociatedWebsite, "Website", test);
			action.writeText(Cust_Firstname, Firstname, "Customer firstname", test);
			action.writeText(Cust_Lastname, Lastname, "Customer lastname", test);
			action.writeText(Cust_Email, Email, "Customer Email", test);
			action.writeText(cellPhone,cellPhoneNumber,"Cellphone Number",test);

			//Mandatory step to give id number or passport number for BP generation
			switch (IDType){
				case "SAID":
					action.writeText(Cust_ID, IDNumber, "Customer ID Number", test);
				case "Passport":
					action.writeText(passport, expPassport, "Customer Passport", test);
			}


			action.click(Save_Customer, "Save_Customer", test);
			
			action.waitForPageLoaded(ajaxTimeOutInSeconds);
			action.ajaxWait(ajaxTimeOutInSeconds, test);
			
			boolean resSavedcustomer = action.waitUntilElementIsDisplayed(Save_Customer_success, waitforelement);
			if(resSavedcustomer==true){
				action.CompareResult("New customer is created successfully in Backend Magento", String.valueOf(ExpCustomerCreateSuccess), String.valueOf(resSavedcustomer), test);

				RetriveCust.searchForCustomer(Email, test);
				RetriveCust.tableData(Email, AssociatedWebsite, test);
//				action.click(Account_Information, "Account Information", test);
				/*if(action.waitUntilElementIsDisplayed(BPnumber, waitforelement)){
					resBPnumber = FetchDataFromCustInfo_MagentoBackend(BPnumber, "BP number", waitforelement, 15, test);
					 input.get("BPnumber").set(rowNumber,resBPnumber);
				}*/

				/*
				 * if(resBPnumber!=null | resBPnumber!="" | !resBPnumber.isEmpty() ){
				 * action.scrollToElement(BPnumber, "BP Number");
				 * action.CompareResult("verify BP number is  fetched : "+resBPnumber,
				 * String.valueOf("True"), String.valueOf("True"), test); }else{
				 * action.scrollToElement(BPnumber, "BP Number");
				 * action.CompareResult("verify BP number is fetched : "+resBPnumber,
				 * String.valueOf("True"), String.valueOf("False"), test); }
				 */

			}else{
				action.CompareResult("New customer is created successfully in Backend Magento", String.valueOf(ExpCustomerCreateSuccess), String.valueOf(resSavedcustomer), test);
			}
		}
	}

	public String FetchDataFromCustInfo_MagentoBackend(WebElement element,String elename,int TimetoLoadpage,int TimeOutinSecond,ExtentTest test) throws Exception {
		int trycount=1;
		String resData="";
		long startTime = System.currentTimeMillis(); // ... long finish = System.currentTimeMillis(); long timeElapsed = finish - start
		Instant start = Instant.now();
		int elapsedTime = 0;
		System.out.println(".....................................");
		System.out.println("elementName: "+elename);
		System.out.println(".....................................");
		while(elapsedTime<=TimeOutinSecond & resData.length()<1){
			action.refresh();
			action.waitForPageLoaded(TimetoLoadpage);
			action.click(Account_Information, "Account_Information", test);
			if(action.elementExists(element, TimetoLoadpage)==true){
				resData = action.getAttribute(element, "value");
			}
			
//			trycount++;
			Thread.sleep(TimetoLoadpage * 1000);
			long endTime = System.currentTimeMillis();
			long elapsedTimeInMils = endTime-startTime;
			elapsedTime = ((int) elapsedTimeInMils)/1000;
			System.out.println("elapsedTime: "+elapsedTime);
//			Instant finish = Instant.now();
//			long timeElapsed = Duration.between(start,finish).toSeconds();
		}
		if(resData.isEmpty() | resData==null | resData == ""){
			action.scrollElemetnToCenterOfView(element,elename,test);
			action.CompareResult(elename+" is fetched sucessfully :"+resData,"True", "False", test);
			return resData;
		}else{
			action.scrollElemetnToCenterOfView(element,elename,test);
			action.CompareResult(elename+" is fetched sucessfully :"+resData,"True", "True", test);
			return resData;
		}
	}

	public void navigateToCustomer(ExtentTest test) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
		ExtentTest node=test.createNode("Check User navigated to All customer section ");
		try {
			action.click(customerTab, "Customer Tab", test);
			if(action.waitUntilElementIsDisplayed(allCustomerTab, 10000)) {
			action.click(allCustomerTab, "All Customers Tab", test);
			}
			
			action.waitForJStoLoad(ajaxTimeOutInSeconds);
			action.ajaxWait(ajaxTimeOutInSeconds, test);
			/*String screenShotPath=action.getScreenShot(dateName);
			node.pass("User navigated to Allcustomer section"+ node.addScreenCaptureFromPath(screenShotPath));*/
			node.pass("Navigated to Magento Customers", MediaEntityBuilder.createScreenCaptureFromBase64String(action.takeScreenShotAsBase64()).build());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			/*String screenShotPath=action.getScreenShot(dateName);
			node.fail("User navigated to Allcustomer section"+e.getMessage()+ node.addScreenCaptureFromPath(screenShotPath));*/
			node.fail(MarkupHelper.createLabel("Unable to navigate to Magento Customers", ExtentColor.RED).getMarkup() + "<br>"+ e.getMessage()+ "</br>", MediaEntityBuilder.createScreenCaptureFromBase64String(action.takeScreenShotAsBase64()).build());
		}
	}

}
