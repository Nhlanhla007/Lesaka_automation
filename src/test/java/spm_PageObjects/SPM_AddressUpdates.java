package spm_PageObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import spm_MagentoPageObjects.SPM_MagentoRetrieveCustomerDetailsPage;
import utils.Action;
import utils.DataTable2;

public class SPM_AddressUpdates {

	
	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    SPM_MagentoRetrieveCustomerDetailsPage navigateToCustomer;
    
    public SPM_AddressUpdates(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
        navigateToCustomer = new SPM_MagentoRetrieveCustomerDetailsPage(driver, dataTable2);
    }
    
    @FindBy(name = "street[]")
    WebElement spm_streetAddressField;
    
    
    @FindBy(className = "my-account")
    WebElement spm_myAccountButton;
    
    @FindBy(xpath = "//*[@title=\"address-book-link\"]")
    WebElement spm_AddressBookLink;
    
    @FindBy(xpath = "//*[@title=\"Add New Address\"]")
    WebElement spm_addNewAddress;
    
    @FindBy(xpath = "//*[@class=\"pac-item\"]")
    List<WebElement> googleAddressOptions;
    
    @FindBy(name = "telephone")
    WebElement telephone;
    
    @FindBy(xpath = "//*[@title=\"Save Address\"]")
    WebElement saveAddress;
    
    @FindBy(xpath = "//*[contains(text(), 'You saved the address.')]")
    WebElement successMessage;
    
    @FindBy(xpath = "//*[contains(text(),'Use as my default billing address')]")
    WebElement setAsDefaultBillingAddress;
    
    @FindBy(xpath = "//*[contains(text(),'Use as my default shipping address')]")
    WebElement setAsDefaultShippingAddress;
    
    @FindBy(xpath = "//*[contains(text(),'Delete address')]")
    WebElement deleteAddressConfirmationMessage;
    
    @FindBy(xpath = "//*[@class=\"action-primary action-accept\"]")
    WebElement okDeleteAddressConfirmationButton;
    
    @FindBy(xpath = "//*[contains(text(),'You deleted the address.')]")
    WebElement deleteConfirmationMessage;
    
     void navigateToAddressBook(ExtentTest test) throws Exception{
    	action.waitUntilElementIsDisplayed(spm_myAccountButton, 20);
    	action.click(spm_myAccountButton, "My Account", test);
    	action.waitUntilElementIsDisplayed(spm_AddressBookLink, 20);
    	action.click(spm_AddressBookLink, "My Account", test);
    	action.waitForPageLoaded(20);
    }
    
     void enterAddress(String searchStreetName,String searchSuburb,String searchCity,String telephoneNumber,ExtentTest test)throws Exception {
    	action.waitUntilElementIsDisplayed(spm_addNewAddress, 20);
    	action.scrollElemetnToCenterOfView(spm_addNewAddress, "Add New Adress", test);
    	action.click(spm_addNewAddress, "Add new address", test);
    	action.waitForPageLoaded(20);
        action.explicitWait(6000);            
            action.writeText(spm_streetAddressField, searchStreetName + " " + searchSuburb + " " + searchCity, "Enter Google Address", test);
            searchStreetName = searchStreetName.substring(searchStreetName.indexOf(" ")).trim();
            action.explicitWait(6000);
            boolean flag = true;
            for (WebElement option : googleAddressOptions) {
                try {
                    String streetName = option.findElement(By.xpath(".//*[contains(text(),'" + searchStreetName + "')]")).getText();
                    boolean suburbInformation = option.findElements(By.xpath(".//*[contains(text(),'" + searchSuburb + "')]")).size() > 0;// option.findElement(By.xpath(".//span[3]")).getText();
                    boolean cityInformation = option.findElements(By.xpath(".//*[contains(text(),'" + searchCity + "')]")).size() > 0;
                    boolean suburbCityInformationStatus = suburbInformation & cityInformation;
                    if (suburbCityInformationStatus) {
                        action.CompareResult("Google Option Match Found", "true", "true", test);
                        action.click(option, "Google address option selected", test);
                        action.ajaxWait(20, test);
                        action.explicitWait(8000);
                        flag = false;
                    }
                } catch (Exception e) {
                }

            }
            if (flag) {
                throw new Exception("Google Address Has Not Been Found");
            }
            action.writeText(telephone, telephoneNumber, "Telephone", test);
    }
    	

