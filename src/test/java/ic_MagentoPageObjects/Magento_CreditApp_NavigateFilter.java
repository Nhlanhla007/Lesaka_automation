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

public class Magento_CreditApp_NavigateFilter {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	public Magento_CreditApp_NavigateFilter(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	}
	
	@FindBy(xpath = "//li[@id='menu-magento-customer-customer']")
	private WebElement Customer;
	@FindBy(xpath = "/html/body/div[1]/nav/ul/li[5]/div/ul/li[1]/ul/li[6]/div/ul/li/a/span[contains(text(),'Credit Applications')]")
	private WebElement Credit_app;
	
	
	@FindBy(xpath = "//h1[contains(text(),'Credit Applications')]")
	private WebElement CreditApplicationPg;
	
	@FindBy(xpath = "/html/body/div[2]/main/div/div/div/div/div[2]/div[1]/div[3]/div[3]/button")
	private WebElement Clear_all;
	@FindBy(xpath = "//button[contains(text(),'Filters')]")
	private WebElement Filter;
	

	@FindBy(xpath = "/html/body/div[2]/main/div/div/div/div/div[2]/div[1]/div[4]/fieldset/div[6]/div/input")
	private WebElement ContactEmail;
	
	@FindBy(xpath = "//span[contains(text(),'Apply Filters')]")
	private WebElement Apply_Filter;
	private static int waiton;
	public void VerifyCreditAppSelection(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException, InterruptedException{
		waiton =21;
		String ExpEmail = dataTable2.getValueOnCurrentModule("EmailIdtoFilter");
		System.out.println(ExpEmail);
		Navigate_To_CreditApp(test);
		action.waitExplicit(waiton);
		SearchForCreditAppByEmail(ExpEmail, test);
		boolean Clearfilterstatus = action.elementExists(Clear_all, waiton);
		boolean NormalFilter =action.elementExists(Filter, waiton);
		if(Clearfilterstatus && NormalFilter){
			action.CompareResult("Application filtered out properly", "true", String.valueOf(Clearfilterstatus), test);
		}else{
			action.CompareResult("Application filtered out properly", "true", String.valueOf(Clearfilterstatus), test);
		}
		
	}
	public void SearchForCreditAppByEmail(String EmailIDtoSearch, ExtentTest test) throws IOException, InterruptedException{
		
				if (action.isDisplayed(Clear_all)) {
					action.click(Clear_all, "Clear_all Filters", test);
					Thread.sleep(5000);
				}
				action.click(Filter, "Filter tab", test);
				action.writeText(ContactEmail, EmailIDtoSearch, "EmailID to Search", test);
				action.click(Apply_Filter, "Apply to filters", test);
				
				Thread.sleep(20000);
		
		
	}
	public void Navigate_To_CreditApp(ExtentTest test) throws IOException{
		boolean checkFlag=false;
		action.click(Customer, "Customer tab ", test);
		if(action.elementExists(Credit_app, waiton)){
			action.click(Credit_app, "Credit_app", test);
			if(action.elementExists(CreditApplicationPg, waiton)){
				checkFlag=true;
			}
		}
		
		action.CompareResult("Verify Navigates to credit application", "true",String.valueOf(checkFlag), test);
	}
}
