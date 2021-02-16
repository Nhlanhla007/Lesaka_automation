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
	
	
	//Sourav TA17
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
  // Sourav TA19 customer info after click on Customer info 
	
	  @FindBy(xpath = "//span[contains(text(),'Account Information')]")
	  WebElement Account_Info_link;
	
	  @FindBy(xpath = "//input[@name='customer[firstname]']")
	  WebElement customerFirstname;
	  @FindBy(xpath = "//input[@name='customer[lastname]']")
	  WebElement customerLastname;
	  @FindBy(xpath = "//input[@name='customer[email]']")
	  WebElement customerEmail;
	  @FindBy(xpath = "//input[@name='customer[partner_number]']")
	  WebElement customerBPnnumber;
	  @FindBy(xpath = "//input[@name='customer[identity_number]']")
	  WebElement customerIdentityNumber;
	
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
		
		//SouravTA17
		String verifyAccFlag = input.get("verifyAccount").get(rowNumber);
		//Sourav TA19 
		String verifyMagentoDetails = input.get("VerifyAccountDetailsInMagentoBackend").get(rowNumber);
		
		
		
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
			
			if(verifyAccFlag.equalsIgnoreCase("yes")) {
				Verify_Acount_Information(test, firstName, lastName, emailAddress, identityNumber);
			}
			if(verifyMagentoDetails.equalsIgnoreCase("Yes")) {
				Magento_VerifyCustomerDetails(test ,firstName,lastName,emailAddress,identityNumber);
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
	
	//Sourav TA17
	@Step("To verify account information")
    public void Verify_Acount_Information(ExtentTest test,String expFirstName,String expLastName,String expEmailAddress, String expSAID) throws IOException{
        String ExpPage ="edit";
        Boolean accInfoOpt = action.elementExists(Account_info_option, 11);
        if(accInfoOpt==true){
            action.CompareResult("Verify account info option is present", String.valueOf(true),String.valueOf(accInfoOpt), test);
            action.clickEle(Account_info_option, "Account info link", test);
            action.waitExplicit(11);
            if(driver.getCurrentUrl().contains(ExpPage+"/")){
                action.CompareResult("Verify Account info page is opened", ExpPage,driver.getCurrentUrl().toString(), test);
               
                String ActualFirstname = action.getAttribute(Firstname, "value");
               
                String ActualLastname = action.getAttribute(Lastname, "value");
               
                action.clickEle(Change_Emailcheckbox, "Enable click email checkbox ", test);
                action.waitExplicit(5);
                String ActualEmail = action.getAttribute(Email, "value");
                action.clickEle(Change_Emailcheckbox, "Enable click email checkbox ", test);
               
                String ActualSAID = action.getAttribute(SAID, "value");
            
                System.out.println(ActualFirstname);
                action.CompareResult("Verify First Name ", expFirstName,ActualFirstname, test);
                System.out.println(ActualLastname);
                action.CompareResult("Verify Last Name ", expLastName,ActualLastname, test);
                System.out.println(ActualEmail);
                action.CompareResult("Verify Email Address ", expEmailAddress,ActualEmail, test);
                System.out.println(ActualSAID);
                action.CompareResult("Verify SA ID ", expSAID,ActualSAID, test);
            }else{
                action.CompareResult("Verify Account info page is opened", ExpPage,driver.getCurrentUrl().toString(), test);
               
            }
        }else{
            action.CompareResult("Verify account info option is present", String.valueOf(true),String.valueOf(accInfoOpt), test);
        }
       
       
        System.out.println("done");
        //action.selectExactValueFromListUsingText(elementAttr, value);
    }
	public void Magento_VerifyCustomerDetails(ExtentTest test,String expFristname,String expLastname,String expEmail,String expSAID) throws IOException{
		//replace by this Parameter while merge parameter (ExtentTest test,String expFristname,String expLastname,String expEmail,String expSAID)
		
		try {
			int loadtime=20;
			
			String expBPnumber =null;
			
			//Starts from Account information tab.
			action.waitExplicit(loadtime);
			String ActualFirstname = action.getAttribute(customerFirstname, "value");
			String ActualLastname = action.getAttribute(customerLastname, "value");
			String ActualEmail = action.getAttribute(customerEmail, "value");
			String ActualBPnumber = action.getAttribute(customerBPnnumber, "value");
			String ActualIdentityNumber= action.getAttribute(customerIdentityNumber, "value");
			
			action.CompareResult("Verify the First name of user in Magento", expFristname, ActualFirstname, test);
			action.CompareResult("Verify the Last name of user in Magento", expLastname, ActualLastname, test);
			action.CompareResult("Verify the Email of user in Magento", expEmail, ActualEmail, test);
			
			action.CompareResult("Verify the SA ID number of user in Magento", expEmail, ActualEmail, test);
			boolean FlagGenerateBPnumber=false;
			if(ActualBPnumber!=null){
				FlagGenerateBPnumber=true;
				action.CompareResult("Verify the BP number of user in Magento :", String.valueOf(true),String.valueOf(FlagGenerateBPnumber)+"-BP no : "+ActualBPnumber.toString(), test);
				
			}else{
				action.CompareResult("Verify the BP number of user in Magento :", String.valueOf(true),String.valueOf(FlagGenerateBPnumber)+"-BP no : "+ActualBPnumber.toString(), test);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			action.CompareResult("Magento_VerifyCustomerDetails method failed : "+"ERROR found as "+e.getMessage(), String.valueOf(true),String.valueOf(false), test);
		}
	}
}
