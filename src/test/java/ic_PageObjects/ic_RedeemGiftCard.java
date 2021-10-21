package ic_PageObjects;

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

public class ic_RedeemGiftCard {
	
	WebDriver driver;
    Action action;
	DataTable2 dataTable2;
	IC_Cart cart ;
    
   Ic_Products ic_products;
	
	 public ic_RedeemGiftCard(WebDriver driver, DataTable2 dataTable2) {
		 this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
		 this.dataTable2=dataTable2;
	        ic_products = new Ic_Products(driver, dataTable2);
	        cart = new IC_Cart(driver, dataTable2);
	 }
	 
	 static Logger logger = Log.getLogData(Action.class.getSimpleName());
	 
	 @FindBy(xpath = "//span[contains(text(),'Products')]")
	 WebElement icProductLink;
	 
	 //@FindBy(xpath = "//html/body/div[1]/header/div/div/a/span[2]")
	 @FindBy(css = "a.go-back")
	 WebElement ic_Back;
	 
	 @FindBy(xpath = "//*[@id=\"block-giftcard-heading\"]")
	 WebElement ic_RedeemGiftCardSelect;
	 
	 @FindBy(xpath = "//*[@id=\"giftcard-code\"]")
	 WebElement ic_GiftCardCode;
	 
	 @FindBy(xpath = "//*[@id=\"giftcard-scratch-code\"]")
	 WebElement ic_GiftCardScratchCode;
	 
	 @FindBy(xpath = "//*[@id=\"giftcard-form\"]/div/div[3]/div[2]/button/span")
	 WebElement ic_Checkstatusbalance;
	 
	 @FindBy(xpath = "//*[@id=\"giftcard-form\"]/div/div[3]/div[1]/button/span")
	 WebElement ic_Apply;
	 
	 @FindBy(xpath = "//*[@data-bind=\"html: $parent.prepareMessageForHtml(message.text)\"]")
	 WebElement ic_SuccessfullyApplied;
	 
	 @FindBy(xpath = "//*[@id=\"cart-totals\"]/div/table/tbody/tr[1]/td/span")
	 WebElement ic_miniCartSubtotal;
	 
	 @FindBy(xpath = "//*[@id=\"cart-totals\"]/div/table/tbody/tr[3]/td")
	 WebElement ic_GiftcardAmount;
	 
	 @FindBy(xpath = "//*[@id=\"cart-totals\"]/div/table/tbody/tr[5]/td/strong/span")
	 WebElement ic_orderNumber;
	 				
	 @FindBy(xpath="//*[@id=\"maincontent\"]/div[2]/div/div/div[1]/div[1]/ul/li/button")
	    private WebElement ic_secure;
	 
	 @FindBy(xpath="//*[@id=\"checkout-shipping-method-load\"]/table/tbody/tr[1]/td[4]/button")
	    private WebElement ic_Deliver;
	 
	 ///html/body/div[1]/header/div[3]/div[2]/div/div/div
	 ///html/body/div[1]/header/div[3]/div[2]/div/div/div/text()
	//div[contains(text(),'Please correct the gift card code.')]
	 @FindBy(xpath="//div[contains(text(),'Please correct the gift card code.')]")
	    private WebElement ic_giftCardError;
	 
	 @FindBy(xpath="//*[@id=\"opc-sidebar\"]/div[1]/div[1]/button/span")
	    private WebElement ic_continuePayment;
	 
	 @FindBy(xpath = "//*[@class=\"message-error error message\"]")
	 private WebElement invalidCouponCodeErrorPopUp;
	 
