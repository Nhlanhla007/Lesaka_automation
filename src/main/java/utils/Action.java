package utils;

import java.io.EOFException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.google.common.base.Function;
import Logger.Log;

/**
 * <copyright file="TestCaseBase.java" company="Modi's Consulting Group">
 * Reproduction or transmission in whole or in part, in any form or by any
 * means, electronic, mechanical or otherwise, is prohibited without the prior
 * written permission of the copyright owner. </copyright>
 * 
 * @author
 */

public class Action {

	WebDriver driver;
	public final int WAIT_IN_SECONDS_MIN = 2;
	public final int WAIT_IN_SECONDS_MED = 4;
	public final int WAIT_IN_SECONDS_MAX = 6;
	
	WebElement newElement = null;
	
	static Logger logger = Log.getLogData(Action.class.getSimpleName());

	public Action(WebDriver driver) {
		this.driver = driver;
	}
	public String getScreenShot(String screenshotName) throws IOException {
		String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir") + "/reports/Screenshots/" + screenshotName + dateName + ".png";
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}
	/**
	 * Selenium click method
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 * @throws Exception
	 * 
	 */


	public <T> void noramlClick (T elementAttr) {
		if(elementAttr.getClass().getName().contains("By")) {
			driver.findElement((By) elementAttr).click();
		} else {
			((WebElement) elementAttr).click();
		}
	}

	public <T> void click(T elementAttr, String name,ExtentTest test)  {
		ExtentTest node=test.createNode("Clicked Element: "+ name);
		try{
			if (elementAttr.getClass().getName().contains("By")) {
				driver.findElement((By) elementAttr).click();
			} else {

				((WebElement) elementAttr).click();
			}
			if(name != null){
				logger.info("Clicked Element: "+ name);
				String screenShotPath=getScreenShot(name);
				node.pass("Clicked Element: "+ name);

			}
		} catch(Throwable e){
			logger.info("Unable to Click Element: "+ name);
			node.fail("Clicked Element: "+ name);

		}
	}

	/**
	 * This method accepts the browser alerts.
	 */
	public void acceptAlert() {
		driver.switchTo().alert().accept();
	}

	/**
	 * This method can switch to new frame using index
	 * 
	 * @param index
	 *            Index of the frame
	 */
	public void switchToFrameUsingIndex(int index) {
		driver.switchTo().frame(index);
	}


	public void switchToFrameUsingName(String framName) {
		driver.switchTo().frame(framName);
	}

    public void switchToFrameUsingWebElement(WebElement webElementName) {
    	logger.info("Switching to iFrame");

        driver.switchTo().frame(webElementName);
    }
    

	/**
	 * This method can switch back to default frame
	 */

	public void switchToMainFrame() {
		logger.info("Switching to default content");

		driver.switchTo().defaultContent();
	}

	/**
	 * This method is used to return the current page URL
	 * 
	 * @return current page URL
	 */

	public String getCurrentURL() {
		return driver.getCurrentUrl();
	}

	/**
	 * This method validates the presence of the alert and if alert present it
	 * will be accepted.
	 */

	public void isAlertPresent(long time) {
		boolean b = false;
		try {
			WebDriverWait wait = new WebDriverWait(driver, time);
			wait.until(ExpectedConditions.alertIsPresent());
			b = true;
		} catch (NoAlertPresentException e) {
			logger.info("Alert not present", e);
			b = false;
		}

		if (b)
			driver.switchTo().alert().dismiss();
	}


	public void waitTillAlertIsPresent(long time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		wait.until(ExpectedConditions.alertIsPresent());

	}

	/**
	 * This method gets the count of open windows/tabs using getWindowHandles()
	 * method and switches to desired window/tab using switchTo() method
	 * 
	 * @param tabNumber
	 */

