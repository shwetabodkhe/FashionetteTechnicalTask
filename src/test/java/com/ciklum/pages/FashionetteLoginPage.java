package com.ciklum.pages;

import com.aventstack.extentreports.service.ExtentTestManager;
import com.ciklum.core.BasePage;
import com.ciklum.core.ScreenshotManager;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class FashionetteLoginPage extends BasePage {

    public static String lnkLogin ="//a[@data-id='user login']";
    public static String txtEmail ="//input[@name='email']";
    public static String txtPassword ="//input[@name='password']";
    public static String btnLogin ="//button[contains(text(),'Login')]";
    public static String labelWelcome ="//div[@class='account__welcome text__center font-size--hero']";


    public FashionetteLoginPage(WebDriver driver) {
        super(driver);
    }

    public void clickOnLoginIcon(){
        click(By.xpath(lnkLogin));
        ExtentTestManager.getTest().info("Clicked on Login button to login");
    }
    public void enterEmailID(String emailID){
        WebDriverWait wait =new WebDriverWait(driver, 5);
        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath(txtEmail)));
        enterValue(By.xpath(txtEmail),emailID);
        ExtentTestManager.getTest().info("Email ID entered");
    }
    public void enterPassword(String password){
        enterValue(By.xpath(txtPassword),password);
        ExtentTestManager.getTest().info("Password entered");
    }
    public void clickOnLoginBtn(){
        click(By.xpath(btnLogin));
    }


    public void VerifySuccessfulLogin(String emailID, String password){
        clickOnLoginIcon();
        enterEmailID(emailID);
        enterPassword(password);
        ScreenshotManager.takeScreenshot(driver,"loginDetails","Login details entered");
        clickOnLoginBtn();
        String welcomeText = driver.findElement(By.xpath(labelWelcome)).getText();
        if(welcomeText.contains("welcome to your account.")){
            ExtentTestManager.getTest().info("User logged in successfully");
        }
        else {
            ScreenshotManager.takeScreenshot(driver,"logInFailed","User log in failed");
            Assert.fail("User log in failed");
        }
    }



}
