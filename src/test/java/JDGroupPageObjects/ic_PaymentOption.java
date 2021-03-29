package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import io.qameta.allure.Step;
import utils.Action;
import utils.DataTable2;

public class ic_PaymentOption {
	WebDriver driver;
	Action action;
	
	public ic_PaymentOption(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
	}
	//place order button
	@FindBy(xpath = "//*[@id='opc-sidebar']/div[1]/div[1]/button")
	WebElement Btn_PlaceOrder;
	//payement options
	
	@FindBy(xpath = "//span[contains(text(),'Credit Card (Processed By PayU)')]")
	WebElement creditcard;
	@FindBy(xpath = "//span[contains(text(),'Visa Checkout')]")
	WebElement VisaCheckout;
	@FindBy(xpath = "//span[contains(text(),'EFT Pro (Processed By PayU)')]")
	WebElement EFT_Pro;
	@FindBy(xpath = "//span[contains(text(),'Masterpass')]")
	WebElement Masterpass;
	@FindBy(xpath = "//span[contains(text(),'Pay with PayGate')]")
	WebElement PayGate;
	@FindBy(xpath = "//span[contains(text(),'uCount (Processed By PayU)')]")
	WebElement uCount;
	@FindBy(xpath = "//span[contains(text(),'Discovery Miles (Processed By PayU)')]")
	WebElement Discovery_Miles;
	@FindBy(xpath = "//span[contains(text(),'RCS Credit (Processed By PayU)')]")
	WebElement RCS_Credit;
	@FindBy(xpath = "//span[contains(text(),'Cash Deposits')]")
	WebElement Cash_Deposits;
	@FindBy(xpath = "//body/div[2]/main[1]/div[2]/div[1]/div[1]/div[4]/ol[1]/li[3]/div[1]/form[1]/fieldset[1]/div[1]/div[1]/div[1]/div[12]/div[1]/label[1]/span[1]")
	WebElement card;
	
	//@FindBy(xpath = "//*[@id='billing-address-same-as-shipping-payumea_creditcard']")
	//WebElement Billingshipping;
	@FindBy(xpath = "//*[@id='checkout-payment-method-load']/div/div/div[3]/div[2]/div[2]/div/div/label/span")
	WebElement Billingshipping;
	@FindBy(xpath = "//span[contains(text(),'I agree to all the terms & conditions')]")
	WebElement TermsCondition;
	
	@Step("To Select payment method")
	public WebElement ic_SelectPaymentMethod(String Paytype){
			Map<String,WebElement> PaymentMap=new HashMap<String,WebElement>();
			PaymentMap.put("creditcard",creditcard);  
			PaymentMap.put("VisaCheckout",VisaCheckout);
			PaymentMap.put("EFT_Pro",EFT_Pro);
			PaymentMap.put("Masterpass",Masterpass);
			PaymentMap.put("PayGate",PayGate);
			PaymentMap.put("uCount",uCount);
			PaymentMap.put("Discovery_Miles",Discovery_Miles);
			PaymentMap.put("RCS_Credit",RCS_Credit);
			PaymentMap.put("Cash_Deposits",Cash_Deposits);
			PaymentMap.put("card",card);
			WebElement actionele =null;
			Flag:
			 for(Map.Entry m:PaymentMap.entrySet()){  
				   if(m.getKey().toString().trim().toUpperCase().equalsIgnoreCase(Paytype)){
					   System.out.println("FOUND match with excel pay type");  
					    actionele =(WebElement) m.getValue();
					    break Flag;	   
				   }
				 
			}  
			
			return actionele;	
     }

	public void CheckoutpaymentOption(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
		try {
			action.explicitWait(14000);
			System.out.println("##############START Execution###############");
			action.explicitWait(8000);
			String Paytype = input.get("Paytype_Option").get(rowNumber);
			action.CheckEnabilityofButton(Btn_PlaceOrder, "Place Order", false, test);
			WebElement paymenttype = ic_SelectPaymentMethod(Paytype);
			action.waitExplicit(10);
			action.clickEle(paymenttype, "Select Payment option " + Paytype, test);
			action.waitExplicit(15);
			action.clickEle(Btn_PlaceOrder, "Click on Place order Button ", test);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
}
	
