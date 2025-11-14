package ApiTest;

import Base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CreateUserTest extends BaseTest {


    @Test(priority = 1)
    public void createUserMorpheus() {
        // Request body as JSON string
        String requestBody = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        // POST request to create user
        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1") // API key to avoid 401
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .extract()
                .response();

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response JSON:\n" + response.asPrettyString());

        // Validate response
        Assert.assertEquals(response.statusCode(), 201, "Expected 201 Created");
        Assert.assertEquals(response.jsonPath().getString("name"), "morpheus");
        Assert.assertEquals(response.jsonPath().getString("job"), "leader");
        Assert.assertNotNull(response.jsonPath().getString("id"), "ID should not be null");
        Assert.assertNotNull(response.jsonPath().getString("createdAt"), "createdAt should not be null");
    }

    @Test(priority=2)
    public void registerUser() {
        // Request body
        String requestBody = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        // POST request with API key
        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1") // ✅ API key added
                .body(requestBody)
                .when()
                .post("/register")
                .then()
                .extract()
                .response();

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response JSON:\n" + response.asPrettyString());

        // Validate response
        Assert.assertEquals(response.statusCode(), 200, "Expected 200 OK");
        Assert.assertNotNull(response.jsonPath().getString("id"), "ID should not be null");
        Assert.assertNotNull(response.jsonPath().getString("token"), "Token should not be null");
    }

    @Test(priority=3)
    public void registerUserMissingPassword() {
        // Request body (missing password)
        String requestBody = "{ \"email\": \"sydney@fife\" }";

        // POST request with API key
        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1") // API key
                .body(requestBody)
                .when()
                .post("/register")
                .then()
                .extract()
                .response();

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response JSON:\n" + response.asPrettyString());

        // Validate response returns 400 and error message
        Assert.assertEquals(response.statusCode(), 400, "Expected 400 Bad Request");
        Assert.assertEquals(response.jsonPath().getString("error"), "Missing password");
    }

    @Test(priority = 4)
    public void loginUserSuccess() {
        // Request body
        String requestBody = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        // POST request with API key
        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1") // optional for free requests
                .body(requestBody)
                .when()
                .post("/login")
                .then()
                .extract()
                .response();

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response JSON:\n" + response.asPrettyString());

        // Validate success
        Assert.assertEquals(response.statusCode(), 200, "Expected 200 OK");
        Assert.assertNotNull(response.jsonPath().getString("token"), "Token should not be null");
    }

    @Test(priority = 5)
    public void loginUserMissingPassword() {
        // Request body without password
        String requestBody = "{ \"email\": \"eve.holt@reqres.in\" }";

        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1")
                .body(requestBody)
                .when()
                .post("/login")
                .then()
                .extract()
                .response();

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response JSON:\n" + response.asPrettyString());

        // Validate error
        Assert.assertEquals(response.statusCode(), 400, "Expected 400 Bad Request");
        Assert.assertEquals(response.jsonPath().getString("error"), "Missing password");
    }



}
