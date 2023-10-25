package com.training.testcases.TasksPage;

import com.training.constants.ApplicationConstants;
import com.training.pages.*;
import com.training.reporting.ExtentTestManager;
import com.training.testcases.BaseTest;
import com.training.utils.CommonUtil;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Random;

public class CreateTaskPageWithRequiredDetails extends BaseTest {

//    private static final String TESTCASENAME = "TC01";
//
//    Map<String, Map<String, String>> sampleTestData;

    @Test
    public void creatTask() throws Exception {

        //Map<String, String> testDetails = xlsFile.getExcelRowValuesIntoMapBasedOnKey("sampleSheet", TESTCASENAME);

        //Login to Application

        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.loginToApplication(configurationDetails.getUserName(), configurationDetails.getPassword());
        ExtentTestManager.getTest().pass("Logged in to application");

        // Navigate to Application
        TasksPage tasksPage = new TasksPage(getDriver());
        tasksPage.navigateToTask();
        ExtentTestManager.getTest().pass("Navigated To Task Home Page");

        //Create and Enter TestData of Task
        HashMap<String, String> objTaskDetails = new HashMap<>();
        String sTaskTitle = CommonUtil.getRandomString("Quality", 11);
        objTaskDetails.put("Title", sTaskTitle);
        objTaskDetails.put("Assigned To", "Vyshnavi  Gullenkula ");
        objTaskDetails.put("Type", "General Support");
        objTaskDetails.put("Due Date", "17/10/2023 10:30");
        objTaskDetails.put("Company", "qualizeal");
        objTaskDetails.put("Contact", "8709977844");
        objTaskDetails.put("Priority", "High");
        tasksPage.createTask(objTaskDetails);
        ExtentTestManager.getTest().pass("Successfully created the task." + sTaskTitle);
        tasksPage.navigateToTask();
        tasksPage.checkRecordDisplayed(sTaskTitle);

        //CleanUp
        tasksPage.deleteTask(sTaskTitle);
        ExtentTestManager.getTest().pass("Cleanup: Delete Task." + sTaskTitle);

    }
}

