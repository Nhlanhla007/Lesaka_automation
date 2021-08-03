package evs_MagentoPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import evs_PageObjects.EVS_ProductSearch;
import utils.Action;
import utils.DataTable2;

public class evs_TVLicenceApproval {

	WebDriver driver;
	Action action;
	DataTable2 dataTable2;

	public evs_TVLicenceApproval(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;
	}
	
	 @FindBy(xpath="//div[contains(text(),'[RabbitMQ] Order SAP Number: ')][1]")
	    private WebElement OrderDetailSAPNumber;
	 
	 @FindBy(xpath="//*[@name=\"approve_decline\"]")
	    private WebElement admin_statuChange;
	 
	 @FindBy(xpath="//*[@name=\"approve_decline\"]/option[2]")
	    private WebElement admin_ApproveStatus;
	 
	 @FindBy(xpath="//*[@name=\"current_password\"]")
	    private WebElement admin_TVpassword;
	 
	 @FindBy(xpath="//*[@title=\"Update Account Validity\"]")
	    private WebElement admin_ButtonTVpassword;
	 
	 @FindBy(xpath="//*[@class=\"odd\"]/tr/td/div/div[2]/span")
	    private WebElement admin_NewTVlicenceSKU;
	 
	 @FindBy(xpath="//div[contains(text(),'Validity change was processed')]")
	    private WebElement admin_TVlicenseApprovedMessage;
	 
	 @FindBy(xpath="admin__table-secondary order-information-table")
	    private WebElement admin_TVOrderStatus;
	 
	 //66200001392 order number
	
	public void approveTVlicence(ExtentTest test,int rowNumber) throws Exception {
		String ApprovalPass = dataTable2.getValueOnCurrentModule("TV licence password Approval");
		String SKUTvLicence = dataTable2.getValueOnCurrentModule("TV licence SKU");
		
		action.explicitWait(30);
		action.scrollToElement(admin_NewTVlicenceSKU, "Status");
		action.CompareResult("Check if the new SKU tv licence is present", SKUTvLicence, action.getText(admin_NewTVlicenceSKU, "", test), test);
		
		action.scrollToElement(admin_statuChange, "Status");
		action.click(admin_statuChange, "choose the required status", test);
		action.click(admin_ApproveStatus, "Selecting Approve option status", test);
		action.writeText(admin_TVpassword, ApprovalPass, "Enter the approval password", test);
		action.click(admin_ButtonTVpassword, "Click Update Account Validity", test);
		String successMessage = action.getText(admin_TVlicenseApprovedMessage, "get the success message", test);
		
		if(successMessage.equalsIgnoreCase("Validity change was processed")){
			action.CompareResult("The licence was appoved", successMessage, action.getText(admin_TVlicenseApprovedMessage, "", test), test);
		} else{
			throw new Exception("The new licence has not been approve!");
		}
		
	/*	String TVorderStatus = action.getText(admin_TVOrderStatus, "Get the current order status", test);

		
		if(TVorderStatus != "Received Paid"){
			action.refresh();
			action.explicitWait(20000);
			
		}*/
		
		
	}
	
}
