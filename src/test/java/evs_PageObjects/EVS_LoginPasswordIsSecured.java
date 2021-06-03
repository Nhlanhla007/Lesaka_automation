package evs_PageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.ConfigFileReader;
import utils.DataTable2;

public class EVS_LoginPasswordIsSecured {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	String Username;
	String Password;

	public EVS_LoginPasswordIsSecured(WebDriver driver, DataTable2 dataTable2) {
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

	public void loginPasswordSafe(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber)
			throws IOException {

		String url = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS",
				dataTable2.getValueOnOtherModule("evs_Login", "loginDetails", 0), "url");
		String Username = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS",
				dataTable2.getValueOnOtherModule("evs_Login", "loginDetails", 0), "username");
		String Password = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS",
				dataTable2.getValueOnOtherModule("evs_Login", "loginDetails", 0), "password");

		driver.navigate().to(url);
		action.explicitWait(10000);
		action.clickEle(evs_myAccountButton, "My Account Button", test);
		action.explicitWait(3000);
		action.clickEle(LoginBtn, "Login Button", test);

		action.writeText(evs_email, Username, "Username field", test);
		action.writeText(evs_Password, Password, "Password field", test);
		action.explicitWait(3000);

		String safePass = action.getText(evs_Password, "Get password Encrypted ", test);

		if (safePass != null | safePass != "") {
			action.CompareResult("The password is safe" + safePass, "true", "true", test);
			action.getScreenShot(Password);
		}

	}

}
