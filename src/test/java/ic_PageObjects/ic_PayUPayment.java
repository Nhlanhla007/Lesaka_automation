package ic_PageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.DataTable2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ic_PayUPayment {
    WebDriver driver;
    Action action;
    DataTable2 dataTable2;

    public ic_PayUPayment(WebDriver driver, DataTable2 dataTable2) {
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
    WebElement OderID;
    public static String Oderid;

    @FindBy(xpath = "//button[@id='validLink']")
    WebElement continueBtn;

    @FindBy(xpath = "//div[contains(text(),'Thank you, we have successfully received your orde')]")
    WebElement successMsg;


    public void PayUPagePayment(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws Exception {
        String cardnumber = dataTable2.getValueOnCurrentModule("cardnumber");
        String cardholdername = dataTable2.getValueOnCurrentModule("cardholdername");
        String Expiremonth = dataTable2.getValueOnCurrentModule("Expiremonth");
        String ExpireYear = dataTable2.getValueOnCurrentModule("ExpireYear");
        String cvv = dataTable2.getValueOnCurrentModule("cvv");
        try {
            action.waitUntilElementIsDisplayed(PayU_Card, 20);
            action.clickEle(PayU_Card, " Card option in PayU", test);
            action.writeText(cardNumber, cardnumber, "card number", test);
            action.writeText(nameOnCard, cardholdername, "name on card", test);
            action.dropDownselectbyvisibletext(expMonth, Expiremonth, "Select Expirey Month on Card", test);
            action.dropDownselectbyvisibletext(expYear, ExpireYear, "Select Expirey Month on Card", test);
            action.writeText(cvvNumber, cvv, "cvv number", test);
            action.clickEle(PayBtn, "Payment submission button", test);
            action.waitForJStoLoad(10);
            if (action.isElementPresent(continueBtn)) {
                action.javaScriptClick(continueBtn, "Continue", test);
                action.waitForJStoLoad(40);
            }
            action.CompareResult("order success message", "Thank you, we have successfully received your order", successMsg.getText(), test);
        } catch (Exception e) {
            throw new Exception("Unable to navigate to final order page. " + e.getMessage());
        }
    }
}
