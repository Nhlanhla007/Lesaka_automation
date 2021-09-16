package evs_PageObjects;

import java.io.IOException;
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

public class evs_TVLicenceValidation {

	WebDriver driver;
	Action action;
	DataTable2 dataTable2;

	public evs_TVLicenceValidation(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;
	}

	@FindBy(xpath = "//*[@class=\"totals sub\"]/td/span")
	WebElement subtotalAfterLicense;

	@FindBy(xpath = "//*[@class=\"product-item-details\"]//strong")
	List<WebElement> productsAddedToCart;

	@FindBy(xpath = "//*[contains(text(),'Items in Cart')]")
	WebElement viewAllItemsInCart;

	@FindBy(xpath = "//*[@class=\"sbp-header\"]")
	private WebElement evs_popUpElement;

	@FindBy(xpath = "//label[contains(text(),'Identity number')]")
	private WebElement evs_radioID;

	@FindBy(xpath = "//*[@class=\"sbp-real-input id-num\"]")
	private WebElement evs_TextfieldID;

	@FindBy(xpath = "//label[contains(text(),'Passport number')]")
	private WebElement evs_radioPassport;

	@FindBy(xpath = "//*[@class=\"sbp-real-input passport-num\"]")
	private WebElement evs_TextfieldPass;

	@FindBy(xpath = "//button[contains(text(),'Validate')]")
	private WebElement evs_buttonValidate;

	@FindBy(xpath = "//button[contains(text(),'Return to cart')]")
	private WebElement evs_buttonReturnToCart;

	@FindBy(xpath = "//div[contains(text(),'Adding product to cart will add TV Licence product')]")
	private WebElement evs_TVlicenceMessage;
	
	@FindBy(xpath = "//div[contains(text(),'TV Licence validated. No outstanding balance')]")
	private WebElement evs_ExistingTvLicence;

	@FindBy(xpath = "//button[contains(text(),'Proceed')]")
	private WebElement evs_buttonProceed;
	
	@FindBy(xpath = "//*[@class=\"sbp-real-input id-num\"]/following-sibling::div//div[@class=\"id-num fake-input-content\"]")	
	private WebElement idValueAddedForValidation;
	
	@FindBy(xpath = "//*[@class=\"sbp-real-input passport-num\"]/following-sibling::div//div[@class=\"passport-num fake-input-content\"]")
	private WebElement passportValueAddedForValidation;
	
	@FindBy(xpath="//*[@data-role=\"proceed-to-checkout\"]")
	private WebElement secureCheckout;
	
	@FindBy(xpath = "//*[@id=\"checkout-shipping-method-load\"]/table/tbody/tr[1]")
    WebElement deliveryLink;
	

