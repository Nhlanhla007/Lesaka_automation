package emailverification;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.Action;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ICGiftCardVerification {
    WebDriver driver;
    Action action;

    @FindBy(xpath="//*[@id=\"identifierId\"]")
    private WebElement userNameText;

    @FindBy(xpath="//*[@id=\"identifierNext\"]/div/button/div[2]']")
    private WebElement nextButton;

    @FindBy(xpath="//input[@class='whsOnd zHQkBf']")
    private WebElement userPassword;

    @FindBy(xpath="//*[@id=\"passwordNext\"]/div/button/div[2]")
    private WebElement userPasswordNext;

    @FindBy(xpath="//*[@id=\":7a\"]/div[1]")
    private WebElement singleEmailText;

    public ICGiftCardVerification (WebDriver driver){
        this.driver = driver;
        action = new Action(driver);
    }
    public void icGiftCardVerificationSender (HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws IOException, ParseException {
        action.navigateToURL("https://mail.google.com/");
        action.explicitWait(10000);
        List<WebElement> email = driver.findElements(By.xpath("//*[@id=\":2d\"]/tbody/tr"));
        int numberOfEmailsBefore=Integer.parseInt(input.get("emailCountbeforeGiftCardBuy").get(rowNumber));
        int waitTimeForBarcodeEmailInSec=Integer.parseInt(input.get("waitTimeForBarcodeEmailInSec").get(rowNumber));
        int numberOfEmailsAfter=email.size();
        Date date1 =new Date();
        long curTime1=date1.getTime();
        long difference = 0;
        boolean found=false;
        while((difference<=waitTimeForBarcodeEmailInSec)||found) {
            if(numberOfEmailsAfter>=(numberOfEmailsBefore+3)) {
                for (WebElement emailsub : email) {
                    if (emailsub.getText().indexOf("You've been sent a gift from Incredible Connection") != -1) {
                        emailsub.click();
                        action.explicitWait(5000);
                        String emailText = action.getText(singleEmailText,"emailText");
                        int barcodeIndex = emailText.indexOf("Barcode for");
                        int serialNumberIndex = emailText.indexOf("Serial Number");
                        int scratchCodeIndex = emailText.indexOf("Scratch Code");
                        System.out.println(emailText.substring(barcodeIndex, barcodeIndex + 37));
                        input.get("barcode").set(rowNumber,emailText.substring(barcodeIndex, barcodeIndex + 37));
                        System.out.println(emailText.substring(serialNumberIndex, serialNumberIndex + 30));
                        input.get("serialNumber").set(rowNumber,emailText.substring(serialNumberIndex, serialNumberIndex + 30));
                        System.out.println(emailText.substring(scratchCodeIndex, scratchCodeIndex + 19));
                        input.get("scratchCode").set(rowNumber,emailText.substring(scratchCodeIndex, scratchCodeIndex + 19));
                        found=true;
                        break;
//
                    }
                }
            }
            action.refresh();
            action.explicitWait(30000);
            Date date2 =new Date();;
            long curTime2=date2.getTime();
            difference=(curTime2-curTime1)/1000;
            System.out.println("Time waiting for email(sec): "+difference);
            System.out.println("Number of emails: "+numberOfEmailsAfter);
            email = driver.findElements(By.xpath("//*[@id=\":2d\"]/tbody/tr"));
            numberOfEmailsAfter=email.size();
        }
        action.CompareResult("Finding Email","true",String.valueOf(found),test);
    }
    public void navigateToGmail(String userName,String password,ExtentTest test) throws IOException {
        action.navigateToURL("https://mail.google.com/");
        driver.findElement(By.id("identifierId")).sendKeys(userName);
        driver.findElement(By.xpath("//*[@id=\"identifierNext\"]/div/button/div[2]")).click();
        action.explicitWait(5000);
        driver.findElement(By.xpath("//input[@class='whsOnd zHQkBf']")).sendKeys(password);
        driver.findElement(By.xpath("//*[@id=\"passwordNext\"]/div/button/div[2]")).click();
        action.explicitWait(10000);
//        action.writeText(userNameText,userName,"userName",test);
//        action.click(nextButton,"nextButton",test);
//        action.explicitWait(5000);
//        action.writeText(userPassword,password,"password",test);
//        action.click(nextButton,"nextButton",test);
//        action.explicitWait(10000);
    }
    public void getnumberOfEmails(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber) throws IOException {
        navigateToGmail(input.get("userName").get(rowNumber),input.get("password").get(rowNumber),test);
        List<WebElement> email = driver.findElements(By.xpath("//*[@id=\":2d\"]/tbody/tr"));
        input.get("emailCountbeforeGiftCardBuy").set(rowNumber,String.valueOf(email.size()));
    }
}
