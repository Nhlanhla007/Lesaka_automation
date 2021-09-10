package ic_PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class IC_myTVLicense {
	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    
    public IC_myTVLicense(WebDriver driver, DataTable2 dataTable2){
    	this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2=dataTable2;
    	
    }
    
    @FindBy(xpath = "//div[contains(@class,'my-account icon')]")
    WebElement myAccountButton;
    
    @FindBy(xpath = "//*[@title=\"my-tv-licences\"]")
    WebElement myTVLicence;
    
    @FindBy(xpath = "//input[@data-bind=\"value: idNumInput\"]")
    WebElement myTv_ID;
    
    @FindBy(xpath = "//span[contains(text(),'Check Licence')]")
    WebElement myTv_CheckLicence;
    
    @FindBy(xpath = "//*[@id=\"customer-tvlicense-dashboard\"]/div[3]/div[2]/div/div")
    WebElement myTv_notValidatedBox;
    
    @FindBy(xpath = "//span[contains(text(),'We could not get your license info. Please try again later.')]")
    WebElement myTv_errorMessage;

    //@FindBy(xpath = "//span[contains(text(),'You have a valid TV license without outstanding amount.')]")
    @FindBy(xpath = "//*[@id=\"customer-tvlicense-dashboard\"]/div[3]/div[2]/div[1]/div")
    WebElement myTv_successBox;
    
    @FindBy(xpath = "//*[@id=\"customer-tvlicense-dashboard\"]/div[3]/div[2]/div")
    WebElement myTv_messageBox;
    
    
    @FindBy(xpath = "//strong[contains(text(),'R0')]")
    WebElement myTv_Amount;
    
    
    public void NavigateToMyTVlicence(ExtentTest test) throws Exception {
    	action.explicitWait(5000);
    	action.click(myAccountButton, "click on my account", test);
    	action.scrollElemetnToCenterOfView(myTVLicence, "scroll to My Licence", test);
    	action.click(myTVLicence, "click on My TV Licence", test);
    }
    
    public void MyTVlicenseValidation(ExtentTest test) throws Exception {
    	String identityNumb = dataTable2.getValueOnOtherModule("accountCreation", "identityNumber/passport", 0);
    	
    	action.explicitWait(5000);
    	NavigateToMyTVlicence(test);
    	action.explicitWait(10000);
    	action.waitForPageLoaded(20000);
    	
    	//String myId = action.getText(myTv_ID, "Check TV lic", test);
    	if (identityNumb.isEmpty()) {
			action.click(myTv_CheckLicence, "check Licence", test);
    	}  
    	else if(!(identityNumb.isEmpty())){
    		action.writeText(myTv_ID, identityNumb, "Write the ID", test);
    		action.click(myTv_CheckLicence, "check Licence", test);
    	} else{
    		throw new Exception("Couldn't validate new ID or Passport");
    	}
    	action.explicitWait(12000);
    	String messageValidate = action.getText(myTv_messageBox, "Did the ID validate?", test);
    	if(messageValidate.equalsIgnoreCase("You have a valid TV license without outstanding amount.")){
    		String smessage = "You have a valid TV license without outstanding amount.";
    		String sAmount = "R0";
    		action.CompareResult("Licence is valid", smessage, "You have a valid TV license without outstanding amount.", test);
    		action.explicitWait(5000);
    		action.CompareResult("The amount outstanding", sAmount, action.getText(myTv_Amount, "", test), test);	
    	} 
    	else if(messageValidate.equalsIgnoreCase("We could not get your license info. Please try again later.")){
    		String Emessage = "We could not get your license info. Please try again later.";
    		action.CompareResult("ID couldn't be validated", Emessage, "We could not get your license info. Please try again later.", test);
    	} else{
    		throw new Exception("Not expected message(s)");
    	}
    	
    }

}
