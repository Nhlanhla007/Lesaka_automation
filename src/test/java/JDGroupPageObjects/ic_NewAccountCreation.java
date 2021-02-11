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
	
	@FindBy(className = "my-account")
	WebElement ic_myAccountButton;

	@FindBy(xpath = "//a[contains(text(),'Create an Account')]")
	WebElement ic_createAccount;
	
	@FindBy(id = "password")
	WebElement ic_password;
	
	@FindBy(id = "password-confirmation")
	WebElement ic_passwordConfirmation;
	
	
	// user details information for new user in IC
	@FindBy(xpath = "//input[@id='firstname']")
	WebElement User_Firstname;
	@FindBy(xpath = "//input[@id='lastname']")
	WebElement User_Lastname;
	@FindBy(xpath = "//input[@id='email_address']")
	WebElement User_EmailId;
	
	@FindBy(xpath = "//span[contains(text(),'Sign Up for Newsletter')]")
	WebElement newsLetter;
	
	@FindBy(id = "taxvat")
	WebElement taxVatNumber;
	
	@FindBy(xpath = "//span[contains(text(),'South African ID')]")
	WebElement User_SAIDbtn;
	
	@FindBy(xpath = "//body[1]/div[1]/main[1]/div[2]/div[1]/form[1]/fieldset[1]/div[5]/div[2]/label[1]/span[1]")
	WebElement User_Passportbtn;
	
	@FindBy(xpath = "//input[@id='identity_number']")
	WebElement User_SAID;
	
	@FindBy(name = "passport_number")
	WebElement User_Passport;
	
	@FindBy(xpath = "//input[@id='password']")
	WebElement User_Password;
	@FindBy(xpath = "//input[@id='password-confirmation']")
	WebElement User_ConfirmPassword;
	@FindBy(xpath = "// *[@type='submit']/span[contains(text(),'Create an Account')]")
	WebElement CreateAccountBtn;
	
	@FindBy(xpath = "//div[@id='password-confirmation-error']")
	WebElement enterMatchingPassword;
	
	@FindBy(id = "identity_number-error")
	WebElement identityNumberError;
	
	@FindBy(xpath = "//header/div[3]/div[2]/div[1]/div[1]/div[1]")
	WebElement existingAccountError;
	
	@Step("Click on create account")
	public void ic_NavigateToCreateAccount(ExtentTest test) {
		try {
			action.click(ic_myAccountButton, "Navigate to accountTab",test);
			action.click(ic_createAccount, "Navigate to create Account",test);
			action.checkIfPageIsLoadedByURL("/customer/account/create/", "validate account page is loaded",test);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Step("Verify IC Home page is opened sucessfully")
	public  void ic_verifyICHomepage(){

	}	

	@Step("verify create new  account  page")
	public  void ic_VerifyICAccountcreatePage(){

	}	
	@Step("verify create new  account  page")
	public void EnterNewUserDetails(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
		/*
		 * System.out.println("HI"); try { String
		 * userFirstname=input.get("User_Firstname").get(rowNumber); String userLastname
		 * = input.get("User_Lastname").get(rowNumber); String emailid =
		 * input.get("Email").get(rowNumber); action.writeText(User_Firstname,
		 * userFirstname, "Enter Fistname feild value :"+userFirstname,test);
		 * action.writeText(User_Lastname, userLastname,
		 * "Enter Lastname feild value :"+userFirstname,test);
		 * action.writeText(User_EmailId, emailid,
		 * "emailId feild value :"+emailid,test); } catch (Exception e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
			
		
	
		
	}
	@Step("Create account")
	public  void accountCreation(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
		String firstName = input.get("firstName").get(rowNumber);
		String lastName = input.get("lastName").get(rowNumber);
		String emailAddress = input.get("emailAddress").get(rowNumber);
		String password = input.get("password").get(rowNumber);
		String confirmPassword = input.get("passwordConfirmation").get(rowNumber);
		String identityType = input.get("identityType").get(rowNumber);
		String identityNumber = input.get("identityNumber/passport").get(rowNumber);
		String selectNewsLetter = input.get("newsletter").get(rowNumber);
		String taxVatNumbe = input.get("vatNumber").get(rowNumber);
		
		String passwordValidation = input.get("validatePassword").get(rowNumber);
		String saIDvalidateIncorrectID = input.get("validateIncorrectID").get(rowNumber);
		String saIDvalidateIDWithLessDigits = input.get("validateIDWithLessDigits").get(rowNumber);
		String saIDvalidateIDWithMoreDigits = input.get("validateIDWithMoreDigits").get(rowNumber);
		
		String existingAccountValidation =input.get("validateExistingAccount").get(rowNumber);		
		try {
			ic_NavigateToCreateAccount(test);
			
			Thread.sleep(10000);
			
			action.writeText(User_Firstname, firstName, "First name", test);
			action.writeText(User_Lastname, lastName, "Last Name", test);
			action.writeText(User_EmailId, emailAddress, "Email", test);
			action.writeText(taxVatNumber, taxVatNumbe, "Tax/Vat", test);
			action.writeText(User_Password, password, "Password", test);
			System.out.println(selectNewsLetter);
			if(selectNewsLetter.equalsIgnoreCase("YES")) {
				System.out.println("Inside newslwtter");
			action.click(newsLetter, "News letter", test);
			}
			//action.writeText(User_ConfirmPassword, confirmPassword, "Confirm password", test);
			System.out.println(identityType);
			
			if(identityType.equalsIgnoreCase("ID")) {
				System.out.println("Inside ID");
			action.click(User_SAIDbtn, "Identity type: ID", test);
			action.writeText(User_SAID, identityNumber, "ID/Passport number", test);
			}else if(identityType.equalsIgnoreCase("Passport")){
				System.out.println("Inside passport");
				action.click(User_Passportbtn, "Identity type: Passport", test);
				action.writeText(User_Passport, identityNumber, "ID/Passport number", test);
			}

			if (saIDvalidateIncorrectID.equalsIgnoreCase("yes")) {
				System.out.println("Enters validate with incorrect digits");
				String identityNumberIncorrect = "7657674565563";
				User_SAID.clear();
				action.writeText(User_SAID, identityNumberIncorrect, "ID/Passport number", test);
				ic_VerifySAIDLimit(identityNumber, test);
			}
			if (saIDvalidateIDWithLessDigits.equalsIgnoreCase("yes")) {
				System.out.println("Enters validate with less digits");
				String identityWithLess = identityNumber.substring(0, 10);
				User_SAID.clear();
				action.writeText(User_SAID, identityWithLess, "ID/Passport number", test);
				ic_VerifySAIDLimit(identityWithLess, test);
			}
			if (saIDvalidateIDWithMoreDigits.equalsIgnoreCase("yes")) {
				System.out.println("Enters validate with more digits");
				String identityWithMore = identityNumber.concat("543");
				User_SAID.clear();
				action.writeText(User_SAID, identityWithMore, "ID/Passport number", test);
				ic_VerifySAIDLimit(identityWithMore, test);

			}
			
			
			
			if(passwordValidation.equalsIgnoreCase("yes")) {
				confirmPassword = ic_VerifyPasswordcanDiffer(confirmPassword);
				action.writeText(User_ConfirmPassword, confirmPassword, "Confirm password", test);
				action.click(CreateAccountBtn, "Create account", test);
				action.elementExistsPopUpMessage(enterMatchingPassword, 4000, "Check password", test);
			}else {
				action.writeText(User_ConfirmPassword, confirmPassword, "Confirm password", test);
				action.click(CreateAccountBtn, "Create account", test);
			}
			
			if(existingAccountValidation.equalsIgnoreCase("yes")) {
				action.elementExistsPopUpMessage(existingAccountError, 4000, existingAccountError.getText(), test);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
		/*
		 * boolean resFlag = false; String DecionTocreateNewAcc =
		 * input.get("DecionTocreateNewAcc").get(rowNumber); String Password =
		 * input.get("Password").get(rowNumber); String ConfirmPassword =
		 * input.get("Confirm_password").get(rowNumber);
		 * 
		 * String IdentyType = input.get("IdentyType").get(rowNumber); String
		 * IdentyNumber = input.get("SAID").get(rowNumber);
		 * 
		 * if (DecionTocreateNewAcc.toString().toUpperCase() == "YES") { switch
		 * (IdentyType.toUpperCase()) { case "SAID": { action.clickEle(User_SAIDbtn,
		 * "SAID radio button click", test); action.writeText(User_SAID, IdentyNumber,
		 * "SAID feild value :" + IdentyNumber, test); } action.writeText(User_Password,
		 * Password, "password feild value :" + Password, test);
		 * action.writeText(User_ConfirmPassword, ConfirmPassword,
		 * "ConfirmPassword feild value :" + ConfirmPassword, test);
		 * 
		 * action.clickEle(CreateAccountBtn, "CreateAccountBtn click", test);
		 * 
		 * } }
		 */
	}
	@Step("Check duplicate usercreate is possible")
	public  void ic_VerifyDuplicateUser_created(){
     // call ic_EnternewUserDetails proceed and check the pop-up
	}
	@Step("Check SAID can be lesser than 13 digit")
	public  void ic_VerifySAIDLimit(String saID,ExtentTest test){

		try {
			if (saID.length() < 13) {
				action.click(CreateAccountBtn, "Create account", test);
				Thread.sleep(15000);
				action.elementExistsPopUpMessage(identityNumberError, 4000, identityNumberError.getText(), test);
			} else if (saID.length() > 13) {
				System.out.println("Enters more than 13 digits");
				action.click(CreateAccountBtn, "Create account", test);
				Thread.sleep(15000);
				action.elementExistsPopUpMessage(identityNumberError, 4000, identityNumberError.getText(), test);
			} else {
				action.click(CreateAccountBtn, "Create account", test);
				Thread.sleep(15000);
				action.elementExistsPopUpMessage(identityNumberError, 4000, identityNumberError.getText(), test);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Step("Check Password can be different")
	public  String ic_VerifyPasswordcanDiffer(String passwordToChange){
		passwordToChange += "fail";
		return passwordToChange;
	}
	/*
	 * public String alterId(String saId) { saId = saId. return; }
	 */
}
