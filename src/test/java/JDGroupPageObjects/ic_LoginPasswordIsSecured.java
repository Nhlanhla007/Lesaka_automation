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

public class ic_LoginPasswordIsSecured {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	
	public ic_LoginPasswordIsSecured(WebDriver driver, DataTable2 dataTable2){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	}
	
	@FindBy(className = "my-account")
	WebElement ic_myAccountButton;
	
	//*[@id="header-slideout--0"]/li[3]/a
	@FindBy(xpath = "//*[@id=\"header-slideout--0\"]/li[3]/a")
	WebElement ic_myAccountlist;
	
	@FindBy(xpath = "//*[@id='email']")
	WebElement ic_Username;
	
	@FindBy(xpath = "//*[@id='pass']")
	WebElement ic_Password;
	
	public List<String> loginPasswordSafe(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
		String url =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnOtherModule("ic_login","loginDetails",0),"url");
		String Username =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnOtherModule("ic_login","loginDetails",0),"username");
		String Password =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnOtherModule("ic_login","loginDetails",0),"password");
		driver.navigate().to(url);
//		action.waitForPageLoaded(10);
		action.explicitWait(10000);
		ic_myAccountButton.click();
		action.explicitWait(3000);
		ic_myAccountlist.click();
		
		List<String> userCred = new ArrayList<>();
		userCred.add(Username);
		userCred.add(Password);
		action.writeText(ic_Username, Username, "Username field", test);
		action.writeText(ic_Password, Password, "Password field", test);
		action.explicitWait(3000);
		
		String safePass = action.getText(ic_Password, "Get password Encrypted ",test);
		
		if(safePass != null | safePass !=""){
			action.CompareResult("The password is "+safePass, "true", "true", test);
			action.getScreenShot(Password);
		}

			return userCred;

     }
	
	public List<String> userCreds(List<String> userCreds){
		return userCreds;
	}	

}
