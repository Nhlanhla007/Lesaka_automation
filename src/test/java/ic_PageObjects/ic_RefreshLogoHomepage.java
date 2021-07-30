package ic_PageObjects;

import java.io.IOException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class ic_RefreshLogoHomepage {

    WebDriver driver;
    Action action;
    DataTable2 dataTable2;

    public ic_RefreshLogoHomepage(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;

    }

    @FindBy(xpath = "//*[@class = \"logo\"]")
    private WebElement ic_logo;

    @FindBy(xpath = "//html/head/title")
    private WebElement ic_titlepage;


    public void homepageLogo(ExtentTest test) throws Exception {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        action.explicitWait(10000);
        js.executeScript("arguments[0].scrollIntoView();", ic_logo);
        try{
        action.javaScriptClick(ic_logo, "Click to go homepage", test);
            action.waitForPageLoaded(40);
            String title = driver.getTitle();
            action.CompareResult("Return homepage", "Incredible Connection Home", title, test);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Unable to Navigate to Home Page: "+e.getMessage());
        }


    }
}
