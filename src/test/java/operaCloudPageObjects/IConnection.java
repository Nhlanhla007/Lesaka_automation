package operaCloudPageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;

import java.util.ArrayList;
import java.util.HashMap;

public class IConnection {
    WebDriver driver;
    Action action;

    public IConnection(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);

    }

    @FindBy(name="username")
    private WebElement testUserName;

    @FindBy(name="password")
    private WebElement testPassword;

    @FindBy(name="FormsButton2")
    private WebElement testLoginButton;

    @FindBy(xpath="//input[contains(@id,'lovTrxCode:odec_lov_itLovetext::content')]")
    private WebElement EnterCode;

    @FindBy(xpath="//input[contains(@id,'fe2:itPrice:odec_in_it::content')]")
    private WebElement EnterPrice;

    @FindBy(xpath="//div[@class='xqu p_AFTextOnly']//span[text()='Post Payment']")
    private WebElement PostPayment;

    @FindBy(xpath="//input[contains(@id,'itAmount:occ_crncy_amt_fld:odec_in_it::content')]")
    private WebElement Amount;

    @FindBy(xpath="//span[text()='Apply Payment']")
    private WebElement ApplyPayment;

    @FindBy(xpath="//a[contains(@id,'dc_pwPost::close')]")
    private WebElement ClosePopup;

    public void login(HashMap<String, ArrayList<String>> input,ExtentTest test,int occCount) {
        try {


            System.out.println("modelStart");
            action.writeText(testUserName, input.get("username").get(occCount), "username", test);
            action.writeText(testPassword, input.get("password").get(occCount), "password", test);
            action.click(testLoginButton, "loginButton", test);
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("modelend");
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
