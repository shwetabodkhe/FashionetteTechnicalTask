package com.ciklum.core;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.service.ExtentTestManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotManager {


    public static void takeScreenshot(WebDriver driver, String fileName, String message){
        if (driver != null) {
            String newFileName = fileName + new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File destFile = new File(  AppConstants.HTML_REPORT_PATH+"/screenshots/" + newFileName + ".png");
            File src = takesScreenshot.getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(src, destFile);
                String imageRelativePath = "./screenshots/" + destFile.getName();
                ExtentTestManager.getTest().info(message, MediaEntityBuilder.createScreenCaptureFromPath(imageRelativePath).build());
            } catch (IOException e) {
            }
        }


    }


}
