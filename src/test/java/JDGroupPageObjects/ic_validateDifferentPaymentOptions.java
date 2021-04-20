package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class ic_validateDifferentPaymentOptions {
	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    
    public ic_validateDifferentPaymentOptions(WebDriver driver, DataTable2 dataTable2){
    	this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2=dataTable2;
    	
    }
    
    public void validatePaymentOption(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws Exception{
    	String typesOfPayments = dataTable2.getValueOnCurrentModule("Types_of_Payments");
    	
    	WebElement paymentMethod = byPayments(typesOfPayments);
    	
    	action.click(paymentMethod, "Select Payment type", test);
   
    }
    
    public WebElement byPayments(String typesOfPayments) {
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement PaymentOptions = driver.findElement(By.xpath("//span[contains(text(),\""+typesOfPayments+"\")]"));
		return PaymentOptions;
	}
}
