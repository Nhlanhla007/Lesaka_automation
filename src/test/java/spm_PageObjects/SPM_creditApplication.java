package spm_PageObjects;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class SPM_creditApplication {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	public SPM_creditApplication(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);		
		this.dataTable2 = dataTable2;
	}
	
	@FindBy(xpath = "//a[contains(text(),'Apply for Credit')]")
	WebElement cr_ApplyForCred;
	
	//Personal details
	@FindBy(xpath = "(//div[@class='modal-inner-wrap'])[2]")
	WebElement cr_laybyModal;
	@FindBy(xpath = "//input[@name=\"first_names\"]")
	WebElement cr_firstName;
	@FindBy(xpath = "(//input[@name=\"surname\"])[1]")
	WebElement cr_lastName;
	@FindBy(xpath = "(//select[@name=\"title\"])[1]")
	WebElement cr_title;
	
	//@FindBy(xpath = "(//select[@name=\"title\"])[2]")
	//WebElement by_initials;
	@FindBy(xpath = "(//select[@name=\"ethnicity\"])[1]")
	WebElement cr_ethinicity;
	@FindBy(xpath = "(//select[@name=\"citizenship\"])[1]")
	WebElement cr_citizenship;
	//@FindBy(xpath = "//select[@name=\"id_type\"]") WebElement by_idType; 
	@FindBy(xpath = "(//input[@name=\"id_number\"])[1]")
	WebElement cr_SAIDorID;
