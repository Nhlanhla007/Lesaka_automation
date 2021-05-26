package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class IC_verifyLogin {
	WebDriver driver;
    Action action;
    DataTable2 dataSheets;

    public IC_verifyLogin(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        dataSheets=dataTable2;
        
    }
    @FindBy(xpath = "//h1[@class='page-title']/span[contains(text(),'Customer Login')]")
    WebElement Header_customerLoginMsg;
    
    @FindBy(xpath = "//div[text()='You must login or register to add items to your wishlist.']")
    WebElement LoginWarning_Msg;
    public void IC_verifyLogin_addingProductTowishlist(ExtentTest test,int rowNumber) throws IOException{
    	int waitTime =11;
    	boolean CheckPagetitle = verifyLoginPageTitle(waitTime, test);
    	if(CheckPagetitle){
    		String LoginMsg = action.getText(LoginWarning_Msg, "Login Warning Msg",test);
    		action.CompareResult("Login to proceed for wishlist", "You must login or register to add items to your wishlist.", LoginMsg, test);
    	}else{
    		action.CompareResult("Customer login is Required to continue", "true", String.valueOf(CheckPagetitle), test);
    	}
    }
    public boolean verifyLoginPageTitle(int Timeout,ExtentTest test) throws IOException{
    	boolean checkflg = false;
    	String ExpHeadermsg = "Customer Login";
    	if(action.elementExists(Header_customerLoginMsg, Timeout)){
    		String ActLoginPageHdr = action.getText(Header_customerLoginMsg, "customer Login",test);
    		if(ActLoginPageHdr.equalsIgnoreCase(ExpHeadermsg)){
    			checkflg=true;
    			action.CompareResult("Customer login page is loaded ", "true", String.valueOf(checkflg), test);
    		}else{
    			action.CompareResult("Customer login page is loaded ", "true", String.valueOf(checkflg), test);
    		}
    	}
		return checkflg;
    	
    }
}
