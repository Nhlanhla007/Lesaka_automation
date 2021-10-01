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
	EVS_Cart cart ;
    
   //Ic_Products ic_products;
	
	 public EVS_RedeemGiftCard(WebDriver driver, DataTable2 dataTable2) {
		 this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
		 this.dataTable2=dataTable2;
	 //       ic_products = new Ic_Products(driver, dataTable2);
	       cart = new EVS_Cart(driver, dataTable2);
	 }
	 
	 static Logger logger = Log.getLogData(Action.class.getSimpleName());
	 
	 @FindBy(xpath = "//span[contains(text(),'Products')]")
	 WebElement evs_ProductLink;
	 
	 //@FindBy(xpath = "//html/body/div[1]/header/div/div/a/span[2]")
	 @FindBy(css = "a.go-back")
	 WebElement evs_Back;
	 
	 @FindBy(xpath = "//*[@id=\"block-giftcard-heading\"]")
	 WebElement evs_RedeemGiftCardSelect;
	 
	 @FindBy(xpath = "//*[@id=\"giftcard-code\"]")
	 WebElement evs_GiftCardCode;
	 
	 @FindBy(xpath = "//*[@id=\"giftcard-scratch-code\"]")
	 WebElement evs_GiftCardScratchCode;
	 
	 @FindBy(xpath = "//*[@id=\"giftcard-form\"]/div/div[3]/div[2]/button/span")
	 WebElement evs_Checkstatusbalance;
	 
	 @FindBy(xpath = "//*[@id=\"giftcard-form\"]/div/div[3]/div[1]/button/span")
	 WebElement evs_Apply;
	 
	 @FindBy(xpath = "//*[@id=\"maincontent\"]/div[1]/div[2]/div/div/div")
	 WebElement evs_SuccessfullyApplied;
	 
	 @FindBy(xpath = "//*[@id=\"cart-totals\"]/div/table/tbody/tr[1]/td/span")
	 WebElement evs_miniCartSubtotal;
	 
	 @FindBy(xpath = "//*[@id=\"cart-totals\"]/div/table/tbody/tr[3]/td")
	 WebElement evs_GiftcardAmount;
	 
	 @FindBy(xpath = "//*[@id=\"cart-totals\"]/div/table/tbody/tr[5]/td/strong/span")
	 WebElement evs_orderNumber;
	 
	 @FindBy(xpath = "//*[@id=\"top-cart-btn-checkout\"]/span")
	    private WebElement evs_Secure;
	 
	 @FindBy(xpath="//*[@id=\"checkout-shipping-method-load\"]/table/tbody/tr[1]/td[4]/button")
	    private WebElement evs_Deliver;
	 
	 ///html/body/div[1]/header/div[3]/div[2]/div/div/div
	 ///html/body/div[1]/header/div[3]/div[2]/div/div/div/text()
	//div[contains(text(),'Please correct the gift card code.')]
	 @FindBy(xpath="//div[contains(text(),'Please correct the gift card code.')]")
	    private WebElement evs_GiftCardError;
	 
	 @FindBy(xpath="//*[@id=\"opc-sidebar\"]/div[1]/div[1]/button/span")
	    private WebElement evs_ContinuePayment;
	 
	 @FindBy(xpath = "//*[@class=\"message-error error message\"]")
	 private WebElement invalidCouponCodeErrorPopUp;
	 
	 int finalAmount = 0;
	 int finalOrder = 0;
	 public void redeemGiftCard(ExtentTest test) throws IOException, Exception{
		 String giftCardCode = dataTable2.getValueOnCurrentModule("giftCardCode");//input.get("giftCardCode").get(rowNumber);
		 String scratchCode = dataTable2.getValueOnCurrentModule("scratchCode");//input.get("scratchCode").get(rowNumber);
		 String UsageType = dataTable2.getValueOnCurrentModule("UsageType");
		 
			boolean buttonAvail = action.waitUntilElementIsDisplayed(evs_Back, 20000);
			action.explicitWait(4000);
			if (buttonAvail) {
				evs_Back.click();
			}else {
				throw new Exception("Back Navigation button is not clickable");
			}
			 
			if (action.waitUntilElementIsDisplayed(evs_RedeemGiftCardSelect, 10000)) {
				action.scrollElemetnToCenterOfView(evs_RedeemGiftCardSelect, "Redeem Gift Card Tab", test);
				action.click(evs_RedeemGiftCardSelect, "Before you enter the gift card details", test);
			}

			if (UsageType.equalsIgnoreCase("Redeem")) {
				action.writeText(evs_GiftCardCode, giftCardCode, "Gift Card code", test);
				action.writeText(evs_GiftCardScratchCode, scratchCode, "Scratch Code", test);
				// click apply
				action.click(evs_Apply, "apply the the gift card", test);
				action.waitForPageLoaded(20);
				action.ajaxWait(10, test);
				String giftCarddValidate = null;
				if (action.elementExistWelcome(evs_SuccessfullyApplied, 4, "Gift card added", test)) {
					giftCarddValidate = action.getText(evs_SuccessfullyApplied, "gift card added", test);
				}

				action.CompareResult("Gift card added", "Gift Card \"" + giftCardCode.trim() + "\" was added.",giftCarddValidate.trim(), test);
				String subTotal = action.getText(evs_miniCartSubtotal, "Subtotal", test);
				String cardAmount = action.getText(evs_GiftcardAmount, "CardAmount", test);
				String finalOrder = action.getText(evs_orderNumber, "value", test);

				finalAmount = (Integer.parseInt(subTotal.replace("R", ""))
						- Integer.parseInt(cardAmount.replace("-", "").replace("R", "")));

				String s = String.valueOf(finalAmount);

				action.CompareResult("Final order finalized ", "R" + s, finalOrder, test);

				 //validate
		 action.click(evs_Secure, "Checkout Secure clicked", test);
		 action.waitForPageLoaded(20);
		 action.ajaxWait(10, test);
		 
		 }else if(UsageType.equalsIgnoreCase("Reuse")){
			 action.scrollElemetnToCenterOfView(evs_GiftCardCode, "Gift card code", test);
			 action.writeText(evs_GiftCardCode, giftCardCode, "Gift Card code", test);
			 action.writeText(evs_GiftCardScratchCode, scratchCode, "Scratch Code", test);
			 
			 action.click(evs_Apply, "apply the the gift card", test);
			 String giftCarddValidate = null;
			 if(action.elementExistWelcome(evs_GiftCardError, 4, "Please correct the gift card code.", test)){
				 giftCarddValidate = action.getText(evs_GiftCardError, "Please correct the gift card code.",test);				 
				 action.explicitWait(5000);
			 }
			 
		 }
		 
	 }

	 
	 public void giftCardWithInvalidCouponCode(ExtentTest test) throws Exception {
	//	 cart.navigateToCart(test);
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
