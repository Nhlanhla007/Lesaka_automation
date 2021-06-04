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

public class EVS_forgotPasswordLink {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;

	public EVS_forgotPasswordLink(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;
	}

	@FindBy(className = "my-account")
	WebElement myAccountButton;

	@FindBy(xpath = "//a[contains(text(),'Log In')]")
	WebElement LoginBtn;

	@FindBy(xpath = "//span[contains(text(),'Forgot Your Password?')]")
	WebElement forgotPasswordLink;

	@FindBy(xpath = "//h3[contains(text(),'Forgot your password?')]")
	WebElement forgotPwdHeader;

	public void forgotPasswordLink(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber)
			throws IOException {

		String url = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS",dataTable2.getValueOnOtherModule("evs_Login", "loginDetails", 0), "url");

		action.navigateToURL(url);

		action.waitForElementVisibility(myAccountButton,"myAccountButton", 15);
		action.clickEle(myAccountButton, "click on my account", test);
		
		action.waitForElementVisibility(LoginBtn,"LoginBtn", 3);
		action.clickEle(LoginBtn, "click on Login Button", test);
		
		action.waitForElementVisibility(forgotPasswordLink,"forgotPasswordLink", 10);
		action.clickEle(forgotPasswordLink, "Click on Forgot Password Link", test);
		
		action.CompareResult("Redirecting to forgot password page", "Forgot your password?",
				forgotPwdHeader.getText(), test);

	}

}
