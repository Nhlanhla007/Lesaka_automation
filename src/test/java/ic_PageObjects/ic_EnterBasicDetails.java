package ic_PageObjects;

import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import utils.Action;
import utils.DataTable2;

public class ic_EnterBasicDetails {

	 WebDriver driver;
	    Action action;
	    DataTable2 dataTable2;
                             
	    public ic_EnterBasicDetails(WebDriver driver, DataTable2 dataTable2) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
	        this.dataTable2= dataTable2;

	    }
	    
	    @FindBy(xpath="//*[@id=\"js-footer-accordion\"]/div[1]/div/div/div[2]/ul/li[7]/a")
	    private WebElement ic_adminApplyForCredit;
	    
	    //Are you subject to an Administration Order?
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.basicDetails__fieldset\"]/div/div[1]/span")
	    private WebElement ic_adminFirstQuest;
	    
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.basicDetails__fieldset\"]/div/div[1]/ol/div[1]/label/span/span")
	    private WebElement ic_adminOrderYES;
	    
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.basicDetails__fieldset\"]/div/div[1]/ol/div[2]/label/span/span")
	    private WebElement ic_adminOrderNO;
	    
	    //Have you applied to be under Administration?
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.basicDetails__fieldset\"]/div/div[2]/ol/div[1]/label/span/span")
	    private WebElement ic_adminAppliedYES;
	    
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.basicDetails__fieldset\"]/div/div[2]/ol/div[2]/label/span/span")
	    private WebElement ic_adminAppliedNO;
	    
	    //Are you under Sequestration?
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.basicDetails__fieldset\"]/div/div[3]/ol/div[1]/label/span/span")
	    private WebElement ic_SequestratedYES;
	    
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.basicDetails__fieldset\"]/div/div[3]/ol/div[2]/label/span/span")
	    private WebElement ic_SequestratedNO;
	    
	    //Have you applied to be Sequestrated?
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.basicDetails__fieldset\"]/div/div[4]/ol/div[1]/label/span/span")
	    private WebElement ic_SequestratedAppliedYES;
	    
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.basicDetails__fieldset\"]/div/div[4]/ol/div[2]/label/span/span")
	    private WebElement ic_SequestratedAppliedNO;
	    
	    //Are you under Debt Review?
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.basicDetails__fieldset\"]/div/div[5]/ol/div[1]/label/span/span")
	    private WebElement ic_DebtReviewYES;
	    
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.basicDetails__fieldset\"]/div/div[5]/ol/div[2]/label/span/span")
	    private WebElement ic_DebtReviewNO;
	    
	    //Have you applied to be placed under Debt Review?
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.basicDetails__fieldset\"]/div/div[6]/ol/div[1]/label/span/span")
	    private WebElement ic_underDebtReviewYES;
	    
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.basicDetails__fieldset\"]/div/div[6]/ol/div[2]/label/span/span")
	    private WebElement ic_underDebtReviewNO;
	    
	    @FindBy(xpath="//select[@ name=\"title\"]")
	    private WebElement ic_basicTitle;
	    
	    @FindBy(xpath="//input[@name=\"surname\"]")
	    private WebElement ic_basicSurname;
	    
	    @FindBy(xpath="//input[@name=\"first_names\"]")
	    private WebElement ic_basicFirstName;
	    
	    @FindBy(xpath="//input[@name=\"initials\"]")
	    private WebElement ic_basicInitials;
	    
	    @FindBy(xpath="//select[@name=\"ethnicity\"]")
	    private WebElement ic_basicEthnicity;
	    
	    @FindBy(xpath="//select[@name=\"martial_status\"]")
	    private WebElement ic_basicMartialStatus;
	    
	    @FindBy(xpath="//select[@ name=\"how_married\"]")
	    private WebElement ic_basicHowMarried;
	    
	    //Have you obtained your spouse consent?
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.basicDetails__fieldset\"]/div/div[14]/ol/div[1]/label/span/span")
	    private WebElement ic_basicSpouseConsentYES;
	    
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.basicDetails__fieldset\"]/div/div[14]/ol/div[2]/label/span/span")
	    private WebElement ic_basicSpouseConsentNO;
	    
	    @FindBy(xpath="//select[@name=\"citizenship\"]")
	    private WebElement ic_basicCitizenship;
	    
	    @FindBy(xpath="//input[@name=\"id_number\"]")
	    private WebElement ic_basicIDnumber;
	    
	    @FindBy(xpath="//input[@name=\"date_of_birth\"]")
	    private WebElement ic_basicDoB;
	    
	    @FindBy(xpath="//select[@name=\"gender\"]")
	    private WebElement ic_basicGender;
	    
	    @FindBy(xpath="//select[@name=\"dependants\"]")
	    private WebElement ic_basicDependants;
	    
	    @FindBy(xpath="//*[@id=\"creditApplicationForm.basicDetails__fieldset\"]/div/div[20]/div[1]/button[1]")
	    private WebElement ic_basicNext;
	    
	    public void enterBasicInfor(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws Exception{
	    	
	    	String AreYouSubjectToAnAdministrationOrder = dataTable2.getValueOnCurrentModule("AreYouSubjectToAnAdministrationOrder?");
	    	String HaveYouAppliedToBeUnderAdministration = dataTable2.getValueOnCurrentModule("HaveYouAppliedToBeUnderAdministration?");
	    	String AreYouUnderSequestration = dataTable2.getValueOnCurrentModule("AreYouUnderSequestration?");
	    	String HaveYouAppliedToBeSequestrated = dataTable2.getValueOnCurrentModule("HaveYouAppliedToBeSequestrated?");
	    	String AreYouUnderDebtReview = dataTable2.getValueOnCurrentModule("AreYouUnderDebtReview?");
	    	String HaveYouAppliedToBePlacedUnderDebtReview= dataTable2.getValueOnCurrentModule("HaveYouAppliedToBePlacedUnderDebtReview?");
	    	
	    	String Title = dataTable2.getValueOnCurrentModule("Title");
	    	String Surname = dataTable2.getValueOnCurrentModule("Surname");
	    	String FirstName = dataTable2.getValueOnCurrentModule("FirstName");
	    	String Initials = dataTable2.getValueOnCurrentModule("Initials");
	    	String Ethnicity = dataTable2.getValueOnCurrentModule("Ethnicity");
	    	String MartialStatus = dataTable2.getValueOnCurrentModule("MartialStatus");
	    	String HowMarried = dataTable2.getValueOnCurrentModule("HowMarried");
	    	String SpouseConsent = dataTable2.getValueOnCurrentModule("SpouseConsent");
	    	
	    	String Citizenship = dataTable2.getValueOnCurrentModule("Citizenship");
	    	String IDnumber = dataTable2.getValueOnCurrentModule("IDnumber");
	    	String DateOfBirth = dataTable2.getValueOnCurrentModule("DateOfBirth");
	    	String Gender = dataTable2.getValueOnCurrentModule("Gender");
	    	String Dependants = dataTable2.getValueOnCurrentModule("Dependants");
	    	
	    	action.scrollToElement(ic_adminApplyForCredit, "Go to apply credit");
	    	
	    	action.click(ic_adminApplyForCredit, "click Apply Credit", test);
	    	
	    	action.mouseover(ic_adminOrderYES, "scroll");
	    	if(AreYouSubjectToAnAdministrationOrder.equalsIgnoreCase("Yes")){
	    		action.click(ic_adminOrderYES, "yes", test);
	    		
	    	} else if(AreYouSubjectToAnAdministrationOrder.equalsIgnoreCase("No")){
	    		action.click(ic_adminOrderNO, "No", test);
	    	} else{
	    		throw new Exception("Please select radio button");
	    	}
	    	
	    	action.mouseover(ic_adminAppliedYES,"Scroll");
	    	if(HaveYouAppliedToBeUnderAdministration.equalsIgnoreCase("Yes")){
	    		action.click(ic_adminAppliedYES, "yes", test);
	    		
	    	} else if(HaveYouAppliedToBeUnderAdministration.equalsIgnoreCase("No")){
	    		action.click(ic_adminAppliedNO, "No", test);
	    	} else{
	    		throw new Exception("Please select radio button");
	    	}
	    	
	    	action.mouseover(ic_SequestratedYES,"Scroll");
	    	if(AreYouUnderSequestration.equalsIgnoreCase("Yes")){
	    		action.click(ic_SequestratedYES, "yes", test);
	    		
	    	} else if(AreYouUnderSequestration.equalsIgnoreCase("No")){
	    		action.click(ic_SequestratedNO, "No", test);
	    	} else{
	    		throw new Exception("Please select radio button");
	    	}
	    	
	    	action.mouseover(ic_SequestratedAppliedYES,"Scroll");
	    	if(HaveYouAppliedToBeSequestrated.equalsIgnoreCase("Yes")){
	    		action.click(ic_SequestratedAppliedYES, "yes", test);
	    		
	    	} else if(HaveYouAppliedToBeSequestrated.equalsIgnoreCase("No")){
	    		action.click(ic_SequestratedAppliedNO, "No", test);
	    	} else{
	    		throw new Exception("Please select radio button");
	    	}
	    	
	    	action.mouseover(ic_DebtReviewYES,"Scroll");
	    	if(AreYouUnderDebtReview.equalsIgnoreCase("Yes")){
	    		action.click(ic_DebtReviewYES, "yes", test);
	    		
	    	} else if(AreYouUnderDebtReview.equalsIgnoreCase("No")){
	    		action.click(ic_DebtReviewNO, "No", test);
	    	} else{
	    		throw new Exception("Please select radio button");
	    	}
	    	
	    	action.mouseover(ic_underDebtReviewYES,"Scroll");
	    	if(HaveYouAppliedToBePlacedUnderDebtReview.equalsIgnoreCase("Yes")){
	    		action.click(ic_underDebtReviewYES, "yes", test);
	    		
	    	} else if(HaveYouAppliedToBePlacedUnderDebtReview.equalsIgnoreCase("No")){
	    		action.click(ic_underDebtReviewNO, "No", test);
	    	} else{
	    		throw new Exception("Please select radio button");
	    	}
	    	
	    	action.mouseover(ic_basicTitle, "scroll to title");
	    	action.writeText(ic_basicTitle,Title , " Title", test);
	    	action.writeText(ic_basicSurname, Surname, "Surname", test);
	    	action.writeText(ic_basicFirstName, FirstName,"First name" , test);
	    	action.writeText(ic_basicEthnicity,Ethnicity , "Ethnicity", test);
	    	action.writeText(ic_basicMartialStatus, MartialStatus, "Martial status", test);
	    	if(MartialStatus.equalsIgnoreCase("Married/Civil Partnership")){
	    		
	    		action.writeText(ic_basicHowMarried, HowMarried, "How married", test);
	    		
	    		if(SpouseConsent.equalsIgnoreCase("Yes")){
	    			action.click(ic_basicSpouseConsentYES, "Yes", test);
	    		}
	    		if(SpouseConsent.equalsIgnoreCase("No")){
	    			action.click(ic_basicSpouseConsentNO, "No", test);
	    		}
	    	}
	    	
	    	action.writeText(ic_basicCitizenship, Citizenship, "Citizenship", test);
	    	action.writeText(ic_basicIDnumber, IDnumber, "ID number", test);
	    	//action.writeText(ic_basicDoB, DateOfBirth, "Date of Birth", test);
	    	action.writeText(ic_basicGender, Gender, "Gender", test);
	    	action.writeText(ic_basicDependants, Dependants, "Dependants", test);
	    	
	    	action.javaScriptClick(ic_basicNext, "Next", test);
	    	
	    	
	    	
	    }
	
}
