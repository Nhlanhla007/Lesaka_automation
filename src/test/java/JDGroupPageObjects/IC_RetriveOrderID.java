package JDGroupPageObjects;

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

public class IC_RetriveOrderID {
    WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    public IC_RetriveOrderID(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2=dataTable2;
    }

//    @FindBy(xpath = "//p[contains(text(),'Your order # is')]")
    @FindBy(xpath = "//*[@class='checkout-success']/p//span")
    WebElement OderID;

    public void RetriveOrderID(ExtentTest test) throws IOException {
        String Oderid = null;
        action.isElementOnNextPage(OderID, (long) 11,test);
        Oderid = action.getText(OderID, "Order ID");
        Oderid = Oderid.replace("Your order # is: ","").replace(".","");
        dataTable2.setValueOnCurrentModule ("orderID",Oderid);
        dataTable2.setValueOnOtherModule("OrderStatusSearch","orderID",Oderid,0);
    }
}
