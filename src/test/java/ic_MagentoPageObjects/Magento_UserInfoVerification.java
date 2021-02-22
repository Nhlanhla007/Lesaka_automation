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

public class Magento_UserInfoVerification {
	
	WebDriver driver;
	Action action;
	MagentoRetrieveCustomerDetailsPage MagentoRetrieveCustomer;
	public Magento_UserInfoVerification(WebDriver driver) {
		this.driver = driver;
		MagentoRetrieveCustomer = new MagentoRetrieveCustomerDetailsPage(driver);
		PageFactory.initElements(driver, this);
		action = new Action(driver);
	}
	//@FindBy(xpath = "//span[contains(text(),'Account Information')]") 
	@FindBy(xpath = "//*[@id='page:left']/div/div/ul/li[@class='admin__page-nav-item']")
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
	public void Validate_UserInfobackend(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
		int TimetoLoadpage=11;	
		String ExpWebsite =input.get("WebSite").get(rowNumber);
		String ExpFirstname=input.get("firstName").get(rowNumber);//"Brian";
		String ExpLastname=input.get("lastName").get(rowNumber);//"Jones";
		String ExpEmail=input.get("emailAddress").get(rowNumber);//"BrenoFernandesMalingaPatrick8@armyspy.com";
	
		//new variables flag  identityType on ID and passport
		String ExpidentityType =input.get("identityType").get(rowNumber);//"ID";
		String ExpPassportnumber=input.get("identityNumber/passport").get(rowNumber);//"5311266534086";
		String ExpSAIDnumber=input.get("identityNumber/passport").get(rowNumber);//"5311266534086";
		
		//new variable flag on newsletter
		String ExpNewsletterFalg = input.get("newsletter").get(rowNumber);//"Yes";
		String NewletterArgs = "";
		
		//VAT validation
		String vatNumberFlag=input.get("vatNumberFlag").get(rowNumber);//"Yes";
		String ExpVATnumber=input.get("vatNumber").get(rowNumber);//"2222224";
		
		//code block---------------------------------------
		MagentoRetrieveCustomer.navigateToCustomer(test);
		MagentoRetrieveCustomer.searchForCustomer(ExpEmail, test);
		MagentoRetrieveCustomer.tableData(ExpEmail, ExpWebsite, test);
		action.clickEle(Account_Information, "Account_Information", test);
		//basic verification--------------------------------------------------------------------------------------
		String ActFirstname = FetchDataFromCustInfo_MagentoBackend(Cust_Firstname, "Customer_Firstname", 11, 2, test);
		action.CompareResult("Verify the First name in Magento backend : ", ExpFirstname, ActFirstname, test);
		
		String ActLastname = FetchDataFromCustInfo_MagentoBackend(Cust_Lastname, "Custome_Firstname", 11, 2, test);
		action.CompareResult("Verify the Last name in Magento backend : ", ExpLastname, ActLastname, test);
		
		String ActEmailname = FetchDataFromCustInfo_MagentoBackend(Cust_Email, "Customer_Email", 11, 2, test);
		action.CompareResult("Verify the Email in Magento backend : ", ExpEmail, ActEmailname, test);
		//validate ID or passport is entered basis of identity type flag..
		switch (ExpidentityType) {
			case "ID":
				
				String ActSAID = FetchDataFromCustInfo_MagentoBackend(Cust_SAID, "Customer_SAID", 11, 2, test);
				action.CompareResult("Verify the SAID number in Magento backend : ", ExpSAIDnumber, ActSAID, test);
				break;
			case "Passport":
				String ActPassport = FetchDataFromCustInfo_MagentoBackend(Cust_Passport, "Customer_Passport", 11, 2, test);
				action.CompareResult("Verify the Passport number in Magento backend : ", ExpPassportnumber, ActPassport, test);
				break;
		
		}
		//------Basic verification ends here------------------------------------
		//validate news letter depending on ExpNewsletterFalg...
		if(ExpNewsletterFalg.equalsIgnoreCase("Yes")){
			NewletterArgs ="Newsletter";
		}else{
			NewletterArgs ="No newsletter";
		}
		
		switch (NewletterArgs) {
			case "Newsletter":
				String ActNewsletteres="";
				action.click(Tab_NewsLetter, "Click Tab_NewsLetter", test);
				boolean checknewsletter =action.elementExists(Cust_NewsLetter, TimetoLoadpage);
				if(checknewsletter==true){
					 ActNewsletteres =action.getAttribute(Cust_NewsLetter, "value");
					action.CompareResult("Verify the Newsletter subscription is Checked  : ", "true",String.valueOf(ActNewsletteres), test);
				}else{
					action.CompareResult("Verify the Newsletter subscription is Checked: ", "true", String.valueOf(ActNewsletteres), test);
				}
				
				break;
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
				break;
		
		}
		//validate the VAT/Tax number
		if(vatNumberFlag.equalsIgnoreCase("Yes")){
			String ActVAT = FetchDataFromCustInfo_MagentoBackend(Cust_VAT, "Customer_VAT", 11, 2, test);
			action.CompareResult("Verify the VAT number in Magento backend : ", ExpVATnumber, ActVAT, test);
		}
		
		
		
		/*
		//conditional verification on basis of verficationFlag
		switch (verficationFlag) {
			case "Vat Number":
				
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
				
		}*/
		
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
