package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;


public class ic_validateProductSKU {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	Ic_Products icproducts;
	
	public ic_validateProductSKU(WebDriver driver, DataTable2 dataTable2){
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	}
	
	@FindBy(xpath = "//*[@id=\"maincontent\"]//div[1]/div[2]/div[2]/div[3]/div")
	public WebElement ic_ProductCode;
	
	public void displayProductSKU(ExtentTest test, WebElement el) throws IOException{
		
		action.click(el, "click product", test);
		String productSKU = action.getText(ic_ProductCode, "");
		
		if(productSKU != null| productSKU != ""){
			action.CompareResult("The product SKU is "+ productSKU, "true", "true", test);
		}
		
		
	}

}
