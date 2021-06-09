package evs_PageObjects;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.Action;
import utils.ConfigFileReader;
import utils.DataTable2;

import java.io.IOException;

public class EVS_NavigetoWishlist {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
		public EVS_NavigetoWishlist(WebDriver driver, DataTable2 dataTable2) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
			action = new Action(driver);
			this.dataTable2=dataTable2;
		}
		//@FindBy(xpath = "/html/body/div[3]/header/div[2]/div/div[3]/div[5]")
		@FindBy(xpath = "/html/body/div[2]/header/div[2]/div/div[3]/div[5]")
		WebElement evs_myAccountButton;
		
		@FindBy(xpath = "//*[@id=\"header-slideout--0\"]/li[6]/a")
		WebElement myWishlist_link;
		@FindBy(xpath = "//*[@id='email']")
		WebElement evs_Username;
		
		@FindBy(xpath = "//*[@id='pass']")
		WebElement evs_Password;
		@FindBy(xpath = "//*[@id=\"send2\"]/span")
		WebElement evs_SigninBtn;
		
		
		@FindBy(xpath = "//h1[@class='page-title']/span[contains(text(),'My Wish List')]")
		WebElement mywishlist_page;
		
		
		
		@FindBy(xpath = "//div[@class='message info empty']/span[contains(text(),'You have no items in your wish list.')]")
		WebElement mywishlist_msg;
		public void NavigateToWishlist_verifymsg(ExtentTest test) throws IOException{
			String url =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"),"url");
			String Username =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"),"username");
			String password =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnCurrentModule("loginDetails"),"password");
			ConfigFileReader configFileReader = new ConfigFileReader();
			action.navigateToURL(url);
			action.explicitWait(5000);
			String ExpLoginType =dataTable2.getValueOnCurrentModule("Login_type");//"ExistingUser";// logedOn_user or ExistingUser
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
			action.click(evs_myAccountButton, "My account", test);
			action.click(myWishlist_link, " navigate myWishlist link", test);
			action.explicitWait(waitTime);
		}
		public boolean loginUser(String Uname, String Passwrd,int waitTime,ExtentTest test) throws IOException{
			boolean check =false;
			
			action.writeText(evs_Username, Uname, "Username feild", test);
			action.writeText(evs_Password, Passwrd, "Password feild", test);
			action.clickEle(evs_SigninBtn, "click evs_SigninBtn", test);
			if(action.elementExists(mywishlist_page, waitTime)){
				check=true;
				action.CompareResult("Login to account in evs is sucessfull", "True", "True", test);
			}else{
				action.CompareResult("Login to account in evs is sucessfull", "True", "False", test);
			}
			return check;
		}	
		public boolean checkWishlist_message(int waitTime, ExtentTest test) throws IOException{
			boolean msg_flag = false;
			if(action.elementExists(mywishlist_msg, waitTime)){
				//action.CompareResult("Wishlist contains no item verification message", "true", "true", test);
				msg_flag=true;
			}else{
				action.CompareResult("Wishlist contains no item verification message", "true", "false", test);
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
