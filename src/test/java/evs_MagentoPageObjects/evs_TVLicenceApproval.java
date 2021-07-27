package evs_MagentoPageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
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
        this.dataTable2=dataTable2;
}}
