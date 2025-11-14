package ApiTest;

import Base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

public class GetListUsersTest extends BaseTest {


    @Test(priority = 1)
    public void GetUsersPage2() {
        Response response = RestAssured
                .given()
                .header("Accept", "application/json")
                .header("x-api-key", "reqres-free-v1") // ✅ Add API key
                .when()
                .get("/users?page=2")
                .then()
                .statusCode(200) // verify status code
                .extract()
                .response();

        // Print complete JSON response
        System.out.println("Response JSON:\n" + response.asPrettyString());

        // Validate page number
        int page = response.jsonPath().getInt("page");
        Assert.assertEquals(page, 2, "Page number should be 2");

        // Validate that user data list is not empty
        int userCount = response.jsonPath().getList("data").size();
        Assert.assertTrue(userCount > 0, "User list should not be empty");
    }

    @Test(priority = 2)
    public void getUserId2() {
        Response response = RestAssured
                .given()
                .header("Accept", "application/json")
                .header("x-api-key", "reqres-free-v1")  // ✅ Add API key
                .when()
                .get("/users/2")
                .then()
                .statusCode(200)  // ✅ should be 200 OK
                .extract()
                .response();

        System.out.println("Response JSON (User ID 2):\n" + response.asPrettyString());

        // Extract user data
        Map<String, Object> user = response.jsonPath().getMap("data");

        // Validate all fields
        Assert.assertEquals(user.get("id"), 2);
        Assert.assertEquals(user.get("email"), "janet.weaver@reqres.in");
        Assert.assertEquals(user.get("first_name"), "Janet");
        Assert.assertEquals(user.get("last_name"), "Weaver");
        Assert.assertNotNull(user.get("avatar"));
    }
    @Test(priority = 3)
    public void getUser23_NotFound() {
        Response response = RestAssured
                .given()
                .header("Accept", "application/json")
                .header("x-api-key", "reqres-free-v1") // API key required
                .when()
                .get("/users/23")
                .then()
                .statusCode(404) // user not found
                .extract()
                .response();

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response JSON:\n" + response.asPrettyString());
    }
    @Test(priority = 4)
    public void getAllUnknownResources() {
        Response response = RestAssured
                .given()
                .header("Accept", "application/json")
                .header("x-api-key", "reqres-free-v1") // API key required
                .when()
                .get("/unknown")
                .then()
                .statusCode(200)  // Expecting 200 OK
                .extract()
                .response();

        System.out.println("Response JSON (All Resources):\n" + response.asPrettyString());

        // Validate pagination fields
        int page = response.jsonPath().getInt("page");
        int total = response.jsonPath().getInt("total");
        Assert.assertTrue(page > 0, "Page number should be greater than 0");
        Assert.assertTrue(total > 0, "Total resources should be greater than 0");

        // Validate resource list
        List<Map<String, Object>> resources = response.jsonPath().getList("data");
        Assert.assertTrue(resources.size() > 0, "Resource list should not be empty");

        // Validate each resource fields
        for (Map<String, Object> resource : resources) {
            Assert.assertNotNull(resource.get("id"), "Resource ID should not be null");
            Assert.assertNotNull(resource.get("name"), "Resource name should not be null");
            Assert.assertNotNull(resource.get("year"), "Resource year should not be null");
            Assert.assertNotNull(resource.get("color"), "Resource color should not be null");
            Assert.assertNotNull(resource.get("pantone_value"), "Pantone value should not be null");
        }
    }

    @Test(priority = 5)
    public void getUnknownResourceId2() {
        Response response = RestAssured
                .given()
                .header("Accept", "application/json")
                .header("x-api-key", "reqres-free-v1") // API key required
                .when()
                .get("/unknown/2")
                .then()
                .statusCode(200) // Expecting 200 OK
                .extract()
                .response();

        System.out.println("Response JSON (Resource ID 2):\n" + response.asPrettyString());

        // Extract resource data
        Map<String, Object> resource = response.jsonPath().getMap("data");

        // Validate resource fields
        Assert.assertEquals(resource.get("id"), 2);
        Assert.assertEquals(resource.get("name"), "fuchsia rose");
        Assert.assertEquals(resource.get("year"), 2001);
        Assert.assertEquals(resource.get("color"), "#C74375");
        Assert.assertEquals(resource.get("pantone_value"), "17-2031");
    }

    @Test(priority = 6)
    public void getUnknownResourceId23() {
        Response response = RestAssured
                .given()
                .header("Accept", "application/json")
                .header("x-api-key", "reqres-free-v1") // API key if required
                .when()
                .get("/unknown/23")
                .then()
                .extract()
                .response();

        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response JSON:\n" + response.asPrettyString());

        // Validate 404 Not Found
        Assert.assertEquals(response.statusCode(), 404, "Expected 404 for non-existing resource");
    }

    @Test(priority = 7)
    public void getUsersWithDelay() {
        // GET request with delay
        Response response = RestAssured
                .given()
                .header("Accept", "application/json")
                .header("x-api-key", "reqres-free-v1") // optional for authentication
                .when()
                .get("/users?delay=3")
                .then()
                .extract()
                .response();

        // Print status code and response
        System.out.println("Status Code: " + response.statusCode());
        System.out.println("Response JSON:\n" + response.asPrettyString());

        // Validate status code
        Assert.assertEquals(response.statusCode(), 200, "Expected 200 OK");

        // Validate that the response contains a data list
        int userCount = response.jsonPath().getList("data").size();
        Assert.assertTrue(userCount > 0, "User list should not be empty");
    }


}