 	public void removeAddress(ExtentTest test) throws Exception {
 		String searchStreetName = dataTable2.getValueOnOtherModule("SPM_DeliveryPopulation", "streetName", 0);
 		action.waitUntilElementIsDisplayed(spm_myAccountButton, 20);
 		action.click(spm_myAccountButton, "My Account", test);
 		action.click(spm_AddressBookLink, "Address Book", test);
 		
 		boolean addressPresence = driver.findElements(By.xpath("//*[contains(text(),'"+searchStreetName+"')]")).size() >0;
 		if(addressPresence) {
 			WebElement address = driver.findElement(By.xpath("//*[contains(text(),'"+searchStreetName+"')]"));
 			action.scrollElemetnToCenterOfView(address, "Address", test);
 			WebElement deleteAddress = address.findElement(By.xpath("./parent::*//*[contains(text(),'Delete')]"));
 			action.click(deleteAddress, "Delete Address", test);
 			action.ajaxWait(20, test);
 		}
 		
 		boolean confirmationPopUp = driver.findElements(By.xpath("//*[contains(text(),'Are you sure you want to delete this address?')]")).size() >0;
 		if(confirmationPopUp) {
 			WebElement confirmPopUp = driver.findElement(By.xpath("//*[contains(text(),'Are you sure you want to delete this address?')]"));
 			action.elementExistWelcome(confirmPopUp, 20, "Delete Confirmation", test);
 			action.click(okDeleteAddressConfirmationButton, "OK", test);
 			action.waitForPageLoaded(20);
 			action.ajaxWait(20, test);
 			action.elementExistWelcome(deleteConfirmationMessage, 20, "Delete Confirmation Pop Up", test);
 		}else {
 			throw new Exception("Delete Pop Up Never Appeared");
 		}
 	}
     
     
    public void addNewAddressToExistingUser(ExtentTest test) throws Exception{
    	String searchStreetName = dataTable2.getValueOnOtherModule("SPM_DeliveryPopulation", "streetName", 0);
        String searchSuburb = dataTable2.getValueOnOtherModule("SPM_DeliveryPopulation", "Suburb", 0);
        String searchCity = dataTable2.getValueOnOtherModule("SPM_DeliveryPopulation", "city", 0);
    	String telephone = dataTable2.getValueOnOtherModule("SPM_DeliveryPopulation", "telephone", 0);
    	navigateToAddressBook(test);
    	enterAddress(searchStreetName,searchSuburb,searchCity,telephone,test);
    	
        action.scrollElemetnToCenterOfView(setAsDefaultBillingAddress, "Default Billing Address", test);
        action.click(setAsDefaultBillingAddress, "Default Billing Address Checkbox", test);
        
        action.scrollElemetnToCenterOfView(setAsDefaultShippingAddress, "Default Shipping Address", test);
        action.click(setAsDefaultShippingAddress, "Default Shipping Address Checkbox", test);
        
        action.click(saveAddress, "Save Address", test);
        action.waitForPageLoaded(20);
    	if(action.waitUntilElementIsDisplayed(successMessage, 20)) {
    		action.CompareResult("Success message", "You saved the address.", successMessage.getText(), test);
    	}else {
    		action.CompareResult("Success Message", "false", "false", test);
    	}
    }
    
	public void navigateToCustomer(ExtentTest test) throws Exception{
		String customerEmail = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS", dataTable2.getValueOnOtherModule("SPM_login", "loginDetails", 0), "username");
		navigateToCustomer.navigateToCustomer(test);
		navigateToCustomer.searchForCustomer(customerEmail, test);
		navigateToCustomer.tableData(customerEmail, "Sleepmasters", test);
	}
	
