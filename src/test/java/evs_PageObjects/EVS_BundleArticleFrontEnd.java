package evs_PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import utils.Action;
import utils.DataTable2;

public class EVS_BundleArticleFrontEnd {
	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    EVS_ProductSearch filterItems;
    
    public EVS_BundleArticleFrontEnd(WebDriver driver, DataTable2 dataTable2){
    	this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2=dataTable2;
        filterItems = new EVS_ProductSearch(driver, dataTable2);
    }
	

}
