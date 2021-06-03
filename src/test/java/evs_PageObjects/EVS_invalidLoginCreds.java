package evs_PageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.ConfigFileReader;
import utils.DataTable2;

public class EVS_invalidLoginCreds {

	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	String Username;
	String Password;

	public EVS_invalidLoginCreds(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;
	}

	@FindBy(className = "my-account")
	WebElement evs_myAccountButton;

	@FindBy(xpath = "//a[contains(text(),'Log In')]")
	WebElement LoginBtn;

	@FindBy(xpath = "//input[@id='email']")
	WebElement evs_email;

	@FindBy(xpath = "//input[@id='pass']")
	WebElement evs_Password;

	@FindBy(xpath = "//button[@class='action login primary']")
	WebElement evs_SigninBtn;

	@FindBy(xpath = "//div[@id='email-error']")
	WebElement ic_InvalidUsername;

	@FindBy(xpath = "//div[contains(text(),'Your account sign-in was incorrect. Please try aga')]")
	WebElement ic_InvalidCreds;

	public void invalidLogin_evs(LinkedHashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber)
			throws IOException {

		String keyvalue=dataTable2.getValueOnOtherModule("evs_Login", "loginDetails", 0);
		String url = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS",keyvalue, "url");

		Username = dataTable2.getValueOnCurrentModule("Username");
		Password = dataTable2.getValueOnCurrentModule("Password");
		action.navigateToURL(url);
		action.explicitWait(10000);
		action.click(evs_myAccountButton, "evs_myAccountButton", test);
		action.explicitWait(3000);
		action.click(LoginBtn, "evs_Login Button", test);
		List<String> userCred = new ArrayList<>();
		String UsernameFLag = dataTable2.getValueOnCurrentModule("UsernameFlag");
		String PasswordFLag = dataTable2.getValueOnCurrentModule("PasswordFlag");
		userCred.add(Username);
		userCred.add(Password);
		action.writeText(evs_email, Username, "Username field", test);
		action.writeText(evs_Password, Password, "Password field", test);
		action.clickEle(evs_SigninBtn, "click ic_SigninBtn", test);

		if (UsernameFLag.equalsIgnoreCase("Yes")) {
			action.CompareResult("Invalid email", "Please enter a valid email address (Ex: johndoe@domain.com).",
					ic_InvalidUsername.getText(), test);
			action.explicitWait(10000);

		}
		if (PasswordFLag.equalsIgnoreCase("Yes")) {
			String errorpopup = action.getText(ic_InvalidCreds, "ic_InvalidCreds", test);
			action.CompareResult("Invalid Login Credentials", "Your account sign-in was incorrect. Please try again.",
					errorpopup, test);
		}
	}

}
