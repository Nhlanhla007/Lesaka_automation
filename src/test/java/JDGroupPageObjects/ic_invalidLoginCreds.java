package JDGroupPageObjects;

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

public class ic_invalidLoginCreds {
	
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	
	public ic_invalidLoginCreds(WebDriver driver, DataTable2 dataTable2){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	}
	
	//Login to Ic
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
			
			@FindBy(xpath = "//*[@id=\"email-error\"]")
			WebElement ic_InvalidUsername;
			//div[contains(text(),'Your account sign-in was incorrect. Please try again.')]Your account sign-in was incorrect. Please try again.
			@FindBy(xpath = "//*[@class= \"message-error error message\"]/div")
			WebElement ic_InvalidCreds;
			
			//*[@id="email-error"]
			
			public List<String> invalidLogin_ic(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
				String url =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"),"url");
				String Username =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"),"username");
				String Password =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"),"password");
				driver.navigate().to(url);
//				action.waitForPageLoaded(10);
				action.explicitWait(10000);
//				ic_myAccountButton.click();
				action.click(ic_myAccountButton,"ic_myAccountButton",test);
				action.explicitWait(3000);
//				ic_myAccountlist.click();
				action.click(ic_myAccountlist,"ic_myAccountlist",test);
				
				List<String> userCred = new ArrayList<>();
				String UsernameFLag = dataTable2.getValueOnCurrentModule("UsernameFlag");
				//String Username =dataTable2.getValueOnOtherModule("ic_login", "Username", 0);
				
				String PasswordFLag = dataTable2.getValueOnCurrentModule("PasswordFlag");
				//String Password =dataTable2.getValueOnOtherModule("ic_login", "Password", 0);
				userCred.add(Username);
				userCred.add(Password);
				action.writeText(ic_Username, Username, "Username field", test);
				action.writeText(ic_Password, Password, "Password field", test);
				action.clickEle(ic_SigninBtn, "click ic_SigninBtn", test);
				
				if(UsernameFLag.equalsIgnoreCase("Yes")){
					action.CompareResult("Invalid email", "Please enter a valid email address (Ex: johndoe@domain.com).", ic_InvalidUsername.getText(), test);
					action.explicitWait(10000);
				
				} 
				if(PasswordFLag.equalsIgnoreCase("Yes")){
					String errorpopup=action.getText(ic_InvalidCreds,"ic_InvalidCreds",test);
				action.CompareResult("Login with invalid creds", "Your account sign-in was incorrect. Please try again.", errorpopup, test);
				}
					userCreds(userCred);
					
					return userCred;

		     }
			
			public List<String> userCreds(List<String> userCreds){
				return userCreds;
			}	

}