	public void switchToWindow(int tabNumber) {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(tabNumber));

	}

	/**
	 * This method gets the count of open windows/tabs using getWindowHandles()
	 * method and switches to desired window/tab using switchTo() method and
	 * closes the particular tab
	 * 
	 * @param tabNumber
	 */

	public void closeWindow(int tabNumber) {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(tabNumber));
		driver.close();

	}

	/**
	 * This method gets switches to the window by its name desired window/tab
	 * using switchTo() method
	 * 
	 * @param name
	 */

	public void switchToWindowByName(String name) {
		driver.switchTo().window(name);

	}

	/**
	 * This method uses JavascriptExecutor to perform an desired operation on a
	 * web element.
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 */

	public <T> void javaScriptClick(T elementAttr, String name) {
		waitExplicit(WAIT_IN_SECONDS_MED);
		try{
			if (elementAttr.getClass().getName().contains("By")) {
				WebElement element = driver.findElement((By) elementAttr);
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", element);
			} else {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", elementAttr);
			}
			logger.info("Clicked Element: "+ name);

		} catch(Throwable e){
			e.printStackTrace();

		}
	}

	/**
	 * This method is used to type some content into an editable field.
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 * @param text
	 *            The text value to be sent to the web element.
	 */

	public <T> void writeText(T elementAttr, String text, String name, ExtentTest test) throws IOException {
		ExtentTest node = test.createNode("Writing text: "+text+" to Element: "+ name);
		try{
//			waitFluent((WebElement) elementAttr);
			if (elementAttr.getClass().getName().contains("By")) {
				driver.findElement((By) elementAttr).sendKeys(text);
			} else {

				((WebElement) elementAttr).sendKeys(text);
			}
			if(name != null){

				String screenShotPath=getScreenShot(name);
//				node.log(Status.PASS,"Writing text: "+text+" to Element: "+ name);
				String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
				node.pass("Writing text: "+text+" to Element: "+ name,MediaEntityBuilder.createScreenCaptureFromPath("C:\\Users\\thoko\\Documents\\OperaCloudSeleniumFramework-master\\reports\\extentreports\\screenshots\\20210204112026215.png").build());


			}
		}catch(Throwable e){
			e.printStackTrace();
//				node.log(Status.FAIL,"Writing text: "+text+" to Element: "+ name);
			String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
			node.fail("Unable to click element :"+name,MediaEntityBuilder.createScreenCaptureFromPath("C:\\Users\\thoko\\Documents\\OperaCloudSeleniumFramework-master\\reports\\extentreports\\screenshots\\20210204112026215.png").build());


		}
	}

	/**
	 * This method is uses moveToElement() from the Actions class to simulate
	 * mouse hover effect.
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 * @param xOffset
	 *            X coordinate
	 * @param yOffset
	 *            Y coordinate
	 */

	public <T> void moveToElement(T elementAttr, int xOffset, int yOffset) {
		Actions builder = new Actions(driver);
		if (elementAttr.getClass().getName().contains("By")) {
			builder.moveToElement(driver.findElement((By) elementAttr)).moveByOffset(xOffset, yOffset).click()
					.perform();
		} else {
			builder.moveToElement(((WebElement) elementAttr)).moveByOffset(xOffset, yOffset).click().perform();

		}
	}

	public <T> void mouseover(T elementAttr, String name) {
		try{
			Actions action = new Actions(driver);
			if (elementAttr.getClass().getName().contains("By")) {
				WebElement myElement = driver.findElement((By) elementAttr);
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", myElement);
				action.moveToElement(myElement).perform();

			} else {
				WebElement fluentElement = waitFluent((WebElement) elementAttr);
				action.moveToElement(fluentElement).build().perform();
				//action.moveToElement(((WebElement) elementAttr)).build().perform();
			}
			if(name != null){
				logger.info("Mouse hovering element: "+ name);

			}
		}catch(Throwable e){
			e.printStackTrace();

		}
	}

	/**
	 * This method is used to simulate keyboard keys
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 * @param key
	 *            Keys enum
	 * 
	 */
	public <T> void sendKeyboardKeys(T elementAttr, Keys key) {
		if (elementAttr.getClass().getName().contains("By")) {
			driver.findElement((By) elementAttr).sendKeys(key);
		} else {
			((WebElement) elementAttr).sendKeys(key);
		}
	}

	/**
	 * The getText() is a method that gets you the inner text of the web
	 * element. Get text is the text inside the HTML tags.
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 * @return returns the inner text of the web element
	 */

	public <T> String getText(T elementAttr, String name) {
		waitExplicit(WAIT_IN_SECONDS_MED);
		String text ="";
		try{
			if (elementAttr.getClass().getName().contains("By")) {
				text = driver.findElement((By) elementAttr).getText();
			} else {
				WebElement fluentElement = waitFluent((WebElement) elementAttr);
				text = fluentElement.getText();
				//text = ((WebElement) elementAttr).getText();
			}
			if(name != null){
				logger.info("Getting text from: "+ name);

			}
		}catch(Throwable e){
			e.printStackTrace();

		}
		return text;
	}

	/**
	 * The method is used to retrieve the value of the specified attribute
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 * @param attribute
	 *            name
	 * @return returns the attribute value of the web element
	 */

	public <T> String getAttribute(T elementAttr, String attribute) {
		String attributeValue;
		if (elementAttr.getClass().getName().contains("By")) {
			attributeValue = driver.findElement((By) elementAttr).getAttribute(attribute);
		} else {
			attributeValue = ((WebElement) elementAttr).getAttribute(attribute);
		}
		return attributeValue;
	}

	/**
	 * The method used Java Thread sleep to halt the execution for specified
	 * time
	 * 
	 * @paramtime
	 *            Time in milliseconds
	 */

	public void onLoadDelay() {
		/*try {
			Thread.sleep(Values.time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}*/
	}

	/**
	 * The method is used to check the existence of the web element on the web
	 * page
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 * @return returns the Boolean value
	 */

	//@SuppressWarnings("unchecked")
	public <T> boolean elementExists(T elementAttr, long time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		if (elementAttr.getClass().getName().contains("By")) {
			By loc = (By) elementAttr;
			wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
			return driver.findElements((By) elementAttr).size() > 0;
		} else {
			wait.until((ExpectedConditions.visibilityOf(((WebElement) elementAttr))));
			return true;
		}

	}

	/**
	 * isEnabled() to Check Whether the Element is Enabled Or Disabled in the
	 * Selenium WebDriver.
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 * @return returns the Boolean value
	 */

	public <T> void isEnabled(T elementAttr) {
		if (elementAttr.getClass().getName().contains("By")) {
			if (!(driver.findElement((By) elementAttr).isEnabled()))
				logger.info("Element not enabled");
			Assert.fail("Element is not displayed");
		} else {
			if (!((WebElement) elementAttr).isEnabled())
				logger.info("Element not enabled");
			Assert.fail("Element is not displayed");

		}

	}

	/**
	 * isDisplayed() to Check Whether the Element is visible Or not visible
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 */

	public <T> Boolean isDisplayed(T elementAttr) {
		if (elementAttr.getClass().getName().contains("By")) {
			if ((driver.findElement((By) elementAttr).isDisplayed())) {
				logger.info("Element displayed");
				return true;
			}
		} else {
			if (((WebElement) elementAttr).isDisplayed()) {
				logger.info("Element displayed");
				return true;
			}
		}
		return false;
	}

	/**
	 * method is used for clearing the text field
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 */
	public <T> void clear(T elementAttr, String name) {
		try{
			if (elementAttr.getClass().getName().contains("By")) {
				driver.findElement((By) elementAttr).clear();
			} else {
				WebElement fluentElement = waitFluent((WebElement) elementAttr);
				fluentElement.clear();
				//((WebElement) elementAttr).clear();
			}
			if(name != null){
				logger.info("Clearing element: "+ name);

			}
		}catch(Throwable e){
			e.printStackTrace();

		}
	}

	/**
	 * List the size of child elements to this element
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 */
	@SuppressWarnings("unchecked")
	public <T> int getlistSize(T elementAttr) {
		if (elementAttr.getClass().getName().contains("By")) {
			return driver.findElements((By) elementAttr).size();
		} else {
			return ((List<WebElement>) elementAttr).size();
		}
	}

	/**
	 * Method name turnOffImplicitWaits : Set implicit wait of the web driver to
	 * zero
	 */

	public void turnOffImplicitWaits(WebDriver driver) {

		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
	}

	/**
	 * method is used selecting the drop down item using the text
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 * @param value
	 *            Value is to be selected from drop down
	 */
	public <T> void selectFromDropDownUsingVisibleText(T elementAttr, String value, String name) {
		try{
			if (elementAttr.getClass().getName().contains("By")) {
				Select select = new Select(driver.findElement((By) elementAttr));
				select.selectByVisibleText(value);
			} else {
				WebElement fluentElement = waitFluent((WebElement) elementAttr);
				fluentElement.click();
				//Select select = new Select((WebElement) elementAttr);
				Select select = new Select(fluentElement);
				select.selectByVisibleText(value);
			}
			if(name != null){
				logger.info("Selecting value: "+value+" from dropdown: "+ name);

			}
		}catch(Throwable e){
			e.printStackTrace();

		}
	}


	public <T> String getSelectedOptionFromDropDown(T elementAttr) {
		if (elementAttr.getClass().getName().contains("By")) {
			Select select = new Select(driver.findElement((By) elementAttr));
			WebElement option = select.getFirstSelectedOption();
			return option.getText();
		} else {
			Select select = new Select((WebElement) elementAttr);
			WebElement option = select.getFirstSelectedOption();
			return option.getText();
		}

	}

	/**
	 * Method name turnOffImplicitWaits : Set implicit wait of the web driver to
	 * required number of seconds
	 * 
	 * @param driver
	 *            driver object
	 * @param time
	 *            wait time
	 */

	public void turnOnImplicitWaits(WebDriver driver, int time) {

		driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
	}

	/**
	 * This method used for checking that an element is either invisible or not
	 * present on the DOM.
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 * @param time
	 *            wait time
	 */
	public <T> void waitForElementInVisible(T elementAttr, long time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		if (elementAttr.getClass().getName().contains("By")) {
			By loc = (By) elementAttr;
			try {
				Thread.sleep(2000);
				wait.until(ExpectedConditions.invisibilityOfElementLocated(loc));
			} catch (Exception e) {
				logger.info("Element still present:" + loc);
				System.out.println(e.toString());
			}
		}
		else {
			try {
				Thread.sleep(2000);
				wait.until((ExpectedConditions.visibilityOf(((WebElement) elementAttr))));
			} catch (Exception e) {
				logger.info("Element Not Found:");
				e.printStackTrace();
			}
		}
	}

	/**
	 * This method used for for checking that an element is present on the DOM
	 * of a page. This does not necessarily mean that the element is visible.
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 * @param time
	 *            wait time
	 */
	public <T> void waitForElementPresent(T elementAttr, long time) {

		WebDriverWait wait = new WebDriverWait(driver, time);
		if (elementAttr.getClass().getName().contains("By")) {
			By loc = (By) elementAttr;
			try {
				wait.until(ExpectedConditions.presenceOfElementLocated(loc));
			} catch (Exception e) {
				logger.info("Element still present:" + loc);
				System.out.println(e.toString());
			}
		}
	}

	/**
	 * This method used for for checking the presence of the value in the list
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public <T> boolean checkWhetherItemIsPresentInList(T elementAttr, String value) {
		if (elementAttr.getClass().getName().contains("By")) {
			List<WebElement> list = driver.findElements((By) elementAttr);
			for (WebElement item : list) {
				String text = item.getAttribute("text");
				if (text.indexOf(value) != -1) {
					System.out.println(value + " : is present in the list");
					return true;
				}
			}
		} else {
			for (WebElement item : ((List<WebElement>) elementAttr)) {
				String text = item.getAttribute("text");
				if (text.indexOf(value) != -1) {
					System.out.println(value + " : is present in the list");
					return true;
				}
			}

		}
		return false;

	}

	/**
	 * This method selecting the value from list
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 * @param value
	 */

	@SuppressWarnings("unchecked")
	public <T> void selectExactValueFromList(T elementAttr, String value) {

		if (elementAttr.getClass().getName().contains("By")) {
			List<WebElement> list = driver.findElements((By) elementAttr);
			System.out.println("Size:" + list.size());
			for (WebElement item : list) {
				String text = item.getAttribute("text");
				// System.out.println(text);
				if (text.equals(value)) {
					item.click();
					System.out.println("Clicked on item: " + text);
					break;
				}
			}
		} else {
			for (WebElement item : (List<WebElement>) elementAttr) {
				String text = item.getAttribute("text");
				// System.out.println(text);
				if (text.equals(value)) {
					item.click();
					System.out.println("Clicked on item: " + text);
					break;
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	public <T> void selectExactValueFromListUsingText(T elementAttr, String value) {

		if (elementAttr.getClass().getName().contains("By")) {
			List<WebElement> list = driver.findElements((By) elementAttr);
			System.out.println("Size:" + list.size());
			for (WebElement item : list) {
				String text = item.getText();
				// System.out.println(text);
				if (text.equals(value)) {
					item.click();
					logger.info("Clicked on item: " + text);
					break;
				}
			}
		} else {
			for (WebElement item : (List<WebElement>) elementAttr) {
				String text = item.getText();
				// System.out.println(text);
				if (text.equals(value)) {
					item.click();
					System.out.println("Clicked on item: " + text);
					break;
				}
			}
		}
	}

	/**
	 * This method selecting the value from list using partial match
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 * @param value
	 */

	@SuppressWarnings("unchecked")
	public <T> void selectPartialValueFromList(T elementAttr, String value) {
		if (elementAttr.getClass().getName().contains("By")) {
			List<WebElement> list = driver.findElements((By) elementAttr);
			System.out.println("Size:" + list.size());
			for (WebElement item : list) {
				String text = item.getAttribute("text");
				// System.out.println(text);
				if (text.contains(value)) {
					item.click();
					System.out.println("Clicked on item: " + text);
					break;
				}
			}
		} else {
			for (WebElement item : (List<WebElement>) elementAttr) {
				String text = item.getAttribute("text");
				// System.out.println(text);
				if (text.contains(value)) {
					item.click();
					System.out.println("Clicked on item: " + text);
					break;
				}
			}
		}
	}

	/**
	 * Used for selecting value fromlist using index.
	 * 
	 * @param elementAttr
	 *            Identifier of the web element.
	 * @param index
	 *            The text to be selected in the list.
	 */

	@SuppressWarnings("unchecked")
	public <T> void selectValueFromListByIndex(T elementAttr, int index) {

		if (elementAttr.getClass().getName().contains("By")) {
			List<WebElement> list = driver.findElements((By) elementAttr);
			logger.info("Clicked on item:=" + list.get(index).getText());
			list.get(index).click();
		} else {
			((List<WebElement>) elementAttr).get(index).click();

		}

	}


	public <T> void switchFrameTo(String frameName) {
		driver.switchTo().frame(frameName);
	}

	public <T> void switchFrameTo(int frameIndex) {
		driver.switchTo().frame(frameIndex);
	}

	public <T> void switchToDefaultFrame() {
		driver.switchTo().defaultContent();
	}

	public <T> void waitForElementVisibility(T elementAttr, long time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		if (elementAttr.getClass().getName().contains("By")) {
			By loc = (By) elementAttr;
			try {
				onLoadDelay();
				wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			try {
				onLoadDelay();
				wait.until((ExpectedConditions.visibilityOf(((WebElement) elementAttr))));
			} catch (Exception e) {
				
				e.printStackTrace();
			}
		}
	}

	public <T> void waitForElementClickable(T elementAttr, long time) {

		WebDriverWait wait = new WebDriverWait(driver, time);
		if (elementAttr.getClass().getName().contains("By")) {
			By loc = (By) elementAttr;
			try {
				onLoadDelay();
				wait.until(ExpectedConditions.elementToBeClickable(loc));
			} catch (Exception e) {
				logger.info("Element still present:" + loc);
				Assert.fail("Element is not clickable");
				System.out.println(e.toString());
			}
		}

		else {
			try {
				onLoadDelay();
				wait.until((ExpectedConditions.elementToBeClickable(((WebElement) elementAttr))));
			} catch (Exception e) {
				Assert.fail("Element is not clickable");
				logger.info("Element Not Found:");
				e.printStackTrace();
			}

		}
	}
	
	
	

	public <T> void waitUntilAttributeContains(T elementAttr, long time) {

		WebDriverWait wait = new WebDriverWait(driver, time);
		if (elementAttr.getClass().getName().contains("By")) {
			By loc = (By) elementAttr;
			try {
				wait.until(ExpectedConditions.attributeContains(loc, "display", "none"));
			} catch (Exception e) {
				logger.info("Element still present:" + loc);
				Assert.fail("Element is not clickable");
				System.out.println(e.toString());
			}
		}

		else {
			try {
				wait.until((ExpectedConditions.attributeContains(((WebElement) elementAttr), "display", "none")));
			} catch (Exception e) {
				Assert.fail("Element is not clickable");
				logger.info("Element Not Found:");
				e.printStackTrace();
			}

		}
	}

	public <T> void waitForFrameVisibility(String frameName, long time) {
		WebDriverWait wait = new WebDriverWait(driver, time);

		try {
			onLoadDelay();
			wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameName));
		} catch (Exception e) {
			logger.info("Frame is not present:" + frameName);
			Assert.fail("Frame is not present");
			System.out.println(e.toString());
		}
	}

	/**
	 * This method will wait until drop down is populated with options.
	 * 
	 * @param elementAttr
	 *            Can be web element or By Object
	 * @param time
	 */

	@SuppressWarnings("deprecation")
	public <T> void waitUntilSelectOptionsPopulated(T elementAttr, long time) {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
		wait.pollingEvery(250, TimeUnit.MILLISECONDS);
		wait.withTimeout(time, TimeUnit.SECONDS);
		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver arg0) {

				if (elementAttr.getClass().getName().contains("By")) {
					Select select = new Select(driver.findElement((By) elementAttr));
					if (select.getOptions().size() > 1)
						return true;
					else
						return false;
				} else {
					Select select = new Select((WebElement) elementAttr);
					if (select.getOptions().size() > 1)
						return true;
					else
						return false;
				}

			}

		};
		wait.until(function);

	}


	@SuppressWarnings("deprecation")
	public <T> void waitForElementAttributeEqualsString(String javaScript, String value, long time) {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
		wait.pollingEvery(250, TimeUnit.MILLISECONDS);
		wait.withTimeout(time, TimeUnit.SECONDS);
		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver arg0) {

				JavascriptExecutor executor = (JavascriptExecutor) driver;
				if (executor.executeScript(javaScript).toString().equalsIgnoreCase(value))
					return true;
				else
					return false;

			}

		};
		wait.until(function);

	}

	/**
	 * This method will wait until number of windows are equal to specific
	 * number.
	 * 
	 * @param numberOfWindows
	 * @param time
	 */

	@SuppressWarnings("deprecation")
	public void waitForNumberOfWindowsToEqual(int numberOfWindows, long time) {
		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
		wait.pollingEvery(250, TimeUnit.MILLISECONDS);
		wait.withTimeout(time, TimeUnit.SECONDS);
		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return (driver.getWindowHandles().size() == numberOfWindows);
			}
		};
		wait.until(function);
	}

	/**
	 * This method will refresh the page.
	 */
	public void refresh() {
		driver.navigate().refresh();
	}
	public <T> void waitForElementAttributeEqualsString(T elementAttr, String attribute, String expectedString,
			int specifiedTimeout) {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
		wait.pollingEvery(250, TimeUnit.MILLISECONDS);
		wait.withTimeout(specifiedTimeout, TimeUnit.SECONDS);
		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver arg0) {
				String attributeValue;
				if (elementAttr.getClass().getName().contains("By")) {

					attributeValue = driver.findElement((By) elementAttr).getAttribute(attribute);
					if (attributeValue.equals(expectedString))
						return true;
					else
						return false;
				} else {
					attributeValue = ((WebElement) elementAttr).getAttribute(attribute);

					if (attributeValue.equals(expectedString))
						return true;
					else
						return false;

				}

			}

		};
		wait.until(function);
	}

	public void waitTillSpinnerDisable(By by, long time) {
		FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver);
		fWait.withTimeout(time, TimeUnit.SECONDS);
		fWait.pollingEvery(250, TimeUnit.MILLISECONDS);
		fWait.ignoring(NoSuchElementException.class);
		Function<WebDriver, Boolean> func = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				WebElement element = driver.findElement(by);
				System.out.println(element.getCssValue("display"));
				if (element.getCssValue("display").equalsIgnoreCase("none")) {
					return true;
				}
				return false;
			}
		};
		fWait.until(func);
	}

	
	
	public void waitTillReleaseClicked(By by, long time) {
		FluentWait<WebDriver> fWait = new FluentWait<WebDriver>(driver);
		fWait.withTimeout(time, TimeUnit.SECONDS);
		fWait.pollingEvery(250, TimeUnit.MILLISECONDS);
		fWait.ignoring(NoSuchElementException.class);
		Function<WebDriver, Boolean> func = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				WebElement element = driver.findElement(by);
				element.click();
				if (element.getAttribute("src").contains("BIU?i=i1e7.png")) {
					return true;
				}
				return false;
			}
		};
		fWait.until(func);
	}

	@SuppressWarnings("deprecation")
	public <T> void waitForPageLoaded(long time) {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
		wait.pollingEvery(250, TimeUnit.MILLISECONDS);
		wait.withTimeout(time, TimeUnit.SECONDS);
		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver arg0) {

				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");

			}

		};
		wait.until(function);

	}

	@SuppressWarnings("deprecation")
	public <T> void waitForJQueryLoad(long time) {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
		wait.pollingEvery(250, TimeUnit.MILLISECONDS);
		wait.withTimeout(time, TimeUnit.SECONDS);
		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver arg0) {

				return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);

			}

		};
		wait.until(function);

	}

	@SuppressWarnings("deprecation")
	public <T> void waitForJQueryReady(long time) {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
		wait.pollingEvery(250, TimeUnit.MILLISECONDS);
		wait.withTimeout(time, TimeUnit.SECONDS);
		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver arg0) {

				return ((Boolean) ((JavascriptExecutor) driver).executeScript("return typeof jQuery != 'undefined'"));

			}

		};
		wait.until(function);

	}

	@SuppressWarnings("deprecation")
	public <T> void waitForAjaxComplete(long time) {

		FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
		wait.pollingEvery(250, TimeUnit.MILLISECONDS);
		wait.withTimeout(time, TimeUnit.SECONDS);
		Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver arg0) {

				return (Boolean) ((JavascriptExecutor) driver)
						.executeScript("var callback = arguments[arguments.length - 1];"

								+ "var xhr = new XMLHttpRequest();" + "xhr.open('GET', '/Ajax_call', true);"

								+ "xhr.onreadystatechange = function() {" + "  if (xhr.readyState == 4) {"

								+ "    callback(xhr.responseText);" + "  }" + "};" + "xhr.send();");
			}

		};
		wait.until(function);

	}

	public <T> void waitWhileElementHasAttributeValue(T elementAttr, String attribute, String value) {

		if (elementAttr.getClass().getName().contains("By")) {
			while (driver.findElement((By) elementAttr).getAttribute(attribute).contains(value))

			{
				int timeout = 10;
				if (timeout > 0) {
					timeout--;
					try {
						System.out.println(attribute + "t" + value);
						Thread.sleep(1000);
					} catch (Exception e) {
					}
				}
			}
		}
	}

	
	public void waitUntilAngularReady() throws Exception {

		try {

			Boolean angularUnDefined = (Boolean) ((JavascriptExecutor) driver).executeScript("return window.angular === undefined");

			if (!angularUnDefined) {

				Boolean angularInjectorUnDefined = (Boolean) ((JavascriptExecutor) driver)
						.executeScript("return angular.element(document).injector() === undefined");

				if (!angularInjectorUnDefined) {

					Thread.sleep(2000);

				}

			}

		} catch (WebDriverException ignored) {

		}

	}
	
	/**
	 * This method is used to scroll to the specific element
	 * @param element
	 * @return true if element present else returns false
	 */
	
	public boolean scrollToElement(WebElement element, String name) {
		waitExplicit(WAIT_IN_SECONDS_MED);
		boolean isElementPresent = false;
		try{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			if (element != null) {
				js.executeScript("arguments[0].scrollIntoView(true);", element);
				isElementPresent = true;
			}
			if(name != null){
				logger.info("Scrolling to Element: "+ name);

			}
		}catch(Throwable e){
			e.printStackTrace();

		}
		return isElementPresent;
	}
	public void waitExplicit(int seconds) {
		try {
			Thread.sleep(1000 * seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Click check box if not checked
	 * @param ele
	 * @param name
	 */
	public void selectCheckBox(WebElement ele, String name){
		try{	
	    	boolean selected = false;
	    	selected = ele.isSelected();
	    	if(selected)
	    		ele.click();
	    	if(name != null){
				logger.info("Clicking checkbox: "+ name);

			}
			
		} catch(Throwable e){
			e.printStackTrace();

		}
    }
	
	public WebElement waitFluent(WebElement ele){
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
	            .withTimeout(Duration.ofSeconds(30))
	            .pollingEvery(Duration.ofSeconds(1))
	            .ignoring(TimeoutException.class)
				.ignoring(NoSuchElementException.class);
		newElement = wait.until(new Function<WebDriver, WebElement>(){
			public WebElement apply(WebDriver driver1){
				if(ele.isDisplayed())
					return ele;
				else
					return null;
			}
		});
		return newElement;
	}
}
