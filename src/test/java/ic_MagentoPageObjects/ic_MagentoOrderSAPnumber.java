package ic_MagentoPageObjects;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    
    public void GenerateOrderSAPnumber(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException {
    	boolean flagres = false;
    	int totalConunter=1;
    	String OrderSAPnumber = "";
    	do{
    	System.out.println(totalConunter);
    	OrderSAPnumber = action.getText(OrderDetailSAPNumber, "SAP Number");
		action.scrollToElement(OrderDetailSAPNumber,"OrderDetailSAPNumber");
    	System.out.println(OrderSAPnumber);
    	if(OrderSAPnumber.length() <= 29){
    		action.explicitWait(Integer.parseInt(input.get("totalConunter").get(rowNumber))*1000);
    		action.refresh();
			System.out.println("not found on count:" + totalConunter);
    	}else{
    		flagres = true;
			System.out.println("OrderSAPnumber :" + OrderSAPnumber);
			input.get("OrderSAPnumber").set(rowNumber,OrderSAPnumber.replace("[RabbitMQ] Order SAP Number: ",""));
    	}
    		totalConunter++;
    	}while(totalConunter<=Integer.parseInt(input.get("totalCounter").get(rowNumber)) && !flagres);

		action.explicitWait(1000,test);

    }
}
