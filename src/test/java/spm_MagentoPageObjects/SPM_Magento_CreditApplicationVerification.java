package spm_MagentoPageObjects;

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

public class SPM_Magento_CreditApplicationVerification {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	public SPM_Magento_CreditApplicationVerification(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	}
	
	//(//span[contains(text(),'Customers')])[1]
	@FindBy(xpath = "//li[@id='menu-magento-customer-customer']")
	private WebElement Customer;
	//@FindBy(xpath = "/html/body/div[1]/nav/ul/li[5]/div/ul/li[1]/ul/li[6]/div/ul/li/a/span[contains(text(),'Credit Applications')]")
	@FindBy(xpath = "//a//span[contains(text(),'Credit Applications')]")
	private WebElement Credit_app;
	@FindBy(xpath = "//h1[contains(text(),'Credit Applications')]")
	private WebElement CreditApplicationPg;
	@FindBy(xpath = "//button[normalize-space()='Clear all']")
	private WebElement Clear_all;
	@FindBy(xpath = "//button[contains(text(),'Filters')]")
	private WebElement Filter;
	@FindBy(xpath = "//input[@name=\"contact_email_address\"]")
	private WebElement ContactEmail;
	@FindBy(xpath = "//button[@data-action='grid-filter-apply']/span")
	private WebElement Apply_Filter;
	@FindBy(xpath = "//h1[normalize-space()='Credit Application Details']")
	private WebElement CreditApplicatioDetails;
	@FindBy(xpath = "//a[normalize-space()='Details']")
	private WebElement detailsClick;
	
	//Validation
	@FindBy(xpath = "//select[@name=\"status\"]")
	WebElement cr_status;
	@FindBy(xpath = "//select[@name=\"title\"]")
	WebElement cr_title;
	@FindBy(xpath = "//input[@name=\"first_names\"]")
	WebElement cr_firstName;
	@FindBy(xpath = "//input[@name=\"surname\"]")
	WebElement cr_lastName;
	@FindBy(xpath = "//select[@name=\"ethnicity\"]")
	WebElement cr_ethn;
	@FindBy(xpath = "//select[@name=\"martial_status\"]")
	WebElement cr_MarStatus;
	@FindBy(xpath = "//select[@name=\"how_married\"]")
	WebElement cr_MarTpye;
	@FindBy(xpath = "//input[@name=\"citizenship\"]")
	WebElement cr_cit;
	@FindBy(xpath = "//input[@name=\"id_number\"]")
	WebElement cr_IDnumber;
	@FindBy(xpath = "//select[@name=\"gender\"]")
	WebElement cr_gender;
	@FindBy(xpath = "//select[@name=\"dependants\"]")
	WebElement cr_Dependant;
	//spouse
	@FindBy(xpath = "//select[@name=\"spouse_title\"]")
	WebElement cr_spTitle;
	@FindBy(xpath = "//input[@name=\"spouse_first_name\"]")
	WebElement cr_spFirstNam;
	@FindBy(xpath = "//input[@name=\"spouse_surname\"]")
	WebElement cr_spLastName;
	@FindBy(xpath = "//input[@name=\"spouse_cell_number\"]")
	WebElement cr_spCellphone;
	@FindBy(xpath = "//input[@name=\"spouse_citizenship\"]")
	WebElement cr_spCitizen;
	@FindBy(xpath = "//input[@name=\"spouse_id_number\"]")
	WebElement cr_spIDnumber;
	@FindBy(xpath = "//select[@name=\"spouse_gender\"]")
	WebElement cr_spGender;
	@FindBy(xpath = "//input[@name=\"spouse_gross_salary\"]")
	WebElement cr_spGrossSalary;
	
	//Employment 
	@FindBy(xpath = "//select[@name=\"employment_type\"]")
	WebElement cr_EmpType;
	@FindBy(xpath = "//select[@name=\"field_of_employment\"]")
	WebElement cr_EmpField;
	@FindBy(xpath = "//input[@name=\"employment_years\"]")
	WebElement cr_TermEmpYears;
	@FindBy(xpath = "//input[@name=\"employment_months\"]")
	WebElement cr_TermEmpMonths;
	@FindBy(xpath = "//input[@name=\"gross_salary\"]")
	WebElement cr_GrossSalary;
	
	//Address
	@FindBy(xpath = "//input[@name=\"residental_street\"]")
	WebElement cr_streetAddress;
	@FindBy(xpath = "//input[@name=\"residental_city\"]")
	WebElement cr_city;
	@FindBy(xpath = "//input[@name=\"residental_postal_code\"]")
	WebElement cr_postalCode;
	@FindBy(xpath = "//input[@name=\"residental_country\"]")
	WebElement cr_country;
	
