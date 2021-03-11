package JDGroupPageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.ConfigFileReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ic_PayUPayment {
		WebDriver driver;
		Action action;
		
		public ic_PayUPayment(WebDriver driver) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
			action = new Action(driver);
		}
		
		// PAYU site pay option
		@FindBy(xpath = "//div[@class='toggle-group']//div[1][text()='Card']")
		WebElement PayU_Card;
		@FindBy(xpath = "//*[@id='0_cardNumber']")
		WebElement cardNumber;
		@FindBy(xpath = "//input[@id='0_nameOnCard']")
		WebElement nameOnCard;
		@FindBy(xpath = "//select[@id='0_expMonth']")
		WebElement expMonth;
		@FindBy(xpath = "//select[@id='0_expYear']")
		WebElement expYear;
		@FindBy(xpath = "//input[@id='0_cvv']")
		WebElement cvvNumber;
		@FindBy(xpath = "//button[@id='btnPay']")
		WebElement PayBtn;
		
		@FindBy(xpath = "//p[contains(text(),'Your order # is')]")
		WebElement OderID;
		

		public void PayUPagePayment(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
			String cardnumber = input.get("cardnumber").get(rowNumber);
			String cardholdername = input.get("cardholdername").get(rowNumber);
			String Expiremonth = input.get("Expiremonth").get(rowNumber);
			String ExpireYear = input.get("ExpireYear").get(rowNumber);
			String cvv = input.get("cvv").get(rowNumber);
			action.explicitWait(5000);
			action.clickEle(PayU_Card, " Card option in PayU",test);
			//Enter card details
			action.writeText(cardNumber,cardnumber, "card number",test);
			action.writeText(nameOnCard, cardholdername, "name on card",test);
			action.dropDownselectbyvisibletext(expMonth, Expiremonth, "Select Expirey Month on Card",test);
			action.dropDownselectbyvisibletext(expYear, ExpireYear, "Select Expirey Month on Card",test);
			action.writeText(cvvNumber, cvv, "cvv number",test);
			action.clickEle(PayBtn, "Payment submission button",test);
			action.explicitWait(10);
			//Retrieve order ID
			action.isElementOnNextPage(OderID, (long) 5,test);
			String Oderid= action.getText(OderID, "Order ID");
			ConfigFileReader configFileReader = new ConfigFileReader();
			configFileReader.setPropertyVal("OrderID",Oderid.replace("Your order # is: ","").replace(".",""));
			input.get("OrderID").set(rowNumber,Oderid.replace("Your order # is: ","").replace(".",""));
			System.out.println("##############END Execution###############");
		}
}