//	@FindBy(xpath = "//input[@name=\"temporary_resident_permit_no\"]")
//	WebElement by_permitNumber;
//	@FindBy(xpath = "//input[@name=\"temporary_resident_permit_expiry\"]")
//	WebElement by_permitExpiryDate;
	@FindBy(xpath = "(//input[@name=\"date_of_birth\"])[1]")
	WebElement cr_dateOB;
	@FindBy(xpath = "(//select[@name=\"gender\"])[1]")
	WebElement cr_gender;
	@FindBy(xpath = "(//select[@name=\"dependants\"])[1]")
	WebElement cr_dependant;
	@FindBy(xpath = "(//select[@name=\"martial_status\"])[1]")
	WebElement cr_maritalStatus;
	@FindBy(xpath = "(//select[@name=\"how_married\"])[1]")
	WebElement cr_howMarried;
	
	//@FindBy(xpath = "//span[normalize-space()='Upload proof of RSA ID']")
	//input[@name='proof_of_id']
	@FindBy(xpath = "//input[@id='laybyApplicationForm__proof_of_id']")
	WebElement by_uploadRSAID;
	@FindBy(xpath = "//span[contains(text(),\"Ready to upload\")]")
    WebElement uploadMsg;
	@FindBy(xpath = "//span[contains(text(),'No file selected')]")
    WebElement NoFileSelection;
	@FindBy(xpath = "//fieldset[@id='creditApplicationForm.basicDetails__fieldset']//button[@type='button']")
	WebElement cr_NextButton;
	
	//Spouse
	@FindBy(xpath="//select[@ name=\"spouse_title\"]")
    private WebElement cr_SpouseTitle;
    @FindBy(xpath="//input[@ name=\"spouse_surname\"]")
    private WebElement cr_SpouseLastName;
    @FindBy(xpath="//input[@ name=\"spouse_first_name\"]")
    private WebElement cr_SpouseFirstName;
     @FindBy(xpath="//input[@ name=\"spouse_initials\"]")
    private WebElement cr_SpouseInitials;
    @FindBy(xpath="//input[@ name=\"spouse_cell_number\"]")
    private WebElement cr_SpouseCellNumber;
    @FindBy(xpath="//select[@ name=\"spouse_citizenship\"]")
    private WebElement cr_SpouseCitizenship;
    @FindBy(xpath="//input[@ name=\"spouse_id_number\"]")
    private WebElement cr_SpouseIdNumber;
    @FindBy(xpath="//input[@ name=\"spouse_date_of_birth\"]")
    private WebElement cr_SpouseDateOfBirth;
    @FindBy(xpath="//select[@ name=\"spouse_gender\"]")
    private WebElement cr_SpouseGender;
    @FindBy(xpath="//input[@ name=\"spouse_gross_salary\"]")
    private WebElement cr_SpouseGrossSalary;
    @FindBy(xpath="//label[@for='creditApplicationForm__obtained_spouse_consent--yes']")
    private WebElement cr_SpouseConsent;
    @FindBy(xpath="//fieldset[@id='creditApplicationForm.spouseDetails__fieldset']//button[@type='button']")
    private WebElement cr_SpouseNext;
	
	//Credit Review
	 @FindBy(xpath="//label[@for='creditApplicationForm__administration_order_subject--yes']")
	    private WebElement cr_adminOrderYES;
	 @FindBy(xpath="//label[@for='creditApplicationForm__applied_under_administration--yes']")
	    private WebElement cr_adminAppliedYES;
	 @FindBy(xpath="//label[@for='creditApplicationForm__under_sequestration--yes']")
	    private WebElement cr_SequestratedYES;
	 @FindBy(xpath="//label[@for='creditApplicationForm__applied_to_sequestrated--yes']")
	    private WebElement cr_SequestratedAppliedYES;
	 @FindBy(xpath="//label[@for='creditApplicationForm__under_debt_review--yes']")
	    private WebElement cr_DebtReviewYES;
	 @FindBy(xpath="//label[@for='creditApplicationForm__applied_under_debt_review--yes']")
	    private WebElement cr_underDebtReviewYES;
	 @FindBy(xpath = "//fieldset[@id='creditApplicationForm.credit__fieldset']//div[@class='primary']")
		WebElement cr_CRNextButton;
	
	
	//Employment details
	 @FindBy(xpath = "//select[@name=\"employment_type\"]")
		WebElement cr_employmentType;
	 @FindBy(xpath = "//input[@name=\"employment_years\"]")
		WebElement cr_employmentYears;
	 @FindBy(xpath = "//input[@name=\"employment_months\"]")
		WebElement cr_employmentMonths;
	 @FindBy(xpath = "//input[@name=\"gross_salary\"]")
		WebElement cr_employmentGrossSalary;
	 @FindBy(xpath = "//select[@name=\"field_of_employment\"]")
		WebElement cr_fieldEmployment;
	 @FindBy(xpath = "//fieldset[@id='creditApplicationForm.employmentDetails__fieldset']//button[@type='button']")
		WebElement cr_EmpNextButton;
	
	//Address
	 @FindBy(xpath = "//input[@name=\"residental_street\"]")
		WebElement cr_StreetAddres;
	 @FindBy(xpath = "//input[@name=\"residental_building_complex\"]")
		WebElement cr_building;
	 @FindBy(xpath = "//select[@name=\"residental_province\"]")
		WebElement cr_resiProvince;
	 @FindBy(xpath = "//input[@name=\"residental_city\"]")
		WebElement cr_city;
	 @FindBy(xpath = "//input[@name=\"residental_suburb\"]")
		WebElement cr_suburb;
	 @FindBy(xpath = "//input[@name=\"residental_postal_code\"]")
		WebElement cr_postalCode;
	 @FindBy(xpath = "//select[@name=\"residental_country\"]")
		WebElement cr_resiCountry;
	 @FindBy(xpath = "//select[@name=\"preferred_store\"]")
		WebElement cr_preferredStore;
	 @FindBy(xpath = "//fieldset[@id='creditApplicationForm.address__fieldset']//button[@type='button']")
		WebElement cr_AddNextButton;
	

	//contact
	 @FindBy(xpath="//input[@ name=\"contact_cell_number\"]")
	    private WebElement cr_contactCellNumber;
	 @FindBy(xpath="//input[@ name=\"contact_email_address\"]")
	    private WebElement cr_contactEmailAddress;
	 @FindBy(xpath="//select[@ name=\"correspondence_language\"]")
	    private WebElement cr_contactCorreLanguage;
	 @FindBy(xpath="//label[@for='creditApplicationForm__do_you_have_bank_account--yes']")
	    private WebElement cr_BankAccYes;
	 @FindBy(xpath="//select[@ name=\"bank_name\"]")
	    private WebElement cr_bankName;
	 @FindBy(xpath="//input[@name=\"accept_tncs\"]")
	    private WebElement cr_contactTermsAccept;
	 @FindBy(xpath="//fieldset[@id='creditApplicationForm.contactDetails__fieldset']//button[@type='submit']")
	    private WebElement cr_Submit;
	 @FindBy(xpath="//strong[normalize-space()='Thank you for submitting your credit application.']")
	    WebElement cr_succesMessage;

	 public void navToApplyCred(ExtentTest test) throws Exception {
		 action.scrollElemetnToCenterOfView(cr_ApplyForCred, "Scroll To Appy For Credit ", test);
		 action.click(cr_ApplyForCred, "Apply For Credit", test);
	 }
	 
	public void personalDetails(ExtentTest test) throws Exception {
		String firstName = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details", "First_Name", 0);
		String lastName = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details", "Last_Name", 0);
		String titleLayBy = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details", "Title", 0);
		String citizen = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details", "Citizenship", 0);
		String idType = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details", "Identification type", 0);
		String idORPass = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details", "IDorPassport", 0);
		String gender = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details", "Gender", 0);
		String enthnicity = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details", "Enthnicity", 0);
		String dependantCr = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details", "Dependant", 0);
		String maritalStatusCr = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details", "Marital Status", 0);
		String HowMarriedCr = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details", "HowMarried", 0);
		
		if(action.isElementPresent(cr_laybyModal)) {
			action.writeText(cr_firstName, firstName, "First name", test);
			action.writeText(cr_lastName, lastName, "Last name", test);
			action.selectFromDropDownUsingVisibleText(cr_title, titleLayBy, "Title");
			action.selectFromDropDownUsingVisibleText(cr_ethinicity, enthnicity, "Enthnicity");
			action.selectFromDropDownUsingVisibleText(cr_citizenship, citizen, "Citizenship");
			action.writeText(cr_SAIDorID, idORPass, "South African ID", test);
			action.scrollElemetnToCenterOfView(cr_gender, "Scroll to Gender", test);
			action.selectFromDropDownUsingVisibleText(cr_gender, gender, "Select the Gender");
			action.selectFromDropDownUsingVisibleText(cr_dependant, dependantCr, "Title");
			action.scrollElemetnToCenterOfView(cr_maritalStatus, "Scroll to Marital Status", test);
			action.selectFromDropDownUsingVisibleText(cr_maritalStatus, maritalStatusCr, "Title");
        if(maritalStatusCr.equalsIgnoreCase("Married / Civil Partnership")){
	    		action.selectFromDropDownUsingVisibleText(cr_howMarried, HowMarriedCr, "How Are You Married?");
	    	}
			action.scrollElemetnToCenterOfView(cr_NextButton,"Scroll To Next",test);
			action.click(cr_NextButton, "Click Next", test);
		
		}else {
			throw new Exception("Personal Details form didn't appear");
		}
	}
	
	public void spouseDetails(ExtentTest test) throws Exception {
		String spouseConsent = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseConsent",0);
		String spouseTitle = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseTitle",0);
    	String spouseSurname = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseSurname",0);
    	String spouseFirstName = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseFirstName",0);
    	String spouseCellNumber = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseCellNumber",0);
    	String spouseCitizenship = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseCitizenship",0);
    	String spouseIdNumber = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseIdNumber",0);
    	String spouseDateOfBirth = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseDateOfBirth",0);
    	String spouseGender = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseGender",0);
    	String spouseGrossSalary = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseGrossSalary",0);
    	
    	if(action.isElementPresent(cr_laybyModal)) {
    		if(spouseConsent.equalsIgnoreCase("yes")) {
    			action.click(cr_SpouseConsent, "Have you obtained your spouse consent?", test);
    		}
	    	action.writeText(cr_SpouseTitle,spouseTitle , "Spouse title", test);
	    	action.writeText(cr_SpouseLastName,spouseSurname , "Spouse Last name", test);
	    	action.writeText(cr_SpouseFirstName,spouseFirstName , "Spouse First name", test);
	    	action.writeText(cr_SpouseCellNumber,spouseCellNumber , "Spouse cell number", test);
	    	action.writeText(cr_SpouseCitizenship,spouseCitizenship , "Spouse citizenship", test);
	    	action.writeText(cr_SpouseIdNumber,spouseIdNumber, "Spouse Id number", test);
	    	action.writeText(cr_SpouseDateOfBirth,spouseDateOfBirth , "Spouse date of birth", test);
	    	action.writeText(cr_SpouseGender,spouseGender , "Spouse gender", test);
	    	action.writeText(cr_SpouseGrossSalary,spouseGrossSalary , "Spouse Grossary salary", test);
	    	
	    	action.click(cr_SpouseNext, "Next", test);
    	}else {
			throw new Exception("Spouse Details form didn't appear");
		}
	}
	
	public void creditReview(ExtentTest test) throws Exception {
		String AreYouSubjectToAnAdministrationOrder = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","AreYouSubjectToAnAdministrationOrder?", 0);
    	String HaveYouAppliedToBeUnderAdministration = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","HaveYouAppliedToBeUnderAdministration?",0);
    	String AreYouUnderSequestration = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","AreYouUnderSequestration?",0);
    	String HaveYouAppliedToBeSequestrated = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","HaveYouAppliedToBeSequestrated?",0);
    	String AreYouUnderDebtReview = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","AreYouUnderDebtReview?",0);
    	String HaveYouAppliedToBePlacedUnderDebtReview= dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","HaveYouAppliedToBePlacedUnderDebtReview?",0);
    	
    	if(action.isElementPresent(cr_laybyModal)) {
	    	if(AreYouSubjectToAnAdministrationOrder.equalsIgnoreCase("Yes")){
	    		action.click(cr_adminOrderYES, "Are You Subject To An AdministrationOrder", test);
	    	}
	    	
	    	if(HaveYouAppliedToBeUnderAdministration.equalsIgnoreCase("Yes")){
	    		action.click(cr_adminAppliedYES, "Have You Applied To Be Under Administration", test);
	    	} 
	    	
	    	if(AreYouUnderSequestration.equalsIgnoreCase("Yes")){
	    		action.click(cr_SequestratedYES, "Are You Under Sequestration", test);
	    	} 
	    
	    	if(HaveYouAppliedToBeSequestrated.equalsIgnoreCase("Yes")){
	    		action.click(cr_SequestratedAppliedYES, "Have You Applied To Be Sequestrated", test);
	    	} 
	    	
	    	if(AreYouUnderDebtReview.equalsIgnoreCase("Yes")){
	    		action.click(cr_DebtReviewYES, "Are You Under Debt Review", test);	
	    	} 
	    	
	    	if(HaveYouAppliedToBePlacedUnderDebtReview.equalsIgnoreCase("Yes")){
	    		action.click(cr_underDebtReviewYES, "Have You Applied To Be Placed Under Debt Review", test);
	    	}
	    	action.click(cr_CRNextButton, "Click Next", test);
	    }else {
			throw new Exception("Credit Questions didn't appear");
	    }
    }
	
	public void EmploymentDetails(ExtentTest test) throws Exception{
		String employmentType = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","employmentType",0);
		String yearsOfEmployment =dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","yearsOfEmployment",0);
		String monthsOfEmployment =dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","monthsOfEmployment",0);
		String grossSalaryPerMonth =dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","grossSalaryPerMonth",0);
		String fieldOfEmployment =dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","fieldOfEmployment",0);
		
		if(action.isElementPresent(cr_laybyModal)) {
			action.selectFromDropDownUsingVisibleText(cr_employmentType,employmentType, "Employment Type");
			action.writeText( cr_employmentYears,yearsOfEmployment,"Employement in years", test);
			action.writeText(cr_employmentMonths,monthsOfEmployment, "Employment in months", test);
			action.writeText(cr_employmentGrossSalary,grossSalaryPerMonth, "Gross salary per month", test);
			action.selectFromDropDownUsingVisibleText(cr_fieldEmployment,fieldOfEmployment, "Employment Field");
			
			action.click(cr_EmpNextButton, "Employment details next button", test);
		}else {
			throw new Exception("Employment form didn't appear");
	    }
	}
	
	public void CreditAddDetails(ExtentTest test) throws Exception{
		String streetAddress = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","streetAddress",0);
		String buidingDetails= dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","buidingDetails",0);
		String province= dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","province",0);
		String city= dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","city",0);
		String suburb= dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","suburb",0);
		String postalCode= dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","postalCode",0);
		String county= dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","county",0);
		String preferredStore= dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","preferredStore",0);
		if(action.isElementPresent(cr_laybyModal)) {
			action.writeText(cr_StreetAddres, streetAddress, "Street Address", test);
			if(buidingDetails != null) {
			action.writeText(cr_building, buidingDetails, "Buidling Details", test);
			}
			action.selectFromDropDownUsingVisibleText(cr_resiProvince, province, "Select Province");
			action.writeText(cr_city, city, "City", test);
			action.writeText(cr_suburb,suburb, "Suburb", test);
			action.writeText(cr_postalCode,postalCode, "Postal Code", test);
			action.selectFromDropDownUsingVisibleText(cr_resiCountry,county, "Select Country");
			action.selectFromDropDownUsingVisibleText(cr_preferredStore,preferredStore, "Select store");
			
			action.click(cr_AddNextButton, "Click next Address button", test);
		}else {
			throw new Exception("Employment form didn't appear");
	    }
		
	}
	public void CreditContactDetails(ExtentTest test) throws Exception{
		String BankAccountAvailability = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","BankAccountAvailability",0);
    	String BankName = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","BankName",0);
    	String contactCellNumber = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","contactCellNumber",0); 
    	String contactEmailAddress = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","contactEmailAddress",0);
    	String contactCorreLAnguage = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","contactCorreLAnguage",0);
    	String contactSubscribeFlag = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","contactSubscribeFlag",0);
    	
    	action.writeText(cr_contactCellNumber,contactCellNumber , "contact cell number", test);
    	action.writeText(cr_contactEmailAddress,contactEmailAddress , "contact email address", test);
    	action.selectFromDropDownUsingVisibleText(cr_contactCorreLanguage,contactCorreLAnguage, "Select The Preferred Language");
    	if(BankAccountAvailability.equalsIgnoreCase("Yes")){
    		action.click(cr_BankAccYes, "Click the button", test);
    		action.writeText(cr_bankName,BankName , "Bank name", test);
    	}
    	action.javaScriptClick(cr_contactTermsAccept, "Terms and Conditions", test);
    	action.scrollElemetnToCenterOfView(cr_Submit, "Scroll to submit", test);
    	action.click(cr_Submit, "Submit The Credit Request", test);
    	action.waitForPageLoaded(30);
    	action.CompareResult("The Application Was Successfully Submitted", "Thank you for submitting your credit application.", cr_succesMessage.getText(), test);
		
	}

}
