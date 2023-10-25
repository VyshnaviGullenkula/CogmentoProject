package com.training.pages;

import com.training.constants.ApplicationConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LoginPage  extends BasePage {
    private static Logger logger = LoggerFactory.getLogger(LoginPage.class);

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    private final By txtUserEmailID= By.name("email");
    private final By txtUserPassword = By.name("password");
    private final By btnLogin = By.xpath("//div[text()='Login']");
    private final By lnkHome = By.xpath("//i[@class='home icon']");
    private final String settingsIcon = "//div[@class='ui basic button floating item dropdown']";
    private final String logOutIcon = "//span[text()='Log Out']";
    private final String errorMessage = "//div[@class='ui error floating icon message']";
    private final String errorMessageCloseButton = "//i[@class='close icon']";

    public LoginPage loginToApplication(String sUserName, String sPassword) throws InterruptedException {
        scriptAction.waitUntilElementIsVisible(txtUserEmailID, ApplicationConstants.MEDIUM_TIMEOUT, "Username field is not displayed.");
        //enter username
        scriptAction.inputText(txtUserEmailID, sUserName);
        //enter password
        scriptAction.inputText(txtUserPassword, sPassword);
        //click login
        scriptAction.clickElement(btnLogin);
        //wait until Home page is displayed
        boolean verifyHomeIcon = scriptAction.isElementVisible(lnkHome,ApplicationConstants.SHORT_TIMEOUT);
        boolean verifyErrorMessage = scriptAction.isElementVisible(By.xpath(errorMessage),ApplicationConstants.SHORT_TIMEOUT);
        if (!verifyHomeIcon){
            if (verifyErrorMessage==true) {
                scriptAction.clickElement(By.xpath(errorMessageCloseButton));
                scriptAction.clickElement(btnLogin);
            }
        }
        scriptAction.waitUntilElementIsVisible(lnkHome,ApplicationConstants.LONG_TIMEOUT,"Home page is not displayed after login. Check your login details.");
        return this;
    }
    public void logOut() {
        scriptAction.clickElement(By.xpath(settingsIcon));
        scriptAction.waitTillClickableAndClick(By.xpath(logOutIcon), ApplicationConstants.MEDIUM_TIMEOUT);
    }
}