	//contact
	@FindBy(xpath = "//select[@name=\"do_you_have_bank_account\"]")
	WebElement cr_AvailabilityBank;
	@FindBy(xpath = "//select[@name=\"bank_name\"]")
	WebElement cr_BankName;
	@FindBy(xpath = "//input[@name=\"contact_cell_number\"]")
	WebElement cr_CellPhone;
	@FindBy(xpath = "//input[@name=\"contact_email_address\"]")
	WebElement cr_emailAdd;
	@FindBy(xpath = "//select[@name=\"correspondence_language\"]")
	WebElement cr_CorresLanguage;
	@FindBy(xpath = "//select[@name=\"subscribe\"]")
	WebElement cr_newsLetter;
	
	@FindBy(xpath = "//aside[contains(@class,'_show')]//div[contains(@class,'modal-inner-wrap')]")
	WebElement by_deleteModal;
	@FindBy(xpath = "//span[normalize-space()='Delete Application']")
	WebElement cr_deleteApp;
	@FindBy(xpath = "//div[contains(text(),'Are you sure you want to do this? This will remove')]")
	WebElement by_deleteMessageModal;
	@FindBy(xpath = "//button[contains(@class,'action-primary action-accept')]")
	WebElement by_deleteModalOK;
	@FindBy(xpath = "//div[@data-ui-id='messages-message-success']")
	WebElement by_deleteSuccess;
	
	
	public void VerifyCreditAppSelection(ExtentTest test) throws Exception {
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
		String employmentType = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","employmentType",0);
		String yearsOfEmployment =dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","yearsOfEmployment",0);
		String monthsOfEmployment =dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","monthsOfEmployment",0);
		String grossSalaryPerMonth =dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","grossSalaryPerMonth",0);
		String fieldOfEmployment =dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","fieldOfEmployment",0);
		String streetAddress = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","streetAddress",0);
		String buidingDetails= dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","buidingDetails",0);
		String province= dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","province",0);
		String city= dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","city",0);
		String suburb= dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","suburb",0);
		String postalCode= dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","postalCode",0);
		String county= dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","county",0);
		String preferredStore= dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","preferredStore",0);
		String BankAccountAvailability = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","BankAccountAvailability",0);
    	String BankName = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","BankName",0);
    	String contactCellNumber = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","contactCellNumber",0); 
    	String contactEmailAddress = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","contactEmailAddress",0);
    	String contactCorreLAnguage = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","contactCorreLAnguage",0);
    	String contactSubscribeFlag = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","contactSubscribeFlag",0);
    	String spouseConsent = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseConsent",0);
		String spouseTitle = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseTitle",0);
    	String spouseSurname = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseSurname",0);
    	String spouseFirstName = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseFirstName",0);
    	String spouseCellNumber = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseCellNumber",0);
    	String spouseCitizenship = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseCitizenship",0);
    	String spouseIdNumber = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseIdNumber",0);
    	String spouseGender = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseGender",0);
    	String spouseGrossSalary = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","spouseGrossSalary",0);
		
		Navigate_To_CreditApp(test);
		SearchForCreditAppByEmail(test);
		
		action.click(detailsClick, "Click to view more details", test);
		if(action.isElementPresent(CreditApplicatioDetails)) {
			action.waitExplicit(10);
			String statusApp = action.getSelectedOptionFromDropDown(cr_status);
			dataTable2.setValueOnOtherModule("SPM_CreditApp_Details", "Application_Status", statusApp, 0);
			String ApplicationSta = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details", "Application_Status", 0);
			action.CompareResult("The Application Status", action.getSelectedOptionFromDropDown(cr_status), ApplicationSta, test);
			action.scrollElemetnToCenterOfView(cr_title, "Scroll to the Title", test);
			action.CompareResult("The Applicant Title", action.getSelectedOptionFromDropDown(cr_title), titleLayBy, test);
			action.CompareResult("The Applicant First Name", cr_firstName.getText(), firstName, test);
			action.CompareResult("The Applicant Last Name", cr_lastName.getText(), lastName, test);
			action.scrollElemetnToCenterOfView(cr_ethn, "Scroll to the Ethnicity", test);
			action.CompareResult("The Application Ethnicity", action.getSelectedOptionFromDropDown(cr_ethn), enthnicity, test);
			action.CompareResult("The Application Martial Status", action.getSelectedOptionFromDropDown(cr_MarStatus), maritalStatusCr, test);
			
			if(maritalStatusCr.equalsIgnoreCase("Married / Civil Partnership")){
				action.scrollElemetnToCenterOfView(cr_spTitle, "Scroll to the Spouse Title", test);
				action.CompareResult("The Spouse Title", action.getSelectedOptionFromDropDown(cr_spTitle), spouseTitle, test);
				action.CompareResult("The Spouse First Name", cr_spFirstNam.getText(), spouseFirstName, test);
				action.CompareResult("The Spouse Last Name", cr_spLastName.getText(), spouseSurname, test);
				action.CompareResult("The Spouse Cell Number", cr_spCellphone.getText(), spouseCellNumber, test);
				action.CompareResult("The Spouse Citizenship", cr_spCitizen.getText(), spouseCitizenship, test);
				action.scrollElemetnToCenterOfView(cr_spIDnumber, "Scroll to the Identification Numbe", test);
				action.CompareResult("The Spouse Identification Number", cr_spIDnumber.getText(), spouseIdNumber, test);
				action.CompareResult("The Spouse Gender", action.getSelectedOptionFromDropDown(cr_spGender), spouseGender, test);
				action.CompareResult("The Spouse Gross Salary", cr_spGrossSalary.getText(), spouseGrossSalary, test);
	    	}
			
			action.CompareResult("The Applicant Citizenship", cr_cit.getText(), citizen, test);
			action.CompareResult("The Applicant Identification Number", cr_IDnumber.getText(), idORPass, test);
			action.scrollElemetnToCenterOfView(cr_gender, "Scroll to the Gender", test);
			action.CompareResult("The Applicant Gender", action.getSelectedOptionFromDropDown(cr_gender), gender, test);
			action.CompareResult("The Applicant Dependants", action.getSelectedOptionFromDropDown(cr_Dependant), dependantCr, test);
			
			action.scrollElemetnToCenterOfView(cr_EmpType, "Scroll to Employment Type", test);
			action.CompareResult("The Applicant Employment Type", action.getSelectedOptionFromDropDown(cr_EmpType), employmentType, test);
			action.CompareResult("The Applicant Field of Employment", action.getSelectedOptionFromDropDown(cr_EmpField), fieldOfEmployment, test);
			action.CompareResult("The Applicant Term of Employment Years", cr_TermEmpYears.getText(), yearsOfEmployment, test);
			action.CompareResult("The Applicant Term of Employment Months", cr_TermEmpMonths.getText(), monthsOfEmployment, test);
			action.CompareResult("The Applicant Term of Gross Salary Per Month", cr_GrossSalary.getText(), grossSalaryPerMonth, test);
			
			action.scrollElemetnToCenterOfView(cr_EmpType, "Scroll to Street Address", test);
			action.CompareResult("The Applicant Street Address", cr_streetAddress.getText(), streetAddress, test);
			action.CompareResult("The Applicant City", cr_city.getText(), city, test);
			action.CompareResult("The Applicant Postal Code", cr_postalCode.getText(), postalCode, test);
			action.CompareResult("The Applicant Country", cr_country.getText(), county, test);
			
			action.scrollElemetnToCenterOfView(cr_AvailabilityBank, " Do you have bank account?", test);
			action.CompareResult("The Applicant has a bank", action.getSelectedOptionFromDropDown(cr_AvailabilityBank), BankAccountAvailability, test);
			action.CompareResult("The Applicant Bank Name", action.getSelectedOptionFromDropDown(cr_BankName), BankName, test);
			action.CompareResult("The Applicant Cell Number", cr_CellPhone.getText(), contactCellNumber, test);
			action.CompareResult("The Applicant E-mail Address", cr_emailAdd.getText(), contactEmailAddress, test);
			action.CompareResult("The Applicant Correspondence Languagee", action.getSelectedOptionFromDropDown(cr_CorresLanguage), contactCorreLAnguage, test);
		}
		
		
	}
	public void SearchForCreditAppByEmail(ExtentTest test) throws IOException, InterruptedException{
		String EmailIDtoSearch = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details","contactEmailAddress",0);
				if (action.isDisplayed(Clear_all)) {
					action.click(Clear_all, "Clear_all Filters", test);
					action.waitForPageLoaded(10);
				}
				action.click(Filter, "Filter tab", test);
				action.writeText(ContactEmail, EmailIDtoSearch, "EmailID to Search", test);
				action.click(Apply_Filter, "Apply to filters", test);
				action.waitForPageLoaded(10);
	
	}
	
