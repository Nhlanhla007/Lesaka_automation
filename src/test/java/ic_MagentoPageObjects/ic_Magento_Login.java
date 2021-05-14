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
		
		public ic_Magento_Login(WebDriver driver, DataTable2 dataTable2) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
			action = new Action(driver);
			
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
		public void Login_magento(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
			String Username = input.get("Username").get(rowNumber);
			String Password = input.get("Password").get(rowNumber);
			LoginToMagento(test,Username,Password);
	     }
		public void LoginToMagento(ExtentTest test,String Username, String Password) throws IOException{
			driver.navigate().to(ConfigFileReader.getPropertyVal("MagentoURL"));
			action.waitForPageLoaded(10);
			String ResPage = driver.getTitle();
			if(ResPage.equalsIgnoreCase("Magento Admin")){
				action.CompareResult("Navigate to magento admin page is success", ResPage, "Magento Admin", test);
				action.writeText(Magento_Username, Username, "Username feild", test);
				action.writeText(Magento_Password, Password, "Password feild", test);
				action.clickEle(Magento_SigninBtn, "click Magento_SigninBtn", test);
				action.explicitWait(10000);
				String resWelcomescreen = action.getText(Dashboard, "Dashboard");
				//System.out.println(resWelcomescreen);
			}else{
				action.CompareResult("Navigate to magento admin page is success", ResPage, "Magento Admin", test);
			
    		}
		}
		

}
