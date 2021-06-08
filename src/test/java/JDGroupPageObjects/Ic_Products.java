package JDGroupPageObjects;

import java.awt.event.InputEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import Logger.Log;
import utils.Action;
import utils.ConfigFileReader;
import utils.DataTable2;

public class Ic_Products {

	WebDriver driver;
	Action action;
	IC_Cart cartValidation;
	DataTable2 dataTable2;
	ic_WishList WishList;
	ic_validateProductSKU validateProductSKU;
	ic_CompareProducts compareProducts;
	public Ic_Products(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		cartValidation = new IC_Cart(driver, dataTable2);
		this.dataTable2 = dataTable2;
		WishList = new ic_WishList(driver, dataTable2);
		validateProductSKU = new ic_validateProductSKU(driver, dataTable2);
		compareProducts = new ic_CompareProducts(driver, dataTable2);
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

	@FindBy(xpath = "//*[@class=\"box-tocart\"]/div/span")
	WebElement productOutOfStock;

	List<WebElement> listElements;

	@FindBy(id = "product-addtocart-button")
	WebElement productDetailsPageAddToCartButton;
	@FindBy(xpath = "//a[@class='action towishlist']//span[contains(text(),'Add to Wish List')]")
	WebElement productDetailsPageAddToWishlistButton;

	/*
	 * PAGE METHODS
	 */

	public void clickNext(ExtentTest test) throws Exception {
		action.mouseover(ic_ClickNext, "scroll to element");
			action.explicitWait(10000);
			action.click(ic_ClickNext, "Clicked Next", test);
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
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		boolean isPresent = driver.findElements(By.xpath( "//span[contains(text(),'Next')]" ) ).size() > 0;
		//boolean clickN = action.attributeEnabled(ic_ClickNext);
		if(isPresent) {
			WebElement web = ic_ClickNext.findElement(By.xpath(".//parent::*"));
			boolean status = action.attributeValidation(web,"aria-disabled","false",5);
			if(status) {
				return ic_ClickNext.findElement(By.xpath(".//parent::*"));
			}
		}
		return null;
	}


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

	public void skuProduct(ExtentTest test) throws IOException, AWTException {
//		String url =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",dataTable2.getValueOnOtherModule("ProductSearch","loginDetails",0),"url");
//		action.navigateToURL(url);
		String typeSearch = dataTable2.getValueOnOtherModule("ProductSearch","typeSearch",0);
		String productsToSearch = dataTable2.getValueOnOtherModule("ProductSearch","specificProduct",0);
		String quantityOfSearchProducts = dataTable2.getValueOnOtherModule("ProductSearch","Quantity",0);
		String waitTimeInSeconds = dataTable2.getValueOnOtherModule("ProductSearch","cartButtonWaitTimeInSeconds",0);
		String TypeOfOperation = dataTable2.getValueOnOtherModule("ProductSearch","TypeOfOperation",0);
		String validationRequired = dataTable2.getValueOnOtherModule("ProductSearch","validationRequired",0);
		List<String> theProducts = filterProducts(productsToSearch);
		ic_EnterTextToSearchBar(theProducts.get(0),test);
		action.explicitWait(5000);

		WebElement addToCart= driver.findElement(By.xpath("//*[@id=\"maincontent\"]//form/button/span"));
		action.scrollElementIntoView(addToCart);
		action.click(addToCart,"addToCart",test);
		action.explicitWait(5000);
		WebElement miniCart= driver.findElement(By.xpath("/html/body/div[2]/header/div[2]/div/div[3]/div[3]/a/span[1]"));
		action.click(miniCart,"miniCart",test);
		WebElement miniCartItemQty= driver.findElement(By.xpath("/html/body/div[2]/header/div[2]/div/div[3]/div[3]/div/div/div[2]/div[1]/ol/li/div/div/div[1]/div[2]/input"));
		action.clear(miniCartItemQty,"miniCartItemQty");
		action.writeText(miniCartItemQty,"99999999999999999999999999999999999","miniCartItemQty",test);

		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);

		WebElement QtyToMuchPopUp= driver.findElement(By.xpath("//*[@id=\"modal-content-15\"]/div"));
		action.getText(QtyToMuchPopUp,"QtyToMuchPopUp",test);
		action.explicitWait(5000);
		WebElement QtyToMuchPopUpOKButton= driver.findElement(By.xpath("/html/body/div[8]/aside/div[2]/footer/button/span"));
		action.click(QtyToMuchPopUpOKButton,"QtyToMuchPopUp",test);
		action.explicitWait(5000);
		WebElement miniCartcheckoutButton= driver.findElement(By.xpath("//*[@id=\"top-cart-btn-checkout\"]/span"));
		action.click(miniCartcheckoutButton,"miniCartcheckoutButton",test);


	}

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
	public void ic_SelectProductAndAddToCart(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException {
//		String urlControl=dataTable2.getValueOnCurrentModule("loginDetails");
////		if(urlControl.equals("")){
//			String url =dataTable2.getRowUsingReferenceAndKey("URL","SUTURLS",urlControl,"url");
//			action.navigateToURL(url);
////		}
		String typeSearch = input.get("typeSearch").get(rowNumber);
		String productsToSearch = input.get("specificProduct").get(rowNumber);
		String quantityOfSearchProducts = input.get("Quantity").get(rowNumber);
		String waitTimeInSeconds = input.get("cartButtonWaitTimeInSeconds").get(rowNumber);
		String TypeOfOperation = dataTable2.getValueOnCurrentModule("TypeOfOperation");
		String validationRequired = dataTable2.getValueOnCurrentModule("validationRequired");
		List<String> theProducts = filterProducts(productsToSearch);

		try {
			Map<String, List<String>> productsInCart =  ic_CreateCartFromProductListing(productsToSearch, quantityOfSearchProducts,typeSearch,waitTimeInSeconds, test);
			switch(TypeOfOperation){
			case "Add_To_Wishlist":
				if(validationRequired.equalsIgnoreCase("Yes_Wishlist")){
					WishList.ValidateProductsIn_Wishlist(productsInCart, test);
				}
				break;
			case "Add_To_Cart":
				cartValidation.iCcartVerification2(productsInCart, test);
				break;
			case "Validate_Out_Of_Stock":
				break;
			/*default:
				cartValidation.iCcartVerification2(productsInCart, test);
				break;*/
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
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
			logger.info(e.getMessage());
		}

	}

	void addToCartFromProdDetailsPage(WebElement productLink,String waitTimeInSeconds,int quanity,ExtentTest test) throws Exception {
		if(quanity == 1) {
			action.click(productLink, "Navigate to product Details page", test);
		}
		//click on product name and enter listing page
		//confirm that page has loaded
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);        
        boolean isPresent = driver.findElements(By.id("product-addtocart-button")).size() > 0;
        if(isPresent) {
		productDetailsPageAddToCartButton.click();
		cartValidation.cartButtonValidation(productDetailsPageAddToCartButton, Integer.parseInt(waitTimeInSeconds), test);
		action.explicitWait(8000);
        }else {
        	String outOfStockMessage = productOutOfStock.getText();
        	action.CompareResult("Product is out of stock", "Currently Out of Stock", outOfStockMessage, test);
        }
		//click add to cart button
	}
	void addToWishlistFromProdDetailsPage(WebElement productLink,String waitTimeInSeconds,int quanity,ExtentTest test) throws Exception {
		if(quanity == 1) {
			action.click(productLink, "Navigate to product Details page", test);
		}
		//click on product name and enter listing page
		//confirm that page has loaded
		if(action.waitUntilElementIsDisplayed(productDetailsPageAddToWishlistButton, 15000)) {
		action.click(productDetailsPageAddToWishlistButton,"Add to wish list button",test);
		action.explicitWait(Integer.parseInt(waitTimeInSeconds));
		}
		//click add to wish list
		
	}

	String findPrice(WebElement theProduct) {
		String price = theProduct.findElement(By.xpath(".//parent::*/following-sibling:: div/div[2]/div/span/span/span")).getText();
		return price;
	}

	public WebElement ic_FindProduct(ExtentTest test,String product) throws Exception {
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
	public static Map<String,List<String>> productData;

	Map<String,List<String>> ic_CreateCartFromProductListing(String productsList,String quantityOfProducts,String searchCategory,String waitTimeInSeconds,ExtentTest test) {
		productData = new LinkedHashMap<>();
		String cartAdditionMethod= dataTable2.getValueOnCurrentModule("CartAdditionMethod");
		String TypeOfOperation = dataTable2.getValueOnCurrentModule("TypeOfOperation");//"Add_To_Cart";//Add_To_Wishlist
		try {
			List<String> theProducts = filterProducts(productsList);
			List<String> quantity = filterProducts(quantityOfProducts);
			for(int s = 0 ; s<theProducts.size();s++) {
				List<String> productPriceAndQuantity = new ArrayList<>();
				loadProductListingPage(searchCategory, theProducts.get(s),test);//change signatures
				WebElement prod =  ic_FindProduct(test,theProducts.get(s));
				String productName = "";
				int set = 0;
				int quantityExecu = 0;
				if(prod!=null) {
					productName = prod.getText();
					String productPrice = findPrice(prod);
					productPriceAndQuantity.add(productPrice);
					for(int o =0;o<quantity.size();o++) {
						if(o==s) {
							for(int g = 0;g<Integer.parseInt(quantity.get(o));g++) {
								
								if(cartAdditionMethod.equalsIgnoreCase("ProductListingPage")) {
									WebElement cartButton = getCartButton(prod);
									addToCart(cartButton,waitTimeInSeconds,test);
								}else if(cartAdditionMethod.equalsIgnoreCase("ProductDetailPage")) {
									quantityExecu++;
									switch(TypeOfOperation){
									case "Add_To_Cart":
										addToCartFromProdDetailsPage(prod,waitTimeInSeconds,quantityExecu,test);
										break;
									case "Add_To_Wishlist":
										addToWishlistFromProdDetailsPage(prod, waitTimeInSeconds, quantityExecu, test);
										
										//add to wish list method call
										break;
									case "Get_SKU_Code":
										validateProductSKU.displayProductSKU(test, prod);
										break;
								   case "Add_To_Compare":
									   compareProducts.compareProductsFunctionality(test, prod);
									   //compareProducts.validateAddedProductsCompare(test, prod);
									  // compareProducts.clearAllProduct(test, prod);
									   break;
									case "Validate_Out_Of_Stock":
										addToCartFromProdDetailsPage(prod,waitTimeInSeconds,quantityExecu,test);
										break;
								}

								}
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
