package com.mobiquity.assessment.reports;

import com.mobiquity.assessment.constants.Constants;
import com.relevantcodes.extentreports.ExtentReports;
import org.testng.ITestContext;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ExtentReport {

    public static ExtentReports report = null;
    public static String extentreportpath = "";


    private ExtentReport(ITestContext ctx) {
        extentreportpath = Constants.EXTENTREPORTPATH + new SimpleDateFormat("dd-MM-yyyy").format(new Date()) + "/" + ctx.getSuite().getName() + "_" + System.currentTimeMillis() + ".html";
        report = new ExtentReports(extentreportpath);
        report.loadConfig(new File(Constants.EXTENTCONFIGFILEPATH));

    }

    public static void initialize(ITestContext ctx) {
        new ExtentReport(ctx);
    }

}
