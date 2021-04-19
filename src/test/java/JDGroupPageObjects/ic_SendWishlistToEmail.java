package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class ic_SendWishlistToEmail {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	
	public ic_SendWishlistToEmail (WebDriver driver,DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	
	}
	
	
	@FindBy(xpath = "//div[@class='actions-toolbar']/div/button[@title='Share Wish List']")
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
			action.CompareResult("Page loaded ", "Wish List Sharing", action.getText(PageTittleWishlist_sharing, "Page Tittle Wishlist sharing").trim(), test);
			action.writeText(EmailID_feild, EmailID, "EmailID feild", test);
			action.click(ShareWishlist_feild, "ShareWishlist_feild", test);
			action.explicitWait(waitTime);
			if(action.elementExists(Hdr_Txt, waitTime)){
				action.CompareResult("Page loaded ", "Your wish list has been shared.", action.getText(Hdr_Txt, "Your wish list has been shared").trim(), test);
			}else{
				action.CompareResult("Page loaded ", "Your wish list has been shared.", action.getText(Hdr_Txt, "Your wish list has been shared").trim(), test);
			}
		}else{
			
			action.CompareResult("Page loaded ", "Wish List Sharing", "false", test);
		}
	}
}
