package ic_PageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class ic_Login {

    WebDriver driver;
    Action action;
    DataTable2 dataTable2;

    public ic_Login(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
    }

    //Login to magento
    @FindBy(className = "my-account")
    WebElement ic_myAccountButton;

    //*[@id="header-slideout--0"]/li[3]/a
    @FindBy(xpath = "//*[@id=\"header-slideout--0\"]/li[3]/a")
    WebElement ic_Login;

    @FindBy(xpath = "//*[@id='email']")
    WebElement ic_Username;

    @FindBy(xpath = "//*[@id='pass']")
    WebElement ic_Password;
    @FindBy(xpath = "//*[@id=\"send2\"]/span")

    WebElement ic_SigninBtn;

    //div[contains(text(),'Your account sign-in was incorrect. Please try again.')]Your account sign-in was incorrect. Please try again.
    @FindBy(xpath = "//html/body/div[1]/header/div[3]/div[2]/div/div")
    WebElement ic_InvalidCreds;

    @FindBy(className = "authorization-link")
    WebElement logout;

    @FindBy(xpath = "//*[@class = \"logo\"]")
    private WebElement ic_logo;

    public static String Username;

    public List<String> Login_ic(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws IOException, InterruptedException {

        String url = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS", dataTable2.getValueOnCurrentModule("loginDetails"), "url");
        String Username = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS", dataTable2.getValueOnCurrentModule("loginDetails"), "username");
        String Password = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS", dataTable2.getValueOnCurrentModule("loginDetails"), "password");
        JavascriptExecutor executor = (JavascriptExecutor) driver;

        action.navigateToURL(url);
        action.waitForJStoLoad(60);
//        action.waitForPageLoaded(30);
        executor.executeScript("arguments[0].click();", ic_myAccountButton);
        action.explicitWait(2000);
        executor.executeScript("arguments[0].click();", ic_Login);
        List<String> userCred = new ArrayList<>();

        userCred.add(Username);
        userCred.add(Password);
        action.waitUntilElementIsDisplayed(ic_Username,2);
        action.writeText(ic_Username, Username, "Username field", test);
        action.writeText(ic_Password, Password, "Password field", test);
        action.clickEle(ic_SigninBtn, "click ic_SigninBtn", test);
        action.waitForJStoLoad(60);
//        action.waitForPageLoaded(20);
        String expectedTitle = "Incredible Connection My Account";

        if (driver.getTitle().equalsIgnoreCase(expectedTitle)) {
            action.CompareResult("User successful Login ", "True", "True", test);
        } else {
            action.CompareResult("User successful Login", "True", "False", test);
        }

        action.clickEle(ic_logo, "IC Home Logo", test);
        action.waitForJStoLoad(30);
//        action.waitForPageLoaded(10);


        userCreds(userCred);
        return userCred;
    }

    public List<String> userCreds(List<String> userCreds) {
        return userCreds;
    }

    public void logout(ExtentTest test, HashMap<String, ArrayList<String>> input, int rowNumber) throws Exception {
        action.click(ic_myAccountButton, "My account", test);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", logout);
        action.click(logout, "logout", test);
    }
}
