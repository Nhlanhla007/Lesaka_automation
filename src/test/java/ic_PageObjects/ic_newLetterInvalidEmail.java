package ic_PageObjects;

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

public class ic_newLetterInvalidEmail {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	
	public ic_newLetterInvalidEmail(WebDriver driver,DataTable2 dataTable2){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
		
	}
	@FindBy(xpath = "//input[@name='email']")
	WebElement icNewsLetterEmail;
	
	//button[@ title="Subscribe"]
	@FindBy(xpath = "//button[@title=\"Subscribe\"]")
	WebElement icClickSubscribe;
	
	@FindBy(xpath = "//*[@id=\"newsletter-error\"]")
	WebElement icNewsLetterErrorMessage;
	
	public void ic_NewsLetterInvalidEmail(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException {
		String newsLetterEmail =dataTable2.getValueOnCurrentModule("LetterEmail");
		
		action.scrollToElement(icNewsLetterEmail, "Scrol to news letter subscribe");
		action.clear(icNewsLetterEmail, "Clear the email");
		action.writeText(icNewsLetterEmail, newsLetterEmail, "enter an incorrect email", test);
		action.click(icClickSubscribe, "Subscribe", test);
		action.CompareResult("Error message", "Please enter a valid email address (Ex: johndoe@domain.com).", icNewsLetterErrorMessage.getText(), test);
	}

	

}
