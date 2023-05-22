package limnia_Li;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class Profile_Validation {
	    Action action;
	    DataTable2 dataTable2;
	    WebDriver driver;

	    public Profile_Validation(WebDriver driver, DataTable2 dataTable2) {

	        this.driver = driver;
	        this.dataTable2 = dataTable2;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
	    }
	    
	    @FindBy(xpath = "//span[@class='badge badge-info']")
	    WebElement txt_role;
	    @FindBy(xpath = "//span[contains(text(),'Account')]")
	    WebElement txt_accoutTab;
	    @FindBy(xpath = "//span[contains(text(),'Profile')]")
	    WebElement txt_accoutProfile;
	    

	    public void navigateToProfile(ExtentTest test) throws Exception {
	    	if(action.isElementPresent(txt_accoutTab)) {
	    		action.click(txt_accoutTab, "Account", test);
	    		action.click(txt_accoutProfile, "Profile", test);
	    		action.waitForPageLoaded(5000);
	    		
	    	}else {
	    		throw new Exception("Couldn't locate the Account btton");
	    	}
	    	
	    }
	    
	    public void profile_verification(ExtentTest test) throws Exception {
	    	
	    	navigateToProfile(test);
	    	action.scrollElemetnToCenterOfView(txt_role, "User Role", test);
	    	String role = dataTable2.getValueOnOtherModule("lIM_Login", "loginDetails", 0);
	    	switch (role) {
	    	  case "Admin_User":
	    	      action.CompareResult("The Role", "Admin",txt_role.getText() , test);
	    	    break;
	    	  case "Teacher_User":
	    		  action.CompareResult("The Role", "Teacher",txt_role.getText() , test);
	    	    break;
	    	  case "Student_User":
	    		  action.CompareResult("The Role", "Student",txt_role.getText() , test);
		    	break;
	    	  default:
	    		  throw new Exception("User Role not found");
	    	}
	    }
}
