package spm_MagentoPageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import evs_MagentoPageObjects.EVS_Magento_Login;
import utils.Action;
import utils.DataTable2;

public class SPM_Magento_LayByValidation {
	WebDriver driver;
	Action action;
	int ajaxTimeOutInSeconds = EVS_Magento_Login.ajaxTimeOutInSeconds;
	DataTable2 dataTable2;
	
	public SPM_Magento_LayByValidation(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);		
		this.dataTable2 = dataTable2;
	}
	@FindBy(xpath = "//a[normalize-space()='Details']")
    WebElement by_Detail;
	@FindBy(xpath = "//h1[normalize-space()='LayBy Application Details']")
    WebElement by_layByPage;
	@FindBy(xpath = "//select[@name='title']")
    WebElement by_title;
	@FindBy(xpath = "//input[@name='name']")
    WebElement by_firstName;
	@FindBy(xpath = "//input[@name='surname']")
    WebElement by_lastName;
	@FindBy(xpath = "//input[@name='contact_cell_number']")
    WebElement by_conCellNumber;
	@FindBy(xpath = "//input[@name='citizenship']")
    WebElement by_citizen;
	@FindBy(xpath = "//input[@name='id_type']")
    WebElement by_IDtype;
	@FindBy(xpath = "//input[@name='id_number']")
    WebElement by_IDno;
	@FindBy(xpath = "//select[@name='gender']")
    WebElement by_gender;
	@FindBy(xpath = "//input[@name='residential_building_complex']")
    WebElement by_build;
	@FindBy(xpath = "//input[@name='residential_type_of_dwelling']")
    WebElement by_ResType;
	@FindBy(xpath = "//input[@name='residential_home_ownership']")
    WebElement by_ResOwnership;
	@FindBy(xpath = "//input[@name='residential_street_no']")
    WebElement by_ResStreetNo;
	@FindBy(xpath = "//input[@name='email_address']")
    WebElement by_emailAddress;
	@FindBy(xpath = "//input[@name='employer_telephone']")
    WebElement by_employerTel;
	@FindBy(xpath = "//select[@name='subscribe_to_newsletter']")
    WebElement by_newLetter;
	
	
	//navigate to LayBy
	@FindBy(xpath = "//a[@href='#']//span[contains(text(),'Customers')]")
    WebElement by_cumstomers;
	@FindBy(xpath = "//a//span[contains(text(),'Layby Applications')]")
    WebElement by_appLayBy;
	
	//filter
	@FindBy(xpath = "//*[contains(text(),'Clear all')]")
    WebElement clearAll;
	@FindBy(xpath = "//*[@class=\"data-grid-filters-action-wrap\"]/button")
    WebElement filter;
	@FindBy(xpath = "//input[@name=\"contact_cell_number\"]")
    WebElement filterBy_cellNumber;
	@FindBy(xpath = "//*[contains(text(),'Apply Filters')]/parent::button")
    WebElement applyFilter;
	
	//delete Application
	@FindBy(xpath = "//button[@id='delete']")
	WebElement by_deleteApplication;
	@FindBy(xpath = "//aside[contains(@class,'_show')]//div[contains(@class,'modal-inner-wrap')]")
	WebElement by_deleteModal;
	@FindBy(xpath = "//div[contains(text(),'Are you sure you want to do this? This will remove')]")
	WebElement by_deleteMessageModal;
	@FindBy(xpath = "//button[contains(@class,'action-primary action-accept')]")
	WebElement by_deleteModalOK;
	@FindBy(xpath = "//div[@data-ui-id='messages-message-success']")
	WebElement by_deleteSuccess;
	
	
	public void navigateToLayBy(ExtentTest test) throws Exception {
		String timeOut = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "TimeOutInSeconds", 0);
	 	Integer timeOutInSeconds = Integer.parseInt(timeOut);
		action.click(by_cumstomers, "Click Customers", test);
		action.click(by_appLayBy, "Click LayBy Application", test);
		action.waitForPageLoaded(timeOutInSeconds);
        action.ajaxWait(timeOutInSeconds, test);
	}
	public void filterForLayBy(ExtentTest test) throws Exception {
		String cellNumToSearch= dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Mobile Number", 0);
			if(action.waitUntilElementIsDisplayed(clearAll, 8000)) {
				action.javaScriptClick(clearAll, "Clear All Filter", test);
				action.ajaxWait(ajaxTimeOutInSeconds, test);
				action.explicitWait(5000);
			}		 
			action.click(filter, "Filter", test);
			action.writeText(filterBy_cellNumber, cellNumToSearch , "Applicant Cell Number", test);
			action.click(applyFilter, "Apply Filters", test);
			
			action.ajaxWait(ajaxTimeOutInSeconds, test);
			action.explicitWait(5000);
		}
	
	public void validateLayBy(ExtentTest test) throws Exception {
		String timeOut = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "TimeOutInSeconds", 0);
	 	Integer timeOutInSeconds = Integer.parseInt(timeOut);
		String lybfirstName = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "First_Name", 0);
		String lyblastName = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Last_Name", 0);
		String lybtitle = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Title", 0);
		String lybcellNum= dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Mobile Number", 0);
		String lybcitizen = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Citizenship", 0);
		String lybidType = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Identification type", 0);
		String lybidORPass = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "IDorPassport", 0);
		String lybgender = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Gender", 0);
		String lybpermitNum = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Temporary Resident Permit No.", 0);
		String lybpermitNumExpiryDate = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Temporary Resident Permit Expiry Date", 0);
		String layByBuilding = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Complex/Building", 0);
		String typeResidence = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Type of Residence", 0);
		String homeOwnership = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Home Ownership", 0);
		String stAddress = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Street Address", 0);
		String conEmail = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Email Address", 0);
		String conEmployerNum = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "Employer Telephone Number", 0);
		
		navigateToLayBy(test);
		filterForLayBy(test);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		action.click(by_Detail, "Click To View All Details", test);
		action.waitForPageLoaded(timeOutInSeconds);
        action.ajaxWait(timeOutInSeconds, test);
        
        if(action.isElementPresent(by_layByPage)) {
        	action.CompareResult("The Title", action.getSelectedOptionFromDropDown(by_title), lybtitle, test);
        	action.CompareResult("The First Name", by_firstName.getText(), lybfirstName, test);
        	action.CompareResult("The Last Name", by_lastName.getText(), lyblastName, test);
        	action.CompareResult("The Contact Cell Number", by_conCellNumber.getText(), lybcellNum, test);
        	action.CompareResult("The Citizenship", by_citizen.getText(), lybcitizen, test);
        	action.scrollElemetnToCenterOfView(by_IDtype, "Scroll to ID type", test);
        	action.CompareResult("The ID Type", by_IDtype.getText(), lybidType, test);
        	action.scrollElemetnToCenterOfView(by_IDno, "Scroll to ID number", test);
        	action.CompareResult("The ID number", by_IDno.getText(), lybidORPass, test);
        	action.scrollElemetnToCenterOfView(by_gender, "Scroll to The Gender", test);
        	action.CompareResult("The Gender", action.getSelectedOptionFromDropDown(by_gender), lybgender, test);
        	action.scrollElemetnToCenterOfView(by_build, "Scroll to The Residential Building", test);
        	action.CompareResult("The Residential Building", by_build.getText(), layByBuilding, test);
        	action.scrollElemetnToCenterOfView(by_ResType, "Scroll to Residential Type Of Dwelling", test);
        	action.CompareResult("Residential Type Of Dwelling", by_ResType.getText(), typeResidence, test);
        	action.scrollElemetnToCenterOfView(by_ResOwnership, "Scroll to Residential Home Ownership", test);
        	action.CompareResult("Residential Home Ownership", by_ResOwnership.getText(), homeOwnership, test);
        	action.scrollElemetnToCenterOfView(by_ResStreetNo, "Scroll to Residential Street No", test);
        	action.CompareResult("Residential Street No", by_ResStreetNo.getText(), stAddress, test);
        	action.scrollElemetnToCenterOfView(by_emailAddress, "Scroll to Email Address", test);
        	action.CompareResult("Email Address", by_emailAddress.getText(), conEmail, test);
        	action.scrollElemetnToCenterOfView(by_employerTel, "Scroll to Employer Telephone", test);
        	action.CompareResult("Employer Telephone", by_employerTel.getText(), conEmployerNum, test);
        	action.scrollElemetnToCenterOfView(by_newLetter, "Scroll to Subscribe to newsletter", test);
        	action.CompareResult("Subscribe to newsletter", action.getSelectedOptionFromDropDown(by_newLetter), "Yes", test);
        }else {
        	throw new Exception("Couldn't validate");
        }
	}
	public void deleteLayByRecord(ExtentTest test) throws Exception {
		String timeOut = dataTable2.getValueOnOtherModule("SPM_LayByDetails", "TimeOutInSeconds", 0);
	 	Integer timeOutInSeconds = Integer.parseInt(timeOut);
		action.click(by_deleteApplication, "Click Delete Application", test);
		if(action.isElementPresent(by_deleteModal)) {
			action.CompareResult("Message Delete",by_deleteMessageModal.getText() , "Are you sure you want to do this? This will remove application from Magento!", test);
		}else {
			throw new Exception("Delete Message didn't appear");
		}
		action.click(by_deleteModalOK, "Click OK", test);
		action.waitForPageLoaded(timeOutInSeconds);
        action.ajaxWait(timeOutInSeconds, test);
        action.CompareResult("The Record Was Deleted", by_deleteSuccess.getText(), "LayBy Application deleted successfully.", test);
		
	}
}
