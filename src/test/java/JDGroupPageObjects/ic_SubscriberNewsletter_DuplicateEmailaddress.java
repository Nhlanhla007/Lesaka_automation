package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class ic_SubscriberNewsletter_DuplicateEmailaddress {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	public ic_SubscriberNewsletter_DuplicateEmailaddress(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	}
	@FindBy(xpath = "//input[@id='newsletter']")
	WebElement Newsletter_EmailID;
	
	@FindBy(xpath = "//span[contains(text(),'Subscribe')]")
	WebElement Newsletter_SubscribeBtn;
	@FindBy(xpath = "//div[contains(text(),'This email address is already subscribed.')]")
	WebElement Newsletter_SubscribeMsg;
	
	public void SubscribeNewsletter_DuplicateEmail(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
		String ExpEmailId = dataTable2.getValueOnCurrentModule("ExpEmail");
		int waitTime =Integer.parseInt(dataTable2.getValueOnCurrentModule("WaitTime")); 
		String ExpMessage =null;
		boolean checkflag = false;
		System.out.println("ExpEmailId : "+ExpEmailId);
		action.writeText(Newsletter_EmailID, ExpEmailId, "Newsletter EmailID field", test);
		action.click(Newsletter_SubscribeBtn, "Subscribe button", test);
		if(action.elementExists(Newsletter_SubscribeMsg, waitTime)){
			ExpMessage = action.getText(Newsletter_SubscribeMsg, "Newsletter Subscribe Message");
			checkflag=true;
		}
		if(ExpMessage!=null){
			action.CompareResult("News letter subcription of Duplicate Email ID ", "true", String.valueOf(checkflag), test);
		}else{
			action.CompareResult("News letter subcription of Duplicate Email ID", "true",  String.valueOf(checkflag), test);
		}
		
		
	}
	
}
