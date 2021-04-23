package ic_MagentoPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import JDGroupPageObjects.ic_PayUPayment;
import io.qameta.allure.Step;
import utils.Action;
import utils.ConfigFileReader;
import utils.DataTable2;

public class MagentoOrderStatusPage {

	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	public MagentoOrderStatusPage(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;
	}
	
	@FindBy(id = "menu-magento-sales-sales")
	public WebElement magentoSalesTab;
	
	@FindBy(xpath = "//body[1]/div[1]/nav[1]/ul[1]/li[3]/div[1]/ul[1]/li[1]/ul[1]/li[1]/div[1]/ul[1]/li[1]/a[1]/span[1]")
	public WebElement magentoOrderTab;
	
	@FindBy(xpath = "//button[contains(text(),'Filters')]")
	public WebElement magentoFilterTab;
	
	@FindBy(name = "increment_id")
	public WebElement magentoIdSearchField;
	
	@FindBy(xpath = "//span[contains(text(),'Apply Filters')]")
	public WebElement magentoApplyFilterTab;
	
	@FindBy(className = "data-row")
	public List<WebElement> magentoTableRecords;
	
	@FindBy(xpath = "//body/div[2]/main[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/div[3]/div[3]/button[1]")
	public WebElement clearFilters;
	
	@FindBy(xpath = "//tbody/tr[1]/td[10]/a[1]")
	public WebElement viewOrderDetails;
	
	@FindBy(xpath = "//tbody/tr[1]/td[9]/div")
	public WebElement magentoOrderStatus;
	
	public void click(WebElement elementAttr, ExtentTest test) {
		try {
			action.click(elementAttr, "NavigateToOrder",test);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void writeText(WebElement elementAttr,String text, ExtentTest test) {
		try {
			action.writeText(elementAttr, text, "Wrote text" +text,test);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void confirmRows(List<WebElement> elements,ExtentTest test) {
		System.out.println(elements.size());
		//action.expectSingleRow(elements, "Confirm single data row returned",test);
	}
	
	public void clickOnOrderStatus() {
		magentoTableRecords.get(0);
	}
	
	public void searchForOrder(String idToSearch, ExtentTest test) throws InterruptedException, IOException {
		if (action.isDisplayed(clearFilters)) {
			Thread.sleep(5000);
			action.click(clearFilters, "Cleared Filters", test);
			Thread.sleep(5000);
		}
		action.click(magentoFilterTab, "Filter tab", test);
		action.writeText(magentoIdSearchField, idToSearch, "searchId", test);
		action.click(magentoApplyFilterTab, "Apply to filters", test);
		// verify if a row is returned
		Thread.sleep(20000);
	}
	
	public void viewOrderDetails(ExtentTest test) throws IOException, InterruptedException {
		confirmRows(magentoTableRecords, test);
		if (magentoTableRecords.size() == 1) {
			action.click(viewOrderDetails, "Order Status", test);
			Thread.sleep(10000);
			action.checkIfPageIsLoadedByURL("sales/order/view/order_id/", "View Details Page", test);
		} else {
			action.checkIfPageIsLoadedByURL("sales/order/view/order_id/", "View Details Page", test);
		}
	}
	
	public void orderStatusCheck(String orderStatus, ExtentTest test) throws IOException {
		action.CompareResult("Order Status", orderStatus, magentoOrderStatus.getText(), test);
	}
	
	@Step("Navigates to the order page")
	public void navigateToOrderPage(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws IOException, InterruptedException {
//		String POfetchFrom = dataTable2.getValueOnOtherModule("OrderStatusSearch", "Fetch PO number", 0);

//		String idToSearch = dataTable2.getValueOnOtherModule("PayUPagePayment","OrderID",0);
		String idToSearch = dataTable2.getValueOnOtherModule("ic_RetriveOrderID","orderID",0);
//		if(POfetchFrom.equalsIgnoreCase("IC")) {
//			idToSearch= ic_PayUPayment.Oderid;
//		}else {
//			idToSearch = input.get("productSearchId").get(rowNumber);
//		}
		String orderStatus = input.get("orderStatus").get(rowNumber);
		System.out.println("orderStatus :"+orderStatus);
		action.explicitWait(15000);
		NavigateOdersPage(test);
		searchForOrder(idToSearch,test);
		orderStatusCheck(orderStatus, test);
		viewOrderDetails(test);
	}
	public void NavigateOdersPage(ExtentTest test) throws IOException, InterruptedException{
		action.click(magentoSalesTab, "Sales tab", test);
		action.click(magentoOrderTab, "Order tab", test);
		driver.navigate().refresh();
		Thread.sleep(5000);
	}
}
