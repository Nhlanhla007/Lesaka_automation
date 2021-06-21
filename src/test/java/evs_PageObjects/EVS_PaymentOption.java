package evs_PageObjects;

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
import utils.ConfigFileReader;
import utils.DataTable2;

public class EVS_PaymentOption {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;

	public EVS_PaymentOption(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		this.dataTable2 = dataTable2;
		PageFactory.initElements(driver, this);
		action = new Action(driver);

	}

	// place order button
	@FindBy(xpath = "//*[@id='opc-sidebar']/div[1]/div[1]/button")
	WebElement Btn_PlaceOrder;

	// -----------------Various Payment Options-----------------

	@FindBy(xpath = "//span[contains(text(),'FASTA Instant Credit')]")
	WebElement FASTA_Instant_Credit;

	@FindBy(xpath = "//span[contains(text(),'PayGate')]")
	WebElement PayGate;

	@FindBy(xpath = "//span[contains(text(),'RCS (via PayU)')]")
	WebElement RCS;

	@FindBy(xpath = "//span[contains(text(),'Visa Checkout')]")
	WebElement Visa_Checkout;

	@FindBy(xpath = "//span[contains(text(),'Masterpass')]")
	WebElement Masterpass;

	@FindBy(xpath = "//span[contains(text(),'EFT Pro (Processed by PayU)')]")
	WebElement EFT_Pro;

	@FindBy(xpath = "//span[contains(text(),'Bank Transfer Payment')]")
	WebElement Bank_Transfer;

	@FindBy(xpath = "//span[contains(text(),'Discovery Miles')]")
	WebElement Discovery_Miles;

	@FindBy(xpath = "//span[contains(text(),'Credit Card (Processed by PayU)')]")
	WebElement Credit_Card;

	@FindBy(xpath = "(//span[contains(text(),'PayFlex')])[1]")
	WebElement PayFlex;

	
	//-------------------------------------------------------------------------------------------------------------------
		//gift card checkout
		
		@FindBy(xpath = "//*[@id=\"customer-email\"]")
		WebElement emaiL;

		/*@FindBy(name = "firstname")
		@FindBy(xpath = "/html/body/div[2]/main/div[2]/div/div/div[4]/ol/li[3]/div/form/fieldset/div[1]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[4]/div/input")*/
		@FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[9]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[4]/div//input")
	    WebElement firstnamE;
		
		/*@FindBy(xpath = "//input[@name="lastname"]")
		 * //*[@id="checkout-payment-method-load"]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[4]/div//input*/
		@FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[9]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[5]/div//input")
	    WebElement lastname;
		
		/*@FindBy(xpath = "//input[@name=\"telephone\"]")
		 * @FindBy(xpath = "/html/body/div[2]/main/div[2]/div/div/div[4]/ol/li[3]/div/form/fieldset/div[1]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[13]/div/input")*/
		@FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[9]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[14]/div//input")
	    WebElement telephone;
		
		/*@FindBy(xpath = "//input[@name=\"custom_attributes[suburb]\"]")
		 * @FindBy(xpath = "/html/body/div[2]/main/div[2]/div/div/div[4]/ol/li[3]/div/form/fieldset/div[1]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[8]/div/input")*/
		@FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[9]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[13]/div//input")
	    WebElement Suburb;
		
		//@FindBy(xpath = "/html/body/div[2]/main/div[2]/div/div/div[4]/ol/li[3]/div/form/fieldset/div[1]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/fieldset/div/div[1]/div/input")
		@FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[9]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/fieldset/div/div[1]/div//input")
		WebElement streetnamE;
		
		/*@FindBy(xpath = "//select[@name=\"region_id\"]")
		 * @FindBy(xpath = "/html/body/div[2]/main/div[2]/div/div/div[4]/ol/li[3]/div/form/fieldset/div[1]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[9]/div/select")*/						
							//*[@id=\"checkout-payment-method-load\"]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[9]/div//select
		@FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[9]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[9]/div/select")
	    WebElement province;
		
		/*@FindBy(xpath = "//input[@name=\"city\"]")
		 * @FindBy(xpath = "/html/body/div[2]/main/div[2]/div/div/div[4]/ol/li[3]/div/form/fieldset/div[1]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[7]/div/input")*/
		@FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[9]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[7]/div//input")
	    WebElement city;
		
		/*@FindBy(xpath = "//input[@name=\"postcode\"]")
		 * @FindBy(xpath= "/html/body/div[2]/main/div[2]/div/div/div[4]/ol/li[3]/div/form/fieldset/div[1]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[11]/div/input")*/
		@FindBy(xpath= "//*[@id=\"checkout-payment-method-load\"]/div/div/div[9]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[11]/div//input")
	    WebElement postalCode;
		
