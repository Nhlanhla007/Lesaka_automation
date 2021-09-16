package ic_PageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class ic_TVLicenceValidation {

    WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    public ic_TVLicenceValidation(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2=dataTable2;
}
    

	@FindBy(xpath = "//*[@class=\"sbp-header\"]")
	private WebElement ic_popUpElement;

	@FindBy(xpath = "//label[contains(text(),'Identity number')]")
	private WebElement ic_radioID;

	@FindBy(xpath = "//*[@class=\"sbp-real-input id-num\"]")
	private WebElement ic_TextfieldID;

	@FindBy(xpath = "//label[contains(text(),'Passport number')]")
	private WebElement ic_radioPassport;

	@FindBy(xpath = "//*[@class=\"sbp-real-input passport-num\"]")
	private WebElement ic_TextfieldPass;

	@FindBy(xpath = "//button[contains(text(),'Validate')]")
	private WebElement ic_buttonValidate;

	@FindBy(xpath = "//button[contains(text(),'Return to cart')]")
	private WebElement ic_buttonReturnToCart;

	@FindBy(xpath = "//div[contains(text(),'Adding product to cart will add TV Licence product')]")
	private WebElement ic_TVlicenceMessage;
	
	@FindBy(xpath = "//div[contains(text(),'TV Licence validated. No outstanding balance')]")
	private WebElement ic_ExistingTvLicence;

	@FindBy(xpath = "//button[contains(text(),'Proceed')]")
	private WebElement ic_buttonProceed;
	
	//@FindBy(xpath = "//*[@class=\"sbp-real-input id-num\"]/following-sibling::div//div[@class=\"id-num fake-input-content\"]")
	@FindBy(xpath = " //*[@class=\"sbp-real-input id-num\"]")
	//@FindBy(xpath = " //*[@class=\"id-num fake-input-content disabled\"]")	
	private WebElement idValueAddedForValidation;
	
	//@FindBy(xpath = "//*[@class=\"sbp-real-input passport-num\"]/following-sibling::div//div[@class=\"passport-num fake-input-content\"]")
	//@FindBy(xpath = "//*[@class=\"passport-num fake-input-content disabled\"]")
	@FindBy(xpath = "//*[@class=\"sbp-real-input passport-num\"]")
	private WebElement passportValueAddedForValidation;
	
	@FindBy(xpath="//*[@data-role=\"proceed-to-checkout\"]")
	private WebElement secureCheckout;
    
	@FindBy(xpath = "//*[contains(text(),'Items in Cart')]")
	WebElement viewAllItemsInCart;
	
	@FindBy(xpath = "//*[@class=\"totals sub\"]/td/span")
	WebElement subtotalAfterLicense;
	
	@FindBy(xpath = "//*[@class=\"product-item-details\"]//strong")
	List<WebElement> productsAddedToCart;
	
	@FindBy(xpath = "//*[@id=\"checkout-shipping-method-load\"]/table/tbody/tr[1]")
    WebElement deliveryLink;

	

	public void TvLicenceValidation(ExtentTest test, int rowNumber) throws Exception {
		String LicenseAdd = dataTable2.getValueOnCurrentModule("License Addition");
		String typeOfIdentity = dataTable2.getValueOnCurrentModule("Type");
		String valueofIdentity = dataTable2.getValueOnCurrentModule("IDOrPassport");
		String userType = dataTable2.getValueOnOtherModule("deliveryPopulation", "UserType", 0);
		String addreType = dataTable2.getValueOnOtherModule("deliveryPopulation", "AddressType", 0);
		String licensePopup = dataTable2.getValueOnCurrentModule("TV Pop-up");
		
		if(licensePopup.equalsIgnoreCase("yes")){
			
			if (action.elementExistWelcome(ic_popUpElement, 180, "TV licence", test)) {
				if (LicenseAdd.equalsIgnoreCase("yes")) {
	
					if (typeOfIdentity.equalsIgnoreCase("ID")) {
						action.javaScriptClick(ic_radioID, "select the ID identity type", test);
						action.writeText(ic_TextfieldID, valueofIdentity, "writing the ID", test);
						action.explicitWait(5000);
						action.click(ic_buttonValidate, "Click validate", test);
						action.ajaxWait(10, test);
					}
					if (typeOfIdentity.equalsIgnoreCase("Passport")) {
						action.javaScriptClick(ic_radioPassport, "select the Passport identity type", test);
						action.writeText(ic_TextfieldPass, valueofIdentity, "writing the ID", test);
						action.explicitWait(5000);
						action.click(ic_buttonValidate, "Click validate", test);
						action.ajaxWait(10, test);
					}
					String feeMessage = action.getText(ic_TVlicenceMessage, "Validate your TV licence?", test);
					action.CompareResult("Adding the license fee R265 ", feeMessage,
							action.getText(ic_TVlicenceMessage, "", test), test);
					action.explicitWait(5000);
					action.click(ic_buttonProceed, "Click Proceed", test);
					action.ajaxWait(40, test);
					action.waitForPageLoaded(30);
	
					cartValidationWithLicense(test);
	
				}
				
				String id_passport_compare;
				if(userType.equalsIgnoreCase("Registered") & addreType.equalsIgnoreCase("New")){
					id_passport_compare = dataTable2.getValueOnOtherModule("accountCreation", "identityNumber/passport", 0);
					
				}
				else {
					id_passport_compare = dataTable2.getValueOnCurrentModule("IDOrPassport");
				}
				
				if (LicenseAdd.equalsIgnoreCase("no")) {
					if (typeOfIdentity.equalsIgnoreCase("ID")) {
						action.javaScriptClick(ic_radioID, "select the ID identity type", test);
						if(!(action.getText(idValueAddedForValidation, "ID Value", test).isEmpty())) {
							dataTable2.setValueOnOtherModule("ic_tvLicenseValidation", "IDOrPassport", idValueAddedForValidation.getText(), 0);
							action.CompareResult("if the ID correct", id_passport_compare, idValueAddedForValidation.getText(), test);
							//action.clear(ic_TextfieldID, "Clearing Validation Field");	
							action.writeText(ic_TextfieldID, id_passport_compare, "writing the ID", test);
						}
						/*action.clear(ic_TextfieldID, "Clearing Validation Field");	
						action.writeText(ic_TextfieldID, valueofIdentity, "writing the ID", test);*/
						action.explicitWait(5000);
						action.click(ic_buttonValidate, "Click validate", test);
						action.ajaxWait(10, test);
					}
					if (typeOfIdentity.equalsIgnoreCase("Passport")) {
						action.javaScriptClick(ic_radioPassport, "select the Passport identity type", test);
						if(!(action.getText(passportValueAddedForValidation, "Passport Value", test).isEmpty())) {
							dataTable2.setValueOnOtherModule("ic_tvLicenseValidation", "IDOrPassport", passportValueAddedForValidation.getText(), 0);
							action.CompareResult("if the ID correct", id_passport_compare, passportValueAddedForValidation.getText(), test);
							//action.clear(ic_TextfieldPass, "Clearing Validation Field");
							action.writeText(ic_TextfieldPass, id_passport_compare, "writing the ID", test);
						}
						//action.clear(ic_TextfieldPass, "Clearing Validation Field");
						//action.writeText(ic_TextfieldPass, valueofIdentity, "writing the ID", test);
						action.explicitWait(5000);
						action.click(ic_buttonValidate, "Click validate", test);
						action.ajaxWait(10, test);
					}
					
					String existingLicence = action.getText(ic_ExistingTvLicence, "Validate No Outstangind balance", test);
					action.CompareResult("Adding the license fee R265 ", existingLicence,
							action.getText(ic_ExistingTvLicence, "", test), test);
					action.explicitWait(5000);
					action.click(ic_buttonProceed, "Click Proceed", test);
					action.ajaxWait(10, test);
					action.waitForPageLoaded(30);
	
				}
				
			} else {
			throw new Exception("TV Licence Validation popup didn't appear");
			}
		}
	
		else {
			boolean isValidationPopUpVisible = driver.findElements(By.xpath("//*[@class=\"sbp-header\"]")).size()>0;
			if(!(isValidationPopUpVisible) ){
				action.CompareResult("TV License Validation Is Not Displayed", "True", "True", test);
				}
				else {
				action.CompareResult("TV License Validation is Still Present", "True", "False", test);
				throw new Exception("TV License Validation is Still Present");
				}
		}

}
	
	
	// *******************************************
    
    
    public void cartValidationWithLicense(ExtentTest test) throws Exception {
		boolean flag = true;
		//NEED TO CREATE A SHEET
		String licenseAddition = dataTable2.getValueOnCurrentModule("License Addition");
		String tvLicenseAmount = dataTable2.getValueOnCurrentModule("TV License Amount");
		// Map<String,List<String>> allProductsInCart = EVS_ProductSearch.productData;
		String allProductsInCart = dataTable2.getValueOnOtherModule("ProductSearch", "CartTotal", 0);
		String subtotal = action.getText(subtotalAfterLicense, "Cart Subtotal", test).replace("R", "").replace(",", "");
		if (licenseAddition.equalsIgnoreCase("yes")) {
			// for (Map.Entry selectedProducts : allProductsInCart.entrySet()) {
			double subTotalAfterTVLicense = Double.parseDouble(allProductsInCart) + Double.parseDouble(tvLicenseAmount);
			action.CompareResult("TV License R265 has been added to cart", subtotal,String.valueOf(subTotalAfterTVLicense), test);

			action.waitUntilElementIsDisplayed(viewAllItemsInCart, 10000);
			action.explicitWait(5000);
			action.click(viewAllItemsInCart, "View All Items In Cart", test);

			for (WebElement tvLicenseProd : productsAddedToCart) {
				String productInCart = tvLicenseProd.getText();
				if (productInCart.equalsIgnoreCase("TV License Application")) {
					action.scrollElemetnToCenterOfView(tvLicenseProd, "TV License Product", test);
					flag = false;
					action.CompareResult("Is TV License Application added to cart?", "True", "True", test);
					
					//Add SKU tv license value to the cart either new SKU or existing SKU
					List<String> tvLicenseProdSKU = new ArrayList<>();
					tvLicenseProdSKU.add("1");
					tvLicenseProdSKU.add("000000000010011406");
					tvLicenseProdSKU.add("R" + tvLicenseAmount );
					Ic_Products.productData.put("TV License", tvLicenseProdSKU);					
					break;
				}
			}
			if (flag) {
				action.CompareResult("Is TV License Application added to cart?", "True", "False", test);
			}
		} else {
			action.CompareResult("Subtotal amount is the same as the cart total", allProductsInCart, subtotal, test);
		}

	}
	
    public void validateNoTVLicensePopUpShows(ExtentTest test) throws Exception {
		action.click(secureCheckout, "Secure Checkout", test);
		action.waitForJStoLoad(30);
		boolean isValidationPopUpVisible = driver.findElements(By.xpath("//*[@class=\"sbp-header\"]")).size()>0;
		if(!(isValidationPopUpVisible) ){
			action.CompareResult("TV License Validation Is Not Displayed", "True", "True", test);
		}
		else {
			action.CompareResult("TV License Validation is Still Present", "True", "False", test);
			throw new Exception("TV License Validation is Still Present");
		}
		}	



}
