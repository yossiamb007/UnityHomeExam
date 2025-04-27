package com.unity.api;

import com.unity.api.controller.EntityClient;
import com.unity.api.model.User;
import com.unity.api.service.EntityService;
import io.qameta.allure.*;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.testng.Assert;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Epic("Unity API Tests")
@Feature("Entity Management")
public class UnityEntityApiTest {

    private final Random random = new Random();
    private String cookieHeader;

    @Test(description = "Validate creation of Publisher and Post entities, and updating post status")
    @Story("Create Publisher and Post")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("YourNameHere")
    public void validateCreatePublisherEntity() throws IOException {
        EntityService service = EntityClient.getApiService();

        //initLogin(service);
        //translateJson(service);
        login(service);
        initAdmin(service);
        translateJson(service);

        String publisherEmail = generateRandomEmail();
        //createPublisher(service, publisherEmail);
        getPublisherList(service);
        createPost(service, "testTitle");
        updatePostStatus(service, "testTitle", "REMOVED");
    }

    @Step("Initialize Login")
    private void initLogin(EntityService service) throws IOException {
        Response<String> response = service.initLogin().execute();
        validateResponse(response, "Initialization login failed!");
        //cookieHeader = response.headers().get("Set-Cookie").split(";")[0];
    }

    @Step("Login with valid user credentials")
    private void login(EntityService service) throws IOException {
        //User user = new User("admin@example.com", "password");
//        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), "admin@example.com");
//        RequestBody passBody = RequestBody.create(MediaType.parse("text/plain"), "password");
        Map<String, String> headers = getDefaultHeaders();
        //headers.put("Content-Type","application/x-www-form-urlencoded");
        //headers.put("Cookie", cookieHeader);
        Response<Void> response = service.login(headers, "admin@example.com", "password").execute();
        cookieHeader = response.headers().get("Set-Cookie").split(";")[0];
        validateResponse(response, "Login failed!");
    }

    @Step("Initialize Admin Session")
    private void initAdmin(EntityService service) throws IOException {
        Response<String> response = service.initAdmin().execute();
        validateResponse(response, "Admin initialization failed!");
        //cookieHeader = response.headers().get("Set-Cookie");
    }

    @Step("Translate JSON with current session")
    private void translateJson(EntityService service) {
        try {
            Response<String> response = service.translateJson(getDefaultHeaders()).execute();
            validateResponse(response, "Translation failed!");
        } catch (IOException e) {
            throw new RuntimeException("Error during JSON translation", e);
        }
    }

    @Step("Get Publisher List")
    private void getPublisherList(EntityService service) {
        try {
            Map<String, String> headers = getDefaultHeaders();
            headers.put("Cookie", cookieHeader);
            Response<String> response = service.getPublisher(headers).execute();
            validateResponse(response, "Fetching publisher list failed!");
        } catch (IOException e) {
            throw new RuntimeException("Error fetching publisher list", e);
        }
    }

    @Step("Create Publisher with email: {email}")
    private void createPublisher(EntityService service, String email) throws IOException {
        Map<String, String> headers = getDefaultHeaders();
        String name = "yossi" + random.nextInt(1000);

        RequestBody nameBody = RequestBody.create(MediaType.parse("text/plain"), name);
        RequestBody emailBody = RequestBody.create(MediaType.parse("text/plain"), email);

        Response<String> response = service.createPublisher(headers, nameBody, emailBody).execute();
        validateResponse(response, "Publisher creation failed!");
    }

    @Step("Create Post with title: {title}")
    private void createPost(EntityService service, String title) throws IOException {
        Map<String, String> headers = getDefaultHeaders();

        Response<String> response = service.createPost(
                headers,
                createTextBody(title),
                createTextBody("testContent"),
                createTextBody("true"),
                createTextBody("40"),
                createTextBody("ACTIVE")
        ).execute();

        validateResponse(response, "Post creation failed!");
    }

    @Step("Update Post '{title}' status to {status}")
    private void updatePostStatus(EntityService service, String title, String status) throws IOException {
        Map<String, String> headers = getDefaultHeaders();

        Response<String> response = service.updatePost(
                headers,
                createTextBody(title),
                createTextBody("testContent"),
                createTextBody("true"),
                createTextBody("40"),
                createTextBody(status)
        ).execute();

        validateResponse(response, "Post update failed!");
    }

    private Map<String, String> getDefaultHeaders() {
        Map<String, String> headers = new HashMap<>();
        //headers.put("Accept", "application/json, text/plain, */*");
        headers.put("Cache-Control", "no-cache");
        headers.put("Sec-Fetch-Site","same-origin");
        headers.put("host","localhost");
        headers.put("Sec-Fetch-Dest","empty");
        return headers;
    }

    private RequestBody createTextBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value);
    }

    private void validateResponse(Response<?> response, String errorMessage) {
        Assert.assertTrue(response.isSuccessful(), errorMessage);
    }

    private String generateRandomEmail() {
        return "user" + random.nextInt(1000) + "@gmail.com";
    }
}