		//@FindBy(xpath = "/html/body/div[2]/main/div[2]/div/div/div[4]/ol/li[3]/div/form/fieldset/div[1]/div/div/div[3]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[14]/div/input")
		@FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[9]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[15]/div//input")
		WebElement vatNumber;

	    @FindBy(name = "custom_attributes[identity_number]")
	    WebElement idNumber;
		
		//@FindBy(xpath = "//*[@id='billing-address-same-as-shipping-payumea_creditcard']")
		//WebElement Billingshipping;
		@FindBy(xpath = "//*[@id='checkout-payment-method-load']/div/div/div[3]/div[2]/div[2]/div/div/label/span")
		WebElement Billingshipping;
		@FindBy(xpath = "//span[contains(text(),'I agree to all the terms & conditions')]")
		WebElement TermsCondition;
	

	public WebElement evs_SelectPaymentMethod(String Paytype) {
		Map<String, WebElement> PaymentMap = new HashMap<String, WebElement>();
		PaymentMap.put("FASTA Instant Credit", FASTA_Instant_Credit);
		PaymentMap.put("PayGate", PayGate);
		PaymentMap.put("RCS", RCS);
		PaymentMap.put("Visa Checkout", Visa_Checkout);
		PaymentMap.put("Masterpass", Masterpass);
		PaymentMap.put("EFT Pro", EFT_Pro);
		PaymentMap.put("Bank Transfer", Bank_Transfer);
		PaymentMap.put("Discovery Miles", Discovery_Miles);
		PaymentMap.put("Credit Card", Credit_Card);
		PaymentMap.put("PayFlex", PayFlex);
		WebElement actionele = null;
		Flag: for (Map.Entry m : PaymentMap.entrySet()) {
			if (m.getKey().toString().trim().toUpperCase().equalsIgnoreCase(Paytype)) {
				System.out.println("FOUND match with excel pay type");
				actionele = (WebElement) m.getValue();
				break Flag;
			}

		}

		return actionele;
	}

	public void CheckoutpaymentOption(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber)
			throws IOException {
		try {
			System.out.println("##############START Execution###############");
			action.explicitWait(15000);
			String Paytype = input.get("Paytype_Option").get(rowNumber);
			action.CheckEnabilityofButton(Btn_PlaceOrder, "Place Order", false, test);
			WebElement paymenttype = evs_SelectPaymentMethod(Paytype);
			action.explicitWait(10000);
			action.clickEle(paymenttype, "Select Payment option " + Paytype, test);
			action.explicitWait(10000);
			action.clickEle(Btn_PlaceOrder, "Click on Place order Button ", test);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void CheckoutpaymentOptionGiftCard(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
		String firstNameGift = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "firstName", 0);
        String lastnameGift = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "lastname", 0);
        String emailGift = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "email", 0);
        String streetNameG = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "streetName", 0);
        String provinceGift = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "province", 0);
        String cityGift = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "city", 0);
        String postalcodeGift= dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "postalCode", 0);
        String phonenumberGift = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "telephone", 0);
        String suburdGift= dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "Suburb", 0);
        String vatnumberGift = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "vatNumber", 0);
		action.explicitWait(14000);
		System.out.println("##############START Execution!###############");
		action.explicitWait(8000);
		//String Paytype = input.get("Paytype_Option").get(rowNumber);
		String Paytype = dataTable2.getValueOnOtherModule("evs_CheckoutpaymentOption", "Paytype_Option", 0);
		action.CheckEnabilityofButton(Btn_PlaceOrder, "Place Order", false, test);
		WebElement paymenttype = evs_SelectPaymentMethod(Paytype);
		action.waitExplicit(10);
		action.clickEle(paymenttype, "Select Payment option " + Paytype, test);
		action.explicitWait(5000);
		action.mouseover(emaiL, "");
		 action.writeText(emaiL, emailGift,"Email", test);
		action.writeText(firstnamE, firstNameGift,"First name", test);
	    action.writeText(lastname, lastnameGift, "Last name", test);
	    action.writeText(streetnamE, streetNameG, "Street name", test);
	    action.writeText(province, provinceGift,"Province", test);
	    action.writeText(city, cityGift,"City", test);
	    action.writeText(postalCode, postalcodeGift,"Postal code", test);
	    action.writeText(telephone, phonenumberGift,"Phone number", test);
	    action.writeText(Suburb, suburdGift,"Suburb", test);
	    action.writeText(vatNumber, vatnumberGift,"Vat number", test);
	    action.explicitWait(14000);
	    
	    EVS_Delivery.Streetname = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "streetName", 0);
		EVS_Delivery.Cityname = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "city", 0);
		EVS_Delivery.Postalcode = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "postalCode", 0);
	    
		action.clickEle(Btn_PlaceOrder, "Click on Place order Button ", test);

	}

}
