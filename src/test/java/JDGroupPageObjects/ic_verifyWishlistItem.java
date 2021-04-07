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

public class ic_verifyWishlistItem {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
		public ic_verifyWishlistItem(WebDriver driver, DataTable2 dataTable2) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
			action = new Action(driver);
			this.dataTable2=dataTable2;
		}
		
		@FindBy(xpath = "//form[@id='wishlist-view-form']//span[contains(text(),'You have no items in your wish list.')]")
		WebElement mywishlist_statusMsg;
		@FindBy(xpath = "//button[@title='Add All to Cart']")
		WebElement Add_all_Tocart;
		
		public void handleWishlistItem(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
			int waitTime=11;
			boolean bfalg=false;
			boolean ItemMsgflag;
			String conditionToaddTocart = dataTable2.getValueOnCurrentModule("AddToCartCondition");//"All";
			ItemMsgflag = verifyitemWishlistMsg(waitTime, test);
			 
			if(ItemMsgflag){
				action.CompareResult(" No Item is presnt in wishlist", "true", String.valueOf(ItemMsgflag), test);
			
			}else if(action.elementExists(Add_all_Tocart, waitTime) && ItemMsgflag==false){
				
				checkAddtocart(conditionToaddTocart , waitTime, test);
			}else{
				action.CompareResult(" No Item is presnt in wishlist", "true", String.valueOf(ItemMsgflag), test);
			}
			
		}
	
		public boolean checkAddtocart(String conditionToaddTocart, int TimeOut,ExtentTest test) throws IOException{
			boolean checkflg=false;
			switch (conditionToaddTocart){
			case "All":
			
				if(action.elementExists(Add_all_Tocart, TimeOut)){
					action.click(Add_all_Tocart, "Add all To cart", test);
					checkflg=true;
				}
				action.explicitWait(10000);
				if(checkflg){
					boolean Itemaddtocartflag = verifyitemWishlistMsg(TimeOut, test);
					if(Itemaddtocartflag){
						checkflg=true;
						action.CompareResult("All the items in wishlist is added to cart ", "true", String.valueOf(checkflg), test);
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
			String statusMsg ="Item is present";
			String expmsg= "You have no items in your wish list.";
			try {
				if(action.elementExists(mywishlist_statusMsg, TimeOut)){
					 statusMsg = action.getText(mywishlist_statusMsg, "Wishlist empty message");
				
					if(statusMsg.equalsIgnoreCase(expmsg)){
						action.CompareResult("Status of wishlist ", expmsg, statusMsg, test);
						checkflag=true;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
			
			}
			return checkflag;
		}
}