	public void Navigate_To_CreditApp(ExtentTest test) throws Exception {
		action.click(Customer, "Customer tab ", test);
		action.waitUntilElementIsDisplayed(Credit_app,10);
		if(action.isElementPresent(Credit_app)){
			action.click(Credit_app, "Credit_app", test);
			action.CompareResult("Credit Application Page loaded", "Credit Applications", CreditApplicationPg.getText(), test);
		}else {
			throw new Exception("Credit Application Page didn't load"); 
		}
		
	}
	
	public void deteleCredApp(ExtentTest test) throws Exception {
			String timeOut = dataTable2.getValueOnOtherModule("SPM_CreditApp_Details", "TimeOutInSeconds", 0);
		 	Integer timeOutInSeconds = Integer.parseInt(timeOut);
			action.click(cr_deleteApp, "Click Delete Application", test);
			if(action.isElementPresent(by_deleteModal)) {
				action.CompareResult("Message Delete",by_deleteMessageModal.getText() , "Are you sure you want to do this? This will remove application from Magento!", test);
			}else {
				throw new Exception("Delete Message didn't appear");
			}
			action.click(by_deleteModalOK, "Click OK", test);
			action.waitForPageLoaded(timeOutInSeconds);
	        action.ajaxWait(timeOutInSeconds, test);
	        action.CompareResult("The Record Was Deleted", by_deleteSuccess.getText(), "Credit Application deleted successfully.", test);
			
		}
}
