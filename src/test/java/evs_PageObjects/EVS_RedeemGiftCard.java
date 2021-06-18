package evs_PageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import Logger.Log;
import utils.Action;
import utils.DataTable2;

public class EVS_RedeemGiftCard {

	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	EVS_Cart cart;

	public EVS_RedeemGiftCard(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;
		// ic_products = new Ic_Products(driver, dataTable2);
		cart = new EVS_Cart(driver, dataTable2);
	}

	static Logger logger = Log.getLogData(Action.class.getSimpleName());

	@FindBy(xpath = "//span[contains(text(),'Products')]")
	WebElement evs_ProductLink;

	@FindBy(css = "a.go-back")
	WebElement evs_Back;

	@FindBy(xpath = "//span[contains(text(),'Want to redeem a gift card?')]")
	WebElement evs_RedeemGiftCardSelect;

	@FindBy(xpath = "//input[@id='giftcard-code']")
	WebElement evs_GiftCardCode;

	@FindBy(xpath = "//input[@id='giftcard-scratch-code']")
	WebElement evs_GiftCardScratchCode;

	@FindBy(xpath = "//span[contains(text(),'Check status')]")
	WebElement evs_Checkstatus;

	@FindBy(xpath = "//span[contains(text(),'Current Balance:')]/../span[@class='price']")
	WebElement card_currentBalance;

	@FindBy(xpath = "//span[contains(text(),'Check status')]/../../../preceding-sibling::div/button")
	WebElement evs_Apply;

	@FindBy(xpath = "//html/body/div[1]/header/div[3]/div[2]/div/div/div")
	WebElement evs_SuccessfullyApplied;

	@FindBy(xpath = "//*[@id=\"cart-totals\"]/div/table/tbody/tr[1]/td/span")
	WebElement evs_miniCartSubtotal;

	@FindBy(xpath = "//*[@id=\"cart-totals\"]/div/table/tbody/tr[3]/td")
	WebElement evs_GiftcardAmount;

	@FindBy(xpath = "//tr[@class='grand totals']/td/strong/span")
	WebElement evs_totalOrderAmount;

	@FindBy(xpath = "//*[@id=\"maincontent\"]/div[2]/div/div/div[1]/div[1]/ul/li/button")
	private WebElement evs_Secure;

	@FindBy(xpath = "//*[@id=\"checkout-shipping-method-load\"]/table/tbody/tr[1]/td[4]/button")
	private WebElement evs_Deliver;

	@FindBy(xpath = "//div[contains(text(),'Could not add gift card code')]")
	private WebElement evs_GiftCardError;

	@FindBy(xpath = "//span[contains(text(),'Order Summary')]/../div/button")
	private WebElement evs_ContinuePayment;

	@FindBy(xpath = "//*[@class=\"message-error error message\"]")
	private WebElement invalidCouponCodeErrorPopUp;

	int finalAmount = 0;
	int finalOrder = 0;

	public void redeemGiftCard(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber)
			throws IOException, Exception {
		String giftCardCode = dataTable2.getValueOnCurrentModule("giftCardCode");
		String scratchCode = dataTable2.getValueOnCurrentModule("scratchCode");
		String UsageType = dataTable2.getValueOnCurrentModule("UsageType");

		action.explicitWait(10000);
		action.clickEle(evs_ContinuePayment, "Click on Continue",test);

		if (action.waitUntilElementIsDisplayed(evs_RedeemGiftCardSelect, 15000)) {
			action.scrollElemetnToCenterOfView(evs_RedeemGiftCardSelect, "Redeem Gift Card Tab", test);
			action.explicitWait(3000, test);
			action.click(evs_RedeemGiftCardSelect, "Want to redeem a gift card?", test);

		}

		if (UsageType.equalsIgnoreCase("Redeem")) {
			action.writeText(evs_GiftCardCode, giftCardCode, "Gift Card code", test);
			action.writeText(evs_GiftCardScratchCode, scratchCode, "Scratch Code", test);
			// click apply
			action.click(evs_Apply, "apply the the gift card", test);
			action.explicitWait(5000);
			String giftCarddValidate = null;
			if (action.elementExistWelcome(evs_SuccessfullyApplied, 5, "Gift card added", test)) {
				giftCarddValidate = action.getText(evs_SuccessfullyApplied, "gift card added", test);
			}

			action.CompareResult("Gift card added", "Gift Card \"" + giftCardCode.trim() + "\" was added.",
					giftCarddValidate, test);
			String subTotal = action.getText(evs_miniCartSubtotal, "Subtotal", test);
			String cardAmount = action.getText(evs_GiftcardAmount, "CardAmount", test);
			String finalOrder = action.getText(evs_totalOrderAmount, "value", test);

			finalAmount = (Integer.parseInt(subTotal.replace("R", ""))
					- Integer.parseInt(cardAmount.replace("-", "").replace("R", "")));

			String s = String.valueOf(finalAmount);

			action.CompareResult("Final order finalized ", "R" + s, finalOrder, test);

			action.click(evs_Secure, "Checkout Secure clicked", test);

		} else if (UsageType.equalsIgnoreCase("Reuse")) {

			String before_totalOrderPrize = action.getText(evs_totalOrderAmount, "value", test);
			action.scrollElemetnToCenterOfView(evs_GiftCardCode, "Gift card code", test);
			action.writeText(evs_GiftCardCode, giftCardCode, "Gift Card code", test);
			action.clickEle(evs_Checkstatus, "Check balance", test);
			action.explicitWait(3000);

			String cardBalance = action.getText(card_currentBalance, "Gift Card Current Balance", test);
			action.CompareResult("Check gift card Balance", "R"+"0", cardBalance, test);

			action.click(evs_Apply, "apply the the gift card", test);
			action.explicitWait(5000, test);
			String giftCarddValidate = null;
			
			if (action.elementExistWelcome(evs_GiftCardError, 4, "Please enter correct gift card code.", test)) {
				action.scrollElementIntoView(evs_GiftCardError);
				giftCarddValidate = action.getText(evs_GiftCardError, "Please correct the gift card code.", test);
				action.CompareResult("Incorrect gift Code", "Could not add gift card code", giftCarddValidate, test);
			}

			action.scrollElemetnToCenterOfView(evs_totalOrderAmount, "Order Amount after gift card applied", test);
			String after_totalOrderPrize = action.getText(evs_totalOrderAmount, "value", test);
			action.CompareResult("Validate Updated Total order amount", after_totalOrderPrize, before_totalOrderPrize,
					test);

		}

	}

	public void giftCardWithInvalidCouponCode(ExtentTest test) throws Exception {
		// cart.navigateToCart(test);
		// cart.navigateToViewAndEditCart(test);
		action.click(evs_RedeemGiftCardSelect, "Redeem gift cart", test);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,1000)");
		action.writeText(evs_GiftCardCode, "abcdGiftWrongGiftCart", "Enter incorrect gift cart code", test);
		action.click(evs_Apply, "Apply gift card coupon code", test);
		action.elementExistWelcome(invalidCouponCodeErrorPopUp, 7, "Invalid Coupon Code pop up error", test);
		String popUpErrorMessage = invalidCouponCodeErrorPopUp.findElement(By.xpath(".//div")).getText();
		action.CompareResult("Pop up error message", "Please correct the gift card code.", popUpErrorMessage, test);
	}

}
