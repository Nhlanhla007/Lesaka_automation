package ic_MagentoPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.ExcelFunctions;

public class MagentoAccountInformation {
	WebDriver driver;
	Action action;
	HashMap<String, HashMap<String, ArrayList<String>>> workbook =null;
	public MagentoAccountInformation(WebDriver driver, HashMap<String, HashMap<String, ArrayList<String>>> workbook) {
		this.driver = driver;
		this.workbook = workbook;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		
	}
	
			//customer info after click on Customer info 
			
			  @FindBy(xpath = "//span[contains(text(),'Account Information')]")
			  WebElement Account_Info_link;
			
			  @FindBy(xpath = "//input[@name='customer[firstname]']")
			  WebElement customerFirstname;
			  @FindBy(xpath = "//input[@name='customer[lastname]']")
			  WebElement customerLastname;
			  @FindBy(xpath = "//input[@name='customer[email]']")
			  WebElement customerEmail;
			  @FindBy(xpath = "//input[@name='customer[partner_number]']")
			  WebElement customerBPnnumber;
			  @FindBy(xpath = "//input[@name='customer[identity_number]']")
			  WebElement customerIdentityNumber;
			
			public void VadidateCustomerInfo_backend(ExtentTest test,int testcaseID) throws IOException{
				//replace by this Parameter while merge parameter (ExtentTest test,String expFristname,String expLastname,String expEmail,String expSAID)
				int loadtime=20;
				HashMap<String, ArrayList<String>> accountCreationSheet = workbook.get("accountCreation++");
				 int rowNumber = -1;
				 rowNumber = findRowToRun(accountCreationSheet, 1, testcaseID);
				String expFristname=accountCreationSheet.get("firstName").get(rowNumber); 
				String expLastname=accountCreationSheet.get("lastName").get(rowNumber);
				String expEmail=accountCreationSheet.get("emailAddress").get(rowNumber);
				String expSAID=accountCreationSheet.get("identityNumber/passport").get(rowNumber);
				String expBPnumber =null;
			
				//Starts from Account information tab
				action.waitExplicit(loadtime);
				String ActualFirstname = action.getAttribute(customerFirstname, "value");
				String ActualLastname = action.getAttribute(customerLastname, "value");
				String ActualEmail = action.getAttribute(customerEmail, "value");
				String ActualBPnumber = action.getAttribute(customerBPnnumber, "value");
				String ActualIdentityNumber= action.getAttribute(customerIdentityNumber, "value");
				
				action.CompareResult("Verify the First name of user in Magento", expFristname, ActualFirstname, test);
				
				action.CompareResult("Verify the Last name of user in Magento", expLastname, ActualLastname, test);
				
				action.CompareResult("Verify the Email of user in Magento", expEmail, ActualEmail, test);
				
				action.CompareResult("Verify the SA ID number of user in Magento", expEmail, ActualEmail, test);
				boolean FlagGenerateBPnumber=false;
				if(ActualBPnumber!=null){
					FlagGenerateBPnumber=true;
					action.CompareResult("Verify the BP number of user in Magento :", String.valueOf(true),String.valueOf(FlagGenerateBPnumber)+"-BP no : "+ActualBPnumber.toString(), test);
					
				}else{
					action.CompareResult("Verify the BP number of user in Magento :", String.valueOf(true),String.valueOf(FlagGenerateBPnumber)+"-BP no : "+ActualBPnumber.toString(), test);
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
			