package ic_PageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class IC_ESDpurchase {
	
	  WebDriver driver;
	  Action action;
	  DataTable2 dataTable2;

	Ic_Products ic_products;
	ic_PayUPayment ic_payU;

	public IC_ESDpurchase(WebDriver driver, DataTable2 dataTable2) {

		this.driver = driver;
		this.dataTable2 = dataTable2;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		ic_products = new Ic_Products(driver, dataTable2);
		ic_payU = new ic_PayUPayment(driver, dataTable2);
	}

	@FindBy(xpath = "//a[contains(text(),'Learn More')]")
	WebElement esd_LearnMore;

	@FindBy(xpath = "//div[@id='modal-content-1']")
	WebElement esd_itemModal;

	@FindBy(xpath = "//*[@class=\"modal-popup mobicred-popup _show\"]/div/header/button")
	WebElement esd_modalClose;

	@FindBy(xpath = "//span[contains(text(),'More Info')]")
	WebElement ic_MoreInfo;

	@FindBy(xpath = "//div[@class=\"product attribute sku\"]/div")
	WebElement ic_proCode;

	@FindBy(xpath = "//span[contains(text(),'Back')]")
	private WebElement backButton;

	@FindBy(xpath = "//tbody/tr[1]/td[1]/div[1]/strong[1]/a[1]")
	private WebElement ic_proName;

	@FindBy(xpath = "//*[@class=\"action showcart\"]")
	private WebElement ic_Cart;

	@FindBy(xpath = "//*[@class=\"price-container price-final_price tax weee\"]/span/span")
	private WebElement ic_esdPrice;

	@FindBy(xpath = "//*[@id=\"minicart-content-wrapper\"]/div[2]/div[2]/div[1]/div/span/span")
	private WebElement icSubtotal;

	@FindBy(xpath = "//*[@id=\"top-cart-btn-checkout\"]/span")
	private WebElement ic_secure;

	public void validateLearnMore(ExtentTest test, int rowNumber) throws IOException, Exception {
		action.explicitWait(2000);
		action.scrollElemetnToCenterOfView(esd_LearnMore, "scroll to learn more", test);
		action.click(esd_LearnMore, "Click learn more", test);
		if (action.elementExistWelcome(esd_itemModal, 60, "ESD explained information", test)) {
			action.explicitWait(2000);
			action.javaScriptClick(esd_modalClose, "close the ESD modal", test);
			action.scrollElemetnToCenterOfView(ic_proCode, "scroll to the sku", test);
		} else {
			throw new Exception("ESD modal didn't open");
		}
	}

	public void navigateBack(ExtentTest test) throws Exception {
		boolean buttonAvail = action.waitUntilElementIsDisplayed(backButton, 20000);
		action.explicitWait(4000);
		if (buttonAvail) {
			action.javaScriptClick(backButton,"Back Button",test);
		}
	}


	public void learnMoreESD(ExtentTest test, int rowNumber) throws IOException, Exception {
		navigateBack(test);
		action.waitForJStoLoad(20);
		action.javaScriptClick(ic_proName, "Click on product discription", test);
		action.waitForPageLoaded(5000);
		validateLearnMore(test, rowNumber);
		action.waitForJStoLoad(10000);
		action.click(ic_Cart, "Cart", test);
		action.explicitWait(2000);
		action.click(ic_secure, "Click secure check out", test);

	}

}
