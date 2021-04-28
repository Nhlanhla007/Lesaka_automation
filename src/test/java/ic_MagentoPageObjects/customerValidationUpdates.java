package ic_MagentoPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;


public class customerValidationUpdates {
	WebDriver driver;
	Action action;
	
	public customerValidationUpdates(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		
		magRetri = new MagentoRetrieveCustomerDetailsPage(driver, dataTable2);
		magAccInfo = new MagentoAccountInformation(driver, dataTable2);
		
	}
	
	@FindBy(xpath = "//input[@name='customer[firstname]']")
	private WebElement admin_firstname;
	
	@FindBy(xpath="//*[@id=\"OYF1IMR\"]")
	private WebElement admin_firstUpdated;
	
	@FindBy(xpath = "//input[@name='customer[lastname]']")
	private WebElement admin_lastname;
	
	@FindBy(xpath="//*[@id=\"D6H32P3\"]")
	private WebElement admin_lastUpdated;
	
	@FindBy(xpath="//input[@name='customer[taxvat]']")
	private WebElement admin_taxVat;
	
	@FindBy(xpath="//input[@name='customer[email]']")
	private WebElement admin_email;
	
	@FindBy(xpath="//*[@id=\"tab_address\"]/span[1]")
	private WebElement admin_AddressBtn;
		
	@FindBy(xpath="//input[@name='street[0]']")
	private WebElement admin_Billing_streetAddress;
	
	@FindBy(xpath="//input[@name='street[0]']")
	private WebElement admin_Shipping_streetAddress;
	
	MagentoRetrieveCustomerDetailsPage magRetri;
	
	MagentoAccountInformation magAccInfo;
	
	public void VerifyCustomerinfoUpadtes(ArrayList<HashMap<String, ArrayList<String>>> adminSheets,ExtentTest test,int testcaseID) throws Exception {
		 
		//magRetri.retrieveCustomerDetails(adminSheets, test, rowNumber);
		
		//magAccInfo.VadidateCustomerInfo_backend(adminSheets, test, rowNumber);
		
		int sheetRow1= findRowToRun(adminSheets.get(0), 0, testcaseID);
		int sheetRow2= findRowToRun(adminSheets.get(1), 0, testcaseID);
		int loadtime=20;
		
		String expFirstName = adminSheets.get(0).get("adminFirstName_output").get(sheetRow1);
    	String expLastName = adminSheets.get(0).get("adminLastName_output").get(sheetRow1);
    	String expTaxVat = adminSheets.get(0).get("adminTaxVat_output").get(sheetRow1);
    	String expEmail = adminSheets.get(0).get("adminEmail_output").get(sheetRow1);
    	String ExpPassWord = adminSheets.get(0).get("passWord").get(sheetRow1);
    	
    	//Default Billing address
    	String billingAddress = adminSheets.get(0).get("billingAddress").get(sheetRow1);
    	String expBilling_streetAddress = adminSheets.get(0).get("adminBilling_streetAddress_output").get(sheetRow1);
    	
    	//Default shipping address
    	String shippingAddress = adminSheets.get(0).get("shippingAddress").get(sheetRow1);
    	String expShipping_streetAddress = adminSheets.get(0).get("shipping_streetAddress_Output").get(sheetRow1);
    	//String shipping_provinceName = adminSheets.get(0).get("shipping_provinceName").get(sheetRow1);
    	
    	//Starts from Account information tab
		action.waitExplicit(loadtime);
		String ActualFirstname = action.getAttribute(admin_firstname, "value");
		String ActualLastname = action.getAttribute(admin_lastname, "value");
		String ActualEmail = action.getAttribute(admin_email, "value");
		String ActualTaxVat = action.getAttribute(admin_taxVat, "value");
		String ActualBilling_streetAddress = action.getAttribute(admin_Billing_streetAddress, "value");
		String ActualShipping_streetAddress = action.getAttribute(admin_Billing_streetAddress, "value");
		
		//navigate to the customer information 
		magRetri.navigateToCustomer(test);
    	
    	magRetri.searchForCustomer(expEmail, test);
		magRetri.tableData(expEmail, "Incredible Connection", test);
		
    	action.CompareResult("Verify the First name of user in Magento", expFirstName, ActualFirstname, test);
		
		action.CompareResult("Verify the Last name of user in Magento", expLastName, ActualLastname, test);
		
		action.CompareResult("Verify the Tax/Vat of user in Magento", expTaxVat, ActualTaxVat, test);
		
		action.CompareResult("Verify the Email of user in Magento", expEmail, ActualEmail, test);

		action.CompareResult("Verify the billing street address for the user in Magento", expBilling_streetAddress, ActualBilling_streetAddress, test);
		
		action.CompareResult("Verify the shipping street address for the user in Magento", expShipping_streetAddress, ActualShipping_streetAddress, test);
	
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
}