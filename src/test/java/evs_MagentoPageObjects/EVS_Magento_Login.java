package evs_MagentoPageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.DataTable2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EVS_Magento_Login {

	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	public static int ajaxTimeOutInSeconds;

		public EVS_Magento_Login(WebDriver driver, DataTable2 dataTable2) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
			action = new Action(driver);
			this.dataTable2=dataTable2;
			
		}
		
		//Login to magento
		@FindBy(xpath = "//input[@id='username']")
		WebElement Magento_Username;
		
		@FindBy(xpath = "//input[@id='login']")
		WebElement Magento_Password;
		@FindBy(xpath = "//span[contains(text(),'Sign in')]")
		WebElement Magento_SigninBtn;
		
		@FindBy(xpath = "//h1[contains(text(),'Dashboard')]")
		WebElement Dashboard;

		public void Login_magento(ExtentTest test) throws Exception{
			String Username = "";
			String Password = "";
			ajaxTimeOutInSeconds = Integer.parseInt(dataTable2.getValueOnOtherModule("EVS_Login_magento", "TimeOutInSecond", 0));
			LoginToMagento(test,Username,Password);			
	     }
		public void LoginToMagento(ExtentTest test,String Username, String Password) throws Exception{
			String url =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"),"url");
			Username =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"),"username");
			Password =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"),"password");
			action.navigateToURL(url);
			action.waitForJStoLoad(ajaxTimeOutInSeconds);
			String ResPage = action.getPageTitle(test);
			if(ResPage.equalsIgnoreCase("Magento Admin - DEFAULT STORE VIEW")){
				action.CompareResult("Navigate to magento admin page is success", ResPage, "Magento Admin - DEFAULT STORE VIEW", test);
				action.writeText(Magento_Username, Username, "Username field", test);
				action.writeText(Magento_Password, Password, "Password field", test);
				action.clickEle(Magento_SigninBtn, "Magento Signin Button", test);
				action.waitForJStoLoad(ajaxTimeOutInSeconds);
				action.ajaxWait(ajaxTimeOutInSeconds,test);
				action.explicitWait(10000);
				//String resWelcomescreen = action.getText(Dashboard, "Dashboard", test);
				//action.CompareResult("Navigation to magento admin page is successful", "Dashboard",driver.getTitle() , test);
			}
//			else{
//				action.CompareResult("Navigation to magento admin page is successful", ResPage, "Magento Admin - DEFAULT STORE VIEW", test);			
//    		}
			
			if(driver.getTitle().contains("Dashboard")) {
				action.CompareResult("Navigation to magento admin page is successful", "Dashboard",driver.getTitle() , test);
			}else {
				action.CompareResult("Navigation to magento admin page is unsuccessful", "Dashboard", driver.getTitle(), test);
				throw new Exception("Login to Magento is unsuccessful");
			}
		}
		

}
