package evs_PageObjects;
import java.io.IOException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class EVS_RefreshLogoHomepage {
	
	 WebDriver driver;
	    Action action;
	    DataTable2 dataTable2;
                             
	    public EVS_RefreshLogoHomepage(WebDriver driver, DataTable2 dataTable2) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
	        this.dataTable2= dataTable2;

	    }

	    @FindBy(xpath="//*[@class = \"logo\"]")
	    private WebElement evs_logo;
	    
	    @FindBy(xpath="//html/head/title")
	    private WebElement evs_titlepage;
	   
	    
    public void homepageLogo(ExtentTest test) throws IOException{
	    action.explicitWait(10000);
	    JavascriptExecutor js = (JavascriptExecutor)driver;
	    js.executeScript("arguments[0].scrollIntoView();", evs_logo); 
	    action.click(evs_logo, "Click to go homepage", test);
	    action.waitForPageLoaded(120);
	    String title= driver.getTitle();
	    action.CompareResult("Return homepage", "Home page - DEFAULT STORE VIEW", title, test);
	    	
	    }
	    
	    
	    
}
