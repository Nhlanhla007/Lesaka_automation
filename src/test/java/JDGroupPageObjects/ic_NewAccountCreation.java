package JDGroupPageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

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
	public  void ic_EnternewUserDetails(String userFirstname,String userLastname,String emailid,String Password,String IdentyType,String IdentyNumber){
		/*action.writeText(User_Firstname, userFirstname, "Enter Fistname feild value :"+userFirstname);
		action.writeText(User_Lastname, userLastname, "Enter Lastname feild value :"+userFirstname);
		action.writeText(User_EmailId, emailid, "Enter emailId feild value :"+emailid); 
		action.click(User_SAIDbtn, "SAID radio button click");
		action.writeText(User_SAID, emailid, "Enter emailId feild value :"+emailid);
		/*


	@FindBy(xpath = "//input[@id='identity_number']")
	WebElement User_SAID;
	@FindBy(xpath = "//input[@id='password']")
	WebElement User_Password;
	@FindBy(xpath = "//input[@id='password-confirmation']")
	WebElement User_ConfirmPassword;
	@FindBy(xpath = "// *[@type='submit']/span[contains(text(),'Create an Account')]")
	WebElement CreateAccountBtn;
	
		 */
	}
	@Step("Verify new account is created")
	public  void ic_VerifyNewaccountCreated(){

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
