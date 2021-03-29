package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import io.qameta.allure.Step;
import utils.Action;
import utils.DataTable2;

public class ic_SpouseDetails {
	
	 WebDriver driver;
	    Action action;
	    DataTable2 dataTable2;
                                
	    public ic_SpouseDetails(WebDriver driver, DataTable2 dataTable2) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        action = new Action(driver);
	        this.dataTable2= dataTable2;

	    }
	    
	    @FindBy(xpath="//select[@ name=\"spouse_title\"]")
	    private WebElement ic_SpouseTitle;
	    
	    @FindBy(xpath="//input[@ name=\"spouse_surname\"]")
	    private WebElement ic_SpouseLastName;
	
	    @FindBy(xpath="//input[@ name=\"spouse_first_name\"]")
	    private WebElement ic_SpouseFirstName;
	    
	    @FindBy(xpath="//input[@ name=\"spouse_initials\"]")
	    private WebElement ic_SpouseInitials;
	    
	    @FindBy(xpath="//input[@ name=\"spouse_cell_number\"]")
	    private WebElement ic_SpouseCellNumber;
	
	    @FindBy(xpath="//select[@ name=\"spouse_citizenship\"]")
	    private WebElement ic_SpouseCitizenship;
	    
	    @FindBy(xpath="//input[@ name=\"spouse_id_number\"]")
	    private WebElement ic_SpouseIdNumber;
	    
	    @FindBy(xpath="//input[@ name=\"spouse_date_of_birth\"]")
	    private WebElement ic_SpouseDateOfBirth;
	    
	    @FindBy(xpath="//select[@ name=\"spouse_gender\"]")
	    private WebElement ic_SpouseGender;
	    
	    @FindBy(xpath="//input[@ name=\"spouse_gross_salary\"]")
	    private WebElement ic_SpouseGrossSalary;
	    
	    @FindBy(xpath="//body[1]/div[1]/main[1]//form[1]/fieldset[2]//div[1]/button/span[1]")
	    private WebElement ic_SpouseNext;
	    
	    @Step("Account Information")
	    public void enterSpouseDetails(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
	    	String spouseTitle = dataTable2.getValueOnCurrentModule("spouseTitle");
	    	String spouseSurname = dataTable2.getValueOnCurrentModule("spouseSurname");
	    	String spouseFirstName = dataTable2.getValueOnCurrentModule("spouseFirstName");
	    	String spouseInitials = dataTable2.getValueOnCurrentModule("spouseInitials");
	    	String spouseCellNumber = dataTable2.getValueOnCurrentModule("spouseCellNumber");
	    	String spouseCitizenship = dataTable2.getValueOnCurrentModule("spouseCitizenship");
	    	String spouseIdNumber = dataTable2.getValueOnCurrentModule("spouseIdNumber");
	    	String spouseDateOfBirth = dataTable2.getValueOnCurrentModule("spouseDateOfBirth");
	    	String spouseGender = dataTable2.getValueOnCurrentModule("spouseGender");
	    	String spouseGrossSalary = dataTable2.getValueOnCurrentModule("spouseGrossSalary");
	    	
	    	action.writeText(ic_SpouseTitle,spouseTitle , "Spouse title", test);
	    	action.writeText(ic_SpouseLastName,spouseSurname , "Spouse Last name", test);
	    	action.writeText(ic_SpouseFirstName,spouseFirstName , "Spouse First name", test);
	    	//action.writeText(ic_SpouseInitials,spouseInitials , "Spouse Initials", test);
	    	action.writeText(ic_SpouseCellNumber,spouseCellNumber , "Spouse cell number", test);
	    	action.writeText(ic_SpouseCitizenship,spouseCitizenship , "Spouse citizenship", test);
	    	action.writeText(ic_SpouseIdNumber,spouseIdNumber, "Spouse Id number", test);
	    	action.writeText(ic_SpouseDateOfBirth,spouseDateOfBirth , "Spouse date of birth", test);
	    	action.writeText(ic_SpouseGender,spouseGender , "Spouse gender", test);
	    	action.writeText(ic_SpouseGrossSalary,spouseGrossSalary , "Spouse Grossary salary", test);
	    	
	    	action.click(ic_SpouseNext, "Next", test);
	    	
	    }
	    
	    public int findRowToRun(HashMap<String, ArrayList<String>> input,int occCount,int testcaseID){
			int numberRows=input.get("TCID").size();
			int rowNumber=-1;
			occCount=occCount+1;
			for(int i=0;i<numberRows;i++){
				if(input.get("TCID").get(i).equals(Integer.toString(testcaseID))&&input.get("occurence").get(i).equals(Integer.toString(occCount))){
					rowNumber=i;
				}
			}
			return rowNumber;
		}
	
}
