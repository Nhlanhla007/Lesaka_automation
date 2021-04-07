package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import io.qameta.allure.Step;
import utils.Action;
import utils.ConfigFileReader;
import utils.DataTable2;

public class ic_Login {

	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
		public ic_Login(WebDriver driver, DataTable2 dataTable2) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
			action = new Action(driver);
			this.dataTable2=dataTable2;
		}
		//Login to magento
		@FindBy(className = "my-account")
		WebElement ic_myAccountButton;
		
		//*[@id="header-slideout--0"]/li[3]/a
		@FindBy(xpath = "//*[@id=\"header-slideout--0\"]/li[3]/a")
		WebElement ic_myAccountlist;
		
		@FindBy(xpath = "//*[@id='email']")
		WebElement ic_Username;
		
		@FindBy(xpath = "//*[@id='pass']")
		WebElement ic_Password;
		@FindBy(xpath = "//*[@id=\"send2\"]/span")
		WebElement ic_SigninBtn;
		
		@FindBy(xpath = "//h1[contains(text(),'Dashboard')]")
		WebElement Dashboard;
		
		public static String Username;
		
		//vv
		@Step("Login to IC")
		public List<String> Login_ic(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
			driver.navigate().to(ConfigFileReader.getPropertyVal("URL"));
//			action.waitForPageLoaded(10);
			action.explicitWait(10000);
			ic_myAccountButton.click();
			action.explicitWait(3000);
			ic_myAccountlist.click();
			
			List<String> userCred = new ArrayList<>();
			String Username =input.get("Username").get(rowNumber);
			String Password =input.get("Password").get(rowNumber);
			userCred.add(Username);
			userCred.add(Password);
			action.writeText(ic_Username, Username, "Username field", test);
			action.writeText(ic_Password, Password, "Password field", test);
			action.clickEle(ic_SigninBtn, "click ic_SigninBtn", test);
				userCreds(userCred);
				
				return userCred;

				/*action.waitExplicit(31);
				String resWelcomescreen = action.getText(Dashboard, "Dashboard");
				System.out.println(resWelcomescreen);*/
			//}else{
			//	action.CompareResult("Navigate to magento admin page is success", ResPage, "Magento Admin", test);
			//}
	     }
		
		public List<String> userCreds(List<String> userCreds){
			return userCreds;
		}	
		

}
