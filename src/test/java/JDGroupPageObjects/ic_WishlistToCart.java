package JDGroupPageObjects;

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
import utils.DataTable2;

public class ic_WishlistToCart {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
	public ic_WishlistToCart(WebDriver driver, DataTable2 dataTable2) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		action = new Action(driver);
		this.dataTable2=dataTable2;
	}
	Ic_Products ic_Products = new Ic_Products(driver,dataTable2);
	IC_Cart ic_Cart =new IC_Cart(driver,dataTable2);
	static Logger logger = Log.getLogData(Action.class.getSimpleName());
	@FindBy(xpath="//ol[@class='product-items']/li")
    private List<WebElement> ic_AllMywishlistProducts;
	@FindBy(xpath = "//ol[@class='product-items']/li//strong[@class='product-item-name']/a")
	public List<WebElement> ic_allproducts_wishlist;
	@FindBy(xpath = "//header/div[2]/div/div[3]/div[3]/a")
    public WebElement iCCartButton_wishlist;
	@FindBy(xpath="//*[@id=\"mini-cart\"]/li")
    List<WebElement> icAllCartProducts;
	public static Map<String,List<String>> productWishlistTocart;
	public void verifyProducts_wishlistTocart(HashMap<String, ArrayList<String>> input,ExtentTest test,int rowNumber) throws IOException{
		String ProductSelectionType = dataTable2.getValueOnCurrentModule("ProductSelectionType");//"All_product";//"Specific_product"
		
		Map<String,List<String>>AllProductsWishlist = ic_Products.productData;
		String waitTime=dataTable2.getValueOnCurrentModule("TimeOut_seconds");
		switch (ProductSelectionType){
		case "All_product":
			checkProductwishlist_AddtoCart(AllProductsWishlist,waitTime,test);
			iCcartVerification_AsperWishlist(AllProductsWishlist, test);
			break;
		case "Specific_product":
			String SelectiveProductsToadd = dataTable2.getValueOnCurrentModule("SelectiveProductsToadd");
			Map<String,List<String>> FilterProductsWishlist = FilterMap(AllProductsWishlist,SelectiveProductsToadd);
			checkProductwishlist_AddtoCart(FilterProductsWishlist,waitTime,test);
			iCcartVerification_AsperWishlist(FilterProductsWishlist, test);
			break;
		}
		
		
	}
	public Map<String,List<String>> FilterMap(Map<String,List<String>>AllProductsWishlist,String Filtercriteria){
		Map<String,List<String>> Filteredlist;
		Filteredlist = new LinkedHashMap<>();
		List<String> Val =null;
		List<String>CriteriaList = ic_Products.filterProducts(Filtercriteria);
		for(Map.Entry eachitems : AllProductsWishlist.entrySet()){
			String eachkeys= (String) eachitems.getKey();
			if(CriteriaList.contains(eachkeys)){
				Filteredlist.put(eachkeys, Val);
			}
			
		}
		return Filteredlist;
		
	}
	public void checkProductwishlist_AddtoCart(Map<String,List<String>>AllProductslist, String waitTimeInSeconds, ExtentTest test) throws IOException{
		productWishlistTocart = new LinkedHashMap<>();
		WebElement cartButton =null;
		List<String> Val =null;
		for (Map.Entry eachProducts : AllProductslist.entrySet()){
	    	 String eachproductname = (String) eachProducts.getKey();
	    	 WebElement prodele =  ic_FindProduct_wishlist(test,eachproductname);
	    	 String WishlistproductName = prodele.getText();
	    	 if(eachproductname.equalsIgnoreCase(WishlistproductName)){
	    		 cartButton = getCartButton_Wishlist(prodele); 
	    		 ic_Products.addToCart(cartButton,waitTimeInSeconds, test);
	    		 productWishlistTocart.put(eachproductname, Val);
	    		 action.CompareResult("Name : " + eachproductname+" Added from Wishlist to Cart " , eachproductname, WishlistproductName, test);
	    	 }
	    		 
	     }
		
	}
	WebElement getCartButton_Wishlist(WebElement product) {
		WebElement cartButton = product.findElement(By.xpath("//parent::*/parent::*/parent::*//button[@title='Add to Cart']"));
		return cartButton;
	}
	public void addToCart(WebElement addToCartButton,String waitTimeInSeconds,ExtentTest test) {
		try {
			action.mouseover(addToCartButton, "Scroll to add to cart");
			Thread.sleep(2000);
			addToCartButton.click();
			//ic_Cart.cartButtonValidation(addToCartButton, Integer.parseInt(waitTimeInSeconds), test);
			Thread.sleep(7000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.info(e.getMessage());
		}

	}
	public WebElement ic_FindProduct_wishlist(ExtentTest test,String product) throws IOException {
		boolean status= true;
		
		while(status) {
			List<WebElement> allProducts = ic_allproducts_wishlist;
			for(WebElement el: allProducts) {
				if(el.getText().trim().toLowerCase().equalsIgnoreCase(product)) {
					status = false;
					return el;
				}
			}
			
		}
		return null;	
	}
	public void iCcartVerification_AsperWishlist(Map<String, List<String>> products,ExtentTest test) {
		  //Find all elements from the list
		 iCCartButton_wishlist.click();
		  try {
			for(WebElement productsInCart : icAllCartProducts) {
				  String nameOfProduct = productsInCart.findElement(By.xpath(".//strong/a")).getText();
				 // String price = productsInCart.findElement(By.xpath(".//span/span/span/span")).getText();					  
			
				  for(Map.Entry selectedProducts : products.entrySet()) {
					  //@SuppressWarnings("unchecked")
					//List<String> data = (List<String>)selectedProducts.getValue();
					if(selectedProducts.getKey().equals(nameOfProduct)) {
					  action.CompareResult("Name : " + nameOfProduct , (String)selectedProducts.getKey(), nameOfProduct, test);
					  //action.CompareResult("Price : " +price +" for " +nameOfProduct, data.get(0), price, test);
					  //action.CompareResult("Quantity : " + quantity +" for " + nameOfProduct, data.get(1), quantity, test);						  
					}
				  }
			  }
			//action.CompareResult("Products Total", String.valueOf(sum), icSubtotal.getText().replace("R", "").replace(",", "").replace(".", "") , test);
			//action.clickEle(icCCheckout, "Secure Checkout", test);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//logger.info(e.getMessage());
		}
		  //Compare with data from the map
		  
	  }	
}
