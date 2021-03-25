package JDGroupPageObjects;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class IC_CreditAppAddressDetails {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	
	public IC_CreditAppAddressDetails(WebDriver driver,DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;	
		}
	
	@FindBy(id = "creditApplicationForm__residental_street")
	WebElement steetAddress;
	
	@FindBy(id = "creditApplicationForm__residental_building_complex")
	WebElement buildingDetails;
	
	@FindBy(id = "creditApplicationForm__residental_province")
	WebElement province;
	
	@FindBy(id = "creditApplicationForm__residental_city")
	WebElement city;
	
	@FindBy(id = "creditApplicationForm__residental_suburb")
	WebElement suburb;
	
	@FindBy(id = "creditApplicationForm__residental_postal_code")
	WebElement postalCode;
	
	@FindBy(id = "creditApplicationForm__residental_country")
	WebElement county;
	
	@FindBy(id = "creditApplicationForm__preferred_store")
	WebElement preferredStore;
	
	@FindBy(xpath = "//*[@id=\"creditApplicationForm.address__fieldset\"]/div/div[9]/div[1]/button[1]/span")
	WebElement nextButton;
	
	public void enterDetails(String location,String buiding,String locProvince,String locCity,String locSuburb,String postal,String locCountry,String store,ExtentTest test) throws Exception {
		action.writeText(steetAddress, location, "Street Address", test);
		if(buiding != null) {
		action.writeText(buildingDetails, buiding, "Buidling Details", test);
		}
		action.selectFromDropDownUsingVisibleText(province, locProvince, "Select Province");
		action.writeText(city, locCity, "City", test);
		action.writeText(suburb, locSuburb, "Suburb", test);
		action.writeText(postalCode, postal, "Postal Code", test);
		action.selectFromDropDownUsingVisibleText(county, locCountry, "Select Country");
		action.selectFromDropDownUsingVisibleText(preferredStore, store, "Select store");
		
	}
	
	
	public void dataInput(DataTable2 dataTable, ExtentTest test) throws Exception {
		String streetAddress = dataTable.getValueOnCurrentModule("streetAddress");
		String buidingDetails= dataTable.getValueOnCurrentModule("buidingDetails");
		String province= dataTable.getValueOnCurrentModule("province");
		String city= dataTable.getValueOnCurrentModule("city");
		String suburb= dataTable.getValueOnCurrentModule("suburb");
		String postalCode= dataTable.getValueOnCurrentModule("postalCode");
		String county= dataTable.getValueOnCurrentModule("county");
		String preferredStore= dataTable.getValueOnCurrentModule("preferredStore");
		
		enterDetails(streetAddress, buidingDetails, province, city, suburb, postalCode, county, preferredStore, test);
		action.click(nextButton, "Click next Address button", test);
	}
}
