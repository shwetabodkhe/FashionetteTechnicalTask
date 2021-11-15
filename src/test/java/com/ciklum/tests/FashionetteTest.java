package com.ciklum.tests;

import com.ciklum.core.AppConstants;
import com.ciklum.core.BaseTest;
import com.ciklum.core.JasonDataProviders;
import com.ciklum.pages.*;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Map;

public class FashionetteTest extends BaseTest {

    @Test(dataProvider = "DataProvider", dataProviderClass = JasonDataProviders.class)
    public void addProductToCartAndLogin(Map<String, String> testData){
        FashionetteHomePage fashionetteHomePage= new FashionetteHomePage(driver());
        fashionetteHomePage.acceptCookies();
        fashionetteHomePage.searchProduct(testData.get("productName"))
                .addSearchedItemToCart(testData);

        FashionetteLoginPage fashionetteLoginPage= new FashionetteLoginPage(driver());
        fashionetteLoginPage.VerifySuccessfulLogin(AppConstants.EMAIL_ID, AppConstants.PASSWORD);

        fashionetteHomePage.clickOnCartIcon();
        FashionetteCartPage fashionetteCartPage= new FashionetteCartPage(driver());
        fashionetteCartPage.VerifyCart(testData);
    }


    @Test(dataProvider = "DataProvider", dataProviderClass = JasonDataProviders.class)
    public void modifyUserInformation(Map<String, String> testData){
        FashionetteHomePage fashionetteHomePage= new FashionetteHomePage(driver());
        fashionetteHomePage.acceptCookies();
        FashionetteLoginPage fashionetteLoginPage= new FashionetteLoginPage(driver());
        fashionetteLoginPage.VerifySuccessfulLogin(AppConstants.EMAIL_ID, AppConstants.PASSWORD);
        FashionetteCustomerProfilePage fashionetteCustomerDeatils = new FashionetteCustomerProfilePage(driver());
        fashionetteCustomerDeatils.modifyUserDetails(testData.get("firstName"), testData.get("lastName"));
        fashionetteCustomerDeatils.verifyModifiedCustomerName(testData.get("firstName"), testData.get("lastName"));
    }

    @Test(dataProvider = "DataProvider", dataProviderClass = JasonDataProviders.class)
    public void applyVoucherAndVerify(Map<String, String> testData){
        FashionetteHomePage fashionetteHomePage= new FashionetteHomePage(driver());
        fashionetteHomePage.acceptCookies();
        fashionetteHomePage.searchProduct(testData.get("productName"))
                .addSearchedItemToCart(testData);
        fashionetteHomePage.clickOnCartIcon();
        FashionetteCartPage fashionetteCartPage= new FashionetteCartPage(driver());
        fashionetteCartPage.verifyVoucherAppliedSuccessfully(testData.get("voucherCode"));
    }
}
