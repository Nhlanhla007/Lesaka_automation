package spm_PageObjects;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
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

public class SPM_ProductSearch {

    WebDriver driver;
    Action action;
    //EVS_Cart cartValidation;
    DataTable2 dataTable2;
    //EVS_WishList WishList;
    //EVS_CompareProducts compareProducts;

    // ic_validateProductSKU validateProductSKU;
    // ic_CompareProducts compareProducts;
    public SPM_ProductSearch(WebDriver driver, DataTable2 dataTable2) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        action = new Action(driver);
        //cartValidation = new EVS_Cart(driver, dataTable2);
        this.dataTable2 = dataTable2;
        //WishList = new EVS_WishList(driver, dataTable2);
       // compareProducts = new EVS_CompareProducts(driver, dataTable2);
        // validateProductSKU = new ic_validateProductSKU(driver, dataTable2);

    }

    static Logger logger = Log.getLogData(Action.class.getSimpleName());

    /*
     * PAGE OBJECTS
     */
    @FindBy(xpath = "//span[contains(text(),'Products')]")
    WebElement productLink;

    @FindBy(xpath = "//input[@id='search']")
    WebElement searchBar;

    @FindBy(xpath = "//div[@class='my-search']")
    WebElement searchIcon;

    @FindBy(css = "a.product-item-link")
    public List<WebElement> products;

    @FindBy(xpath = "//span[contains(text(),'Next')]")
    public WebElement clickNext;

    @FindBy(xpath = "//a[@class=\"icon__movie-projector\"]")
    WebElement entertainmentProdLink;

    @FindBy(xpath = "//body[1]/div[1]/header[1]/div[2]/div[1]/div[2]/div[3]/nav[1]/ul[1]/li[1]/ul[1]/li[1]/ul[1]/li[7]/a[1]/span[1]")
    WebElement fitness;

    @FindBy(xpath = "//body[1]/div[1]/header[1]/div[2]/div[1]/div[2]/div[3]/nav[1]/ul[1]/li[1]/ul[1]/li[1]/ul[1]/li[9]/a[1]/span[1]")
    WebElement software;

    @FindBy(xpath = "//span[contains(text(),'Furniture & D�cor')]")
    WebElement furnitureAndDecor;

    @FindBy(xpath = "//*[@title='Availability']/span")
    WebElement verifyAvailability1;

    @FindBy(xpath = "//*[@title='Availability:']/span")
    WebElement verifyAvailability;

    @FindBy(xpath = "//span[@class = \"sr-only\"]")
    WebElement shopByDeptLink;

    @FindBy(xpath = "//span[@class='sku-code']")
    WebElement skuCode;

    @FindBy(xpath = "//*[@data-price-type = \"finalPrice\"]/span")
    WebElement productFinalPrice;

    List<WebElement> listElements;

    @FindBy(id = "product-addtocart-button")
    WebElement productDetailsPageAddToCartButton;

    @FindBy(xpath = "//span[text()='Add to Wish List']")
    WebElement addToWishListButton;

    @FindBy(xpath = "//a[@title='Wishlist']")
    WebElement wishListButton;

    @FindBy(xpath = "//span[@class='logged-in']")
    WebElement loggedInIcon;

    @FindBy(xpath = "//div[contains(text(),'You must login or register to add items to your wi')]")
    WebElement loginMsg;

    @FindBy(xpath = "//span[contains(text(),'My Wish List(s)')]")
    WebElement myWishListPopUp;

    @FindBy(xpath = "//span[@data-action='add-to-existing-wishlist' and @title='Wish List']")
    WebElement addToWishListCheckBox;

    @FindBy(xpath = "//span[contains(text(),'Add to Wish List(s)')]")
    WebElement addToWishLists;

    @FindBy(xpath = "//*[@class=\"message-error error message\"]")
    WebElement productOfStockErrorMessage;

    @FindBy(xpath = "//*[@title=\"Notify me when available\"]")
    WebElement notifyWhenProductIsAvailable;

    @FindBy(xpath = "//*[@class = \"modal-content\"]/div")
    WebElement quantityExceedPopUpMsg;

    @FindBy(xpath = "//button[@class=\"qty-action update update-cart-item\"]")
    WebElement updateQuantityButton;
    
    //Range
    @FindBy(xpath = "//div[@class='product-info-top-title']//div[@class='base']")
    WebElement bed_prodName;
    
    @FindBy(xpath = "//div[@option-label='Bed Set (Mattress + Base)']")
    WebElement bed_bedSet;
    
    @FindBy(xpath = "//div[@option-label='Mattress Only']")
    WebElement bed_bedMattress;
    
    @FindBy(xpath = "(//span[@class='swatch-option-inner'])[1]")
    WebElement bed_lengthMattress;
    
    @FindBy(xpath = "(//span[@class='swatch-option-inner'])[2]")
    WebElement bed_exraLength;
    @FindBy(xpath = "//div[@option-label='Single (92cm)']")
    WebElement bed_type92;
    
    @FindBy(xpath = "//div[@option-label='Three Quarter']")
    WebElement bed_type107CM;
    
    @FindBy(xpath = "//div[@option-label='Double (137cm)']")
    WebElement bed_type137CM;
    
    @FindBy(xpath = "//div[@option-label='Queen (152cm)']")
    WebElement bed_type152CM;
    
    @FindBy(xpath = "//div[@option-label='King (183cm)']")
    WebElement bed_type183CM;
    
    @FindBy(xpath = "//div[@class='product-info-price']//span[@class='price']")
    WebElement bed_price ;

    public void clickNext(ExtentTest test) throws Exception {
        action.mouseover(clickNext, "scroll to element");
        action.click(clickNext, "Clicked Next", test);
    }

    public List<WebElement> returnList() {
        return products;
    }

    public WebElement returnNext() {
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        boolean isPresent = driver.findElements(By.xpath("//span[contains(text(),'Next')]")).size() > 0;
        if (isPresent) {
            WebElement web = clickNext.findElement(By.xpath(".//parent::*"));
            boolean status = action.attributeValidation(web, "aria-disabled", "false", 5);
            if (status) {
                return clickNext.findElement(By.xpath(".//parent::*"));
            }
        }
        return null;
    }

    public void ic_ClickProductLink(ExtentTest test) throws Exception {
        //try {
            if (ic_ElementVisable(productLink)) {
                action.click(productLink, "Click product link", test);
                action.waitForJStoLoad(60);
            }

//        } catch (Exception e) {
      //      e.printStackTrace();
    //        logger.info(e.getMessage());
  //      }
    }

