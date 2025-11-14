package ApiTest;

import Base.BaseTest;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class DeleteUserTest  extends BaseTest {

    @Test
    public void deleteUserId2() {
        RestAssured
                .given()
                .header("x-api-key", "reqres-free-v1") // only if needed
                .when()
                .delete("/users/2")
                .then()
                .statusCode(204); // No Content means successfully deleted

        System.out.println("User 2 deleted successfully");
    }

}
