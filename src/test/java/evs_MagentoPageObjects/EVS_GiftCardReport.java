package evs_MagentoPageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.DataTable2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EVS_GiftCardReport {
	
	WebDriver driver;
    Action action;
	DataTable2 dataTable2;
	int ajaxTimeOutInSeconds = EVS_Magento_Login.ajaxTimeOutInSeconds;
    
	public EVS_GiftCardReport(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;
       
	}
	
	@FindBy(xpath="//*[@id=\"menu-magento-backend-marketing\"]/a")
	private WebElement admin_Marketing;
	
	@FindBy(xpath="//span[contains(text(),'Gift Card Accounts')]")
	private WebElement admin_GiftCardAccounts;
	
	@FindBy(xpath="//span[contains(text(),'Reset Filter')]")
	private WebElement admin_ResetFilter;
	
	@FindBy(xpath="//input[@id='giftcardaccountGrid_filter_code']")
	private WebElement admin_GiftCardCode;
	
	@FindBy(xpath="//*[@title=\"Search\"]/span")
	private WebElement admin_GiftCardSearch;
	
	//after gift card Purchase
	                
	@FindBy(xpath="//*[@id=\"giftcardaccountGrid_table\"]/tbody/tr[1]/td[3]")
	private WebElement admin_code;
	
	@FindBy(xpath="//*[@id=\"giftcardaccount_info_tabs_history\"]")
	private WebElement admin_GiftCardHistory;
	                //*[@id="giftcardaccountGrid_table"]/tbody/tr[1]/td[8]
	@FindBy(xpath="//*[@id=\"historyGrid_table\"]/tbody/tr[2]/td[3]")
	private WebElement admin_Action;
	
	@FindBy(xpath="//*[@id=\"historyGrid_table\"]/tbody/tr[2]/td[4]")
	private WebElement admin_GiftCardChangeBalance;
	
	@FindBy(xpath="//*[@id=\"historyGrid_table\"]/tbody/tr[2]/td[5]")
	private WebElement admin_GiftCardBalance;
	
	@FindBy(xpath="//*[@id=\"historyGrid_table\"]/tbody/tr[2]/td[6]")
	private WebElement admin_GiftCardMoreInfo;
	
	//after gift card redeem
	@FindBy(xpath="//*[@id=\"giftcardaccountGrid_table\"]/tbody/tr[1]/td[8]")
	private WebElement admin_StatusUsed;
	
	                //*[@id="historyGrid_table"]/tbody/tr[1]/td[3]
	@FindBy(xpath="//*[@id=\"giftcardaccountGrid_table\"]/tbody/tr[1]/td[3]")
	private WebElement admin_CodeUdsed;
	
	@FindBy(xpath="//*[@id=\"giftcardaccount_info_tabs_history\"]")
	private WebElement admin_ActionUsed;
					
	@FindBy(xpath="//*[@id=\"historyGrid_table\"]/tbody/tr[1]/td[4]")
	private WebElement admin_GiftCardBalanceChan;
					
	@FindBy(xpath="//*[@id=\"historyGrid_table\"]/tbody/tr[1]/td[5]")
	private WebElement admin_GiftCardBalanceLeft;
					
	@FindBy(xpath="//*[@id=\"historyGrid_table\"]/tbody/tr[1]/td[6]")
	private WebElement admin_GiftCardMoreInfoUsed;
	
	
	public void giftCardReports(ArrayList<HashMap<String, ArrayList<String>>> mySheet,ExtentTest test,int testcaseID) throws Exception{
		int sheetRow1= findRowToRun(mySheet.get(0), 0, testcaseID);
		
		String giftCardCode = mySheet.get(0).get("giftCardCode").get(sheetRow1);
		String giftCardStatus = mySheet.get(0).get("giftCardStatus").get(sheetRow1);
		String Action_Output = mySheet.get(0).get("Action_Output").get(sheetRow1);
		String balanceChange_Output = mySheet.get(0).get("balanceChange_Output").get(sheetRow1);
		String currentBalance_Output = mySheet.get(0).get("currentBalance_Output").get(sheetRow1);
		String OrderNum_Output = mySheet.get(0).get("OrderNum_Output").get(sheetRow1);
		action.click(admin_Marketing, "Click marketing", test);
		action.click(admin_GiftCardAccounts, "Click gift accounts", test);
		
		action.waitForPageLoaded(ajaxTimeOutInSeconds);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		
		action.click(admin_ResetFilter, "reset filter", test);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		action.writeText(admin_GiftCardCode, giftCardCode, "Get the gift card code", test);
		//action.explicitWait(10000);
		action.javaScriptClick(admin_GiftCardSearch, "we search", test);
		action.ajaxWait(ajaxTimeOutInSeconds, test);
		
		if(giftCardStatus.equalsIgnoreCase("Available")){
			action.click(admin_code, "click information", test);
			action.waitForPageLoaded(ajaxTimeOutInSeconds);
			action.ajaxWait(ajaxTimeOutInSeconds, test);
			action.click(admin_GiftCardHistory, "click History", test);
			action.ajaxWait(ajaxTimeOutInSeconds,test);
			Action_Output = action.getText(admin_Action, "value",test);
			mySheet.get(0).get("Action_Output").set(sheetRow1, Action_Output);
			/*balanceChange_Output = action.getText(admin_GiftCardChangeBalance, "value",test);
			mySheet.get(0).get("balanceChange_Output").set(sheetRow1, balanceChange_Output);
			currentBalance_Output = action.getText(admin_GiftCardBalance, "value",test);
			mySheet.get(0).get("currentBalance_Output").set(sheetRow1, currentBalance_Output);
			OrderNum_Output = action.getText(admin_GiftCardMoreInfo, "value",test);
			mySheet.get(0).get("OrderNum_Output").set(sheetRow1, OrderNum_Output);*/
		}
		if(giftCardStatus.equalsIgnoreCase("Used")){
			action.click(admin_CodeUdsed, "click information", test);
			action.click(admin_GiftCardHistory, "click History", test);
			action.ajaxWait(ajaxTimeOutInSeconds,test);
			Action_Output = action.getText(admin_ActionUsed, "value",test);
			mySheet.get(0).get("Action_Output").set(sheetRow1, Action_Output);
			/*balanceChange_Output = action.getText(admin_GiftCardBalanceChan, "value",test);
			mySheet.get(0).get("balanceChange_Output").set(sheetRow1, balanceChange_Output);
			currentBalance_Output = action.getText(admin_GiftCardBalanceLeft, "value",test);
			mySheet.get(0).get("currentBalance_Output").set(sheetRow1, currentBalance_Output);
			OrderNum_Output = action.getText(admin_GiftCardMoreInfoUsed, "value",test);
			mySheet.get(0).get("OrderNum_Output").set(sheetRow1, OrderNum_Output);*/
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

