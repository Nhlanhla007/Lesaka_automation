package ic_PageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.aventstack.extentreports.ExtentTest;
import utils.Action;

public class ic_AccountInformation {
	    WebDriver driver;
	    Action action;
		LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> workbook =null;
	    public ic_AccountInformation(WebDriver driver, LinkedHashMap<String, LinkedHashMap<String, ArrayList<String>>> workbook) {
	        this.driver = driver;
	        this.workbook = workbook;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);

	    }

	    @FindBy(xpath = "//*[@id='account-nav']/ul[@class='nav items']/li/a[contains(text(),'Account Information')]")
		WebElement Account_info_option;
	    
	    
	    @FindBy(xpath = "//input[@id='firstname']")
		WebElement Firstname;
	    @FindBy(xpath = "//input[@id='lastname']")
		WebElement Lastname;
	    
	    @FindBy(xpath = "//*[@class='field choice']/label[@for='change-email']")
		WebElement Change_Emailcheckbox;

	    @FindBy(xpath = "//input[@id='email']")
		WebElement Email;
	    @FindBy(xpath = "//input[@id='identity_number']")
		WebElement SAID;
	    
	    public void Verify_Acount_Information(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws IOException, InterruptedException {
			String ExpPage ="edit";
			String ExpFirstname=input.get("firstName").get(rowNumber);
			String ExpLastname=input.get("lastName").get(rowNumber);
			String ExpEmail=input.get("emailAddress").get(rowNumber);
			String ExpSAID=input.get("identityNumber/passport").get(rowNumber);
			Boolean accInfoOpt = action.waitUntilElementIsDisplayed(Account_info_option, 10);
			if(accInfoOpt==true){
				action.CompareResult("Verify account info option is present", String.valueOf(true),String.valueOf(accInfoOpt), test);
				action.clickEle(Account_info_option, "Account info link", test);
				action.waitForPageLoaded(10);
				if(driver.getCurrentUrl().contains(ExpPage+"/")){
					action.CompareResult("Verify Account info page is opened", ExpPage,driver.getCurrentUrl().toString(), test);
					String ActualFirstname = action.getAttribute(Firstname, "value");
					String ActualLastname = action.getAttribute(Lastname, "value");
					action.clickEle(Change_Emailcheckbox, "Enable click email checkbox ", test);
					String ActualEmail = action.getAttribute(Email, "value");
					action.clickEle(Change_Emailcheckbox, "Enable click email checkbox ", test);
					String ActualSAID = action.getAttribute(SAID, "value");
	                action.CompareResult("Verify First Name feild", ExpFirstname,ActualFirstname, test);
	                action.CompareResult("Verify Last Name feild", ExpLastname,ActualLastname, test);
	                action.CompareResult("Verify Email Address feild ", ExpEmail,ActualEmail, test);
	                action.CompareResult("Verify SA ID feild ", ExpSAID,ActualSAID, test);
				}else{
					action.CompareResult("account info page is opened", ExpPage,driver.getCurrentUrl().toString(), test);
					
				}
			}else{
				action.CompareResult("account info option is present", String.valueOf(true),String.valueOf(accInfoOpt), test);
			}

		}

}
