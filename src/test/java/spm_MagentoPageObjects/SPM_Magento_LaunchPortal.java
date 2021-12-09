package spm_MagentoPageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.DataTable2;

public class SPM_Magento_LaunchPortal {
    WebDriver driver;
    Action action;
    DataTable2 dataTable2;

    public SPM_Magento_LaunchPortal(WebDriver driver, DataTable2 dataTable2){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;

    }
    public void launchPortal(ExtentTest test){
        String url =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnOtherModule("SPM_LoginMagento", "loginDetails", 0),"url");
        action.navigateToURL(url);
    }
}
