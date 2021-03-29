package ic_MagentoPageObjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class Magento_CancelSalesorderVerification {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	public Magento_CancelSalesorderVerification(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
		
	}
	
	@FindBy(xpath = "//*[@id='sales_order_view_tabs_order_history']")
	private WebElement CommentHistoryTab;
	@FindBy(xpath = "//*[@id='Order_History']/section/ul[@class='note-list']/li[@class='note-list-item']//span[contains(text(),'Closed')]")
	private WebElement OrderStatusMsg;
	@FindBy(xpath = "//*[@id='Order_History']/section/ul[@class='note-list']/li[@class='note-list-item']//span[contains(text(),'Credit memo') and contains(text(),'created')]")
	private WebElement CreditmemoMsg;
	@FindBy(xpath = "//*[@id='Order_History']/section//div[contains(text(),'We refunded')]")
	private WebElement RefundValueMsg;
	public void verifyCancelOrderdetails_commentHistory(ExtentTest test, int rowNumber) throws IOException{
		boolean checkFinalstatus =false;
		int timeout=21;
		try {
			action.click(CommentHistoryTab, "CommentHistory Tab", test);
			if(action.elementExists(OrderStatusMsg, timeout)){
				String OderstatusOnCommentHistory = action.getText(OrderStatusMsg, "Order Status on comment history");
				action.CompareResult("Order status message on Comment History Tab", "Closed", OderstatusOnCommentHistory, test);
				
			
			
				if(action.elementExists(CreditmemoMsg, timeout)){
					String CreditmemoMsgOnCommentHistory = action.getText(CreditmemoMsg, "CreditmemoMsg on comment history");
					action.CompareResult("Credit memo message on Comment History Tab", "Credit memo", CreditmemoMsgOnCommentHistory, test);
					
					if(action.elementExists(RefundValueMsg, timeout)){
						String RefundAmtMsgOnCommentHistory = action.getText(RefundValueMsg, "RefundValueMsg on comment history");
						action.CompareResult("Refund amount message on Comment History Tab", "We refunded", RefundAmtMsgOnCommentHistory, test);
						checkFinalstatus =true;
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			action.CompareResult("Validate Order cancel related message on Comment History Tab", "True", "False", test);
		}
		if(checkFinalstatus){
			action.CompareResult("Verification of details in magento comment history", "True", "True", test);
		}else{
			action.CompareResult("Verification of details in magento comment history", "True", "False", test);
		}
		
	}
}
