package com.ciklum.pages;

import com.ciklum.core.BasePage;
import com.ciklum.core.ScreenshotManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class FashionetteCustomerProfilePage extends BasePage {

    public static String lnkPersonalData ="//div[@class='account-personaldata']//a[@data-component-name='accordion']";
    public static String lnkEdit ="//div[contains(text(),'Edit')]";
    public static String txtFirstName ="//input[@name='firstName']";
    public static String txtLastName ="//input[@name='lastName']";
    public static String lnkSave ="//div[contains(text(),'Save')]";
    public static String txtCustomerName ="//div[contains(text(),'Mr  %s %s')]";


    public FashionetteCustomerProfilePage(WebDriver driver) {
        super(driver);
    }

    public void clickOnPersonalData(){
        click(By.xpath(lnkPersonalData));
    }
    public void clickOnEditPersonalData(){
        click(By.xpath(lnkEdit));
    }
    public void enterFirstName(String firstName){
        enterValue(By.xpath(txtFirstName),firstName);
    }
    public void enterLastName(String lastName){
        enterValue(By.xpath(txtLastName),lastName);
    }
    public void clickOnSave(){
        click(By.xpath(lnkSave));
    }

    public void modifyUserDetails(String firstName,String lastName){
        clickOnPersonalData();
        clickOnEditPersonalData();
        enterFirstName(firstName);
        enterLastName(lastName);
        ScreenshotManager.takeScreenshot(driver,"newCustomerName","New first and last name entered.");
        clickOnSave();
    }

    public void verifyModifiedCustomerName(String firstName,String lastName){
        Assert.assertTrue(driver.findElement(By.xpath(String.format(txtCustomerName, firstName,lastName))).isDisplayed(),"Customer details are not modified and saved successfully");
        ScreenshotManager.takeScreenshot(driver,"modifiedDetails","Modified saved details");
    }
}
