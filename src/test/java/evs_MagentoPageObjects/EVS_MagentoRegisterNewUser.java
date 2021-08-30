package evs_MagentoPageObjects;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import ic_MagentoPageObjects.MagentoRetrieveCustomerDetailsPage;
import utils.Action;
import utils.DataTable2;

public class EVS_MagentoRegisterNewUser {
	WebDriver driver;
	Action action;
	EVS_MagentoRetrieveCustomerDetailsPage RetriveCust;
	int ajaxTimeOutInSeconds = EVS_Magento_Login.ajaxTimeOutInSeconds;
	DataTable2 dataTable2;
	
	public EVS_MagentoRegisterNewUser(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		RetriveCust =new EVS_MagentoRetrieveCustomerDetailsPage(driver, dataTable2);
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
	//Inside account info

	@FindBy(xpath = "//select[@name='customer[website_id]']")
	WebElement AssociatedWebsite_ele;
	
	@FindBy(xpath = "//*[@name=\"customer[group_id]\"]")
	WebElement Group_ele;
	
	@FindBy(xpath = "//*[@class=\"admin__action-multiselect-label\"]/span[contains(text(),'Default (General)')]")
	WebElement Group_Default;

	@FindBy(xpath = "//input[@name='customer[firstname]']")
	WebElement Cust_Firstname;
	@FindBy(xpath = "//input[@name='customer[lastname]']")
	WebElement Cust_Lastname;
	@FindBy(xpath = "//input[@name='customer[email]']")
	WebElement Cust_Email;
	@FindBy(xpath = "//input[@name='customer[identity_number]']")
	WebElement Cust_ID;
	@FindBy(xpath = "//input[@name='customer[cellphone_number]']")
	WebElement Cust_Phonenumber;
	
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
		String AssociatedWebsite=dataTable2.getValueOnCurrentModule("Website");
		String Group = dataTable2.getValueOnCurrentModule("Group");
		String Firstname = dataTable2.getValueOnCurrentModule("Firstname");
		String Lastname = dataTable2.getValueOnCurrentModule("Lastname");
		String Email = dataTable2.getValueOnCurrentModule("Email");
		String IDType =dataTable2.getValueOnCurrentModule("Identitynumber/passport");
		String Phonenumber = dataTable2.getValueOnCurrentModule("Cellphone");
		String IDNumber = dataTable2.getValueOnCurrentModule("SAID");
		String expPassport = dataTable2.getValueOnCurrentModule("Passport");
		int waitforelement = Integer.parseInt(dataTable2.getValueOnCurrentModule("DelayforElements"));
		String resBPnumber = null;
		boolean ExpCustomerCreateSuccess = true;
		navigateToCustomer(test);

		action.click(Add_Customer, "Add new Customer", test);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		action.explicitWait(10000);
		boolean resAccountinfo = action.elementExists(Account_Information, waitforelement);
		if(resAccountinfo==true){
			action.dropDownselectbyvisibletext(AssociatedWebsite_ele, AssociatedWebsite, "Website", test);
			action.explicitWait(2000);
			action.click(Group_ele, Group, test);
			action.explicitWait(2000);
			action.click(Group_Default, Group, test);
			action.explicitWait(2000);
		    action.writeText(Cust_Firstname, Firstname, "Customer firstname", test);
			action.writeText(Cust_Lastname, Lastname, "Customer lastname", test);
			action.writeText(Cust_Email, Email, "Customer Email", test);

			//Mandatory step to give id number or passport number for BP generation
			switch (IDType){
				case "SAID":
					action.writeText(Cust_ID, IDNumber, "Customer ID Number", test);
					break;
				case "Passport":
					action.writeText(passport, expPassport, "Customer Passport", test);
					break;
			}
			action.writeText(Cust_Phonenumber, Phonenumber, "Customer Cellphone number", test);
			
			action.clickEle(Save_Customer, "Save_Customer", test);
			action.waitForPageLoaded(ajaxTimeOutInSeconds);
			action.ajaxWait(ajaxTimeOutInSeconds, test);
			action.explicitWait(3000);
			boolean resSavedcustomer = action.elementExists(Save_Customer_success, waitforelement);
			if(resSavedcustomer==true){
				action.CompareResult("New customer is created successfully in Backend magento", String.valueOf(ExpCustomerCreateSuccess), String.valueOf(resSavedcustomer), test);

				RetriveCust.searchForCustomer(Email, test);
				action.waitExplicit(waitforelement);
				RetriveCust.tableData(Email, AssociatedWebsite, test);
//				action.clickEle(Account_Information, "Account Information", test);
				/*if(action.waitUntilElementIsDisplayed(BPnumber, waitforelement)){
					resBPnumber = FetchDataFromCustInfo_MagentoBackend(BPnumber, "BP number", waitforelement, 5, test);
					 input.get("BPnumber").set(rowNumber,resBPnumber);
				}*/

				/*if(resBPnumber!=null){
					action.scrollToElement(BPnumber, "BP Number");
					action.CompareResult("BP number is  fetched : "+resBPnumber, String.valueOf("True"), String.valueOf("True"), test);
				}else{
					action.CompareResult("BP number is fetched : "+resBPnumber, String.valueOf("True"), String.valueOf("False"), test);
				}*/

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

			action.CompareResult(elename+" is fetched sucessfully :"+resData,"True", "True", test);
			return resData;
		}else{
			action.CompareResult(elename+" is not fetched sucessfully :"+resData,"True", "False", test);
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
			action.waitForPageLoaded(ajaxTimeOutInSeconds);
			action.ajaxWait(ajaxTimeOutInSeconds, test);
			/*String screenShotPath=action.getScreenShot(dateName);
			node.pass("User navigated to Allcustomer section"+ node.addScreenCaptureFromPath(screenShotPath));*/
			node.pass("Navigated to Magento Customers", MediaEntityBuilder.createScreenCaptureFromBase64String(action.takeScreenShotAsBase64()).build());

		} catch (Exception e) {
			// TODO Auto-generated catch block
//			String screenShotPath=action.getScreenShot(dateName);
			node.fail(MarkupHelper.createLabel("Unable to navigate to Magento Customers", ExtentColor.RED).getMarkup() + "<br>"+ e.getMessage()+ "</br>", MediaEntityBuilder.createScreenCaptureFromBase64String(action.takeScreenShotAsBase64()).build());
		}
	}

}
