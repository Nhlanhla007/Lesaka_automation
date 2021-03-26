package JDGroupPageObjects;

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
import java.util.concurrent.TimeUnit;

public class ICDelivery {
    WebDriver driver;
    Action action;
    DataTable2 dataSheets;

    public ICDelivery(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        dataSheets=dataTable2;
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

    @FindBy(name= "postcode")
    WebElement postalCode;

    @FindBy(name = "vat_id")
    WebElement vatNumber;

    @FindBy(name = "custom_attributes[identity_number]")
    WebElement idNumber;

    @FindBy(xpath = "//*[@id=\"opc-sidebar\"]/div[1]/div[1]/button")
    WebElement ContinueToPayment;

    //---------------------------------------------------------------

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

    @FindBy(name= "postcode")
    WebElement postalCodeGift;

    @FindBy(xpath = "//*[@id=\"opc-sidebar\"]/div[1]/div[1]/button/span")
    WebElement placeOrder;

    public static String Streetname;
    public static String Cityname;
    public static String Postalcode;

    public void deliveryPopulation(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException, InterruptedException {
    	Streetname =input.get("streetName").get(rowNumber);
    	Cityname =input.get("city").get(rowNumber);
    	Postalcode = input.get("postalCode").get(rowNumber);
        Thread.sleep(8000);
        action.click(deliveryLink,"deliveryLink",test);
        Thread.sleep(8000);
        action.writeText(streetName,dataSheets.getValueOnCurrentModule("streetName"),"streetName",test);
        action.writeText(firstName,dataSheets.getValueOnCurrentModule("firstName"),"firstName",test);
        action.writeText(lastname,dataSheets.getValueOnCurrentModule("lastname"),"lastname",test);
        action.writeText(telephone,dataSheets.getValueOnCurrentModule("telephone"),"telephone",test);
        action.writeText(city,dataSheets.getValueOnCurrentModule("city"),"city",test);
        action.writeText(Suburb,dataSheets.getValueOnCurrentModule("Suburb"),"Suburb",test);
        action.writeText(postalCode,dataSheets.getValueOnCurrentModule("postalCode"),"postalCode",test);
        action.writeText(vatNumber,dataSheets.getValueOnCurrentModule("vatNumber"),"vatNumber",test);
        action.writeText(email,dataSheets.getValueOnCurrentModule("email"),"email",test);
        Thread.sleep(15000);
        action.writeText(idNumber,dataSheets.getValueOnCurrentModule("idNumber"),"idNumber",test);
        Thread.sleep(12000);
        action.dropDownselectbyvisibletext(province,dataSheets.getValueOnCurrentModule("province"),"province",test);
        Thread.sleep(10000);
        action.click(ContinueToPayment,"ContinueToPayment",test);
    }
    public void deliveryPopulationGiftCard(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws InterruptedException, IOException {
        Streetname =input.get("streetName").get(rowNumber);
        Cityname =input.get("city").get(rowNumber);
        Postalcode = input.get("postalCode").get(rowNumber);
        driver.manage().timeouts().implicitlyWait(300, TimeUnit.SECONDS);
        streetNameGift.sendKeys("text");
//        action.writeText(streetNameGift,"Indian Drive, Keysborough VIC, Australia","streetName",test);
        action.writeText(telephoneGift,input.get("telephone").get(rowNumber),"telephone",test);
        action.writeText(cityGift,input.get("city").get(rowNumber),"city",test);
        action.writeText(SuburbGift,input.get("Suburb").get(rowNumber),"Suburb",test);
        action.writeText(postalCodeGift,input.get("postalCode").get(rowNumber),"postalCode",test);
        Thread.sleep(12000);
        action.dropDownselectbyvisibletext(provinceGift,input.get("province").get(rowNumber),"province",test);
        Thread.sleep(10000);
        action.click(placeOrder,"placeOrder",test);
    }
}
