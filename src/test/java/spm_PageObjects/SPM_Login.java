package spm_PageObjects;

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

public class SPM_Login {

    WebDriver driver;
    Action action;
    DataTable2 dataTable2;

    public SPM_Login(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
    }

    @FindBy(className = "my-account")
    WebElement spm_myAccountButton;

    @FindBy(xpath = "//*[@class=\"action primary login\"]")
    WebElement spm_Login;

    @FindBy(xpath = "//*[@id='email']")
    WebElement spm_Username;

    @FindBy(xpath = "//*[@id='pass']")
    WebElement spm_Password;

    @FindBy(xpath = "//*[@id=\"send2\"]")
    WebElement spm_SigninBtn;

    @FindBy(xpath = "//html/body/div[1]/header/div[3]/div[2]/div/div")
    WebElement ic_InvalidCreds;

    @FindBy(className = "authorization-link")
    WebElement logout;

    @FindBy(xpath = "//*[@class = \"logo\"]")
    private WebElement ic_logo;

    public static String Username;

    public List<String> Login_ic(ExtentTest test) throws Exception {

        String url = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS", dataTable2.getValueOnCurrentModule("loginDetails"), "url");
        String Username = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS", dataTable2.getValueOnCurrentModule("loginDetails"), "username");
        String Password = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS", dataTable2.getValueOnCurrentModule("loginDetails"), "password");
        JavascriptExecutor executor = (JavascriptExecutor) driver;

        action.navigateToURL(url);
        action.waitForJStoLoad(120);
        executor.executeScript("arguments[0].click();", spm_myAccountButton);
        action.explicitWait(2000);
        executor.executeScript("arguments[0].click();", spm_Login);
        List<String> userCred = new ArrayList<>();

        userCred.add(Username);
        userCred.add(Password);
        action.waitUntilElementIsDisplayed(spm_Username,2);
        action.writeText(spm_Username, Username, "Username field", test);
        action.writeText(spm_Password, Password, "Password field", test);
        action.clickEle(spm_SigninBtn, "click Sleepmasters Sign In Button", test);
//        action.waitForJStoLoad(120);
        action.waitForPageLoaded(20);
        action.ajaxWait(20, test);
        String expectedTitle = "SleepMasters Home";

        action.explicitWait(5000);
        if (driver.getTitle().contains(expectedTitle)) {
        	System.out.println(driver.getTitle());
            action.CompareResult("User successful Login ", "True", "True", test);
        } else {
            action.CompareResult("User successful Login", "True", "False", test);
            throw new Exception("Login was Unsuccessful");
        }

        //action.clickEle(ic_logo, "IC Home Logo", test);
            //action.explicitWait(16000);
       // action.waitForJStoLoad(30);
        //userCreds(userCred);
        return userCred;
    }

    public List<String> userCreds(List<String> userCreds) {
        return userCreds;
    }

    public void logout(ExtentTest test, HashMap<String, ArrayList<String>> input, int rowNumber) throws Exception {
        action.click(spm_myAccountButton, "My account", test);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", logout);
        action.click(logout, "logout", test);
    }
}
