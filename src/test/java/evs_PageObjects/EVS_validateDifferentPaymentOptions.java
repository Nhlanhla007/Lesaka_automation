package evs_PageObjects;

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

public class EVS_validateDifferentPaymentOptions {
	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    
    public EVS_validateDifferentPaymentOptions(WebDriver driver, DataTable2 dataTable2){
    	this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2=dataTable2;
    	
    }
    
    public void validatePaymentOption(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws Exception{
    	String paymentOptions = dataTable2.getValueOnCurrentModule("PaymentOptions");
    	String [] paymentArray = paymentOptions.split("\\|");
    	action.explicitWait(9000);
    	for(String singlePayment: paymentArray){
    		WebElement paymentMethod = byPayments(singlePayment.trim());
    		action.explicitWait(5000);
        	action.click(paymentMethod, singlePayment, test);		
    	}
    
    }
    
    public WebElement byPayments(String typesOfPayments) {
    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		WebElement PaymentOptions = driver.findElement(By.xpath("//span[contains(text(),\""+typesOfPayments+"\")]"));//span[contains(text(),'FASTA Instant Credit')]
		return PaymentOptions;
	}
}
