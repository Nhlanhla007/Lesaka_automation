package ic_MagentoPageObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class admin_UserUpdate {
	
	 WebDriver driver;
	    Action action;
	    
	    MagentoRetrieveCustomerDetailsPage magRetri ;
		MagentoAccountInformation magAccInfo;

	    public admin_UserUpdate(WebDriver driver, DataTable2 dataTable2) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
	        magRetri = new MagentoRetrieveCustomerDetailsPage(driver, dataTable2);
	    }
	

	@FindBy(xpath="//*[@id=\"menu-magento-customer-customer\"]/a")
    private WebElement admin_Customer;
	
	@FindBy(xpath="//*[@id=\"menu-magento-customer-customer\"]/div/ul/li[1]/ul/li[1]/a/span")
    private WebElement admin_AllCustomer;
	
	@FindBy(xpath="//*[@id=\"container\"]/div/div[3]/div/div[1]/div/div[3]/div/button")
    private WebElement admin_filterBtn;
	
	
	@FindBy(xpath="//*[@id=\"container\"]/div/div[2]/div[1]/div[4]/div/div/button[2]/span")
	private WebElement applyFilter;
	
	@FindBy(xpath="//*[@id=\"container\"]/div/div[4]/table/tbody/tr[2]/td[17]/a")
    private WebElement admin_EditBtn;
	
	@FindBy(xpath="//*[@id=\"tab_customer\"]/span[1]")
    private WebElement admin_AccountInfoCustomer;
	
	//account Information customer
	@FindBy(xpath = "//input[@name='customer[firstname]']")
	private WebElement admin_firstname;
	
	@FindBy(xpath = "//input[@name='customer[firstname]']")
	private WebElement admin_firstUpdated;
	
	@FindBy(xpath = "//input[@name='customer[lastname]']")
	private WebElement admin_lastname;
	
	@FindBy(xpath = "//input[@name='customer[lastname]']")
	private WebElement admin_lastUpdated;
	
	@FindBy(xpath="//input[@name='customer[taxvat]']")
	private WebElement admin_taxVat;
	
	@FindBy(xpath="//input[@name='customer[email]']")
	private WebElement admin_email;
	
	@FindBy(xpath="//*[@id=\"tab_address\"]")
	private WebElement admin_AddressBtn;
					//*[@id="container"]/div/div/div[2]/div[2]/div/div[2]/fieldset/div[1]/button/span 
	@FindBy(xpath = "//*[@class=\"edit-default-billing-address-button action-additional\"]/span")//button[@title='Add New Customer'] /html[1]/body[1]/div[6]/aside[1]/div[2]/header[1]/div[1]/div[1]/button[1]/span[1]/span[1]
	private WebElement admin_billingEdit;	
	
	@FindBy(xpath="//*[@id=\"container\"]/div/div/div[2]/div[2]/div/div[2]/fieldset/div[3]/div/div[3]/table/tbody/tr[2]/td[10]/div/ul/li[1]/a")
	private WebElement admin_Edit;
	
	@FindBy(xpath="//input[@name='street[0]']")
	private WebElement admin_BillingStreetAddress;
	
	@FindBy(className =  "edit-default-shipping-address-button action-additional")
	private WebElement admin_shippingEdit;
	
	@FindBy(xpath="//input[@name='street[0]']")
	private WebElement admin_shippingStreetAddress;
	
	//*[@id="save"]/span/span
	@FindBy(xpath="//*[@id=\"save\"]/span/span")
	private WebElement admin_SaveCustomerBtn;
	
	@FindBy(xpath="//header/div[1]/div[1]/button[1]")
	private WebElement admin_SaveBillingBtn;
	
	@FindBy(xpath="//*[@id=\"messages\"]/div/div/div ")
	private WebElement admin_successSaved;
	
	List<String> streetAddresses;
	
	
	//@Step("Edit Customer information")
    public void editCustomerDetails(ArrayList<HashMap<String, ArrayList<String>>> adminSheets,ExtentTest test,int testcaseID){
    	int sheetRow1= findRowToRun(adminSheets.get(0), 0, testcaseID);
		//int sheetRow2= findRowToRun(adminSheets.get(1), 0, testcaseID);
    	
    	try {
    		
    		String firstName = adminSheets.get(0).get("firstName").get(sheetRow1);
	    	String lastName = adminSheets.get(0).get("lastName").get(sheetRow1);
	    	String taxVat = adminSheets.get(0).get("taxVat").get(sheetRow1);
	    	String email = adminSheets.get(0).get("emailAddress_input").get(sheetRow1);
	    	String emailFlag = adminSheets.get(0).get("email").get(sheetRow1);
	    	Random rand = new Random();
			String id = String.format("%04d", rand.nextInt(10000));
	    	
	    	//Default Billing address
	    	String billingAddress = adminSheets.get(0).get("billingAddress").get(sheetRow1);
	    	String billing_streetAddress = adminSheets.get(0).get("billing_streetAddress").get(sheetRow1);
	    	//String billing_provinceName = mySheets.get(0).get("billing_provinceName").get(sheetRow1);
	    	
	    	//Default shipping address
	    	String shippingAddress = adminSheets.get(0).get("shippingAddress").get(sheetRow1);
	    	String shipping_streetAddress = adminSheets.get(0).get("shipping_streetAddress").get(sheetRow1);
	    	//String shipping_provinceName = mySheets.get(0).get("shipping_provinceName").get(sheetRow1);
	    	
	    	//navigate to customer and search the customer to get the information;
	    	magRetri.navigateToCustomer(test);
	    	
	    	magRetri.searchForCustomer(email, test);
			magRetri.tableData(email, "Incredible Connection", test);
			
			action.click(admin_AccountInfoCustomer, "Click Account information", test);	    	
	    	action.explicitWait(3000);
			String firstNameText = action.getAttribute(admin_firstname, "value");
			if(firstName.equalsIgnoreCase("Yes")){
	    		
	    		String firstNameTextUpdated = "Updated" + id;
	    		action.clear(admin_firstname, "Removing first name");
	    		action.writeText(admin_firstname, firstNameTextUpdated,"first Name", test);
	    		adminSheets.get(0).get("adminFirstName_output").set(sheetRow1, firstNameTextUpdated);
	    		
	    		action.click(admin_SaveCustomerBtn, "Save", test);
	    		action.CompareResult("User Saved", "You saved the customer.", action.getText(admin_successSaved, "",test), test);
	    		
	    		if(action.waitUntilElementIsDisplayed(admin_EditBtn, 10000)) {
	    			action.explicitWait(4000);
	    		action.click(admin_EditBtn, "clicking edit to confirm", test);
	    		}
//	    		action.explicitWait(5000);
	    		action.waitUntilElementIsDisplayed(admin_AccountInfoCustomer,5);
	    		action.click(admin_AccountInfoCustomer, "clicking to account infor", test);
	    		
	    		String firstNameTextSaved = action.getAttribute(admin_firstUpdated, "value");
	    		action.CompareResult("compare updated first name", firstNameTextUpdated, firstNameTextSaved, test);
	    	}else {
	    		adminSheets.get(0).get("adminFirstName_output").set(sheetRow1, firstNameText);
	    	}
			
			action.click(admin_AccountInfoCustomer, "Click Account information", test);
			String lastNameText = action.getAttribute(admin_lastname, "value");
	    	if(lastName.equalsIgnoreCase("yes")){
	    		
	    		String lastNameTextUpdated = "Updated" + id;
	    		action.clear(admin_lastname, "Remove last name");
	    		action.writeText(admin_lastname, lastNameTextUpdated,"last Name", test);
	    		adminSheets.get(0).get("adminLastName_output").set(sheetRow1, lastNameTextUpdated);
	    		
	    		action.click(admin_SaveCustomerBtn, "Save", test);
	    		action.CompareResult("User Saved", "You saved the customer.", action.getText(admin_successSaved, "",test), test);
	    		
	    		action.click(admin_EditBtn, "clicking edit to confirm", test);
	    		action.explicitWait(5000);
	    		action.click(admin_AccountInfoCustomer, "Check if the lastname is updated", test);
	    		
	    		String lastNameTextSaved = action.getAttribute(admin_lastUpdated, "value");
	    		action.CompareResult("compare updated last name", lastNameTextUpdated, lastNameTextSaved, test);
	    	}else {
	    		adminSheets.get(0).get("adminLastName_output").set(sheetRow1, lastNameText);
	    	}

	    	action.click(admin_AccountInfoCustomer, "account infor", test);
	    	String taxVatText = action.getAttribute(admin_taxVat, "value");
	    	if(taxVat.equalsIgnoreCase("yes")){
	    		String taxVatTextUpdated = "Updated" + id;
	    		action.clear(admin_taxVat, "removing tax/vat");
	    		action.writeText(admin_taxVat, taxVatTextUpdated ," VAT/TAX", test);
	    		adminSheets.get(0).get("adminTaxVat_output").set(sheetRow1, taxVatTextUpdated);
	    		
	    		action.click(admin_SaveCustomerBtn, "Save", test);
	    		action.CompareResult("User Saved", "You saved the customer.", action.getText(admin_successSaved, "",test), test);
	    		
	    		action.click(admin_EditBtn, "clicking edit to confirm", test);
	    		action.explicitWait(5000);
	    		//admin_AccountInfoCustomer.click();
	    		action.click(admin_AccountInfoCustomer, "account infor", test);
	    		
	    	}else {
	    		adminSheets.get(0).get("adminTaxVat_output").set(sheetRow1, taxVatText);
	    	}
	    	
	    	action.click(admin_AccountInfoCustomer, "account infor", test);
	    	String emailText = action.getAttribute(admin_email, "value");
	    	if(emailFlag.equalsIgnoreCase("yes")){
	    		String emailTextUpdated = id+"updated@jdg.co.za";
	    		action.clear(admin_email, "removing the email");
	    		action.writeText(admin_email,emailTextUpdated ,"last Name", test);
	    		adminSheets.get(0).get("adminEmail_output").set(sheetRow1, emailTextUpdated);
	    		
	    		action.click(admin_SaveCustomerBtn, "Save", test);	
	    		action.CompareResult("User Saved", "You saved the customer.", action.getText(admin_successSaved, "",test), test);
	    		
	    		action.click(admin_EditBtn, "clicking edit to confirm", test);
	    		action.explicitWait(5000);
	    		//admin_AccountInfoCustomer.click();
	    		action.click(admin_AccountInfoCustomer, "customer infor", test);
	    	}else {
	    		adminSheets.get(0).get("adminEmail_output").set(sheetRow1, emailText);
	    	}
	    	
	    	//action.click(admin_AddressBtn, "Change addresses", test);
	    	//admin_AddressBtn.click();// update it
	    	action.click(admin_AddressBtn, "Address button", test);
	    	action.explicitWait(5000);
	    	//Billing Address
	    	

	    	
	    	if(billingAddress.equalsIgnoreCase("Yes")){
	    		action.click(admin_billingEdit, "Edit", test);
	    		
		    	Random r = new Random();
	    		//String randomAddres = streetAddresses.get(randomitem);
	    		
	    		streetAddresses = new ArrayList<>();
	    		streetAddresses.add("98 Van Riebeeck Avenue");
	    		streetAddresses.add("28 Harrison Street");
	    		streetAddresses.add("45 Zenith Drive");
	    		streetAddresses.add("510 Mississippi Street");
	    		streetAddresses.add("4 Loop Street");
	    		streetAddresses.add("234 Glover Avenue");
	    		streetAddresses.add("72 Ceramic Curve");
	    		streetAddresses.add("15 Alice Lane");
	    		streetAddresses.add("315 York Avenue");
	    		streetAddresses.add("35 Ballyclare Drive");
	    		streetAddresses.add("100 Northern Parkway");
	    		int randomitem = r.nextInt(streetAddresses.size());
	    		
	    		String streetAdressText = action.getAttribute(admin_BillingStreetAddress, "value");
	    		if(billing_streetAddress.equalsIgnoreCase("yes")){
		    		
		    		String streetAdressTextUpdated = streetAddresses.get(randomitem);
		    		action.clear(admin_BillingStreetAddress, "Removing Street address");
	    	
		    		action.writeText(admin_BillingStreetAddress, streetAdressTextUpdated, "Street address", test);
		    		adminSheets.get(0).get("adminBilling_streetAddress_output").set(sheetRow1, streetAdressTextUpdated);
		    		
		    		action.explicitWait(6000);
		    		action.click(admin_SaveBillingBtn, "Save", test);
		    	}else {
		    		adminSheets.get(0).get("adminBilling_streetAddress_output").set(sheetRow1, streetAdressText);
		    	}
	    		action.explicitWait(5000);
	    		action.click(admin_AddressBtn, "address button", test);
	    		action.explicitWait(5000);
	    		
	    		//address shipping address
		    	if(shippingAddress.equalsIgnoreCase("Yes")){
		    		
		    		action.javaScriptClick(admin_shippingEdit, "Shipping edit clicked", test);
		    		String streetAddressText = action.getAttribute(admin_shippingStreetAddress, "value");
		    	if(shipping_streetAddress.equals("yes")){
		    		action.explicitWait(5000);
		    		
		    		String streetAddressTextUpdated = streetAddressText + "Updated" + id;
		    		action.clear(admin_shippingStreetAddress, "Removing shipping Street address");
		    		
		    		action.writeText(admin_shippingStreetAddress,streetAddressText, "Street address", test);
		    		adminSheets.get(0).get("adminShipping_streetAddress_Output").set(sheetRow1, streetAddressTextUpdated);
		    		
		    		action.click(admin_SaveBillingBtn, "Save", test);
		    		
		    	}else {
		    		adminSheets.get(0).get("adminShipping_streetAddress_Output").set(sheetRow1, streetAddressText);
		    	}
		    	}
		    	} 	
	    	
    	} catch (Exception e) {
			e.printStackTrace();
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
	
}
