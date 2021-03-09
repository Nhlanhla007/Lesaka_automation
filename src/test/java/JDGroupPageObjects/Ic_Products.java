package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;

import Logger.Log;
import utils.Action;

public class Ic_Products {

	WebDriver driver;
	Action action;
	IC_Cart cartValidation;

	public Ic_Products(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		cartValidation = new IC_Cart(driver);
	}

	static Logger logger = Log.getLogData(Action.class.getSimpleName());

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
			logger.info(e.getMessage());
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
		boolean clickN = action.attributeEnabled(ic_ClickNext);
		if(clickN) {
			WebElement web = ic_ClickNext.findElement(By.xpath(".//parent::*"));
			boolean status = action.attributeValidation(web,"aria-disabled","false",5);
			if(status) {
				return ic_ClickNext.findElement(By.xpath(".//parent::*"));
			}
		}
		return null;
	}

	/**
	 * Clicks On product to view all products
	 * @param productToFind
	 * @param quantityOfProducts
	 * @param test
	 */
	public void ic_ClickProductLink(ExtentTest test) {
		try {
			if(ic_ElementVisable(icProductLink)) {
				action.click(icProductLink, "Click product link",test);
				Thread.sleep(10000);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}


	/**
	 * Enters search data into the search bar and searches
	 * @param productToFind
	 * @param quantityOfProducts
	 * @param test
	 */
	public void ic_EnterTextToSearchBar(String productToFind,ExtentTest test) {
		try {
			ic_ElementVisable(icSearchBar);
			action.clear(icSearchBar,"SearchBar");
			action.writeText(icSearchBar, productToFind,"SearchBar",test);
			action.click(icSearchIcon, "Click on search", test);
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
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
	public void ic_SelectProductAndAddToCart(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) {
		String typeSearch = input.get("typeSearch").get(rowNumber);
		String productsToSearch = input.get("specificProduct").get(rowNumber);
		String quantityOfSearchProducts = input.get("Quantity").get(rowNumber);
		String waitTimeInSeconds = input.get("cartButtonWaitTimeInSeconds").get(rowNumber);
		List<String> theProducts = filterProducts(productsToSearch);

		try {
			Map<String, List<String>> productsInCart =  ic_CreateCartFromProductListing(productsToSearch, quantityOfSearchProducts,typeSearch,waitTimeInSeconds, test);
			cartValidation.iCcartVerification2(productsInCart, test);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * Loads the listing page
	 * @param category
	 * @param product
	 * @param test
	 * @throws IOException
	 */
	public void loadProductListingPage(String category,String product,ExtentTest test) throws IOException {
		switch (category) {
			case "SearchUsingSearchBar":
				ic_EnterTextToSearchBar(product,test);
				break;
			case"All Products":
				ic_ClickProductLink(test);
				break;
			default:
				if(category.equalsIgnoreCase("Computers Notebooks & Tablet's")) {
					action.mouseover(icProductLink, "MouseOverICProduct");
					action.click(computersNoteBooks, "Computers Notebooks & Tablet's", test);

				}
				else if(category.equalsIgnoreCase("Downloads & Top Ups")) {
					action.mouseover(icProductLink, "MouseOverICProduct");
					action.click(downloads, "Downloads & Top Ups", test);

				}else if(category.equalsIgnoreCase("Software")) {
					action.mouseover(icProductLink, "MouseOverICProduct");
					action.click(software, "Software", test);

				}else if(category.equalsIgnoreCase("Fitness & Wearables")) {
					action.mouseover(icProductLink, "MouseOverICProduct");
					action.click(fitness, "Fitness & Wearables", test);

				}else {
					WebElement typeOfSearch = byCategory(category);
					action.mouseover(icProductLink, "MouseOverICProduct");
					action.click(typeOfSearch, typeOfSearch.getText(), test);

				}

				break;

		}

	}

	/**
	 * Finds the category that will be searched
	 * @param nameOfCategory
	 * @return WebElement
	 */
	public WebElement byCategory(String nameOfCategory) {
		WebElement category = driver.findElement(By.xpath("//span[contains(text(),\""+nameOfCategory+"\")]"));
		return category;
	}

	WebElement ic_FindProduct(WebElement elem) {
		return elem;
	}

	WebElement getCartButton(WebElement product) {
		WebElement cartButton = product.findElement(By.xpath(".//parent::*/following-sibling::div/div[3]/div/div[1]/form"));
		return cartButton;
	}

	void addToCart(WebElement addToCartButton,String waitTimeInSeconds,ExtentTest test) {
		try {
			action.mouseover(addToCartButton, "Scroll to add to cart");
			Thread.sleep(2000);
			addToCartButton.click();
			cartValidation.cartButtonValidation(addToCartButton, Integer.parseInt(waitTimeInSeconds), test);
			Thread.sleep(7000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	String findPrice(WebElement theProduct) {
		String price = theProduct.findElement(By.xpath(".//parent::*/following-sibling:: div/div[2]/div/span/span/span")).getText();
		return price;
	}


	/**
	 * Finds the specified products from the listing product page.
	 * Adds the product found to the cart
	 * @param test
	 * @param productsList
	 * @param quantityOfProducts
	 * @return  List<WebElement>
	 * @throws IOException
	 */
	public WebElement ic_FindProduct(ExtentTest test,String product) throws IOException {
		boolean status= true;
		while(status) {
			List<WebElement> allProducts = ic_products;
			for(WebElement el: allProducts) {
				if(el.getText().trim().toLowerCase().equalsIgnoreCase(product)) {
					status = false;
					return el;
				}
			}
			WebElement nextButton = returnNext();
			if(nextButton != null) {
				clickNext(test);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					logger.info(e.getMessage());
				}
			}else {
				status = false;
				action.CompareResult("Product Not Found",product, "", test);
				//System.out.println("Item has not been found anywhere");
			}
		}
		return null;
	}

	Map<String,List<String>> ic_CreateCartFromProductListing(String productsList,String quantityOfProducts,String searchCategory,String waitTimeInSeconds,ExtentTest test) {
		Map<String,List<String>> productData = new LinkedHashMap<>();
		try {
			List<String> theProducts = filterProducts(productsList);
			List<String> quantity = filterProducts(quantityOfProducts);
			for(int s = 0 ; s<theProducts.size();s++) {
				List<String> productPriceAndQuantity = new ArrayList<>();
				loadProductListingPage(searchCategory, theProducts.get(s),test);//change signatures
				WebElement prod =  ic_FindProduct(test,theProducts.get(s));
				String productName = prod.getText();
				String productPrice = findPrice(prod);
				productPriceAndQuantity.add(productPrice);
				int set = 0;
				if(prod!=null) {
					for(int o =0;o<quantity.size();o++) {
						if(o==s) {
							for(int g = 0;g<Integer.parseInt(quantity.get(o));g++) {
								WebElement cartButton = getCartButton(prod);
								addToCart(cartButton,waitTimeInSeconds,test);
								if(set<=0) {
									set++;
									productPriceAndQuantity.add(quantity.get(o));
								}
							}
						}
					}
				}
				productData.put(productName, productPriceAndQuantity);
			}
		} catch (Exception e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return productData;

	}
}
