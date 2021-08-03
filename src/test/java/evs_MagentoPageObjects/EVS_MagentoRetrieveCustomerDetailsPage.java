package evs_MagentoPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;


import Logger.Log;
import ic_MagentoPageObjects.ic_Magento_Login;
import utils.Action;
import utils.DataTable2;

public class EVS_MagentoRetrieveCustomerDetailsPage {

	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	int ajaxTimeOutInSeconds = EVS_Magento_Login.ajaxTimeOutInSeconds;
	public EVS_MagentoRetrieveCustomerDetailsPage(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;
	}
	
	Logger logger = Log.getLogData(this.getClass().getSimpleName());

	@FindBy(xpath = "//*[@class=\"admin__menu\"]/ul[@id='nav']/li[@id=\"menu-magento-customer-customer\"]/a/span[contains(text(),\"Customers\")]")
	WebElement customerTab;

	@FindBy(xpath = "//li[@role=\"menu-item\"]/a/span[contains(text(),'All Customers')]")
	WebElement allCustomerTab;

	@FindBy(xpath = "//button[contains(text(),'Filters')]")
	WebElement magentoFilterTab;

	@FindBy(xpath = "//div[@class='admin__data-grid-filters-current _show']/div[@class='admin__current-filters-actions-wrap']/button[@class='action-tertiary action-clear'][1]")
	WebElement clearFilters;

	@FindBy(name = "email")
	WebElement emailSearchField;

	@FindBy(xpath = "//span[contains(text(),'Apply Filters')]")
	public WebElement magentoApplyFilterTab;

	@FindBy(xpath = "//div/div[4]/table/tbody/tr")
	//@FindBy(className = "data-row")
	List<WebElement> customerTableRecords;
	
	//@FindBy(className = "data-row")
	//List<WebElement> customerTable;

	@FindBy(xpath = "//div/div[3]/div/div[3]/table/thead/tr/th")
	List<WebElement> customerTableHeaders;

	@FindBy(xpath = "//tbody/tr[2]/td[17]/a")
	WebElement viewCustomerDetails;

	public void navigateToCustomer(ExtentTest test) throws Exception {
		if (action.waitUntilElementIsDisplayed(customerTab, 10)) {
			action.click(customerTab, "Customer Tab", test);
		}
		if (action.waitUntilElementIsDisplayed(allCustomerTab, 10000)) {
			action.explicitWait(5000);
			action.click(allCustomerTab, "All Customers Tab", test);
		}
		action.explicitWait(5000);
		action.waitForPageLoaded(ajaxTimeOutInSeconds);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
	}

	public void searchForCustomer(String emailToSearchBy,ExtentTest test) throws Exception {
		try {
			if (action.waitUntilElementIsDisplayed(clearFilters, 60)) {
				action.javaScriptClick(clearFilters, "Cleared Filters", test);
				action.ajaxWait(ajaxTimeOutInSeconds, test);
			}
		}catch(Exception e){
			logger.info("Clear filter is not clickable");
		}
			action.click(magentoFilterTab, "Filter tab", test);
			action.clear(emailSearchField, "Email ID");
			action.writeText(emailSearchField,emailToSearchBy,"Email search field" , test);
			action.click(magentoApplyFilterTab, "Apply to filters", test);
			action.ajaxWait(ajaxTimeOutInSeconds, test);
			action.explicitWait(5000);

		//}
	}

