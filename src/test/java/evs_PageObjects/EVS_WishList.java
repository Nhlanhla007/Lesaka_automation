package evs_PageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ic_PageObjects.ic_NavigetoWishlist;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class EVS_WishList {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;

	public EVS_WishList(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2 = dataTable2;
	}

	@FindBy(xpath = "//ol[@class='product-items wishlist']/li")
	List<WebElement> ic_AllMywishlistProducts;

	EVS_NavigetoWishlist navigateToWishlist;

	public void ValidateProductsIn_Wishlist(Map<String, List<String>> products, ExtentTest test) throws IOException {
		int waitTime = 10;;
		navigateToWishlist = new EVS_NavigetoWishlist(driver, dataTable2);
		navigateToWishlist.navigateWishlist(waitTime,test);

		for (Map.Entry selectedProducts : products.entrySet()) {

			for (WebElement productsInCart : ic_AllMywishlistProducts) {
				WebElement nameOfProduct = productsInCart.findElement(By.xpath(".//div/a[2]"));
				String item = action.getAttribute(nameOfProduct, "title");

				if (selectedProducts.getKey().equals(item)) {
					action.CompareResult("Name : " + item + " present in wishlist", (String) selectedProducts.getKey(),
							item, test);

				}
			}
		}
	}
}
