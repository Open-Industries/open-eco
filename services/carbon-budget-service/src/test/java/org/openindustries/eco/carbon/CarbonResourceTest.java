package org.openindustries.eco.carbon;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class CarbonResourceTest {

    @Test
    void consumptionDrawsDown() {
        String id = given().contentType("application/json")
                .body("{\"facilityCode\":\"FAC-01\",\"budgetKgCo2e\":100000}")
                .when().post("/api/v1/carbon-budgets")
                .then().statusCode(201).body("budgetKgCo2e", is(100000))
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"scope\":\"SCOPE1\",\"kgCo2e\":25000}")
                .when().post("/api/v1/carbon-budgets/" + id + "/entries")
                .then().statusCode(200)
                .body("remainingAfter", is(75000));
    }

    @Test
    void overdrawIsRejected() {
        String id = given().contentType("application/json")
                .body("{\"facilityCode\":\"FAC-01\",\"budgetKgCo2e\":100000}")
                .when().post("/api/v1/carbon-budgets")
                .then().statusCode(201)
                .extract().path("id");

        given().contentType("application/json")
                .body("{\"scope\":\"SCOPE1\",\"kgCo2e\":250000}")
                .when().post("/api/v1/carbon-budgets/" + id + "/entries")
                .then().statusCode(422).body("status", is(422));

        given().when().get("/api/v1/carbon-budgets/" + id)
                .then().statusCode(200).body("budgetKgCo2e", is(100000));
    }
}
