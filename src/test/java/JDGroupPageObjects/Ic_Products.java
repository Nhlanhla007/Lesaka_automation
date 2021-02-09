package JDGroupPageObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;

public class Ic_Products {

	WebDriver driver;
	Action action;

	 public Ic_Products(WebDriver driver) {
	this.driver = driver;
	PageFactory.initElements(driver, this);
	action = new Action(driver);
	 }
	 
	 /*
	  * PAGE OBJECTS
	  */
	 
	
	 @FindBy(xpath = "//span[contains(text(),'Products')]")
	 WebElement icProductLink;
	 
	 @FindBy(xpath = "//input[@id='search']")
	 WebElement icSearchBar;
	 
	 @FindBy(xpath = "//header/div[2]/div[1]/div[2]/div[1]/form[1]/div[3]/button[1]")
	 WebElement icSearchIcon;
	 
	 @FindBy(css = "a.product-item-link")
	 public List<WebElement> ic_products;
	 
	 @FindBy(xpath = "//a[@title='Next']")
	 public WebElement ic_ClickNext;
	 
	 
	 @FindBy(xpath = "//span[contains(text(),'Computers, Notebooks & Tablets')]")
	 WebElement computersNoteBooks;
	 
	 @FindBy(xpath = "//span[contains(text(),'Computer Accessories')]")
	 WebElement compAccess;
	 
	 @FindBy(xpath = "//span[contains(text(),'Cellphones & Accessories')]")
	 WebElement cellPhones;
	 
	 @FindBy(xpath = "//span[contains(text(),'Gaming, Gadgets & Wearables')]")
	 WebElement gaming;
	 
	 @FindBy(xpath = "//span[contains(text(),'Printing, Scanners & Ink')]")
	 WebElement printing;
	 
	 @FindBy(xpath = "//span[contains(text(),'TVs, Displays & Audio')]")
	 WebElement tv;
	 
	 @FindBy(xpath = "//body[1]/div[1]/header[1]/div[2]/div[1]/div[2]/div[3]/nav[1]/ul[1]/li[1]/ul[1]/li[1]/ul[1]/li[7]/a[1]/span[1]")
	 WebElement fitness;
	 
	 @FindBy(xpath = "//span[contains(text(),'Office Solutions')]")
	 WebElement officeSo;
	 
	 @FindBy(xpath = "//body[1]/div[1]/header[1]/div[2]/div[1]/div[2]/div[3]/nav[1]/ul[1]/li[1]/ul[1]/li[1]/ul[1]/li[9]/a[1]/span[1]")
	 WebElement software;
	 
	 @FindBy(xpath = "//span[contains(text(),'Networking')]")
	 WebElement networking;
	 
	 @FindBy(xpath = "//span[contains(text(),'Photography')]")
	 WebElement photo;
	 
	 @FindBy(xpath = "//span[contains(text(),'Home Security')]")
	 WebElement homeSecurity;
	 
	 @FindBy(xpath = "//span[contains(text(),'Office Appliances')]")
	 WebElement officeAp;
	 
	 @FindBy(xpath = "//span[contains(text(),'Gift Voucher')]")
	 WebElement gift;
	 
	 @FindBy(xpath = "//body[1]/div[1]/header[1]/div[2]/div[1]/div[2]/div[3]/nav[1]/ul[1]/li[1]/ul[1]/li[1]/ul[1]/li[15]/a[1]/span[1]")
	 WebElement downloads;
	 
	 List<WebElement> listElements;
	 
	 /*
	  * PAGE METHODS
	  */
	 
	 //select category from drop down next to product list 
	 public void ic_CategoryFind(String category) {
		 action.mouseover(icProductLink, "MouseOverICProduct");
		 listElements = new ArrayList<WebElement>();
		 listElements.add(computersNoteBooks);
		 listElements.add(compAccess);
		 listElements.add(cellPhones);
		 listElements.add(gaming);
		 listElements.add(printing);
		 listElements.add(tv);
		 listElements.add(fitness);
		 listElements.add(officeSo);
		 listElements.add(software);
		 listElements.add(networking);
		 listElements.add(photo);
		 listElements.add(homeSecurity);
		 listElements.add(officeAp);
		 listElements.add(gift);
		 listElements.add(downloads);
		 
		 for(WebElement el : listElements) {
			 if(el.getText().contains(category)) {
				//action.click(el, "Click category link");
			 }else {
				 
			 }
		 }
		 
	 }
	 
	 public void clickNext() {
		 //action.scrollToElement(ic_ClickNext, "Next");
		 //ic_ElementVisable(elementToClick);
		// action.click(ic_ClickNext, "Clicked Next");
	 }
	 
	 public List<WebElement> returnList(){
		 return ic_products;
	 }
	 
	 public WebElement returnNext() {
		 return ic_ClickNext;
	 }
	 
	 //Click product link from bar
	 public void ic_ClickProductLink(ExtentTest test) {
		 System.out.println("ENTERS CLICK ON PRODUCT LINK");
		 if(ic_ElementVisable(icProductLink)) {
		 action.click(icProductLink, "Click product link",test);
		 }
		 //To validate page has been loaded
		 //action.validateUrl("product", "Validate product URL");
	 }
	 
	 //enters text into search bar
		/*
		 * public void ic_EnterTextToSearchBar(String text) {
		 * ic_ElementVisable(icSearchBar); action.writeText(icSearchBar, text,
		 * "Search for a product"); ic_ClickIcon(icSearchIcon); }
		 */
	 
	 //clicks on search(magnifying glass icon)
		/*
		 * public void ic_ClickIcon(WebElement elementToClick) {
		 * ic_ElementVisable(elementToClick); action.click(elementToClick,
		 * "Click Icon"); }
		 */
	 //Checks if an element is visible
	 public boolean ic_ElementVisable(WebElement element) {
		 return action.elementExists(element, 10);
	 }
	 
		/*
		 * public boolean ic_ElementEnabled(WebElement elementAttr) { return
		 * action.isEnabled(elementAttr); }
		 */
	 
	 //Which search should be run
	 public void searchType(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) {
		 switch (input.get("typeSearch").get(rowNumber)) {
		case "searchbar":
			//ic_EnterTextToSearchBar("");
			break;
		case"general":
			System.out.println("ENTERS THE SWITCH");
			ic_ClickProductLink(test);
			break;
		case "category":
			ic_CategoryFind("");
		break;
			
		default:
			break;
		}
	 }
	 
		
	 
	 
	 
	 
}
