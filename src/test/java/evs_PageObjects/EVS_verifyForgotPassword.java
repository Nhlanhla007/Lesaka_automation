package evs_PageObjects;

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

public class EVS_verifyForgotPassword {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	
	public EVS_verifyForgotPassword(WebDriver driver, DataTable2 dataTable2){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
		
	}
	
	@FindBy(className = "my-account")
	WebElement myAccountButton;

	@FindBy(xpath = "//a[contains(text(),'Log In')]")
	WebElement LoginBtn;
	
	@FindBy(xpath = "//span[contains(text(),'Forgot Your Password?')]")
	WebElement forgotPasswordLink;
	
	@FindBy(xpath = "//input[@id='email_address']")
	WebElement emailAddressTextBox;
	
	@FindBy(xpath = "//button[@type='submit' and @class = 'action submit primary']")
	WebElement resetPasswordButton;
	
	@FindBy(xpath = "//div[contains(text(),'If there is an account associated with')]")
	WebElement confirmationMessage;

	public void forgotPasswordPage(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
		
		String url = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS",dataTable2.getValueOnOtherModule("evs_Login", "loginDetails", 0), "url");
		String Username = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS",dataTable2.getValueOnOtherModule("evs_Login", "loginDetails", 0), "username");
	
		action.navigateToURL(url);
		action.waitForElementVisibility(myAccountButton,"myAccountButton", 10);
		action.clickEle(myAccountButton, "click on my account", test);
		action.waitForElementVisibility(myAccountButton,"myAccountButton", 3);
		action.clickEle(LoginBtn, "Login Button", test);
		action.waitForElementVisibility(forgotPasswordLink,"forgotPasswordLink", 10);
		action.CompareResult("Redirecting to forgot password page", "Forgot Your Password?", forgotPasswordLink.getText(), test);
		action.clickEle(forgotPasswordLink, "Forgot Password Link", test);
		action.waitForElementVisibility(emailAddressTextBox,"emailAddressTextBox", 10);
		action.writeText(emailAddressTextBox, Username, "enter existing email", test);
		action.clickEle(resetPasswordButton, "Reset Password Button", test);
		action.waitForElementVisibility(confirmationMessage,"confirmationMessage", 10);
		
		String resetNewEmailTest=action.getText(confirmationMessage,"confirmMessage",test);
		action.CompareResult("Reset new email sent message", "If there is an account associated with "+Username+" you will receive an email with a link to reset your password.", resetNewEmailTest, test);
		action.explicitWait(5000);

	}
}
