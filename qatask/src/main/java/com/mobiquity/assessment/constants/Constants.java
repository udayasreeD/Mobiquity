package com.mobiquity.assessment.constants;

public class Constants {

    private Constants() {

    }


    public static final String EXTENTCONFIGFILEPATH = System.getProperty("user.dir")
            +"/src/test/resources/extentreport.xml";
    public static final String EXTENTREPORTPATH = System.getProperty("user.dir")+"/ExtentReports/";
    public static final String BASEURL = "https://jsonplaceholder.typicode.com";

    public static final String USER_URL = "/users?username=";
    public static final String POST_URL = "/posts?userId=";
    public static final String COMMENT_URL = "/comments?postId=";

}
