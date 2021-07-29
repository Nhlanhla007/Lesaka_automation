package ic_PageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import utils.Action;
import utils.DataTable2;

public class ic_PaymentOption {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	public ic_PaymentOption(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		this.dataTable2 = dataTable2;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	}
	//place order button
	@FindBy(xpath = "//*[@id='opc-sidebar']/div[1]/div[1]/button")
	WebElement Btn_PlaceOrder;
	//payement options
	
	@FindBy(xpath = "//span[contains(text(),'Credit Card (Processed By PayU)')]")
	WebElement payUcreditcard;
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
	
	//-------------------------------------------------------------------------------------------------------------------
	//gift card checkout
	
	@FindBy(xpath = "//*[@id=\"customer-email\"]")
	WebElement emaiL;

	/*@FindBy(name = "firstname")
	@FindBy(xpath = "/html/body/div[2]/main/div[2]/div/div/div[4]/ol/li[3]/div/form/fieldset/div[1]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[4]/div/input")*/
	//@FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[14]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[4]/div//input")
	@FindBy(xpath = "//*[@name=\"firstname\"]")
    WebElement firstnamE;
	
	/*@FindBy(xpath = "//input[@name="lastname"]")
	 * //*[@id="checkout-payment-method-load"]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[4]/div//input*/
	//@FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[14]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[5]/div//input")
	@FindBy(xpath = "//*[@name=\"lastname\"]")
    WebElement lastname;
	
	/*@FindBy(xpath = "//input[@name=\"telephone\"]")
	 * @FindBy(xpath = "/html/body/div[2]/main/div[2]/div/div/div[4]/ol/li[3]/div/form/fieldset/div[1]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[13]/div/input")*/
	//@FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[14]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[13]/div//input")
	@FindBy(xpath = "//*[@name=\"telephone\"]")
    WebElement telephone;
	
	/*@FindBy(xpath = "//input[@name=\"custom_attributes[suburb]\"]")
	 * @FindBy(xpath = "/html/body/div[2]/main/div[2]/div/div/div[4]/ol/li[3]/div/form/fieldset/div[1]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[8]/div/input")*/
	//@FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[14]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[8]/div//input")
	@FindBy(xpath = "//*[@name=\"custom_attributes[suburb]\"]")
    WebElement Suburb;
	
	//@FindBy(xpath = "/html/body/div[2]/main/div[2]/div/div/div[4]/ol/li[3]/div/form/fieldset/div[1]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/fieldset/div/div[1]/div/input")
	//@FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[14]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/fieldset/div/div[1]/div//input")
	@FindBy(xpath = "//*[@name=\"street[0]\"]")
	WebElement streetnamE;
	
	/*@FindBy(xpath = "//select[@name=\"region_id\"]")
	 * @FindBy(xpath = "/html/body/div[2]/main/div[2]/div/div/div[4]/ol/li[3]/div/form/fieldset/div[1]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[9]/div/select")*/
	//@FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[14]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[9]/div//select")
	@FindBy(xpath = "//select[@name=\"region_id\"]")
    WebElement province;
	
	/*@FindBy(xpath = "//input[@name=\"city\"]")
	 * @FindBy(xpath = "/html/body/div[2]/main/div[2]/div/div/div[4]/ol/li[3]/div/form/fieldset/div[1]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[7]/div/input")*/
	//@FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[14]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[7]/div//input")
	@FindBy(xpath = "//*[@name=\"city\"]")
    WebElement city;
	
	/*@FindBy(xpath = "//input[@name=\"postcode\"]")
	 * @FindBy(xpath= "/html/body/div[2]/main/div[2]/div/div/div[4]/ol/li[3]/div/form/fieldset/div[1]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[11]/div/input")*/
	//@FindBy(xpath= "//*[@id=\"checkout-payment-method-load\"]/div/div/div[14]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[11]/div//input")
	@FindBy(xpath = "//*[@name=\"postcode\"]")
    WebElement postalCode;
	
	//@FindBy(xpath = "/html/body/div[2]/main/div[2]/div/div/div[4]/ol/li[3]/div/form/fieldset/div[1]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[14]/div/input")
	//@FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[14]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[14]/div//input")
	@FindBy(xpath = "//*[@name=\"vat_id\"]")
	WebElement vatNumber;

    @FindBy(name = "custom_attributes[identity_number]")
    WebElement idNumber;
	
	
	
	//@FindBy(xpath = "//*[@id='billing-address-same-as-shipping-payumea_creditcard']")
	//WebElement Billingshipping;
	@FindBy(xpath = "//*[@id='checkout-payment-method-load']/div/div/div[3]/div[2]/div[2]/div/div/label/span")
	WebElement Billingshipping;
	@FindBy(xpath = "//span[contains(text(),'I agree to all the terms & conditions')]")
	WebElement TermsCondition;


	
	public WebElement ic_SelectPaymentMethod(String Paytype){
			Map<String,WebElement> PaymentMap=new HashMap<String,WebElement>();
			PaymentMap.put("payUcreditcard", payUcreditcard);
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
//					   System.out.println("FOUND match with excel pay type");
					    actionele =(WebElement) m.getValue();
					    break Flag;	   
				   }
				 
			}  
			
			return actionele;	
     }

