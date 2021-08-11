package ic_MagentoPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.aventstack.extentreports.MediaEntityBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import ic_PageObjects.ic_PayUPayment;
import utils.Action;
import utils.ConfigFileReader;
import utils.DataTable2;
import utils.GenerateScreenShot;

public class MagentoOrderStatusPage {

	WebDriver driver;
	Action action;
	DataTable2 dataTable2;

	int ajaxTimeOutInSeconds = ic_Magento_Login.ajaxTimeOutInSeconds;
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
	
	//Bundle Article Info below
    @FindBy(xpath = "//table[@class='admin__table-secondary order-information-table']/tbody/tr/td//span[@id='order_status']")
    public WebElement OrderStatus_orderDetailPage;

    
    @FindBy(xpath = "/html/body/div[2]/main/div[2]/div[1]/div/div[1]/div[1]/section[4]/div[2]/table/tbody/tr[1]/td[1]/div[2]")
    public WebElement listSKU;
	
	
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
	
	public void searchForOrder(String idToSearch, ExtentTest test) throws Exception {
		action.waitForPageLoaded(ajaxTimeOutInSeconds);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		if (action.waitUntilElementIsDisplayed(clearFilters,10)) {
			action.javaScriptClick(clearFilters, "Cleared Filters", test);
			action.ajaxWait(ajaxTimeOutInSeconds, test);
		}
		action.javaScriptClick(magentoFilterTab, "Filter tab", test);
		action.writeText(magentoIdSearchField, idToSearch, "searchId", test);
		action.javaScriptClick(magentoApplyFilterTab, "Apply to filters", test);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
	}
	
	public void viewOrderDetails(ExtentTest test) throws Exception {
		boolean ajaxLoadCompleted = action.ajaxWait(ajaxTimeOutInSeconds, test);
		confirmRows(magentoTableRecords, test);
		if (magentoTableRecords.size() >= 1) {
			if(!ajaxLoadCompleted) {
				driver.navigate().refresh();
				action.waitForPageLoaded(ajaxTimeOutInSeconds);
				action.ajaxWait(ajaxTimeOutInSeconds, test);
				ExtentTest node = test.createNode("Reloading the Search Page");
				/*String screenShot = GenerateScreenShot.getScreenShot(driver);
	            node.info("Page Reload Completed"+ node.addScreenCaptureFromPath(screenShot));*/
				node.info("Page Reload Completed", MediaEntityBuilder.createScreenCaptureFromBase64String(action.takeScreenShotAsBase64()).build());
				action.javaScriptClick(viewOrderDetails, "Order Status", test);
			}
			else{
				action.javaScriptClick(viewOrderDetails, "Order Status", test);
			}

			//action.checkIfPageIsLoadedByURL("sales/order/view/order_id/", "View Details Page", test);
		} else {
			//action.checkIfPageIsLoadedByURL("sales/order/view/order_id/", "View Details Page", test);
		}
	}
	
