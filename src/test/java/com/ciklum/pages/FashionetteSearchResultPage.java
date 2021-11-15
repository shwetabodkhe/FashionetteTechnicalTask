package com.ciklum.pages;

import com.aventstack.extentreports.service.ExtentTestManager;
import com.ciklum.core.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.List;
import java.util.Map;

public class FashionetteSearchResultPage extends BasePage {

    public static String txtResult = "//div[@class='listing-page__product-count']/span";
    public static String lnkResultItems = "//a[@itemprop='itemListElement']";
    public static String addToCart ="//form//div[@class='btn__content'][contains(text(),'Add to cart')]//span";
    public static String labelAlreadyInCart = "//form//div[@class='btn__content'][contains(text(),'Already in Cart')]//span";
    public static String finalProductPrice ="//div[@class='product-details__description__price--special font-size--h1 text__weight--semi-bold']";


    public FashionetteSearchResultPage(WebDriver driver) {
        super(driver);
    }

    public void addSearchedItemToCart(Map<String,String> testData){
        if(driver.findElements(By.xpath(txtResult)).size() != 0) {
            String countOfResult = driver.findElement(By.xpath(txtResult)).getText();
            if (Integer.parseInt(countOfResult) > 0) {
                List<WebElement> totalFoundItems = driver.findElements(By.xpath(lnkResultItems));
                totalFoundItems.get(0).click();

                //Storing the product details for further verification
                storeProductDetails(testData);

                click(By.xpath(addToCart));
                Assert.assertTrue(driver.findElement(By.xpath(labelAlreadyInCart)).isDisplayed());
                ExtentTestManager.getTest().info("Product added to the cart successfully");
            }
            else {
                Assert.fail("Product is out of stock");
            }
        }
        else {
            Assert.fail("We’re sorry, but we have no products matching your search.\n" +
                    "Please try again with different keywords.");
        }
    }

    public void storeProductDetails(Map<String,String> testData){
        String finalPrice = driver.findElement(By.xpath(finalProductPrice)).getText();
        testData.put("finalProductPrice",finalPrice);
    }
}
