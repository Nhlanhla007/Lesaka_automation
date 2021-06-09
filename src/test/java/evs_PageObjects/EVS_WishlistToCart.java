package evs_PageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import Logger.Log;
import utils.Action;
import utils.ConfigFileReader;
import utils.DataTable2;

public class EVS_WishlistToCart {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;

	public EVS_WishlistToCart(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;
	}

	EVS_ProductSearch evs_Products = new EVS_ProductSearch(driver, dataTable2);
	EVS_Cart evs_Cart = new EVS_Cart(driver, dataTable2);

	static Logger logger = Log.getLogData(Action.class.getSimpleName());

	@FindBy(xpath = "//ol[@class='product-items wishlist']/li")
	List<WebElement> evs_allproducts_wishlist;

	@FindBy(xpath = "//div[@data-block='minicart']")
	public WebElement evs_CartButton_wishlist;

	@FindBy(xpath = "//ol[@class='minicart-items']/li")
	List<WebElement> evsAllCartProducts;

	public static Map<String, List<String>> productWishlistTocart;

	public void verifyProducts_wishlistTocart(HashMap<String, ArrayList<String>> input, ExtentTest test, int rowNumber)
			throws Exception {

		String ProductSelectionType = dataTable2.getValueOnCurrentModule("ProductSelectionType");


		Map<String, List<String>> AllProductsWishlist = evs_Products.productData;
		String waitTime = dataTable2.getValueOnCurrentModule("TimeOut_seconds");
		switch (ProductSelectionType) {
		case "All_product":
			checkProductwishlist_AddtoCart(AllProductsWishlist, waitTime, test);
			evs_cartVerification_AsperWishlist(AllProductsWishlist, test);
			break;
		case "Specific_product":
			String SelectiveProductsToadd = dataTable2.getValueOnCurrentModule("SelectiveProductsToadd");
			Map<String, List<String>> FilterProductsWishlist = FilterMap(AllProductsWishlist, SelectiveProductsToadd);
			checkProductwishlist_AddtoCart(FilterProductsWishlist, waitTime, test);
			evs_cartVerification_AsperWishlist(FilterProductsWishlist, test);
			break;
		}

	}

	public Map<String, List<String>> FilterMap(Map<String, List<String>> AllProductsWishlist, String Filtercriteria) {
		Map<String, List<String>> Filteredlist;
		Filteredlist = new LinkedHashMap<>();
		List<String> Val = null;
		List<String> CriteriaList = evs_Products.filterProducts(Filtercriteria);
		for (Map.Entry eachitems : AllProductsWishlist.entrySet()) {
			String eachkeys = (String) eachitems.getKey();
			if (CriteriaList.contains(eachkeys)) {
				Filteredlist.put(eachkeys, Val);
			}

		}
		return Filteredlist;

	}

	public void checkProductwishlist_AddtoCart(Map<String, List<String>> AllProductslist, String waitTimeInSeconds,
			ExtentTest test) throws IOException {
		productWishlistTocart = new LinkedHashMap<>();
		WebElement cartButton = null;
		List<String> Val = null;
		for (Map.Entry eachProducts : AllProductslist.entrySet()) {
			String eachproductname = (String) eachProducts.getKey();
			WebElement prodele = evs_FindProduct_wishlist(test, eachproductname);

			WebElement nameOfProduct = prodele.findElement(By.xpath(".//div/a[2]"));
			String WishlistproductName = action.getAttribute(nameOfProduct, "title");
			if (eachproductname.equalsIgnoreCase(WishlistproductName)) {
				cartButton = getCartButton_Wishlist(prodele);
				action.scrollElementIntoView(prodele);
				action.mouseover(prodele, "Mouse Over element");
				action.clickEle(cartButton, "Add Product", test);
				productWishlistTocart.put(eachproductname, Val);
			}

		}

	}

	WebElement getCartButton_Wishlist(WebElement product) {

		WebElement cartButton = product.findElement(By.xpath(".//div/div/fieldset/div/div/button"));
		return cartButton;
	}

	public void addToCart(WebElement addToCartButton, String waitTimeInSeconds, ExtentTest test)
			throws InterruptedException {
		action.mouseover(addToCartButton, "Scroll to add to cart");
		action.explicitWait(2000);
		addToCartButton.click();
		action.explicitWait(7000);
	}

	public WebElement evs_FindProduct_wishlist(ExtentTest test, String product) throws IOException {
		boolean status = true;

		while (status) {
			List<WebElement> allProducts = evs_allproducts_wishlist;
			for (WebElement el : allProducts) {
				WebElement nameOfProduct = el.findElement(By.xpath(".//div/a[2]"));
				String item = action.getAttribute(nameOfProduct, "title");
				if (item.trim().toLowerCase().equalsIgnoreCase(product)) {
					status = false;
					return el;
				}
			}

		}
		return null;
	}

	public void evs_cartVerification_AsperWishlist(Map<String, List<String>> products, ExtentTest test) throws Exception {
		action.explicitWait(10000);
		action.click(evs_CartButton_wishlist, "View Cart", test);
		for (WebElement productsInCart : evsAllCartProducts) {
			WebElement nameOfProduct = productsInCart.findElement(By.xpath(".//div/a"));
			String item = action.getAttribute(nameOfProduct, "title");
			for (Map.Entry selectedProducts : products.entrySet()) {
				String productsName = (String) selectedProducts.getKey();
				if (productsName.equalsIgnoreCase(item)) {
					action.CompareResult("Name : " + item, productsName, item, test);

				}
			}
		}

	}
}
