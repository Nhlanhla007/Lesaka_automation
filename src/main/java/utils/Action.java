package utils;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.*;
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
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import com.google.common.base.Function;
import Logger.Log;

import static org.apache.poi.sl.draw.binding.STRectAlignment.T;

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

    public String getPageTitle(ExtentTest test) throws IOException {
        ExtentTest node = test.createNode("PageTitle");
        String title = "";
        try {
            title = driver.getTitle();
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            node.pass("success get page title: " + title + node.addScreenCaptureFromPath(screenShot));
        } catch (Exception e) {
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            node.pass("failed to get page title: " + node.addScreenCaptureFromPath(screenShot));
            throw e;
        }
        return title;
    }

    public String getScreenShot(String screenshotName) throws IOException {
        File currentDirFile = new File(".");
        String helper = currentDirFile.getAbsolutePath();
        helper = helper.replace(".", "").replaceAll("\"\"", "/");
        String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = "\\reports\\screenshots\\" + 1 + dateName + ".png";
        File finalDestination = new File(destination);
        copyFile(source, finalDestination);
        return destination;
    }

    public void copyFile(File source, File dest) {
        try {
            // File file1 = new File(source);
            // File file2 = new File(dest);
            FileInputStream fis = new FileInputStream(source);
            FileOutputStream fos = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
        } catch (Exception e) {

        }

    }

    /**
     * Selenium click method
     *
     * @param elementAttr Can be web element or By Object
     * @throws Exception
     */

    public <T> void noramlClick(T elementAttr) {
        if (elementAttr.getClass().getName().contains("By")) {
            driver.findElement((By) elementAttr).click();
        } else {
            ((WebElement) elementAttr).click();
        }
    }

    public <T> void click(T elementAttr, String name, ExtentTest test) throws IOException {
        ExtentTest node = test.createNode("Clicked Element: " + name);

        try {
            // String screenShot=GenerateScreenShot.getScreenShot(driver);
            if (elementAttr.getClass().getName().contains("By")) {
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                jse.executeScript("arguments[0].style.border='2px solid red'", driver.findElement((By) elementAttr));
                driver.findElement((By) elementAttr).click();
            } else {
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                jse.executeScript("arguments[0].style.border='2px solid red'", (WebElement) elementAttr);
                WebDriverWait wait = new WebDriverWait(driver, 15);
                List<WebElement> elements = new ArrayList<WebElement>();
                elements.add((WebElement) elementAttr);
                List<WebElement> elements2 = wait.until(ExpectedConditions.visibilityOfAllElements(elements));
                ((WebElement) elements2.get(0)).click();
            }
            if (name != null) {
                logger.info("Clicked Element: " + name);
                String screenShot = GenerateScreenShot.getScreenShot(driver);
                node.pass("Clicked Element: " + name + node.addScreenCaptureFromPath(screenShot));

            }
        } catch (Exception e) {
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            node.fail("unable to Click on element:" + name + node.addScreenCaptureFromPath(screenShot));
            throw e;
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
     * @param index Index of the frame
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

    public void navigateToURL(String url) {
        driver.navigate().to(url);
        driver.manage().window().maximize();
        if (url.contains("incredibleconnection")) {
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            explicitWait(5000);
            boolean clearCookiesAvailability = driver.findElements(By.xpath("//*[@class=\"cookie-notice-content\"]")).size() > 0;
            if (clearCookiesAvailability) {
                try {
                    WebElement closeCookie = driver.findElement(By.xpath("//*[@id=\"btn-cookie-allow\"]"));
                    if (waitUntilElementIsDisplayed(closeCookie, 10)) {
                        closeCookie.click();
                    }
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }


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
     * @param elementAttr Can be web element or By Object
     */

    public <T> void javaScriptClick(T elementAttr, String name, ExtentTest test) throws IOException {
        ExtentTest node = test.createNode("javaScriptClick element: " + name);
        waitExplicit(WAIT_IN_SECONDS_MED);
        try {
            if (elementAttr.getClass().getName().contains("By")) {
                WebElement element = driver.findElement((By) elementAttr);
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", element);
                logger.info("Clicked Element: " + name);
                String screenShot = GenerateScreenShot.getScreenShot(driver);
                node.pass("Clicked Element: " + name + node.addScreenCaptureFromPath(screenShot));

            } else {
                JavascriptExecutor executor = (JavascriptExecutor) driver;
                executor.executeScript("arguments[0].click();", elementAttr);
                logger.info("Clicked Element: " + name);
                String screenShot = GenerateScreenShot.getScreenShot(driver);
                node.pass("Clicked Element: " + name + node.addScreenCaptureFromPath(screenShot));
            }
            logger.info("Clicked Element: " + name);

        } catch (Throwable e) {
            logger.info("an error has occured : " + e.getMessage());
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            node.fail(" failed to Clicked Element: " + name + node.addScreenCaptureFromPath(screenShot));

        }
    }

    /**
     * This method is used to type some content into an editable field.
     *
     * @param elementAttr Can be web element or By Object
     * @param text        The text value to be sent to the web element.
     */

    public <T> void writeText(T elementAttr, String text, String name, ExtentTest test) throws IOException {
        ExtentTest node = test.createNode("Writing text: " + text + " to Element: " + name);
        try {
            if (elementAttr.getClass().getName().contains("By")) {
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                jse.executeScript("arguments[0].style.border='2px solid red'", driver.findElement((By) elementAttr));
                driver.findElement((By) elementAttr).sendKeys(text);
            } else {
                JavascriptExecutor jse = (JavascriptExecutor) driver;
                jse.executeScript("arguments[0].style.border='2px solid red'", (WebElement) elementAttr);
                ((WebElement) elementAttr).sendKeys(text);
            }
            if (name != null) {
                String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
                String screenShot = GenerateScreenShot.getScreenShot(driver);
                node.pass("Writing text: " + text + " to Element: " + node.addScreenCaptureFromPath(screenShot));
            }
        } catch (Exception e) {
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            node.fail("Unable to Write to element:" + name + node.addScreenCaptureFromPath(screenShot));
            throw e;
        }
    }

    /**
     * This method is uses moveToElement() from the Actions class to simulate
     * mouse hover effect.
     *
     * @param elementAttr Can be web element or By Object
     * @param xOffset     X coordinate
     * @param yOffset     Y coordinate
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
        try {
            Actions action = new Actions(driver);
            if (elementAttr.getClass().getName().contains("By")) {
                WebElement myElement = driver.findElement((By) elementAttr);
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", myElement);
                action.moveToElement(myElement).perform();

            } else {
                WebElement fluentElement = waitFluent((WebElement) elementAttr);
                action.moveToElement(fluentElement).build().perform();
                // action.moveToElement(((WebElement)
                // elementAttr)).build().perform();
            }
            if (name != null) {
                logger.info("Mouse hovering element: " + name);

            }
        } catch (Throwable e) {
            logger.info("Mouse not hovering element: " + e.getMessage());

        }
    }

    /**
     * This method is used to simulate keyboard keys
     *
     * @param elementAttr Can be web element or By Object
     * @param key         Keys enum
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
     * @param elementAttr Can be web element or By Object
     * @return returns the inner text of the web element
     */

    public <T> String getText(T elementAttr, String name, ExtentTest test) throws IOException {
        ExtentTest node = test.createNode("Get Text from element: " + name);
        waitExplicit(WAIT_IN_SECONDS_MED);
        String text = "";
        try {
            if (elementAttr.getClass().getName().contains("By")) {
                text = driver.findElement((By) elementAttr).getText();
                String screenShot = GenerateScreenShot.getScreenShot(driver);
                node.pass("Getting text from element:" + node.addScreenCaptureFromPath(screenShot));
            } else {
                // WebElement fluentElement = waitFluent((WebElement)
                // elementAttr);
                // text = elementAttr.getText();
                String screenShot = GenerateScreenShot.getScreenShot(driver);
                node.pass("Getting text from element:" + node.addScreenCaptureFromPath(screenShot));
                text = ((WebElement) elementAttr).getText();
            }
            if (name != null) {
                logger.info("Getting text from: " + name);

            }

            return text;
        } catch (Exception e) {
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            node.fail("unable to get Text from element:" + name + node.addScreenCaptureFromPath(screenShot));
            throw e;
        }
    }


    public <T> void setAttribute(String attribute) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.getElementById('identifierId').setAttribute('data-initial-value', '10')");
    }

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
     * @paramtime Time in milliseconds
     */

    public void onLoadDelay() {
        /*
         * try { Thread.sleep(Values.time); } catch (InterruptedException e) {
         * e.printStackTrace(); }
         */
    }

    /**
     * The method is used to check the existence of the web element on the web
     * page
     *
     * @param elementAttr Can be web element or By Object
     * @return returns the Boolean value
     */

    // @SuppressWarnings("unchecked")
    public <T> boolean elementExists(T elementAttr, long time) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, time);
            if (elementAttr.getClass().getName().contains("By")) {
                By loc = (By) elementAttr;

                logger.info("elementExists");
                return driver.findElements((By) elementAttr).size() > 0;
            } else {

                logger.info("elementExists");
                return true;
            }
        } catch (Exception e) {
            logger.info("element doesnt Exists:" + e.getMessage());
            return false;
        }

    }

    public <T> boolean isElementPresent(WebElement ele) {
        try {
            ele.getLocation();
        } catch (Exception e) {
            return false;

        }
        return true;
    }

    public <T> boolean elementExistsPopUpMessage(T elementAttr, long time, String name, ExtentTest test) {
        ExtentTest node = test.createNode(name);
        try {
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            WebDriverWait wait = new WebDriverWait(driver, time);
            if (elementAttr.getClass().getName().contains("By")) {
                By loc = (By) elementAttr;
                wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
                return driver.findElements((By) elementAttr).size() > 0;
            } else {
                wait.until((ExpectedConditions.visibilityOf(((WebElement) elementAttr))));
                node.fail("Pop up is displayed " + name + node.addScreenCaptureFromPath(screenShot));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            node.pass("Pop up is NOT displayed ");
            return false;
        }

    }

    public <T> void isElementOnNextPage(T elementAttr, Long time, ExtentTest test) throws IOException {
        ExtentTest node = test.createNode("is element on next page ?");
        try {
            boolean flag = elementExists(elementAttr, time);
            if (flag) {
                String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
                String screenShot = GenerateScreenShot.getScreenShot(driver);
                node.pass("Element is on next page" + node.addScreenCaptureFromPath(screenShot));
            } else {
                String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
                String screenShot = GenerateScreenShot.getScreenShot(driver);
                node.fail("Element is not on next page" + node.addScreenCaptureFromPath(screenShot));

            }
        } catch (Exception e) {
            node.fail("issue with getting element" + e.getMessage());

        }
    }

    /**
     * isEnabled() to Check Whether the Element is Enabled Or Disabled in the
     * Selenium WebDriver.
     *
     * @param elementAttr Can be web element or By Object
     * @return returns the Boolean value
     */

    public <T> boolean isEnabled(T elementAttr) {
        if (elementAttr.getClass().getName().contains("By")) {
            if (!(driver.findElement((By) elementAttr).isEnabled()))
                logger.info("Element not enabled");

        } else {
            if (!((WebElement) elementAttr).isEnabled())
                logger.info("Element not enabled");


        }
        return true;

    }
    /*
     * public boolean ic_isEnabled(WebElement elementAttr) throws Exception {
     * boolean Finalresult = false; boolean result = false;
     *
     * //test= ExtentFactory.getInstance().createCase(name);
     *
     * if (elementAttr.getClass().getName().contains("By")) { result =
     * driver.findElement((By) elementAttr).isEnabled();
     *
     * } else{ result = elementAttr.isEnabled(); }
     *
     *
     *
     * return Finalresult; }
     */

    // ************************************ADDED LEVERCH FOR NEXT BUTTON ON
    // PRODUCT PAGE
    public boolean attributeEnabled(WebElement webe) {
        boolean decide = false;
        try {
            if (webe.isDisplayed()) {
                decide = true;
            }
        } catch (NoSuchElementException e) {
            return decide = false;
        }

        if (decide) {
            try {
                if (webe.isDisplayed()) {
                    decide = true;
                }
            } catch (NoSuchElementException e) {
                return decide = false;
            }
            return decide;
        }
        return decide;
    }

    public boolean attributeValidation(WebElement element, String attributeToCheck, String valueOfAttribute,
                                       int waitTime) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, waitTime);
            if (element.getClass().getName().contains("By")) {
                By loc = (By) element;

                wait.until(ExpectedConditions.visibilityOf(element));
                return true;
            } else {
                wait.until(ExpectedConditions.attributeToBe(element, attributeToCheck, valueOfAttribute));
                return true;
            }
        } catch (Exception e) {
            return false;
        }

    }

    /**
     * isDisplayed() to Check Whether the Element is visible Or not visible
     *
     * @param elementAttr Can be web element or By Object
     * @return
     */
    public <T> boolean waitUntilElementIsDisplayed(T elementAttr, int secs) throws InterruptedException {

        if ((secs / 1000) >= 1) {
            secs = secs / 1000;
        }

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        boolean flag = isDisplayed(elementAttr);
        int count = 0;
        while (flag == false & count < secs) {
            // refresh();
            flag = isDisplayed(elementAttr);
            explicitWait(1000);
            count++;
        }
        return flag;
    }

    public <T> Boolean isDisplayed(T elementAttr) {
        boolean flag = false;
        try {
            if (elementAttr.getClass().getName().contains("By")) {
                if ((driver.findElement((By) elementAttr).isDisplayed())) {
                    flag = true;
                }
            } else {
                if (((WebElement) elementAttr).isDisplayed()) {
                    flag = true;
                    return flag;
                }
            }
        } catch (Exception e) {
            flag = false;
            return flag;
        }
        return flag;
    }

    public void explicitWait(int timeInMillsecs) {
        try {
            Thread.sleep(timeInMillsecs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void explicitWait(int timeInMillsecs, ExtentTest test) throws IOException {
        ExtentTest node = test.createNode("waiting for:" + timeInMillsecs);
        try {
            Thread.sleep(timeInMillsecs);
            String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            node.pass("waiting " + node.addScreenCaptureFromPath(screenShot));
        } catch (InterruptedException e) {
            String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            node.fail("waiting" + node.addScreenCaptureFromPath(screenShot));
        }
    }

    public <T> void clear(T elementAttr, String name) throws IOException {
        try {
            if (elementAttr.getClass().getName().contains("By")) {
                driver.findElement((By) elementAttr).clear();
            } else {
                WebElement fluentElement = waitFluent((WebElement) elementAttr);
                fluentElement.clear();
                // ((WebElement) elementAttr).clear();
            }
            if (name != null) {
                logger.info("Clearing element: " + name);

            }
        } catch (Throwable e) {
            // e.printStackTrace();

        }
    }

    /**
     * List the size of child elements to this element
     *
     * @param elementAttr Can be web element or By Object
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
     * @param elementAttr Can be web element or By Object
     * @param value       Value is to be selected from drop down
     */
    public <T> void selectFromDropDownUsingVisibleText(T elementAttr, String value, String name) {
        try {
            if (elementAttr.getClass().getName().contains("By")) {
                Select select = new Select(driver.findElement((By) elementAttr));
                select.selectByVisibleText(value);
            } else {
                WebElement fluentElement = waitFluent((WebElement) elementAttr);
                fluentElement.click();
                // Select select = new Select((WebElement) elementAttr);
                Select select = new Select(fluentElement);
                select.selectByVisibleText(value);
            }
            if (name != null) {
                logger.info("Selecting value: " + value + " from dropdown: " + name);

            }
        } catch (Throwable e) {
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
     * @param driver driver object
     * @param time   wait time
     */

    public void turnOnImplicitWaits(WebDriver driver, int time) {

        driver.manage().timeouts().implicitlyWait(time, TimeUnit.SECONDS);
    }

    /**
     * This method used for checking that an element is either invisible or not
     * present on the DOM.
     *
     * @param elementAttr Can be web element or By Object
     * @param time        wait time
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
        } else {
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
     * @param elementAttr Can be web element or By Object
     * @param time        wait time
     */
    public <T> void waitForElementPresent(T elementAttr, long time) {

        WebDriverWait wait = new WebDriverWait(driver, time);
        if (elementAttr.getClass().getName().contains("By")) {
            By loc = (By) elementAttr;
            try {
                wait.until(ExpectedConditions.presenceOfElementLocated(loc));
            } catch (Exception e) {
                logger.info("Element still present: " + loc);
                System.out.println(e.toString());
            }
        }
    }

    public <T> void waitForElementDisplay(WebElement el, long time, String name) {

        WebDriverWait wait = new WebDriverWait(driver, time);
        try {
            wait.until(ExpectedConditions.visibilityOf(el));

        } catch (Exception e) {
            logger.info("Unable to locate element: " + name);
            throw e;

        }
    }

    /**
     * This method used for for checking the presence of the value in the list
     *
     * @param elementAttr Can be web element or By Object
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
     * @param elementAttr Can be web element or By Object
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


    public <T> void selectExactValueFromListUsingTex(T elementAttr, String value) {

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
     * @param elementAttr Can be web element or By Object
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
     * @param elementAttr Identifier of the web element.
     * @param index       The text to be selected in the list.
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

    public <T> void waitForElementVisibility(T elementAttr, String name, long time) {
        WebDriverWait wait = new WebDriverWait(driver, time);
        try {
            if (elementAttr.getClass().getName().contains("By")) {
                By loc = (By) elementAttr;
                wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
                logger.info(name + "is visible");
            } else {
                wait.until((ExpectedConditions.visibilityOf(((WebElement) elementAttr))));
                logger.info(name + "is visible");
            }
        } catch (Exception e) {
            logger.info(name + "is not visible");
            throw e;

        }
    }

    public <T> void waitForElementClickable(T elementAttr, String name, long time) {
        WebDriverWait wait = new WebDriverWait(driver, time);
        try {
            if (elementAttr.getClass().getName().contains("By")) {
                wait.until(ExpectedConditions.elementToBeClickable(driver.findElement((By) elementAttr)));
                logger.info("find element:" + name);
            } else {
                wait.until((ExpectedConditions.elementToBeClickable(((WebElement) elementAttr))));
                logger.info("find element:" + name);
            }
        } catch (Exception e) {
            logger.info("can't find element:" + name);
            throw e;
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
        } else {
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
            // onLoadDelay();
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
     * @param elementAttr Can be web element or By Object
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

    // @SuppressWarnings("deprecation")
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

    // @SuppressWarnings("deprecation")
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

    // @SuppressWarnings("deprecation")
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
            while (driver.findElement((By) elementAttr).getAttribute(attribute).contains(value)) {
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

            Boolean angularUnDefined = (Boolean) ((JavascriptExecutor) driver)
                    .executeScript("return window.angular === undefined");

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
     *
     * @param element
     * @return true if element present else returns false
     */

    public boolean scrollToElement(WebElement element, String name) {
        waitExplicit(WAIT_IN_SECONDS_MED);
        boolean isElementPresent = false;
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            if (element != null) {
                js.executeScript("arguments[0].scrollIntoView(true);", element);
                isElementPresent = true;
            }
            if (name != null) {
                logger.info("Scrolling to Element: " + name);

            }
        } catch (Throwable e) {
            e.printStackTrace();

        }
        return isElementPresent;
    }

    public void scrollToElement(WebElement element, String name, ExtentTest test) throws IOException {
        ExtentTest node = test.createNode("Searched Element: " + name);

        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            if (element != null) {
                js.executeScript("arguments[0].scrollIntoView(true);", element);
                String screenShot = GenerateScreenShot.getScreenShot(driver);
                node.pass("Scrolling to element:" + node.addScreenCaptureFromPath(screenShot));

            }
            if (name != null) {
                logger.info("Scrolling to Element: " + name);

            }
        } catch (Exception e) {
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            node.fail("Unable to scroll to element" + name + node.addScreenCaptureFromPath(screenShot));
            throw e;

        }
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
     *
     * @param ele
     * @param name
     */
    public void selectCheckBox(WebElement ele, String name) {
        try {
            boolean selected = false;
            selected = ele.isSelected();
            if (selected)
                ele.click();
            if (name != null) {
                logger.info("Clicking checkbox: " + name);

            }

        } catch (Throwable e) {
            e.printStackTrace();

        }
    }

    public WebElement waitFluent(WebElement ele) {
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(1)).ignoring(TimeoutException.class)
                .ignoring(NoSuchElementException.class);
        newElement = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver1) {
                if (ele.isDisplayed())
                    return ele;
                else
                    return null;
            }
        });
        return newElement;
    }

    // By sourav
    public void CheckEnabilityofButton(WebElement elementAttr, String name, boolean Expstatus, ExtentTest test)
            throws IOException {
        String TestDescription = "Verify that " + name + " button Enabled ";
        boolean resEnable = true;
        String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
        // ExtentTest test=
        // ExtentFactory.getInstance().createCase(TestDescription);
        ExtentTest node = test.createNode("Check Enability of Button is " + String.valueOf(Expstatus));
        try {
            resEnable = ic_isEnabled(elementAttr);
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            node.pass(TestDescription + " Expected : " + Expstatus + " Actual :" + resEnable,
                    MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
        } catch (Exception e) {
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            node.fail(TestDescription + " Expected : " + Expstatus + " Actual :" + resEnable,
                    MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
        }
    }

    public <T> void clickEle(T elementAttr, String name, ExtentTest test) throws IOException {
        // INSTANCE IS CREATED THAT HAS REFERENCE TO THE MAIN TEST THAT WAS
        // CREATED
        ExtentTest node = test.createNode("Clicked Element: " + name);
        String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
        if (elementAttr.getClass().getName().contains("By")) {
            driver.findElement((By) elementAttr).click();
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            node.pass("Successfully clicked on " + name,
                    MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
            logger.info("Clicked Element: " + name);
        } else {
            WebElement fluentElement = waitFluent((WebElement) elementAttr);
            fluentElement.click();
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            node.pass("Successfully clicked on " + name,
                    MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
            logger.info("Clicked Element: " + name);

            // ((WebElement) elementAttr).click();
        }
        // if(name != null){
        // ADD THE VALIDATION METHODS- WILL BE USED WITH THE test INSTANCE THAT
        // WILL PRINT OUT THE STEPS

        // }

    }

    // public void CompareResult(String TestDescription,String Exp, String
    // Actual,ExtentTest test) throws IOException{
    // //INSTANCE IS CREATED THAT HAS REFERENCE TO THE MAIN TEST THAT WAS
    // CREATED
    // ExtentTest node=test.createNode("Verify result for test
    // "+TestDescription);
    // String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new
    // Date());
    // String screenShot=GenerateScreenShot.getScreenShot(driver);
    // try{
    // if (Actual.contains(Exp)) {
    //
    // node.pass("Successfully Verified : " + TestDescription + " Expected :
    // "+Exp+" Actual
    // :"+Actual,MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
    //
    // } else {
    //
    // node.fail("Error found : " + TestDescription + " Expected : "+Exp+"
    // Actual
    // :"+Actual,MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
    //
    // }
    //
    // } catch(Throwable e){
    // e.printStackTrace();
    // try {
    // node.fail(" Unknown Error found : : " + TestDescription + " Expected :
    // "+Exp+" Actual
    // :"+e.getMessage(),MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
    // } catch (IOException e1) {
    // // TODO Auto-generated catch block
    // e1.printStackTrace();
    // }
    //
    // }
    // }
    // public void dropDownselectbyvisibletext(WebElement elementAttr,String
    // valueToselect,String Testname,ExtentTest test) {
    // //INSTANCE IS CREATED THAT HAS REFERENCE TO THE MAIN TEST THAT WAS
    // CREATED
    // ExtentTest node=test.createNode("Select value from dropdown : "+
    // Testname);
    // String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new
    // Date());
    //
    // try{
    // // Create object of the Select class
    // Select se = new Select(elementAttr);
    //
    // // Select the option with value
    //
    // se.selectByVisibleText(valueToselect);
    // String res = se.getFirstSelectedOption().getText();
    // if(res.equalsIgnoreCase(valueToselect)){
    // String screenShot=GenerateScreenShot.getScreenShot(driver);
    // node.pass("Successfully selected : " + Testname + " Expected :
    // "+valueToselect+" Actual
    // :"+res,MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
    // }
    // }catch(Throwable e){
    // e.printStackTrace();
    // try {
    // String screenShot=GenerateScreenShot.getScreenShot(driver);
    // test.fail("Error to select : " + valueToselect + " form the dropdown :
    // "+Testname+" Error message
    // :"+e.getMessage(),MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
    // } catch (IOException e1) {
    // // TODO Auto-generated catch block
    // e1.printStackTrace();
    // }
    //
    // }
    //
    // }
    public boolean ic_isEnabled(WebElement elementAttr) throws Exception {
        boolean Finalresult = false;
        boolean result = false;

        // test= ExtentFactory.getInstance().createCase(name);

        if (elementAttr.getClass().getName().contains("By")) {
            result = driver.findElement((By) elementAttr).isEnabled();

        } else {
            result = ((WebElement) elementAttr).isEnabled();
        }
        return result;

    }


    public void checkIfPageIsLoadedByURL(String urlFragment, String name, ExtentTest test) {
        ExtentTest node = test.createNode("Has next Page louded? " + name);
        try {
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            if (driver.getCurrentUrl().contains(urlFragment)) {

                node.pass("Page has been loaded: " + name + node.addScreenCaptureFromPath(screenShot));

            } else {

                node.fail("Page has not been loaded: " + name + node.addScreenCaptureFromPath(screenShot));

            }

        } catch (IOException e) {

            node.fail("Page has not been loaded: " + name + e.getMessage());
        }
    }

    // NOTE THE BELOW METHOD IS CASE SENSTIVE WITH THE ACTUAL/EXP
    public void CompareResult(String TestDescription, String Exp, String Actual, ExtentTest test) throws IOException {
        ExtentTest node = test.createNode("Verify " + TestDescription);
        String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
        String screenShot = GenerateScreenShot.getScreenShot(driver);
        if (Actual.contains(Exp)) {
            System.out.println("INSIDE COMPARE RESULT expected : " + Exp + " " + Actual);
            node.pass("Successfully Verified : " + TestDescription + " Expected : " + Exp + " Actual :" + Actual,
                    MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
        } else {
            node.fail("Error found  : " + TestDescription + " Expected : " + Exp + " Actual :" + Actual,
                    MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
        }
    }

    public <T> boolean elementExistWelcome(T elementAttr, long time, String name, ExtentTest test) {
        ExtentTest node = test.createNode(name);
        try {
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            WebDriverWait wait = new WebDriverWait(driver, time);
            if (elementAttr.getClass().getName().contains("By")) {
                By loc = (By) elementAttr;
                wait.until(ExpectedConditions.visibilityOfElementLocated(loc));
                return driver.findElements((By) elementAttr).size() > 0;
            } else {
                wait.until((ExpectedConditions.visibilityOf(((WebElement) elementAttr))));
                node.pass("Pop up is display " + name + node.addScreenCaptureFromPath(screenShot));
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            node.fail("Pop up is NOT displayed");
            return false;

        }

    }

    public void noRecordsReturnedFromTable(ExtentTest test, String name) {
        ExtentTest node = test.createNode("Clicked Element: " + name);
        try {

            String screenShot = GenerateScreenShot.getScreenShot(driver);
            node.fail(name + node.addScreenCaptureFromPath(screenShot));
        } catch (IOException e) {
            node.fail(e.getMessage());
        }
    }

    public void dropDownselectbyvisibletext(WebElement elementAttr, String valueToselect, String Testname,
                                            ExtentTest test) {
        // INSTANCE IS CREATED THAT HAS REFERENCE TO THE MAIN TEST THAT WAS
        // CREATED
        ExtentTest node = test.createNode("Select value from dropdown : " + Testname);
        String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());

        try {
            Select se = new Select(elementAttr);
            // Select the option with value
            se.selectByVisibleText(valueToselect);
            String res = se.getFirstSelectedOption().getText();
            if (res.equalsIgnoreCase(valueToselect)) {
                String screenShot = GenerateScreenShot.getScreenShot(driver);
                node.pass("Successfully selected : " + Testname + " Expected : " + valueToselect + " Actual :" + res,
                        MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
            }
        } catch (Throwable e) {
            node.fail(e.getMessage());
            try {
                String screenShot = GenerateScreenShot.getScreenShot(driver);
                test.fail("Error to select  : " + valueToselect + " form the dropdown : " + Testname
                                + " Error message :" + e.getMessage(),
                        MediaEntityBuilder.createScreenCaptureFromPath(screenShot).build());
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                node.fail(e1.getMessage());
            }

        }

    }

    public void expectSingleRow(List<WebElement> elements, String name, ExtentTest test) {
        ExtentTest node = test.createNode("Clicked Element: " + name);
        System.out.println(elements.size());
        try {
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            if (elements.size() >= 2) {
                node.fail("More than one element was returned" + name + node.addScreenCaptureFromPath(screenShot));
            } else if (elements.size() == 1) {
                node.pass("Exactly one element has been returned" + name + node.addScreenCaptureFromPath(screenShot));
            } else {
                node.fail("No results has been found" + name + node.addScreenCaptureFromPath(screenShot));
            }
        } catch (Exception e) {
            node.fail(e.getMessage());
        }
    }

    public void scrollElementIntoView(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", element);
        explicitWait(6000);
    }

    public void scrollElemetnToCenterOfView(WebElement element, String name, ExtentTest test) throws IOException {
        ExtentTest node = test.createNode("Scrolled to : " + name);
        try {
            String scrollElementIntoMiddle = "var viewPortHeight = Math.max(document.documentElement.clientHeight, window.innerHeight || 0);"
                    + "var elementTop = arguments[0].getBoundingClientRect().top;"
                    + "window.scrollBy(0, elementTop-(viewPortHeight/2));";

            ((JavascriptExecutor) driver).executeScript(scrollElementIntoMiddle, element);
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            node.pass("scroll to element" + name + node.addScreenCaptureFromPath(screenShot));
        } catch (Exception e) {
            String screenShot = GenerateScreenShot.getScreenShot(driver);
            node.fail("unable to scroll to element: " + name + node.addScreenCaptureFromPath(screenShot));
            throw e;
        }
    }

    public void Robot_WriteText(String Input) throws AWTException {
        Robot robot = new Robot();
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection stringSelection = new StringSelection(Input);
        clipboard.setContents(stringSelection, null);
        //Use Robot class instance to simulate CTRL+C and CTRL+V key events :

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        //Simulate Enter key event
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

}