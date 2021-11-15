package com.ciklum.core;

import com.aventstack.extentreports.testng.listener.ExtentITestListenerAdapter;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

import java.util.concurrent.TimeUnit;

/**
 * It contains all common test configuration methods.
 */

@Listeners(ExtentITestListenerAdapter.class)
public class BaseTest {

    private WebDriver driverInstance;
    private WebDriverManager webDriverManager;
    @BeforeMethod
    public void beforeMethod(){
        initializeBrowser();
        driver().navigate().to(AppConstants.APPLICATION_URL);
        driver().manage().window().maximize();
        driver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        System.out.println("Driver created");
    }

    @AfterMethod
    public void afterMethod(ITestResult result) {

        if(result.getStatus() == ITestResult.FAILURE){
            ScreenshotManager.takeScreenshot(driver(), "Failed" + result.getTestName(), "Screenshot after Failure...!!!");
        }
        driver().quit();
    }

    public void initializeBrowser()
    {
        WebDriver driver;
        if (AppConstants.BROWSER.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
        }
        else if(AppConstants.BROWSER.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver= new FirefoxDriver();
        }
        else{
            WebDriverManager.chromedriver().setup();
            driver= new ChromeDriver();
        }
        driverInstance = driver;
    }

    public WebDriver driver(){
        return driverInstance;
    }
}
