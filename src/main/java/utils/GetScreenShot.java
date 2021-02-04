package utils;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

/**
 * <copyright file="GetScreenShot.java" company="Modi's Consulting Group"> Reproduction or
 * transmission in whole or in part, in any form or by any means, electronic,
 * mechanical or otherwise, is prohibited without the prior written permission
 * of the copyright owner. </copyright>
 * 
 * @author 
 */

public class GetScreenShot {

	
	/**
	 * capture() method takes the screenshots using TakeScreenShot method of selenium and stores the screenshot in png format under
	 * screenshots folder.
	 * @param driver
	 * @param screenShotName Failed Test case Name is passed
	 * @return dest screen shot path
	 */

	
	public static String capture(WebDriver driver,String screenShotName) throws IOException
    {
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String dest = System.getProperty("user.dir") +"\\reports\\extentreports\\screenshots\\"+screenShotName+".png";
        File destination = new File(dest);
        FileUtils.copyFile(source, destination);        
 
        return dest;
    }
	
}
