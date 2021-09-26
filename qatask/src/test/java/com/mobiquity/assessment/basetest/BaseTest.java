package com.mobiquity.assessment.basetest;

import com.mobiquity.assessment.constants.Constants;
import com.mobiquity.assessment.reports.ExtentReport;
import com.mobiquity.assessment.reports.LogStatus;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.io.output.WriterOutputStream;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.awt.*;
import java.io.*;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class BaseTest {

    protected StringWriter writer;
    protected PrintStream captor;
    public static Properties prop;
    public String propertyFilePath = System.getProperty("user.dir")
            + "//src//main//resources//config.properties";

    public BaseTest() {
        prop = new Properties();
        try {
            prop.load(new FileInputStream(propertyFilePath));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @BeforeSuite
    public void setUpSuite(ITestContext ctx) {
        ExtentReport.initialize(ctx);

    }

    @AfterSuite
    public void afterSuite() throws IOException {
        ExtentReport.report.flush();
        File htmlFile = new File(Constants.EXTENTREPORTPATH);
        Desktop.getDesktop().browse(htmlFile.toURI());
    }

    @BeforeMethod
    public void setUp() {
        writer = new StringWriter();
        captor = new PrintStream(new WriterOutputStream(writer), true);
    }

    protected void formatAPIAndLogInReport(String content) {

        String prettyPrint = content.replace("\n", "<br>");
        LogStatus.info("<pre>" + prettyPrint + "</pre>");

    }

    public boolean isValid(String email) {
        String emailRegex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(emailRegex);
    }

    public void writeRequestAndResponseInReport(String request, String response) {

        LogStatus.info("---- Request ---");
        formatAPIAndLogInReport(request);
        LogStatus.info("---- Response ---");
        formatAPIAndLogInReport(response);
    }

    public JsonPath getResponse(String url) {
        Response response = given()
                .filter(new RequestLoggingFilter(captor))
                .log()
                .all()
                .get(Constants.BASEURL + url);
        writeRequestAndResponseInReport(writer.toString(), response.prettyPrint());
        return response.jsonPath();
    }

}
