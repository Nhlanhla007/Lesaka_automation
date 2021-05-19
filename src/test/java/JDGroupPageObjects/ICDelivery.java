package JDGroupPageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.DataTable2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ICDelivery {
    WebDriver driver;
    Action action;
    DataTable2 dataSheets;

    ICUpdateCustomer customerAddressDetails;
    public ICDelivery(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        dataSheets=dataTable2;
        customerAddressDetails = new ICUpdateCustomer(driver,dataTable2);
    }

    @FindBy(xpath = "//*[@id=\"checkout-shipping-method-load\"]/table/tbody/tr[1]")
    WebElement deliveryLink;

    @FindBy(id = "customer-email")
    WebElement email;

    @FindBy(name = "street[0]")
    WebElement streetName;

    @FindBy(name = "firstname")
    WebElement firstName;

    @FindBy(name = "lastname")
    WebElement lastname;

    @FindBy(name = "telephone")
    WebElement telephone;

    @FindBy(name = "region_id")
    WebElement province;

    @FindBy(name = "city")
    WebElement city;

    @FindBy(name = "custom_attributes[suburb]")
    WebElement Suburb;

    @FindBy(name= "postcode")
    WebElement postalCode;

    @FindBy(name = "vat_id")
    WebElement vatNumber;

    @FindBy(name = "custom_attributes[identity_number]")
    WebElement idNumber;

    @FindBy(xpath = "//*[@id=\"opc-sidebar\"]/div[1]/div[1]/button")
    WebElement ContinueToPayment;

    //---------------------------------------------------------------

    @FindBy(name = "street[0]")
    WebElement streetNameGift;

    @FindBy(name = "telephone")
    WebElement telephoneGift;

    @FindBy(name = "region_id")
    WebElement provinceGift;

    @FindBy(name = "city")
    WebElement cityGift;

    @FindBy(name = "custom_attributes[suburb]")
    WebElement SuburbGift;

    @FindBy(name= "postcode")
    WebElement postalCodeGift;

    @FindBy(xpath = "//*[@id=\"checkout-shipping-method-load\"]/table/tbody/tr[1]/td[4]/button")
    WebElement cardDeliver_btn;

    @FindBy(xpath = "//*[@id=\"opc-sidebar\"]/div[1]/div[1]/button/span")
    WebElement placeOrder;

    @FindBy(className = "form-checkout-title")
    WebElement ic_AddressType;
    
    @FindBy(name = "street[0]")
    WebElement popUpStreetName;
    
    @FindBy(name = "region_id")
    WebElement popUpProvince;
    
	/*
	 * @FindBy(name = "firstname") WebElement popUpFirstName;
	 * 
	 * @FindBy(name = "lastname") WebElement popUpLastName;
	 */
    
    @FindBy(name = "city")
    WebElement popUpCity;

    @FindBy(name = "postcode")
    WebElement popUpPostalCode;
    
    @FindBy(name = "custom_attributes[suburb]")
    WebElement popUpsuburb;
    
    @FindBy(name = "vat_id")
    WebElement popUpVatNumber;
    
    @FindBy(name = "telephone")
    WebElement popUpPhone;
    
    @FindBy(xpath = "//span[contains(text(),'New Address')]")
    WebElement newAddressButton;
    
    @FindBy(xpath = "//div[6]/aside[2]/div[2]/footer/button[1]")
    WebElement popUpSave;
    
    @FindBy(xpath = "//*[@id=\"checkout-shipping-method-load\"]/table/tbody/tr[1]/td[4]/button")
    WebElement cardDeliver_btn;
    
    public static String Streetname;
    public static String Cityname;
    public static String Postalcode;

    public static Map<String,String> registeredUserDetails;
    
    public void deliveryPopulation(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws Exception {
    	Streetname =input.get("streetName").get(rowNumber);
    	Cityname =input.get("city").get(rowNumber);
    	Postalcode = input.get("postalCode").get(rowNumber);
    	String addressType = dataSheets.getValueOnCurrentModule("AddressType"); 
    	String userType = dataSheets.getValueOnCurrentModule("UserType");
        //Thread.sleep(10000);
    	action.explicitWait(15000);
    	if(action.waitUntilElementIsDisplayed(deliveryLink, 10000)) {
        action.click(deliveryLink,"deliveryLink",test);
    	}
        String addressTypeICFont = action.getText(ic_AddressType, "Get Address Type");//ic_AddressType.getText();        
        action.explicitWait(4000);
        if(addressType.equalsIgnoreCase("New") & addressTypeICFont.equalsIgnoreCase("Enter your delivery address & contact details:")) {
        if(userType.equalsIgnoreCase("Guest")) {
            action.writeText(firstName,dataSheets.getValueOnCurrentModule("firstName"),"firstName",test);
            action.writeText(lastname,dataSheets.getValueOnCurrentModule("lastname"),"lastname",test);
            action.writeText(email,dataSheets.getValueOnCurrentModule("email"),"email",test);
           // action.writeText(idNumber,dataSheets.getValueOnCurrentModule("idNumber"),"idNumber",test);
        }else if(userType.equalsIgnoreCase("Registered")) {
        	customerAddressDetails.navigateBackToCustomerDetails(userType,addressTypeICFont);
        	registeredUserDetails = customerAddressDetails.getExistingAddressInformation(userType,addressTypeICFont);
        	dataSheets.setValueOnCurrentModule("firstName", registeredUserDetails.get("firstName"));
        	dataSheets.setValueOnCurrentModule("lastname", registeredUserDetails.get("Last name"));
        	dataSheets.setValueOnCurrentModule("email", registeredUserDetails.get("email"));
        //	dataSheets.setValueOnCurrentModule("idNumber", registeredUserDetails.get("ID"));
			/*
			 * registeredUserDetails.put("Street Address", value);
			 * registeredUserDetails.put("City", value);
			 * registeredUserDetails.put("Province", value);
			 * registeredUserDetails.put("Suburb", value);
			 * registeredUserDetails.put("Post Code", value);
			 * registeredUserDetails.put("Telephone", value);
			 */

            //action.writeText(firstName,dataSheets.getValueOnCurrentModule("firstName"),"firstName",test);
            //action.writeText(lastname,dataSheets.getValueOnCurrentModule("lastname"),"lastname",test);
            //action.writeText(email,dataSheets.getValueOnCurrentModule("email"),"email",test);
            //action.writeText(idNumber,dataSheets.getValueOnCurrentModule("idNumber"),"idNumber",test);

        }
        if(action.waitUntilElementIsDisplayed(ic_AddressType, 20000)) {
        	action.explicitWait(8000);
        action.writeText(streetName,dataSheets.getValueOnCurrentModule("streetName"),"streetName",test);
        action.writeText(telephone,dataSheets.getValueOnCurrentModule("telephone"),"telephone",test);
        action.writeText(city,dataSheets.getValueOnCurrentModule("city"),"city",test);
        action.writeText(Suburb,dataSheets.getValueOnCurrentModule("Suburb"),"Suburb",test);
        action.writeText(postalCode,dataSheets.getValueOnCurrentModule("postalCode"),"postalCode",test);
        action.writeText(vatNumber,dataSheets.getValueOnCurrentModule("vatNumber"),"vatNumber",test);
        action.dropDownselectbyvisibletext(province,dataSheets.getValueOnCurrentModule("province"),"province",test);
        action.explicitWait(10000);
        }
        }else if(addressType.equalsIgnoreCase("Existing") & addressTypeICFont.equalsIgnoreCase("Select a saved address or add a new address:")) {
        	//details returned from this map will be written to excel --DONE NEED THOKOZANI'S INPUT AS TO DOES IT REALLY ADD AND THE TCID AND OCCURENCE
        	customerAddressDetails.navigateBackToCustomerDetails(userType,addressTypeICFont);
        	registeredUserDetails = customerAddressDetails.getExistingAddressInformation(userType,addressTypeICFont);  
        	//SHOULD BE TESTED THOKOZANI
        	
        	Streetname = registeredUserDetails.get("Street Address");
            Cityname = registeredUserDetails.get("City");
            Postalcode =registeredUserDetails.get("Post Code");
            
        	dataSheets.setValueOnCurrentModule("streetName", registeredUserDetails.get("Street Address"));
        	dataSheets.setValueOnCurrentModule("firstName", registeredUserDetails.get("firstName"));
        	dataSheets.setValueOnCurrentModule("lastname", registeredUserDetails.get("Last name"));
        	dataSheets.setValueOnCurrentModule("telephone", registeredUserDetails.get("Telephone"));
        	dataSheets.setValueOnCurrentModule("province", registeredUserDetails.get("Province"));
        	dataSheets.setValueOnCurrentModule("city", registeredUserDetails.get("City"));
        	dataSheets.setValueOnCurrentModule("Suburb", registeredUserDetails.get("Suburb"));
        	dataSheets.setValueOnCurrentModule("postalCode", registeredUserDetails.get("Post Code"));
        	dataSheets.setValueOnCurrentModule("vatNumber", registeredUserDetails.get("Vat number"));
        	dataSheets.setValueOnCurrentModule("email", registeredUserDetails.get("email"));
        //	dataSheets.setValueOnCurrentModule("idNumber", registeredUserDetails.get("ID"));
        	action.explicitWait(12000);
        	
        }else if(addressType.equalsIgnoreCase("New") & addressTypeICFont.equalsIgnoreCase("Select a saved address or add a new address:")){
        	//Enters a new address with an existing address
        	enterNewAddressWithAnExistingAddress(test);
		} /*
			 * else if(addressType.equalsIgnoreCase("Existing") & addressTypeICFont.
			 * equalsIgnoreCase("Enter your delivery address & contact details:")){ //WHAT
			 * SHOULD HAPPEN HERE THERE IS NO EXISITNG ADDRESS????????????? }
			 *///Add else if for other scenario
        if(action.waitUntilElementIsDisplayed(ContinueToPayment, 15000)) {
        	action.explicitWait(5000);
        action.click(ContinueToPayment,"ContinueToPayment",test);
        }
    }
    
    public void enterNewAddressWithAnExistingAddress(ExtentTest test) throws Exception {
    	customerAddressDetails.navigateBackToCustomerDetails("New","Select a saved address or add a new address:");
    	registeredUserDetails = customerAddressDetails.getExistingAddressInformation("New","Select a saved address or add a new address:");
    	dataSheets.setValueOnCurrentModule("lastname", registeredUserDetails.get("Last name"));
    	dataSheets.setValueOnCurrentModule("firstName", registeredUserDetails.get("firstName"));
    	dataSheets.setValueOnCurrentModule("email", registeredUserDetails.get("email"));
    	dataSheets.setValueOnCurrentModule("idNumber", registeredUserDetails.get("ID"));
    	if(action.waitUntilElementIsDisplayed(newAddressButton, 15000)) {
    		action.explicitWait(8000);
       	    //newAddressButton.click();
       	    action.click(newAddressButton, "New Address", test);
    	}
       	//action.writeText(popUpFirstName, dataSheets.getValueOnCurrentModule(""), "New First name", test);
    	action.writeText(popUpStreetName, dataSheets.getValueOnCurrentModule("streetName"), "New Address Street name", test);
    	action.explicitWait(4000);
    	popUpCity.clear();
    	action.writeText(popUpCity, dataSheets.getValueOnCurrentModule("city"), "New Address City", test);
    	action.writeText(popUpPhone, dataSheets.getValueOnCurrentModule("telephone"), "New Address Telephone", test);
    	popUpsuburb.clear();
    	action.writeText(popUpsuburb, dataSheets.getValueOnCurrentModule("Suburb"), "New Address Suburb", test);
    	popUpPostalCode.clear();
    	action.writeText(popUpPostalCode, dataSheets.getValueOnCurrentModule("postalCode"), "New Address postal code", test);
    	action.writeText(popUpVatNumber, dataSheets.getValueOnCurrentModule("vatNumber"), "New Address Vat number", test);
    	//popUpProvince.clear();
    	action.selectFromDropDownUsingVisibleText(popUpProvince, dataSheets.getValueOnCurrentModule("province"), "New Address Province");
    	action.explicitWait(4000);
    	//popUpSave.click();
    	action.click(popUpSave, "Save", test);
    }
    
    public void deliveryPopulationGiftCard(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws InterruptedException, IOException {
    	Streetname =input.get("streetName").get(rowNumber);
        Cityname =input.get("city").get(rowNumber);
        Postalcode = input.get("postalCode").get(rowNumber);
        String firstNameGift = dataSheets.getValueOnOtherModule("deliveryPopulation", "firstName", 0);
        String lastnameGift = dataSheets.getValueOnOtherModule("deliveryPopulation", "lastname", 0);
        String emailGift = dataSheets.getValueOnOtherModule("deliveryPopulation", "email", 0);
        String streetNameG = dataSheets.getValueOnOtherModule("deliveryPopulation", "streetName", 0);
        
        driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
        action.click(cardDeliver_btn, "click Deliver", test);
  
        action.writeText(firstName, firstNameGift,"First name", test);
        action.writeText(lastname, lastnameGift, "Last name", test);
        action.writeText(email, emailGift,"Email", test);
        action.writeText(streetNameGift, streetNameG, "Street name", test);
        action.writeText(telephoneGift,input.get("telephone").get(rowNumber),"telephone",test);
        action.writeText(cityGift,input.get("city").get(rowNumber),"city",test);
        action.writeText(SuburbGift,input.get("Suburb").get(rowNumber),"Suburb",test);
        action.writeText(postalCodeGift,input.get("postalCode").get(rowNumber),"postalCode",test);
        //Thread.sleep(12000);
        action.explicitWait(12000);
        action.dropDownselectbyvisibletext(provinceGift,input.get("province").get(rowNumber),"province",test);
        //Thread.sleep(10000);
        action.explicitWait(10000);
        action.click(placeOrder,"placeOrder",test);
    }
}
