package evs_MagentoPageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.DataTable2;

import java.io.IOException;

public class EVS_CancelOrder_CreditMemo {

    WebDriver driver;
    Action action;
    DataTable2 dataTable2;

    public EVS_CancelOrder_CreditMemo(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;

    }

    @FindBy(xpath = "//span[@id='order_status']")
    WebElement orderStatusField;

    @FindBy(xpath = "//button[@id='order_creditmemo']")
    WebElement creditMemoBtn;

    @FindBy(xpath = "//h1[contains(text(),'New Memo')]")
    WebElement newMemoHeader;

    @FindBy(xpath = "//input[contains(@id,'creditmemo_customerbalance_return_enable')]")
    WebElement refundTStoreCreditBtn;

    @FindBy(xpath = "//span[contains(text(),'Refund Offline')]")
    WebElement refundOfflineBtn;

    @FindBy(xpath = "//div[contains(text(),'You created the credit memo.')]")
    WebElement creditMemoMsg;

    public void cancelOrderCreditMemo(ExtentTest test) throws IOException, InterruptedException {

        action.explicitWait(10000);
        boolean check = creditMemoBtn.isDisplayed();
        action.click(creditMemoBtn, "Credit Memo Btn", test);
        action.waitUntilElementIsDisplayed(newMemoHeader, 5);
        action.scrollToElement(refundTStoreCreditBtn, "Refund to Credit Store Button");
        action.click(refundTStoreCreditBtn, "Refund to Credit Button", test);
        action.click(refundOfflineBtn, "Refund Offline", test);
        action.explicitWait(10000);
        String msg = action.getText(creditMemoMsg, "Cancel Message", test);
        action.CompareResult("Success message comparision", "You created the credit memo.", msg, test);
        String orderStatus = action.getText(orderStatusField, "Order Status", test);
        action.CompareResult("Compare Order status", "Closed", orderStatus, test);



    }
}
