package SRS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.Base64Decoding;
import utils.DataTable2;

public class srs_Login {
	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
	Base64Decoding decodePassword;
    public srs_Login(WebDriver driver,DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2=dataTable2;
		decodePassword = new Base64Decoding();
    }
    @FindBy(xpath = "//input[@id='USERNAME_FIELD-inner']")
	 WebElement Uname;
    @FindBy(xpath = "//input[@id='PASSWORD_FIELD-inner']")
	 WebElement Pass;
    @FindBy(xpath = "//button[@id='LOGIN_LINK']")
	 WebElement LogOn;
    @FindBy(xpath = "//*[@id='store']")
	 WebElement Store;
    @FindBy(xpath = "/html/body/form[3]/p/table/tbody/tr[2]/td[2]/table/tbody/tr[1]/td/input")
	 WebElement StoreLogon;
    @FindBy(xpath = "/html/body/table/tbody/tr[1]/td[2]/table/tbody/tr[1]/td[3]/a/strong[contains(text(),'Sales Order')]")
	 WebElement SalerOrder;
    
    public void SRS_Login(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws Exception {
    	boolean CheckSRSlogin = false;
    	String URL = dataTable2.getValueOnCurrentModule("URL");//input.get("URL").get(rowNumber);
    	String UserName = dataTable2.getValueOnCurrentModule("Username");//input.get("Username").get(rowNumber);
    	String Password = dataTable2.getValueOnCurrentModule("Password");//input.get("Password").get(rowNumber);
		Password = decodePassword.decode(Password);
		String ExpectedKeysonFetchPayload = "";
    	int WaitTime=21;
    	action.navigateToURL(URL);
        CheckSRSlogin = LoginSRS(URL, UserName, Password,WaitTime,test);
    	action.CompareResult("Login to SRS application is success", "true", String.valueOf(CheckSRSlogin), test);
    	
    }
    public boolean LoginSRS(String URL,String Usrname,String PassWrd,int TimeTowait,ExtentTest test) throws IOException{
    	boolean FlagSRSlogin = false;
		action.writeText(Uname, Usrname, "UserName", test);
		action.writeText(Pass, PassWrd, "Password", test);
		action.click(LogOn, "Log On SRS", test);
		action.waitForPageLoaded(TimeTowait);
		if(action.elementExists(Store, TimeTowait)){
			FlagSRSlogin=true;
		}
		return FlagSRSlogin;
    }
   
}
