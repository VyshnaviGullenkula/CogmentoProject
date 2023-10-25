package com.training.testcases;

import com.training.beans.ConfigurationDetails;
import com.training.constants.ApplicationConstants;
import com.training.listeners.CustomNGListener;
import com.training.listeners.RetryAnalyzerListener;
import com.training.reporting.ExtentManager;
import com.training.reporting.ExtentTestManager;
import com.training.support.BrowserFactory;
import com.training.support.ExcelDataReader;
import com.training.support.FilesHelper;
import com.training.utils.ConfigurationDetailsUtil;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestContext;
import org.testng.annotations.*;

import java.lang.reflect.Method;

@Listeners({CustomNGListener.class, RetryAnalyzerListener.class})
public class BaseTest extends BrowserFactory {
    private static Logger logger = LoggerFactory.getLogger(BaseTest.class);
    public static ConfigurationDetails configurationDetails;
    protected static ExcelDataReader xlsFile = null;

    //execute the static block first
    static {
        configurationDetails = ConfigurationDetailsUtil.getInstance().getConfigurationDetails();
        xlsFile = new ExcelDataReader(configurationDetails.getTestDataFileLocation());
    }

    @BeforeSuite(alwaysRun = true)
    public void setUpSuite() {
        logger.debug("initializing logs and reports, creating directories for test runs ....");
        createDirectories();
        ExtentManager.initExtentReport(configurationDetails);
    }

    @AfterSuite(alwaysRun = true)
    public void tearDownSuite() {
        ExtentManager.getInstance().flush();
        logger.debug("in suite teardown..");
    }

    @BeforeMethod(alwaysRun = true)
    public void setUp(ITestContext iTestContext, Method method) {
        logger.info("test started: " + method.getName());
        ExtentTestManager.startTest(method.getName());

        startSession(configurationDetails.getBrowserDetails());
        getDriver().manage().window().maximize();
        iTestContext.setAttribute("driver", getDriver());
        iTestContext.setAttribute("test-name", method.getName());
    }

    @AfterMethod(alwaysRun = true)
    public void teardown(ITestContext iTestContext, Method method) {
        WebDriver driver = BrowserFactory.getDriver();
        if (driver != null) {
            closeSession();
        }
        ExtentTestManager.endTest();
        logger.info("test ended: " + method.getName());
    }

    private void createDirectories() {
        FilesHelper.createDirectory(ApplicationConstants.REPORTS_FILE_PATH);
        FilesHelper.createDirectory(ApplicationConstants.SCREENSHOTS_DIR);
    }
}