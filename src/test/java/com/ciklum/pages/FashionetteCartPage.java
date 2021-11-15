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

import java.text.DecimalFormat;
import java.util.Map;

public class FashionetteCartPage  extends BasePage {

    public static String cartPageIcon ="//a[@href='https://www.fashionette.co.uk/cart']";
    public static String txtBrandName="//div[@class='cart-item--brand']";
    public static String txtOldPrice ="//div[@class='cart-item--price']//span[@class='text']";
    public static String txtSpecialPrice ="//div[@class='cart-item--price']//span[@class='product__price--special']/span";
    public static String txtCartTotal ="//td[@id='cart__total']";
    public static String btnContinueToAddress ="//button[@id='checkout-start']";
    public static String labelSubtotalAmount ="//th[contains(text(),'Subtotal')]//following-sibling::td";
    public static String labelVoucher= "//th[contains(text(),'Voucher')]";
    public static String lnkRedeem= "//a[contains(text(),'redeem')]";
    public static String txtVoucherCode= "//input[@name='voucherCode']";
    public static String btnRedeem= "//button[contains(text(),'redeem')]";
    public static String iconCross= "//th[contains(text(),'Voucher')]//i[@class='icon icon--inline icon--cross']";
    public static String labelVoucherAmount= "//th[contains(text(),'Voucher')]//following-sibling::td//span";


    public FashionetteCartPage(WebDriver driver) {
        super(driver);
    }

    public void veriftCartPageIsOpened(){
        WebDriverWait wait =new WebDriverWait(driver, 5);
        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath(cartPageIcon)));
        Assert.assertTrue(driver.findElement(By.xpath(cartPageIcon)).isDisplayed());
        ScreenshotManager.takeScreenshot(driver,"CartPage","Cart page is displayed");
    }

    public void verifyProductName(String expectedBrandName){
        WebDriverWait wait =new WebDriverWait(driver, 5);
        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.elementToBeClickable(By.xpath(txtBrandName)));
        String actualBrandName = driver.findElement(By.xpath(txtBrandName)).getText();
        Assert.assertEquals(actualBrandName,expectedBrandName,"Brand name is not matching with expected one");
        ExtentTestManager.getTest().info("Brand name is as expected");
    }
    public void verifyProductPrice(String finalProductPrice){
        if(driver.findElements(By.xpath(txtSpecialPrice)).size() != 0){
        String displayedSpecialPrice = driver.findElement(By.xpath(txtSpecialPrice)).getText();
        Assert.assertEquals(displayedSpecialPrice,finalProductPrice,"Displayed price is not matching with expected  price");
        ExtentTestManager.getTest().info("Displayed Product price is as expected");
        }
        else{
            String displayedPrice = driver.findElement(By.xpath(txtOldPrice)).getText();
            Assert.assertEquals(displayedPrice,finalProductPrice,"Displayed price is not maching with expected price");
            ExtentTestManager.getTest().info("Displayed Product price is as expected");
        }
    }

    public void verifyContinueToAddressButton(){
        Assert.assertFalse(driver.findElement(By.xpath(btnContinueToAddress)).isDisplayed(),"Button is not available on screen");
        ExtentTestManager.getTest().info("Continue to shipping address button is available on screen");
    }
    public void VerifyCart(Map<String ,String> testData ){
        veriftCartPageIsOpened();
        verifyProductName(testData.get("productName"));
        verifyProductPrice(testData.get("finalProductPrice"));
        verifyContinueToAddressButton();
    }

    public void enterVoucherCode(String voucherCode){
        if(driver.findElement(By.xpath(labelVoucher)).isDisplayed()){
            click(By.xpath(lnkRedeem));
            enterValue(By.xpath(txtVoucherCode),voucherCode);
            ExtentTestManager.getTest().info("Voucher code entered");
            click(By.xpath(btnRedeem));
        }
    }
    public void verifyVoucherAppliedSuccessfully(String voucherCode) {
        enterVoucherCode(voucherCode);
        WebDriverWait wait =new WebDriverWait(driver, 5);
        wait.ignoring(StaleElementReferenceException.class).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(txtVoucherCode)));
        if (driver.findElements(By.xpath(iconCross)).size() != 0) {
            String expectedTotalAmount= calculateExpectedTotalAmount();
            String displayedTotalAmount = getDisplayedTotalAmount();
            Assert.assertEquals(displayedTotalAmount, expectedTotalAmount,"Amount by adding voucher is not matching with expected amount");

            ScreenshotManager.takeScreenshot(driver,"voucherCheck","voucher applied successfully");
            ExtentTestManager.getTest().info("Voucher applied successfully");
        }
    }

    public String calculateExpectedTotalAmount(){
        /*Taking the subtotal amount and minus the voucher amount to get the expected amount*/
        String subtotalAmount = driver.findElement(By.xpath(labelSubtotalAmount)).getText().replaceAll("[^0-9.]", "");
        String voucherAmount = driver.findElement(By.xpath(labelVoucherAmount)).getText().replaceAll("[^0-9.]", "");

        double totalAmount = Double.parseDouble(subtotalAmount) - Double.parseDouble(voucherAmount);
        String expectedTotalAmount = "£" + decimalNumberFormatter(totalAmount);
        return expectedTotalAmount;
    }

    public String getDisplayedTotalAmount(){
        /* Take the total amount displayed on screen
         * and compare with calculated expected amount */
        String amount = driver.findElement(By.xpath(txtCartTotal)).getText().replaceAll("[^0-9.]", "");
        double totalAmount = decimalNumberFormatter(Double.parseDouble(amount));
        String displayedTotalAmount = "£" + totalAmount;
        return displayedTotalAmount;
    }
    public double decimalNumberFormatter(double numberToFormat) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        decimalFormat.format(numberToFormat);
        return numberToFormat;
    }

}

