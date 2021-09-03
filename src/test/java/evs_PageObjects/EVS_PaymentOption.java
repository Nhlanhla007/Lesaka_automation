package evs_PageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import utils.Action;
import utils.ConfigFileReader;
import utils.DataTable2;
import utils.JavaUtils;

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

    @FindBy(xpath = "//*[@id='opc-sidebar']/div[1]/div[1]/button")
    WebElement Btn_PlaceOrder;

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

    @FindBy(xpath = "//*[@id=\"customer-email\"]")
    WebElement emaiL;

    @FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[10]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[4]/div//input")
    WebElement firstnamE;

    @FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[10]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[5]/div//input")
    WebElement lastname;

    @FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[10]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[14]/div//input")
    WebElement telephone;

    @FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[10]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[13]/div//input")
    WebElement Suburb;

    @FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[10]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/fieldset/div/div[1]/div//input")
    WebElement streetnamE;

    @FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[10]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[9]/div/select")
    WebElement province;

    @FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[10]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[7]/div//input")
    WebElement city;

    @FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[10]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[11]/div//input")
    WebElement postalCode;

    @FindBy(xpath = "//*[@id=\"checkout-payment-method-load\"]/div/div/div[10]/div[2]/div[2]/div/fieldset/div[2]/div/form/div/div[15]/div//input")
    WebElement vatNumber;

    @FindBy(name = "custom_attributes[identity_number]")
    WebElement idNumber;

    @FindBy(xpath = "//*[@id='checkout-payment-method-load']/div/div/div[3]/div[2]/div[2]/div/div/label/span")
    WebElement Billingshipping;

    @FindBy(xpath = "//span[contains(text(),'I agree to all the terms & conditions')]")
    WebElement TermsCondition;

    @FindBy(xpath = "//input[@id='id-book-upload']")
    WebElement selectIDButton;

    @FindBy(xpath = "//span[contains(text(),'No file selected')]")
    WebElement NoFileSelection;

    @FindBy(xpath = "//span[contains(text(),\"Ready to upload\")]")
    WebElement uploadMsg;

    @FindBy(xpath = "//span[contains(text(),'Submit')]")
    WebElement IDSubmitBtn;

    @FindBy(xpath = "//span[contains(text(),'Uploading proof of ID, please wait...')]")
    WebElement uploadingMsg;


    public void uploadValidID(ExtentTest test) throws Exception {
        action.explicitWait(15000);
        action.ajaxWait(20, test);
        String uploadDocument = dataTable2.getValueOnOtherModule("tvLicenseValidation", "Upload Document", 0);

        try {
            if (uploadDocument.equalsIgnoreCase("yes")) {
                boolean uploadButton = action.isElementPresent(selectIDButton);
                action.CompareResult("Upload Button is displayed", "true", String.valueOf(uploadButton), test);

                if (uploadButton) {
                    boolean NoFileSelectionCheck = action.isElementPresent(NoFileSelection);
                    action.CompareResult("No Document is selected for Upload", "true", String.valueOf(NoFileSelectionCheck), test);
                    if (NoFileSelectionCheck) {

                        String filePath = dataTable2.getValueOnOtherModule("tvLicenseValidation", "Document Upload Location", 0);

                        selectIDButton.sendKeys(filePath);
                        action.ajaxWait(10, test);
                        boolean uploadMessage = action.waitUntilElementIsDisplayed(uploadMsg, 5);
                        action.CompareResult("Ready to upload message", "true", String.valueOf(uploadMessage), test);
                        if (uploadMessage) {
                            action.explicitWait(10000);
                            action.javaScriptClick(IDSubmitBtn, "Submit Button", test);
                            boolean uploadingMessage = action.waitUntilElementIsDisplayed(uploadingMsg, 5);
                            action.CompareResult("File uploading message", "true", String.valueOf(uploadingMessage), test);
                            action.explicitWait(10000);
                            boolean uploadCompleteFlag = driver.findElements(By.xpath("//span[contains(text(),'Uploading proof of ID, please wait...')]")).size() > 0;
                            action.CompareResult("Uploading is Completed", "false", String.valueOf(uploadCompleteFlag), test);
                        }
                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Unable to upload the Document: " + e.getMessage());
        }
    }


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
        Flag:
        for (Map.Entry m : PaymentMap.entrySet()) {
            if (m.getKey().toString().trim().toUpperCase().equalsIgnoreCase(Paytype)) {
                actionele = (WebElement) m.getValue();
                break Flag;
            }

        }

        return actionele;
    }

    public void CheckoutpaymentOption(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws Exception {
        try {
            action.explicitWait(20000);
            action.waitForJStoLoad(60);
            action.ajaxWait(20, test);
            if (action.waitUntilElementIsDisplayed(Btn_PlaceOrder,30)) {
                String paytype = input.get("Paytype_Option").get(rowNumber);
                WebElement paymentsType = evs_SelectPaymentMethod(paytype);
                action.scrollToElement(paymentsType, "Payment type");
                action.explicitWait(2000);
                action.click(paymentsType, "Select Payment option as " + paytype, test);
                action.ajaxWait(10, test);
                action.scrollToElement(Btn_PlaceOrder, "Place Order Button");
                action.explicitWait(2000);
                action.clickEle(Btn_PlaceOrder, "Click on Place order Button ", test);
                action.ajaxWait(10, test);
                action.ajaxWait(10, test);
                action.waitForPageLoaded(40);

            } else {
                throw new Exception("Unable to navigate to Checkout Payment");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void CheckoutpaymentOptionGiftCard(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws Exception {
        String firstNameGift = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "firstName", 0);
        String lastnameGift = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "lastname", 0);
        String emailGift = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "email", 0);
        String streetNameG = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "streetName", 0);
        String provinceGift = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "province", 0);
        String cityGift = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "city", 0);
        String postalcodeGift = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "postalCode", 0);
        String phonenumberGift = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "telephone", 0);
        String suburdGift = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "Suburb", 0);
        String vatnumberGift = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "vatNumber", 0);
        action.explicitWait(14000);
        String Paytype = dataTable2.getValueOnOtherModule("evs_CheckoutpaymentOption", "Paytype_Option", 0);
        action.CheckEnabilityofButton(Btn_PlaceOrder, "Place Order", false, test);
        WebElement paymenttype = evs_SelectPaymentMethod(Paytype);
        action.waitExplicit(10);
        action.clickEle(paymenttype, "Select Payment option " + Paytype, test);
        action.explicitWait(5000);
        action.mouseover(emaiL, "");
        action.writeText(emaiL, emailGift, "Email", test);
        action.writeText(firstnamE, firstNameGift, "First name", test);
        action.writeText(lastname, lastnameGift, "Last name", test);
        action.writeText(streetnamE, streetNameG, "Street name", test);
        action.writeText(province, provinceGift, "Province", test);
        action.writeText(city, cityGift, "City", test);
        action.writeText(postalCode, postalcodeGift, "Postal code", test);
        action.writeText(telephone, phonenumberGift, "Phone number", test);
        action.writeText(Suburb, suburdGift, "Suburb", test);
        action.writeText(vatNumber, vatnumberGift, "Vat number", test);
        action.explicitWait(14000);

        action.clickEle(Btn_PlaceOrder, "Click on Place order Button ", test);

    }

}
