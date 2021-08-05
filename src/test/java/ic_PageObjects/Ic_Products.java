package ic_PageObjects;

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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import Logger.Log;
import utils.Action;
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

	@FindBy(xpath = "//*[@class=\"qty-action update update-cart-item\"]")
	WebElement updateCartQuantityButton;
	
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

	public List<WebElement> returnList() {
		return ic_products;
	}

	public WebElement returnNext() {
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		boolean isPresent = driver.findElements(By.xpath("//span[contains(text(),'Next')]")).size() > 0;
		// boolean clickN = action.attributeEnabled(ic_ClickNext);
		if (isPresent) {
			WebElement web = ic_ClickNext.findElement(By.xpath(".//parent::*"));
			boolean status = action.attributeValidation(web, "aria-disabled", "false", 5);
			if (status) {
				return ic_ClickNext.findElement(By.xpath(".//parent::*"));
			}
		}
		return null;
	}

	public void ic_ClickProductLink(ExtentTest test) {
		try {
			if (ic_ElementVisable(icProductLink)) {
				action.click(icProductLink, "Click product link", test);
			//	Thread.sleep(5000);
				action.waitForPageLoaded(40);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}

	public void skuProduct(ExtentTest test) throws IOException, AWTException {
		String productsToSearch = dataTable2.getValueOnOtherModule("ProductSearch", "specificProduct", 0);
		List<String> theProducts = filterProducts(productsToSearch);
		ic_EnterTextToSearchBar(theProducts.get(0), test);
		action.explicitWait(5000);

		WebElement addToCart= driver.findElements(By.xpath("//*[@class=\"product-item-link\"]")).get(0);
		action.mouseover(addToCart, "Scroll to add to cart");
		System.out.println("Mouse over the");
		WebElement btnAddToC = addToCart.findElement(By.xpath(".//parent::*/following-sibling::div//button"));
		action.click(btnAddToC,"addToCart",test);
		action.explicitWait(5000);
		WebElement miniCart = driver.findElement(By.xpath("//*[@class=\"action showcart\"]"));
		action.click(miniCart, "miniCart", test);

		WebElement miniCartcheckoutButton = driver.findElement(By.xpath("//*[@id=\"top-cart-btn-checkout\"]/span"));
		action.click(miniCartcheckoutButton, "miniCartcheckoutButton", test);

	}

	public void skuProductValidateQuantity(ExtentTest test) throws IOException, AWTException {
		String productsToSearch = dataTable2.getValueOnOtherModule("ProductSearch","specificProduct",0);
		List<String> theProducts = filterProducts(productsToSearch);
		ic_EnterTextToSearchBar(theProducts.get(0),test);
		action.explicitWait(5000);

		WebElement addToCart= driver.findElements(By.xpath("//*[@class=\"product-item-link\"]")).get(0);
		action.mouseover(addToCart, "Scroll to add to cart");
		WebElement btnAddToC = addToCart.findElement(By.xpath(".//parent::*/following-sibling::div//button"));
		action.click(btnAddToC,"addToCart",test);
		action.explicitWait(5000);
		WebElement miniCart = driver.findElement(By.xpath("//*[@class=\"action showcart\"]"));
		action.click(miniCart, "miniCart", test);

		WebElement miniCartItemQty= driver.findElement(By.xpath("//*[@class = \"details-qty qty\"]/input"));
		action.clear(miniCartItemQty,"miniCartItemQty");
		action.writeText(miniCartItemQty,"9999999999999","miniCartItemQty",test);
		action.explicitWait(2000);
		action.javaScriptClick(updateCartQuantityButton, "Update Quantity", test);

		action.explicitWait(2000);
		
		WebElement QtyToMuchPopUp= driver.findElement(By.xpath("//*[@class = \"modal-content\"]/div"));
		action.getText(QtyToMuchPopUp,"QtyToMuchPopUp",test);
		action.explicitWait(5000);
		WebElement QtyToMuchPopUpOKButton= driver.findElement(By.xpath("//*[@class=\"modal-footer\"]/button"));
		action.click(QtyToMuchPopUpOKButton,"QtyToMuchPopUp",test);

	}

	
	
	public void ic_EnterTextToSearchBar(String productToFind, ExtentTest test) {
		try {
			ic_ElementVisable(icSearchBar);
			action.clear(icSearchBar, "SearchBar");
			action.writeText(icSearchBar, productToFind, "Search Bar", test);
			action.click(icSearchIcon, "Click on search", test);
			action.waitForPageLoaded(20);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
	}

	public boolean ic_ElementVisable(WebElement element) {
		return action.elementExists(element, 10);
	}

	public List<String> filterProducts(String allProductsToSearch) {
		String[] productsArray = allProductsToSearch.split("#");
		List<String> productsList = new ArrayList<String>(Arrays.asList(productsArray));
		return productsList;
	}

	public void ic_SelectProductAndAddToCart(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber)
			throws Exception {
		String typeSearch = input.get("typeSearch").get(rowNumber);
		String productsToSearch = input.get("specificProduct").get(rowNumber);
		String quantityOfSearchProducts = input.get("Quantity").get(rowNumber);
		String waitTimeInSeconds = input.get("cartButtonWaitTimeInSeconds").get(rowNumber);
		String TypeOfOperation = dataTable2.getValueOnCurrentModule("TypeOfOperation");
		String validationRequired = dataTable2.getValueOnCurrentModule("validationRequired");
		List<String> theProducts = filterProducts(productsToSearch);

		//try {
			Map<String, List<String>> productsInCart = ic_CreateCartFromProductListing(productsToSearch,
					quantityOfSearchProducts, typeSearch, waitTimeInSeconds, test);
			switch (TypeOfOperation) {
			case "Add_To_Wishlist":
				if (validationRequired.equalsIgnoreCase("Yes_Wishlist")) {
					WishList.ValidateProductsIn_Wishlist(productsInCart, test);
				}
				break;
			case "Add_To_Cart":
				cartValidation.iCcartVerification2(productsInCart, test);
				break;
			case "Validate_Out_Of_Stock":
				break;

			}
	}

	public void loadProductListingPage(String category, String product, ExtentTest test) throws IOException {
		switch (category) {
		case "SearchUsingSearchBar":
			ic_EnterTextToSearchBar(product, test);
			break;
		case "All Products":
			ic_ClickProductLink(test);
			break;
		default:
			if (category.equalsIgnoreCase("Computers Notebooks & Tablet's")) {
				action.mouseover(icProductLink, "MouseOverICProduct");
				action.click(computersNoteBooks, "Computers Notebooks & Tablet's", test);

			} else if (category.equalsIgnoreCase("Downloads & Top Ups")) {
				action.mouseover(icProductLink, "MouseOverICProduct");
				action.click(downloads, "Downloads & Top Ups", test);

			} else if (category.equalsIgnoreCase("Software")) {
				action.mouseover(icProductLink, "MouseOverICProduct");
				action.click(software, "Software", test);

			} else if (category.equalsIgnoreCase("Fitness & Wearables")) {
				action.mouseover(icProductLink, "MouseOverICProduct");
				action.click(fitness, "Fitness & Wearables", test);

			} else {
				WebElement typeOfSearch = byCategory(category);
				action.mouseover(icProductLink, "MouseOverICProduct");
				action.click(typeOfSearch, typeOfSearch.getText(), test);

			}

			break;

		}

		action.waitForPageLoaded(40);
		action.explicitWait(3000);
		
	}

	public WebElement byCategory(String nameOfCategory) {
		WebElement category = driver.findElement(By.xpath("//span[contains(text(),\"" + nameOfCategory + "\")]"));
		return category;
	}

	WebElement ic_FindProduct(WebElement elem) {
		return elem;
	}

	WebElement getCartButton(WebElement product) {
		WebElement cartButton = product
				.findElement(By.xpath(".//parent::*/following-sibling::div/div[3]/div/div[1]/form"));
		return cartButton;
	}

	void addToCart(WebElement addToCartButton, String waitTimeInSeconds, ExtentTest test) {
		try {
			action.mouseover(addToCartButton, "Scroll to add to cart");
			Thread.sleep(2000);
			addToCartButton.click();
			cartValidation.cartButtonValidation(addToCartButton, Integer.parseInt(waitTimeInSeconds), test);
			Thread.sleep(4000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
		}

	}

	void addToCartFromProdDetailsPage(WebElement productLink, String waitTimeInSeconds, int quanity, ExtentTest test)
			throws Exception {
		if (quanity == 1) {
			action.click(productLink, "Navigate to product Details page", test);
		}

		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		boolean isPresent = driver.findElements(By.id("product-addtocart-button")).size() > 0;
		if (isPresent) {
			productDetailsPageAddToCartButton.click();
			cartValidation.cartButtonValidation(productDetailsPageAddToCartButton, Integer.parseInt(waitTimeInSeconds),
					test);
			action.explicitWait(5000);
		} /*
			 * else { String outOfStockMessage = productOutOfStock.getText();
			 * action.CompareResult("Product is out of stock", "Currently Out of Stock",
			 * outOfStockMessage, test); }
			 */
		// click add to cart button
	}

	void addToWishlistFromProdDetailsPage(WebElement productLink, String waitTimeInSeconds, int quanity,
			ExtentTest test) throws Exception {
		if (quanity == 1) {
			action.click(productLink, "Navigate to product Details page", test);
		}
		if (action.waitUntilElementIsDisplayed(productDetailsPageAddToWishlistButton, 15000)) {
			action.click(productDetailsPageAddToWishlistButton, "Add to wish list button", test);
			action.explicitWait(Integer.parseInt(waitTimeInSeconds));
		}
	}

	String findPrice(WebElement theProduct) {
		String price = theProduct
				.findElement(By.xpath(".//parent::*/following-sibling:: div/div[2]/div/span/span/span")).getText();
		return price;
	}

	public WebElement ic_FindProduct(ExtentTest test, String product) throws Exception {
		boolean status = true;
		while (status) {
			List<WebElement> allProducts = ic_products;
			for (WebElement el : allProducts) {
				if (el.getText().trim().toLowerCase().equalsIgnoreCase(product)) {
					status = false;
					return el;
				}
			}
			WebElement nextButton = returnNext();
			if (nextButton != null) {
				clickNext(test);
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					logger.info(e.getMessage());
				}
			} else {
				status = false;
				action.CompareResult("Product Not Found", product, "", test);
				throw new Exception("Product Not Found");
			}
		}
		return null;
	}

	public static Map<String, List<String>> productData;

	Map<String, List<String>> ic_CreateCartFromProductListing(String productsList, String quantityOfProducts,
			String searchCategory, String waitTimeInSeconds, ExtentTest test) throws Exception {
		productData = new LinkedHashMap<>();
		String cartAdditionMethod = dataTable2.getValueOnCurrentModule("CartAdditionMethod");
		String TypeOfOperation = dataTable2.getValueOnCurrentModule("TypeOfOperation");
		//try {
			List<String> theProducts = filterProducts(productsList);
			List<String> quantity = filterProducts(quantityOfProducts);
			for (int s = 0; s < theProducts.size(); s++) {
				List<String> productPriceAndQuantity = new ArrayList<>();
				loadProductListingPage(searchCategory, theProducts.get(s), test);
				WebElement prod = ic_FindProduct(test, theProducts.get(s));
				String productName = "";
				int set = 0;
				int quantityExecu = 0;
				if (prod != null) {
					productName = prod.getText();
					String productPrice = findPrice(prod);
					productPriceAndQuantity.add(productPrice);
					for (int o = 0; o < quantity.size(); o++) {
						if (o == s) {
							for (int g = 0; g < Integer.parseInt(quantity.get(o)); g++) {

								if (cartAdditionMethod.equalsIgnoreCase("ProductListingPage")) {
									WebElement cartButton = getCartButton(prod);
									addToCart(cartButton, waitTimeInSeconds, test);
								} else if (cartAdditionMethod.equalsIgnoreCase("ProductDetailPage")) {
									quantityExecu++;
									switch (TypeOfOperation) {
									case "Add_To_Cart":
										addToCartFromProdDetailsPage(prod, waitTimeInSeconds, quantityExecu, test);
										break;
									case "Add_To_Wishlist":
										addToWishlistFromProdDetailsPage(prod, waitTimeInSeconds, quantityExecu, test);

										// add to wish list method call
										break;
									case "Get_SKU_Code":
										validateProductSKU.displayProductSKU(test, prod);
										break;
									case "Add_To_Compare":
										compareProducts.compareProductsFunctionality(test, prod);
										// compareProducts.validateAddedProductsCompare(test, prod);
										// compareProducts.clearAllProduct(test, prod);
										break;
									case "Validate_Out_Of_Stock":
										validateProductOutOfStock(prod, waitTimeInSeconds, quantityExecu, test);
										break;
									}

								}
								if (set <= 0) {
									set++;
									productPriceAndQuantity.add(quantity.get(o));
								}
							}
						}
					}
				}
				productData.put(productName, productPriceAndQuantity);
			}
//		} catch (Exception e) {
//			logger.info(e.getMessage());
//			e.printStackTrace();
//		}
		return productData;

	}
	
	public void validateProductOutOfStock(WebElement productLink, String waitTimeInSeconds, int quanity, ExtentTest test) throws Exception {
		if (quanity == 1) {
			action.click(productLink, "Navigate to product Details page", test);
		}

		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		boolean isPresent = driver.findElements(By.id("product-addtocart-button")).size() > 0;
		action.CompareResult("Add To Cart Button Should Not Be Present", "true", String.valueOf(!isPresent), test);
		if (isPresent) {
			throw new Exception("Selected Product Is In Stock");
		} else {
			String outOfStockMessage = productOutOfStock.getText();
			action.CompareResult("Product is out of stock", "Currently Out of Stock", outOfStockMessage, test);
		}
	}
	
	
}
