package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class ic_GiftCardUsability {
	WebDriver driver;
    Action action;
    
   
	
	public ic_GiftCardUsability(WebDriver driver, DataTable2 dataTable2){
		
		this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        
	}
	
	@FindBy(xpath="//*[@id='block-giftcard-heading']//span[contains(text(),'Want to redeem a gift card?')]")
    private WebElement Reedem_giftcardOption;
	@FindBy(xpath="//input[@id='giftcard-code']")
    private WebElement Giftcard_code;
	@FindBy(xpath="//input[@id='giftcard-scratch-code']")
    private WebElement Giftcard_Scratchcode;
	
	@FindBy(xpath="//*[@id='giftcard-form']//button[@type='submit'] ")
    private WebElement Giftcard_Apply;
	
	@FindBy(xpath="//span[contains(text(),'Check status & balance')]")
    private WebElement Giftcard_BalanceCheck;
	
	@FindBy(xpath="//*[@id='giftcard-form']/div[1]/div[3]/div[2]/span[2]")
    private WebElement Giftcard_Balance;

	@FindBy(xpath="//table//tbody//tr[@class='grand totals']//td//span[@class='price']")
    private WebElement GrandTotal;
	public void VeriyGiftcardUsableity(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException, InterruptedException{
	    action.waitExplicit(15);
		int WaitTime =Integer.parseInt(input.get("DelayTime").get(rowNumber));// 5;
		String GiftcardCode = input.get("GiftcardCode").get(rowNumber);//"RFU6Z5BAZ7X8HG3";
		String ScratchCode = input.get("ScratchCode").get(rowNumber);//"5QDHR";
		String ExpBalance =input.get("ExpBalance").get(rowNumber);
		action.elementExists(Reedem_giftcardOption, WaitTime);
		action.clickEle(Reedem_giftcardOption, "Reedem_giftcardOption", test);
		if(GiftcardCode!=""){
			action.elementExists(Giftcard_code, WaitTime);
			action.writeText(Giftcard_code, GiftcardCode, "Giftcard_code", test);
		}
		if(ScratchCode!=""){
			action.elementExists(Giftcard_Scratchcode, WaitTime);
			action.writeText(Giftcard_Scratchcode, ScratchCode, "Giftcard_Scratchcode", test);
		}
		String ExpAmoutBeforegiftcard = action.getText(GrandTotal, " Total price before applying for gift card ");
		action.elementExists(Giftcard_Apply, WaitTime);
		action.clickEle(Giftcard_Apply, "Giftcard_Apply", test);
		Thread.sleep(8000);
		action.elementExists(Giftcard_BalanceCheck, WaitTime);
		action.clickEle(Giftcard_BalanceCheck, "Giftcard_BalanceCheck", test);
		Thread.sleep(8000);
		String ActualBalance = action.getText(Giftcard_Balance, "Giftcard_Balance");
		action.CompareResult(" Total Balance Left on Re-Using Gift card ", ExpBalance, ActualBalance, test);
		String AmountAfterGiftcard = action.getText(GrandTotal, " Total price before applying for gift card ");
		action.CompareResult(" Total Value of product remains constant, No balance in giftcard", ExpAmoutBeforegiftcard, AmountAfterGiftcard, test);
	}
	
}
