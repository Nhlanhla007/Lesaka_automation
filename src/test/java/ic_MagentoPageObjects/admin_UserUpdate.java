package ic_MagentoPageObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;

public class admin_UserUpdate {
	
	 WebDriver driver;
	    Action action;

	    public admin_UserUpdate(WebDriver driver) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);

	    }
	

	@FindBy(xpath="//*[@id=\"menu-magento-customer-customer\"]/a")
    private WebElement admin_Customer;
	
	@FindBy(xpath="//*[@id=\"menu-magento-customer-customer\"]/div/ul/li[1]/ul/li[1]/a/span")
    private WebElement admin_AllCustomer;
	
	@FindBy(xpath="//*[@id=\"container\"]/div/div[3]/div/div[1]/div/div[3]/div/button")
    private WebElement admin_filterBtn;
	
	
	@FindBy(xpath="//*[@id=\"KXLWA9I\"]")
	private WebElement emailSearch;
	
	@FindBy(xpath="//*[@id=\"container\"]/div/div[2]/div[1]/div[4]/div/div/button[2]/span")
	private WebElement applyFilter;
	
	@FindBy(xpath="//*[@id=\"container\"]/div/div[4]/table/tbody/tr[2]/td[17]/a")
    private WebElement admin_EditBtn;
	
	@FindBy(xpath="//*[@id=\"tab_customer\"]/span[1]")
    private WebElement admin_AccountInfoCustomer;
	
	//account Information customer
	@FindBy(xpath="//*[@id=\"OYF1IMR\"]")
	private WebElement admin_firstname;
	
	@FindBy(xpath="//*[@id=\"OYF1IMR\"]")
	private WebElement admin_firstUpdated;
	
	@FindBy(xpath="//*[@id=\"D6H32P3\"]")
	private WebElement admin_lastname;
	
	@FindBy(xpath="//*[@id=\"D6H32P3\"]")
	private WebElement admin_lastUpdated;
	
	@FindBy(xpath="//*[@id=\"E6RB8SX\"]")
	private WebElement admin_taxVat;
	
	@FindBy(xpath="//*[@id=\"HKQ59L2\"]")
	private WebElement admin_email;
	
	@FindBy(xpath="//*[@id=\"tab_address\"]/span[1]")
	private WebElement admin_AddressBtn;
	
	@FindBy(xpath="//*[@id=\"container\"]/div/div/div[2]/div[3]/div/div[2]/fieldset/div[1]/button/span")
	private WebElement admin_BillingEdit;
	
	@FindBy(xpath="//*[@id=\"MTVB4NQ\"]")
	private WebElement admin_BillingStreetAddress;
	
	@FindBy(xpath="//*[@id=\"container\"]/div/div/div[2]/div[3]/div/div[2]/fieldset/div[2]/button/span")
	private WebElement admin_shippingEdit;
	
	@FindBy(xpath="//*[@id=\"MTVB4NQ\"]")
	private WebElement admin_shippingStreetAddress;
	
	//*[@id="save"]/span/span
	@FindBy(xpath="//*[@id=\"save\"]/span/span")
	private WebElement admin_SaveCustomerBtn;
	
	@FindBy(xpath="//*[@id=\"messages\"]/div/div/div ")
	private WebElement admin_successSaved;
	
	
	
	
	//@Step("Edit Customer information")
    public void editCustomerDetails(ArrayList<HashMap<String, ArrayList<String>>> adminSheets,ExtentTest test,int testcaseID){
    	int sheetRow1= findRowToRun(adminSheets.get(0), 0, testcaseID);
	//	int sheetRow2= findRowToRun(mySheets.get(1), 0, testcaseID);
    	
    	try {
    		
    		String firstName = adminSheets.get(0).get("firstName").get(sheetRow1);
	    	String lastName = adminSheets.get(0).get("lastName").get(sheetRow1);
	    	String taxVat = adminSheets.get(0).get("taxVat").get(sheetRow1);
	    	String email = adminSheets.get(0).get("email").get(sheetRow1);
	    	
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
	    	
	    	
	    	admin_Customer.click();
	    	admin_AllCustomer.click();
	    	
	    	admin_filterBtn.click();
	    	
	    	//seach using email
	    	
	    	applyFilter.click();
	    	//wait
	    	
	    	admin_EditBtn.click();
	    	
	    	admin_AccountInfoCustomer.click();
	if(firstName.equalsIgnoreCase("Yes")){
	    		
	    		admin_AccountInfoCustomer.click();
	    		
	    		String firstNameText = action.getAttribute(admin_firstname, "value");
	    		String firstNameTextUpdated = firstNameText + "Updated" + id;
	    		admin_firstname.clear();
	    		action.writeText(admin_firstname, firstNameTextUpdated,"first Name", test);
	    		adminSheets.get(0).get("adminFirstName_output").set(sheetRow1, firstNameTextUpdated);
	    		
	    		action.click(admin_SaveCustomerBtn, "Save", test);
	    		action.CompareResult("User Saved", "You saved the customer.", action.getText(admin_successSaved, ""), test);
	    		
	    		admin_AccountInfoCustomer.click();
	    		
	    		String firstNameTextSaved = admin_firstUpdated.getText();
	    		action.CompareResult("compare updated first name", firstNameTextUpdated, firstNameTextSaved, test);
	    	}
	    	if(lastName.equalsIgnoreCase("yes")){
	    		
	    		admin_AccountInfoCustomer.click();
	    		String lastNameText = action.getAttribute(admin_lastname, "value");
	    		String lastNameTextUpdated = lastNameText + "Updated" + id;
	    		admin_lastname.clear();
	    		action.writeText(admin_lastname, lastNameTextUpdated,"last Name", test);
	    		adminSheets.get(0).get("adminLastName_output").set(sheetRow1, lastNameTextUpdated);
	    		
	    		action.click(admin_SaveCustomerBtn, "Save", test);
	    		action.CompareResult("User Saved", "You saved the account information.", action.getText(admin_successSaved, ""), test);
	    		
	    		admin_AccountInfoCustomer.click();
	    		
	    		String lastNameTextSaved = admin_lastUpdated.getText();
	    		action.CompareResult("compare updated last name", lastNameText+ "Updated", lastNameTextSaved, test);
	    	}
	    	
	    	if(taxVat.equalsIgnoreCase("yes")){
	    		admin_AccountInfoCustomer.click();
	    		String taxVatText = action.getAttribute(admin_taxVat, "value");
	    		String taxVatTextUpdated = taxVatText + "Updated" + id;
	    		admin_taxVat.clear();
	    		action.writeText(admin_taxVat, taxVatText+ "Updated"," VAT/TAX", test);
	    		adminSheets.get(0).get("adminTaxVat_output").set(sheetRow1, taxVatTextUpdated);
	    		
	    		action.click(admin_SaveCustomerBtn, "Save", test);
	    		action.CompareResult("User Saved", "You saved the account information.", action.getText(admin_successSaved, ""), test);
	    		admin_AccountInfoCustomer.click();
	    		
	    	}
	    	
	    	if(email.equalsIgnoreCase("yes")){
	    		admin_AccountInfoCustomer.click();
	   
	    		String emailText = action.getAttribute(admin_email, "");
	    		String emailTextUpdated = emailText + "Updated" + id;
	    		
	    		admin_email.clear();
	    		action.writeText(admin_email,"Updated"+ emailText ,"last Name", test);
	    		adminSheets.get(0).get("adminEmail_output").set(sheetRow1, emailTextUpdated);
	    		
	    		action.click(admin_SaveCustomerBtn, "Save", test);	
	    		action.CompareResult("User Saved", "You saved the account information.", action.getText(admin_successSaved, ""), test);
	    		admin_AccountInfoCustomer.click();
	 
	    		
	    	}
	    	
	    	action.click(admin_AddressBtn, "Change addresses", test);
	    	
	    	//Billing Address
	    	if(billingAddress.equalsIgnoreCase("Yes")){
	    		action.click(admin_BillingEdit, "Change Billing address", test);
	    		//ic_BillingAddress.click();
	    		
	    		if(billing_streetAddress.equalsIgnoreCase("yes")){
		    		String streetAdressText = action.getAttribute(admin_BillingStreetAddress, "value");
		    		String streetAdressTextUpdated = streetAdressText + "Updated" + id;
		    		action.clear(admin_BillingStreetAddress, "Removing Streeta address");
	    	
		    		action.writeText(admin_BillingStreetAddress, streetAdressTextUpdated, "Street address", test);
		    		adminSheets.get(0).get("adminBilling_streetAddress_output").set(sheetRow1, streetAdressTextUpdated);
		    		
		    		action.click(admin_SaveCustomerBtn, "Save", test);
		    	
		    	}
	    		
	    		
	    		//address shipping address
		    	if(shippingAddress.equalsIgnoreCase("Yes")){
		    		action.click(admin_shippingEdit, "Change Shipping address", test);
		    	
		    	if(shipping_streetAddress.equals("yes")){
		    		String streetAddressText = action.getAttribute(admin_shippingStreetAddress, "value");
		    		String streetAddressTextUpdated = streetAddressText + "Updated" + id;
		    		action.clear(admin_shippingStreetAddress, "Removing Streeta address");
		    		
		    		action.writeText(admin_shippingStreetAddress,streetAddressText, "Street address", test);
		    		adminSheets.get(0).get("adminShipping_streetAddress_Output").set(sheetRow1, streetAddressTextUpdated);
		    		
		    		action.click(admin_SaveCustomerBtn, "Save", test);
		    		
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
