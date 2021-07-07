package evs_PageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.DataTable2;

import java.io.IOException;

public class EVS_MyOrders {

    WebDriver driver;
    Action action;
    DataTable2 dataTable2;

    public EVS_MyOrders(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;

    }

    @FindBy(xpath = "//div[contains(@class,'my-account icon')]")
    WebElement myAccountButton;

    @FindBy(xpath = "//a[contains(text(),'My Orders')]")
    WebElement myOrders;

    @FindBy(xpath = "//span[contains(text(),'My Orders')]")
    WebElement myOrdersHeader;

    @FindBy(xpath = "//button[@data-role='proceed-to-checkout']")
    WebElement secureCheckout;

    public void searchOrder(ExtentTest test) throws IOException {

        String idToSearch = dataTable2.getValueOnOtherModule("evs_RetriveOrderID", "orderID", 0);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", myAccountButton);
        executor.executeScript("arguments[0].click();", myOrders);
        action.explicitWait(5000);

        WebElement orderElememt = driver.findElement(By.xpath("//table[@id='my-orders-table']/tbody/tr/td[contains(text(),'" + idToSearch + "')]"));
        boolean orderFound = orderElememt.isDisplayed();
        action.CompareResult("Is Order displayed", "true", String.valueOf(orderFound), test);

        String orderId = orderElememt.getText();
        action.CompareResult("Check Order ID", idToSearch, orderId, test);

        WebElement reorderLink = orderElememt.findElement(By.xpath(".//following-sibling::td/a[@class='action order']"));
        action.click(reorderLink, "Reorder Link", test);
        action.explicitWait(10000);
        action.click(secureCheckout, "Secure Checkout", test);

    }


}
