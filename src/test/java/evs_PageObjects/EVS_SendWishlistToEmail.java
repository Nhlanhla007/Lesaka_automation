package evs_PageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.DataTable2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EVS_SendWishlistToEmail {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;

	public EVS_SendWishlistToEmail(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	
	}
	
	
	@FindBy(xpath = "/html/body/div[3]/main/div[2]/div[2]/form[1]/div[3]/div[1]/button[2]/span")
	WebElement ShareWishlist;
	@FindBy(xpath = "//h1//span[contains(text(),'Wish List Sharing')]")
	WebElement PageTittleWishlist_sharing;
	@FindBy(xpath = "//textarea[@id='email_address']")
	WebElement EmailID_feild;
	
	@FindBy(xpath = "//span[contains(text(),'Share Wish List')]")
	WebElement ShareWishlist_feild;
	@FindBy(xpath ="//header//div[contains(text(),'Your wish list has been shared.')]")
	WebElement Hdr_Txt;	
	
	public void ShareYourwishlist(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
		int waitTime=Integer.parseInt(dataTable2.getValueOnCurrentModule("TimeOut"));
		String EmailID = dataTable2.getValueOnCurrentModule("Email_To_Share");
		action.click(ShareWishlist, "Share Wish list", test);
		if(action.elementExists(PageTittleWishlist_sharing, waitTime)){
			action.CompareResult("Page loaded ", "Wish List Sharing", action.getText(PageTittleWishlist_sharing, "Page Tittle Wishlist sharing",test).trim(), test);
			action.writeText(EmailID_feild, EmailID, "EmailID feild", test);
			action.click(ShareWishlist_feild, "ShareWishlist_feild", test);
			action.explicitWait(waitTime);
			if(action.elementExists(Hdr_Txt, waitTime)){
				action.CompareResult("Page loaded ", "Your wish list has been shared.", action.getText(Hdr_Txt, "Your wish list has been shared",test).trim(), test);
			}else{
				action.CompareResult("Page loaded ", "Your wish list has been shared.", action.getText(Hdr_Txt, "Your wish list has been shared",test).trim(), test);
			}
		}else{
			
			action.CompareResult("Page loaded ", "Wish List Sharing", "false", test);
		}
	}
}
