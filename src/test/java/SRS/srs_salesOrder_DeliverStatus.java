package SRS;

import java.awt.AWTException;
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

public class srs_salesOrder_DeliverStatus {
	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    public srs_salesOrder_DeliverStatus(WebDriver driver,DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2=dataTable2;
    }
   
    @FindBy(xpath = "/html/body/table/tbody/tr[1]/td[2]/table/tbody/tr[1]/td[3]/a/strong[contains(text(),'Sales Order')]")
	 private WebElement SalerOrder;
    @FindBy(name="WISO_HNDL-DOC_NUMBER_SLCT")
	 private WebElement DocumentNumber;
    @FindBy(xpath ="/html/frameset/frame[2]")
	 private WebElement FrameSalesorder;
  
    @FindBy(xpath ="//*[@id='application-ZEccSRS-display']")
	 private WebElement FrameDeliverScreen;
    @FindBy(xpath = "//*[@id='button6t']")
	private WebElement DeliverBtn;
    @FindBy(xpath = "/html/body/form/table[2]/tbody/tr/td/table[2]/tbody/tr[2]/td[1]/input[@value='2']")
	private WebElement Customer_collectionBtn;
    public void verifySalesDeliverStatus(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException, AWTException{
    	String DocumentNo = input.get("PurchaseDocumentNo").get(rowNumber);//"0005234620";
    	
    	action.explicitWait(10000);
    	System.out.println("Start frame");    
    	action.switchToFrameUsingWebElement(FrameSalesorder);
    	System.out.println("Start test");    	
    	DocumentNumber.click();
    	action.Robot_WriteText(DocumentNo);
  
    	action.click(DeliverBtn, "Deliver Button", test);
    	action.explicitWait(10000);
    	action.switchToFrameUsingWebElement(FrameDeliverScreen);
    	boolean Cust_collectionFalgchecked = action.elementExists(Customer_collectionBtn, 11);
    	
    	if(Cust_collectionFalgchecked){
    		action.CompareResult(" Customer collection Button is Checked ", "true", String.valueOf(Cust_collectionFalgchecked), test);
    	}else{
    		action.CompareResult(" Customer collection Button is Checked ", "True",String.valueOf(Cust_collectionFalgchecked), test);
    	}
    }
}
