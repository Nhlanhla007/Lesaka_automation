package ic_MagentoPageObjects;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import utils.Action;
import utils.ConfigFileReader;
import utils.DataTable2;

public class ic_MagentoOrderSAPnumber {
    WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    
    public ic_MagentoOrderSAPnumber(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;

    }
    
    @FindBy(xpath = "//div[contains(text(),'[RabbitMQ] Order SAP Number: ')][1]")
    private WebElement OrderDetailSAPNumber;

    @FindBy(xpath = "//span[@id='order_status']")
    private WebElement magentoOrderStatus;

    @FindBy(xpath = "//ul[@class='note-list']//div[contains(text(),'successfully activated.')]")
    private WebElement OpenGateActivationSuccess;

    @FindBy(xpath = "//ul[@class='note-list']//div[contains(text(),'Failed activate') ]")
    private WebElement OpenGateActivationFailure;

    @FindBy(xpath = "//span[contains(text(),'Items Ordered')]")
    private WebElement itemsOrdered;

    @FindBy(xpath = "//a[contains(text(),'Download SABC ID Book')]")
    private WebElement downloadSABCButton;

    @FindBy(xpath = "//div[contains(text(),'was downloaded')]")
    private WebElement downloadSuccessMsg;
    
    @FindBy(xpath = "//*[contains(text(),'Submit Comment')]")
    private WebElement submitComment;
    
    @FindBy(xpath = "//*[contains(text(),'Re-Validate')]/parent::button")
    private WebElement reValidate;
    
    @FindBy(xpath = "//*[contains(text(),'Use with discretion')]")
    private WebElement reValidatePopUp;
    
    @FindBy(xpath = "//*[@class='action-primary action-accept']")
    private WebElement confirmReValidate;
    
    Timer t = new Timer();
    public static String OrderSAPnumber;
    
    public void GenerateOrderSAPnumber(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws Exception {
        boolean flagres = false;
        boolean openGateFlag = false;
        int totalConunter = 0;
        String OrderSAPnumber = "";
        String OpenGateActivationMessage = "";
        long startTime = System.currentTimeMillis();
        int TimeOutinSecond = Integer.parseInt(input.get("TimeOutinSecond").get(rowNumber));
        int trycount = Integer.parseInt(input.get("totalCounter").get(rowNumber));
        int elapsedTime = 0;
        action.explicitWait(10000);

        String orderStatus = dataTable2.getValueOnOtherModule("OrderStatusSearch", "orderStatus", 0);
        String re_validate =  dataTable2.getValueOnCurrentModule("re_validate");
        System.out.println("Sales Order Status :" + orderStatus);
        action.CompareResult("Sales Order Status", orderStatus, magentoOrderStatus.getText(), test);
        action.scrollElemetnToCenterOfView(itemsOrdered,"Items Ordered",test);
        action.explicitWait(2000);
        try {
            String esdProduct = dataTable2.getValueOnOtherModule("ProductSearch", "ESD Product", 0);
            if (esdProduct.equalsIgnoreCase("yes")) {
                boolean check = driver.findElements(By.xpath("//ul[@class='note-list']//div[contains(text(),'successfully activated.')]")).size() > 0;
                if (check) {
                    OpenGateActivationMessage = OpenGateActivationSuccess.getText();
                    action.scrollElemetnToCenterOfView(OpenGateActivationSuccess, "OpenGate Activation", test);
                    openGateFlag = true;
                }
                else{
                    OpenGateActivationMessage = OpenGateActivationFailure.getText();
                    action.scrollElemetnToCenterOfView(OpenGateActivationFailure, "OpenGate Activation", test);
                }
                action.CompareResult("OpenGate Activation: " + OpenGateActivationMessage, String.valueOf(true), String.valueOf(openGateFlag), test);
            }
        } catch (Exception e) {
           // e.printStackTrace();
        }

        
        if(magentoOrderStatus.getText().contains("Pending SABC Validation") && re_validate.equalsIgnoreCase("yes")) {
        	action.click(reValidate, "Re Validate", test);
        	action.elementExistWelcome(reValidatePopUp, 20, "Re Validate pop up", test);
        	action.explicitWait(2000);
        	action.click(confirmReValidate, "Ok", test);
        }
        while (elapsedTime <= TimeOutinSecond && flagres == false) {
            action.refresh();
            action.waitForPageLoaded(TimeOutinSecond);
			
            try {
                if (action.waitUntilElementIsDisplayed(OrderDetailSAPNumber, 2)) {
                    action.scrollToElement(OrderDetailSAPNumber, "OrderDetailSAPNumber");
                    OrderSAPnumber = OrderDetailSAPNumber.getText();
                    if (OrderSAPnumber.isEmpty()) {
                        action.explicitWait(TimeOutinSecond);
                        action.refresh();
                        System.out.println("Not found on count:" + totalConunter);
					
                    } else {
                        flagres = true;
                        System.out.println("SAP Order Number: " + OrderSAPnumber);
                        input.get("OrderSAPnumber").set(rowNumber, OrderSAPnumber.replace("[RabbitMQ] Order SAP Number: ", ""));
                    }
                }

            } catch (Exception e) {
                if (trycount == totalConunter) {
                    throw new Exception("Unable to obtain SAP Order Number " + e.getMessage());
                }
            }

            long endTime = System.currentTimeMillis();
            long elapsedTimeInMils = endTime - startTime;
            elapsedTime = ((int) elapsedTimeInMils) / 1000;
            System.out.println("ElapsedTime: " + elapsedTime);
            totalConunter++;
        }

            if (flagres) {
                action.CompareResult("SAP order Number generated: " + OrderSAPnumber + "  ", String.valueOf(true), String.valueOf(flagres), test);
            } else {
                JavascriptExecutor exe = (JavascriptExecutor) driver;
                exe.executeScript("window.scrollTo(0, document.body.scrollHeight)");
                action.scrollElementIntoView(submitComment);
                //exe.executeScript("window.scrollBy(0,-500)");
                action.CompareResult("SAP order Number generated: " + OrderSAPnumber + "  ", String.valueOf(true), String.valueOf(flagres), test);
                throw new Exception("SAP Order Number Is Not Generated");
        }
    }

    public void downloadSABC_ID(ExtentTest test) throws Exception {

        try {
            boolean sabcFlag = action.isElementPresent(downloadSABCButton);
            if (sabcFlag) {
                action.click(downloadSABCButton, "SABC Download ID Book", test);
                action.waitForJStoLoad(30);
                boolean msgFlag = action.isElementPresent(downloadSuccessMsg);
                action.CompareResult("SABC ID Download message", String.valueOf(true), String.valueOf(msgFlag), test);
            }
        } catch (Exception e) {
            throw new Exception("Unable to Download SABC ID Book " + e.getMessage());
        }
    }
}