	 double finalAmount = 0;
	 int finalOrder = 0;
	 public void redeemGiftCard(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException, Exception{
		 String giftCardCode = input.get("giftCardCode").get(rowNumber);
		 String scratchCode = input.get("scratchCode").get(rowNumber);
		 String UsageType = dataTable2.getValueOnCurrentModule("UsageType");
		 
		 if(action.waitUntilElementIsDisplayed(ic_Back, 10000)) {
		 action.explicitWait(5000);
		 action.click(ic_Back, "Navigate back to cart screen", test);
		 }
		 if(action.waitUntilElementIsDisplayed(ic_RedeemGiftCardSelect, 10000)) {
		 action.click(ic_RedeemGiftCardSelect, "Before you enter the gift card details", test);
		 }
		 //Enter the field
		 if(UsageType.equalsIgnoreCase("Redeem")){
			action.scrollElemetnToCenterOfView(ic_GiftCardCode,"Scroll to gift card view",test); 
		 action.writeText(ic_GiftCardCode, giftCardCode, "Gift Card code", test);
		 action.writeText(ic_GiftCardScratchCode, scratchCode, "Scratch Code", test);
		 //click apply
		 action.click(ic_Apply, "apply the the gift card", test);
		 String giftCarddValidate = null;
		 if(action.elementExists(ic_SuccessfullyApplied, 5)){
			 giftCarddValidate = action.getText(ic_SuccessfullyApplied, "gift card added",test);
			 action.CompareResult("Gift card added", "Gift Card \""+giftCardCode.trim()+"\" was added.",giftCarddValidate.trim() , test);
		 } else{
			 action.CompareResult("Popup appeared?", "True", "False", test);
			 throw new Exception("There's a problem with the gift card code");
		 }

		 String subTotal = action.getText(ic_miniCartSubtotal, "Subtotal",test);
		 String cardAmount = action.getText(ic_GiftcardAmount, "CardAmount",test);
		 String finalOrder = action.getText(ic_orderNumber, "value",test);
		 double subTotalInt = Integer.parseInt(subTotal.replace("R", "").replace(",", ""));
		 double cardAmountInt = Integer.parseInt(cardAmount.replace("-", "").replace("R", ""));
		 double finalOrderInt = Integer.parseInt(finalOrder.replace("R", "").replace(",", ""));
			
		 finalAmount = subTotalInt - cardAmountInt;
		 
		 String s= String.valueOf(finalAmount);
		 String fo = String.valueOf(finalOrderInt);
		 
		 action.CompareResult("Final order finalized ", s, fo, test);
		 
		 //validate
		 action.click(ic_secure, "Checkout Secure clicked", test);
		 
		 }
		 
		 if(UsageType.equalsIgnoreCase("Reuse")){
			 action.scrollElemetnToCenterOfView(ic_GiftCardCode,"Scroll to gift card view",test); 
			 action.writeText(ic_GiftCardCode, giftCardCode, "Gift Card code", test);
			 action.writeText(ic_GiftCardScratchCode, scratchCode, "Scratch Code", test);
			 
			 action.click(ic_Apply, "apply the the gift card", test);
			 String giftCarddValidate = null;
			 if(action.elementExists(ic_giftCardError, 5)){
				 giftCarddValidate = action.getText(ic_giftCardError, "Please correct the gift card code.",test);
				 action.CompareResult("Popup is displayed", giftCarddValidate, "Please correct the gift card code.", test);
				 action.explicitWait(5000);
			 } else {
				 action.CompareResult("Popup appeared?", "True", "False", test);
				 throw new Exception("The pop-up didn't appear");
			 }
			 
		 }
		 
	 }

	 
	 public void giftCardWithInvalidCouponCode(ExtentTest test) throws Exception {
		 cart.navigateToCart(test);
		 cart.navigateToViewAndEditCart(test);
		 action.click(ic_RedeemGiftCardSelect, "Redeem gift cart", test);
		 JavascriptExecutor js = (JavascriptExecutor) driver;
		 js.executeScript("window.scrollBy(0,1000)");
		 action.writeText(ic_GiftCardCode, "abcdGiftWrongGiftCart", "Enter incorrect gift cart code", test);
		 action.click(ic_Apply, "Apply gift card coupon code", test);
		 action.elementExistWelcome(invalidCouponCodeErrorPopUp, 7, "Invalid Coupon Code pop up error", test);
		 String popUpErrorMessage = invalidCouponCodeErrorPopUp.findElement(By.xpath(".//div")).getText();
		 action.CompareResult("Pop up error message", "Please correct the gift card code.", popUpErrorMessage, test);
	 }
	 
}
