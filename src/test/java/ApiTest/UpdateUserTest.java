package ApiTest;

import Base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Map;

public class UpdateUserTest extends BaseTest {


    @Test(priority=1)
    public void updateUserMorpheus() {
        // Request body for updating user
        String requestBody = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        // PUT request to update user with ID 2
        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1") // optional API key for authentication
                .body(requestBody)
                .when()
                .put("/users/2")
                .then()
                .statusCode(200) // ✅ 200 OK for update
                .extract()
                .response();

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response JSON:\n" + response.asPrettyString());

        // Validate response contains updated fields
        Assert.assertEquals(response.jsonPath().getString("name"), "morpheus");
        Assert.assertEquals(response.jsonPath().getString("job"), "zion resident");
        Assert.assertNotNull(response.jsonPath().getString("updatedAt"), "updatedAt should not be null");
    }

    @Test(priority=2)
    public void updateUser() {
        // Request body
        String requestBody = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";

        // PUT request to update user with ID 2
        Response response = RestAssured
                .given()
                .header("Content-Type", "application/json")
                .header("x-api-key", "reqres-free-v1") // optional if needed
                .body(requestBody)
                .when()
                .put("/users/2")
                .then()
                .statusCode(200) // 200 OK
                .extract()
                .response();

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response JSON:\n" + response.asPrettyString());

        // Validate updated fields
        Assert.assertEquals(response.jsonPath().getString("name"), "morpheus");
        Assert.assertEquals(response.jsonPath().getString("job"), "zion resident");
        Assert.assertNotNull(response.jsonPath().getString("updatedAt"), "updatedAt should not be null");
    }









}
