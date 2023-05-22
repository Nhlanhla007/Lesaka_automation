package hico_1;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class create_user {
	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    public create_user(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
    }
    //my element
    @FindBy(xpath = "//*[@id=\"firstNames\"]")
    WebElement Firstname;
    @FindBy(xpath = "//*[@id=\"surname\"]")
    WebElement Surname;
    @FindBy(xpath = "//select[@id=\"title\"]")
    WebElement select_title;
    @FindBy(xpath = "//*[@id=\"salary\"]")
    WebElement salary;
    @FindBy(xpath = "//*[@id=\"formHorizontalRadiosGender1\"]")
    WebElement male;
    @FindBy(xpath = "//*[@id=\"formHorizontalRadiosGender2\"]")
    WebElement female;
    @FindBy(xpath = "//*[@id=\"formHorizontalRadiosGender3\"]")
    WebElement unspecified;
    @FindBy(xpath = "//*[@id=\"employeeNumber\"]")
    WebElement emplNum;
    @FindBy(xpath = "//button[contains(text(),\"Add Employee\")]")
    WebElement btnAddEmp;
    @FindBy(xpath = "//button[contains(text(),\"Remove Employee\")]")
    WebElement btnRemEmp;
    @FindBy(xpath = "//button[contains(text(),\"Save\")]")
    WebElement btnSave;
    @FindBy(xpath = "//button[contains(text(),\"Cancel\")]")
    WebElement btnCancel;
    @FindBy(xpath = "//*[@id=\"formHorizontalRadiosProfile1\"]")
    WebElement RbtnGreen;
    @FindBy(xpath = "//*[@id=\"formHorizontalRadiosProfile2\"]")
    WebElement RbtnBlue;
    @FindBy(xpath = "//*[@id=\"formHorizontalRadiosProfile3\"]")
    WebElement RbtnRed;
    @FindBy(xpath = "//*[@id=\"formHorizontalRadiosProfile4\"]")
    WebElement RbtnDefault;
    @FindBy(xpath = "//*[contains(text(),\"Susan\")]")
    WebElement lastuser;
    @FindBy(xpath = "//*[contains(text(),\"John\")]")
    WebElement firstuser;
    @FindBy(xpath = "//*[@id=\"firstNamesFieldBlock\"]")
    WebElement val_First;
    @FindBy(xpath = "//*[@id=\"surnameFieldBlock\"]")
    WebElement val_surname;
    @FindBy(xpath = "//*[@id=\"titleFieldBlock\"]")
    WebElement val_title;
    @FindBy(xpath = "//*[@id=\"genderFieldBlock\"]")
    WebElement val_gender;
    @FindBy(xpath = "//*[@id=\"employeeNumberFieldBlock\"]")
    WebElement val_EmploNum;
    @FindBy(xpath = "//*[@id=\"employeeNumberFieldBlockVal\"]")
    WebElement val_EmploNumUni;
    
  //*[@class="table table-striped table-bordered table-hover"]//tbody//tr[contains(text(),"")]
    //mvn compile exec:java -Dexec.mainClass="Run" test -Dsurefire.suiteXmlFiles=testng.xml
    
    //add new employee
    public void add_employee(ExtentTest test) throws Exception {
		String url = "https://sensedev.highcoordination.de:8080/hicotest/index.html";
		String firstName= "Kenny";
		String lastName= "Smith";
		String salutaion= "Mr";
		String gender= "Male";
		int employeeNum= 2;
		String employeeSal= "943984";
		String ProfileColour= "Blue";
		
		action.navigateToURL(url);
		action.explicitWait(1000, test);
		
		if(action.isEnabled(btnAddEmp)) {
			action.click(btnAddEmp, "Click Add Employee", test);
			action.writeText(Firstname, firstName, "write name", test);
			action.writeText(Surname, lastName, "write lastname", test);
			action.writeText(select_title, salutaion, "write salutation", test);
			//action.selectFromDropDownUsingVisibleText(select_title, salutaion, "");
			switch (gender) {
			case "Male":
				action.click(male, "Male", test);
				break;
			case "Female":
				action.click(female, "Female", test);
				break;
			default:
				action.click(unspecified, "unspecified", test);
				break;
			}
			action.scrollElemetnToCenterOfView(emplNum, "scroll", test);
			action.writeText(emplNum, Integer.toString(employeeNum) , "write employee number", test);
			salary.clear();
			action.writeText(salary, employeeSal, "write salary employee", test);
			
			switch (ProfileColour) {
			case "Green":
				action.click(RbtnGreen, "Green", test);
				break;
			case "Blue":
				action.click(RbtnBlue, "Blue", test);
				break;
			case "Red":
				action.click(RbtnRed, "Red", test);
				break;
			default:
				action.click(RbtnDefault, "Default", test);
				break;
			}
			action.click(btnSave, "Click Save", test);
			action.explicitWait(5000, test);
			
		}else {
			throw new Exception("Unable to add new user");
		}
		
	}
    
    //remove employee
   
    public void remove_employee(ExtentTest test) throws Exception,IOException, InterruptedException {
    	String url = "https://sensedev.highcoordination.de:8080/hicotest/index.html";
    	action.navigateToURL(url);
    	action.explicitWait(1000, test);
    	
    	action.click(lastuser, "last user", test);
    	action.click(btnRemEmp, "Remove Employee", test);
    	action.explicitWait(5000, test);
    	
    }
    
    //editing employee
    public void editing_employee(ExtentTest test) throws Exception,IOException, InterruptedException {
    	String url = "https://sensedev.highcoordination.de:8080/hicotest/index.html";
    	String editInfo= "FirstName";
    	String salutaion= "Dr";
    	action.navigateToURL(url);
    	action.explicitWait(1000, test);
    	
    	if(action.isElementPresent(firstuser)) {
    	action.click(firstuser, "last user", test);
    	switch (editInfo) {
			case "FirstName":
				action.writeText(Firstname, Firstname.getText()+"low", "write name", test);
				action.click(btnSave, "Click Save", test);
				action.explicitWait(5000, test);
				break;
			case "Surname":
				action.writeText(Surname, Surname.getText()+"high", "write lastname", test);
				action.click(btnSave, "Click Save", test);
				action.explicitWait(5000, test);
				break;
			case "Salutaion":
				action.selectFromDropDownUsingVisibleText(select_title, salutaion, "");
				action.click(btnSave, "Click Save", test);
				action.explicitWait(5000, test);
				break;
			default:
				action.click(btnSave, "Click Save", test);
				break;
    	}
    	}else {
			throw new Exception("Unable to edit the user");
		}
		
    }
    
    //validation
    public void validations(ExtentTest test) throws IOException, InterruptedException {
    	String url = "https://sensedev.highcoordination.de:8080/hicotest/index.html";
    	int employeeNum= 9;
    	action.navigateToURL(url);
    	action.explicitWait(1000, test);
    	
    	try {
    		if(action.isDisplayed(val_First)) {
        		action.CompareResult("if Firstname is a mandotary field", "First Names field is required", val_First.getText(), test);
        		action.CompareResult("if Surname is a mandotary field", "Surname field is required", val_surname.getText(), test);
        		action.CompareResult("if Gender is a mandotary field", "Gender field is required", val_gender.getText(), test);
        		action.CompareResult("if Salutaion is a mandotary field", "Salutation field is required", val_title.getText(), test);
        		action.CompareResult("if Employee number is a mandotary field", "Employee Number field is required", val_EmploNum.getText(), test);
        		
        		action.scrollElemetnToCenterOfView(emplNum, "scroll", test);
        		action.writeText(emplNum, Integer.toString(employeeNum) , "write employee number", test);
        		action.CompareResult("if Employee number is unique", "Employee Number must be unique", val_EmploNumUni.getText(), test);
        		
        	}	
			
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
    	
    }
}
