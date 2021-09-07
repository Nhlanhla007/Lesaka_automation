package ic_PageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.DataTable2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ICDelivery {
    WebDriver driver;
    Action action;
    DataTable2 dataSheets;

    ICUpdateCustomer customerAddressDetails;
    public static int timeOutInSeconds;

    public ICDelivery(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        dataSheets = dataTable2;
        customerAddressDetails = new ICUpdateCustomer(driver, dataTable2);
    }

    @FindBy(xpath = "//*[@id=\"checkout-shipping-method-load\"]/table/tbody/tr[1]")
    WebElement deliveryLink;

    @FindBy(id = "customer-email")
    WebElement email;

    @FindBy(name = "street[0]")
    WebElement streetName;

    @FindBy(name = "firstname")
    WebElement firstName;

    @FindBy(name = "lastname")
    WebElement lastname;

    @FindBy(name = "telephone")
    WebElement telephoneNumber;

    @FindBy(name = "region_id")
    WebElement province;

    @FindBy(name = "city")
    WebElement city;

    @FindBy(name = "custom_attributes[suburb]")
    WebElement Suburb;

    @FindBy(name = "postcode")
    WebElement postalCode;

    @FindBy(name = "vat_id")
    WebElement vatNumber;

    @FindBy(name = "custom_attributes[identity_number]")
    WebElement idNumber;

    @FindBy(xpath = "//*[@id=\"opc-sidebar\"]/div[1]/div[1]/button")
    WebElement ContinueToPayment;

    @FindBy(name = "street[0]")
    WebElement streetNameGift;

    @FindBy(name = "telephone")
    WebElement telephoneGift;

    @FindBy(name = "region_id")
    WebElement provinceGift;

    @FindBy(name = "city")
    WebElement cityGift;

    @FindBy(name = "custom_attributes[suburb]")
    WebElement SuburbGift;

    @FindBy(name = "postcode")
    WebElement postalCodeGift;

    @FindBy(xpath = "//*[@id=\"checkout-shipping-method-load\"]/table/tbody/tr[1]/td[4]/button")
    WebElement cardDeliver_btn;

    @FindBy(xpath = "//*[@id=\"opc-sidebar\"]/div[1]/div[1]/button/span")
    WebElement placeOrder;

    @FindBy(className = "form-checkout-title")
    WebElement ic_AddressType;

    @FindBy(name = "street[0]")
    WebElement popUpStreetName;

    @FindBy(name = "region_id")
    WebElement popUpProvince;

    @FindBy(name = "city")
    WebElement popUpCity;

    @FindBy(name = "postcode")
    WebElement popUpPostalCode;

    @FindBy(name = "custom_attributes[suburb]")
    WebElement popUpsuburb;

    @FindBy(name = "vat_id")
    WebElement popUpVatNumber;

    @FindBy(name = "telephone")
    WebElement popUpPhone;

    @FindBy(xpath = "//span[contains(text(),'New Address')]")
    WebElement newAddressButton;

    // @FindBy(xpath = "//div[6]/aside[2]/div[2]/footer/button[1]")
    @FindBy(xpath = "//*[@class=\"modal-footer\"]/button[1]")
    WebElement popUpSave;

    public static String Streetname;
    public static String Cityname;
    public static String Postalcode;

    public static Map<String, String> registeredUserDetails;

    public void deliveryPopulation(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws Exception {
        timeOutInSeconds = Integer.parseInt(dataSheets.getValueOnOtherModule("deliveryPopulation","TimeOutInSecond",0));
        Streetname = input.get("streetName").get(rowNumber);
        Cityname = input.get("city").get(rowNumber);
        Postalcode = input.get("postalCode").get(rowNumber);
        String telephone = dataSheets.getValueOnCurrentModule("telephone");
        String addressType = dataSheets.getValueOnCurrentModule("AddressType");
        String userType = dataSheets.getValueOnCurrentModule("UserType");
        action.waitForJStoLoad(timeOutInSeconds);
        action.ajaxWait(timeOutInSeconds, test);
        if (action.waitUntilElementIsDisplayed(deliveryLink, timeOutInSeconds)) {
//        	action.ajaxWait(20, test);
            action.explicitWait(5000);
            action.scrollToElement(deliveryLink, "Delivery Link", test);
            action.javaScriptClick(deliveryLink, "Delivery Link", test);
            action.ajaxWait(timeOutInSeconds, test);
        }
        String addressTypeICFont = action.getText(ic_AddressType, "Get Address Type", test);//ic_AddressType.getText();
        action.explicitWait(timeOutInSeconds);
        if (addressType.equalsIgnoreCase("New") & addressTypeICFont.equalsIgnoreCase("Enter your delivery address & contact details:")) {
            if (userType.equalsIgnoreCase("Guest")) {
                action.writeText(firstName, dataSheets.getValueOnCurrentModule("firstName"), "firstName", test);
                action.writeText(lastname, dataSheets.getValueOnCurrentModule("lastname"), "lastname", test);
                action.writeText(email, dataSheets.getValueOnCurrentModule("email"), "email", test);
                // action.writeText(idNumber,dataSheets.getValueOnCurrentModule("idNumber"),"idNumber",test);
            } else if (userType.equalsIgnoreCase("Registered")) {
                customerAddressDetails.navigateBackToCustomerDetails(userType, addressTypeICFont, test);
                registeredUserDetails = customerAddressDetails.getExistingAddressInformation(userType, addressTypeICFont, test);
                dataSheets.setValueOnCurrentModule("firstName", registeredUserDetails.get("firstName"));
                dataSheets.setValueOnCurrentModule("lastname", registeredUserDetails.get("Last name"));
                dataSheets.setValueOnCurrentModule("email", registeredUserDetails.get("email"));
                dataSheets.setValueOnCurrentModule("idNumber", registeredUserDetails.get("ID"));
                /*
                 * registeredUserDetails.put("Street Address", value);
                 * registeredUserDetails.put("City", value);
                 * registeredUserDetails.put("Province", value);
                 * registeredUserDetails.put("Suburb", value);
                 * registeredUserDetails.put("Post Code", value);
                 * registeredUserDetails.put("Telephone", value);
                 */

                //action.writeText(firstName,dataSheets.getValueOnCurrentModule("firstName"),"firstName",test);
                //action.writeText(lastname,dataSheets.getValueOnCurrentModule("lastname"),"lastname",test);
                //action.writeText(email,dataSheets.getValueOnCurrentModule("email"),"email",test);
                //action.writeText(idNumber,dataSheets.getValueOnCurrentModule("idNumber"),"idNumber",test);

            }
            if (action.waitUntilElementIsDisplayed(ic_AddressType, timeOutInSeconds)) {
//        	action.explicitWait(5000);
                action.writeText(streetName, dataSheets.getValueOnCurrentModule("streetName"), "streetName", test);
                action.writeText(telephoneNumber, dataSheets.getValueOnCurrentModule("telephone"), "telephone", test);
                action.writeText(city, dataSheets.getValueOnCurrentModule("city"), "city", test);
                action.writeText(Suburb, dataSheets.getValueOnCurrentModule("Suburb"), "Suburb", test);
                action.writeText(postalCode, dataSheets.getValueOnCurrentModule("postalCode"), "postalCode", test);
                action.writeText(vatNumber, dataSheets.getValueOnCurrentModule("vatNumber"), "vatNumber", test);
                action.dropDownselectbyvisibletext(province, dataSheets.getValueOnCurrentModule("province"), "province", test);
                action.ajaxWait(timeOutInSeconds, test);
//        action.explicitWait(10000);
            }
        } else if (addressType.equalsIgnoreCase("Existing") & addressTypeICFont.equalsIgnoreCase("Select a saved address or add a new address:")) {
            customerAddressDetails.navigateBackToCustomerDetails(userType, addressTypeICFont, test);
            registeredUserDetails = customerAddressDetails.getExistingAddressInformation(userType, addressTypeICFont, test);

            Streetname = registeredUserDetails.get("Street Address");
            Cityname = registeredUserDetails.get("City");
            Postalcode = registeredUserDetails.get("Post Code");

            dataSheets.setValueOnCurrentModule("streetName", registeredUserDetails.get("Street Address"));
            dataSheets.setValueOnCurrentModule("firstName", registeredUserDetails.get("firstName"));
            dataSheets.setValueOnCurrentModule("lastname", registeredUserDetails.get("Last name"));
            dataSheets.setValueOnCurrentModule("telephone", registeredUserDetails.get("Telephone"));
            dataSheets.setValueOnCurrentModule("province", registeredUserDetails.get("Province"));
            dataSheets.setValueOnCurrentModule("city", registeredUserDetails.get("City"));
            dataSheets.setValueOnCurrentModule("Suburb", registeredUserDetails.get("Suburb"));
            dataSheets.setValueOnCurrentModule("postalCode", registeredUserDetails.get("Post Code"));
            dataSheets.setValueOnCurrentModule("vatNumber", registeredUserDetails.get("Vat number"));
            dataSheets.setValueOnCurrentModule("email", registeredUserDetails.get("email"));
            //	dataSheets.setValueOnCurrentModule("idNumber", registeredUserDetails.get("ID"));
//        	action.explicitWait(12000);


        } else if (addressType.equalsIgnoreCase("New") & addressTypeICFont.equalsIgnoreCase("Select a saved address or add a new address:")) {
            //Enters a new address with an existing address
            enterNewAddressWithAnExistingAddress(test);
        }
        if (action.waitUntilElementIsDisplayed(ContinueToPayment, timeOutInSeconds)) {
            action.waitForJStoLoad(timeOutInSeconds);
            action.ajaxWait(timeOutInSeconds, test);
            action.explicitWait(5000);
            action.javaScriptClick(ContinueToPayment, "Continue To Payment", test);

        }
    }

    public void enterNewAddressWithAnExistingAddress(ExtentTest test) throws Exception {
        customerAddressDetails.navigateBackToCustomerDetails("New", "Select a saved address or add a new address:", test);
        registeredUserDetails = customerAddressDetails.getExistingAddressInformation("New", "Select a saved address or add a new address:", test);
        dataSheets.setValueOnCurrentModule("lastname", registeredUserDetails.get("Last name"));
        dataSheets.setValueOnCurrentModule("firstName", registeredUserDetails.get("firstName"));
        dataSheets.setValueOnCurrentModule("email", registeredUserDetails.get("email"));
        dataSheets.setValueOnCurrentModule("idNumber", registeredUserDetails.get("ID"));
        if (action.waitUntilElementIsDisplayed(newAddressButton, timeOutInSeconds)) {
            action.explicitWait(4000);
            action.javaScriptClick(newAddressButton, "New Address", test);
        }
        //action.writeText(popUpFirstName, dataSheets.getValueOnCurrentModule(""), "New First name", test);
        action.writeText(popUpStreetName, dataSheets.getValueOnCurrentModule("streetName"), "New Address Street name", test);
//    	action.explicitWait(4000);
        popUpCity.clear();
        action.writeText(popUpCity, dataSheets.getValueOnCurrentModule("city"), "New Address City", test);
        action.writeText(popUpPhone, dataSheets.getValueOnCurrentModule("telephone"), "New Address Telephone", test);
        popUpsuburb.clear();
        action.writeText(popUpsuburb, dataSheets.getValueOnCurrentModule("Suburb"), "New Address Suburb", test);
        popUpPostalCode.clear();
        action.writeText(popUpPostalCode, dataSheets.getValueOnCurrentModule("postalCode"), "New Address postal code", test);
        action.writeText(popUpVatNumber, dataSheets.getValueOnCurrentModule("vatNumber"), "New Address Vat number", test);
        //popUpProvince.clear();
        action.selectFromDropDownUsingVisibleText(popUpProvince, dataSheets.getValueOnCurrentModule("province"), "New Address Province");
//    	action.explicitWait(4000);
        //popUpSave.click();
        action.click(popUpSave, "Save", test);
        action.ajaxWait(timeOutInSeconds, test);
    }

    public void deliveryPopulationGiftCard(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws Exception {
        timeOutInSeconds = Integer.parseInt(dataSheets.getValueOnOtherModule("deliveryPopulation","TimeOutInSecond",0));
        String cityName = dataSheets.getValueOnOtherModule("deliveryPopulation", "city", 0);
        String suburb = dataSheets.getValueOnOtherModule("deliveryPopulation", "Suburb", 0);
        String postalCode = dataSheets.getValueOnOtherModule("deliveryPopulation", "postalCode", 0);
        String firstNameGift = dataSheets.getValueOnOtherModule("deliveryPopulation", "firstName", 0);
        String lastnameGift = dataSheets.getValueOnOtherModule("deliveryPopulation", "lastname", 0);
        String emailGift = dataSheets.getValueOnOtherModule("deliveryPopulation", "email", 0);
        String streetNameG = dataSheets.getValueOnOtherModule("deliveryPopulation", "streetName", 0);
        String telephone = dataSheets.getValueOnOtherModule("deliveryPopulation", "telephone", 0);
        String province = dataSheets.getValueOnOtherModule("deliveryPopulation", "province", 0);

//        driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
        if (action.waitUntilElementIsDisplayed(cardDeliver_btn, timeOutInSeconds)) {
            action.ajaxWait(timeOutInSeconds, test);
            action.explicitWait(5000);
            action.scrollToElement(cardDeliver_btn, "Delivery Link", test);
            action.javaScriptClick(cardDeliver_btn, "DeliveryLink", test);
            action.ajaxWait(timeOutInSeconds, test);
            action.explicitWait(10000);
        }

        action.writeText(firstName, firstNameGift, "First name", test);
        action.writeText(lastname, lastnameGift, "Last name", test);
        action.writeText(email, emailGift, "Email", test);
        action.writeText(streetNameGift, streetNameG, "Street name", test);
        action.writeText(telephoneGift, telephone, "Telephone", test);
        action.writeText(provinceGift, province, "Province", test);
        action.writeText(cityGift, cityName, "City", test);
        action.writeText(SuburbGift, suburb, "Suburb", test);
        action.writeText(postalCodeGift, postalCode, "Postal Code", test);
        action.explicitWait(2000);
        action.click(placeOrder, "Place Order", test);
        action.ajaxWait(timeOutInSeconds, test);

    }
}
