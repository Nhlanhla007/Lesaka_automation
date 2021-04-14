package JDGroupPageObjects;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;

import utils.Action;
import utils.DataTable2;

public class ic_AddProductsTowishlist {
	WebDriver driver;
	Action action;
	DataTable2 dataTable2;
		public ic_AddProductsTowishlist(WebDriver driver, DataTable2 dataTable2) {
			this.driver = driver;
			PageFactory.initElements(driver, this);
			action = new Action(driver);
			this.dataTable2=dataTable2;
		}
		ic_RefreshLogoHomepage RefreshLogoHomepage =new ic_RefreshLogoHomepage(driver,dataTable2);
		Ic_Products icProducts =new Ic_Products(driver,dataTable2);
		public void addProductsTowishlist(ExtentTest test) throws IOException{
			String AllProducts = "Samsung Toner D105L#b#c";
			String AllQuantity = "1#2#3";
			RefreshLogoHomepage.homepageLogo(test);
			//List<String> ToatalProducts = icProducts.filterProducts(AllProducts);
			//List<String> ToatalQuantity = icProducts.filterProducts(AllQuantity);
			
		}
		/*
		public static Map<String,String> productListWishlist;
		public Map<String,String> ic_CreateWishlistFromProductListing(String productsList,String quantityOfProducts,String searchCategory,String waitTimeInSeconds,ExtentTest test) {
			productListWishlist = new LinkedHashMap<>();
			String cartAdditionMethod= dataTable2.getValueOnCurrentModule("CartAdditionMethod");
			try {
				List<String> theProducts = icProducts.filterProducts(productsList);
				List<String> quantity = icProducts.filterProducts(quantityOfProducts);
				for(int s = 0 ; s<theProducts.size();s++) {
					
					icProducts.loadProductListingPage(searchCategory, theProducts.get(s),test);//change signatures
					WebElement prod =  icProducts.ic_FindProduct(test,theProducts.get(s));
					String productName = "";
					int set = 0;
					int quantityExecu = 0;
					if(prod!=null) {
						productName = prod.getText();
						
						
						for(int o =0;o<quantity.size();o++) {
							if(o==s) {
								for(int g = 0;g<Integer.parseInt(quantity.get(o));g++) {
									
									if(cartAdditionMethod.equalsIgnoreCase("ProductListingPage")) {
										WebElement cartButton = icProducts.getCartButton(prod);
										icProducts.addToCart(cartButton,waitTimeInSeconds,test);
									if(cartAdditionMethod.equalsIgnoreCase("ProductDetailPage")) {
										quantityExecu++;
										icProducts.addToCartFromProdDetailsPage(prod,waitTimeInSeconds,quantityExecu,test);
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

		}*/
		
}
