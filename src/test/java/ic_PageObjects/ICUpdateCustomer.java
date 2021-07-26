package ic_PageObjects;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import utils.Action;
import utils.DataTable2;

public class ICUpdateCustomer {
	
	 WebDriver driver;
	 Action action;
	 DataTable2 dataTable2;

	    public ICUpdateCustomer(WebDriver driver, DataTable2 dataTable2) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
			this.dataTable2=dataTable2;
	    }
	    
	    
	    @FindBy(className = "my-account")
		WebElement ic_myAccountButton;
	  
	    @FindBy(xpath="//*[@id=\"header-slideout--0\"]/li[3]/a")
	    private WebElement MyAccountButton2;
	  
	    @FindBy(xpath="//*[@id=\"account-nav\"]/ul/li[5]/a")
	    private WebElement AddressBookEdit;
	    
	    @FindBy(xpath="//*[@id=\"account-nav\"]/ul/li[6]/a")
	    private WebElement AccountInfoEdit;
	    
	    //Edit
	    @FindBy(xpath="//*[@id=\"firstname\"]")
	    private WebElement ic_firstname;
	  //*[@id="firstname"]
	    @FindBy(xpath="//*[@id=\"firstname\"]")
	    private WebElement firstUpdated;
	    
	    @FindBy(xpath="//*[@id=\"lastname\"]")
	    private WebElement ic_lastname;
	    
	    @FindBy(xpath="//*[@id=\"lastname\"]")
	    private WebElement lastNameUpdated;
	    
	    @FindBy(xpath="//*[@id=\"taxvat\"]")
	    private WebElement ic_taxVat;
	  
	    @FindBy(xpath="//*[@id=\"change-email\"]/following-sibling::*/span")
	    private WebElement emailCheckBox;
	    
	    @FindBy(xpath="//*[@id=\"email\"]")
	    private WebElement ic_email;
	    
	    @FindBy(xpath="//*[@id=\"maincontent\"]/div/div[2]/div[2]/div[2]/div[1]/div[1]/p")
	    private WebElement emailSaved;
	    
	    @FindBy(xpath="//*[@id=\"form-validate\"]/fieldset[1]/div[5]/label")
	    private WebElement passCheckBox;
	    
	    @FindBy(xpath="//*[@id=\"current-password\"]")
	    private WebElement passCurrent;
	    
	    @FindBy(xpath="//*[@id=\"password\"]")
	    private WebElement passNew;
	    				//*[@id="password-confirmation"]
	    @FindBy(xpath="//*[@id=\"password-confirmation\"]")
	    private WebElement passConfirmation;
	    
	   // @FindBy(xpath="/html/body/div[1]/header/div[3]/div[2]/div/div/div")
        @FindBy(xpath="//div[contains(text(),'You saved the account information.')]")
        private WebElement successSaved;

	@FindBy(xpath="//div[contains(text(),'You saved the address.')]")
	private WebElement successSavedAddress;
	    
	    //still need to add the xpath
	  //*[@id="account-nav"]/ul/li[5]/a
	    @FindBy(xpath="//*[@id=\"account-nav\"]/ul/li[5]/a")
	    private WebElement ic_addressInformation;
	    				
	    @FindBy(xpath="//*[@id=\"street_1\"]")
	    private WebElement ic_streetAddress;
	    
	    @FindBy(xpath="//*[@id=\"building_details\"]")
	    private WebElement ic_buildingDetails;
	    
	    @FindBy(xpath="//*[@id=\"region_id\"]")
	    private WebElement ic_province;
	    
	    @FindBy(xpath="//*[@id=\"city\"]")
	    private WebElement ic_city;
	    
	    @FindBy(xpath="//*[@id=\"suburb\"]")
	    private WebElement ic_suburb;
	    
	    @FindBy(xpath="//*[@id=\"zip\"]")
	    private WebElement ic_postalCode;
	    
	    @FindBy(xpath="//*[@id=\"form-validate\"]/div/div[1]/button/span")
	    private WebElement SaveButton ;
	    
	    @FindBy(xpath="//*[@id=\"maincontent\"]/div/div[2]/div[2]/div[2]/div[1]/div[2]/a/span")
	    private WebElement ic_BillingAddress;
	    
	    @FindBy(xpath="//*[@id=\"maincontent\"]/div/div[2]/div[2]/div[2]/div[2]/div[2]/a/span")
	    private WebElement ic_ShippingAddress;
	 
	    @FindBy(id = "identity_number")
	    private WebElement identityNumber;
	    
	    @FindBy(id = "passport_number")
	    private WebElement passportNumber;
	    
	    @FindBy(id = "switcher--id-field")
	    private WebElement idRadioButton;
	    
	    @FindBy(name = "telephone")
	    private WebElement telephone;
	    
	    @FindBy(css = "a.go-back")
	    private WebElement backButton;
	    
	    @FindBy(xpath="//*[@class=\"action showcart\"]")
	    private WebElement iCCartButton;
	    
	    @FindBy(xpath="//*[@id=\"top-cart-btn-checkout\"]/span")
	    private WebElement icCCheckout;
	    
	    List<String> streetAddresses;
	    
	    /**
	     * 
	     * @param test
	     */
		public void ic_NavigateToMyAccount(ExtentTest test) {
			try {
				action.click(ic_myAccountButton, "Navigate to accountTab",test);
				action.click(MyAccountButton2, "My Account", test);
				
				//action.click(AddressBook, "Address", test);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	    public void updateAccount(ArrayList<HashMap<String, ArrayList<String>>> mySheets,ExtentTest test,int testcaseID){
	    	int sheetRow1= findRowToRun(mySheets.get(0), 0, testcaseID);
			int sheetRow2= findRowToRun(mySheets.get(1), 0, testcaseID);
	    	
	    try {
	    	
	    	String firstName = dataTable2.getValueOnCurrentModule("firstName");
	    	String lastName = dataTable2.getValueOnCurrentModule("lastName");
	    	String taxVat = dataTable2.getValueOnCurrentModule("taxVat");
	    	String email = dataTable2.getValueOnCurrentModule("email");
	    	String passWord = dataTable2.getValueOnCurrentModule("passWord");
	    	
	    	Random rand = new Random();
			String id = String.format("%04d", rand.nextInt(10000));
	    	

	    	//Default Billing address
	    	String billingAddress = dataTable2.getValueOnCurrentModule("billingAddress");
	    	String billing_streetAddress = dataTable2.getValueOnCurrentModule("billing_streetAddress");
	    	//String billing_provinceName = mySheets.get(0).get("billing_provinceName").get(sheetRow1);
	    	
	    	//Default shipping address
	    	String shippingAddress = dataTable2.getValueOnCurrentModule("shippingAddress");
	    	String shipping_streetAddress = dataTable2.getValueOnCurrentModule("shipping_streetAddress");
	    	//String shipping_provinceName = mySheets.get(0).get("shipping_provinceName").get(sheetRow1);
	    	
	    	
	    	action.click(ic_myAccountButton, "Navigate to accountTab",test);
	    	action.click(MyAccountButton2, "My Account", test);
	    	
	    	action.click(AccountInfoEdit, "Account Information", test);
	    	String firstNameText = action.getAttribute(ic_firstname, "value");
	    	if(firstName.equalsIgnoreCase("Yes")){
	    		String firstNameTextUpdated = "Updated" + id;
	    		action.clear(ic_firstname, "Removing first name");
	    		action.writeText(ic_firstname, firstNameTextUpdated,"first Name", test);
				dataTable2.setValueOnCurrentModule("firstName_output",firstNameTextUpdated);
	    		action.click(SaveButton, "Save", test);
	    		action.waitForPageLoaded(30);
	    		action.explicitWait(3000);
	    		action.CompareResult("User Saved", "You saved the account information.", action.getText(successSaved, "",test), test);
	    		action.click(AccountInfoEdit, "Account Information", test);
				action.waitForPageLoaded(30);
//	    		action.explicitWait(5000);
	    		String firstNameTextSaved = action.getAttribute(firstUpdated, "value");
	    		action.CompareResult("updated first name", firstNameTextUpdated, firstNameTextSaved, test);
	    	}else {
				dataTable2.setValueOnCurrentModule("firstName_output",firstNameText);
	    	}
	    	
	    	String lastNameText = action.getAttribute(ic_lastname, "value");
	    	if(lastName.equalsIgnoreCase("yes")) {
	    		
	    		String lastNameTextUpdated = "Updated" + id;
	    		action.clear(ic_lastname, "Remmoving last name");
	    		action.writeText(ic_lastname, lastNameTextUpdated,"last Name", test);
				dataTable2.setValueOnCurrentModule("lastName_output",lastNameTextUpdated);
	    		
	    		action.click(SaveButton, "Save", test);
				action.waitForPageLoaded(30);
	    		action.CompareResult("User Saved", "You saved the account information.", action.getText(successSaved, "",test), test);
	    		action.click(AccountInfoEdit, "Account Information", test);
				action.waitForPageLoaded(30);
	    		action.explicitWait(3000);
	    		String lastNameTextSaved = action.getAttribute(lastNameUpdated, "value");
	    		action.CompareResult("updated last name", lastNameTextUpdated, lastNameTextSaved, test);
	    	}else {
				dataTable2.setValueOnCurrentModule("lastName_output",lastNameText);
	    	}
	    	
	    	
	    	String taxVatText = action.getAttribute(ic_taxVat, "value");
	    	if(taxVat.equalsIgnoreCase("yes")){
	    		String taxVatTextUpdated = "Updated" + id;
	    		action.clear(ic_taxVat, "Removing Vat");
	    		action.writeText(ic_taxVat, taxVatTextUpdated," VAT/TAX", test);
				dataTable2.setValueOnCurrentModule("taxVat_output",taxVatTextUpdated);
	    		action.click(SaveButton, "Save", test);
				action.waitForPageLoaded(30);
	    		action.click(AccountInfoEdit, "Account Information", test);
				action.waitForPageLoaded(30);
	    	}else {
				dataTable2.setValueOnCurrentModule("taxVat_output",taxVatText);
	    	}
	    	
	    	
	    	String emailText = action.getAttribute(ic_email, "value");
	    	if(email.equalsIgnoreCase("yes")){
				action.click(emailCheckBox, "EmailCheckBox", test);
				String emailTextUpdated = id+"Updated@updated.com";
				ic_email.clear();
				action.writeText(ic_email, emailTextUpdated ,"email", test);
				dataTable2.setValueOnCurrentModule("email_output",emailTextUpdated);


				String currentPassWordText = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS", dataTable2.getValueOnOtherModule("ic_login","loginDetails",0), "password");
				action.writeText(passCurrent, currentPassWordText, "Current password", test);
				action.click(SaveButton, "Save", test);
				action.waitForPageLoaded(30);
				action.explicitWait(3000);
				action.CompareResult("User Saved", "You saved the account information.", action.getText(successSaved, "",test), test);
				action.click(AccountInfoEdit, "Account Information", test);
				action.waitForPageLoaded(30);
			}else {
				dataTable2.setValueOnCurrentModule("email_output",emailText);
			}
	    	
	    	if(passWord.equalsIgnoreCase("yes")){
	    		action.click(passCheckBox, "PassWordCheckBox", test);
	    		String currentPassWordText = dataTable2.getValueOnCurrentModule("Password");
//	    		action.writeText(passCurrent, currentPassWordText, "current Password", test);
	    		String passWTextUpdated = "updated"+id;
	    		action.writeText(passNew, passWTextUpdated , "new Password", test);
				dataTable2.setValueOnCurrentModule("passWord_output",passWTextUpdated);
				dataTable2.setValueOnOtherModule("ic_login","Password",passWTextUpdated,0);
	    		action.writeText(passConfirmation, passWTextUpdated, "Confirm new Password", test);
	    		action.click(SaveButton, "Save", test);
				action.waitForPageLoaded(30);
				action.explicitWait(3000);
	    		action.CompareResult("User Saved", "You saved the account information.", action.getText(successSaved, "",test), test);
	    		action.click(AccountInfoEdit, "Account Information", test);
				action.waitForPageLoaded(30);
	    	}
	    	
	    	//Address Book edit
	    	
	    	action.click(ic_addressInformation, "Information Address", test);
			action.waitForPageLoaded(30);
//	    	action.explicitWait(5000);
	    	
	    	//Billing Address
	    	if(billingAddress.equalsIgnoreCase("Yes")){
	    		
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
	    		
	    		
	    		//action.click(ic_BillingAddress, "Change Billing address", test);
	    		//ic_BillingAddress.click();

	    		action.javaScriptClick(ic_BillingAddress, "Change Billing address", test);
	    		String streetAdressText = action.getAttribute(ic_streetAddress, "value");	    		
	    		if(billing_streetAddress.equalsIgnoreCase("yes")){
	    			//ADD random data from list here for addresses
	    			String streetAdressTextUpdated = streetAddresses.get(randomitem);
		    		action.clear(ic_streetAddress, "Removing Street address");
	    	
		    		action.writeText(ic_streetAddress, streetAdressTextUpdated, "Street address", test);
		    		mySheets.get(0).get("billing_streetAddress_output").set(sheetRow1, streetAdressTextUpdated);
		    		
		    		/*action.click(SaveButton, "Save", test);
		    		action.CompareResult("User address Saved", "You saved the address.", action.getText(successSaved, ""), test);*/
		    		String buildDetailsText = action.getAttribute(ic_buildingDetails, "value");
		    		dataTable2.setValueOnCurrentModule("billing_buildingDetails_output",buildDetailsText);
		    		//Fix this province get the proper province
		    		String province = action.getSelectedOptionFromDropDown(ic_province);
		    		String cityText = action.getAttribute(ic_city, "value");
		    		dataTable2.setValueOnCurrentModule("billing_city_output",cityText);
		    		
		    		String suburbText = action.getAttribute(ic_suburb, "value");
		    		dataTable2.setValueOnCurrentModule("billing_suburb_output",suburbText);
		    		
		    		String postalCodeText = action.getAttribute(ic_postalCode, "value");
		    		dataTable2.setValueOnCurrentModule("billing_postalCode_output",postalCodeText);
		    		
		    		action.click(SaveButton, "Save", test);
					action.waitForPageLoaded(30);
		    		action.explicitWait(3000);
		    		action.waitUntilElementIsDisplayed(successSavedAddress,10);
		    		String successMsg=action.getText(successSavedAddress, "You saved the address.",test);

		    		action.CompareResult("User address Saved", "You saved the address.", successMsg, test);
		    	
		    	}else {
		    		dataTable2.setValueOnCurrentModule("billing_streetAddress_output",streetAdressText);
		    	}
	    		
	    	}
	    	
	    	//address shipping address
	    	if(shippingAddress.equalsIgnoreCase("Yes")){
	    		action.click(ic_ShippingAddress, "Change Shipping address", test);
				action.waitForPageLoaded(30);
	    	
	    	if(shipping_streetAddress.equals("yes")){
	    		String streetAddressText = action.getAttribute(ic_streetAddress, "value");
	    		String streetAddressTextUpdated = streetAddressText + "Updated" + id;
	    		action.clear(ic_streetAddress, "Removing Streeta address");
	    		
	    		action.writeText(ic_streetAddress,streetAddressText, "Street address", test);
	    		dataTable2.setValueOnCurrentModule("shipping_streetAddress_Output",streetAddressTextUpdated);
	    		
	    		/*action.click(SaveButton, "Save", test);
	    		action.CompareResult("User address Saved", "You saved the address.", action.getText(successSaved, ""), test);*/
	    		
	    		String buildDetailsText = action.getAttribute(ic_buildingDetails, "value");
	    		dataTable2.setValueOnCurrentModule("shipping_buildingDetails_output",buildDetailsText);
	    		
	    		//action.selectExactValueFromListUsingTex(ic_province, billing_provinceName);
	    		
	    		String cityText = action.getAttribute(ic_city, "value");
	    		dataTable2.setValueOnCurrentModule("shipping_city_output",cityText);
	    		
	    		String suburbText = action.getAttribute(ic_suburb, "value");
	    		dataTable2.setValueOnCurrentModule("shipping_suburb_output",suburbText);
	    		
	    		String postalCodeText = action.getAttribute(ic_postalCode, "value");
	    		dataTable2.setValueOnCurrentModule("shipping_postalCode_output",postalCodeText);
	    		
	    		action.click(SaveButton, "Save", test);
				action.waitForPageLoaded(30);
				action.explicitWait(3000);
	    		action.CompareResult("User address Saved", " You saved the address.", action.getText(successSavedAddress, "Shipping address updated",test), test);
	    		
	    	}
	    	}
	    
	    } catch(Exception e){
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
	
	public void navigateBackToCustomerDetails(String userType,String addressExist,ExtentTest test) throws Exception {
//		action.explicitWait(4000);
		//backButton.click();
		action.click(backButton, "Back Button", test);
		action.waitForPageLoaded(30);
//		action.click(backButton,"backButton",test);
//		action.explicitWait(4000);
		//ic_myAccountButton.click();
		action.click(ic_myAccountButton, "Account Information", test);
		action.waitForPageLoaded(30);
//		action.explicitWait(4000);
		//MyAccountButton2.click();
		action.click(MyAccountButton2, "Account Information", test);
		action.waitForPageLoaded(30);
//		action.explicitWait(4000);
		if(userType.equalsIgnoreCase("Registered") & addressExist.equalsIgnoreCase("Select a saved address or add a new address:")) {
		//AddressBookEdit.click();
		action.click(AddressBookEdit, "Address Book Edit", test);
		action.waitForPageLoaded(30);
		//ic_BillingAddress.click();
		action.click(ic_BillingAddress, "Billing Address", test);
		action.waitForPageLoaded(30);
		}
	}
	
	public Map<String,String> getExistingAddressInformation(String userType,String addressExist,ExtentTest test) throws Exception {
		Map<String, String> addressInfo = new LinkedHashMap<>();
		if(userType.equalsIgnoreCase("Registered") & addressExist.equalsIgnoreCase("Select a saved address or add a new address:") ){
		String streetAdd = action.getAttribute(ic_streetAddress, "value");
		addressInfo.put("Street Address", streetAdd);
		String city =action.getAttribute(ic_city, "value");
		addressInfo.put("City", city);
		String province = action.getSelectedOptionFromDropDown(ic_province);
		addressInfo.put("Province", province);
		String suburb = action.getAttribute(ic_suburb, "value");
		addressInfo.put("Suburb", suburb);	
		String postal = action.getAttribute(ic_postalCode, "value");
		addressInfo.put("Post Code", postal);
		String tele =action.getAttribute(telephone, "value");
		addressInfo.put("Telephone", tele);
		}
		//AccountInfoEdit.click();
		action.click(AccountInfoEdit, "Account Information", test);
		action.waitForPageLoaded(30);
//		action.explicitWait(5000);
		String firstName = action.getAttribute(ic_firstname, "value");
		addressInfo.put("firstName", firstName);
		String lastNAme = action.getAttribute(ic_lastname, "value");
		addressInfo.put("Last name", lastNAme);
		String taxVat = action.getAttribute(ic_taxVat, "value");
		addressInfo.put("Vat number", taxVat);
		//emailCheckBox.click();
		action.click(emailCheckBox, "Email Checkbox", test);
//		action.explicitWait(4000);
		String email = action.getAttribute(ic_email, "value");
		addressInfo.put("email", email);
		//emailCheckBox.click();
		action.click(emailCheckBox, "Email Checkbox", test);
		if(idRadioButton.isSelected()) {
			addressInfo.put("ID", action.getAttribute(identityNumber, "value")) ;
		}else {
			addressInfo.put("ID", action.getAttribute(passportNumber, "value"));
		}
//		action.explicitWait(5000);
		//iCCartButton.click();
		action.click(iCCartButton, "Cart Button", test);		
		//icCCheckout.click();
		action.click(icCCheckout, "Check Out Button", test);
		return addressInfo;
		
	}
	

	
	
	
}
