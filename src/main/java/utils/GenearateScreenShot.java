package utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GenearateScreenShot {

    public static String getScreenShot(WebDriver driver) throws IOException {
        File currentDirFile = new File(".");
        String helper = currentDirFile.getAbsolutePath();
        helper=helper.replace(".","").replaceAll("\"\"","/");
        String dateName = new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date());
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        String destination = "\\reports\\screenshots\\" + 1 + dateName + ".png";
        File finalDestination = new File(destination);
        copyFile(source,finalDestination);
        return destination;
    }
    public static void copyFile(File source,File dest) throws IOException {
            FileInputStream fis = new FileInputStream(source);
            FileOutputStream fos = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }

    }
}