	public void TvLicenceValidation(ExtentTest test, int rowNumber) throws Exception {
		String LicenseAdd = dataTable2.getValueOnCurrentModule("License Addition");
		String typeOfIdentity = dataTable2.getValueOnCurrentModule("Type");
		String valueofIdentity = dataTable2.getValueOnCurrentModule("IDOrPassport");
		String userType = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "UserType", 0);
		String addreType = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "AddressType", 0);
		String licensePopup = dataTable2.getValueOnCurrentModule("TV Pop-up");
		
		if(licensePopup.equalsIgnoreCase("yes")){

		if (action.elementExistWelcome(evs_popUpElement, 180, "TV licence", test)) {
			if (LicenseAdd.equalsIgnoreCase("yes")) {

				if (typeOfIdentity.equalsIgnoreCase("ID")) {
					action.javaScriptClick(evs_radioID, "select the ID identity type", test);
					action.writeText(evs_TextfieldID, valueofIdentity, "writing the ID", test);
					action.explicitWait(5000);
					action.click(evs_buttonValidate, "Click validate", test);
					action.ajaxWait(10, test);
				}
				if (typeOfIdentity.equalsIgnoreCase("Passport")) {
					action.javaScriptClick(evs_radioPassport, "select the Passport identity type", test);
					action.writeText(evs_TextfieldPass, valueofIdentity, "writing the ID", test);
					action.explicitWait(5000);
					action.click(evs_buttonValidate, "Click validate", test);
					action.ajaxWait(10, test);
				}
				String feeMessage = action.getText(evs_TVlicenceMessage, "Validate your TV licence?", test);
				action.CompareResult("Adding the license fee R265 ", feeMessage,
						action.getText(evs_TVlicenceMessage, "", test), test);
				action.explicitWait(5000);
				action.click(evs_buttonProceed, "Click Proceed", test);
				action.ajaxWait(10, test);
				action.waitForPageLoaded(30);

				cartValidationWithLicense(test);

			}
			String id_passport_compare;
			if(userType.equalsIgnoreCase("Registered") & addreType.equalsIgnoreCase("New")){
				id_passport_compare = dataTable2.getValueOnOtherModule("evs_AccountCreation", "IDOrPassport", 0);
				
			}
			else {
				id_passport_compare = dataTable2.getValueOnCurrentModule("IDOrPassport");
			}
			

			if (LicenseAdd.equalsIgnoreCase("no")) {
				if (typeOfIdentity.equalsIgnoreCase("ID")) {
					action.javaScriptClick(evs_radioID, "select the ID identity type", test);
					if(action.getText(idValueAddedForValidation, "ID Value", test).isEmpty()) {
						dataTable2.setValueOnOtherModule("tvLicenseValidation", "IDOrPassport", idValueAddedForValidation.getText(), 0);
						action.CompareResult("if the ID correct", id_passport_compare, idValueAddedForValidation.getText(), test);
						//action.clear(evs_TextfieldID, "Clearing Validation Field");	
						action.writeText(evs_TextfieldID, id_passport_compare, "writing the ID", test);
					}
					//action.clear(evs_TextfieldID, "Clearing Validation Field");	
					//action.writeText(evs_TextfieldID, valueofIdentity, "writing the ID", test);
					action.explicitWait(5000);
					action.click(evs_buttonValidate, "Click validate", test);
					action.ajaxWait(10, test);
				}
				if (typeOfIdentity.equalsIgnoreCase("Passport")) {
					action.javaScriptClick(evs_radioPassport, "select the Passport identity type", test);
					if(action.getText(passportValueAddedForValidation, "Passport Value", test).isEmpty()) {
						dataTable2.setValueOnOtherModule("tvLicenseValidation", "IDOrPassport", passportValueAddedForValidation.getText(), 0);
						action.CompareResult("if the ID correct", id_passport_compare, passportValueAddedForValidation.getText(), test);
						//action.clear(evs_TextfieldPass, "Clearing Validation Field");
						action.writeText(evs_TextfieldPass, id_passport_compare, "writing the ID", test);
					}
					/*action.clear(evs_TextfieldPass, "Clearing Validation Field");
					action.writeText(evs_TextfieldPass, valueofIdentity, "writing the ID", test);*/
					action.explicitWait(5000);
					action.click(evs_buttonValidate, "Click validate", test);
					action.ajaxWait(10, test);
				}
				
				String existingLicence = action.getText(evs_ExistingTvLicence, "Validate No Outstangind balance", test);
				action.CompareResult("Adding the license fee R265 ", existingLicence,
						action.getText(evs_ExistingTvLicence, "", test), test);
				action.explicitWait(5000);
				action.click(evs_buttonProceed, "Click Proceed", test);
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
		String licenseAddition = dataTable2.getValueOnCurrentModule("License Addition");
		String tvLicenseAmount = dataTable2.getValueOnCurrentModule("TV License Amount");
		// Map<String,List<String>> allProductsInCart = EVS_ProductSearch.productData;
		String allProductsInCart = dataTable2.getValueOnOtherModule("evs_ProductSearch", "CartTotal", 0);
		String subtotal = action.getText(subtotalAfterLicense, "Cart Subtotal", test).replace("R", "").replace(",", "");
		if (licenseAddition.equalsIgnoreCase("yes")) {
			// for (Map.Entry selectedProducts : allProductsInCart.entrySet()) {
			double subTotalAfterTVLicense = Double.parseDouble(allProductsInCart) + Double.parseDouble(tvLicenseAmount);
			action.CompareResult("TV License R265 has been added to cart", subtotal,
					String.valueOf(subTotalAfterTVLicense), test);

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
					EVS_ProductSearch.productData.put("TV License", tvLicenseProdSKU);					
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
