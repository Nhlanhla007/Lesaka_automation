package evs_PageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import Logger.Log;
import utils.Action;
import utils.DataTable2;

public class EVS_Subscriber_Newsletter_ValidEmailaddress {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;

	public EVS_Subscriber_Newsletter_ValidEmailaddress(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;
	}

	static Logger logger = Log.getLogData(Action.class.getSimpleName());

	@FindBy(xpath = "//input[@id='newsletter']")
	WebElement newsLetterEmailTextBox;

	@FindBy(xpath = "//button[@aria-label='Subscribe']")
	WebElement clickSubscribeButton;

	@FindBy(xpath = "//div[contains(text(),'Thank you for your subscription.')]")
	WebElement newLetterSuccessMessage;

	public void SubscribeNewsletter(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber)
			throws IOException {
		String uniqueEmailID = dataTable2.getValueOnCurrentModule("ExpEmail");
		int waitTime = Integer.parseInt(dataTable2.getValueOnCurrentModule("WaitTime"));
		String actualMessage = null;
		boolean checkflag = false;
		System.out.println("ExpEmailId : " + uniqueEmailID);
		logger.info("Entered unique email ID:"+uniqueEmailID);
		
		action.scrollToElement(newsLetterEmailTextBox, "Scrolling to News letter Field");
		action.writeText(newsLetterEmailTextBox, uniqueEmailID, "Newsletter EmailID field", test);
		action.click(clickSubscribeButton, "Subscribe button", test);
		boolean errorMesg = action.isElementPresent(newLetterSuccessMessage);
		if (errorMesg) {
			actualMessage = action.getText(newLetterSuccessMessage, "Newsletter Subscribe Message", test);
			checkflag = true;
		}
		if (checkflag) {
			action.CompareResult("Subscription Message", "Thank you for your subscription.", actualMessage,
					test);
		} else {
			action.CompareResult("Subscription Message", "true", String.valueOf(checkflag), test);
		}

	}

}