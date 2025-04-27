package com.unity.ui;

import com.unity.ui.pageObject.HomePage;
import com.unity.ui.pageObject.LoginPage;
import com.unity.ui.pageObject.PostPage;
import com.unity.ui.pageObject.PublisherPage;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Epic("Unity UI Tests")
@Feature("Entity Management in UI")
public class UnityEntityTest extends TestBase {

    private final String UNITY_TEST_URL = "http://localhost:3000/admin";
    private final Random random = new Random();

    @Test(description = "Validate creation of Publisher entity and linking it to a Post via UI")
    @Story("Create Publisher and Post via UI")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("YourNameHere")
    public void validateCreatePublisherEntityAndLinkItToPost() {
        // Navigate to Unity admin
        navigateToUnityAdmin();

        // Initialize page objects
        LoginPage loginPage = new LoginPage(webAction);
        HomePage homePage = new HomePage(webAction);
        PublisherPage publisherPage = new PublisherPage(webAction);
        PostPage postPage = new PostPage(webAction);

        // Perform login
        loginAsAdmin(loginPage);

        // Navigate to Publisher
        navigateToPublisherTab(homePage);

        // Create Publisher
        Map<String, String> publisherData = createPublisher(publisherPage);

        // Create Post linked to Publisher
        Map<String, String> postData = createPostLinkedToPublisher(homePage, postPage, publisherData.get("email"));

        // Update Post status
        updatePostStatus(postPage, postData);
    }

    @Step("Navigate to Unity Admin Interface URL")
    private void navigateToUnityAdmin() {
        webAction.getUrl(UNITY_TEST_URL);
    }

    @Step("Login as Admin")
    private void loginAsAdmin(LoginPage loginPage) {
        String loginResult = loginPage.login("admin@example.com", "password");
        Assert.assertEquals(loginResult, "Unity Task", "Expected successful login");
    }

    @Step("Navigate to Publisher Tab")
    private void navigateToPublisherTab(HomePage homePage) {
        boolean navigationResult = homePage.clickOnHappyFolder();
        Assert.assertTrue(navigationResult, "Entities not found!");

        navigationResult = homePage.clickOnPublisherTab();
        Assert.assertTrue(navigationResult, "Publisher page not found!");
    }

    @Step("Create Publisher Entity")
    private Map<String, String> createPublisher(PublisherPage publisherPage) {
        Map<String, String> publisherData = new HashMap<>();
        String publisherName = "yossi" + random.nextInt(1000);
        String publisherEmail = random.nextInt(1000) + "yossi@gmail.com";

        publisherData.put("name", publisherName);
        publisherData.put("email", publisherEmail);

        boolean creationResult = publisherPage.createRecord(publisherData, true);
        Assert.assertTrue(creationResult, "Failed to create publisher entity!");

        return publisherData;
    }

    @Step("Create Post Linked to Publisher with email: {publisherEmail}")
    private Map<String, String> createPostLinkedToPublisher(HomePage homePage, PostPage postPage, String publisherEmail) {
        Map<String, String> postData = new HashMap<>();
        postData.put("title", "testTitle");
        postData.put("content", "test content");
        postData.put("status", "ACTIVE");
        postData.put("published", "true");
        postData.put("publisher", publisherEmail);

        homePage.clickOnPostTab();
        postPage.createRecord(postData, true);

        return postData;
    }

    @Step("Update Post Status to REMOVED and Save")
    private void updatePostStatus(PostPage postPage, Map<String, String> postData) {
        postData.put("status", "REMOVED");
        postPage.createRecord(postData, false);

        String postStatusResult = postPage.saveEntity(postData.get("title"));
        Assert.assertEquals(postStatusResult, "REMOVED", "Failed to update post status to REMOVED!");
    }
}