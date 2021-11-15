package com.ciklum.core;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Common base class for all page classes.
 * It is going to have all common methods required in the page classes.
 */
public class BasePage {

    public WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver=driver;
    }

    public void click(By locator){
        driver.findElement(locator).click();
    }

    public void enterValue(By locator, String value){
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
    }
}

