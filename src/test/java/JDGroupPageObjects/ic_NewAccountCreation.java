package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import io.qameta.allure.Step;
import utils.Action;

public class ic_NewAccountCreation {
	WebDriver driver;
	Action action;
	public ic_NewAccountCreation(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		
	}
	// user details information for new user in IC
	@FindBy(xpath = "//input[@id='firstname']")
	WebElement User_Firstname;
	@FindBy(xpath = "//input[@id='lastname']")
	WebElement User_Lastname;
	@FindBy(xpath = "//input[@id='email_address']")
	WebElement User_EmailId;
	@FindBy(xpath = "//span[contains(text(),'South African ID')]")
	WebElement User_SAIDbtn;
	@FindBy(xpath = "//input[@id='identity_number']")
	WebElement User_SAID;
	@FindBy(xpath = "//input[@id='password']")
	WebElement User_Password;
	@FindBy(xpath = "//input[@id='password-confirmation']")
	WebElement User_ConfirmPassword;
	@FindBy(xpath = "// *[@type='submit']/span[contains(text(),'Create an Account')]")
	WebElement CreateAccountBtn;
	
	
	@Step("Verify IC Home page is opened sucessfully")
	public  void ic_verifyICHomepage(){

	}	
	@Step("Click on create account")
	public  void ic_NavigateToCreateAccount(){

	}	
	@Step("verify create new  account  page")
	public  void ic_VerifyICAccountcreatePage(){

	}	
	@Step("verify create new  account  page")
	public void EnterNewUserDetails(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
			System.out.println("HI");
			try {
				String userFirstname=input.get("User_Firstname").get(rowNumber);
				String userLastname = input.get("User_Lastname").get(rowNumber);
				String emailid = input.get("Email").get(rowNumber);
				action.writeText(User_Firstname, userFirstname, "Enter Fistname feild value :"+userFirstname,test);
				action.writeText(User_Lastname, userLastname, "Enter Lastname feild value :"+userFirstname,test);
				action.writeText(User_EmailId, emailid, "emailId feild value :"+emailid,test);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
		
	
		
	}
	@Step("Verify new account is created")
	public  void AccountCreationUserDetails(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
		boolean resFlag =false;
		String DecionTocreateNewAcc =input.get("DecionTocreateNewAcc").get(rowNumber);
		String Password = input.get("Password").get(rowNumber);
		String ConfirmPassword = input.get("Confirm_password").get(rowNumber);
		
		String IdentyType = input.get("IdentyType").get(rowNumber);
		String IdentyNumber = input.get("SAID").get(rowNumber);
		
		
		if (DecionTocreateNewAcc.toString().toUpperCase() =="YES"){
			switch (IdentyType.toUpperCase()){
				case "SAID":{
					action.clickEle(User_SAIDbtn, "SAID radio button click",test);
					action.writeText(User_SAID, IdentyNumber, "SAID feild value :"+IdentyNumber,test);
				}
				action.writeText(User_Password, Password, "password feild value :"+Password,test); 
				action.writeText(User_ConfirmPassword, ConfirmPassword, "ConfirmPassword feild value :"+ConfirmPassword,test); 
				
				action.clickEle(CreateAccountBtn, "CreateAccountBtn click",test);
				
				
			}
		}
	
	}
	@Step("Check duplicate usercreate is possible")
	public  void ic_VerifyDuplicateUser_created(){
     // call ic_EnternewUserDetails proceed and check the pop-up
	}
	@Step("Check SAID can be lesser than 13 digit")
	public  void ic_VerifySAIDLimit(){
		// call ic_EnternewUserDetails proceed and said field < 13 digit and 
		

	}
	@Step("Check Password can be different")
	public  void ic_VerifyPasswordcanDiffer(){
		// call ic_EnternewUserDetails proceed and ic_VerifyPasswordcanDiffer
	}
	
	
}
