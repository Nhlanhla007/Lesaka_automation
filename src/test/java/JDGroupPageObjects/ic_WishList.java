package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class ic_WishList {
	WebDriver driver;
    Action action;
    DataTable2 dataTable2;
    public ic_WishList(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        this.dataTable2 = dataTable2;
    }
    @FindBy(xpath="//ol[@class='product-items']/li")
    List<WebElement> ic_AllMywishlistProducts;
    ic_NavigetoWishlist NavigetoWishlist =new ic_NavigetoWishlist(driver,dataTable2);
    public void ValidateProductsIn_Wishlist(Map<String, List<String>> products,ExtentTest test) throws IOException{
    	int waitTime = 11;
    	//NavigetoWishlist.navigateWishlist(waitTime, test);
    	for(Map.Entry selectedProducts : products.entrySet()) {
    		
    	
	    	for(WebElement productsInCart : ic_AllMywishlistProducts) {
	    		String nameOfProduct = productsInCart.findElement(By.xpath(".//strong/a")).getText();
					  action.CompareResult("Name : " + nameOfProduct +" present in wishlist", (String)selectedProducts.getKey(), nameOfProduct, test);
    				  action.CompareResult("Name : " + nameOfProduct , (String)selectedProducts.getKey(), nameOfProduct, test);
					}
	    	}
        }
    }
}
