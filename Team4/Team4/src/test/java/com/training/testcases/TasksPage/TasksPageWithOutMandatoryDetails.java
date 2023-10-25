package com.training.testcases.TasksPage;

import com.training.pages.BasePage;
import com.training.pages.LoginPage;
import com.training.pages.TasksPage;
import com.training.reporting.ExtentTestManager;
import com.training.testcases.BaseTest;
import com.training.utils.CommonUtil;
import org.testng.annotations.Test;

import java.util.HashMap;

public class TasksPageWithOutMandatoryDetails extends BaseTest {
   @Test
   public void taskWithoutMandatoryDetails() throws Exception {


        //Map<String, String> testDetails = xlsFile.getExcelRowValuesIntoMapBasedOnKey("sampleSheet", TESTCASENAME);

        //Login to Application
       LoginPage loginPage = new LoginPage(getDriver());
       loginPage.loginToApplication(configurationDetails.getUserName(), configurationDetails.getPassword());
       ExtentTestManager.getTest().pass("Logged in to application");

       //Navigate to Application
       TasksPage tasksPage = new TasksPage(getDriver());
       tasksPage.navigateToTask();
       ExtentTestManager.getTest().pass("Navigated To Task Home Page");

       //Enter Details without Mandatory Fields
        HashMap<String, String> objData = new HashMap<>();
        objData.put("Title", "");
        tasksPage.clickOnCreateButton();
        tasksPage.enterTaskDetails(objData);
        tasksPage.saveTask();
        tasksPage.errorMessage("The field Title is required.");
        ExtentTestManager.getTest().pass("The error message is displayed successfully");

    }

}
