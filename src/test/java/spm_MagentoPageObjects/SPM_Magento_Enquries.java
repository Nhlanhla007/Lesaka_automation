package spm_MagentoPageObjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class SPM_Magento_Enquries {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	public SPM_Magento_Enquries(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);		
		this.dataTable2 = dataTable2;
	}
	@FindBy(xpath = "//*[@class=\"admin__menu\"]/ul[@id='nav']/li[@id=\"menu-magento-customer-customer\"]/a/span[contains(text(),\"Customers\")]")
	WebElement customerTab;
	
	@FindBy(xpath = "//span[contains(text(),\"Enquiries\")]/parent::a")
	WebElement enquiryTab;

    @FindBy(name = "email")
    WebElement emailFilterField; 
    
    @FindBy(xpath = "//button[contains(text(),'Clear all')]")
    public WebElement clearFilters;
    
    @FindBy(xpath = "//button[contains(text(),'Filters')]")
    public WebElement magentoFilterTab;
	
    @FindBy(xpath = "//span[contains(text(),'Apply Filters')]")
    public WebElement magentoApplyFilterTab;
    
	@FindBy(xpath = "//*[@class=\"admin__data-grid-wrap\"]/table/thead/tr/th")
	List<WebElement> allTableHeaders;
	
	@FindBy(xpath = "//*[@class=\"admin__data-grid-wrap\"]/table/tbody/tr/td")
	List<WebElement> allTableData;
    
	public void navigateToEnquiries(ExtentTest test) throws Exception {
		//Replace 20 with ajaxTimeOut
		String emailAddress = dataTable2.getValueOnOtherModule("SPM_Equiries","Email",0);
		action.waitUntilElementIsDisplayed(customerTab, 20);
		action.click(customerTab, "Customer Tab", test);
		action.waitUntilElementIsDisplayed(enquiryTab, 20);
		action.scrollElementIntoView(enquiryTab);
		action.javaScriptClick(enquiryTab, "Enquiry Tab", test);
		action.waitForJStoLoad(20);
		action.ajaxWait(20, test);
		action.ajaxWait(20, test);
		action.ajaxWait(20, test);			
		magnetoFilter(emailFilterField, emailAddress, test);
	
		//THIS CAN BE PLACED IN A SEPERATE REUSABLE METHOD
//		boolean isProductsRetruned = driver.findElements(By.xpath("//*[@class=\"data-row\"]")).size()>0;
//		if(isProductsRetruned) {
//			action.click(driver.findElement(By.xpath("//*[@class=\"data-row\"]")), "Navigate To Enquiry Details", test);
//		}else {
//			throw new Exception("No Enquires have been returned");
//		}
	}
	
	void magnetoFilter(WebElement fieldToWrite,String textToWrite,ExtentTest test) throws Exception {
		//Replace 20 with ajaxTimeOut
        action.waitForPageLoaded(20);
        action.ajaxWait(20, test);
        if (action.waitUntilElementIsDisplayed(clearFilters, 10)) {
            action.javaScriptClick(clearFilters, "Cleared Filters", test);
            action.ajaxWait(20, test);
        }
        action.javaScriptClick(magentoFilterTab, "Filter tab", test);
        action.writeText(fieldToWrite, textToWrite, "searchId", test);
        action.explicitWait(3000);
        action.click(magentoApplyFilterTab, "Apply to filters", test);
        action.ajaxWait(20, test);
        action.explicitWait(5000);
	}
	
	String headerValue;
	String tableValue;
	public void userEquiryDetails(ExtentTest test) throws Exception {
		String enqiName = dataTable2.getValueOnOtherModule("SPM_Equiries","Name",0);
		String emailAddress = dataTable2.getValueOnOtherModule("SPM_Equiries","Email",0);
		String phoneNumber = dataTable2.getValueOnOtherModule("SPM_Equiries","Phone",0);
		String enquiryMsg = dataTable2.getValueOnOtherModule("SPM_Equiries","Message",0);
		//Add all text into a look and look over it 
		//Add loop over all headers store the index in array than go loop over that array with the index and find elements
		for(int i = 1;i<allTableHeaders.size();i++) {
			headerValue = allTableHeaders.get(i).findElement(By.xpath(".//span")).getText();
			tableValue = allTableData.get(i).findElement(By.xpath(".//div")).getText();
			switch(headerValue.toLowerCase().trim()) {
			case"name":
				//Find the td equivalent and validate
				action.CompareResult("Enquiry Name", enqiName, tableValue, test);
				break;
			case"email":
				action.CompareResult("Enquiry Email Address", emailAddress, tableValue, test);
				break;
			case"telephone":
				action.CompareResult("Enquiry Phone Number", phoneNumber, tableValue, test);
				break;
			case"comment":
				action.CompareResult("Enquiry Comment Message", enquiryMsg, tableValue, test);
				break;
				default:
					continue;
					
			}
		}
		//Store all this data into a map
		//Loop through all of the headers, add a switch that matches the names(Name,Email,Telephone,Message/Comment)
		//if the case matches than store that case name with the index in a map MIGHT NOT NEED THE MAP
		
		//Use the MAP go into the <td> and using the index and go the relevant <td>
	}
}
