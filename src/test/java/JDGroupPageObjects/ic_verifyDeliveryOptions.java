package JDGroupPageObjects;

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

public class ic_verifyDeliveryOptions {
	
	WebDriver driver;
    Action action;
    
    public ic_verifyDeliveryOptions(WebDriver driver, DataTable2 dataTable2){
    	
    	this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
    	
    }
    
    @FindBy(xpath="//*[@id=\"label_method_111_matrixrate\"]")
    private WebElement ic_Deliver;
    
    @FindBy(xpath="//div[@id='label_method_clickandcollect_clickandcollect']")
    private WebElement ic_collect;
    
    public void validateDeliveryOptionsDisplays (ExtentTest test,int rowNumber) throws IOException{
    	String checkoutTitle = driver.getTitle();
    	
    	action.elementExists(ic_Deliver, 1000);
    	String delivery = action.getScreenShot("ic_Deliver");
    	//action.CompareResult("display different Delivery Options", delivery,delivery , test);
    	action.explicitWait(5000);
    	action.elementExists(ic_collect, 1000);
    	String collect = action.getScreenShot("ic_collect");
    	action.explicitWait(5000);
    	action.CompareResult("The Delivery and Collect are displaying", collect,collect , test);
    	action.CompareResult("Confirm the page title", checkoutTitle, driver.getTitle(), test);
    	
    	
    }

}