	public void addNewSecondaryAddressToExistingUser(ExtentTest test) throws Exception{
		String searchStreetName = dataTable2.getValueOnOtherModule("SPM_DeliveryPopulation", "streetName", 0);
        String searchSuburb = dataTable2.getValueOnOtherModule("SPM_DeliveryPopulation", "Suburb", 0);
        String searchCity = dataTable2.getValueOnOtherModule("SPM_DeliveryPopulation", "city", 0);
    	String telephone = dataTable2.getValueOnOtherModule("SPM_DeliveryPopulation", "telephone", 0);
    	navigateToAddressBook(test);
    	enterAddress(searchStreetName,searchSuburb,searchCity,telephone,test);
    	
    	action.scrollElemetnToCenterOfView(saveAddress, "Save Address", test);
    	action.click(saveAddress, "Save Address", test);
        action.waitForPageLoaded(20);
    	if(action.waitUntilElementIsDisplayed(successMessage, 20)) {
    		action.CompareResult("Success message", "You saved the address.", successMessage.getText(), test);
    	}else {
    		action.CompareResult("Success Message", "false", "false", test);
    	}
	}
	
	
	@FindBy(xpath = "//*[@class=\"admin__page-nav-items items\"]//*[contains(text(),'Addresses')]")
	WebElement magentoAddress;
	
	@FindBy(xpath = "//*[@class=\"admin__data-grid-wrap\"]/table/thead/tr/th")
	List<WebElement> allTableHeaders;
	
	@FindBy(xpath = "//*[@class=\"admin__data-grid-wrap\"]/table/tbody/tr/td")
	List<WebElement> allTableData;
	
	@FindBy(xpath = "//table[@class=\"data-grid data-grid-draggable\"]")
	WebElement table;
	
	
	//CONTINUE HERE ADD THE LOOP FOR THE MEGENTO ADDRESS TABLE VALIDATE IF THE DETAILS SHOW UP
	public void validateAddedAddressInMagento(ExtentTest test) throws Exception {
		String searchStreetName = dataTable2.getValueOnOtherModule("SPM_DeliveryPopulation", "streetName", 0);
       // String searchSuburb = dataTable2.getValueOnOtherModule("SPM_DeliveryPopulation", "Suburb", 0);
        String searchCity = dataTable2.getValueOnOtherModule("SPM_DeliveryPopulation", "city", 0);
    	String telephone = dataTable2.getValueOnOtherModule("SPM_DeliveryPopulation", "telephone", 0);
    	String state = dataTable2.getValueOnOtherModule("SPM_DeliveryPopulation", "province", 0);
    	String streetPostalCode = dataTable2.getValueOnOtherModule("SPM_DeliveryPopulation", "postalCode", 0);
		action.waitUntilElementIsDisplayed(magentoAddress, 20);
		action.click(magentoAddress, "Address Tab", test);
		action.ajaxWait(10, test);
		action.explicitWait(5000);
		Map<String,String> dataStore = new HashMap();
		dataStore.put("Street Name",searchStreetName);
		dataStore.put("Telephone",telephone);
		dataStore.put("City",searchCity);
		dataStore.put("State",state);
		dataStore.put("Postal Code",streetPostalCode);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		
		boolean theEl = driver.findElements(By.xpath("//*[contains(text(),'"+searchStreetName+"')]")).size() >0;
		if(theEl) {
		WebElement theNewAddress = driver.findElement((By.xpath("//*[contains(text(),'"+searchStreetName+"')]")));
		action.scrollElemetnToCenterOfView(theNewAddress, "Street Address", test);
		jse.executeScript("arguments[0].style.border='2px solid red'", (WebElement) theNewAddress);
		action.CompareResult("Street Address", searchStreetName, theNewAddress.getText(), test);
//		
//		WebElement newStreetAddCity = theNewAddress.findElement(By.xpath(".//parent::td/parent::tr//*[contains(text(),'"+searchCity+"')]"));
//		jse.executeScript("arguments[0].style.border='2px solid red'", (WebElement) newStreetAddCity);
//		action.CompareResult("City", searchCity, newStreetAddCity.getText(), test);
//		
//		//WebElement newStreetSuburb = theNewAddress.findElement(By.xpath(".//parent::td/parent::tr//*[contains(text(),'"+searchSuburb+"')]"));
//		//action.CompareResult("Suburb", searchSuburb, newStreetSuburb.getText(), test);
//		
//		WebElement newStreetTelephone = theNewAddress.findElement(By.xpath(".//parent::td/parent::tr//*[contains(text(),'"+telephone+"')]"));
//		jse.executeScript("arguments[0].style.border='2px solid red'", (WebElement) newStreetTelephone);
//		action.CompareResult("Magento Telephone", telephone, newStreetTelephone.getText(), test);
//		
//		WebElement newStreetState = theNewAddress.findElement(By.xpath(".//parent::td/parent::tr//*[contains(text(),'"+state+"')]"));
//		jse.executeScript("arguments[0].style.border='2px solid red'", (WebElement) newStreetState);
//		action.CompareResult("Magento State", state, newStreetState.getText(), test);
//		
//		WebElement newStreetPostalCode = theNewAddress.findElement(By.xpath(".//parent::td/parent::tr//*[contains(text(),'"+streetPostalCode+"')]"));
//		jse.executeScript("arguments[0].style.border='2px solid red'", (WebElement) newStreetPostalCode);
//		action.CompareResult("Magento Postal Code", streetPostalCode, newStreetPostalCode.getText(), test);
//		
		for (Map.Entry map : dataStore.entrySet()) {
			try {
				WebElement element = theNewAddress.findElement(By.xpath(".//parent::td/parent::tr//*[contains(text(),'"+map.getValue().toString()+"')]"));
				jse.executeScript("arguments[0].style.border='2px solid red'", (WebElement) element);
				action.CompareResult(map.getKey().toString(), map.getValue().toString(), element.getText(), test);
			} catch (Exception e) {
				action.CompareResult(map.getKey().toString() + " expected " + map.getValue().toString(), "true", "false", test);
				continue;			
				}
		}
		}else {
			throw new Exception("Address has not been updated in Magento");
		}
		
		}
		//Store all this data into a map
		//Loop through all of the headers, add a switch that matches the names(Name,Email,Telephone,Message/Comment)
		//if the case matches than store that case name with the index in a map MIGHT NOT NEED THE MAP
		
