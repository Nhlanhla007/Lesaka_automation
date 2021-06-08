package base;


import org.apache.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import Logger.Log;



/**
 * <copyright file="TestCaseBase.java" company="Modi's Consulting Group"> Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic,
 * mechanical or otherwise, is prohibited without the prior written permission
 * of the copyright owner. </copyright>
 * 
 * @author 
 */


public class TestCaseBase {

	static WebDriver driver = null;
	private static final String DRIVERPATH = System.getProperty("user.dir")+"//drivers";

	static Logger logger = Log.getLogData(TestCaseBase.class.getSimpleName());



	/**
	 * setDriver() method is used to initialize the respective drivers based on the browser type
	 * @param browser
	 * @return WebDriver
	 */


	private static WebDriver setDriver(String browser) throws Exception {
		ChromeOptions chromeOptions=null;
		FirefoxOptions firefoxOptions=null;
		InternetExplorerOptions ieOptions=null;

		switch (browser.toUpperCase()) {
		case "CHROME":
			chromeOptions =getChromeOptions();
			driver = new ChromeDriver(chromeOptions);
			break;
		case "FIREFOX":
			firefoxOptions=getFirefoxOptions();
			driver = new FirefoxDriver(firefoxOptions);
			break;
		case "IE":
			ieOptions = getIEOptions();
			driver = new InternetExplorerDriver(ieOptions);
			break;
		default:
			break;
		}
		return driver;
	}


	/**
	 * setDriver() method is set the driver executable path for each browser type
	 * @param browser
	 */
	private static void setPropertyByOS(String browser) throws Exception {
		switch (getOS()) {
		case "OS_WINDOWS":
			switch (browser.toUpperCase()) {
			case "CHROME":
				System.out.println("path"+ DRIVERPATH+"//chromedriver.exe");
				System.setProperty("webdriver.chrome.driver", DRIVERPATH+"//chromedriver.exe");
				break;
			case "FIREFOX":
				System.setProperty("webdriver.gecko.driver", DRIVERPATH+"//geckodriver.exe");
				break;
			case "IE":
				System.setProperty("webdriver.ie.driver", DRIVERPATH+"//IEDriverServer.exe");
				break;
			default:
				break;
			}
			break;
		case "OS_MAC":
			switch (browser.toUpperCase()) {
			case "CHROME":
				System.setProperty("webdriver.chrome.bin", System.getProperty("browser_bin_path"));
				System.setProperty("webdriver.chrome.driver", System.getProperty("browser_driver_path"));
				break;
			case "FIREFOX":
				System.setProperty("webdriver.firefox.bin", System.getProperty("browser_bin_path"));
				System.setProperty("webdriver.gecko.driver", System.getProperty("browser_driver_path"));
				break;
			default:
				break;
			}
			break;
		default:
			throw new Exception("Unknown OS, Please check the Operating System Paramter");
		}
	}

	
	/**
	 * getOS() method returns the OS type
	 * @return OS type
	 */
	public static String getOS() {
		String osType = null;
		String osName = System.getProperty("os.name");
		String osNameMatch = osName.toLowerCase();
		if (osNameMatch.contains("linux")) {
			osType = "OS_LINUX";
		} else if (osNameMatch.contains("windows")) {
			osType = "OS_WINDOWS";
		} else if (osNameMatch.contains("solaris") || osNameMatch.contains("sunos")) {
			osType = "OS_SOLARIS";
		} else if (osNameMatch.contains("mac os") || osNameMatch.contains("macos") || osNameMatch.contains("darwin")) {
			osType = "OS_MAC";
		} else {
			osType = "Unknown";
		}
		return osType;
	}



	/**
	 * getChromeOptions() method sets the desired capabilities for Chrome browser 
	 * @return ChromeOptions
	 */
	private static ChromeOptions getChromeOptions() throws Exception
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		options.addArguments("disable-infobars");
//		options.addArguments("--disable-gpu");
		options.addArguments("--disable-dev-shm-usage");
		//new
		/*	options.addArguments("enable-automation"); 
		options.addArguments("--disable-browser-side-navigation");
		options.addArguments("--disable-gpu");
		options.addArguments("--disable-dev-shm-usage");
		 options.addArguments("--dns-prefetch-disable"); */
		options.addArguments("--disable-features=VizDisplayCompositor");
		//	options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		options.setHeadless(true);
		options.merge(capabilities);
		setPropertyByOS("chrome");
		return options;
	}
	
	/**
	 * getIEOptions() method sets the desired capabilities for Internet Explorer browser 
	 * @return InternetExplorerOptions
	 */
	
	private static InternetExplorerOptions getIEOptions() throws Exception
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
		capabilities.setCapability(InternetExplorerDriver.UNEXPECTED_ALERT_BEHAVIOR, UnexpectedAlertBehaviour.ACCEPT);
		capabilities.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
		InternetExplorerOptions options= new InternetExplorerOptions();
		options.setPageLoadStrategy(PageLoadStrategy.EAGER);
	//	options.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		options.merge(capabilities);
		setPropertyByOS("IE");
		return options;
	}

	/**
	 * getFirefoxOptions() method sets the desired capabilities for firefox browser 
	 * @return FirefoxOptions
	 */
	
	private static FirefoxOptions getFirefoxOptions() throws Exception
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		FirefoxOptions options= new FirefoxOptions();
		options.merge(capabilities);
		setPropertyByOS("firefox");
		return options;
	}

	/**
	 * initializeTestBaseSetup() method accepts parameters from testng.xml file
	 * @return WebDriver
	 */
	
	public static WebDriver initializeTestBaseSetup(String browserType
			) {
		logger.info("In Initialize Base Setup");
		try {
			return setDriver(browserType);
		} catch (Exception e) {
			logger.error("There is a exception is driver initialization");
			e.printStackTrace();
		}
		return null;
	}
}
