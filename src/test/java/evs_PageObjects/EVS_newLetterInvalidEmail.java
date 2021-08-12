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

public class EVS_newLetterInvalidEmail {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;

	public EVS_newLetterInvalidEmail(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;

	}

	@FindBy(xpath = "//input[@id='newsletter']")
	WebElement newsLetterEmailTextBox;

	@FindBy(xpath = "//button[@aria-label='Subscribe']")
	WebElement clickSubscribeButton;

	@FindBy(xpath = "//div[@id='newsletter-error']")
	WebElement newsLetterErrorMessage;

	public void evs_NewsLetterInvalidEmail(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber)
			throws IOException {
		String newsLetterEmail = dataTable2.getValueOnCurrentModule("LetterEmail");
		action.explicitWait(5000);
		action.scrollToElement(newsLetterEmailTextBox, "Scroll to news letter subscribtion");
		action.explicitWait(2000);
		action.clear(newsLetterEmailTextBox, "Clear the email text box");
		action.writeText(newsLetterEmailTextBox, newsLetterEmail, "Enter an incorrect email", test);
		action.click(clickSubscribeButton, "Subscribe", test);
		action.explicitWait(5000);
		action.CompareResult("Error message", "Please enter a valid email address (Ex: johndoe@domain.com).",
				newsLetterErrorMessage.getText(), test);
	}

}
