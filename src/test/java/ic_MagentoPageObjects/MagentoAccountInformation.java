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

public class MagentoAccountInformation {
	WebDriver driver;
	Action action;

	public MagentoAccountInformation(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
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
			
			  public static String ActualBPnumber;
			  
			public void VadidateCustomerInfo_backend(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws IOException{
				//replace by this Parameter while merge parameter (ExtentTest test,String expFristname,String expLastname,String expEmail,String expSAID)
				int loadtime=20;

				String expFristname=input.get("firstName").get(rowNumber);
				String expLastname=input.get("lastName").get(rowNumber);
				String expEmail=input.get("emailAddress").get(rowNumber);
				String expSAID=input.get("identityNumber/passport").get(rowNumber);
				String expBPnumber =null;
			
				//Starts from Account information tab
				action.waitExplicit(loadtime);
				action.click(Account_Info_link, "Account Information", test);
				String ActualFirstname = action.getAttribute(customerFirstname, "value");
				String ActualLastname = action.getAttribute(customerLastname, "value");
				String ActualEmail = action.getAttribute(customerEmail, "value");
				ActualBPnumber = action.getAttribute(customerBPnnumber, "value");
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
			
			public String getPartnerNumber(ExtentTest test) throws Exception {
				action.click(Account_Info_link, "Account Information", test);
				ActualBPnumber = action.getAttribute(customerBPnnumber, "value");
				action.waitExplicit(10);
				boolean FlagGenerateBPnumber = false;
				if(ActualBPnumber!=null){
					FlagGenerateBPnumber = true;
					action.CompareResult("Verify the BP number of user in Magento :", String.valueOf(true),String.valueOf(FlagGenerateBPnumber)+"-BP no : "+ActualBPnumber.toString(), test);
					return ActualBPnumber = action.getAttribute(customerBPnnumber, "value");
				}else{
					action.CompareResult("Verify the BP number of user in Magento :", String.valueOf(true),String.valueOf(FlagGenerateBPnumber)+"-BP no : "+ActualBPnumber.toString(), test);
					return null;
			    }
				
			}
}
			