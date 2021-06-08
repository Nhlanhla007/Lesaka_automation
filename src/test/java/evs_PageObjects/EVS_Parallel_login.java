package evs_PageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import base.TestCaseBase;
import utils.Action;
import utils.ConfigFileReader;
import utils.DataTable2;

public class EVS_Parallel_login {

	WebDriver driver;
	Action action;
	DataTable2 dataSheets;
	DataTable2 dataTable2;
	String email;
	String Password;
	String FirstName;

	public EVS_Parallel_login(WebDriver driver, DataTable2 dataTable2) {
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

	@FindBy(xpath = "//span[@class='logged-in']")
	WebElement userName_display;

	public void checkParallelExecution(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber)
			throws IOException, InterruptedException {

		email = dataTable2.getValueOnCurrentModule("Email_Chrome_User");
		Password = dataTable2.getValueOnCurrentModule("Password_Chrome_User");
		FirstName = dataTable2.getValueOnCurrentModule("FirstName_Chrome_User");
		login(action, test);

		WebDriver driver2 = TestCaseBase.initializeTestBaseSetup("FIREFOX");
		PageFactory.initElements(driver2, this);
		Action action2 = new Action(driver2);

		email = dataTable2.getValueOnCurrentModule("Email_Firefox_User");
		Password = dataTable2.getValueOnCurrentModule("Password_Firefox_User");
		FirstName = dataTable2.getValueOnCurrentModule("FirstName_Firefox_User");
		login(action2, test);

		driver2.close();

	}

	public void login(Action action, ExtentTest test) throws IOException {

//		String url = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS",
//				dataTable2.getValueOnOtherModule("evs_Login", "loginDetails", 0), "url");
//
//		action.navigateToURL(url);
		action.waitForElementClickable(evs_myAccountButton,"evs_myAccountButton", 5);
		action.click(evs_myAccountButton, "My Account", test);
		action.waitForElementClickable(LoginBtn,"LoginBtn", 5);
		action.click(LoginBtn, "Login In", test);
		action.waitForElementVisibility(evs_email,"evs_email", 10);
		action.writeText(evs_email, email, "email field", test);
		action.waitForElementVisibility(evs_Password,"evs_Password", 5);
		action.writeText(evs_Password, Password, "Password field", test);
		action.clickEle(evs_SigninBtn, "click ic_SigninBtn", test);
		action.explicitWait(15000);
		action.waitForElementVisibility(userName_display,"userName_display", 15000);
		String expMsg = "Hi, " + FirstName;
		String wlc_msg = action.getText(userName_display, "Welcome Messsage for the User", test);
		action.CompareResult("Signup Validation", expMsg, wlc_msg, test);

	}

}
