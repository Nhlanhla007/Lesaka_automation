package JDGroupPageObjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class IC_RetivedLoggedUserBillingAddress {
	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    public IC_RetivedLoggedUserBillingAddress(WebDriver driver,DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2=dataTable2;	
    }
    
    
    @FindBy(className = "my-account")
    private WebElement ic_myAccountButton;
    @FindBy(xpath = "//*[@id='header-slideout--0']/li[3]/a")
	WebElement ic_myAccountlist;
    
    @FindBy(xpath="//*[@id=\"account-nav\"]/ul/li[5]/a")
    private WebElement AddressBookEdit;
    
    @FindBy(xpath="//*[@id=\"maincontent\"]/div/div[2]/div[2]/div[2]/div[1]/div[2]/a/span")
    private WebElement ic_BillingAddress;
    
    @FindBy(xpath="//*[@id=\"street_1\"]")
    private WebElement ic_streetAddress;
    @FindBy(xpath="//*[@id=\"city\"]")
    private WebElement ic_city;
    @FindBy(xpath="//*[@id=\"zip\"]")
    private WebElement ic_postalCode;
    public static String streetAdd;
    public static String city;
    public static String postal;
    public void retriveLoggedUserBillingAddress(ExtentTest test) throws IOException{
    	navigateToBillingAddess(test);
    	if(action.elementExists(ic_streetAddress, 11)){
    		 streetAdd = action.getAttribute(ic_streetAddress, "value");
            ICDelivery.Streetname = streetAdd;
    		 city =action.getAttribute(ic_city, "value");
            ICDelivery.Cityname = city;
    		 postal = action.getAttribute(ic_postalCode, "value");
            ICDelivery.Postalcode = postal;
    		 if(streetAdd !="" & city!="" & postal!=""){
    			 action.CompareResult("Basic address details collected for logged In user Street: "+streetAdd+" city : "+city+" postalcode : "+postal, "True", "True", test);
    		 }else{
    			 action.CompareResult("Basic address details collected for logged In user Street: "+streetAdd+" city : "+city+" postalcode : "+postal, "True", "False", test); 
    		 }
    	}else{
    		action.CompareResult(" Navigate to collect User Billing Addresss", "True", "False", test);
    	}
    	
    }
    public void navigateToBillingAddess(ExtentTest test) throws IOException{
    	action.explicitWait(10000);
    	ic_myAccountButton.click();
        action.explicitWait(2000);
    	ic_myAccountlist.click();
    	action.explicitWait(5000);
    	action.click(AddressBookEdit, "AddressBookEdit", test);
    	if(action.elementExists(ic_BillingAddress, 11)){
    		action.click(ic_BillingAddress, "ic_BillingAddress", test);
    		action.CompareResult(" Navigate to collect User Billing Addresss", "True", "True", test);
    	}
    }
}