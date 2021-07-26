package ic_PageObjects;

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

public class ic_CashDepositPayment {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
//	ic_Magento_Login Magentologin;
//	MagentoOrderStatusPage magentoOrderStatusPage;
	public ic_CashDepositPayment(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
//        Magentologin =new ic_Magento_Login(driver, dataTable2);
//        magentoOrderStatusPage = new MagentoOrderStatusPage(driver, dataTable2);
	}
	
	
	@FindBy(xpath = "//p[contains(text(),'Your order # is')]")
	WebElement OderID;
	
	@FindBy(xpath = "//*[@id='order_invoice']/span[text()='Invoice']")
	WebElement Invoice_Tab;
	
	@FindBy(xpath = "//*[@title='Submit Invoice']/span[text()='Submit Invoice']")
	WebElement SubmitInvoice;
	@FindBy(xpath = "//*[@id='messages']//div[text()='The invoice has been created.']")
	WebElement CreateInvoiceSuccessMsg;
	
	@FindBy(xpath = "//table[@class='admin__table-secondary order-information-table']//tr//td//span[@id='order_status']")
	WebElement OrderStatus;
//	public String RetriveOrderID(ExtentTest test) throws IOException {
//		String Oderid = null;
//		action.isElementOnNextPage(OderID, (long) 11,test);
//	    Oderid = action.getText(OderID, "Order ID");
//	    Oderid = Oderid.replace("Your order # is: ","").replace(".","");
//		return Oderid;
//	}
	public void InvoiceCashDeposit(ArrayList<HashMap<String, ArrayList<String>>> RequiredSheets,ExtentTest test,int testcaseID) throws IOException{
		int CurrentSheetRow = findRowToRun(RequiredSheets.get(0), 0, testcaseID);
		int LoginsheetRow = findRowToRun(RequiredSheets.get(1), 0, testcaseID);
		String ExpOrderStatus = "Received Paid";
		try{
//			String OrderID = dataTable2.getValueOnOtherModule("ic_RetriveOrderID","orderID",0);
//			System.out.println("OrderID found is :"+OrderID);
//			//RequiredSheets.get(1).get("Retrived OrderID").set(LoginsheetRow,OrderID.replace("Your order # is: ","").replace(".",""));
//			String Username = RequiredSheets.get(1).get("Username").get(LoginsheetRow);
//			String Password = RequiredSheets.get(1).get("Password").get(LoginsheetRow);
//			Magentologin.LoginToMagento(test, Username, Password);
//			System.out.println("login success");
//			magentoOrderStatusPage.NavigateOdersPage(test);
//			magentoOrderStatusPage.searchForOrder(OrderID, test);
//			magentoOrderStatusPage.viewOrderDetails(test);
			MagentoAdminDoInvoiceing(test);
			MagentoOrderInfoVerification(test, ExpOrderStatus);
		}catch(Exception e){
			e.printStackTrace();
		}
	
		
		
	}
	
	public void MagentoOrderInfoVerification(ExtentTest test,String ExpOrderstatus) throws Exception{
		
		//try {
			action.scrollToElement(OrderStatus, "OrderStatus");
			String ActorderStatus = action.getText(OrderStatus,"OrderStatus",test);
			if(ActorderStatus.equalsIgnoreCase(ExpOrderstatus)){
				action.CompareResult("verify Order ID status ", ExpOrderstatus, ActorderStatus, test);
			}else{
				action.CompareResult("verify Order ID status ", ExpOrderstatus, ActorderStatus, test);
			}
		//} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
	
		
	}
	public void MagentoAdminDoInvoiceing(ExtentTest test) throws Exception{
		//try {
			action.click(Invoice_Tab, "Invoice_Tab", test);
			action.waitForElementPresent(SubmitInvoice, 21);
			action.scrollToElement(SubmitInvoice,"SubmitInvoice");
			action.click(SubmitInvoice, "SubmitInvoice", test);
			action.waitForElementPresent(CreateInvoiceSuccessMsg, 21);
			String createInvoicemsg = action.getText(CreateInvoiceSuccessMsg, "Create Invoice Success Message",test);
			if(createInvoicemsg.contains("invoice has been created")){
				action.CompareResult("Verify Invoicing Done successfully : ", "true", "true", test);
			}else{
				action.CompareResult("Verify Invoicing Done successfully : ", "true", "false", test);
			}
		//} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
	
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
