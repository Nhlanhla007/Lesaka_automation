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

public class Magento_CreditStatusVerification {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	public Magento_CreditStatusVerification(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	}
	@FindBy(xpath = "/html/body/div[2]/main/div/div/div/div/div[3]/table/tbody/tr[1]/td[7]")
	private WebElement CreditApp_Status;
	public void VerifyCreditAppStatus(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
		String ExpcreditStatus = dataTable2.getValueOnOtherModule("CreditStatusVerification", "FinalExpectedStatus", 0);
		int Timeout = Integer.parseInt(dataTable2.getValueOnCurrentModule("TimeOut_Inseconds"));
		int WaitTime =Integer.parseInt(dataTable2.getValueOnCurrentModule("WaitTime"));
		checkCreditAppStatus(test, ExpcreditStatus, Timeout, WaitTime);
	}
	public void checkCreditAppStatus(ExtentTest test, String ExporderStatus, int TimeOutInseconds, int RefreshInterval) throws IOException{
		boolean flagres = false;
    	
    	
    	long startTime = System.currentTimeMillis();
    	int TimeOutinSecond =TimeOutInseconds;
    	
    	
    	int elapsedTime = 0;
    	String ActOrderStatus="";
    	//--------------code---------
    	while(elapsedTime<=TimeOutinSecond && flagres==false){
    		
    		action.refresh();
			action.waitForPageLoaded(TimeOutinSecond);
			
				try {
					if(action.elementExists(CreditApp_Status, RefreshInterval))
					{
							ActOrderStatus = action.getText(CreditApp_Status, "Credit app status");
							action.scrollToElement(CreditApp_Status,"Credit app status");
							System.out.println("Credit status is  : "+ActOrderStatus);
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
    		action.CompareResult("Credit app status in MagentoAdmin", ExporderStatus, ActOrderStatus, test);
    	}else{
    		action.CompareResult("Credit app status in MagentoAdmin", ExporderStatus, ActOrderStatus, test);
    	}
    
    	
		
	}
}
