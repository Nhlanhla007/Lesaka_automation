package evs_MagentoPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

import org.openqa.selenium.JavascriptExecutor;
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
    EVS_MagentoOrderSAPnumber generateReceived;
    int ajaxTimeOutInSeconds = EVS_Magento_Login.ajaxTimeOutInSeconds;

    public evs_TVLicenceApproval(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
        generateReceived = new EVS_MagentoOrderSAPnumber(driver, dataTable2);
    }

    @FindBy(xpath = "//div[contains(text(),'[RabbitMQ] Order SAP Number: ')][1]")
    private WebElement OrderDetailSAPNumber;

    @FindBy(xpath = "//*[@name=\"approve_decline\"]")
    private WebElement admin_statuChange;

    @FindBy(xpath = "//*[@name=\"approve_decline\"]/option[2]")
    private WebElement admin_ApproveStatus;

    @FindBy(xpath = "//*[@name=\"current_password\"]")
    private WebElement admin_TVpassword;

    @FindBy(xpath = "//*[@title=\"Update Account Validity\"]")
    private WebElement admin_ButtonTVpassword;

    @FindBy(xpath = "//*[@class=\"odd\"]/tr/td/div/div[2]")
    private WebElement admin_NewTVlicenceSKU;

    @FindBy(xpath = "//div[contains(text(),'Validity change was processed')]")
    private WebElement admin_TVlicenseApprovedMessage;

    @FindBy(xpath = "admin__table-secondary order-information-table")
    private WebElement admin_TVOrderStatus;

    @FindBy(xpath = "//*[@class=\"odd\"]/tr/td/div/div[2]/span")
    private WebElement admin_NewTVlicenceHover;
    
    @FindBy(xpath = " //*[@id=\"order_status\"]")
    private WebElement admin_ChangedStatusRecieved;
    
 

    public void approveTVlicence(ExtentTest test, int rowNumber) throws Exception {
        String ApprovalPass = dataTable2.getValueOnCurrentModule("TV licence password Approval");
        String SKUTvLicence = dataTable2.getValueOnCurrentModule("TV licence SKU");

        action.explicitWait(5000);
        try {
            action.scrollElemetnToCenterOfView(admin_NewTVlicenceHover, "Status", test);
            String SKUnewTvli = action.getText(admin_NewTVlicenceSKU, "", test).trim();
            SKUnewTvli.replace("SKU:", "");
            action.CompareResult("is the new tv lic SKU", SKUTvLicence.trim(), SKUnewTvli.trim(), test);
            action.scrollElemetnToCenterOfView(admin_statuChange, "Select", test);
            action.explicitWait(3000);
            action.click(admin_statuChange, "choose the required status", test);
            action.explicitWait(5000);
            action.click(admin_ApproveStatus, "Selecting Approve option status", test);
            action.ajaxWait(10, test);
            action.writeText(admin_TVpassword, ApprovalPass, "Enter the approval password", test);
            action.javaScriptClick(admin_ButtonTVpassword, "Click Update Account Validity", test);
            action.ajaxWait(10, test);
            action.explicitWait(3000);
            String expSuccessMessage = "Validity change was processed";
            if (admin_TVlicenseApprovedMessage.isDisplayed()) {
                String actualSuccessMessage = action.getText(admin_TVlicenseApprovedMessage, "get the success message", test);
                action.CompareResult("The licence was appoved", actualSuccessMessage, expSuccessMessage, test);
            }
        } catch (Exception e) {
            throw new Exception("The new licence has not been approved! " + e.getMessage());
        }
        
       // GenerateRecieveStatus(test);
        
    }
        
       /* Timer t = new Timer();
        public void GenerateRecieveStatus(ExtentTest test) throws Exception {
        boolean flagres = false;
    	int totalConunter=0;
    	String Receivedstatus = "";
    	long startTime = System.currentTimeMillis();
    	int TimeOutinSecond =Integer.parseInt(dataTable2.getValueOnCurrentModule("TimeOutforRecievePaidStatus"));
    	//int trycount =Integer.parseInt(input.get("totalCounter").get(rowNumber));
    	int elapsedTime = 0;    	
    	while(elapsedTime<=TimeOutinSecond && flagres==false)
    	{
			action.refresh();
			action.waitForPageLoaded(TimeOutinSecond);
			
			try {
				if(action.waitUntilElementIsDisplayed(admin_ChangedStatusRecieved, 2000)){						
					Receivedstatus = admin_ChangedStatusRecieved.getText();//action.getText(OrderDetailSAPNumber, "SAP Number",test);
						//action.scrollToElement(OrderDetailSAPNumber,"OrderDetailSAPNumber");
						System.out.println(Receivedstatus);
					if(Receivedstatus !="Pending SABC Validation"){
			    		action.explicitWait(TimeOutinSecond);
			    		action.refresh();
			    	}else{
			    		flagres = true;			    		
						System.out.println("Show :" + Receivedstatus);
			    	}
				}else{
					System.out.println("The Receive status does not exist");
				}
					
			} catch (Exception e) {
				
			}

			long endTime = System.currentTimeMillis();
			long elapsedTimeInMils = endTime-startTime;
			elapsedTime = ((int) elapsedTimeInMils)/1000;
			System.out.println("elapsedTime: "+elapsedTime);
			totalConunter++;
		}
    	if(flagres){
    		action.scrollElemetnToCenterOfView(admin_ChangedStatusRecieved,"Receive Status",test);
    		action.CompareResult("Is Receive Status visible ?", String.valueOf(true), String.valueOf(flagres), test);
    	}else{
    		JavascriptExecutor exe = (JavascriptExecutor)driver;
            exe.executeScript("window.scrollTo(0, document.body.scrollHeight)");
            exe.executeScript("window.scrollBy(0,-500)");
    		action.CompareResult("Is Receive Status visible ?", String.valueOf(true), String.valueOf(flagres), test);
    	}
    	System.out.println();
    }*/
        
          

    }
