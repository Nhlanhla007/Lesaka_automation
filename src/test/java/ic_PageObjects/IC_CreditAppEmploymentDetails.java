package ic_PageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class IC_CreditAppEmploymentDetails {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	
	public IC_CreditAppEmploymentDetails(WebDriver driver,DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	}
	
	@FindBy(id = "creditApplicationForm__employment_type")
	WebElement employmentType;
	
	@FindBy(id = "creditApplicationForm__employment_years")
	WebElement yearsOfEmployment;

	@FindBy(id = "creditApplicationForm__employment_months")
	WebElement monthsOfEmployment;
	
	@FindBy(id = "creditApplicationForm__gross_salary")
	WebElement grossSalaryPerMonth;
	
	@FindBy(id = "creditApplicationForm__field_of_employment")
	WebElement fieldOfEmployment;
	
	@FindBy(xpath = "//*[@id=\"creditApplicationForm.employmentDetails__fieldset\"]/div/div[6]/div[1]/button[1]/span")
	WebElement nextButton;
	
	public void enterDetails(String typeOfEmployment,String employmentYears,String EmploymentInMonths,String SalaryPerMonthgross,String employmentField,ExtentTest test) throws Exception {
		action.selectFromDropDownUsingVisibleText(employmentType, typeOfEmployment, "Employment Type");
		action.writeText(yearsOfEmployment, employmentYears, "Employement in years", test);
		action.writeText(monthsOfEmployment, EmploymentInMonths, "Employment in months", test);
		action.writeText(grossSalaryPerMonth, SalaryPerMonthgross, "Gross salary per month", test);
		action.selectFromDropDownUsingVisibleText(fieldOfEmployment, employmentField, "Employment Field");
	}
	
	public void dataInput(DataTable2 dataTable2,ExtentTest test) throws Exception {
		String employmentType = dataTable2.getValueOnCurrentModule("employmentType");
		String yearsOfEmployment =dataTable2.getValueOnCurrentModule("yearsOfEmployment");
		String monthsOfEmployment =dataTable2.getValueOnCurrentModule("monthsOfEmployment");
		String grossSalaryPerMonth =dataTable2.getValueOnCurrentModule("grossSalaryPerMonth");
		String fieldOfEmployment =dataTable2.getValueOnCurrentModule("fieldOfEmployment");
		enterDetails(employmentType, yearsOfEmployment, monthsOfEmployment, grossSalaryPerMonth, fieldOfEmployment, test);
		action.click(nextButton, "Employment details next button", test);
	}
	
	
}

