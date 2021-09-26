package com.mobiquity.assessment.testcases;

import com.mobiquity.assessment.basetest.BaseTest;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static com.mobiquity.assessment.constants.Constants.*;

public class ResponseTestsValidator extends BaseTest {

    protected ArrayList<Integer> id;
    protected ArrayList<Integer> postId;
    protected ArrayList<String> email;

    @Test(description = "getting specific user")
    public void getUsers() {
        JsonPath jsonPath = getResponse(USER_URL+prop.getProperty("user"));
        id = jsonPath.get("id");
    }

    @Test(description = "getting specific user posts", dependsOnMethods = "getUsers")
    public void getPosts() {
        JsonPath jsonPath = getResponse(POST_URL + id.get(0));
        postId = jsonPath.get("id");
    }

    @Test(description = "getting comments of specific user posts", dependsOnMethods = "getPosts")
    public void getComments() {
        for (Integer i : postId) {
            JsonPath jsonPath = getResponse(COMMENT_URL + i);
            email = jsonPath.get("email");
            for (String mailId : email) {
                Assert.assertTrue(isValid(mailId));
            }
        }
    }
}
