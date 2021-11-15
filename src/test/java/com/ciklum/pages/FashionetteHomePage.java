package com.ciklum.pages;

import com.aventstack.extentreports.service.ExtentTestManager;
import com.ciklum.core.BasePage;
import com.ciklum.core.ScreenshotManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class FashionetteHomePage extends BasePage {

    public static String btnAcceptCookies="//button[@id='uc-btn-accept-banner']";
    public static String lnkSearch = "//a[@title='Search']";
    public static String txtSearchBox ="//input[@type='search']";
    public static String lnkCartIcon ="//a[@class='header__cart-icon']";
    public FashionetteHomePage(WebDriver driver) {
        super(driver);
    }

    public void acceptCookies()
    {
        click(By.xpath(btnAcceptCookies));
    }
    public void clickOnSearchIcon(){
        click(By.xpath(lnkSearch));
    }

    public FashionetteSearchResultPage searchProduct(String productName){
        clickOnSearchIcon();
        enterValue(By.xpath(txtSearchBox),productName);
        ScreenshotManager.takeScreenshot(driver,"itemToSearch","Product name entered for search");
        driver.findElement(By.xpath(txtSearchBox)).sendKeys(Keys.ENTER);
        ExtentTestManager.getTest().info("Product name entered for search and click on enter");
        return new FashionetteSearchResultPage(driver);
    }
    public void clickOnCartIcon(){
        click(By.xpath(lnkCartIcon));
        ExtentTestManager.getTest().info("Clicked on the cart Icon to Open the Cart");
    }

}


