package JDGroupPageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ICDelivery {
    WebDriver driver;
    Action action;

    public ICDelivery(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
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

    @FindBy(xpath = "//*[@id='opc-sidebar']/div[1]/div[1]/button")
    WebElement Btn_PlaceOrder;



    public void deliveryPopulation(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException, InterruptedException {

//        action.isElementOnNextPage(deliveryLink,(long)10,test);
        Thread.sleep(8000);
        action.click(deliveryLink,"deliveryLink",test);
//        action.isElementOnNextPage(streetName,(long)10,test);
        Thread.sleep(8000);
        action.writeText(streetName,input.get("streetName").get(rowNumber),"streetName",test);
        action.writeText(firstName,input.get("firstName").get(rowNumber),"firstName",test);
        action.writeText(lastname,input.get("lastname").get(rowNumber),"lastname",test);
        action.writeText(telephone,input.get("telephone").get(rowNumber),"telephone",test);
        action.writeText(city,input.get("city").get(rowNumber),"city",test);
        action.writeText(Suburb,input.get("Suburb").get(rowNumber),"Suburb",test);
        action.writeText(postalCode,input.get("postalCode").get(rowNumber),"postalCode",test);
        action.writeText(vatNumber,input.get("vatNumber").get(rowNumber),"vatNumber",test);
        action.writeText(email,input.get("email").get(rowNumber),"email",test);
        action.writeText(idNumber,input.get("idNumber").get(rowNumber),"idNumber",test);
        Thread.sleep(8000);
        action.dropDownselectbyvisibletext(province,input.get("province").get(rowNumber),"province",test);
        Thread.sleep(10000);
//        action.isElementOnNextPage(ContinueToPayment,(long)10,test);
        action.click(ContinueToPayment,"ContinueToPayment",test);



    }
}
