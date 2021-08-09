package evs_MagentoPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import evs_PageObjects.EVS_ProductSearch;
import utils.Action;
import utils.DataTable2;

public class evs_TVLicenceApproval {

    WebDriver driver;
    Action action;
    DataTable2 dataTable2;

    public evs_TVLicenceApproval(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
    }

    @FindBy(xpath = "//div[contains(text(),'[RabbitMQ] Order SAP Number: ')][1]")
    private WebElement OrderDetailSAPNumber;

    @FindBy(xpath = "//*[@name=\"approve_decline\"]")
    private WebElement admin_statuChange;

    @FindBy(xpath = "//*[@name=\"approve_decline\"]/option[2]")
    private WebElement admin_ApproveStatus;

    @FindBy(xpath = "//*[@name=\"current_password\"]")
    private WebElement admin_TVpassword;

    @FindBy(xpath = "//*[@title=\"Update Account Validity\"]")
    private WebElement admin_ButtonTVpassword;

    @FindBy(xpath = "//*[@class=\"odd\"]/tr/td/div/div[2]/span")
    private WebElement admin_NewTVlicenceSKU;

    @FindBy(xpath = "//div[contains(text(),'Validity change was processed')]")
    private WebElement admin_TVlicenseApprovedMessage;

    @FindBy(xpath = "admin__table-secondary order-information-table")
    private WebElement admin_TVOrderStatus;

    @FindBy(xpath = "//*[@class=\"odd\"]/tr/td/div/div[2]/span")
    private WebElement admin_NewTVlicenceHover;

    public void approveTVlicence(ExtentTest test, int rowNumber) throws Exception {
        String ApprovalPass = dataTable2.getValueOnCurrentModule("TV licence password Approval");
        String SKUTvLicence = dataTable2.getValueOnCurrentModule("TV licence SKU");

        action.explicitWait(5000);
        try {
            action.scrollElemetnToCenterOfView(admin_NewTVlicenceHover, "Status", test);
            String SKUnewTvli = action.getText(admin_NewTVlicenceSKU, "", test).trim();
            SKUnewTvli.replace("SKU:", "");
            action.CompareResult("is the new tv lic SKU", SKUTvLicence.trim(), SKUnewTvli.trim(), test);
            action.scrollElemetnToCenterOfView(admin_statuChange, "Select", test);
            action.explicitWait(3000);
            action.click(admin_statuChange, "choose the required status", test);
            action.explicitWait(5000);
            action.click(admin_ApproveStatus, "Selecting Approve option status", test);
            action.ajaxWait(10, test);
            action.writeText(admin_TVpassword, ApprovalPass, "Enter the approval password", test);
            action.javaScriptClick(admin_ButtonTVpassword, "Click Update Account Validity", test);
            action.ajaxWait(10, test);
            action.explicitWait(3000);
            String expSuccessMessage = "Validity change was processed";
            if (admin_TVlicenseApprovedMessage.isDisplayed()) {
                String actualSuccessMessage = action.getText(admin_TVlicenseApprovedMessage, "get the success message", test);
                action.CompareResult("The licence was appoved", actualSuccessMessage, expSuccessMessage, test);
            }
        } catch (Exception e) {
            throw new Exception("The new licence has not been approved! " + e.getMessage());
        }

    /*    String TVorderStatus = action.getText(admin_TVOrderStatus, "Get the current order status", test);




        if(TVorderStatus != "Received Paid"){
            action.refresh();
            action.explicitWait(20000);

        }*/


    }


}