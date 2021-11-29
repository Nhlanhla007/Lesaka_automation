package spm_PageObjects;

import com.aventstack.extentreports.ExtentTest;
import ic_MagentoPageObjects.MagentoOrderStatusPage;
import ic_MagentoPageObjects.ic_Magento_Login;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.DataTable2;

import java.io.IOException;

public class SPM_RetriveOrderID {
    WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    public SPM_RetriveOrderID(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2=dataTable2;
    }

    @FindBy(xpath = "//*[@class='order-number']/span")
    WebElement OrderIDRegisteredUser;
    
    @FindBy(xpath = "//*[@class='checkout-success']/p/span")
    WebElement OrderIDGuestUser;
    
    

    public void RetriveOrderID(ExtentTest test) throws IOException, Exception {
    	String typeOfUser = dataTable2.getValueOnOtherModule("SPM_DeliveryPopulation", "UserType", 0).trim();
        String orderID = null;
        action.waitForJStoLoad(40);
        action.explicitWait(5000);
        
        if(typeOfUser.equalsIgnoreCase("Registered")) {
        	action.waitUntilElementIsDisplayed(OrderIDRegisteredUser, 15000);
            orderID = action.getText(OrderIDRegisteredUser, "Order ID",test);
            orderID = orderID.replace("Your order # is: ","").replace(".","");
            action.CompareResult("Order Id "+orderID+" has been retrieved ", "true", "true", test);
            dataTable2.setValueOnCurrentModule ("orderID",orderID);
            dataTable2.setValueOnOtherModule("SPM_OrderStatusSearch","orderID",orderID,0);	
        }else if(typeOfUser.equalsIgnoreCase("Guest")) {
        	action.waitUntilElementIsDisplayed(OrderIDGuestUser, 15000);
            orderID = action.getText(OrderIDGuestUser, "Order ID",test);
             orderID = orderID.replace("Your order # is: ","").replace(".","");
            action.CompareResult("Order Id "+orderID+" has been retrieved ", "true", "true", test);
            dataTable2.setValueOnCurrentModule ("orderID",orderID);
            dataTable2.setValueOnOtherModule("SPM_OrderStatusSearch","orderID",orderID,0);
        }        
    }

}