//    public void skuProduct(ExtentTest test) throws IOException, AWTException {
//        String productsToSearch = dataTable2.getValueOnOtherModule("evs_ProductSearch", "specificProduct", 0);
//        List<String> theProducts = filterProducts(productsToSearch);
//        ic_EnterTextToSearchBar(theProducts.get(0), test);
//        action.explicitWait(5000);
//
//        boolean productExistence = driver.findElements(By.xpath("//*[@class=\"message info empty\"]")).size()>0;
//
//        WebElement addToCart = driver.findElements(By.xpath("//*[@class=\"product-item-link\"]")).get(0);
//        action.mouseover(addToCart, "Scroll to add to cart");
//        WebElement btnAddToC = addToCart.findElement(By.xpath(".//parent::*/following-sibling::div[5]/div[2]/div/form/button"));
//        action.click(btnAddToC, "addToCart", test);
//        action.explicitWait(5000);
//
//        WebElement miniCart = driver.findElement(By.xpath("//*[@class = \"minicart-wrapper\"]/a"));
//        action.click(miniCart, "miniCart", test);
//
//        WebElement miniCartcheckoutButton = driver.findElement(By.xpath("//*[@id=\"top-cart-btn-checkout\"]/span"));
//        action.click(miniCartcheckoutButton, "miniCartcheckoutButton", test);
//
//
//    }

    public void skuProduct(ExtentTest test) throws Exception {
        String productsToSearch = dataTable2.getValueOnOtherModule("evs_ProductSearch", "specificProduct", 0);
        List<String> theProducts = filterProducts(productsToSearch);
        ic_EnterTextToSearchBar(theProducts.get(0), test);
        action.explicitWait(5000);
        boolean productExistence = driver.findElements(By.xpath("//*[@class=\"message info empty\"]")).size()>0;
        WebElement addToCart;
        if(!productExistence) {
            addToCart = driver.findElements(By.xpath("//*[@class=\"product-item-link\"]")).get(0);
            action.mouseover(addToCart, "Scroll to add to cart");
            boolean canProductBeAddedToCart = addToCart.findElements(By.xpath(".//parent::*/following-sibling::div[5]/div[2]/div/form/button")).size()>0;
            if(canProductBeAddedToCart) {
                WebElement btnAddToC = addToCart.findElement(By.xpath(".//parent::*/following-sibling::div[5]/div[2]/div/form/button"));
                action.click(btnAddToC, "addToCart", test);
                action.explicitWait(5000);
            }else {
                addToCartFromProdDetailsPage(addToCart, "10", 1, test);

            }
            WebElement miniCart = driver.findElement(By.xpath("//*[@class = \"minicart-wrapper\"]/a"));
            action.click(miniCart, "miniCart", test);
            WebElement miniCartcheckoutButton = driver.findElement(By.xpath("//*[@id=\"top-cart-btn-checkout\"]/span"));
            action.click(miniCartcheckoutButton, "miniCartcheckoutButton", test);
        }else {
            throw new Exception("Product Does Not Exist");
        }
    }


    public void skuProductValidateQuantity(ExtentTest test) throws Exception, AWTException {
        String productsToSearch = dataTable2.getValueOnOtherModule("evs_ProductSearch", "specificProduct", 0);
        List<String> theProducts = filterProducts(productsToSearch);
        ic_EnterTextToSearchBar(theProducts.get(0), test);
        action.explicitWait(5000);

        WebElement addToCart = driver.findElements(By.xpath("//*[@class=\"product-item-link\"]")).get(0);
        action.mouseover(addToCart, "Scroll to add to cart");
        WebElement btnAddToC = addToCart.findElement(By.xpath(".//parent::*/following-sibling::div[5]/div[2]/div/form/button"));
        action.click(btnAddToC, "addToCart", test);
        action.explicitWait(5000);
        WebElement miniCart = driver.findElement(By.xpath("//*[@class = \"minicart-wrapper\"]/a"));
        action.click(miniCart, "miniCart", test);
        WebElement miniCartItemQty = driver.findElement(By.xpath("//*[@class = \"details-qty qty\"]/input"));
        action.clear(miniCartItemQty, "miniCartItemQty");
        action.writeText(miniCartItemQty, "9999999999999", "miniCartItemQty", test);
        action.explicitWait(3000);
        action.waitUntilElementIsDisplayed(updateQuantityButton, 10000);
        action.javaScriptClick(updateQuantityButton, "Update Quantity", test);
        action.explicitWait(2000);
        if (action.waitUntilElementIsDisplayed(quantityExceedPopUpMsg, 20000)) {
            action.getText(quantityExceedPopUpMsg, "QtyToMuchPopUp", test);
            action.explicitWait(5000);
            WebElement QtyToMuchPopUpOKButton = driver.findElement(By.xpath("//*[@class=\"modal-footer\"]/button"));
            action.click(QtyToMuchPopUpOKButton, "QtyToMuchPopUp", test);
        } else {
            throw new Exception("Pop Up is not displayed");
        }

    }

    public void ic_EnterTextToSearchBar(String productToFind, ExtentTest test) throws Exception {
        //try {
            ic_ElementVisable(searchBar);
            action.click(searchIcon, "Click on search", test);
            action.clear(searchBar, "SearchBar");
            action.writeText(searchBar, productToFind, "SearchBar", test);
            searchBar.sendKeys(Keys.ENTER);
            //action.click(searchIcon, "Click on search", test);
            action.waitForPageLoaded(40);            
        //} catch (Exception e) {
          //  e.printStackTrace();
            //logger.info(e.getMessage());
        //}
    }

    public boolean ic_ElementVisable(WebElement element) {
        return action.elementExists(element, 10);
    }

    public List<String> filterProducts(String allProductsToSearch) {
        String[] productsArray = allProductsToSearch.split("#");
        List<String> productsList = new ArrayList<String>(Arrays.asList(productsArray));
        return productsList;
    }

    public void spm_SelectProductAndAddToCart(ExtentTest test) throws Exception {
        String typeSearch = dataTable2.getValueOnCurrentModule("typeSearch");// input.get("typeSearch").get(rowNumber);
        String productsToSearch = dataTable2.getValueOnCurrentModule("specificProduct");// input.get("specificProduct").get(rowNumber);
        String quantityOfSearchProducts = dataTable2.getValueOnCurrentModule("Quantity");// input.get("Quantity").get(rowNumber);
        String waitTimeInSeconds = dataTable2.getValueOnCurrentModule("cartButtonWaitTimeInSeconds");// input.get("cartButtonWaitTimeInSeconds").get(rowNumber);
        String TypeOfOperation = dataTable2.getValueOnCurrentModule("TypeOfOperation");
        String validationRequired = dataTable2.getValueOnCurrentModule("validationRequired");
        List<String> theProducts = filterProducts(productsToSearch);

        Map<String, List<String>> productsInCart = evs_CreateCartFromProductListing(productsToSearch,
                quantityOfSearchProducts, typeSearch, waitTimeInSeconds, test);
        switch (TypeOfOperation) {
            case "Add_To_Wishlist":
                if (validationRequired.equalsIgnoreCase("Yes_Wishlist")) {
                  //  WishList.ValidateProductsIn_Wishlist(productsInCart, test);
                }
                break;
            case "Add_To_Cart":
               // cartValidation.iCcartVerification2(productsInCart, test);
                break;
            case "Validate_Out_Of_Stock":
                break;

        }
    }

    public void loadProductListingPage(String category, String product, ExtentTest test) throws IOException, Exception {
        switch (category) {
            case "SearchUsingSearchBar":
                ic_EnterTextToSearchBar(product, test);
                break;
            case "All Products":
                ic_ClickProductLink(test);
                break;
            default:
                boolean isPresent = action.waitUntilElementIsDisplayed(shopByDeptLink, 15);
                if (category.equalsIgnoreCase("Entertainment") | category.equalsIgnoreCase("Fashion")
                        | category.equalsIgnoreCase("Health & Beauty") | category.equalsIgnoreCase("Home")
                        | category.equalsIgnoreCase("Work & Study") | category.equalsIgnoreCase("Projects & DIY")
                        | category.equalsIgnoreCase("Lifestyle & Leisure") | category.equalsIgnoreCase("Fitness")
                        | category.equalsIgnoreCase("Kids World")) {

                    Map<String, String> productLinks = new HashMap<>();
                    productLinks.put("Entertainment", "icon__movie-projector");
                    productLinks.put("Fashion", "icon__t-shirt");
                    productLinks.put("Health & Beauty", "icon__heart-with-pulse");
                    productLinks.put("Home", "icon__home");
                    productLinks.put("Work & Study", "icon__laptop");
                    productLinks.put("Projects & DIY", "icon__wrench");
                    productLinks.put("Lifestyle & Leisure", "icon__road");
                    productLinks.put("Fitness", "icon__cycling");
                    productLinks.put("Cellular", "icon__iphone-x");
                    productLinks.put("Kids World", "icon__party-balloon");
                    for (Map.Entry map : productLinks.entrySet()) {
                        if (category.equalsIgnoreCase(map.getKey().toString())) {
                            WebElement linkFromNavBar = byCategoryFromMenuBar(map.getValue().toString());
                            action.click(linkFromNavBar, "Navigating to : " + map.getKey().toString(), test);
                            action.waitForPageLoaded(40);
                        }
                    }
                } else {
                    if (isPresent) {
                        action.click(shopByDeptLink, "Shop By Department", test);
                        WebElement typeOfSearch = byCategory(category);
                        action.scrollElemetnToCenterOfView(typeOfSearch, "typeOfSearch", test);
                        action.scrollElementIntoView(typeOfSearch);
                        action.click(typeOfSearch, typeOfSearch.getText(), test);
                        action.waitForPageLoaded(40);
                    }
                }
                break;
        }
        action.explicitWait(10000);
    }

    public WebElement byCategory(String nameOfCategory) {
        WebElement category = driver
                .findElement(By.xpath("//span[contains(text(),'" + nameOfCategory + "')]/parent::a"));
        return category;
    }

    public WebElement byCategoryFromMenuBar(String nameOfCategory) {
        WebElement category = driver.findElement(By.xpath("//*[@class ='" + nameOfCategory + "']/parent::li"));
        return category;
    }

    WebElement ic_FindProduct(WebElement elem) {
        return elem;
    }

    WebElement getCartButton(WebElement product) throws Exception {
        try {
            WebElement cartButton = product.findElement(By.xpath(".//parent::*/following-sibling::div[5]/div[2]/div/form/button"));
            return cartButton;
        } catch (Exception e) {
            throw new Exception("Add to cart button is not displayed for the Product. " + e.getMessage());
        }
    }

    void addToCart(WebElement addToCartButton, String waitTimeInSeconds, ExtentTest test) throws Exception {
        try {
            action.explicitWait(2000);
            action.click(addToCartButton, "Add To Cart", test);
           // cartValidation.cartButtonValidation(addToCartButton, Integer.parseInt(waitTimeInSeconds), test);
        } catch (Exception e) {
            throw new Exception("Unable to Add product to Cart. "+e.getMessage());
        }

    }

    public void addItemToCompare(WebElement productLink, int quanity, ExtentTest test) throws IOException, AWTException {
        if (quanity == 1) {
            WebElement compareLink = productLink.findElement(By.xpath(".//parent::strong/parent::div/div[@class='product-item-inner']/div[contains(@class,\"product actions\")]/div[@class='actions-secondary']/a[@title='Compare']/span"));
            action.explicitWait(2000);
            action.mouseover(productLink, "On Product");
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", compareLink);
            action.waitForPageLoaded(40);
        }
    }

    void addToCartFromProdDetailsPage(WebElement productLink, String waitTimeInSeconds, int quanity, ExtentTest test)
            throws Exception {
        if (quanity == 1) {
            WebElement prodC = productLink.findElement(By.xpath(".//parent::strong/parent::*/parent::*/a[1]"));
            action.javaScriptClick(prodC, "Navigate to product Details page", test);
            action.waitForPageLoaded(40);
        }
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
        boolean isPresent = driver.findElements(By.id("product-addtocart-button")).size() > 0;
        boolean availXpath1 = driver.findElements(By.xpath("//*[@title='Availability']/span")).size() > 0;
        boolean availXpath2 = driver.findElements(By.xpath("//*[@title='Availability:']/span")).size() > 0;
        String availabilityStatus = "";
        if (availXpath1) {
            availabilityStatus = action.getText(verifyAvailability1, "Availability Of Product", test);
        } else if (availXpath2) {
            availabilityStatus = action.getText(verifyAvailability, "Availability Of Product", test);
        }
        if (isPresent & availabilityStatus.equalsIgnoreCase("In stock")) {
            action.scrollElemetnToCenterOfView(productDetailsPageAddToCartButton, "productDetailsPageAddToCartButton",test);
            productDetailsPageAddToCartButton.click();
          //  cartValidation.cartButtonValidation(productDetailsPageAddToCartButton, Integer.parseInt(waitTimeInSeconds),test);
        } else {
            if (action.waitUntilElementIsDisplayed(notifyWhenProductIsAvailable, 15000)) {
                action.CompareResult("\"Notify Me When Available Is Present\"", "True", "True", test);
            }
            action.scrollElemetnToCenterOfView(productDetailsPageAddToCartButton, "productDetailsPageAddToCartButton", test);
            productDetailsPageAddToCartButton.click();
            action.waitForJStoLoad(60);
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("window.scrollTo(0,0)");

            if (action.elementExistWelcome(productOfStockErrorMessage, 20, "Out Of Stock Pop Up", test)) {
                action.CompareResult("Product Out Of Stock", "This product is out of stock.", driver.findElement(By.xpath("//div[contains(text(),'This product is out of stock.')]")).getText(), test);
            } else {
                throw new Exception("Out Of Stock Pop Up Is Not Displayed");
            }
        }

    }

    void addToWishlistFromProdDetailsPage(WebElement productLink, String waitTimeInSeconds, int quanity,
                                          ExtentTest test) throws Exception {

        if (quanity == 1) {
            WebElement prodC = productLink.findElement(By.xpath(".//parent::strong/parent::*/parent::*/a[1]"));
            action.click(prodC, "Navigate to product Details page", test);
            action.waitForPageLoaded(40);

        }

        if (!action.isElementPresent(loggedInIcon)) {

            action.clickEle(addToWishListButton, "Add to WishList Button", test);
            action.waitForJStoLoad(60);
            String msg = action.getText(loginMsg, "Login Message", test);
            action.CompareResult("User redirected to Login Page",
                    "You must login or register to add items to your wishlist.", msg, test);

        } else {
            action.javaScriptClick(wishListButton, "Wish list button", test);
            action.explicitWait(2000);
            addToWishList(test);
        }
    }


    public void addToWishList(ExtentTest test) throws IOException {

        if (action.isElementPresent(myWishListPopUp)) {
            if (action.isElementPresent(addToWishListCheckBox)) {
                action.clickEle(addToWishListCheckBox, "Adding item to Wish List", test);
                action.clickEle(addToWishLists, "Add to Wish List Pop up", test);
                action.waitForJStoLoad(40);
            }

        }

    }

    String findPrice(WebElement theProduct) {
        String price = theProduct.findElement(By.xpath("parent::*/following-sibling::*[1]/div/span/span/span"))
                .getText();
        return price;
    }

    public WebElement ic_FindProduct(ExtentTest test, String product) throws Exception {
        boolean status = true;
        while (status) {
            List<WebElement> allProducts = products;
            for (WebElement el : allProducts) {
                if (el.getText().trim().toLowerCase().equalsIgnoreCase(product.trim())) {
                    status = false;
                    action.mouseover(el, "On Product");
                    return el;
                }
            }
            WebElement nextButton = returnNext();
            if (nextButton != null) {
                clickNext(test);
                action.explicitWait(5000);
            } else {
                status = false;
                action.CompareResult("Product Not Found or Out of Stock", product, "", test);
                throw new Exception("Product Not Found or Out of Stock ");
            }
        }
        return null;
    }


    public static Map<String, List<String>> productData;

    Map<String, List<String>> evs_CreateCartFromProductListing(String productsList, String quantityOfProducts,
                                                               String searchCategory, String waitTimeInSeconds, ExtentTest test) throws Exception {
        productData = new LinkedHashMap<>();
        String cartAdditionMethod = dataTable2.getValueOnCurrentModule("CartAdditionMethod");
        String TypeOfOperation = dataTable2.getValueOnCurrentModule("TypeOfOperation");
        try {
            List<String> theProducts = filterProducts(productsList);
            List<String> quantity = filterProducts(quantityOfProducts);
            for (int s = 0; s < theProducts.size(); s++) {
                List<String> productPrice_Quantity_SKU = new ArrayList<>();
                loadProductListingPage(searchCategory, theProducts.get(s), test);// change
                WebElement prod = ic_FindProduct(test, theProducts.get(s));
                String productName = "";
                int set = 0;
                int quantityExecu = 0;
                if (prod != null) {
                    productName = prod.getText();
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
                                            break;
                                        case "Get_SKU_Code":
                                            // validateProductSKU.displayProductSKU(test,
                                            // prod);
                                            break;
                                        case "Add_To_Compare":
                                            addItemToCompare(prod, quantityExecu, test);
                                          //  compareProducts.comparePageValidation(productName, test);
                                            break;
                                        case "Validate_Out_Of_Stock":
                                            addToCartFromProdDetailsPage(prod, waitTimeInSeconds, quantityExecu, test);
                                            break;
                                        case "Validate_Range":
                                        	rangeValidation(test);
                                            break;
                                    }
                                }
                                if (set <= 0) {
                                    set++;
                                    productPrice_Quantity_SKU.add(quantity.get(o));
                                }
                            }
                        }
                    }
                    if (!((TypeOfOperation.equalsIgnoreCase("Add_To_Wishlist") | TypeOfOperation.equalsIgnoreCase("Add_To_Compare")))) {
                        String skuCode = getSKUCode(cartAdditionMethod, prod, test);

                        productPrice_Quantity_SKU.add(skuCode);
                        String productPrice = productFinalPrice.getText();
                        productPrice_Quantity_SKU.add(productPrice);
                    }
                }
                productData.put(productName, productPrice_Quantity_SKU);
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
            e.printStackTrace();
            throw e;
        }
        return productData;

    }

    String getSKUCode(String cartAdditionType, WebElement productLink, ExtentTest test) throws Exception {
        if (cartAdditionType.equalsIgnoreCase("ProductListingPage")) {
            WebElement prodC = productLink.findElement(By.xpath(".//parent::strong/parent::*/parent::*/a[1]"));
            action.javaScriptClick(prodC, "Navigate to product Details page to Retrieve SKU", test);
            // Navigate to the cart detail page
            if (skuCode.getText() != null | skuCode.getText() != "") {
                return skuCode.getText();
            }
        } else {
            if (skuCode.getText() != null | skuCode.getText() != "") {
                return skuCode.getText();
            }
        }
        return "NA";
    }
    
    public void rangeValidation(ExtentTest test) throws IOException {
    	String prodName = dataTable2.getValueOnOtherModule("SPM_ArticleRanges", "ProductName", 0);
    	String lengthType = dataTable2.getValueOnOtherModule("SPM_ArticleRanges", "Length_Type", 0);
    	String beddingType = dataTable2.getValueOnOtherModule("SPM_ArticleRanges", "Bedding_Type", 0);
    	String beddingWidth = dataTable2.getValueOnOtherModule("SPM_ArticleRanges", "Width", 0);
    	String beddingBreadth = dataTable2.getValueOnOtherModule("SPM_ArticleRanges", "breadthFront", 0);
    	String beddingLength = dataTable2.getValueOnOtherModule("SPM_ArticleRanges", "Length", 0);
    	
    	action.CompareResult("The Product Name Is Visible", prodName.trim(), bed_prodName.getText().trim(), test);
    		
    switch(beddingWidth){
    	case "92":
    			action.scrollElemetnToCenterOfView(bed_type92, "Scroll To View The Width", test);
    			action.CompareResult("The Mattress Width Size",beddingBreadth.trim(),bed_type92.getText().replace("width: 91cm", "").trim(), test);
    			String frontWidth = bed_type92.getText().replace("width: 91cm", "").trim();
    			dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Width", frontWidth, 0);
    			if(beddingType.equalsIgnoreCase("Bed Set (Mattress + Base)")){
    				action.isEnabled(bed_bedSet);
    				action.CompareResult("The Bedding Type",beddingType.trim() , bed_bedSet.getText().trim(), test);
    					if(lengthType.equalsIgnoreCase("Standard")){
    						action.CompareResult("The Bed Set Standard Length Size",beddingLength.trim() , bed_lengthMattress.getText(), test);
    						String frontLength = bed_lengthMattress.getText().trim();
    						dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
    					}else{
    					action.CompareResult("The Bed Set Extra Length Size", beddingLength.trim(), bed_exraLength.getText(), test);
    					String frontLength = bed_lengthMattress.getText().trim();
						dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
    					}
    				}
    			else if(beddingType.equalsIgnoreCase("Mattress Only")){
    				action.isEnabled(bed_bedSet);
    				action.CompareResult("The The Bedding Type", beddingType.trim(), bed_bedMattress.getText().trim(), test);
    				if(lengthType.equalsIgnoreCase("Standard")){
    					action.CompareResult("The Mattress Standard Length Size", beddingLength.trim(), bed_lengthMattress.getText(), test);
    					String frontLength = bed_lengthMattress.getText().trim();
						dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
    				}else {
    					action.CompareResult("The Mattress Extra Length Size", beddingLength.trim(), bed_exraLength.getText(), test);
    					String frontLength = bed_lengthMattress.getText().trim();
						dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
    				}
    			}
    		break;
    	case "107":
    		action.scrollElemetnToCenterOfView(bed_type107CM, "Scroll To View The Width", test);
			action.CompareResult("The Mattress Width Size", beddingBreadth.trim(), bed_type107CM.getText().trim(), test);
			String frontWidth107 = bed_type107CM.getText().trim();
			dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Width", frontWidth107, 0);
			if(beddingType.equalsIgnoreCase("Bed Set (Mattress + Base)")){
				action.isEnabled(bed_bedSet);
				action.CompareResult("The Bedding Type", beddingType.trim(),bed_bedSet.getText().trim(), test);
					if(lengthType.equalsIgnoreCase("Standard")){
						action.CompareResult("The Bed Set Standard Length Size", beddingLength.trim(), bed_lengthMattress.getText(), test);
						String frontLength = bed_lengthMattress.getText().trim();
						dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
					}else{
						action.CompareResult("The Bed Set Extra Length Size",beddingLength.trim(), bed_exraLength.getText(), test);
    					String frontLength = bed_lengthMattress.getText().trim();
						dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
    				}
				}
			else if(beddingType.equalsIgnoreCase("Mattress Only")){
				action.isEnabled(bed_bedSet);
				action.CompareResult("The Bedding Type", beddingType.trim(),bed_bedMattress.getText().trim(), test);
					if(lengthType.equalsIgnoreCase("Standard")){
						action.CompareResult("The Mattress Standard Length Size", beddingLength.trim(), bed_lengthMattress.getText(), test);
						String frontLength = bed_lengthMattress.getText().trim();
						dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
					}else{
						action.CompareResult("The Mattress Extra Length Size",beddingLength.trim(), bed_exraLength.getText(), test);
    					String frontLength = bed_lengthMattress.getText().trim();
						dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
    				}
			}
    		break;
    	case "137":
    		action.scrollElemetnToCenterOfView(bed_type137CM, "Scroll To View The Width", test);
			action.CompareResult("The Mattress Width Size", beddingBreadth.trim(), bed_type137CM.getText().trim(), test);
			String frontWidth137 = bed_type137CM.getText().trim();
			dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Width", frontWidth137, 0);
			if(beddingType.equalsIgnoreCase("Bed Set (Mattress + Base)")){
				action.isEnabled(bed_bedSet);
				action.CompareResult("The Bedding Type", beddingType.trim(),bed_bedSet.getText().trim(), test);
					if(lengthType.equalsIgnoreCase("Standard")){
						action.CompareResult("The Bed Set Standard Length Size", beddingLength.trim(), bed_lengthMattress.getText(), test);
						String frontLength = bed_lengthMattress.getText().trim();
						dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
					}else{
						action.CompareResult("The Bed Set Extra Length Size",beddingLength.trim(), bed_exraLength.getText(), test);
    					String frontLength = bed_lengthMattress.getText().trim();
						dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
					}
				}
			else if(beddingType.equalsIgnoreCase("Mattress Only")){
				action.isEnabled(bed_bedSet);
				action.CompareResult("The Bedding Type", beddingType.trim(),bed_bedMattress.getText().trim(), test);
				if(lengthType.equalsIgnoreCase("Standard")){
					action.CompareResult("The Mattress Standard Length Size", beddingLength.trim(), bed_lengthMattress.getText(), test);
					String frontLength = bed_lengthMattress.getText().trim();
					dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
				}else {
					action.CompareResult("The Mattress Extra Length Size",beddingLength.trim(), bed_exraLength.getText(), test);
					String frontLength = bed_lengthMattress.getText().trim();
					dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
				}
			}
    		break;	
    	case "152":
    		action.scrollElemetnToCenterOfView(bed_type152CM, "Scroll To View The Width", test);
			action.CompareResult("The Mattress Width Size", beddingBreadth.trim(), bed_type152CM.getText().trim(), test);
			String frontWidth152 = bed_type152CM.getText().trim();
			dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Width", frontWidth152, 0);
			if(beddingType.equalsIgnoreCase("Bed Set (Mattress + Base)")){
				action.isEnabled(bed_bedSet);
				action.CompareResult("The Bedding Type", beddingType.trim(),bed_bedSet.getText().trim(), test);
					if(lengthType.equalsIgnoreCase("Standard")){
						action.CompareResult("The Bed Set Standard Length Size", beddingLength.trim(), bed_lengthMattress.getText(), test);
						String frontLength = bed_lengthMattress.getText().trim();
						dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
					}else{
						action.CompareResult("The Bed Set Extra Length Size",beddingLength.trim(), bed_exraLength.getText(), test);
    					String frontLength = bed_lengthMattress.getText().trim();
						dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
					}
				}
			else if(beddingType.equalsIgnoreCase("Mattress Only")){
				action.isEnabled(bed_bedSet);
				action.CompareResult("The Bedding Type", beddingType.trim(),bed_bedMattress.getText().trim(), test);
				if(lengthType.equalsIgnoreCase("Standard")){
					action.CompareResult("The Mattress Standard Length Size", beddingLength.trim(), bed_lengthMattress.getText(), test);
					String frontLength = bed_lengthMattress.getText().trim();
					dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
				}else {
					action.CompareResult("The Mattress Extra Length Size",beddingLength.trim(), bed_exraLength.getText(), test);
					String frontLength = bed_lengthMattress.getText().trim();
					dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
				}
			}
    		break;
    	case "183":
    		action.scrollElemetnToCenterOfView(bed_type183CM, "Scroll To View The Width", test);
			action.CompareResult("The Mattress Width Size", beddingBreadth.trim(), bed_type183CM.getText().trim(), test);
			String frontWidth183 = bed_type183CM.getText().trim();
			dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Width", frontWidth183, 0);
			if(beddingType.equalsIgnoreCase("Bed Set (Mattress + Base)")){
				action.isEnabled(bed_bedSet);
				action.CompareResult("The Bedding Type", beddingType.trim(),bed_bedSet.getText().trim(), test);
					if(lengthType.equalsIgnoreCase("Standard")){
						action.CompareResult("The Bed Set Standard Length Size", beddingLength.trim(), bed_lengthMattress.getText(), test);
						String frontLength = bed_lengthMattress.getText().trim();
						dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
					}else{
						action.CompareResult("The Bed Set Extra Length Size",beddingLength.trim(), bed_exraLength.getText(), test);
    					String frontLength = bed_lengthMattress.getText().trim();
						dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
					}
				}
			else if(beddingType.equalsIgnoreCase("Mattress Only")){
				action.isEnabled(bed_bedSet);
				action.CompareResult("The Bedding Type", beddingType.trim(),bed_bedMattress.getText().trim(), test);
				if(lengthType.equalsIgnoreCase("Standard")){
					action.CompareResult("The Mattress Standard Length Size", beddingLength.trim(), bed_lengthMattress.getText(), test);
					String frontLength = bed_lengthMattress.getText().trim();
					dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
				}else {
					action.CompareResult("The Mattress Extra Length Size",beddingLength.trim(), bed_exraLength.getText(), test);
					String frontLength = bed_lengthMattress.getText().trim();
					dataTable2.setValueOnOtherModule("SPM_ArticleRanges", "FrontEnd_Length", frontLength, 0);
				}
			}
    		break;	

    		}	
    	}
    	
    public void rangeSearch(ExtentTest test) throws IOException, Exception {
    	String searchTY = dataTable2.getValueOnOtherModule("SPM_ProductSearch", "typeSearch", 0);
    	String rangeSKU = dataTable2.getValueOnOtherModule("SPM_ProductSearch", "specificProduct", 0);
    	String prodName = dataTable2.getValueOnOtherModule("SPM_ArticleRanges", "ProductName", 0);
    	loadProductListingPage(searchTY, rangeSKU, test);
    	WebElement prod = ic_FindProduct(test, prodName);
    	action.click(prod, "Product detail page", test);
    	action.waitForPageLoaded(10);
    }
    
    
    

}
