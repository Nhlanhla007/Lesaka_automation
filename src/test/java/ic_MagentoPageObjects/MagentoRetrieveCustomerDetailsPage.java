package ic_MagentoPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class MagentoRetrieveCustomerDetailsPage {

	WebDriver driver;
	Action action;
	DataTable2 dataTable2;

	public MagentoRetrieveCustomerDetailsPage(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;
	}

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
	List<WebElement> customerTableRecords;

	@FindBy(xpath = "//div/div[3]/div/div[3]/table/thead/tr/th")
	List<WebElement> customerTableHeaders;

	@FindBy(xpath = "//tbody/tr[2]/td[17]/a")
	WebElement viewCustomerDetails;

	public void navigateToCustomer(ExtentTest test) throws IOException, InterruptedException {
			action.click(customerTab, "Customer Tab", test);
			if(action.waitUntilElementIsDisplayed(allCustomerTab, 10000)) {
				action.click(allCustomerTab, "All Customers Tab", test);
			}
			Thread.sleep(10000);
	}

	public void searchForCustomer(String emailToSearchBy,ExtentTest test) throws IOException {
		boolean testallFlag=true;
		try {
			if (action.waitUntilElementIsDisplayed(clearFilters, 15000)) {
				action.click(clearFilters, "Cleared Filters", test);
				Thread.sleep(6000);
			}

			action.click(magentoFilterTab, "Filter tab", test);
			action.explicitWait(2000);
			action.clear(emailSearchField, "Email ID");
			action.explicitWait(2000);
			action.writeText(emailSearchField,emailToSearchBy,"Email search field" , test);
			action.explicitWait(2000);
			action.click(magentoApplyFilterTab, "Apply to filters", test);
			testallFlag=false;



		} catch (Exception e) {

			if(testallFlag==true){

				action.click(magentoFilterTab, "Filter tab", test);
				action.clear(emailSearchField, "Email ID");
				action.writeText(emailSearchField,emailToSearchBy,"Email search field" , test);
				action.click(magentoApplyFilterTab, "Apply to filters", test);

			}else{
				test.fail(e.getMessage());
			}
		}

	}

	public void retrieveCustomerDetails(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws Exception {
		String typeOfOperation = dataTable2.getValueOnOtherModule("Magento_UserInfoVerification", "Data Source", 0);
		//need to add distinction for
		String customerEmail;
		if(typeOfOperation.equalsIgnoreCase("Create Account")) {
		customerEmail = dataTable2.getValueOnOtherModule("accountCreation", "emailAddress", 0);//input.get("customerEmail").get(rowNumber);
		}//else if(typeOfOperation.equalsIgnoreCase("Create Account Magento Admin")) {
			//customerEmail = dataTable2.getValueOnOtherModule("CreateaccountBackend", "Email", 0);
		else if(typeOfOperation.equalsIgnoreCase("Update Account")) {
			customerEmail = dataTable2.getValueOnOtherModule("ICUpdateUser", "email_output", 0);
		}else if(typeOfOperation.equalsIgnoreCase("Registered customer from sales order")){
			customerEmail = dataTable2.getValueOnOtherModule("ic_login", "Username", 0);
		}else if(typeOfOperation.equalsIgnoreCase("Guest Customer Creation")){
			customerEmail = dataTable2.getValueOnOtherModule("deliveryPopulation", "email", 0);
		}else {
			customerEmail = "";
		}
		String webSite = dataTable2.getValueOnOtherModule("accountCreation", "WebSite", 0);//input.get("WebSite").get(rowNumber);
		System.out.println(customerEmail);
		navigateToCustomer(test);
		System.out.println("Hello from " + customerEmail);
		searchForCustomer(customerEmail, test);
		tableData(customerEmail, webSite, test);
		//confirmRows(customerTableRecords, test);
		try {
			//action.click(viewCustomerDetails, "View Customer details", test);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		System.out.println(totalRows);
		int totalColums = customerTableHeaders.size();
		System.out.println(totalColums);
		if(totalRows>=2) {
//				outerloop:
//				for(int i =2;i<=totalRows;i++) {
//					for(int j = 1;j<totalColums;j++) {
//						String emailColumn = driver.findElement(By.xpath("//tbody/tr["+i+"]/td[4]/div")).getText();
//						String webSite = driver.findElement(By.xpath("//tbody/tr["+i+"]/td[11]/div")).getText();
						WebElement clickEdit = driver.findElement(By.xpath("//tbody/tr[2]/td[17]/a"));
//						if(emailColumn.equalsIgnoreCase(email) & webSite.equalsIgnoreCase(webStore)) {
							//clickEdit.click();
							viewCustomerDetails(clickEdit, test);
//							break outerloop;
//						}

//					}
//				}
		}else {
			throw new Exception("No Records Returned");
			//action.noRecordsReturnedFromTable(test, "No Records were returned");
		}
	}

	public void viewCustomerDetails(WebElement clickElement,ExtentTest test) {
		try {
			//confirmRows(customerTableRecords, test);
			if (customerTableRecords.size() >= 1) {
				action.javaScriptClick(clickElement, "Customer Details", test);
				Thread.sleep(5000);
				action.checkIfPageIsLoadedByURL("/customer/index/edit/", "View Customer Details Page", test);
			} else {
				action.checkIfPageIsLoadedByURL("/customer/index/edit/", "Customer not found", test);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
