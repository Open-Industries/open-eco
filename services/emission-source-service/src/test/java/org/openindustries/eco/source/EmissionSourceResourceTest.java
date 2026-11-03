package org.openindustries.eco.source;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.endsWith;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class EmissionSourceResourceTest {

    @Test
    void createAndFetch() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"Boiler 1\",\"sourceType\":\"ENERGY\",\"facilityCode\":\"FAC-01\"}")
                .when().post("/api/v1/emission-sources")
                .then().statusCode(201)
                .body("status", is("ACTIVE"))
                .body("sourceType", is("ENERGY"))
                .extract().path("id");

        given().when().get("/api/v1/emission-sources/" + id)
                .then().statusCode(200).body("status", is("ACTIVE"));
    }

    @Test
    void transitionWithoutBody() {
        // Lifecycle transitions take no body and must not require a Content-Type (no 415).
        String id = given().contentType("application/json")
                .body("{\"name\":\"Boiler 1\",\"sourceType\":\"ENERGY\",\"facilityCode\":\"FAC-01\"}")
                .when().post("/api/v1/emission-sources")
                .then().statusCode(201)
                .extract().path("id");

        given().when().post("/api/v1/emission-sources/" + id + "/suspend")
                .then().statusCode(200).body("status", is("SUSPENDED"));
    }
}
