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

public class ic_NavigetoWishlist {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
		public ic_NavigetoWishlist(WebDriver driver, DataTable2 dataTable2) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
			action = new Action(driver);
			this.dataTable2=dataTable2;
		}
		@FindBy(className = "my-account")
		WebElement ic_myAccountButton;
		
		@FindBy(xpath = "//ul[@id='header-slideout--0']//li//a[contains(text(),'My Wish List')]")
		WebElement myWishlist_link;
		@FindBy(xpath = "//*[@id='email']")
		WebElement ic_Username;
		
		@FindBy(xpath = "//*[@id='pass']")
		WebElement ic_Password;
		@FindBy(xpath = "//*[@id=\"send2\"]/span")
		WebElement ic_SigninBtn;
		
		
		@FindBy(xpath = "//h1[@class='page-title']/span[contains(text(),'My Wish List')]")
		WebElement mywishlist_page;
		
		
		
		@FindBy(xpath = "//div[@class='message info empty']/span[contains(text(),'You have no items in your wish list.')]")
		WebElement mywishlist_msg;
		public void NavigateToWishlist_verifymsg(ExtentTest test) throws IOException{
			String ExpLoginType =dataTable2.getValueOnCurrentModule("Login_type");//"ExistingUser";// logedOn_user or ExistingUser
			String Username =dataTable2.getValueOnCurrentModule("Username");
			String password = dataTable2.getValueOnCurrentModule("Password");
			int waitTime = Integer.parseInt(dataTable2.getValueOnCurrentModule("TimeOut_seconds"));
			boolean checkmsg;
			switch (ExpLoginType) {
			case "logedOn_user":
				navigateWishlist(waitTime,test);
				checkmsg = checkWishlist_message(waitTime,test);
				break;
			case "ExistingUser":
				navigateWishlist(waitTime,test);
				boolean checkLogin = loginUser(Username,password,waitTime,test);
				if(checkLogin){
					checkmsg = checkWishlist_message(waitTime,test);
					
				}
				break;
				
			}
			
		}
		public void navigateWishlist(int waitTime,ExtentTest test) throws IOException{
			action.click(ic_myAccountButton, "My account", test);
			action.click(myWishlist_link, " navigate myWishlist link", test);
			action.explicitWait(waitTime);
		}
		public boolean loginUser(String Uname, String Passwrd,int waitTime,ExtentTest test) throws IOException{
			boolean check =false;
			
			action.writeText(ic_Username, Uname, "Username feild", test);
			action.writeText(ic_Password, Passwrd, "Password feild", test);
			action.clickEle(ic_SigninBtn, "click ic_SigninBtn", test);
			if(action.elementExists(mywishlist_page, waitTime)){
				check=true;
				action.CompareResult("Login to account in ic is sucessfull", "True", "True", test);
			}else{
				action.CompareResult("Login to account in ic is sucessfull", "True", "False", test);
			}
			return check;
		}	
		public boolean checkWishlist_message(int waitTime, ExtentTest test) throws IOException{
			boolean msg_flag = false;
			if(action.elementExists(mywishlist_msg, waitTime)){
				action.CompareResult("Wishlist contains no item verification message", "true", "true", test);
				msg_flag=true;
			}else{
				action.CompareResult("Wishlist contains no item verification message", "true", "flase", test);
			}
			return msg_flag;
		}
		
		public void NavigateToWishlist_verifyLoginPageAppears(ExtentTest test) throws Exception{
				navigateWishlist(15000,test);
				String titleOfPage = driver.getTitle();
				String expectedTitle = "Incredible Connection Customer Login";
				action.CompareResult("Verify Title of page", expectedTitle, titleOfPage, test);
		}
		
}
