package ic_MagentoPageObjects;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class MagentoRegisterNewUser {
	WebDriver driver;
	Action action;
	MagentoRetrieveCustomerDetailsPage RetriveCust;
	public MagentoRegisterNewUser(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		RetriveCust =new MagentoRetrieveCustomerDetailsPage(driver, dataTable2);
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
	//Inside account info

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

	//save new customer
	@FindBy(xpath = "//span[contains(text(),'Save Customer')]")
	WebElement Save_Customer;
	@FindBy(xpath = "//div[contains(text(),'You saved the customer.')]")
	WebElement Save_Customer_success;
	//Fetch partner number
	@FindBy(xpath = "//input[@name='customer[partner_number]']")
	WebElement BPnumber;

	public void CreateAccount_validateInfo_Backend(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws Exception {
		String AssociatedWebsite=input.get("Website").get(rowNumber);//"Incredible Connection";
		String Firstname = input.get("Firstname").get(rowNumber);//"Backend_Fisrtname";
		String Lastname = input.get("Lastname").get(rowNumber);//"Backend_Lastname";
		String Email = input.get("Email").get(rowNumber);//"TestAutomation1@gmail.com";
		String IDType = input.get("Identitynumber/passport").get(rowNumber);
		String IDNumber = input.get("SAID").get(rowNumber);//"7503226018089";
		String expPassport = input.get("Passport").get(rowNumber);
		int waitforelement =Integer.parseInt(input.get("DelayforElements").get(rowNumber));
		String resBPnumber = null;
		boolean ExpCustomerCreateSuccess = true;
		//Navigate to all customer
		navigateToCustomer(test);

		action.clickEle(Add_Customer, "Add new Customer", test);
		boolean resAccountinfo = action.elementExists(Account_Information, waitforelement);
		if(resAccountinfo==true){
			action.dropDownselectbyvisibletext(AssociatedWebsite_ele, AssociatedWebsite, "Website", test);
			action.writeText(Cust_Firstname, Firstname, "Customer firstname", test);
			action.writeText(Cust_Lastname, Lastname, "Customer lastname", test);
			action.writeText(Cust_Email, Email, "Customer Email", test);

			//Mandatory step to give id number or passport number for BP generation
			switch (IDType){
				case "SAID":
					action.writeText(Cust_ID, IDNumber, "Customer ID Number", test);
				case "Passport":
					action.writeText(passport, expPassport, "Customer Passport", test);
			}


			action.clickEle(Save_Customer, "Save_Customer", test);
			boolean resSavedcustomer = action.elementExists(Save_Customer_success, waitforelement);
			if(resSavedcustomer==true){
				action.CompareResult("verify New customer is created sucessfully in Backend magento", String.valueOf(ExpCustomerCreateSuccess), String.valueOf(resSavedcustomer), test);

				//navigate to the table and click edit
				RetriveCust.searchForCustomer(Email, test);
				action.waitExplicit(waitforelement);
				RetriveCust.tableData(Email, AssociatedWebsite, test);
				action.clickEle(Account_Information, "Account Information", test);
				if(action.waitUntilElementIsDisplayed(BPnumber, waitforelement)){
					resBPnumber = FetchDataFromCustInfo_MagentoBackend(BPnumber, "BP number", waitforelement, 5, test);
					 input.get("BPnumber").set(rowNumber,resBPnumber);
				}

				if(resBPnumber!=null){
					action.scrollToElement(BPnumber, "BP Number");
					action.CompareResult("verify BP number is  fetched : "+resBPnumber, String.valueOf("True"), String.valueOf("True"), test);
				}else{
					action.CompareResult("verify BP number is fetched : "+resBPnumber, String.valueOf("True"), String.valueOf("False"), test);
				}

			}else{
				action.CompareResult("verify New customer is created sucessfully in Backend magento", String.valueOf(ExpCustomerCreateSuccess), String.valueOf(resSavedcustomer), test);
			}
		}
	}

	public String FetchDataFromCustInfo_MagentoBackend(WebElement element,String elename,int TimetoLoadpage,int TotalTrycount,ExtentTest test) throws IOException{
		int trycount=1;
		String resData="";
		while(trycount<=TotalTrycount & resData.length()<1){
			action.refresh();
			action.explicitWait(2000);
			action.click(Account_Information, "Account_Information", test);
			if(action.elementExists(element, TimetoLoadpage)==true){
				resData = action.getAttribute(element, "value");
			}

			trycount++;
		}
		if(!resData.isEmpty() ||resData!=null){

			action.CompareResult("Verify "+elename+" is fetched sucessfully :"+resData,"True", "True", test);
			return resData;
		}else{
			action.CompareResult("Verify "+elename+" is not fetched sucessfully :"+resData,"True", "False", test);
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
			Thread.sleep(10000);
			String screenShotPath=action.getScreenShot(dateName);
			node.pass("User navigated to Allcustomer section"+ node.addScreenCaptureFromPath(screenShotPath));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			String screenShotPath=action.getScreenShot(dateName);
			node.fail("User navigated to Allcustomer section"+e.getMessage()+ node.addScreenCaptureFromPath(screenShotPath));
		}
	}

}
