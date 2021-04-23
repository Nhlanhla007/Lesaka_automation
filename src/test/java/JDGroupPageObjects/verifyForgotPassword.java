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
import utils.ConfigFileReader;
import utils.DataTable2;

public class verifyForgotPassword {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	
	public verifyForgotPassword(WebDriver driver, DataTable2 dataTable2){
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
	
	@FindBy(xpath = "//input[@id =\"email_address\"]")
	WebElement ic_forgotPasswordTxt;
	
	@FindBy(xpath = "//span[contains(text(),'Submit')]")
	WebElement ic_submit;
	
	@FindBy(xpath = "/html/body/div[1]/header/div[3]/div[2]/div/div/div")
	WebElement ic_confirmMessage;

	public void forgotPasswordPage(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
		String email = dataTable2.getValueOnOtherModule("accountCreation", "emailAddress", 0);
		ConfigFileReader configFileReader = new ConfigFileReader();
		action.navigateToURL(ConfigFileReader.getPropertyVal("URL"));
		action.explicitWait(5000);
		action.click(ic_myAccountButton, "click on my account", test);
		action.explicitWait(3000);
		action.click(ic_myAccountlist, "list", test);
		action.explicitWait(3000);
		action.click(ic_forgotPassword, "click forgot link password", test);
		action.CompareResult("Redirecting to forgot password page", "Forgot Your Password?", ic_forgotPassword.getText(), test);
		
		action.writeText(ic_forgotPasswordTxt, email, "enter existing email", test);
		action.explicitWait(5000);
		action.click(ic_submit, "click submit", test);
		
		action.CompareResult("Reset new email sent message", "If there is an account associated with "+email+" you will receive an email with a link to reset your password.", ic_confirmMessage.getText(), test);
		
	}
}
