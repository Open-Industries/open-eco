package org.openindustries.eco.functions.risk;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void healthyIsNormal() {
        given().contentType("application/json")
                .body("{\"facilityId\":\"f-1\",\"intensityIndex\":5,\"leakIndex\":3,\"gapIndex\":2}")
                .when().post("/emissionRiskScorer")
                .then().statusCode(200).body("decision", is("NORMAL")).body("score", is(0));
    }

    @Test
    void stackedSignalsAreCritical() {
        given().contentType("application/json")
                .body("{\"facilityId\":\"f-2\",\"intensityIndex\":90,\"leakIndex\":80,\"gapIndex\":70}")
                .when().post("/emissionRiskScorer")
                .then().statusCode(200).body("decision", is("CRITICAL")).body("score", is(100));
    }
}
