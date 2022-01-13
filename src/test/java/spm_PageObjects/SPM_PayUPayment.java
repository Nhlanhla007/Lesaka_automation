package spm_PageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.ConfigFileReader;
import utils.DataTable2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SPM_PayUPayment {
    WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    public static int timeOutInSeconds;

    public SPM_PayUPayment(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
    }

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

    @FindBy(xpath = "//*[@class='checkout-success']/p//span")
    WebElement OrderID;

    @FindBy(xpath = "//*[@class='checkout-success']/p//span")
    WebElement OderID;
    public static String Oderid;

    @FindBy(xpath = "//button[@id='validLink']")
    WebElement continueBtn;

    @FindBy(xpath = "//div[contains(text(),'Thank you, we have successfully received your orde')]")
    WebElement successMsg;
    
    //Paygate
    @FindBy(xpath = "//div[@id='testAlert']//div[@class='modal-content alert-danger']")
    WebElement popUp_modal;
    @FindBy(xpath = "//button[normalize-space()='OK']")
    WebElement okay_modal;
    @FindBy(xpath = "//input[@id='ccName']")
    WebElement cardHolder_payGate;
    @FindBy(xpath = "//input[@id='ccNumber']")
    WebElement cardNumber_payGate;
    @FindBy(xpath = "//select[@id='ccOpMonth']")
    WebElement cardMonth_payGate;
    @FindBy(xpath = "//select[@id='ccOpYear']")
    WebElement cardYear_payGate;
    @FindBy(xpath = "//input[@id='ccCvv']")
    WebElement cardCVV_payGate;
    @FindBy(xpath = "//button[@id='nextBtn']")
    WebElement nextBtn_payGate;
    
    @FindBy(xpath = "//input[@id='zapperName']")
    WebElement zapperName_payGate;
    
    @FindBy(xpath = "//input[@id='snapScanName']")
    WebElement SnapScanName_payGate;
    
    @FindBy(xpath = "//strong[normalize-space()='Processing, please wait.']")
    WebElement scanCode_payGate;
    

    public static String Orderid;

    public void PayUPagePayment(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber)
            throws Exception {
        timeOutInSeconds = Integer.parseInt(dataTable2.getValueOnOtherModule("SPM_PayUPagePayment", "TimeOutInSecond", 0));
        String cardnumber = dataTable2.getValueOnCurrentModule("cardnumber");
        String cardholdername = dataTable2.getValueOnCurrentModule("cardholdername");
        String Expiremonth = dataTable2.getValueOnCurrentModule("Expiremonth");
        String ExpireYear = dataTable2.getValueOnCurrentModule("ExpireYear");
        String cvv = dataTable2.getValueOnCurrentModule("cvv");

        if (action.waitUntilElementIsDisplayed(PayU_Card,timeOutInSeconds)) {
            action.clickEle(PayU_Card, " Card option in PayU", test);
            action.writeText(cardNumber, cardnumber, "card number", test);
            action.writeText(nameOnCard, cardholdername, "name on card", test);
            action.dropDownselectbyvisibletext(expMonth, Expiremonth, "Select Expirey Month on Card", test);
            action.dropDownselectbyvisibletext(expYear, ExpireYear, "Select Expirey Year on Card", test);
            action.writeText(cvvNumber, cvv, "cvv number", test);
            action.clickEle(PayBtn, "Payment submission button", test);
            action.waitForJStoLoad(timeOutInSeconds);
            try {
                if (action.isElementPresent(continueBtn)) {
                    action.javaScriptClick(continueBtn, "Continue", test);
                    action.waitForJStoLoad(timeOutInSeconds);
                }
                action.CompareResult("order success message", "Thank you, we have successfully received your order", successMsg.getText(), test);
            } catch (Exception e) {
                throw new Exception("Unable to navigate to final order page. " + e.getMessage());
            }
        } else {
            throw new Exception("Unable to Navigate to PayU page");
        }
    }
    
    public void PayFlexPayment(ExtentTest test) throws Exception{
    	action.clickEle(PayU_Card, " Card option in PayU", test);
    }
    
    public void PayGatePayment(ExtentTest test) throws Exception{
    	String tpayment = dataTable2.getValueOnOtherModule("SPM_CheckoutpaymentOption", "PayGate_Dropdown", 0);
    	String cardnumber = dataTable2.getValueOnOtherModule("SPM_PayUPagePayment","cardnumber", 0);
        String cardholdername = dataTable2.getValueOnOtherModule("SPM_PayUPagePayment","cardholdername", 0);
        String Expiremonth = dataTable2.getValueOnOtherModule("SPM_PayUPagePayment","Expiremonth", 0);
        String ExpireYear = dataTable2.getValueOnOtherModule("SPM_PayUPagePayment","ExpireYear", 0);
        String cvv = dataTable2.getValueOnOtherModule("SPM_PayUPagePayment","cvv", 0);
    	if(tpayment.equalsIgnoreCase("Card")) {
    		if (action.waitUntilElementIsDisplayed(popUp_modal,timeOutInSeconds)) {
    			action.click(okay_modal, "Close the Pop-up Modal", test);
    			
                action.writeText(cardNumber_payGate, cardnumber, "card number", test);
                action.writeText(cardHolder_payGate, cardholdername, "name on card", test);
                action.selectFromDropDownUsingVisibleText(cardMonth_payGate, Expiremonth, "Select Expirey Month on Card");
                action.selectFromDropDownUsingVisibleText(cardYear_payGate, ExpireYear, "Select Expirey Year on Card");
                action.writeText(cardCVV_payGate, cvv, "cvv number", test);
                action.clickEle(nextBtn_payGate, "Payment submission button", test);
                action.waitForJStoLoad(timeOutInSeconds);
              /*  try {
                    if (action.isElementPresent(continueBtn)) {
                        action.javaScriptClick(continueBtn, "Continue", test);
                        action.waitForJStoLoad(timeOutInSeconds);
                    }
                    action.CompareResult("order success message", "Thank you, we have successfully received your order", successMsg.getText(), test);
                } catch (Exception e) {
                    throw new Exception("Unable to navigate to final order page. " + e.getMessage());
                }
            } else {
                throw new Exception("Unable to Navigate to PayU page");
            }*/
        }
    	}
 	
    	if(tpayment.equalsIgnoreCase("Zapper")) {
    		if (action.waitUntilElementIsDisplayed(popUp_modal,timeOutInSeconds)) {
    			action.click(okay_modal, "Close the Pop-up Modal", test);
    			action.writeText(zapperName_payGate, cardholdername, "First & Last Name", test);
    			action.clickEle(nextBtn_payGate, "Payment submission button", test);
    			action.waitForJStoLoad(timeOutInSeconds);
    			action.CompareResult("Zapper Scan Code", "Processing, please wait.", scanCode_payGate.getText(), test);
    			action.waitForJStoLoad(timeOutInSeconds);
    		}
    		
    	}
    	if(tpayment.equalsIgnoreCase("SnapScan")) {
    		if (action.waitUntilElementIsDisplayed(popUp_modal,timeOutInSeconds)) {
    			action.click(okay_modal, "Close the Pop-up Modal", test);
    			action.writeText(SnapScanName_payGate, cardholdername, "First & Last Name", test);
    			action.clickEle(nextBtn_payGate, "Payment submission button", test);
    			action.waitForJStoLoad(timeOutInSeconds);
    			action.CompareResult("SnapScan Code", "Processing, please wait.", scanCode_payGate.getText(), test);
    			action.waitForJStoLoad(timeOutInSeconds);
    		}
    	}
    }
    
    
}
