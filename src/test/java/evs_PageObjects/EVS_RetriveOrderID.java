package evs_PageObjects;

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

public class EVS_RetriveOrderID {
    WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    public EVS_RetriveOrderID(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2=dataTable2;
    }

    @FindBy(xpath = "//*[@class='order-number']/span")
    WebElement OrderIDRegisteredUser;
    
    @FindBy(xpath = "//*[@class='checkout-success']/p/span")
    WebElement OrderIDGuestUser;
    
    

    public void RetriveOrderID(ExtentTest test) throws IOException {
    	String typeOfUser = dataTable2.getValueOnOtherModule("evs_deliveryPopulation", "UserType", 0).trim();
	
        String orderID = null;
        action.explicitWait(10000);
        if(typeOfUser.equalsIgnoreCase("Registered")) {
        	action.isElementOnNextPage(OrderIDRegisteredUser, (long) 11,test);
            orderID = orderID.replace("Your order # is: ","").replace(".","");
            dataTable2.setValueOnCurrentModule ("orderID",orderID);
            dataTable2.setValueOnOtherModule("OrderStatusSearch","orderID",orderID,0);	
        }else if(typeOfUser.equalsIgnoreCase("Guest")) {
        	action.isElementOnNextPage(OrderIDGuestUser, (long) 11,test);
            orderID = orderID.replace("Your order # is: ","").replace(".","");
            dataTable2.setValueOnCurrentModule ("orderID",orderID);
            dataTable2.setValueOnOtherModule("OrderStatusSearch","orderID",orderID,0);
        }        
    }

}
