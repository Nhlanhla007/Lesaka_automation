package evs_PageObjects;
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

public class EVS_GiftCardPurchase {
	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    
    EVS_ProductSearch evs_products;
    EVS_PayUPayment evs_payU;
	
	public EVS_GiftCardPurchase(WebDriver driver, DataTable2 dataTable2){
		
		this.driver = driver;
		this.dataTable2 = dataTable2;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        evs_products = new EVS_ProductSearch(driver, dataTable2);
        evs_payU = new EVS_PayUPayment(driver, dataTable2);
	}
	
	@FindBy(xpath="//input[@id='giftcard-amount-input']")
    private WebElement evs_AmountInput;
	
	@FindBy(xpath="//input[@id='giftcard_sender_name']")
    private WebElement evs_SenderName;
	
	@FindBy(xpath="//input[@id='giftcard_sender_email']")
    private WebElement evs_SenderEmail;
	
	@FindBy(xpath="//input[@id='giftcard_recipient_name']")
    private WebElement evs_RecipientName;
	
	@FindBy(xpath="//input[@id='giftcard_recipient_email']")
    private WebElement evs_RecipientEmail;
	
	@FindBy(xpath="//*[@id=\"giftcard-message\"]")
    private WebElement evs_CardMessage;
	
	@FindBy(xpath="//*[@id=\"product-addtocart-button\"]/span")
    private WebElement evs_AddToCart;
	
	//@FindBy(xpath="/html/body/div[2]/header/div[2]/div/div[3]/div[3]/a")
	@FindBy(xpath="//*[@class=\"action showcart\"]")
    private WebElement evs_Cart;
	
	@FindBy(xpath="//*[@id=\"maincontent\"]/div[2]/div[1]/div[6]/div[2]/ol/li/div/div[2]/div[5]/div[2]/div[1]/form/button/span")
    private WebElement evs_viewItem;
	
	@FindBy(xpath="//*[@id=\"maincontent\"]/div[2]/div[1]/div[6]/div[2]/ol/li/div/div[2]")
    private WebElement evs_detail;
	
	@FindBy(xpath="//*[@id=\"top-cart-btn-checkout\"]/span")
    private WebElement evs_secure;
	
    @FindBy(xpath="//*[@id=\"minicart-content-wrapper\"]/div[3]/div[2]/div[1]/div/span/span")
    private WebElement evs_Subtotal;
    
	
	public void purchaseGiftCard(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException, Exception{
		/*String selectAmountFlag = input.get("selectAmountFlag").get(rowNumber);
		String selectAmount = input.get("selectAmount").get(rowNumber);*/
		String inputAmount = input.get("inputAmount").get(rowNumber);
		String senderName = input.get("senderName").get(rowNumber);
		String senderEmail = input.get("senderEmail").get(rowNumber);
		String recipientName = input.get("recipientName").get(rowNumber);
		String recipientEmail = input.get("recipientEmail").get(rowNumber);
		String cardMessage = input.get("cardMessage").get(rowNumber);
		String giftC = dataTable2.getValueOnCurrentModule("GiftCardSKU");
		evs_products.loadProductListingPage("SearchUsingSearchBar", giftC, test);
		String sapSKUValidation = dataTable2.getValueOnCurrentModule("sapSKUValidation");
		action.mouseover(evs_detail, "");
		action.click(evs_viewItem, "more information", test);
		/*
		 * if(selectAmountFlag.equalsIgnoreCase("Yes")){ action.writeText(ic_Amount,
		 * selectAmount, "Select the Amount", test); }else {
		 * action.writeText(ic_AmountInput, inputAmount, "Write your amount of choice",
		 * test); }
		 */
		action.clear(evs_AmountInput, "clear the amount");
		action.writeText(evs_AmountInput, inputAmount, "Enter your amount of choice", test);
		action.writeText(evs_SenderName, senderName, "sender name", test);
		action.writeText(evs_SenderEmail, senderEmail, "sender email", test);
		action.writeText(evs_RecipientName, recipientName, "recipient ame", test);
		action.writeText(evs_RecipientEmail, recipientEmail, "recipient email", test);
		action.writeText(evs_CardMessage, cardMessage, "card message", test);
		action.explicitWait(9000);
		action.click(evs_AddToCart, "add to cart", test);
		action.explicitWait(12000);
		action.click(evs_Cart, "cart clicked", test);
		
		//GetCartTotal and set to ProductSearch cart total
		String cartTots = action.getText(evs_Subtotal, "EVS SubTotal",test);
		cartTots= cartTots.replace("R", "").replace(",", "").replace(".00", "");
		if(action.waitUntilElementIsDisplayed(evs_Subtotal, 15000)) {
			action.explicitWait(5000);
		//cartTots.replace("R", "").replace(",", "").replace(".", "");
		dataTable2.setValueOnOtherModule("evs_ProductSearch", "CartTotal", cartTots, 0);
		}
		
		action.click(evs_secure, "Checkout Secure clicked", test);
		//["Gift Card",[R899,1]]
		//String giftCardAmountFlag = dataTable2.getValueOnOtherModule("icGiftCardPurchase", "selectAmountFlag", 0);
		String giftCardAmount;
			giftCardAmount = dataTable2.getValueOnOtherModule("evs_GiftCardPurchase", "inputAmount", 0);
			giftCardAmount.replace("R", "");
		
		List<String> giftCardData =new ArrayList<>();
		giftCardData.add("1");
		giftCardData.add(sapSKUValidation);

		giftCardData.add(cartTots);
		evs_products.productData =new LinkedHashMap<>();
		evs_products.productData.put("CH Gift Card IC SA", giftCardData);
		
	}

}






