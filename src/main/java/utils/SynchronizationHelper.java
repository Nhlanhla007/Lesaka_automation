/* -----------------------------------------------------------------------------
 * Copyright (c) 2008 Plateau Systems, Ltd.
 *
 * This software and documentation is the confidential and proprietary
 * information of Plateau Systems.  Plateau Systems
 * makes no representation or warranties about the suitability of the
 * software, either expressed or implied.  It is subject to change
 * without notice.
 *
 * U.S. and international copyright laws protect this material.  No part
 * of this material may be reproduced, published, disclosed, or
 * transmitted in any form or by any means, in whole or in part, without
 * the prior written permission of Plateau Systems.
 * -----------------------------------------------------------------------------
 

package utils;


import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import Logger.Log;

import java.util.List;
import java.util.logging.Level;

*//**
 * <p>Automation synchronization helper class.  This class contains methods to help webdriver
 * clients synchronize with events in the browser.  Specifically, this class contains helper
 * methods to facilitate the wait-and-retry behavior - clients of these methods must pass in
 * the behaviors desired.
 *
 * <p>Many methods are overloaded to take a number of different combinations of arguments.  In
 * general, the various extra options are typically:<ul>
 * <li>AutomationTarget target - this is used to target the action to a specific window or frame (default = current)
 * <li>AssertionError throwOnTimeout - exception to throw on a time-out (the default is AutomationTimeoutError)
 * <li>long syncTimeout - use to override the time that the waitFor() loop waits for the condition to be true (defeault = 30 seconds)
 * </ul>
 *
 * @author SHamilton
 *//*
public class SynchronizationHelper {
	static Logger logger = Log.getLogData(Action.class.getSimpleName());

	public static final long SYNC_TICK = 1000;
	private static final String SELECTOR_NAME = "selectorName";
	private static final String DONE = "done";
	private static final String OPTION = "option";

	*//**
	 * make sure all jquery based ajax component is loaded
	 *//*
	public static void waitForJQueryBaseAjaxCalls(final long timeOutFactor) {
		waitForHTMLBasePageLoading();
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				try {
					final String activeConnection = String.valueOf(HtmlAutomationHelper.executeJavascript("return jQuery.active;")).trim();
					return "0".equals(activeConnection);
				} catch (final WebDriverException wex) {
					return true;
				}
			}
		}, null, new ImplicitAssertionError("Ajax Component is still loading due to server response is slow."), getSyncTimeout() * timeOutFactor);
	}

	*//**
	 * Make sure that current window is loaded
	 *//*
	public static void waitForHTMLBasePageLoading() {
		waitForHTMLBasePageLoading(1);
	}

	public static void waitForJQueryBaseAjaxCalls() {
		waitForHTMLBasePageLoading(1);
	}

	*//**
	 *  Make sure that current window is loaded
	 *  @param timeOutFactor if you want to increase the timout provided in config
	 *//*
	public static void waitForHTMLBasePageLoading(final long timeOutFactor) {
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				try {
					final String readyState = String.valueOf(HtmlAutomationHelper.executeJavascript("return document.readyState;"));
					return "complete".equals(readyState);
				} catch (final WebDriverException wex) {
					if (wex.getMessage().contains("Modal dialog present")) {
						logger.info("Modal dialog present so Webdriver is waiting until it is disappeared !");
						return false;
					}
					throw new ImplicitAssertionError("window is closed or it is not html page :" + wex.getMessage());
				}
			}
		}, null, new ImplicitAssertionError("document.readyState is not complete state !"), getSyncTimeout() * timeOutFactor);
	}

	*//**
	 * Waits for the loading icon to disappear in the UI5 page.
	 *//*
	public static void waitForUI5PageLoading() {
		waitFor(new SyncCondition() {
				@Override
				public boolean testCondition() {
					return !(HtmlAutomationHelper.doesElementExistByClassName("sapUiLocalBusyIndicator")// sapUiLocalBusyIndicatorAnimStandard
							&& HtmlAutomationHelper.isElementVisibleByClassName("sapUiLocalBusyIndicator"));
				}
			}, new ImplicitAssertionError("UI5 page not loaded properly"));
	}

	*//**
	 * This method wait for mobile page to be loaded successfully before
	 * performing any other action on the new window. If it times out, the ImplicitAssertionError is thrown.
	 *
	 * @param none
	 * @throws ImplicitAssertionError if the operation fails to return true in the time allotted
	 *//*
	public static void waitForMobilePageLoaded() {
		waitForMobilePageLoaded(3);
	}

	*//**
	 * This method wait for mobile page to be loaded successfully before
	 * performing any other action on the new window. If it times out, the ImplicitAssertionError is thrown.
	 *
	 * @param none
	 * @throws ImplicitAssertionError if the operation fails to return true in the time allotted
	 *//*
	public static void waitForMobilePageLoaded(final long timeOutFactor) {
		final String loadingXpath = "//UIAActivityIndicator[@name='In progress']";
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				try {
					return !HtmlAutomationHelper.doesWebElementExistByXPath(loadingXpath);
				} catch (final WebDriverException ex) {
					throw new ImplicitAssertionError(ex.getMessage());
				}

			}
		}, null, new ImplicitAssertionError("User Home page not loaded, time took more than " + getSyncTimeout() * timeOutFactor + " seconds"),
				getSyncTimeout() * timeOutFactor);
	}

	*//**
	 * Perform an operation against an AutomationTarget until it succeeds (returns true) or
	 * times out.  If it times out, the AutomationTimeoutError exception is thrown.
	 *
	 * @param condition SyncCondition implementation to execute until it returns true or times out
	 * @param target optional AutomationTarget to execute (can be null)
	 * @param syncTimeout timeout interval till which the execution has to be retried
	 * @throws AutomationTimeoutException if the operation fails to return true in the time allotted
	 *//*
	public static void waitFor(final SyncCondition condition, final AutomationTarget target, final long syncTimeout) {
		waitFor(condition, target, null, syncTimeout);
	}

	*//**
	 * Perform an operation against an AutomationTarget until it succeeds (returns true) or
	 * times out.  If it times out, the AutomationTimeoutError exception is thrown.
	 *
	 * @param condition SyncCondition implementation to execute until it returns true or times out
	 * @param throwOnTimeout exception to throw on timeout (null = throw the AutomationTimeoutException)
	 * @throws AutomationTimeoutException if the operation fails to return true in the time allotted
	 *//*
	public static void waitFor(final SyncCondition condition, final AssertionError throwOnTimeout) {
		waitFor(condition, null, throwOnTimeout, getSyncTimeout());
	}

	public static long getSyncTimeout() {
		return AutomationConfiguration.getSyncTimeout();
	}

	*//**
	 * Perform an operation against an AutomationTarget until it succeeds (returns true) or
	 * times out.  If it times out, the AutomationTimeoutError exception is thrown.
	 *
	 * @param condition SyncCondition implementation to execute until it returns true or times out
	 * @param target optional AutomationTarget to execute (can be null)
	 * @param throwOnTimeout exception to throw on timeout (null = throw the AutomationTimeoutException)
	 * @param syncTimeout timeout interval till which the execution has to be retried
	 * @throws AutomationTimeoutException if the operation fails to return true in the time allotted
	 *//*
	public static void waitFor(final SyncCondition condition, final AutomationTarget target, final AssertionError throwOnTimeout, final long syncTimeout) {
		long timeoutThresholdValue = System.currentTimeMillis() + syncTimeout;
		boolean retValue = false;
		int i = 0;
		while (!retValue && System.currentTimeMillis() < timeoutThresholdValue && i < 10) {
			try {
				if (target != null && target.getName() != null)
					target.switchTo();
				retValue = condition.testCondition();
			} catch (final NullPointerException e) {
				logger.finest(FrameworkUtil.getStackTrace(e));
			} catch (final NoSuchElementException e) {
				logger.finest(FrameworkUtil.getStackTrace(e));
			} catch (final UnsupportedOperationException e) {
				logger.finest(FrameworkUtil.getStackTrace(e));
			} catch (final WebDriverException e) {
				logger.finest(FrameworkUtil.getStackTrace(e));
				if (e.getMessage().indexOf("java.net.SocketException: Connection reset") > -1
						|| e.getMessage().indexOf("Error calling method on NPObject!") > -1 || e.getMessage().indexOf("Session has no driver") > -1) {
					i++;
					logger.info("waitFor() exception: " + e.getMessage() + " {" + i + "}");
					timeoutThresholdValue = System.currentTimeMillis() + syncTimeout;
				}
			} catch (final Throwable t) {
				logger.info("Unexcepted Exception at SynchronizationHelper-->waitFor()");
				throw new AutomationTimeoutError("Unexcepted Exception at SynchronizationHelper-->waitFor()");
			}
			if (!retValue)
				pause(SYNC_TICK);
		}
		if (!retValue) {
			if (throwOnTimeout != null)
				throw throwOnTimeout;
			throw new AutomationTimeoutError("Operation timed out");
		}
	}

	*//**
	 * Sleep the current thread for the specified time.
	 * @param milliSec milliseconds to sleep
	 *//*
	public static void pause(final long milliSec) {
		try {
			Thread.sleep(milliSec);
		} catch (final InterruptedException e) {
			// ignore
		}
	}

	*//**
	 * Sleep the current thread for the specified time if the automation.latency.pause is turned on
	 * in automation-config.txt.
	 *
	 * This method is provided for the convenience of the remote employees, whose connections to the
	 * HQ have noticeable latencies. The automation.latency.pause should only be enabled in such
	 * cases.
	 *
	 * If you simply want to pause, please use {@link #pause(long)}.
	 *
	 * @see {@link #pause(long)}
	 * @param milliSec milliseconds to sleep
	 *//*
	public static void latencyPause(final long milliSec) {
		if (AutomationConfiguration.isLatencyPauseEnabled()) {
			pause(milliSec);
		}
	}

	*//**
	 * Wait until an element becomes visible (WebElement.isDisplayed() == true).
	 *
	 * @param elementID id of element
	 *//*
	public static void waitUntilElementVisible(final String elementID) {
		waitUntilElementVisible(elementID, null);
	}

	*//**
	 * Wait until an element becomes visible (WebElement.isDisplayed() == true).
	 *
	 * @param elementID id of element
	 * @param target the window/frame to target
	 *//*
	public static void waitUntilElementVisible(final String element, final AutomationTarget target) {
		waitUntilElementVisible(element, target, new ImplicitAssertionError("Element is not visible !"));
	}

	*//**
	 * Wait until an element becomes visible (WebElement.isDisplayed() == true).
	 *
	 * @param elementID id of element
	 * @param target the window/frame to target
	 * @param throwOnTimeout exception to throw on timeout
	 *//*
	public static void waitUntilElementVisible(final String element, final AutomationTarget target, final AssertionError throwOnTimeout) {
		waitUntilElementVisible(element, target, throwOnTimeout, getSyncTimeout());
	}

	*//**
	 * Wait until an element becomes visible (WebElement.isDisplayed() == true).
	 *
	 * @param elementID id of element
	 * @param target the window/frame to target
	 * @param throwOnTimeout exception to throw on timeout
	 * @param syncTimeout milliseconds to wait
	 *//*
	public static void waitUntilElementVisible(final String element, final AutomationTarget target, final AssertionError throwOnTimeout,
			final long syncTimeout) {
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				final WebElement resultsDiv = AutomationSession.getWebDriver().findElement(By.id(element));
				return (resultsDiv != null && resultsDiv.isDisplayed());
			}
		}, target, throwOnTimeout, syncTimeout);
	}

	*//**
	 * Wait until an element becomes visible (WebElement.isDisplayed() == true).
	 *
	 * @param elementClassName css class of element
	 * @param target the window/frame to target
	 * @param throwOnTimeout exception to throw on timeout
	 * @param syncTimeout milliseconds to wait
	 *//*
	public static void waitUntilElementVisibleByClassName(final String elementClassName, final AutomationTarget target, final AssertionError throwOnTimeout,
			final long syncTimeout) {
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				final WebElement resultsDiv = AutomationSession.getWebDriver().findElement(By.className(elementClassName));
				return (resultsDiv != null && resultsDiv.isDisplayed());
			}
		}, target, throwOnTimeout, syncTimeout);
	}

	*//**
	 * Wait until an element becomes visible (WebElement.isDisplayed() == true).
	 *
	 * @param xpath xpath of element
	 * @param target the window/frame to target
	 * @param throwOnTimeout exception to throw on timeout
	 * @param syncTimeout milliseconds to wait
	 *//*
	public static void waitUntilElementVisibleByXPath(final String xpath, final AutomationTarget target, final AssertionError throwOnTimeout,
			final long syncTimeout) {
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				final WebElement resultsDiv = AutomationSession.getWebDriver().findElement(By.xpath(xpath));
				return (resultsDiv != null && resultsDiv.isDisplayed());
			}
		}, target, throwOnTimeout, syncTimeout);
	}

	*//**
	 * Check to see if the element text exists in the provided divID.
	 *
	 * @param divID id of the DIV to check
	 * @param text text to look for
	 * @param throwOnTimeout exception to throw on timeout
	 *//*
	public static void doesElementTextExist(final String divID, final String text, final AssertionError throwOnTimeout) {
		doesElementTextExist(divID, text, null, throwOnTimeout, getSyncTimeout());
	}

	*//**
	 * Check to see if the element text exists in the provided divID.
	 *
	 * @param divID id of the DIV to check
	 * @param text text to look for
	 * @param target the window/frame to target
	 * @param throwOnTimeout exception to throw on timeout
	 * @param syncTimeout milliseconds to wait
	 *//*
	public static void doesElementTextExist(final String divID, final String text, final AutomationTarget target, final AssertionError throwOnTimeout,
			final long syncTimeout) {
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				return AutomationSession.getWebDriver().findElement(By.id(divID)).getText().contains(text);
			}
		}, target, throwOnTimeout, syncTimeout);
	}

	*//**
	 * Wait for a link identified by the provided linkText to become available.
	 * @param linkText link text to search for
	 *//*
	public static void waitForLinkByText(final String linkText) {
		waitForLinkByText(linkText, null, null);
	}

	*//**
	 * Wait for a link identified by the provided linkText to become available.
	 * @param linkText link text to search for
	 * @param target the window/frame to target
	 *//*
	public static void waitForLinkByText(final String linkText, final AutomationTarget target) {
		waitForLinkByText(linkText, target, null);
	}

	*//**
	 * Wait for a link identified by the provided linkText to become available.
	 * @param linkText link text to search for
	 * @param target the window/frame to target
	 * @param throwOnTimeout exception to throw on timeout
	 *//*
	public static void waitForLinkByText(final String linkText, final AutomationTarget target, final AssertionError throwOnTimeout) {
		waitForLinkByText(linkText, target, throwOnTimeout, getSyncTimeout());
	}

	*//**
	 * Wait for a link identified by the provided linkText to become available.
	 * @param linkText link text to search for
	 * @param target the window/frame to target
	 * @param throwOnTimeout exception to throw on timeout
	 * @param syncTimeout milliseconds to wait
	 *//*
	public static void waitForLinkByText(final String linkText, final AutomationTarget target, final AssertionError throwOnTimeout, final long syncTimeout) {
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				final WebDriver driver = AutomationSession.getWebDriver();
				if (driver.findElement(By.linkText(linkText)) != null) {
					return true;
				}
				return false;
			}
		}, target, throwOnTimeout, syncTimeout);
	}

	*//**
	 * Wait for the frame/window title to match the provided text.
	 * @param title title text to wait for
	 * @param target the window/frame to target
	 * @param throwOnTimeout exception to throw on timeout
	 *//*
	public static void waitForPageTitle(final String title, final AutomationTarget target, final AssertionError throwOnTimeout) {
		waitForPageTitle(title, target, throwOnTimeout, getSyncTimeout());
	}

	*//**
	 * Wait for the frame/window title to match the provided text.
	 * @param title title text to wait for
	 * @param throwOnTimeout exception to throw on timeout
	 *//*
	public static void waitForPageTitle(final String title, final AssertionError throwOnTimeout) {
		waitForPageTitle(title, null, throwOnTimeout, getSyncTimeout());
	}

	*//**
	 * Wait for the frame/window title to match the provided text.
	 * @param title title text to wait for
	 * @param target the window/frame to target
	 * @param throwOnTimeout exception to throw on timeout
	 * @param syncTimeout milliseconds to wait
	 *//*
	public static void waitForPageTitle(final String title, final AutomationTarget target, final AssertionError throwOnTimeout, final long syncTimeout) {
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				final WebDriver driver = AutomationSession.getWebDriver();
				if (driver.getTitle().contains(title)) {
					return true;
				}
				return false;
			}
		}, target, throwOnTimeout, syncTimeout);
	}

	*//**
	 * Wait for search selector named element to possess the given value.
	 * @param value value to wait for the element to have
	 * @param target the window/frame to target
	 * @param throwOnTimeout exception to throw on timeout
	 *//*
	public static void waitForSearchSelector(final String value, final AutomationTarget target, final AssertionError throwOnTimeout) {
		waitForElementByNameWithValue(SELECTOR_NAME, value, target, throwOnTimeout, getSyncTimeout());
		//pause(500); // give webdriver some time to catch up
	}

	*//**
	 * Wait for the element identified by the provided name to possess the given value.
	 * @param name name of the element to look for
	 * @param value value to wait for the element to have
	 * @param target the window/frame to target
	 *//*
	public static void waitForElementByNameWithValue(final String name, final String value, final AutomationTarget target) {
		waitForElementByNameWithValue(name, value, target, null, getSyncTimeout());
	}

	*//**
	 * Wait for the element identified by the provided name to possess the given value.
	 * @param name name of the element to look for
	 * @param value value to wait for the element to have
	 * @param target the window/frame to target
	 * @param throwOnTimeout exception to throw on timeout
	 *//*
	public static void waitForElementByNameWithValue(final String name, final String value, final AutomationTarget target,
			final AssertionError throwOnTimeout) {
		waitForElementByNameWithValue(name, value, target, throwOnTimeout, getSyncTimeout());
	}

	*//**
	 * Wait for the element identified by the provided name to possess the given value.
	 * @param name name of the element to look for
	 * @param value value to wait for the element to have
	 * @param target the window/frame to target
	 * @param throwOnTimeout exception to throw on timeout
	 * @param syncTimeout milliseconds to wait
	 *//*
	public static void waitForElementByNameWithValue(final String name, final String value, final AutomationTarget target, final AssertionError throwOnTimeout,
			final long syncTimeout) {
		waitFor(new SyncCondition() {
			final WebDriver driver = AutomationSession.getWebDriver();

			@Override
			public boolean testCondition() {
				if (driver.findElement(By.name(name)).getAttribute("value").equals(value)) {
					return true;
				}
				return false;
			}
		}, target, throwOnTimeout, syncTimeout);
	}

	*//**
	 * Wait for the element identified by the provided ID to be present.
	 * @param elementID id of the element to look for
	 * @param target the window/frame to target
	 * @param throwOnTimeout exception to throw on timeout
	 *//*
	public static void waitForElementByID(final String elementID, final AutomationTarget target, final AssertionError throwOnTimeout) {
		waitForElementByID(elementID, target, throwOnTimeout, getSyncTimeout());
	}

	*//**
	 * Wait for the element identified by the provided ID to be present.
	 * @param elementID id of the element to look for
	 * @param target the window/frame to target
	 * @param syncTimeout milliseconds to wait
	 *//*
	public static void waitForElementByID(final String elementID, final AutomationTarget target, final long syncTimeout) {
		waitForElementByID(elementID, target, null, syncTimeout);
	}

	*//**
	 * Wait for the element identified by the provided ID to be present.
	 * @param elementID id of the element to look for
	 * @param target the window/frame to target
	 * @param throwOnTimeout exception to throw on timeout
	 * @param syncTimeout milliseconds to wait
	 *//*
	public static void waitForElementByID(final String elementID, final AutomationTarget target, final AssertionError throwOnTimeout, final long syncTimeout) {
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				final WebDriver driver = AutomationSession.getWebDriver();
				//System.out.println(driver.getTitle());
				//System.out.println(driver.getCurrentUrl());
				try {
					return (driver.findElement(By.id(elementID)) != null);
				} catch (final WebDriverException e) {
					return findElementByID(elementID) != null;//this can be moved one level up as well so that there will be better stability to the DSL, however as of now, the wait happens only when the element is not found.
				}
			}
		}, target, throwOnTimeout, syncTimeout);
	}

	*//**
		 * Wait for the element identified by the provided ID to be present.
		 * @param elementID id of the element to look for
		 * @param target the window/frame to target
		 * @param throwOnTimeout exception to throw on timeout
		 * @param syncTimeout milliseconds to wait
		 *//*
	public static void waitForElementByClassName(final String className, final AutomationTarget target, final AssertionError throwOnTimeout,
			final long syncTimeout) {
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				final WebDriver driver = AutomationSession.getWebDriver();
				return (driver.findElement(By.className(className)) != null && driver.findElement(By.className(className)).getText() != null);
			}
		}, target, throwOnTimeout, syncTimeout);
	}

	*//**
	 * Wait for the div of the given name to contain the DONE string.
	 * @param pageFileName id of the element to look for
	 *//*
	public static void waitForDoneDiv(final String pageFileName) {
		waitForDoneDiv(pageFileName, null, new ImplicitAssertionError("Timeout loading " + pageFileName + " page."));
	}

	*//**
	 * Wait for the div of the given name to contain the DONE string.
	 * @param divID id of the element to look for
	 * @param throwOnTimeout exception to throw on timeout
	 *//*
	public static void waitForDoneDiv(final String divID, final AssertionError throwOnTimeout) {
		waitForDoneDiv(divID, null, throwOnTimeout);
	}

	*//**
	 * Wait for the div of the given jsp page to contain the DONE string.
	 *
	 * @param pageFileName the jsp file name (by convention, it is the id for the done div)
	 * @param target the window/frame to target
	 *
	 * @see #waitForDoneDiv(String, AutomationTarget, AssertionError)
	 *//*
	public static void waitForDoneDiv(final String pageFileName, final AutomationTarget target) {
		waitForDoneDiv(pageFileName, target, new ImplicitAssertionError("Timeout loading " + pageFileName + " page."));
	}

	*//**
	 * Wait for the div of the given name to contain the DONE string.
	 * @param divID id of the element to look for
	 * @param target the window/frame to target
	 * @param throwOnTimeout exception to throw on timeout
	 *//*
	public static void waitForDoneDiv(final String divID, final AutomationTarget target, final AssertionError throwOnTimeout) {
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				final WebDriver driver = AutomationSession.getWebDriver();
				//we don't use getText() because it's a hidden div, getText() will alaways return empty string for it
				try {
					final WebElement doneDiv = driver.findElement(By.id(divID));//in future, move driver.findElementByID() to a common method, to reduce maintainability.
					return DONE.equals(((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML;", doneDiv));
				} catch (final WebDriverException e) {
					//fluentWait("id", divID);
					//new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.id(divID)));//works in selenium 2.43 as well.
					final WebElement doneDiv = driver.findElement(By.id(divID));
					return DONE.equals(((JavascriptExecutor) driver).executeScript("return arguments[0].innerHTML;", doneDiv));
				}
			}
		}, target, throwOnTimeout, getSyncTimeout());
	}

	*//**
	 * Wait for the element identified by the provided ID to be present and have the given value.
	 * @param elementID id of the element to look for
	 * @param value value to assert on the element
	 * @param target the window/frame to target
	 * @param throwOnTimeout exception to throw on timeout
	 * @param syncTimeout milliseconds to wait
	 *//*
	public static void waitForElementByIDWithValue(final String elementID, final String value, final AutomationTarget target,
			final AssertionError throwOnTimeout, final long syncTimeout) {
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				final WebDriver driver = AutomationSession.getWebDriver();
				final WebElement targetElement = driver.findElement(By.id(elementID));
				boolean match = targetElement.getText().equals(value);
				if (!match) {
					// element was found, but the value is different; wait for 10 ms
					SynchronizationHelper.pause(SYNC_TICK);
					match = targetElement.getText().equals(value);
				}
				return match;
			}
		}, target, throwOnTimeout, syncTimeout);
	}

	*//**
	 * Wait for the element identified by the provided ID to be present and have the given value.
	 * @param elementID id of the element to look for
	 * @param value value to assert on the element
	 * @param target the window/frame to target
	 * @param throwOnTimeout exception to throw on timeout
	 * @param syncTimeout milliseconds to wait
	 *//*
	public static void waitForElementByIDWithValue(final String elementID, final String value, final AutomationTarget target,
			final AssertionError throwOnTimeout) {
		waitForElementByIDWithValue(elementID, value, target, throwOnTimeout, getSyncTimeout());
	}

	*//**
	 * Set the selected option in the drop-down options list based on the text matching of the options.
	 * @param dropDownElement WebElement of the drop-down/select
	 * @param optionText text of the option to match to select
	 * @param throwOnTimeout exception to throw on timeout
	 *//*
	public static void selectDropDownOptionByText(final Object dropDownElement, final String optionText, final AssertionError throwOnTimeout) {
		selectDropDownOptionByText(dropDownElement, optionText, throwOnTimeout, getSyncTimeout(), true);
	}

	*//**
	 * Set the selected option in the drop-down options list based on the text matching of the options.
	 * @param dropDownElement WebElement of the drop-down/select
	 * @param optionText text of the option to match to select
	 * @param throwOnTimeout exception to throw on timeout
	 * @param assertValueSelected do implicit assertion of the selected value (true/false)
	 *//*
	public static void selectDropDownOptionByText(final WebElement dropDownElement, final String optionText, final AssertionError throwOnTimeout,
			final boolean assertValueSelected) {
		selectDropDownOptionByText(dropDownElement, optionText, throwOnTimeout, getSyncTimeout(), assertValueSelected);
	}

	*//**
	 * Set the selected option in the drop-down options list based on the text matching of the options.
	 * @param dropDownElement WebElement of the drop-down/select
	 * @param optionText text of the option to match to select
	 * @param throwOnTimeout exception to throw on timeout
	 * @param assertValueSelected do implicit assertion of the selected value (true/false)
	 *//*
	public static void selectDropDownOptionByPartialText(final WebElement dropDownElement, final String optionText, final AssertionError throwOnTimeout,
			final boolean assertValueSelected) {
		selectDropDownOptionByPartialText(dropDownElement, optionText, throwOnTimeout, getSyncTimeout(), assertValueSelected);
	}

	*//**
	 * Set the selected option in the drop-down options list based on the text matching of the options.
	 * @param dropDownElement WebElement of the drop-down/select
	 * @param optionText text of the option to match to select
	 * @param throwOnTimeout exception to throw on timeout
	 * @param syncTimeout milliseconds to wait
	 * @param assertValueSelected do implicit assertion of the selected value (true/false)
	 *//*

	public static void selectDropDownOptionByText(final Object dropDownElement, final String optionText, final AssertionError throwOnTimeout,
			final long syncTimeout, final boolean assertValueSelected) {
		boolean textFound = false;
		if (optionText != null) {
			final List<WebElement> options = ((WebElement) dropDownElement).findElements(By.tagName(OPTION));
			try {
				for (final WebElement option : options) {
					if (optionText.equalsIgnoreCase(option.getText().trim())) {
						textFound = true;
						if (!AutomationSession.getSeleniumVersion().startsWith("2.1")) {
							final Select selectObj = new Select(((WebElement) dropDownElement));
							selectObj.selectByVisibleText(option.getText());
						} else if (!assertValueSelected) {
							option.click();
						} else {
							waitFor(new SyncCondition() {
								@Override
								public boolean testCondition() {
									option.click();
									if (option.isSelected()) {
										option.click();
										return true;
									}
									SynchronizationHelper.pause(SYNC_TICK);
									return false;
								}
							}, null, syncTimeout);
						}
						break;
					}
				}

				if (!textFound)
					logger.log(Level.WARNING, "\n *****WARNING****** Could not find drop down value with text " + optionText
							+ " in <select> <options /> </select> **********WARNING********* \n");
				//throw new ExplicitAssertionError("Could not find drop down value with text " + optionText + " in <select> <options /> </select>");
			} catch (final AutomationTimeoutError e) {
				logger.finest(FrameworkUtil.getStackTrace(e));
				throw throwOnTimeout;
			}
		}
	}

	public static void waitForElementByClassName(final String className, final ImplicitAssertionError implicitAssertionError) {
		waitForElementByClassName(className, null, implicitAssertionError, getSyncTimeout());
	}

	public static void tryMultipleTimes(final SyncCondition condition, final int maxCount) {
		boolean retValue = false;
		int i = 0;
		Throwable throwOnMaxCount = null;
		while (!retValue && i < maxCount) {
			i++;
			try {
				retValue = condition.testCondition();
			} catch (final RuntimeException e) {
				logger.finest(FrameworkUtil.getStackTrace(e));
				throwOnMaxCount = e;
			} catch (final AssertionError e1) {
				logger.finest(FrameworkUtil.getStackTrace(e1));
				throwOnMaxCount = e1;
			}
			if (!retValue)
				pause(SYNC_TICK);
		}
		if (!retValue) {
			if (throwOnMaxCount instanceof AssertionError) {
				throw (AssertionError) throwOnMaxCount;
			} else if (throwOnMaxCount instanceof RuntimeException) {
				throw (RuntimeException) throwOnMaxCount;
			} else {
				throw new RuntimeException("Unexpected Throwable type.", throwOnMaxCount);
			}
		}
	}

	public static void waitForElementByXPath(final String xpathExpression, final AutomationTarget target, final AssertionError thrownOnTimeOut,
			final long syncTimeOut) {
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				final WebDriver driver = AutomationSession.getWebDriver();
				try {
					return (driver.findElement(By.xpath(xpathExpression)) != null && driver.findElement(By.xpath(xpathExpression)).getText() != null);
				} catch (final WebDriverException e) {
					return findElementByXPath(xpathExpression) != null;
				}
			}
		}, target, thrownOnTimeOut, syncTimeOut);
	}

	public static void waitForElementByXPath(final String xpathExpression, final ImplicitAssertionError implicitAssertionError) {
		waitForElementByXPath(xpathExpression, null, implicitAssertionError, getSyncTimeout());
	}

	public static void waitUntilElementNotAvailableByXpath(final String xpathExpression, final ImplicitAssertionError implicitAssertionError) {
		waitUntilElementNotAvailableByXpath(xpathExpression, null, implicitAssertionError, getSyncTimeout());
	}

	public static void waitUntilElementNotAvailableByXpath(final String xpathExpression, final AutomationTarget target, final AssertionError thrownOnTimeOut,
			final long syncTimeOut) {
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				try {
					AutomationSession.getWebDriver().findElement(By.xpath(xpathExpression));
				} catch (final NoSuchElementException e) {
					return true;
				}
				return false;
			}
		}, target, thrownOnTimeOut, syncTimeOut);
	}

	*//**
	 * Set the selected option in the drop-down options list based on the text matching of the options, selects the partial text in the option value, DSL was written for dropdown which have option values changing dynamically.
	 * @param dropDownElement WebElement of the drop-down/select
	 * @param optionText text of the option to match to select
	 * @param throwOnTimeout exception to throw on timeout
	 * @param syncTimeout milliseconds to wait
	 * @param assertValueSelected do implicit assertion of the selected value (true/false)
	 *//*

	public static void selectDropDownOptionByPartialText(final Object dropDownElement, final String optionText, final AssertionError throwOnTimeout,
			final long syncTimeout, final boolean assertValueSelected) {
		boolean textFound = false;
		if (optionText != null) {
			final List<WebElement> options = ((WebElement) dropDownElement).findElements(By.tagName(OPTION));
			try {
				for (final WebElement option : options) {
					if (option.getText().contains(optionText)) {
						final String text = option.getText();
						textFound = true;
						if (!AutomationSession.getSeleniumVersion().startsWith("2.1")) {
							final Select selectObj = new Select(((WebElement) dropDownElement));
							selectObj.selectByVisibleText(text);
						} else if (!assertValueSelected) {
							option.click();
						} else {
							waitFor(new SyncCondition() {
								@Override
								public boolean testCondition() {
									option.click();
									if (option.isSelected()) {
										option.click();
										return true;
									}
									SynchronizationHelper.pause(SYNC_TICK);
									return false;
								}
							}, null, syncTimeout);
						}
						break;
					}
				}

				if (!textFound)
					logger.log(Level.WARNING, "\n *****WARNING****** Could not find drop down value with text " + optionText
							+ " in <select> <options /> </select> **********WARNING********* \n");
				//throw new ExplicitAssertionError("Could not find drop down value with text " + optionText + " in <select> <options /> </select>");
			} catch (final AutomationTimeoutError e) {
				logger.finest(FrameworkUtil.getStackTrace(e));
				throw throwOnTimeout;
			}
		}
	}

	public static void waitForJuicLoading() {
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				if (HtmlAutomationHelper.doesWebElementExistByXPath("//div[@class='sfLoadingContainer']")) {
					return !HtmlAutomationHelper.isElementVisibleByXPath("//div[text()='Loading...']")
							&& !HtmlAutomationHelper.doesWebElementExistByXPath("//div[@class='sfLoadingContainer']");
				}
				return !HtmlAutomationHelper.doesWebElementExistByXPath("//div[@class='sfLoadingContainer']");
			}
		}, new ImplicitAssertionError("JUIC Loading still in progress....!"));
	}

	public static void waitForJAMPageToLoad() {
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				return !HtmlAutomationHelper.doesWebElementExistByXPath("//*[contains(@class,'pjax-loading')]");
			}
		}, new ImplicitAssertionError("Jam Page is still loading.."));
	}

	*//**
	 * Wait for the element to be located. The DSL can be used in cases where document.readyState is complete, but the element is not yet loaded, which results in a NoSuchElementException.
	 * See http://toolsqa.com/selenium-webdriver/implicit-explicit-n-fluent-wait/
	 *//*
	private static WebElement findElementByID(final String id) {
		//return fluentWait("id", id);
		//new WebDriverWait(AutomationSession.getWebDriver(), 10).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
		final WebElement doneDiv = AutomationSession.getWebDriver().findElement(By.id(id));
		return doneDiv;
	}

	*//**
	 * Wait for the element to be located. The DSL can be used in cases where document.readyState is complete, but the element is not yet loaded, which results in a NoSuchElementException.
	 * See http://toolsqa.com/selenium-webdriver/implicit-explicit-n-fluent-wait/
	 *//*
	public static WebElement findElementByXPath(final String xpath) {
		return fluentWait("xpath", xpath);
		new WebDriverWait(AutomationSession.getWebDriver(), 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		 final WebElement doneDiv = AutomationSession.getWebDriver().findElement(By.xpath(xpath));
		return doneDiv;
	}

	*//**
	 * Hi, my name is Fluent wait. I will find a better way to wait for the element.
	 * My distant cousins, Implict Waits and Explicit waits also handle waits in other different ways.
	 * See http://stackoverflow.com/questions/28658418/differences-between-impilicit-explicit-and-fluentwait for differences between waits.
	 *//*
	private static WebElement fluentWait(final String locator, final String element) {
		// Waiting 30 seconds for an element to be present on the page, checking
		// for its presence once every 5 seconds.
		final WebDriver driver = AutomationSession.getWebDriver();
		final FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver).withTimeout(30, TimeUnit.SECONDS).pollingEvery(5, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class);
		logger.info("Fluent wait triggered for " + locator);
		WebElement myDynamicElement = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.presenceOfElementLocated(By.id(element)));
		final WebElement e1 = wait.until(new Function<WebDriver, WebElement>() {
			@Override
			public WebElement apply(final WebDriver driver1) {
				switch (locator) {
					case "id":
						return driver1.findElement(By.id(element));
					case "xpath":
						return driver1.findElement(By.xpath(element));
					default:
						return null;
				}
			}
		});
		return e1;
	}

	*//**
	 * Asserts dropdown value in the dropdown list
	 *//*
	public static void assertDropDownValues(final String elementId, final String selectedValue, final boolean isPresent) {
		SynchronizationHelper.waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				return HtmlAutomationHelper.doesElementExist(elementId) && HtmlAutomationHelper.isElementVisible(elementId);
			}
		}, new ImplicitAssertionError("Could not locate selected dropdown with id - " + elementId));
		final List<WebElement> list = AutomationSession.getWebDriver().findElement(By.id(elementId)).findElements(By.tagName("option"));
		boolean isOptionPresent = false;
		for (final WebElement e : list) {
			if (e.getText() != null && e.getText().trim().equals(selectedValue)) {
				isOptionPresent = true;
				break;
			}
		}
		if (isOptionPresent != isPresent) {
			throw new ImplicitAssertionError("Assert failed to locate input value " + selectedValue + " in  the page !");
		}
	}

	*//**
	 * Asserts Dropdown options identified by XPath
	 * @param xpath
	 * @param selectedValue
	 * @param isPresent
	 *//*
	public static void assertDropDownValuesByXPath(final String xpath, final String selectedValue, final boolean isPresent) {
		SynchronizationHelper.waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				return HtmlAutomationHelper.doesWebElementExistByXPath(xpath) && HtmlAutomationHelper.isElementVisibleByXPath(xpath);
			}
		}, new ImplicitAssertionError("Could not locate selected dropdown with XPath - " + xpath));
		final List<WebElement> list = AutomationSession.getWebDriver().findElements(By.xpath(xpath));
		boolean isOptionPresent = false;
		for (final WebElement e : list) {
			if (e.getText().equals(selectedValue)) {
				isOptionPresent = true;
				break;
			}
		}
		if (isOptionPresent != isPresent) {
			throw new ImplicitAssertionError("Assert failed to locate input value " + selectedValue + " in  the page !");
		}
	}

	*//**
	 * Asserts selected dropdown value in the dropdown list
	 *//*
	public static void assertSelectedDropDownValues(final String elementId, final String selectedValue, final boolean isPresent) {
		SynchronizationHelper.waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				return HtmlAutomationHelper.doesElementExist(elementId) && HtmlAutomationHelper.isElementVisible(elementId);
			}
		}, new ImplicitAssertionError("Could not locate selected dropdown with id - " + elementId));
		final List<WebElement> list = AutomationSession.getWebDriver().findElement(By.id(elementId)).findElements(By.tagName("option"));
		boolean isOptionPresent = false;
		for (final WebElement e : list) {
			if (e.isSelected() && e.getText() != null && e.getText().trim().equals(selectedValue)) {
				isOptionPresent = true;
				break;
			}
		}
		if (isOptionPresent != isPresent) {
			throw new ImplicitAssertionError("Assert failed to locate input value " + selectedValue + " in  the page !");
		}
	}

	*//**
	 * Asserts the Selected dropdown value identified by XPath
	 * @param xpath
	 * @param selectedValue
	 * @param isPresent
	 *//*
	public static void assertSelectedDropDownValuesByXPath(final String xpath, final String selectedValue, final boolean isPresent) {

		SynchronizationHelper.waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				return HtmlAutomationHelper.doesWebElementExistByXPath(xpath) && HtmlAutomationHelper.isElementVisibleByXPath(xpath);
			}
		}, new ImplicitAssertionError("Could not locate selected dropdown with xpath - " + xpath));
		final String selectedOption = new Select(AutomationSession.getWebDriver().findElement(By.xpath(xpath))).getFirstSelectedOption().getText();

		if (selectedOption.trim().equals(selectedValue) != isPresent) {
			throw new ImplicitAssertionError("Assert failed to locate input value " + selectedValue + " in  the page !");
		}
	}

	*//**
	 * This method searches for the given string in the page and throws an implicit assersion error if doesn't find
	 * @param textToFind
	 * @throws ImplicitAssertionError
	 *//*
	public static void assertTextInPageSource(final String textToFind) throws ImplicitAssertionError {
		assertTextInPageSource(textToFind, "");
	}

	*//**
	 * This method searches for the given string in the page and throws an implicit assersion error if doesn't find
	 *
	 * @param textToFind String to look for in the page source
	 * @param errorMessage Addition message you want to append to error message displayed to implicit assertion error
	 *//*
	public static void assertTextInPageSource(final String textToFind, final String errorMessage) throws ImplicitAssertionError {
		String pageSource = null;
		try {
			pageSource = AutomationSession.getWebDriver().getPageSource();

		} catch (final WebDriverException e) {
			final long timeoutThresholdValue = System.currentTimeMillis() + SynchronizationHelper.getSyncTimeout();
			boolean isError = true;
			while (isError && System.currentTimeMillis() < timeoutThresholdValue) {
				SynchronizationHelper.pause(2000);
				try {
					pageSource = AutomationSession.getWebDriver().getPageSource();
					isError = false;
				} catch (final WebDriverException ex) {
					isError = true;
				}
			}
		}
		if (pageSource == null || pageSource.indexOf(textToFind) < 0) {
			throw new ImplicitAssertionError("Expected message: '" + textToFind + "' could not be found. " + errorMessage);
		}
	}

	 Wait until an element becomes visible (WebElement.isDisplayed() == true).
	*
	* @param xpath xpath of element
	* @param target the window/frame to target
	* @param throwOnTimeout exception to throw on timeout
	* @param syncTimeout milliseconds to wait
	
	public static void waitUntilElementVisibleByXPath(final String xpath, final AutomationTarget target, final AssertionError throwOnTimeout) {
		waitUntilElementVisibleByXPath(xpath, target, throwOnTimeout, AutomationConfiguration.getSyncTimeout());
	}

	*//**
	 * Wait until an element gets Enabled (WebElement.isEnabled() == true).
	 *
	 * @param elementID id of element
	 * @param target the window/frame to target
	 * @param throwOnTimeout exception to throw on timeout
	 * @param syncTimeout milliseconds to wait
	 *//*
	public static void waitUntilElementEanbledByID(final String element, final AutomationTarget target, final AssertionError throwOnTimeout,
			final long syncTimeout) {
		waitForHTMLBasePageLoading();
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				final WebElement resultsDiv = AutomationSession.getWebDriver().findElement(By.id(element));
				SynchronizationHelper.waitForHTMLBasePageLoading(10);
				SynchronizationHelper.waitForJQueryBaseAjaxCalls(10);
				SynchronizationHelper.waitForElementByID(element, null, new ImplicitAssertionError("element not found"));
				SynchronizationHelper.waitUntilElementVisible(element);
				return (resultsDiv != null && resultsDiv.isEnabled());
			}
		}, target, throwOnTimeout, syncTimeout);
	}

	*//**
	 * Wait until an element becomes visible (WebElement.isDisplayed() == true).
	 *
	 * @param elementLink link of element
	 *//*
	public static void waitUntilElementVisibleByLink(final String elementLink) {
		waitUntilElementVisible(elementLink, null);
	}

	*//**
	 * The DSL waits for until the loading bar disaapears on user home page for home and my employess tab.
	 *//*
	public static void waitForDisabledElementsToLoad() {
		final long timeOutFactor = AutomationConfiguration.getSyncTimeout();
		final String loadingXpath = "//*[contains(@class, 'disabledWhileLoading')]";
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				try {
					return !HtmlAutomationHelper.doesElementExistByXpath(loadingXpath);
				} catch (final WebDriverException ex) {
					throw new ImplicitAssertionError(ex.getMessage());
				}

			}
		}, null, new ImplicitAssertionError("Element is not loaded!"), getSyncTimeout() * timeOutFactor);

	}

	*//**
	 * The DSL waits for until the loading indicator disaapears on typeAhead serach
	 *//*
	public static void waitForTypeAheadJuicLoading() {
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				return !HtmlAutomationHelper
						.doesWebElementExistByXPath("//img[contains(@class,'searchingIndicator') and contains(@style, 'display: inline;')]");
			}
		}, new ImplicitAssertionError("Search Card is Loading!"));
	}

	public static boolean waitUntilElementClickable(final Object webElementObj) {
		final WebElement webElement = (WebElement) webElementObj;
		try {
			final WebDriverWait wait = new WebDriverWait(AutomationSession.getWebDriver(), 1);
			//wait.until(ExpectedConditions.elementToBeClickable(webElement));
			return true;
		} catch (final Exception e) {
			return false;
		}
	}

	public static void waitForAdminOverlayPopupToClose() {
		waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				return !HtmlAutomationHelper.doesElementExistByClassName("jqmOverlay");
			}
		}, new ImplicitAssertionError(""));
	}

	public static void waitForElementNotVisibleByXPath(final String xpathExpression) {
		SynchronizationHelper.waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				try {
					return !HtmlAutomationHelper.isElementVisibleByXPath(xpathExpression);
				} catch (final WebDriverException ex) {
					throw new ImplicitAssertionError(ex.getMessage());
				}

			}
		}, null, new ImplicitAssertionError("Element is still visible"), getSyncTimeout());
	}

	public static void waitForWindowToOpen(final int numberOfWindows) {
		SynchronizationHelper.waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				try {
					return (HtmlAutomationHelper.getWindowHandles().size() == numberOfWindows);
				} catch (final WebDriverException ex) {
					throw new ImplicitAssertionError(ex.getMessage());
				}

			}
		}, null, new ImplicitAssertionError("Expected number of windows do not match"), getSyncTimeout());
	}

	public static void waitForElementDoesNotExistsByXPath(final String xpathExpression) {
			SynchronizationHelper.waitFor(new SyncCondition() {
				@Override
				public boolean testCondition() {
					try {
						return !HtmlAutomationHelper.doesElementExistByXpath(xpathExpression);
					} catch (final WebDriverException ex) {
						throw new ImplicitAssertionError(ex.getMessage());
					}

				}
			}, null, new ImplicitAssertionError("Element is still existing"), getSyncTimeout());
		}


	public static void waitForElementDoesNotExistsByXPath(final String xpathExpression, final long syncTimeOut) {
		SynchronizationHelper.waitFor(new SyncCondition() {
			@Override
			public boolean testCondition() {
				try {
					return !HtmlAutomationHelper.doesElementExistByXpath(xpathExpression);
				} catch (final WebDriverException ex) {
					throw new ImplicitAssertionError(ex.getMessage());
				}

			}
		}, null, new ImplicitAssertionError("Element is still existing"), syncTimeOut);
	}

}*/