package evs_PageObjects;

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

public class EVS_SubscriberNewsletter_DuplicateEmailaddress {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;

	public EVS_SubscriberNewsletter_DuplicateEmailaddress(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;
	}

	@FindBy(xpath = "//input[@id='newsletter']")
	WebElement newsLetterEmailTextBox;

	@FindBy(xpath = "//button[@aria-label='Subscribe']")
	WebElement clickSubscribeButton;

	@FindBy(xpath = "//div[contains(text(),'This email address is already subscribed.')]")
	WebElement newLetterDuplicateMessage;

	public void SubscribeNewsletter_DuplicateEmail(HashMap<String, ArrayList<String>> input, ExtentTest test,
			int rowNumber) throws IOException {
		String ExpEmailId = dataTable2.getValueOnCurrentModule("ExpEmail");
		String actualMessage = null;
		boolean checkflag = false;
		System.out.println("ExpEmailId : " + ExpEmailId);
		action.scrollToElement(newsLetterEmailTextBox, "Scrolling to News letter Field");
		action.writeText(newsLetterEmailTextBox, ExpEmailId, "Newsletter EmailID field", test);
		action.click(clickSubscribeButton, "Subscribe button", test);
		boolean errorMesg = action.isElementPresent(newLetterDuplicateMessage);
		if (errorMesg) {
			actualMessage = action.getText(newLetterDuplicateMessage, "Newsletter Subscribe Message", test);
			checkflag = true;
		}
		if (checkflag) {
			action.CompareResult("Duplicate Email Message", "This email address is already subscribed.", actualMessage,
					test);
		} else {
			action.CompareResult("Duplicate Email Message", "true", String.valueOf(checkflag), test);

		}

	}
}
