package com.training.testcases.TasksPage;

import com.training.pages.LoginPage;
import com.training.pages.TasksPage;
import com.training.reporting.ExtentTestManager;
import com.training.testcases.BaseTest;
import com.training.utils.CommonUtil;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.training.support.BrowserFactory.getDriver;
import static com.training.testcases.BaseTest.configurationDetails;

public class CharacterExceedsLimit extends BaseTest {
    @Test
    public void characterExceedsLimit() throws Exception {

        //Map<String, String> testDetails = xlsFile.getExcelRowValuesIntoMapBasedOnKey("sampleSheet", TESTCASENAME);

        //Login to Application

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginToApplication(configurationDetails.getUserName(), configurationDetails.getPassword());
        ExtentTestManager.getTest().pass("Logged in to application");

        // Navigate to Application
        TasksPage tasksPage = new TasksPage(getDriver());
        tasksPage.navigateToTask();
        ExtentTestManager.getTest().pass("Navigated To Task Home Page");
        tasksPage.clickOnCreateButton();
        //Enter Mandatory fields with exceeding limit
        HashMap<String, String> objTaskDetails = new HashMap<>();
        String sTaskTitle = CommonUtil.getRandomString("Quality", 252);
        objTaskDetails.put("Title", sTaskTitle);
        tasksPage.enterTaskDetails(objTaskDetails);
        tasksPage.saveTask();
        tasksPage.errorMessagesBoundary("Title is longer than 250 characters");
        ExtentTestManager.getTest().pass("The error message is displayed successfully :: Title is longer than 250 characters");

    }

}