//	public void CheckoutpaymentOption(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
//		try {
////			action.explicitWait(14000);
//			String Paytype = input.get("Paytype_Option").get(rowNumber);
//			action.CheckEnabilityofButton(Btn_PlaceOrder, "Place Order", false, test);
//			WebElement paymenttype = ic_SelectPaymentMethod(Paytype);
////			action.explicitWait(10);
//			action.scrollElemetnToCenterOfView(paymenttype,"paymenttype",test);
//			action.explicitWait(2000);
//			action.clickEle(paymenttype, "Select Payment option " + Paytype, test);
////			action.waitExplicit(15);
//			action.scrollElemetnToCenterOfView(Btn_PlaceOrder,"paymenttype",test);
//			action.explicitWait(2000);
//			action.clickEle(Btn_PlaceOrder, "Click on Place order Button ", test);
//			action.waitForPageLoaded(30);
//			action.ajaxWait(10,test);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}

		public void CheckoutpaymentOption(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber)
				throws IOException {
			try {
				action.waitUntilElementIsDisplayed(Btn_PlaceOrder, 10);
				action.explicitWait(7000);
				action.waitForPageLoaded(30);
				action.ajaxWait(10, test);
				String Paytype = input.get("Paytype_Option").get(rowNumber);
				action.CheckEnabilityofButton(Btn_PlaceOrder, "Place Order", false, test);
				WebElement paymenttype = ic_SelectPaymentMethod(Paytype);
				// action.explicitWait(10);
				action.ajaxWait(10, test);
 				action.explicitWait(1000);
 				action.ajaxWait(10, test);
 				action.explicitWait(1000);
 				action.ajaxWait(10, test);
				action.scrollElemetnToCenterOfView(paymenttype, "paymenttype", test);
				// action.explicitWait(2000);
				action.ajaxWait(10, test);
				action.javaScriptClick(paymenttype, "Select Payment option " + Paytype, test);
				// action.waitExplicit(15);
				action.ajaxWait(10, test);
 				action.explicitWait(1000);
 				action.ajaxWait(10, test);
 				action.explicitWait(1000);
 				action.ajaxWait(10, test);
				action.scrollElemetnToCenterOfView(Btn_PlaceOrder, "Place Order Button", test);
				action.explicitWait(2000);
				action.javaScriptClick(Btn_PlaceOrder, "Click on Place order Button ", test);
				action.ajaxWait(10, test);
				action.waitForPageLoaded(30);
				action.ajaxWait(10, test);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	
	public void CheckoutpaymentOptionGiftCard(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws Exception{
		if(action.waitUntilElementIsDisplayed(Btn_PlaceOrder, 10)){
		String firstNameGift = dataTable2.getValueOnOtherModule("deliveryPopulation", "firstName", 0);
        String lastnameGift = dataTable2.getValueOnOtherModule("deliveryPopulation", "lastname", 0);
        String emailGift = dataTable2.getValueOnOtherModule("deliveryPopulation", "email", 0);
        String streetNameG = dataTable2.getValueOnOtherModule("deliveryPopulation", "streetName", 0);
        String provinceGift = dataTable2.getValueOnOtherModule("deliveryPopulation", "province", 0);
        String cityGift = dataTable2.getValueOnOtherModule("deliveryPopulation", "city", 0);
        String postalcodeGift= dataTable2.getValueOnOtherModule("deliveryPopulation", "postalCode", 0);
        String phonenumberGift = dataTable2.getValueOnOtherModule("deliveryPopulation", "telephone", 0);
        String suburdGift= dataTable2.getValueOnOtherModule("deliveryPopulation", "Suburb", 0);
        String vatnumberGift = dataTable2.getValueOnOtherModule("deliveryPopulation", "vatNumber", 0);
		action.explicitWait(14000);
		action.explicitWait(8000);
		//String Paytype = input.get("Paytype_Option").get(rowNumber);
		String Paytype = dataTable2.getValueOnOtherModule("CheckoutpaymentOption", "Paytype_Option", 0);
		action.CheckEnabilityofButton(Btn_PlaceOrder, "Place Order", false, test);
		WebElement paymenttype = ic_SelectPaymentMethod(Paytype);
		action.waitExplicit(10);
		action.clickEle(paymenttype, "Select Payment option " + Paytype, test);
		action.explicitWait(5000);
		 action.writeText(emaiL, emailGift,"Email", test);
		action.writeText(firstnamE, firstNameGift,"First name", test);
	    action.writeText(lastname, lastnameGift, "Last name", test);
	    action.writeText(streetnamE, streetNameG, "Street name", test);
	    action.writeText(province, provinceGift,"Province", test);
	    action.clear(city, "");
	    action.writeText(city, cityGift,"City", test);
	    action.clear(postalCode, "");
	    action.writeText(postalCode, postalcodeGift,"Postal code", test);
	    action.writeText(telephone, phonenumberGift,"Phone number", test);
	    action.clear(Suburb, "");
	    action.writeText(Suburb, suburdGift,"Suburb", test);
	    action.writeText(vatNumber, vatnumberGift,"Vat number", test);
	    action.explicitWait(14000);
	    
	    ICDelivery.Streetname = dataTable2.getValueOnOtherModule("deliveryPopulation", "streetName", 0);
		ICDelivery.Cityname = dataTable2.getValueOnOtherModule("deliveryPopulation", "city", 0);
		ICDelivery.Postalcode = dataTable2.getValueOnOtherModule("deliveryPopulation", "postalCode", 0);
	    
		action.clickEle(Btn_PlaceOrder, "Click on Place order Button ", test);

	} else{
		throw new Exception("Couldn't continue to PayU page Error gift card delivery population");
	}
}

}
	
