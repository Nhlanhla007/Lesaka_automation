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
			action.waitExplicit(10);
			action.clickEle(paymenttype, "Select Payment option " + Paytype, test);
			action.explicitWait(10000);
			action.clickEle(Btn_PlaceOrder, "Click on Place order Button ", test);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
