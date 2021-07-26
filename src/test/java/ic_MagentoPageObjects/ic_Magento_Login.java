package ic_MagentoPageObjects;

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

public class ic_Magento_Login {

	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	public static int ajaxTimeOutInSeconds;
		public ic_Magento_Login(WebDriver driver, DataTable2 dataTable2) {
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
		
		//vv
		public void Login_magento(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws Exception {
			String Username = "";
			String Password = "";
			LoginToMagento(test,Username,Password);
			ajaxTimeOutInSeconds = Integer.parseInt(dataTable2.getValueOnOtherModule("Login_magento", "TimeOutInSecond", 0));
	     }
		public void LoginToMagento(ExtentTest test,String Username, String Password) throws Exception {
			String url =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"),"url");
			Username =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"),"username");
			Password =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"),"password");
			action.navigateToURL(url);
			action.waitForPageLoaded(10);
			String ResPage = driver.getTitle();
			System.out.println("Launch page title: "+driver.getTitle());

			if(ResPage.equalsIgnoreCase("Magento Admin")){
				action.CompareResult("Navigate to magento admin page is success", ResPage, "Magento Admin", test);
				action.writeText(Magento_Username, Username, "Username feild", test);
				action.writeText(Magento_Password, Password, "Password feild", test);
				action.clickEle(Magento_SigninBtn, "click Magento_SigninBtn", test);
				action.waitForPageLoaded(10);
				action.ajaxWait(10,test);
//				action.explicitWait(10000);
				String resWelcomescreen = action.getText(Dashboard, "Dashboard", test);
				System.out.println("Welcome page title: "+driver.getTitle());
				action.CompareResult("Navigate to magento admin page is success", "Dashboard",driver.getTitle() , test);

			}else{
				action.CompareResult("Navigate to magento admin page is success", ResPage, "Magento Admin", test);
			
    		}
		}
		

}
