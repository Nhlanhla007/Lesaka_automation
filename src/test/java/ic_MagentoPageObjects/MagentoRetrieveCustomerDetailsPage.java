package ic_MagentoPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;

public class MagentoRetrieveCustomerDetailsPage {

	WebDriver driver;
	Action action;
	
    HashMap<String, HashMap<String, ArrayList<String>>> workbook =null;
	
	public MagentoRetrieveCustomerDetailsPage(WebDriver driver,HashMap<String, HashMap<String, ArrayList<String>>> workbook) {
		this.driver = driver;
		this.workbook = workbook;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
	}
	
	@FindBy(xpath = "//*[@class=\"admin__menu\"]/ul[@id='nav']/li[@id=\"menu-magento-customer-customer\"]/a/span[contains(text(),\"Customers\")]")
	WebElement customerTab;

	@FindBy(xpath = "//li[@role=\"menu-item\"]/a/span[contains(text(),'All Customers')]")
	WebElement allCustomerTab;

	@FindBy(xpath = "//button[contains(text(),'Filters')]")
	WebElement magentoFilterTab;

	@FindBy(xpath = "//body/div[2]/main[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/div[3]/div[3]/button[1]")
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
	
	public void navigateToCustomer(ExtentTest test) {
		try {
			action.click(customerTab, "Customer Tab", test);
			action.click(allCustomerTab, "All Customers Tab", test);
			Thread.sleep(10000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void searchForCustomer(String emailToSearchBy,ExtentTest test) {
		try {
			if (action.isDisplayed(clearFilters)) {
				action.click(clearFilters, "Cleared Filters", test);
				Thread.sleep(5000);
			}
			action.click(magentoFilterTab, "Filter tab", test);
			action.writeText(emailSearchField,emailToSearchBy,"Email search field" , test);
			action.click(magentoApplyFilterTab, "Apply to filters", test);

		} catch (Exception e) {
			e.printStackTrace();
		} 
	
	}
	
	public void retrieveCustomerDetails(ExtentTest test,int testcaseID) {

		//ADDED HERE
		 HashMap<String, ArrayList<String>> accountCreationSheet = workbook.get("accountCreation++");
		 int rowNumber = -1;
		 rowNumber = findRowToRun(accountCreationSheet, 1, testcaseID);
		 
		String customerEmail = accountCreationSheet.get("customerEmail").get(rowNumber);
		String webSite = accountCreationSheet.get("WebSite").get(rowNumber);
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
	
	
	public void tableData(String email,String webStore,ExtentTest test) {
		int totalRows = customerTableRecords.size()-1;
		System.out.println(totalRows);
		int totalColums = customerTableHeaders.size();
		System.out.println(totalColums);
		if(totalRows>=2) {
		try {
			outerloop:
			for(int i =2;i<=totalRows;i++) {
				for(int j = 1;j<totalColums;j++) {
					String emailColumn = driver.findElement(By.xpath("//tbody/tr["+i+"]/td[4]/div")).getText();
					String webSite = driver.findElement(By.xpath("//tbody/tr["+i+"]/td[11]/div")).getText();
					WebElement clickEdit = driver.findElement(By.xpath("//tbody/tr["+i+"]/td[17]/a"));
					
					if(emailColumn.equalsIgnoreCase(email) & webSite.equalsIgnoreCase(webStore)) {
						//clickEdit.click();
						viewCustomerDetails(clickEdit, test);
						break outerloop;
					}
					
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}else {
			action.noRecordsReturnedFromTable(test, "No Records were returned");
		}
	}
	
	public void viewCustomerDetails(WebElement clickElement,ExtentTest test) {
		try {
			//confirmRows(customerTableRecords, test);
			if (customerTableRecords.size() >= 1) {
				action.click(clickElement, "Customer Details", test);
				Thread.sleep(5000);
				action.checkIfPageIsLoadedByURL("/customer/index/edit/", "View Customer Details Page", test);
			} else {
				action.checkIfPageIsLoadedByURL("/customer/index/edit/", "Customer not found", test);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public int findRowToRun(HashMap<String, ArrayList<String>> input,int occCount,int testcaseID){
		int numberRows=input.get("TCID").size();
		int rowNumber=-1;
		occCount=occCount+1;
		for(int i=0;i<numberRows;i++){
			if(input.get("TCID").get(i).equals(Integer.toString(testcaseID))&&input.get("occurence").get(i).equals(Integer.toString(occCount))){
				rowNumber=i;
			}
		}
		return rowNumber;
	}
}
