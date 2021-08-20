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
		this.dataTable2 = dataTable2;
	}

	@FindBy(xpath = "//div[@class='my-account icon__expand-arrow']")
	WebElement myAccount;

	@FindBy(xpath = "//a[contains(text(),'Log In')]")
	WebElement loginLink;

	@FindBy(xpath = "//a[contains(text(),'My Wish Lists')]")
	WebElement wishListLink;

	@FindBy(xpath = "//input[@id='email']")
	WebElement emailField;

	@FindBy(xpath = "//input[@id='pass']")
	WebElement passwordField;

	@FindBy(xpath = "//button[@id='send2']")
	WebElement SigninBtn;

	@FindBy(xpath = "//span[contains(text(),'My Wish Lists')]")
	WebElement myWishlist_page;

	@FindBy(xpath = "//span[contains(text(),'You have no items in your wish list.')]")
	WebElement mywishlist_msg;

	public void NavigateToWishlist_verifymsg(ExtentTest test) throws IOException {
		String Username = dataTable2.getValueOnCurrentModule("Username");
		String Password = dataTable2.getValueOnCurrentModule("Password");
		String ExpLoginType = dataTable2.getValueOnCurrentModule("Login_type");

		int waitTime = Integer.parseInt(dataTable2.getValueOnCurrentModule("TimeOut_seconds"));
		boolean checkmsg;
		switch (ExpLoginType) {
		case "logedOn_user":
			navigateWishlist(waitTime, test);
			checkmsg = checkWishlist_message(waitTime, test);
			break;
		case "ExistingUser":
			navigateWishlist(waitTime, test);
			boolean checkLogin = loginUser(Username, Password, waitTime, test);
			if (checkLogin) {
				checkmsg = checkWishlist_message(waitTime, test);

			}
			break;

		}

	}

	public void navigateWishlist(int waitTime, ExtentTest test) throws IOException {
		if (!action.isElementPresent(myWishlist_page)){
		action.waitForElementPresent(myAccount, waitTime);
		action.clickEle(myAccount, "Click on My Account", test);
		action.waitForElementPresent(wishListLink, waitTime);
		action.clickEle(wishListLink, "Click on My Wish List", test);
	}}

	public boolean loginUser(String Uname, String Passwrd, int waitTime, ExtentTest test) throws IOException {
		boolean check = false;

		action.waitForElementPresent(emailField, waitTime);
		action.writeText(emailField, Uname, "Username field", test);
		action.writeText(passwordField, Passwrd, "Password field", test);
		action.clickEle(SigninBtn, "click ic_SigninBtn", test);
		action.explicitWait(3000);

		if (action.isElementPresent(myWishlist_page)) {
			check = true;
			action.CompareResult("Navigation to Wishlist Page", "True", "True", test);
		} else {
			action.CompareResult("Navigation to Wishlist Page", "True", "False", test);
		}
		return check;
	}

	public boolean checkWishlist_message(int waitTime, ExtentTest test) throws IOException {
		boolean msg_flag = false;

		if (action.isElementPresent(mywishlist_msg)) {
			msg_flag = true;
			String wishListMsg = mywishlist_msg.getText();
			action.CompareResult("Wish list Page Message", "You have no items in your wish list.", wishListMsg, test);

		} else {
			System.out.println("Wish List is not Empty");

		}
		return msg_flag;
	}
}
