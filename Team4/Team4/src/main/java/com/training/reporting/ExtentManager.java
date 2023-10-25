package com.training.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.training.beans.ConfigurationDetails;
import com.training.constants.ApplicationConstants;
import com.training.support.FilesHelper;
import com.training.utils.CommonUtil;

import java.io.File;

import java.io.IOException;

public class ExtentManager {

    private final static String EXTENT_CONFIG = "src/test/resources/extent-config.xml";
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            throw new NullPointerException("ExtentManager instance is not created. Please initialize with method initExtentReport() before running your tests.");
        }
        return extent;
    }

    //Create an extent report instance
    public static ExtentReports initExtentReport(final ConfigurationDetails configDetails) {
        String reportFilepath = ApplicationConstants.REPORTS_FILE_PATH + File.separator + ApplicationConstants.EXTENT_REPORT_FILE_NAME;
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFilepath);
        try {
            sparkReporter.loadXMLConfig(EXTENT_CONFIG);
        } catch (IOException e) {
            throw new RuntimeException("Extent Config file path is invalid.");
        }

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);

        //Set environment details
        extent.setSystemInfo("OS", ApplicationConstants.OS);
        extent.setSystemInfo("AUT", ApplicationConstants.AUT);
        extent.setSystemInfo("HOST", CommonUtil.getHostName());
        //extent.setSystemInfo("BROWSER",getBrowserDetails().getBrowserName());
        //extent.setSystemInfo("URL",getBrowserDetails().getApplicationURL());

        return extent;
    }
}