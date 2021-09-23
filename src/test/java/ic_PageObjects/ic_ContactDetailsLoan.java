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

public class ic_ContactDetailsLoan {
	
	 WebDriver driver;
	    Action action;
	    DataTable2 dataTable2;

	    public ic_ContactDetailsLoan(WebDriver driver, DataTable2 dataTable2) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
	        this.dataTable2= dataTable2;

	    }
	    
	    
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.contactDetails__fieldset\"]/div/div[1]/ol/div[1]/label/span/span")
	    private WebElement ic_RadioBankAccYes;
	    
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.contactDetails__fieldset\"]/div/div[1]/ol/div[2]/label/span/span")
	    private WebElement ic_RadioBankAccNo;
	    
	    @FindBy(xpath="//select[@ name=\"bank_name\"]")
	    private WebElement ic_bankName;
	    
	    @FindBy(xpath="//input[@ name=\"contact_cell_number\"]")
	    private WebElement ic_contactCellNumber;
	    
	    @FindBy(xpath="//input[@ name=\"contact_email_address\"]")
	    private WebElement ic_contactEmailAddress;
	    
	    @FindBy(xpath="//select[@ name=\"correspondence_language\"]")
	    private WebElement ic_contactCorreLanguage;
	    
	    @FindBy(xpath="//input[@name=\"accept_tncs\"]")
	    private WebElement ic_contactTermsAccept;
	    
	    @FindBy(xpath="//input[@name=\"subscribe\"]")
	    private WebElement ic_contactNewsLetterSub;
	    
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.contactDetails__fieldset\"]/div/div[7]/div[1]/button[2]/span")
	    private WebElement ic_Submit;
	    
	    
	    public void enterContactDetailsForLoan(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
	    	
	    	String BankAccountAvailability = dataTable2.getValueOnCurrentModule("BankAccountAvailability");
	    	String BankName = dataTable2.getValueOnCurrentModule("BankName");
	    	String contactCellNumber = dataTable2.getValueOnCurrentModule("contactCellNumber"); 
	    	String contactEmailAddress = dataTable2.getValueOnCurrentModule("contactEmailAddress");
	    	String contactCorreLAnguage = dataTable2.getValueOnCurrentModule("contactCorreLAnguage");
	    	String contactSubscribeFlag = dataTable2.getValueOnCurrentModule("contactSubscribeFlag");
	    	
	    	if(BankAccountAvailability.equalsIgnoreCase("Yes")){
	    		action.click(ic_RadioBankAccYes, "Click the radiao button", test);
	    		action.writeText(ic_bankName,BankName , "Bank name", test);
	    	} 
	    	if((BankAccountAvailability.equalsIgnoreCase("No"))){
	    	action.click(ic_RadioBankAccNo, "Click the radiao button", test);
	    	}
	    	action.writeText(ic_contactCellNumber,contactCellNumber , "contact cell number", test);
	    	action.writeText(ic_contactEmailAddress,contactEmailAddress , "contact email address", test);
	    	action.writeText(ic_contactCorreLanguage,contactCorreLAnguage , "Correspondence Language", test);
	    	action.javaScriptClick(ic_contactTermsAccept, "Terms and Conditions", test);
			/*
			 * if((contactSubscribeFlag.equalsIgnoreCase("Yes"))){
			 * action.javaScriptClick(ic_contactNewsLetterSub, "News letter", test); }
			 */
	    	action.scrollElemetnToCenterOfView(ic_Submit, "scroll to submit", test);
	    	action.click(ic_Submit, "Submit the loan Request", test);
	    	action.waitForPageLoaded(30);
	    		
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

