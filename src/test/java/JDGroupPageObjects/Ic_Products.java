package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
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
	 
	 @FindBy(xpath = "//span[contains(text(),'Next')]")
	 public WebElement ic_ClickNext;
	 
	 
	 @FindBy(xpath = "//span[contains(text(),\"Computers, Notebooks & Tablet's\")]")
	 WebElement computersNoteBooks;
	 
	 @FindBy(xpath = "//body[1]/div[1]/header[1]/div[2]/div[1]/div[2]/div[3]/nav[1]/ul[1]/li[1]/ul[1]/li[1]/ul[1]/li[7]/a[1]/span[1]")
	 WebElement fitness;
	 
	 @FindBy(xpath = "//body[1]/div[1]/header[1]/div[2]/div[1]/div[2]/div[3]/nav[1]/ul[1]/li[1]/ul[1]/li[1]/ul[1]/li[9]/a[1]/span[1]")
	 WebElement software;
	 
	 @FindBy(xpath = "//body[1]/div[1]/header[1]/div[2]/div[1]/div[2]/div[3]/nav[1]/ul[1]/li[1]/ul[1]/li[1]/ul[1]/li[15]/a[1]/span[1]")
	 WebElement downloads;
	 
	 List<WebElement> listElements;
	 
	 /*
	  * PAGE METHODS
	  */
	 
	 public void clickNext(ExtentTest test) {
		 action.mouseover(ic_ClickNext, "scroll to element");	 
		 try {
			 Thread.sleep(10000);
			action.click(ic_ClickNext, "Clicked Next", test);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }
	 
	 /**
	  * Returns a list of products from current viewing page
	  * @return List<WebElement>
	  */
	 public List<WebElement> returnList(){
		 return ic_products;
	 }
	 
	 /**
	  * Validates if the product click NEXT page button exists/is visible
	  * @return WebElement
	  */
	 public WebElement returnNext() {
		 boolean status = action.attributeEnabled(ic_ClickNext.findElement(By.xpath(".//parent::*")));
		 if(status) {
			 return ic_ClickNext.findElement(By.xpath(".//parent::*"));
		 }
		 return null;
	 }
	 
	 /**
	  * Clicks On product to view all products
	  * @param productToFind
	  * @param quantityOfProducts
	  * @param test
	  */
	 public void ic_ClickProductLink(String productToFind,String quantityOfProducts, ExtentTest test) {
		 try {
			System.out.println("ENTERS CLICK ON PRODUCT LINK");
			 if(ic_ElementVisable(icProductLink)) {
			 action.click(icProductLink, "Click product link",test);
			 Thread.sleep(10000);
			 List<WebElement> products =ic_SelectProduct(test,productToFind,quantityOfProducts);			 
			 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 //To validate page has been loaded
		 //action.validateUrl("product", "Validate product URL");
	 }
	 
	 //enters text into search bar
		
	 /**
	  * Enters search data into the search bar and searches
	  * @param productToFind
	  * @param quantityOfProducts
	  * @param test
	  */
		public void ic_EnterTextToSearchBar(String productToFind,String quantityOfProducts,ExtentTest test) {
			try {
				ic_ElementVisable(icSearchBar);
				action.clear(icSearchBar,"SearchBar");
				action.writeText(icSearchBar, productToFind,"SearchBar",test);
				action.click(icSearchIcon, "Click on search", test);
				Thread.sleep(10000);
				List<WebElement> products = ic_SelectProduct(test,productToFind,quantityOfProducts);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	 
	 //Checks if an element is visible
		/**
		 * Checks for the visability of an element
		 * @param element
		 * @return boolean
		 */
	 public boolean ic_ElementVisable(WebElement element) {
		 return action.elementExists(element, 10);
	 }
	 
	 /**
	  * Filters through excel data, removes delimeters.
	  * @param allProductsToSearch
	  * @return List<String>
	  */
		  public List<String> filterProducts(String allProductsToSearch){
			  String[] productsArray= allProductsToSearch.split("#");
			  List<String> productsList = new ArrayList<String>(Arrays.asList(productsArray));
			  return productsList;
		  }
		 
		  /**
		   * Gathers data from excel.
		   * Determines search type inserted from excel and selects appropriate construct for execution
		   * @param input
		   * @param test
		   * @param rowNumber
		   */
	 public void searchType(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) {
		 String typeSearch = input.get("typeSearch").get(rowNumber);
		 String productsToSearch = input.get("specificProduct").get(rowNumber);
		 String quantityOfSearchProducts = input.get("Quantity").get(rowNumber);
		 
		 switch (typeSearch) {
		case "SearchUsingSearchBar":
			ic_EnterTextToSearchBar(productsToSearch,quantityOfSearchProducts,test);
			break;
		case"All Products":
			System.out.println("ENTERS THE SWITCH");
			ic_ClickProductLink(productsToSearch,quantityOfSearchProducts,test);
			break;		
		default:
			if(typeSearch.equalsIgnoreCase("Computers Notebooks & Tablet's")) {
				action.mouseover(icProductLink, "MouseOverICProduct");
				computersNoteBooks.click();
				ic_SelectProduct(test, productsToSearch, quantityOfSearchProducts);
			}
			else if(typeSearch.equalsIgnoreCase("Downloads & Top Ups")) {
				action.mouseover(icProductLink, "MouseOverICProduct");
				downloads.click();
				ic_SelectProduct(test, productsToSearch, quantityOfSearchProducts);
			}else if(typeSearch.equalsIgnoreCase("Software")) {
				action.mouseover(icProductLink, "MouseOverICProduct");
				software.click();
				ic_SelectProduct(test, productsToSearch, quantityOfSearchProducts);
			}else if(typeSearch.equalsIgnoreCase("Fitness & Wearables")) {
				action.mouseover(icProductLink, "MouseOverICProduct");
				fitness.click();
				ic_SelectProduct(test, productsToSearch, quantityOfSearchProducts);
			}else {
				System.out.println("ENTERS else for any other product");
				System.out.println(typeSearch);
				WebElement category = byCategory(typeSearch);
				action.mouseover(icProductLink, "MouseOverICProduct");
				category.click();
				ic_SelectProduct(test, productsToSearch, quantityOfSearchProducts);
			}
			break;
		}
	 }
	 
	 /**
	  * Finds the category that will be searched
	  * @param nameOfCategory
	  * @return WebElement
	  * @author Leverch Watson
	  */
	 public WebElement byCategory(String nameOfCategory) {
		 WebElement category = driver.findElement(By.xpath("//span[contains(text(),\""+nameOfCategory+"\")]"));
		 return category;
	 }
	 

	 /**
	  * Adds product to the cart according to the quantity provided by excel
	  * @param products
	  * @param quantity
	  */
	 public void addToCart(List<WebElement> products,List<String> quantity) {	
		 for(int i = 0; i < quantity.size();i++) { //quantity maximum elements
				 int sv = 0;
				 for(WebElement ele : products) {
					 
					 if(i == sv) {					 
						 System.out.println(ele.getText());
							for(int s = 0;s< Integer.parseInt(quantity.get(i)); s++) {
								System.out.println(ele.getText());
						 	WebElement clicls = ele.findElement(By.xpath(".//parent::*/following-sibling::div/div[3]/div/div[1]/form"));
						 	clicls.click();
						 	//ADD VALIDATION FOR THE ADD,ADDING,ADDED
						 	//POP UP THAT COMES UP SAYING PRODUCTS ARE ADDED
						 	//STORE PRICE,NAME,QUANTITY IN SOME DATA STRUCTURE -- CAN CREATE FROM products 
						 	//CREATE METHOD THAT TALLY UP THE TOTALS AND COMPARES THE NAMES IN THE CART OF THE PRODUCTS
						 	try {
								Thread.sleep(8000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						 	}
					 }
					 sv++;
				 }
				
			 
		 }
		 

	 }

	 /**
	  * Finds the specified products from the listing product page.
	  * Adds the product found to the cart 
	  * @param test
	  * @param productsList
	  * @param quantityOfProducts
	  * @return  List<WebElement>
	  * @author Leverch Watson
	  */
	 public List<WebElement> ic_SelectProduct(ExtentTest test,String productsList,String quantityOfProducts) {
		 	List<String> theProducts = filterProducts(productsList);
		 	List<WebElement> finalResultsFromSearch = new ArrayList<>();
		 	
		 	List<WebElement> productsFromSearch = new ArrayList<>();
		 	List<String> quantity = filterProducts(quantityOfProducts);
		 	
		 	boolean flag = true;
			outerloop:
			while(flag) {
				productsFromSearch.clear();
			List<WebElement> allProducts = returnList();
			for(WebElement el: allProducts) {
				System.out.println("LOOPS ALL THE PRODUCTS FROM LISTING PAGE " + el.getText() );
				productsFromSearch.clear();
			for(Iterator<String> iterator = theProducts.iterator(); iterator.hasNext(); ) {
				String value = iterator.next();
				System.out.println("Loops all the products to be search for "+ value);
				System.out.println(el.getText());
				if(el.getText().trim().equalsIgnoreCase(value.trim())) {
					System.out.println("FOUND THE PRODUCT " + value);
					productsFromSearch.add(el);
					finalResultsFromSearch.add(el);
					System.out.println("ADDED SUCCESSFULLY TO LIST");
					iterator.remove();
					System.out.println("REMOVED SUCCESSFULLY FROM LIST");

					if(!iterator.hasNext()) {
						addToCart(productsFromSearch,quantity);
						break outerloop;
					}
		}
				
				}
			if(!productsFromSearch.isEmpty()) {
				addToCart(productsFromSearch,quantity);		
				for(int s = 0;s<productsFromSearch.size();s++) {
				quantity.remove(0);
				}
				}
			}
			WebElement nextButton = returnNext();
			if(nextButton != null) {
				clickNext(test);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				flag = false;
				System.out.println("Item has not been found anywhere");
			}
			
		 	}
		 	return finalResultsFromSearch;
		}
}
