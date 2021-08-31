package ic_PageObjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class IC_validateRegistrationForm {
	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    
    public IC_validateRegistrationForm(WebDriver driver, DataTable2 dataTable2){
    	this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2=dataTable2;
    	
    }
    @FindBy(xpath = "//div[contains(@class,'my-account icon')]")
    WebElement myAccountButton;
    
    @FindBy(xpath = "//*[@class=\"register\"]")
    WebElement myAcc_createAcc;
    
    @FindBy(xpath = "//button[@title=\"Create an Account\"]")
    WebElement myAcc_RegisterButton;
    
    @FindBy(xpath = "//input[@id='firstname']")
	WebElement User_Firstname;
	@FindBy(xpath = "//input[@id='lastname']")
	WebElement User_Lastname;
	@FindBy(xpath = "//input[@id='email_address']")
	WebElement User_EmailId;
	
	@FindBy(id = "password")
	WebElement ic_password;
	
	@FindBy(id = "password-confirmation")
	WebElement ic_passwordConfirmation;
	@FindBy(xpath = "//input[@id='email']")
    WebElement Email;
    @FindBy(xpath = "//input[@id='identity_number']")
    WebElement SAID;

    @FindBy(id = "cellphone_number")
    WebElement telephoneNumber;
	
    @FindBy(xpath = "//*[@id=\"firstname-error\"]")
    WebElement firstNam_error;
    
    @FindBy(xpath = "//*[@id=\"lastname-error\"]")
    WebElement lastNam_error;
    
    @FindBy(xpath = "//*[@id=\"email_address-error\"]")
    WebElement emaiAddre_error;
    
    @FindBy(xpath = "//*[@id=\"password-error\"]")
    WebElement password_error;
    
    @FindBy(xpath = "//*[@id=\"password-confirmation-error\"]")
    WebElement passConfirm_error;
    
    @FindBy(xpath = "//*[@id=\"cellphone_number-error\"]")
    WebElement cellphone_error;
    
    @FindBy(xpath = "//div[contains(text(),\"Optional, required for TV licence validation or to view your Loan Account Balance. \")]")
    WebElement identity_error;
    
  
    public void validateRegForm(ExtentTest test) throws Exception{
    	action.waitForPageLoaded(10);
    	action.click(myAccountButton, "click on my account", test);
    	action.waitForPageLoaded(10);
    	action.click(myAcc_createAcc, "Create account", test);
    	action.waitForPageLoaded(10);
    	action.scrollElemetnToCenterOfView(myAcc_RegisterButton, "scroll to the button", test);
    	action.click(myAcc_RegisterButton, "click on register", test);
    	action.scrollElemetnToCenterOfView(User_Firstname, "the beginning of the page", test);
    	if(action.elementExistWelcome(User_Firstname, 2, "Check if the Firstname textbox is available", test)){
    		String firstN = action.getText(firstNam_error, " Firstname error message ", test);
    		action.CompareResult("The First Name error messages", "This is a required field.", firstN, test);
    	}
    	
    	 if(action.elementExistWelcome(User_Lastname, 2, "Check if the Lastname textbox is available", test)){
    		String lastN = action.getText(lastNam_error, " Lastname error message  ", test);
    		action.CompareResult("The Last Name error messages", "This is a required field.", lastN, test);
    	}
    	
    	 if(action.elementExistWelcome(User_EmailId, 2, "Check if the Email Addresstextbox is available", test)){
    		String emailAdd = action.getText(emaiAddre_error, " Email address error message", test);
    		action.CompareResult("The Email Address error messages", "This is a required field.", emailAdd, test);
    	}
    	
    	if(action.elementExistWelcome(ic_password, 2, "Check if the Password textbox is available", test)){
    		String passW = action.getText(password_error, "Password error message ", test);
    		action.CompareResult("The Password error messages", "This is a required field.", passW, test);
    	}
    	if(action.elementExistWelcome(ic_passwordConfirmation, 2, "Check if the Password Confirmation textbox is available", test)){
    		String confirmP = action.getText(passConfirm_error, "Password Confirmation error message ", test);
    		action.CompareResult("The Password Confirmation error message", "This is a required field.", confirmP, test);
    	}
    	if(action.elementExistWelcome(telephoneNumber, 2, "Check if the Telephone textbox is available", test)){
    		String cellP = action.getText(cellphone_error, "Telephone error message", test);
    		action.CompareResult("The Telephone error message", "This is a required field.", cellP, test);
    	}
    	action.scrollElemetnToCenterOfView(SAID, "scroll to identity", test);
    	if(action.elementExistWelcome(SAID, 2, "Check if the Identity textbox is available", test)){
    		String identityN = action.getText(identity_error, "Identity error message ", test);
    		String messageE = "Optional, required for TV licence validation or to view your Loan Account Balance.";
    		action.CompareResult("The Identity Error Message", messageE, identityN, test);
    	}
    	 else {
			throw new Exception("The error message didn't appear");
    	
    	}
    	
    	
    }

}
