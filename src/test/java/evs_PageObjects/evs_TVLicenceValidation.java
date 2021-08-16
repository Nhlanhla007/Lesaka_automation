package evs_PageObjects;

import java.util.ArrayList;
import java.util.List;

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

	// Bongi Code
	// *******************************************
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

	public void TvLicenceValidation(ExtentTest test, int rowNumber) throws Exception {
		String LicenseAdd = dataTable2.getValueOnCurrentModule("License Addition");
		String typeOfIdentity = dataTable2.getValueOnCurrentModule("Type");
		String valueofIdentity = dataTable2.getValueOnCurrentModule("ID/Passport");

		if (action.elementExistWelcome(evs_popUpElement, 10000, "TV licence", test)) {
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

			if (LicenseAdd.equalsIgnoreCase("no")) {
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

}