	public void orderStatusCheck(String orderStatus, ExtentTest test) throws Exception {
		action.explicitWait(10000);
		action.waitForJStoLoad(ajaxTimeOutInSeconds);
		//if (magentoTableRecords.size() >= 1) {
		action.CompareResult("Order Status", orderStatus, magentoOrderStatus.getText(), test);
		//}else {
			//throw new Exception("No Records Are Returned");
		//}
	}
	
	
    //public List<String> AllSKU;
    public Boolean isSKUPresent ;
    public String AllSKU = "";
	public void navigateToOrderPage(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws Exception, InterruptedException {
//		String POfetchFrom = dataTable2.getValueOnOtherModule("OrderStatusSearch", "Fetch PO number", 0);
//		String idToSearch = dataTable2.getValueOnOtherModule("PayUPagePayment","OrderID",0);
		String idToSearch = dataTable2.getValueOnOtherModule("ic_RetriveOrderID","orderID",0);//"3100002010";
//		if(POfetchFrom.equalsIgnoreCase("IC")) {
//			idToSearch= ic_PayUPayment.Oderid;
//		}else {
//			idToSearch = input.get("productSearchId").get(rowNumber);
//		}
		String orderStatus = input.get("orderStatus").get(rowNumber);
		System.out.println("orderStatus :"+orderStatus);
		//action.explicitWait(15000);
		
		action.waitForPageLoaded(ajaxTimeOutInSeconds);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		
		NavigateOdersPage(test);
		searchForOrder(idToSearch,test);
		orderStatusCheck(orderStatus, test);
		viewOrderDetails(test);
		
		//Validate SKU for bundle article
		//NavigateTo_OrderdetailsPage(test);		
		
		action.waitForPageLoaded(ajaxTimeOutInSeconds);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		isSKUPresent = driver.findElements(By.xpath("/html/body/div[2]/main/div[2]/div[1]/div/div[1]/div[1]/section[4]/div[2]/table/tbody/tr[1]/td[1]/div[2]")).size()>0;
		dataTable2.setValueOnCurrentModule("IsBundleArticleSKUPresent", String.valueOf(isSKUPresent));
		if(isSKUPresent) {
		VerifyOrderStatus(test, orderStatus, ajaxTimeOutInSeconds, 5);
		action.scrollElemetnToCenterOfView(listSKU, "SKU", test);
		String skudata = action.getText(listSKU, "listSKU",test);
		if (skudata.contains("SKU:")) {
			String[] arraySKU = skudata.replace("-", "").split("\n");
			System.out.println(arraySKU.length);
			//AllSKU = new ArrayList<String>();
			for (int i = 0; i < arraySKU.length; i++) {
				int SKUsize = arraySKU[i].toString().trim().length();
				String SKUValue = arraySKU[i].toString().trim();
				int spitSKU= SKUValue.indexOf("000");
				SKUValue = SKUValue.substring(spitSKU);
				/*
				 * for(String value : spitSKU) { SKUValue = value; }
				 */
				//AllSKU.add(SKUValue);
				AllSKU += SKUValue + "#";
				if (SKUsize == 18 & i == 1) {								
					action.CompareResult("SKU Code was found in Magento", "true", "true", test);
					}
				}
			}
		}
		dataTable2.setValueOnCurrentModule("BundleArticleSKU", AllSKU);
		//else {
			//action.CompareResult("SKU code not found", "True", "False", test);		
		//input.get("SKU").set(rowNumber, "".join(",", AllSKU));
		//dataTable2.setValueOnCurrentModule("BundleArticleSKU", AllSKU);
		//}
	}


	
	public void NavigateOdersPage(ExtentTest test) throws Exception {
		if(action.waitUntilElementIsDisplayed(magentoSalesTab, 10000)) {
		action.click(magentoSalesTab, "Sales tab", test);
		}
		if(action.waitUntilElementIsDisplayed(magentoOrderTab, 10000)) {
		action.click(magentoOrderTab, "Order tab", test);
		}
		//driver.navigate().refresh();
		//Thread.sleep(5000);
	}
	
    public void VerifyOrderStatus(ExtentTest test, String ExporderStatus, int TimeOutInseconds, int RefreshInterval) throws IOException{



        boolean flagres = false;

        long startTime = System.currentTimeMillis();
        int TimeOutinSecond =TimeOutInseconds;



        int trycount =RefreshInterval;

        int elapsedTime = 0;



        String ActOrderStatus="";



        //--------------code---------



        while(elapsedTime<=TimeOutinSecond && flagres==false){
            action.refresh();
            action.waitForPageLoaded(TimeOutinSecond);
                try {
                    if(action.elementExists(OrderStatus_orderDetailPage, RefreshInterval))
                    {
                            ActOrderStatus = action.getText(OrderStatus_orderDetailPage, "OrderStatus_orderDetailPage",test);
                            action.scrollToElement(OrderStatus_orderDetailPage,"OrderStatus_orderDetailPage");
                            System.out.println("ActOrderStatus is  : "+ActOrderStatus);
                            if(ActOrderStatus.equalsIgnoreCase(ExporderStatus)){
                                flagres = true;
                                break;
                            }
                    }
                } catch (Exception e) {

                }
                long endTime = System.currentTimeMillis();
                long elapsedTimeInMils = endTime-startTime;
                elapsedTime = ((int) elapsedTimeInMils)/1000;
                System.out.println("elapsedTime: "+elapsedTime);        
             
        }
        if(flagres==true){
            action.CompareResult(" Order Status on Order Details Page in MagentoAdmin", ExporderStatus, ActOrderStatus, test);
        }else{
            action.CompareResult(" Order Status on Order Details Page in MagentoAdmin", ExporderStatus, ActOrderStatus, test);

        }

    }

	
}
