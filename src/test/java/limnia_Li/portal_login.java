package limnia_Li;

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

public class portal_login {
	 WebDriver driver;
	    Action action;
	    DataTable2 dataTable2;

	    public portal_login(WebDriver driver, DataTable2 dataTable2) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
	        this.dataTable2 = dataTable2;
	    }
	    
	    @FindBy(xpath = "(//*[contains(text(),'Portal')])[1]")
	    WebElement btn_portal;
	    @FindBy(xpath = "//*[@id='username']")
	    WebElement txt_username;
	    @FindBy(xpath = "//*[@id='password']")
	    WebElement txt_password;
	    @FindBy(xpath = "//*[contains(text(),'Login ')]")
	    WebElement btn_login;
	    @FindBy(xpath = "//title[contains(text(),'Limnia | Dashboard')]")
	    WebElement btn_Dashboardpage;
	    
	    
	    public void launchPortal(ExtentTest test) {
			String url = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS",dataTable2.getValueOnCurrentModule("urlKey"), "url");
			action.navigateToURL(url);
			action.waitForJStoLoad(120);
		}
	    
	    
	    public void Login( ExtentTest test, int rowNumber) throws IOException, InterruptedException {
	    	 String url = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS", dataTable2.getValueOnCurrentModule("loginDetails"), "url");
	         String Username = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS", dataTable2.getValueOnCurrentModule("loginDetails"), "username");
	         String Password = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS", dataTable2.getValueOnCurrentModule("loginDetails"), "password");
	         JavascriptExecutor executor = (JavascriptExecutor) driver;
	         
	         //action.navigateToURL(url); 
	         
	        	 if(action.waitUntilElementIsDisplayed(txt_username,2)) {
	        		 action.writeText(txt_username, Username, "Username field", test);
	        	        action.writeText(txt_password, Password, "Password field", test);
	        	        action.clickEle(btn_login, "click on Login", test);
	        	        action.waitForJStoLoad(120);
	        	        
	        	        if(action.isDisplayed(btn_Dashboardpage)) {
	        	        	action.CompareResult("Successful Login", "true", "true", test);
	        	        }else {
	        	        	action.CompareResult("Unsuccessful Login", "true", "true", test);
	        	        }
	        		 
	        	 }
	        	 
	    }

}
