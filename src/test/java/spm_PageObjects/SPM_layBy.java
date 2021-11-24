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

public class SPM_layBy {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	public SPM_layBy(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);		
		this.dataTable2 = dataTable2;
	}
	
	@FindBy(xpath = "//button[@class='action apply-for-layby']")
	WebElement by_applyForLaybay;
	
	//Personal details
	@FindBy(xpath = "(//div[@class='modal-inner-wrap'])[2]")
	WebElement by_laybyModal;
	@FindBy(xpath = "//input[@name=\"name\"]")
	WebElement by_firstName;
	@FindBy(xpath = "(//input[@name=\"surname\"])[2]")
	WebElement by_lastName;
	@FindBy(xpath = "(//select[@name=\"title\"])[2]")
	WebElement by_title;
	
	//@FindBy(xpath = "(//select[@name=\"title\"])[2]")
	//WebElement by_initials;
	@FindBy(xpath = "(//select[@name=\"citizenship\"])[2]")
	WebElement by_citizenship;
	@FindBy(xpath = "//select[@name=\"id_type\"]")
	WebElement by_idType;
	@FindBy(xpath = "(//input[@name=\"id_number\"])[2]")
	WebElement by_SAIDorID;
	@FindBy(xpath = "//input[@name=\"temporary_resident_permit_no\"]")
	WebElement by_permitNumber;
	@FindBy(xpath = "//input[@name=\"temporary_resident_permit_expiry\"]")
	WebElement by_permitExpiryDate;
	@FindBy(xpath = "(//input[@name=\"date_of_birth\"])[2]")
	WebElement by_dateOB;
	@FindBy(xpath = "(//select[@name=\"gender\"])[2]")
	WebElement by_gender;
	
	//@FindBy(xpath = "//span[normalize-space()='Upload proof of RSA ID']")
	//input[@name='proof_of_id']
	@FindBy(xpath = "//input[@id='laybyApplicationForm__proof_of_id']")
	WebElement by_uploadRSAID;
	@FindBy(xpath = "//span[contains(text(),\"Ready to upload\")]")
    WebElement uploadMsg;
	@FindBy(xpath = "//span[contains(text(),'No file selected')]")
    WebElement NoFileSelection;
	@FindBy(xpath = "//fieldset[@id='laybyApplicationForm.personalDetails__fieldset']//button[@type='button']")
	WebElement by_NextButton;
	
	//Residential Address Details
	@FindBy(xpath = "//select[@name=\"residential_type_of_dwelling\"]")
	WebElement by_ResType;
	@FindBy(xpath = "//select[@name=\"residential_home_ownership\"]")
	WebElement by_ResOnwership;
	@FindBy(xpath = "//input[@name=\"residential_street_no\"]")
	WebElement by_ResStreetNo;
	@FindBy(xpath = "//select[@name=\"residential_province\"]")
	WebElement by_ResProvince;
	@FindBy(xpath = "//input[@name=\"residential_building_complex\"]")
	WebElement by_ResBuilding;
	@FindBy(xpath = "//input[@name=\"residential_city\"]")
	WebElement by_ResCity;
	@FindBy(xpath = "//input[@name=\"residential_suburb\"]")
	WebElement by_ResSuburb;
	@FindBy(xpath = "//input[@name=\"residential_postal_code\"]")
	WebElement by_ResPostalCode;
	@FindBy(xpath = "//select[@name=\"residential_country\"]")
	WebElement by_ResCountry;
	@FindBy(xpath = "//fieldset[@id='laybyApplicationForm.residentialAddress__fieldset']//button[@type='button']")
	WebElement by_ResNextButton;
	
	//Contact details
	@FindBy(xpath = "(//input[@name=\"contact_cell_number\"])[2]")
	WebElement by_ConContactNumber;
	@FindBy(xpath = "//input[@name=\"email_address\"]")
	WebElement by_ConEmailAddress;
	@FindBy(xpath = "//input[@name=\"home_phone\"]")
	WebElement by_ConHomephone;
	@FindBy(xpath = "//input[@name=\"employer_telephone\"]")
	WebElement by_ConEmployerTel;
	@FindBy(xpath = "//select[@name=\"home_language\"]")
	WebElement by_ConHomeLAnguage;
	@FindBy(xpath = "//fieldset[@id='laybyApplicationForm.contactDetails__fieldset']//button[@type='button']")
	WebElement by_ConNextButton;
	
	@FindBy(xpath = "//label[@for='laybyApplicationForm__deliver_to_home--yes']")
	WebElement by_deliverHome;
	@FindBy(xpath = "//label[@for='laybyApplicationForm__extended_guarantee--yes']")
	WebElement by_exteGuara;
	@FindBy(xpath = "//fieldset[@id='laybyApplicationForm.deliveryAndEgt__fieldset']//button[@type='button']")
	WebElement by_GuaNextButton;
	
	//Marketing Question
	@FindBy(xpath = "//label[@for='laybyApplicationForm__associated_market--yes']")
	WebElement by_associatedCompa;
	@FindBy(xpath = "//select[@name=\"statement_method\"]")
	WebElement by_statementCorresp;
	@FindBy(xpath = "//select[@name=\"marketing_language\"]")
	WebElement by_marketingLanguage;
	@FindBy(xpath = "//select[@name=\"marketing_method\"]")
	WebElement by_marketingMethod;
	@FindBy(xpath = "//select[@name=\"deposit_payment\"]")
	WebElement by_marketDeposit;
	@FindBy(xpath = "//label[@for='laybyApplicationForm__credit_bureau_check--yes']")
	WebElement by_creditCheck;
	@FindBy(xpath = "//label[@for='laybyApplicationForm__ts_and_cs']")
	WebElement by_marketTCs;
	@FindBy(xpath = "//fieldset[@id='laybyApplicationForm.marketingQuestions__fieldset']//button[@type='submit']")
	WebElement by_marketSubmit;
	@FindBy(xpath = "//h1[normalize-space()='Thank you for submitting your lay-by application.']")
	WebElement by_successfull;
	
	//Enquries
	@FindBy(xpath = "//*[contains(text(),'Talk to a Consultant')]")
	WebElement consultantPopUp;
	
	@FindBy(id = "first_name")
	WebElement enquiryName;
	
	@FindBy(id ="email")
	WebElement equiryEmail;
	
	@FindBy(id = "phone")
	WebElement enquiryPhone;
	
	@FindBy(id = "preferred_store")
	WebElement enquiryPreferredStore;
	
	@FindBy(id = "comment")
	WebElement enquiryMessage;
	
	@FindBy(xpath = "//*[contains(text(),'Send Message')]/parent::button")
	WebElement sendMessage;

	public void personalDetails(ExtentTest test) throws Exception {
		String firstName = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "First_Name", 0);
		String lastName = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Last_Name", 0);
		String titleLayBy = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Title", 0);
		String citizen = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Citizenship", 0);
		String idType = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Identification type", 0);
		String idORPass = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "IDorPassport", 0);
		String gender = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Gender", 0);
		String permitNum = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Temporary Resident Permit No.", 0);
		String permitNumExpiryDate = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Temporary Resident Permit Expiry Date", 0);
		
		if(action.isElementPresent(by_laybyModal)) {
			action.writeText(by_firstName, firstName, "First name", test);
			action.writeText(by_lastName, lastName, "Last name", test);
			action.selectFromDropDownUsingVisibleText(by_title, titleLayBy, "Title");
			action.selectFromDropDownUsingVisibleText(by_citizenship, citizen, "Citizenship");
			if(idType.equalsIgnoreCase("SA ID Number")) {
				action.selectFromDropDownUsingVisibleText(by_idType, idType, "Select SA ID Number");
				action.writeText(by_SAIDorID, idORPass, "SA ID", test);
				//upload documnet
				//scroll to date of birth
			}else {
				action.selectFromDropDownUsingVisibleText(by_idType, idType, "Select Passport Number");
				action.writeText(by_SAIDorID, idORPass, "Passport", test);
				action.scrollElemetnToCenterOfView(by_permitNumber, "Scroll To Permit Number ", test);
				action.writeText(by_permitNumber, permitNum, "Permit Number", test);
				action.writeText(by_permitExpiryDate, permitNumExpiryDate, "Permit Expiry Date", test);
			}
			action.scrollElemetnToCenterOfView(by_uploadRSAID,"Scroll To Upload Document",test);
			uploadRSAID(test);
			action.selectFromDropDownUsingVisibleText(by_gender, gender, "Select the Gender");
			action.click(by_NextButton, "Click Next", test);
		
		}else {
			throw new Exception("Personal Details form didn't appear");
		}
	}
	public void uploadRSAID(ExtentTest test) throws Exception {
        String uploadDocument = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Upload Document", 0);
        action.explicitWait(10000);
        try {
            if (uploadDocument.equalsIgnoreCase("yes")) {
                boolean uploadButton = action.isElementPresent(by_uploadRSAID);
                action.CompareResult("Upload Button is displayed", "true", String.valueOf(uploadButton), test);

                if (uploadButton) {
                    boolean NoFileSelectionCheck = action.isElementPresent(NoFileSelection);
                    action.CompareResult("No Document is selected for Upload", "true", String.valueOf(NoFileSelectionCheck), test);
                    if (NoFileSelectionCheck) {

                        String filePath = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Document Upload Location", 0);

                        by_uploadRSAID.sendKeys(filePath);
                        boolean uploadMessage = action.waitUntilElementIsDisplayed(uploadMsg, 10);
                        action.CompareResult("Ready to upload message", "true", String.valueOf(uploadMessage), test);

                    }
                }
            }
        } catch (Exception e) {
            throw new Exception("Unable to upload the Document: " + e.getMessage());
        }
    }
	
	public void residentialAddressDetails(ExtentTest test)throws Exception  {
		String typeResidence = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Type of Residence", 0);
		String homeOwnership = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Home Ownership", 0);
		String stAddress = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Street Address", 0);
		String layByBuilding = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Complex/Building", 0);
		String layByprovince = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Province", 0);
		String layBycity = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "City", 0);
		String layBysuburb = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Suburb", 0);
		String layBypostalCode = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Postal Code", 0);
		String layBycountry = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Country", 0);
		
		if(action.isElementPresent(by_laybyModal)) {
			action.selectFromDropDownUsingVisibleText(by_ResType, typeResidence, "Type Of Residence");
			action.selectFromDropDownUsingVisibleText(by_ResOnwership, homeOwnership, "Home Ownership");
			action.writeText(by_ResStreetNo, stAddress, "Street Address", test);
			action.writeText(by_ResBuilding, layByBuilding, "Street Address", test);
			action.selectFromDropDownUsingVisibleText(by_ResProvince, layByprovince, "Province");
			action.writeText(by_ResCity, layBycity, "City", test);
			action.writeText(by_ResSuburb, layBysuburb, "Suburb", test);
			action.writeText(by_ResPostalCode, layBypostalCode, "Postal Code", test);
			action.selectFromDropDownUsingVisibleText(by_ResCountry, layBycountry, "Country");
			
			action.scrollElemetnToCenterOfView(by_ResNextButton, "Scroll To Next button", test);
			action.click(by_ResNextButton, "Click next", test);
			
		}else {
			throw new Exception("Residential Address Details form didn't appear");
		}
	}
	
	public void contactLayByDetails(ExtentTest test)throws Exception  {
		String mobileNum = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Mobile Number", 0);
		String conEmail = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Email Address", 0);
		String conEmployerNum = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Employer Telephone Number", 0);
		String conHomeLanguage = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Home Language", 0);
		if(action.isElementPresent(by_laybyModal)) {
			action.writeText(by_ConContactNumber, mobileNum, "Mobile Number", test);
			action.writeText(by_ConEmailAddress, conEmail, "Email Address ", test);
			action.writeText(by_ConEmployerTel, conEmployerNum, "Employer Telephone Number", test);
			action.selectFromDropDownUsingVisibleText(by_ConHomeLAnguage, conHomeLanguage, "Home Language");
			
			action.click(by_ConNextButton, "Click next", test);
		}else {
			throw new Exception("Contact Details form didn't appear");
		}
	}
	
	
	public void deliveryAndEGT(ExtentTest test)throws Exception  {
		String homeDelivery = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Home Delivery", 0);
		String exetendGua = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Extended Guarantee", 0);
		if(action.isElementPresent(by_laybyModal)) {
			if(homeDelivery.equalsIgnoreCase("Yes")) {
				action.click(by_deliverHome, "Deliver Home", test);
				action.scrollElemetnToCenterOfView(by_GuaNextButton, "Scroll To Next button", test);	
			}
			if(exetendGua.equalsIgnoreCase("Yes")) {
				action.click(by_exteGuara, "Extended Guarantee", test);
			}
			action.click(by_GuaNextButton, "Click next", test);	
		}else {
			throw new Exception("Delivery and EGT didn't appear");
		}
	}
	
	public void marketQuestions(ExtentTest test)throws Exception {
		String timeOut = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "TimeOutInSeconds", 0);
	 	Integer timeOutInSeconds = Integer.parseInt(timeOut);
		String AssociatedCompany = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Companies market", 0);
		String statementCorresp = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Statement Correspondence Method", 0);
		String marketLang = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Marketing Language", 0);
		String marketCorresp = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Marketing Correspondence Method", 0);
		String bureauCheck = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Credit bureau check", 0);
		String likeDeposit = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Deposit", 0);
		
		if(action.isElementPresent(by_laybyModal)) {
			if(AssociatedCompany.equalsIgnoreCase("No")) {
				action.selectFromDropDownUsingVisibleText(by_statementCorresp, statementCorresp, "Statement Correspondence Method");
			}else {
				action.click(by_associatedCompa, "Click For Marketing", test);
				action.selectFromDropDownUsingVisibleText(by_marketingLanguage, marketLang, "Marketing Language");
				action.selectFromDropDownUsingVisibleText(by_marketingMethod, marketCorresp, "Marketing Correspondence Method");
				action.selectFromDropDownUsingVisibleText(by_statementCorresp, statementCorresp, "Statement Correspondence Method");
			}
			
			if(bureauCheck.equalsIgnoreCase("Yes")) {
				action.click(by_creditCheck, "Click for Bureau Check", test);
			}
			action.scrollElemetnToCenterOfView(by_marketDeposit,"How much deposit would you like", test);
			action.selectFromDropDownUsingVisibleText(by_marketDeposit, likeDeposit, "How much deposit would you like");
			action.click(by_marketTCs, "Checkbox Selected", test);
			action.click(by_marketSubmit, "Click submit", test);
			action.waitForPageLoaded(timeOutInSeconds);
	        action.ajaxWait(timeOutInSeconds, test);
	        action.CompareResult("The Lay-by application was Successfully Submited", by_successfull.getText(), "Thank you for submitting your lay-by application.", test);
	        
		}
		
	}
	//Enquries
	public void enterEquiryDetails(ExtentTest test) throws Exception{
		String enquName = dataTable2.getValueOnCurrentModule("Name");
		String emailAddress = dataTable2.getValueOnCurrentModule("Email");
		String phoneNumber = dataTable2.getValueOnCurrentModule("Phone");
		String preferredStore = dataTable2.getValueOnCurrentModule("Preferred_Store").toLowerCase();
		String enquiryMsg = dataTable2.getValueOnCurrentModule("Message");
		action.elementExistWelcome(consultantPopUp, 20, "Talk To a Consultant", test);
		action.ajaxWait(20, test);
		action.writeText(enquiryName, enquName, "Name", test);
		action.writeText(equiryEmail, emailAddress, "Email", test);
		action.writeText(enquiryPhone, phoneNumber, "Phone Number", test);
		action.writeText(enquiryMessage, enquiryMsg, "Enquiry Message", test);
		action.dropDownselectbyvisibletext(enquiryPreferredStore, preferredStore,"Preferred Store", test);
		
		action.scrollElementIntoView(sendMessage);
		action.click(sendMessage, "Send Message", test);
		action.waitForPageLoaded(20);
		//action.ajaxWait(20, test);
		action.explicitWait(6000);
		
	}


}