	public void retrieveCustomerDetails(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws Exception {
		String typeOfOperation = dataTable2.getValueOnOtherModule("evs_Magento_UserInfoVerify", "Data Source", 0);
		//need to add distinction for
		String customerEmail;
		if(typeOfOperation.equalsIgnoreCase("Create Account")) {
		customerEmail = dataTable2.getValueOnOtherModule("evs_AccountCreation", "emailAddress", 0);//input.get("customerEmail").get(rowNumber);
		}//else if(typeOfOperation.equalsIgnoreCase("Create Account Magento Admin")) {
			//customerEmail = dataTable2.getValueOnOtherModule("CreateaccountBackend", "Email", 0);
		else if(typeOfOperation.equalsIgnoreCase("Update Account")) {
			customerEmail = dataTable2.getValueOnOtherModule("evs_UpdateUser", "email_output", 0);
		}else if(typeOfOperation.equalsIgnoreCase("Registered customer from sales order")){
			customerEmail = dataTable2.getRowUsingReferenceAndKey("URL", "SUTURLS",dataTable2.getValueOnOtherModule("evs_Login", "loginDetails", 0), "username");
		}else if(typeOfOperation.equalsIgnoreCase("Guest Customer Creation")){
			customerEmail = dataTable2.getValueOnOtherModule("evs_DeliveryPopulation", "email", 0);
		}else {
			customerEmail = "";
		}
		String webSite = "Main Website";//dataTable2.getValueOnOtherModule("evs_AccountCreation", "WebSite", 0);//input.get("WebSite").get(rowNumber);
		//System.out.println(customerEmail);
		navigateToCustomer(test);
		//System.out.println("Hello from " + customerEmail);
		searchForCustomer(customerEmail, test);
		tableData(customerEmail, webSite, test);
		//confirmRows(customerTableRecords, test);
	}


	/*
	 * public void confirmRows(ExtentTest test) {
	 * System.out.println(elements.size()); //action.expectSingleRow(elements,
	 * "Data has been returned",test); //for(WebElement row: elements) {
	 * //WebElement emailAdd = row.findElement(By.xpath("/td[4]/div"));
	 * //System.out.println(emailAdd); //} }
	 */


	public void tableData(String email,String webStore,ExtentTest test) throws Exception{
		int totalRows = customerTableRecords.size();
		//System.out.println(totalRows);
		int totalColums = customerTableHeaders.size();
		//System.out.println(totalColums);
		if(totalRows>=2) {
						WebElement clickEdit = driver.findElement(By.xpath("//*[@id=\"container\"]/div/div[4]/table/tbody/tr[2]/td[20]/a"));//tbody/tr[2]/td[17]/a
						viewCustomerDetails(clickEdit, test);
		}else {
			throw new Exception("No Records Returned");
		}
		action.waitForPageLoaded(ajaxTimeOutInSeconds);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
	}

	public void viewCustomerDetails(WebElement clickElement,ExtentTest test) throws Exception {
		//confirmRows(customerTableRecords, test);
		if (customerTableRecords.size() >= 1) {
			try {
			action.javaScriptClick(clickElement, "Customer Details", test);
			}catch(Exception e){
				driver.navigate().refresh();
				action.waitForPageLoaded(ajaxTimeOutInSeconds);
				action.ajaxWait(ajaxTimeOutInSeconds, test);
				action.javaScriptClick(clickElement, "Customer Details", test);
			}
			//action.explicitWait(15000);
			//action.checkIfPageIsLoadedByURL("/customer/index/edit/", "View Customer Details Page", test);
		}
	}
	
	public boolean isMagentoSearchPageLoaded(int timeoutInSec) throws Exception {
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);		
		int timer = 0;
		boolean status = false;
		boolean run = true;
		while (run) {
			if (timer <= timeoutInSec) {
				System.out.println("Running inside timer " + timer);				
				boolean isNoRecordsDisplayed = driver.findElements(By.xpath("//*[@class=\"data-grid-tr-no-datas\"]")).size() > 0;
				boolean isRecordsAvail = driver.findElements(By.xpath("//*[@class=\"data-rows\"]")).size() > 0;
				if (isNoRecordsDisplayed | isRecordsAvail) {
					status = true;
					run = false;					
				} else {
					timer += 1;
				}
			} else {
				run = false;
				throw new Exception("Time Exceeded for search");
			}
		}
		return status;
	}
 
	/*
	 * public boolean isMagentoSearchPageLoaded(int timeoutInSec) throws Exception {
	 * boolean status = false;
	 * if(action.attributeValidation(driver.findElement(By.xpath(
	 * "//*[@class=\"spinner\"]")), "style", "display: none;", 4000)) { return
	 * status = true; } return status; }
	 */
	
}
