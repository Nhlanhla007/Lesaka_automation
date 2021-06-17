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
		
		//vv
		public void Login_magento(ExtentTest test) throws IOException{
			String Username = "";
			String Password = "";
			LoginToMagento(test,Username,Password);
	     }
		public void LoginToMagento(ExtentTest test,String Username, String Password) throws IOException{
			String url =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"),"url");
			Username =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"),"username");
			Password =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"),"password");
			action.navigateToURL(url);
			action.waitForPageLoaded(10);
			String ResPage = action.getPageTitle(test);
			if(ResPage.equalsIgnoreCase("Magento Admin - DEFAULT STORE VIEW")){
				action.CompareResult("Navigate to magento admin page is success", ResPage, "Magento Admin - DEFAULT STORE VIEW", test);
				action.writeText(Magento_Username, Username, "Username field", test);
				action.writeText(Magento_Password, Password, "Password field", test);
				action.clickEle(Magento_SigninBtn, "click Magento_SigninBtn", test);
				action.explicitWait(10000);
				String resWelcomescreen = action.getText(Dashboard, "Dashboard", test);
			}else{
				action.CompareResult("Navigate to magento admin page is success", ResPage, "Magento Admin - DEFAULT STORE VIEW", test);
			
    		}
		}
		

}
