package org.openindustries.eco.offset;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class OffsetProjectResourceTest {

    @Test
    void createAndTransition() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"Mangrove Restoration Yucatan\",\"projectType\":\"REFORESTATION\",\"creditsTotal\":50000}")
                .when().post("/api/v1/offset-projects")
                .then().statusCode(201)
                .body("status", is("DRAFT"))
                .body("projectType", is("REFORESTATION"))
                .extract().path("id");

        given().when().post("/api/v1/offset-projects/" + id + "/verify")
                .then().statusCode(200).body("status", is("VERIFIED"));
    }
}
