package evs_PageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class EVS_clearWishList {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;

	public EVS_clearWishList(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;
	}

	@FindBy(xpath = "//span[contains(text(),'You have no items in your wish list.')]")
	WebElement mywishlist_statusMsg;

	@FindBy(xpath = "//button[@title='Add All to Cart']")
	WebElement Add_all_Tocart;
	
	String statusMsg;
	String expmsg;

	public void handleWishlistItem(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
		int waitTime=5;
		boolean bfalg=false;
		boolean ItemMsgflag;
		String conditionToaddTocart = dataTable2.getValueOnCurrentModule("AddToCartCondition");
		ItemMsgflag = verifyitemWishlistMsg(waitTime, test);
		 
		if(ItemMsgflag){
			action.CompareResult("No Item is presnt in wishlist", "true", String.valueOf(ItemMsgflag), test);
			
			action.CompareResult("No Item is presnt in wishlist", statusMsg, expmsg, test);
		
		}else if(action.isElementPresent(Add_all_Tocart)&& ItemMsgflag==false){
			
			checkAddtocart(conditionToaddTocart , waitTime, test);
		}else{
			action.CompareResult(" No Item is presnt in wishlist", "true", String.valueOf(ItemMsgflag), test);
		}
		
	}

	public boolean checkAddtocart(String conditionToaddTocart, int TimeOut,ExtentTest test) throws IOException{
		boolean checkflg=false;
		switch (conditionToaddTocart){
		case "All":
		
			if(action.isElementPresent(Add_all_Tocart)){
				action.scrollElemetnToCenterOfView(Add_all_Tocart, "Scrolling to Add all to Cart button", test);
				action.clickEle(Add_all_Tocart, "Add all To cart button", test);
				checkflg=true;
			}
			action.explicitWait(5000);
			if(checkflg){
				boolean Itemaddtocartflag = verifyitemWishlistMsg(TimeOut, test);
				if(Itemaddtocartflag){
					checkflg=true;
					action.CompareResult("All the items in wishlist is added to cart ", "true", String.valueOf(checkflg), test);
					action.CompareResult("Product in wish list has ben removed", expmsg, statusMsg, test);
				}else{
					checkflg=false;
					action.CompareResult("All the items in wishlist is added to cart ", "true", String.valueOf(checkflg), test);
				}
			}
			break;
		case "Specific":
			break;
		}
		return checkflg;
	}
	public boolean verifyitemWishlistMsg(int TimeOut,ExtentTest test) throws IOException{
		boolean checkflag=false;
//		statusMsg ="Item is present";
		expmsg= "You have no items in your wish list.";		
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);  
			boolean isPresent = driver.findElements(By.xpath("//form[@id='wishlist-view-form']//span[contains(text(),'You have no items in your wish list.')]") ).size() > 0;
			if(isPresent){
				 statusMsg = action.getText(mywishlist_statusMsg, "Wishlist Header Message",test);
			
				if(statusMsg.equalsIgnoreCase(expmsg)){
					action.CompareResult("Status of wishlist ", expmsg, statusMsg, test);
					checkflag=true;
				}
		}
		return checkflag;
	}
}
