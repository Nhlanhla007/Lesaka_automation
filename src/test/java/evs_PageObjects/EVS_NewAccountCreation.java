package evs_PageObjects;

import com.aventstack.extentreports.ExtentTest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.log4j.Logger;
import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.aventstack.extentreports.ExtentTest;
import Logger.Log;
import utils.Action;
import utils.ConfigFileReader;
import utils.DataTable2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EVS_NewAccountCreation {
	WebDriver driver;
	Action action;

	public EVS_NewAccountCreation(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);

	}

	static Logger logger = Log.getLogData(Action.class.getSimpleName());

	@FindBy(className = "my-account")
	WebElement evs_myAccountButton;

	@FindBy(xpath = "//a[contains(text(),'Create an Account')]")
	WebElement evs_createAccount;

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

	@FindBy(xpath = "//input[@id='passport_number']")
	WebElement passport;

	@FindBy(xpath = "//span[contains(text(),'South African ID')]")
	WebElement User_SAIDbtn;

	@FindBy(xpath = "//*[@id=\"switcher--passport-field\"]/following-sibling::label/span")
	WebElement User_Passportbtn;

	@FindBy(xpath = "//input[@id='identity_number']")
	WebElement User_SAID;

	@FindBy(name = "passport_number")
	WebElement User_Passport;

	@FindBy(xpath = "//input[@id='password']")
	WebElement User_Password;

	@FindBy(xpath = "//input[@id='password-confirmation']")
	WebElement User_ConfirmPassword;

	// @FindBy(xpath = "// *[@type='submit']/span[contains(text(),'Create an
	// Account')]")
	@FindBy(xpath = "// *[@type='submit']/span[contains(text(),'Register')]")
	WebElement CreateAccountBtn;

	@FindBy(xpath = "//div[@id='password-confirmation-error']")
	WebElement enterMatchingPassword;

	@FindBy(id = "identity_number-error")
	WebElement identityNumberError;

	@FindBy(xpath = "//body/div[2]/main[1]/div[1]/div[2]/div[1]/div[1]/div[1]")
	WebElement existingAccountError;

	@FindBy(xpath = "//*[@id='account-nav']/ul[@class='nav items']/li/a[contains(text(),'Account Information')]")
	WebElement Account_info_option;

	// TA31 Added the My Account click in evs AccountDetails
	@FindBy(xpath = "//*[@id=\"account-nav\"]/ul/li[1]/a")
	WebElement myAccountOption;

	@FindBy(xpath = "//*[@id=\"maincontent\"]/div/div[2]/div[2]/div[2]/div[2]/div[1]/p")
	WebElement findNewsLetterStatus;

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

	@FindBy(id = "cellphone_number")
	WebElement telephoneNumber;

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

	public void evs_NavigateToCreateAccount(ExtentTest test) {
		try {
			action.click(evs_myAccountButton, "Navigate to accountTab", test);
			action.click(evs_createAccount, "Navigate to create Account", test);
			action.checkIfPageIsLoadedByURL("/customer/account/create/", "validate account page is loaded", test);
		} catch (IOException e) {
			logger.info(e.getMessage());
			ExtentTest node = test.createNode("Click on create account");
			node.fail(e.getMessage());
		}
	}

	public void accountCreation(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber)
			throws Exception {
		driver.navigate().to(ConfigFileReader.getPropertyVal("EVS_URL"));
		action.explicitWait(3000);
		String firstName = input.get("firstName").get(rowNumber);
		String lastName = input.get("lastName").get(rowNumber);
		String emailAddress = input.get("emailAddress").get(rowNumber);
		String password = input.get("password").get(rowNumber);
		String confirmPassword = input.get("passwordConfirmation").get(rowNumber);
		String identityType = input.get("identityType").get(rowNumber);
		String identityNumber = input.get("identityNumber/passport").get(rowNumber);
		String selectNewsLetter = input.get("newsletter").get(rowNumber);
		String taxVatNumbe = input.get("vatNumber").get(rowNumber);
		String telephone = input.get("Telephone").get(rowNumber);
		// Added flag for VAT number status check TA31
		String tavVatNumberFlagStatus = input.get("vatNumberFlag").get(rowNumber);
		String passwordValidation = input.get("validatePassword").get(rowNumber);
		String saIDvalidateIncorrectID = input.get("validateIncorrectID").get(rowNumber);
		String saIDvalidateIDWithLessDigits = input.get("validateIDWithLessDigits").get(rowNumber);
		String saIDvalidateIDWithMoreDigits = input.get("validateIDWithMoreDigits").get(rowNumber);
		String existingAccountValidation = input.get("validateExistingAccount").get(rowNumber);

		// try {
		evs_NavigateToCreateAccount(test);

		action.explicitWait(5000);

		action.writeText(User_Firstname, firstName, "First name", test);
		action.writeText(User_Lastname, lastName, "Last Name", test);
		action.writeText(User_EmailId, emailAddress, "Email", test);
		action.writeText(telephoneNumber, telephone, "Telephone", test);
		// action.writeText(taxVatNumber, taxVatNumbe, "Tax/Vat", test);
		action.writeText(User_Password, password, "Password", test);

		if (selectNewsLetter.equalsIgnoreCase("YES")) {
			System.out.println("Inside newslwtter");
			action.click(newsLetter, "News letter", test);
		}

		if (identityType.equalsIgnoreCase("ID")) {
			// action.click(User_SAIDbtn, "Identity type: ID", test);
			action.writeText(User_SAID, identityNumber, "ID/Passport number", test);
		} else if (identityType.equalsIgnoreCase("Passport")) {
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

		if (passwordValidation.equalsIgnoreCase("yes")) {
			confirmPassword = ic_VerifyPasswordcanDiffer(confirmPassword);
			action.writeText(User_ConfirmPassword, confirmPassword, "Confirm password", test);
			action.click(CreateAccountBtn, "Create account", test);
			action.elementExistWelcome(enterMatchingPassword, 4000, "Check password", test);
		} else {
			action.writeText(User_ConfirmPassword, confirmPassword, "Confirm password", test);
			action.click(CreateAccountBtn, "Create account", test);
		}

		if (existingAccountValidation.equalsIgnoreCase("yes")) {
			action.explicitWait(8000);
			action.elementExistWelcome(existingAccountError, 6, existingAccountError.getText(), test);
		}

		if (!(saIDvalidateIncorrectID.equalsIgnoreCase("yes") | saIDvalidateIDWithLessDigits.equalsIgnoreCase("yes")
				| saIDvalidateIDWithMoreDigits.equalsIgnoreCase("yes") | passwordValidation.equalsIgnoreCase("yes")
				| existingAccountValidation.equalsIgnoreCase("yes"))) {
			Verify_Acount_Information(test, firstName, lastName, emailAddress, identityNumber, taxVatNumbe,
					tavVatNumberFlagStatus, identityType, selectNewsLetter);
		}
		/*
		 * if(verifyMagentoDetails.equalsIgnoreCase("Yes")) {
		 * Magento_VerifyCustomerDetails(test
		 * ,firstName,lastName,emailAddress,identityNumber); }
		 */
		// } catch (Exception e) {
		// logger.info(e.getMessage());
		//
		// }

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
		 * (IdentyType.toUpperCase()) { case "SAID": {
		 * action.clickEle(User_SAIDbtn, "SAID radio button click", test);
		 * action.writeText(User_SAID, IdentyNumber, "SAID feild value :" +
		 * IdentyNumber, test); } action.writeText(User_Password, Password,
		 * "password feild value :" + Password, test);
		 * action.writeText(User_ConfirmPassword, ConfirmPassword,
		 * "ConfirmPassword feild value :" + ConfirmPassword, test);
		 * 
		 * action.clickEle(CreateAccountBtn, "CreateAccountBtn click", test);
		 * 
		 * } }
		 */
	}

	public void ic_VerifyDuplicateUser_created() {
		// call ic_EnternewUserDetails proceed and check the pop-up
	}

	// TA15 ID VALIDATION
	public void ic_VerifySAIDLimit(String saID, ExtentTest test) {

		try {
			if (saID.length() < 13) {
				action.click(CreateAccountBtn, "Create account", test);
				Thread.sleep(15000);
				action.elementExistWelcome(identityNumberError, 4000, identityNumberError.getText(), test);
			} else if (saID.length() > 13) {
				System.out.println("Enters more than 13 digits");
				action.click(CreateAccountBtn, "Create account", test);
				Thread.sleep(15000);
				action.elementExistWelcome(identityNumberError, 4000, identityNumberError.getText(), test);
			} else {
				action.click(CreateAccountBtn, "Create account", test);
				Thread.sleep(15000);
				action.elementExistWelcome(identityNumberError, 4000, identityNumberError.getText(), test);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());

		}
	}

	// TA15 PASSWORD CHECK
	public String ic_VerifyPasswordcanDiffer(String passwordToChange) {
		passwordToChange += "fail";
		return passwordToChange;
	}

	/*
	 * public String alterId(String saId) { saId = saId. return; }
	 * 
	 */
	// TA29,30 CHECKS FOR THE STATUS OF THE NEWSLETTER IN IC FRONT END
	String newsletterStatusCheck(ExtentTest test) {
		String subscriptionStatus = null;
		try {
			action.clickEle(myAccountOption, "My Account Link", test);
			subscriptionStatus = findNewsLetterStatus.getText();
			action.clickEle(Account_info_option, "Account info link", test);
			return subscriptionStatus;
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
		return subscriptionStatus;
	}

	// Sourav TA17
	// TA27,28,29,30,31 CHECKS FLAG AND VALIDATES WHAT FLAG INDICATED COLUMN H
	// IN accountCreation++
	public void Verify_Acount_Information(ExtentTest test, String expFirstName, String expLastName,
			String expEmailAddress, String expSAID, String expVatNumber, String expVatNumberFlag,
			String expIdentityType, String expNewsletter) throws IOException, Exception {
		String ExpPage = "edit";
		boolean accInfoOpt = action.waitUntilElementIsDisplayed(Account_info_option, 20000);// action.elementExists(Account_info_option,
																							// 11);
		if (accInfoOpt == true) {
			action.CompareResult("Verify account info option is present", String.valueOf(true),
					String.valueOf(accInfoOpt), test);
			action.click(Account_info_option, "Account info link", test);
			action.explicitWait(11000);
			if (driver.getCurrentUrl().contains(ExpPage + "/")) {
				action.CompareResult("Verify Account info page is opened", ExpPage, driver.getCurrentUrl().toString(),
						test);

				String ActualFirstname = action.getAttribute(Firstname, "value");

				String ActualLastname = action.getAttribute(Lastname, "value");

				action.clickEle(Change_Emailcheckbox, "Enable click email checkbox ", test);
				action.explicitWait(5000);
				String ActualEmail = action.getAttribute(Email, "value");
				action.clickEle(Change_Emailcheckbox, "Enable click email checkbox ", test);

				String ActualSAID = action.getAttribute(SAID, "value");
				// TA31
				String ActualTaxVatNumber = action.getAttribute(taxVatNumber, "value");
				String ActualPassport = action.getAttribute(passport, "value");

				action.CompareResult("Verify First Name ", expFirstName, ActualFirstname, test);
				action.CompareResult("Verify Last Name ", expLastName, ActualLastname, test);
				action.CompareResult("Verify Email Address ", expEmailAddress, ActualEmail, test);

				switch (expIdentityType) {
				case "ID":
					action.CompareResult("SA ID ", expSAID, ActualSAID, test);
					break;
				case "Passport":
					action.CompareResult("Passport", expSAID, ActualPassport, test);
					break;
				}

				if (expNewsletter.equalsIgnoreCase("yes")) {
					String actualStatus = newsletterStatusCheck(test);
					String expectedStatus = "You are subscribed to \"General Subscription\".";
					if (actualStatus.contains("subscribed")) {
						action.CompareResult("Verify Newsletter subscription, you are subscribed to newsletter",
								expectedStatus, actualStatus, test);
					} else {
						action.CompareResult("Verify Newsletter subscription", expectedStatus, actualStatus, test);
					}
				} else if (expNewsletter.equalsIgnoreCase("no")) {
					String actualStatus1 = newsletterStatusCheck(test);
					String expectedStatus1 = "You aren't subscribed to our newsletter.";
					if (actualStatus1.contains("aren't")) {
						action.CompareResult("Verify Newsletter subscription, you are not Subscibed to newsletter",
								expectedStatus1, actualStatus1, test);
					} else {
						action.CompareResult("Verify Newsletter subscription", expectedStatus1, actualStatus1, test);
					}

				}
				// TA31
				if (expVatNumberFlag.equalsIgnoreCase("yes")) {
					action.CompareResult("Verify Tax Vat Number", expVatNumber, ActualTaxVatNumber, test);
				}
			} else {
				action.CompareResult("Verify Account info page is opened", ExpPage, driver.getCurrentUrl().toString(),
						test);

			}
		} else {
			action.CompareResult("Verify account info option is present", String.valueOf(true),
					String.valueOf(accInfoOpt), test);
		}

		// action.selectExactValueFromListUsingText(elementAttr, value);
	}

	public void Magento_VerifyCustomerDetails(ExtentTest test, String expFristname, String expLastname, String expEmail,
			String expSAID) throws IOException {
		// replace by this Parameter while merge parameter (ExtentTest
		// test,String expFristname,String expLastname,String expEmail,String
		// expSAID)

		try {
			int loadtime = 20;

			String expBPnumber = null;

			// Starts from Account information tab.
			action.explicitWait(loadtime);
			String ActualFirstname = action.getAttribute(customerFirstname, "value");
			String ActualLastname = action.getAttribute(customerLastname, "value");
			String ActualEmail = action.getAttribute(customerEmail, "value");
			String ActualBPnumber = action.getAttribute(customerBPnnumber, "value");
			String ActualIdentityNumber = action.getAttribute(customerIdentityNumber, "value");

			action.CompareResult("Verify the First name of user in Magento", expFristname, ActualFirstname, test);
			action.CompareResult("Verify the Last name of user in Magento", expLastname, ActualLastname, test);
			action.CompareResult("Verify the Email of user in Magento", expEmail, ActualEmail, test);

			action.CompareResult("Verify the SA ID number of user in Magento", expEmail, ActualEmail, test);
			boolean FlagGenerateBPnumber = false;
			if (ActualBPnumber != null) {
				FlagGenerateBPnumber = true;
				action.CompareResult("Verify the BP number of user in Magento :", String.valueOf(true),
						String.valueOf(FlagGenerateBPnumber) + "-BP no : " + ActualBPnumber.toString(), test);

			} else {
				action.CompareResult("Verify the BP number of user in Magento :", String.valueOf(true),
						String.valueOf(FlagGenerateBPnumber) + "-BP no : " + ActualBPnumber.toString(), test);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			action.CompareResult("Magento_VerifyCustomerDetails method failed : " + "ERROR found as " + e.getMessage(),
					String.valueOf(true), String.valueOf(false), test);
		}
	}
}
