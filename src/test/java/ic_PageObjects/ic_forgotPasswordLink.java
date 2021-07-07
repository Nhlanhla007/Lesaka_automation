package ic_PageObjects;

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

public class ic_forgotPasswordLink {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	public ic_forgotPasswordLink(WebDriver driver, DataTable2 dataTable2){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	}
	@FindBy(className = "my-account")
	WebElement ic_myAccountButton;
	
	//*[@id="header-slideout--0"]/li[3]/a
	@FindBy(xpath = "//*[@id=\"header-slideout--0\"]/li[3]/a")
	WebElement ic_myAccountlist;
	
	@FindBy(xpath = "//span[contains(text(),'Forgot Your Password?')]")
	WebElement ic_forgotPassword;
	
	 public void forgotPasswordLink(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
//		 String url =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnOtherModule("ic_login","loginDetails",0),"url");
//		 action.navigateToURL(url);

			action.explicitWait(10000);
			action.click(ic_myAccountButton, "click on my account", test);
			action.explicitWait(3000);
			action.click(ic_myAccountlist, "list", test);
			action.explicitWait(3000);
			action.click(ic_forgotPassword, "click forgot link password", test);
			//String pageTitle = driver.getTitle();
			//System.out.println("this is the page"+ pageTitle);
			action.CompareResult("Redirecting to forgot password page", "Forgot Your Password?", ic_forgotPassword.getText(), test);
			
	 }
	
}