		//Use the MAP go into the <td> and using the index and go the relevant <td>
		
	public void deleteAddressMagento(ExtentTest test) throws Exception{
		//Find the delete and delete
		action.scrollElemetnToCenterOfView(magentoAddress, "Magento Address", test);
		action.click(magentoAddress, "Magento Address", test);
		action.ajaxWait(20, test);
		action.ajaxWait(20, test);
		String searchStreetName = dataTable2.getValueOnOtherModule("SPM_DeliveryPopulation", "streetName", 0);
		WebElement theNewAddress = driver.findElement((By.xpath("//*[contains(text(),'"+searchStreetName+"')]")));
		action.scrollElemetnToCenterOfView(theNewAddress, "Street Address", test);

		WebElement selectMenu = theNewAddress.findElement(By.xpath("./parent::*/parent::*//*[contains(text(),'Select')]"));
		action.click(selectMenu, "Select", test);
		WebElement deleteButton = selectMenu.findElement(By.xpath("./following-sibling::ul/li[4]/a"));
		action.javaScriptClick(deleteButton, "Delete Button", test);
		action.ajaxWait(20, test);
		action.elementExistWelcome(deleteAddressConfirmationMessage, 20, "DeleteAddress", test);
		action.click(okDeleteAddressConfirmationButton, "Confirm Delete", test);
		action.ajaxWait(20, test);
	}
	
	public void validateAddressIsNotPresent(ExtentTest test) throws Exception {
		String searchStreetName = dataTable2.getValueOnOtherModule("SPM_DeliveryPopulation", "streetName", 0);
		action.click(magentoAddress, "Address", test);
		action.ajaxWait(10, test);
		boolean theEl = driver.findElements(By.xpath("//*[contains(text(),'"+searchStreetName+"')]")).size() >0;
		if(!theEl) {
			//WebElement table = driver.findElement(By.tagName("table"));
			action.scrollElemetnToCenterOfView(table, "Table", test);
			action.CompareResult("Address Is Not Present", "true", "true", test);
		}else {
			action.CompareResult("Address Is Not Present", "true", "false", test);
		}
		}
		
}



