package ic_MagentoPageObjects;

import java.io.IOException;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;

public class Magento_UserInfoVerification {
	
	WebDriver driver;
	Action action;
	public Magento_UserInfoVerification(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
	}
	@FindBy(xpath = "//div[@id='container']//span[contains(text(),'Account Information')]")
	WebElement Account_Information;
	@FindBy(xpath = "//input[@name='customer[firstname]']")
	WebElement Cust_Firstname;
	@FindBy(xpath = "//input[@name='customer[lastname]']")
	WebElement Cust_Lastname;
	@FindBy(xpath = "//input[@name='customer[email]']")
	WebElement Cust_Email;
	@FindBy(xpath = "//input[@name='customer[identity_number]']")
	WebElement Cust_SAID;
	@FindBy(xpath = "//input[@name='customer[passport_number]']")
	WebElement Cust_Passport;
	////input[@name="customer[taxvat]"]
	@FindBy(xpath = "//input[@name='customer[taxvat]']")
	WebElement Cust_VAT;
	
	@FindBy(xpath = "//a[@id='tab_newsletter_content']")
	WebElement Tab_NewsLetter;
	
	@FindBy(xpath = "//input[@id='_newslettersubscription']")
	WebElement Cust_NewsLetter;
	public void Validate_UserInfobackend(ExtentTest test) throws IOException{
		int TimetoLoadpage=11;
		//Vat Number,ID,Passport,Newsletter,No newsletter
		String verficationFlag ="Vat Number";
		
		
		String ExpFirstname="Brian";
		String ExpLastname="Jones";
		String ExpEmail="BrenoFernandesMalingaPatrick8@armyspy.com";
		
		String ExpVATnumber="2222224";
		String ExpPassportnumber="5311266534086";
		String ExpSAIDnumber="5311266534086";
		
		action.clickEle(Account_Information, "Account_Information", test);
		//basic verification
		String ActFirstname = FetchDataFromCustInfo_MagentoBackend(Cust_Firstname, "Customer_Firstname", 11, 2, test);
		action.CompareResult("Verify the First name in Magento backend : ", ExpFirstname, ActFirstname, test);
		
		String ActLastname = FetchDataFromCustInfo_MagentoBackend(Cust_Lastname, "Custome_Firstname", 11, 2, test);
		action.CompareResult("Verify the Last name in Magento backend : ", ExpLastname, ActLastname, test);
		
		String ActEmailname = FetchDataFromCustInfo_MagentoBackend(Cust_Email, "Customer_Email", 11, 2, test);
		action.CompareResult("Verify the Email in Magento backend : ", ExpEmail, ActLastname, test);
		
		
		
		
		//conditional verification on basis of verficationFlag
		switch (verficationFlag) {
			case "Vat Number":
				String ActVAT = FetchDataFromCustInfo_MagentoBackend(Cust_VAT, "Customer_VAT", 11, 2, test);
				action.CompareResult("Verify the VAT number in Magento backend : ", ExpVATnumber, ActVAT, test);
			case "ID":
				String ActSAID = FetchDataFromCustInfo_MagentoBackend(Cust_SAID, "Customer_SAID", 11, 2, test);
				action.CompareResult("Verify the SAID number in Magento backend : ", ExpSAIDnumber, ActSAID, test);
			case "Passport":
				String ActPassport = FetchDataFromCustInfo_MagentoBackend(Cust_VAT, "Customer_VAT", 11, 2, test);
				action.CompareResult("Verify the Passport number in Magento backend : ", ExpPassportnumber, ActPassport, test);
			case "Newsletter":
				String ActNewsletteres="";
				action.click(Tab_NewsLetter, "Click Tab_NewsLetter", test);
				boolean checknewsletter =action.elementExists(Cust_NewsLetter, TimetoLoadpage);
				if(checknewsletter==true){
					 ActNewsletteres =action.getAttribute(Cust_NewsLetter, "value");
					action.CompareResult("Verify the Newsletter subscription : ", "true",String.valueOf(ActNewsletteres), test);
				}else{
					action.CompareResult("Verify the Newsletter subscription : ", "true", String.valueOf(ActNewsletteres), test);
				}
				
				
			case "No newsletter":
				String ActNonewsletter="";
				action.click(Tab_NewsLetter, "Click Tab_NewsLetter", test);
				boolean checknewsletter1=action.elementExists(Cust_NewsLetter, TimetoLoadpage);
				if(checknewsletter1==true){
					ActNonewsletter =action.getAttribute(Cust_NewsLetter, "value");
					action.CompareResult("Verify the No Newsletter subscription : ", "false",String.valueOf(ActNonewsletter), test);
				}else{
					action.CompareResult("Verify the No Newsletter subscription : ", "false", String.valueOf(ActNonewsletter), test);
				}
				
		}
		
	}
	public String FetchDataFromCustInfo_MagentoBackend(WebElement element,String elename,int TimetoLoadpage,int TotalTrycount,ExtentTest test) throws IOException{
		int trycount=1;
		String resData="";
		while(trycount<=TotalTrycount & resData.length()<1){
			action.refresh();
			action.waitForPageLoaded(TimetoLoadpage);
			action.click(Account_Information, "Account_Information", test);
			if(action.elementExists(element, TimetoLoadpage)==true){
				resData = action.getAttribute(element, "value");
			}
			
			trycount++;
		}
		if(!resData.isEmpty() ||resData!=null){
			
			action.CompareResult("Verify "+elename+" is fetched sucessfully :"+resData,"True", "True", test);
			return resData;
		}else{
			action.CompareResult("Verify "+elename+" is fetched sucessfully :"+resData,"True", "False", test);
			return resData;
		}
	}
	
	
	
}
