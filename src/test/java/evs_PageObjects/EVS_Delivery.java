package evs_PageObjects;

import com.aventstack.extentreports.ExtentTest;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class EVS_Delivery {
    WebDriver driver;
    Action action;
    DataTable2 dataSheets;

    EVS_UpdateCustomer customerAddressDetails;

    public EVS_Delivery(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        dataSheets = dataTable2;
        customerAddressDetails = new EVS_UpdateCustomer(driver, dataTable2);

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
    WebElement telephone;

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

    @FindBy(xpath = "//input[@name=\"custom_attributes[nickname]\"]")
    WebElement evs_nickname;

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

    /*
     * @FindBy(name = "firstname") WebElement popUpFirstName;
     *
     * @FindBy(name = "lastname") WebElement popUpLastName;
     */

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

    @FindBy(xpath = "//span[contains(text(),'Add Address')]")
    WebElement newAddressButton;

    @FindBy(xpath = "//div[6]/aside[2]/div[2]/footer/button[1]")
    WebElement popUpSave;


    @FindBy(xpath = "//*[@class=\"pac-item\"]")
    List<WebElement> googleAddressOptions;

//    @FindBy(xpath = "//*[@id=\"checkout-shipping-method-load\"]/table/tbody/tr[1]/td[4]/button")
//    WebElement cardDeliver_btn;

//    public static String Streetname;
//    public static String Cityname;
//    public static String Postalcode;

    public static Map<String, String> registeredUserDetails;

    public void deliveryPopulation(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws Exception {
        //driver.navigate().to(ConfigFileReader.getPropertyVal("EVS_URL"));
        String Streetname = input.get("streetName").get(rowNumber);
        String Cityname = input.get("city").get(rowNumber);
        String Postalcode = input.get("postalCode").get(rowNumber);
        String addressType = dataSheets.getValueOnCurrentModule("AddressType");
        String userType = dataSheets.getValueOnCurrentModule("UserType");
        //Thread.sleep(10000);
        action.explicitWait(15000);
        if (action.waitUntilElementIsDisplayed(deliveryLink, 20000)) {
            action.javaScriptClick(deliveryLink, "deliveryLink", test);
        }
        String addressTypeICFont = action.getText(ic_AddressType, "Get Address Type", test);//ic_AddressType.getText();
        action.explicitWait(4000);
        if (addressType.equalsIgnoreCase("New") & addressTypeICFont.equalsIgnoreCase("Enter your delivery address & contact details:")) {
            if (userType.equalsIgnoreCase("Guest")) {
                action.writeText(firstName, dataSheets.getValueOnCurrentModule("firstName"), "firstName", test);
                action.writeText(lastname, dataSheets.getValueOnCurrentModule("lastname"), "lastname", test);
                action.writeText(email, dataSheets.getValueOnCurrentModule("email"), "email", test);
                // action.writeText(idNumber,dataSheets.getValueOnCurrentModule("idNumber"),"idNumber",test);
            } else if (userType.equalsIgnoreCase("Registered")) {
                customerAddressDetails.navigateBackToCustomerDetails(userType, addressTypeICFont);
                registeredUserDetails = customerAddressDetails.getExistingAddressInformation(userType, addressTypeICFont);
                dataSheets.setValueOnCurrentModule("firstName", registeredUserDetails.get("firstName"));
                dataSheets.setValueOnCurrentModule("lastname", registeredUserDetails.get("Last name"));
                dataSheets.setValueOnCurrentModule("email", registeredUserDetails.get("email"));
                //dataSheets.setValueOnCurrentModule("idNumber", registeredUserDetails.get("ID"));
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
            if (action.waitUntilElementIsDisplayed(ic_AddressType, 20000)) {
                action.explicitWait(8000);
                String searchStreetName = dataSheets.getValueOnCurrentModule("streetName").trim();
                String searchSuburb = dataSheets.getValueOnCurrentModule("Suburb").trim();
                String searchCity = dataSheets.getValueOnCurrentModule("city").trim();
                action.writeText(streetName, searchStreetName + " " + searchSuburb + " " + searchCity, "Enter Google Address", test);
                searchStreetName = searchStreetName.substring(searchStreetName.indexOf(" ")).trim();
                action.explicitWait(8000);
                boolean flag = true;
                for (WebElement option : googleAddressOptions) {
                    try {
                        String streetName = option.findElement(By.xpath(".//*[contains(text(),'" + searchStreetName + "')]")).getText();
                        boolean suburbInformation = option.findElements(By.xpath(".//*[contains(text(),'" + searchSuburb + "')]")).size() > 0;// option.findElement(By.xpath(".//span[3]")).getText();
                        boolean cityInformation = option.findElements(By.xpath(".//*[contains(text(),'" + searchCity + "')]")).size() > 0;
                        boolean suburbCityInformationStatus = suburbInformation & cityInformation;
                        if (suburbCityInformationStatus) {
                            action.CompareResult("Google Option Match Found", "true", "true", test);
                            action.click(option, "Google address option selected", test);
                            action.ajaxWait(40, test);
                            action.explicitWait(8000);
                            flag = false;
                        }
                    } catch (Exception e) {
                    }

                }

                if (flag) {
                    throw new Exception("Google Address Has Not Been Found");
                }

//        action.writeText(streetName,dataSheets.getValueOnCurrentModule("streetName"),"streetName",test);
                action.waitUntilElementIsDisplayed(telephone, 5000);
                action.writeText(telephone, dataSheets.getValueOnCurrentModule("telephone"), "telephone", test);
//        action.writeText(city,dataSheets.getValueOnCurrentModule("city"),"city",test);
//        action.writeText(Suburb,dataSheets.getValueOnCurrentModule("Suburb"),"Suburb",test);
//        action.writeText(postalCode,dataSheets.getValueOnCurrentModule("postalCode"),"postalCode",test);
                action.writeText(vatNumber, dataSheets.getValueOnCurrentModule("vatNumber"), "vatNumber", test);
                action.writeText(evs_nickname, dataSheets.getValueOnCurrentModule("nickName"), "nickName", test);
//        action.explicitWait(5000);
//        action.dropDownselectbyvisibletext(province,dataSheets.getValueOnCurrentModule("province"),"province",test);
//        action.explicitWait(10000);
            }
        } else if (addressType.equalsIgnoreCase("Existing") & addressTypeICFont.equalsIgnoreCase("Choose your address or add a new one:")) {
            customerAddressDetails.navigateBackToCustomerDetails(userType, addressTypeICFont);
            registeredUserDetails = customerAddressDetails.getExistingAddressInformation(userType, addressTypeICFont);

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
            action.explicitWait(12000);

        } else if (addressType.equalsIgnoreCase("New") & addressTypeICFont.equalsIgnoreCase("Choose your address or add a new one:")) {
            //Enters a new address with an existing address
            enterNewAddressWithAnExistingAddress(test);
        } /*
         * else if(addressType.equalsIgnoreCase("Existing") & addressTypeICFont.
         * equalsIgnoreCase("Enter your delivery address & contact details:")){ //WHAT
         * SHOULD HAPPEN HERE THERE IS NO EXISITNG ADDRESS????????????? }
         *///Add else if for other scenario
        if (action.waitUntilElementIsDisplayed(ContinueToPayment, 20000)) {
            action.explicitWait(5000);
            action.javaScriptClick(ContinueToPayment, "Continue To Payment", test);
            action.waitForPageLoaded(40);
        }
    }


    public void enterNewAddressWithAnExistingAddress(ExtentTest test) throws Exception {
        customerAddressDetails.navigateBackToCustomerDetails("New", "Select a saved address or add a new address:");
        registeredUserDetails = customerAddressDetails.getExistingAddressInformation("New", "Choose your address or add a new one:");
        dataSheets.setValueOnCurrentModule("lastname", registeredUserDetails.get("Last name"));
        dataSheets.setValueOnCurrentModule("firstName", registeredUserDetails.get("firstName"));
        dataSheets.setValueOnCurrentModule("email", registeredUserDetails.get("email"));
        dataSheets.setValueOnCurrentModule("idNumber", registeredUserDetails.get("ID"));
        if (action.waitUntilElementIsDisplayed(newAddressButton, 15000)) {
            action.explicitWait(5000);
            // newAddressButton.click();
            action.click(newAddressButton, "New Address", test);
        }
        // action.writeText(popUpFirstName, dataSheets.getValueOnCurrentModule(""), "New
        // First name", test);
        String searchStreetName = dataSheets.getValueOnCurrentModule("streetName").trim();
        String searchSuburb = dataSheets.getValueOnCurrentModule("Suburb").trim();
        String searchCity = dataSheets.getValueOnCurrentModule("city").trim();

        action.explicitWait(4000);
        action.writeText(popUpStreetName, searchStreetName + " " + searchSuburb + " " + searchCity, "New Address Street name", test);
        searchStreetName = searchStreetName.substring(searchStreetName.indexOf(" ")).trim();
        action.ajaxWait(30, test);
        action.explicitWait(12000);
        boolean flag = true;
        for (WebElement option : googleAddressOptions) {
            try {
                String googlestreetName = option.findElement(By.xpath(".//*[contains(text(),'" + searchStreetName + "')]")).getText();
                boolean suburbInformation = option.findElements(By.xpath(".//*[contains(text(),'" + searchSuburb + "')]")).size() > 0;// option.findElement(By.xpath(".//span[3]")).getText();
                boolean cityInformation = option.findElements(By.xpath(".//*[contains(text(),'" + searchCity + "')]")).size() > 0;
                boolean suburbCityInformationStatus = suburbInformation & cityInformation;
                if (suburbCityInformationStatus) {
                    action.click(option, "Google address option selected", test);
                    action.ajaxWait(30, test);
                    action.explicitWait(8000);
                    flag = false;
                }
            } catch (NoSuchElementException e) {
            }
        }

        if (flag) {
            throw new Exception("Google Address Has Not Been Found");
        }

//    	action.explicitWait(4000);
//    	popUpCity.clear();
//    	action.writeText(popUpCity, dataSheets.getValueOnCurrentModule("city"), "New Address City", test);
        action.writeText(popUpPhone, dataSheets.getValueOnCurrentModule("telephone"), "New Address Telephone", test);
//    	popUpsuburb.clear();
//    	action.writeText(popUpsuburb, dataSheets.getValueOnCurrentModule("Suburb"), "New Address Suburb", test);
//    	popUpPostalCode.clear();
//    	action.writeText(popUpPostalCode, dataSheets.getValueOnCurrentModule("postalCode"), "New Address postal code", test);
        action.writeText(popUpVatNumber, dataSheets.getValueOnCurrentModule("vatNumber"), "New Address Vat number", test);
//    	//popUpProvince.clear();
//    	action.selectFromDropDownUsingVisibleText(popUpProvince, dataSheets.getValueOnCurrentModule("province"), "New Address Province");
//    	action.explicitWait(4000);
        // popUpSave.click();
        action.click(popUpSave, "Save", test);
    }

    public void deliveryPopulationGiftCard(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws Exception {
        //Streetname =input.get("streetName").get(rowNumber);
        String Streetname = dataSheets.getValueOnOtherModule("evs_DeliveryPopulation", "streetName", 0);
        //Cityname =input.get("city").get(rowNumber);
        String Cityname = dataSheets.getValueOnOtherModule("evs_DeliveryPopulation", "city", 0);
        //Postalcode = input.get("postalCode").get(rowNumber);
        String Postalcode = dataSheets.getValueOnOtherModule("evs_DeliveryPopulation", "postalCode", 0);
        String firstNameGift = dataSheets.getValueOnOtherModule("evs_DeliveryPopulation", "firstName", 0);
        String phoneGift = dataSheets.getValueOnOtherModule("evs_DeliveryPopulation", "telephone", 0);
        String lastnameGift = dataSheets.getValueOnOtherModule("evs_DeliveryPopulation", "lastname", 0);
        String emailGift = dataSheets.getValueOnOtherModule("evs_DeliveryPopulation", "email", 0);
        String SuburbG = dataSheets.getValueOnOtherModule("evs_DeliveryPopulation", "Suburb", 0);
        String provinceG = dataSheets.getValueOnOtherModule("evs_DeliveryPopulation", "province", 0);

        driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
        //action.click(cardDeliver_btn, "click Deliver", test);
        action.waitForPageLoaded(30);
        action.ajaxWait(10, test);
        action.writeText(firstName, firstNameGift, "First name", test);
        action.writeText(lastname, lastnameGift, "Last name", test);
        action.writeText(email, emailGift, "Email", test);
        action.writeText(streetNameGift, Streetname, "Street name", test);
        action.writeText(telephoneGift, phoneGift, "telephone", test);
        action.writeText(cityGift, Cityname, "city", test);
        action.writeText(SuburbGift, SuburbG, "Suburb", test);
        action.writeText(postalCodeGift, Postalcode, "postalCode", test);
        action.explicitWait(12000);
        action.dropDownselectbyvisibletext(provinceGift, provinceG, "province", test);
        action.explicitWait(10000);
        action.click(placeOrder, "placeOrder", test);

    }
}
