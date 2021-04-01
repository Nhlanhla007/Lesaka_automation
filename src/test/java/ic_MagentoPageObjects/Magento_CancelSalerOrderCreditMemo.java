package ic_MagentoPageObjects;

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

public class Magento_CancelSalerOrderCreditMemo {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	public Magento_CancelSalerOrderCreditMemo(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
		
	}
	MagentoOrderStatusPage magentoOrderStatusPage =new MagentoOrderStatusPage(driver, dataTable2);
	@FindBy(xpath = "/html/body/div[2]/main/div[1]/div[2]/div/div/button[@id='order_creditmemo']")
	private WebElement Credit_memo;
	@FindBy(xpath = "/html/body/div[2]/main/div[2]/div/div/form/div[2]/section[2]/div[2]/div[2]/div[3]/div[3]//button[@title='Refund Offline']")
	private WebElement Credit_Refund_offline;
	
	@FindBy(xpath = "//div[contains(text(),'You created the credit memo.')]")
	private WebElement Creationof_creditMemoMsg;
	
	@FindBy(xpath = "//table[@class='admin__table-secondary order-information-table']/tbody/tr/td//span[@id='order_status']")
	private WebElement OrderStatus_orderDetailPage;

	@FindBy(xpath = "	//span[@id='order_status']")
	private WebElement OrderStatusele;
	
	
	public void magento_CancelSalesOrder(ExtentTest test, int rowNumber) throws IOException{
		int Standardtimeout =21;
		String orderStatuscheck ="Closed";
		
			action.click(Credit_memo, "Credit Memo", test);
			boolean refundProcessFalg  = Verify_RefundOffline(test, Standardtimeout);
			if(refundProcessFalg){
				action.CompareResult("Order Status", orderStatuscheck, OrderStatusele.getText().trim(), test);	
			}else{
				action.CompareResult("Order Status", orderStatuscheck, OrderStatusele.getText().trim(), test);	
			}
	
	}
	
	
	
	public boolean Verify_RefundOffline(ExtentTest test, int timeout) throws IOException{
		boolean checkfalg =false;
		if(action.elementExists(Credit_Refund_offline, timeout)){
			action.click(Credit_Refund_offline, "Refund offline Button", test);
			if(action.elementExists(Creationof_creditMemoMsg, timeout)){
				checkfalg =true;
				action.CompareResult(" refund offline processed and Credit memo created", "true", String.valueOf(checkfalg), test);
			}else{
				
				action.CompareResult(" refund offline processed and Credit memo created", "true", String.valueOf(checkfalg), test);
			}
		}else{
			action.CompareResult(" refund offline processed and Credit memo created", "true", String.valueOf(checkfalg), test);
		}
		return checkfalg;
	}
	
}
