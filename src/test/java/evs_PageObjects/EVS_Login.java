package evs_PageObjects;

import com.aventstack.extentreports.ExtentTest;

import base.TestCaseBase;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.ConfigFileReader;
import utils.DataTable2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EVS_Login {

	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
		public EVS_Login(WebDriver driver, DataTable2 dataTable2) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
			action = new Action(driver);
			this.dataTable2=dataTable2;
		}
		//Login to magento
		@FindBy(xpath =  "//div[@class = \"my-account icon__expand-arrow\"]")
		WebElement evs_myAccountButton;
		
	    @FindBy(css = "a.go-back")
	    private WebElement backButton;
		
		//*[@id="header-slideout--0"]/li[3]/a
		@FindBy(xpath = "//li[@class=\"authorization-link\"]/a")
		WebElement evs_myAccountlist;
		
		@FindBy(xpath = "//*[@id='email']")
		WebElement evs_Username;
		
		@FindBy(xpath = "//*[@id='pass']")
		WebElement evs_Password;
		
		@FindBy(xpath = "//*[@id=\"send2\"]/span")
		WebElement evs_SigninBtn;

		//div[contains(text(),'Your account sign-in was incorrect. Please try again.')]Your account sign-in was incorrect. Please try again.
		@FindBy(xpath = "//html/body/div[1]/header/div[3]/div[2]/div/div")
		WebElement ic_InvalidCreds;
		
		@FindBy(xpath = "//li[@class=\"authorization-link\"]")
		WebElement logout;
		
		//public static String Username;
		//public static String Password;
		
		public List<String> Login(ExtentTest test) throws Exception {
			// driver.navigate().to(ConfigFileReader.getPropertyVal("EVS_URL"));
//			action.waitForPageLoaded(10);
			String url = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"), "url");
			String Username = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"), "username");
			String Password = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"), "password");
			action.navigateToURL(url);
			action.waitForPageLoaded(10);
			driver.navigate().refresh();
			action.explicitWait(10000);
			action.click(evs_myAccountButton, "Sign In/Create Account", test);
			action.explicitWait(3000);
			action.click(evs_myAccountlist, "Log In", test);

			List<String> userCred = new ArrayList<>();
			// Username =dataTable2.getValueOnCurrentModule("Username");
			// Password =dataTable2.getValueOnCurrentModule("Password");
			userCred.add(Username);
			userCred.add(Password);
			action.writeText(evs_Username, Username, "Username field", test);
			action.writeText(evs_Password, Password, "Password field", test);
			action.clickEle(evs_SigninBtn, "click evs_SigninBtn", test);
			userCreds(userCred);
			action.explicitWait(5000);
			
			boolean loginVisablilityy = driver.findElements(By.xpath("//span[@class=\"logged-in\"]")).size() > 0;
			if(loginVisablilityy) {
				action.CompareResult("Is Login Successful?", "True", "True", test);
			}else {
				action.CompareResult("Is Login Successful?", "True", "False", test);
				throw new Exception("Login Is Unsuccessful");
			}

			return userCred;

			/*
			 * action.waitExplicit(31); String resWelcomescreen = action.getText(Dashboard,
			 * "Dashboard"); System.out.println(resWelcomescreen);
			 */
			// }else{
			// action.CompareResult("Navigate to magento admin page is success", ResPage,
			// "Magento Admin", test);
			// }
		}
		
		public List<String> userCreds(List<String> userCreds){
			return userCreds;
		}	

		public void logout(ExtentTest test) throws Exception {
			boolean buttonAvail = action.waitUntilElementIsDisplayed(backButton, 15000);
			action.explicitWait(4000);
			if(buttonAvail) {
				backButton.click();
			}
			if(action.waitUntilElementIsDisplayed(evs_myAccountButton, 10000)) {
			action.click(evs_myAccountButton, "My account", test);
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].scrollIntoView();", logout); 
			action.click(logout, "logout", test);
			}
		}
		
		/*
		 * public void closeBrowserAndLogin(ExtentTest test) throws Exception {
		 * driver.quit(); driver = null; driver =
		 * TestCaseBase.initializeTestBaseSetup("CHROME");
		 * 
		 * PageFactory.initElements(driver, this); action = new Action(driver);
		 * action.navigateToURL(ConfigFileReader.getPropertyVal("EVS_URL"));
		 * //Login(test); }
		 */
		
		
}
