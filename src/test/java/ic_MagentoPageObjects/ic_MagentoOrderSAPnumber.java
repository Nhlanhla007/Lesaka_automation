package ic_MagentoPageObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import groovy.time.TimeDuration;
import utils.Action;
import utils.ConfigFileReader;

public class ic_MagentoOrderSAPnumber {
	WebDriver driver;
    Action action;
    
    public ic_MagentoOrderSAPnumber(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);

    }
    
    @FindBy(xpath="//div[contains(text(),'[RabbitMQ] Order SAP Number: ')][1]")
    private WebElement OrderDetailSAPNumber;
    
    Timer t = new Timer();
    
    public void GenerateOrderSAPnumber( ExtentTest test ){
    	
//    	String navigateURL = ConfigFileReader.getPropertyVal("URL");
//    	action.navigateToURL("https://staging-jdgroup-m23.vaimo.net/T5sjY7drHkyB6Z4n/sales/order/view/order_id/2297/key/5a2950d9fb153d87004762841a476070b240ea9fac19aefa18835be91cc06bd3/");
//
    	boolean flagres = false;
    	int timer= 10;
    	int totalConunter=1;
    	String OrderSAPnumber = "";
    	
    	do{
    	System.out.println(totalConunter);
    	OrderSAPnumber = action.getText(OrderDetailSAPNumber, "SAP Number");
    	System.out.println(OrderSAPnumber);
    	if(OrderSAPnumber.length() <= 29){
    		action.explicitWait(10);
    		action.refresh();
			System.out.println("not found on count:" + totalConunter);
    	}else{
    		flagres = true;
    	}

    		totalConunter++;
    	}while(totalConunter<=10 && !flagres);
    	//to update
    	System.out.println("OrderSAPnumber :" + OrderSAPnumber);
    		
    	
    }
}
