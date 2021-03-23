package JDGroupPageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.DataTable2;

import java.util.ArrayList;
import java.util.HashMap;

public class IConnection {
    WebDriver driver;
    Action action;

    public IConnection(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);

    }

    @FindBy(id="txtUsername")
    private WebElement testUserName;

    @FindBy(id="txtPassword")
    private WebElement testPassword;

    @FindBy(id="btnLogin")
    private WebElement testLoginButton;

    @FindBy(id="welcome")
    private WebElement userMenu;

    @FindBy(linkText="Logout")
    private WebElement logoutLink;

    @FindBy(xpath="//div[@class='xqu p_AFTextOnly']//span[text()='Post Payment']")
    private WebElement PostPayment;

    @FindBy(xpath="//input[contains(@id,'itAmount:occ_crncy_amt_fld:odec_in_it::content')]")
    private WebElement Amount;

    @FindBy(xpath="//span[text()='Apply Payment']")
    private WebElement ApplyPayment;

    @FindBy(xpath="//a[contains(@id,'dc_pwPost::close')]")
    private WebElement ClosePopup;

    public void login(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) {
        try {
//
            System.out.println("modelStart");
            System.out.println("rowNumber:"+rowNumber);
            System.out.println("username:"+input.get("username").get(rowNumber));
            System.out.println("password:"+input.get("password").get(rowNumber));
            action.writeText(testUserName, input.get("username").get(rowNumber), "username", test);
            action.writeText(testPassword, input.get("password").get(rowNumber), "password", test);
            action.click(testLoginButton, "loginButton", test);
            action.isElementOnNextPage(userMenu, (long) 5,test);
            System.out.println("modelend");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void logout(ExtentTest test) {
        try {
            //
            System.out.println("modelStart");
            action.click(userMenu, "userMenu", test);
            action.click(logoutLink, "logoutLink", test);
            action.isElementOnNextPage(testUserName, (long) 5,test);
            System.out.println("modelend");
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
