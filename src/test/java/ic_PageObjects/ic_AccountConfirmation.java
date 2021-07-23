package ic_PageObjects;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class ic_AccountConfirmation {
	

	  WebDriver driver;
	    Action action;

	    public ic_AccountConfirmation(WebDriver driver, DataTable2 dataTable2) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);

	    }
	    
	    @FindBy(xpath="//html/body/div[1]/header/div[3]/div[2]/div/div/div")
	    private WebElement WelcomePopUp;
	    
	    @FindBy(xpath="//html/head/meta[2]")
	    private WebElement TitlePage;
	    
	    @FindBy(xpath="//html/body/div[1]/header/div[2]/div/div[3]/div[2]/span")
	    private WebElement MyAccountButton;
	    
	    @FindBy(xpath="//*[@id=\"header-slideout--0\"]/li[2]")
	    private WebElement HelloMessage;
	    
	    @FindBy(xpath="//*[@id=\"header-slideout--0\"]/li[1]/a")
	    private WebElement HelloClosed;
	    
	    
	    public void AccountCreationConfirmation(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber){
	    	
	    	try {
				action.explicitWait(6000);

	    	String customerName = input.get("CustomerName").get(rowNumber);
	    	long time = 10000;
			action.elementExistWelcome(WelcomePopUp, time , "Account created", test);
	    	//action.CompareResult(TestDescription, Exp, Actual, test);

//			Thread.sleep(10000);
			action.waitForPageLoaded(20);
	    	action.checkIfPageIsLoadedByURL("/customer/account/", "verify account", test);	    	   	
	    	
				action.click(MyAccountButton, "ClickingOnMyAccountButton", test);
				action.CompareResult("usernameDisplayed", "Hello, "+ customerName, HelloMessage.getText(), test);
			
				action.click(HelloClosed, "close", test);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	
	    	}
	    	
	
}
