package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SeleniumHelpers {
    private String screenshotsPath;
    private String fileSeparator;

    public void SetupScreenshotsFolder() {
        fileSeparator = System.getProperty("file.separator");
        screenshotsPath = System.getProperty("user.dir") + fileSeparator + "screenshots";

        File file = new File(screenshotsPath); // Set screenshot folder
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public String logWebScreenShotToFile(WebDriver driver) throws IOException {

        String targetLocation;
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String screenShotName = Thread.currentThread().getName() + " - " + timeStamp + ".png";
        File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        targetLocation = screenshotsPath + fileSeparator + screenShotName;// define location

        File targetFile = new File(targetLocation);
        FileHandler.copy(screenshotFile, targetFile);
        return targetFile.getAbsolutePath();
    }
    public By DetermineWebElementType(String webElementType, String elementId) {
        By loc = null;
        switch (webElementType.toUpperCase()) {
            case "ID":
                loc = By.id(elementId);
                break;
            case "NAME":
                loc = By.name(elementId);
                break;
            case "XPATH":
                loc = By.xpath(elementId);
                break;
            case "CSSSELECTOR":
                loc = By.cssSelector(elementId);
                break;
            case "CLASSNAME":
                loc = By.className(elementId);
                break;
            case "LINKTEXT":
                loc = By.linkText(elementId);
                break;
        }
        return loc;
    }
}
