package org.openindustries.eco.sustainability;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class SustainabilityRuleResourceTest {

    @Test
    void activateThenEvaluateAllowed() {
        String id = given().contentType("application/json")
                .body("{\"name\":\"energy-2026\",\"category\":\"ENERGY\",\"reductionCapacityKg\":100000,\"maxAuditFindings\":3,\"priority\":1}")
                .when().post("/api/v1/sustainability-rules")
                .then().statusCode(201).body("status", is("DRAFT"))
                .extract().path("id");

        given().when().post("/api/v1/sustainability-rules/" + id + "/activate")
                .then().statusCode(200).body("status", is("ACTIVE"));

        given().contentType("application/json")
                .body("{\"category\":\"ENERGY\",\"requiredMaxAuditFindings\":5,\"requiredReductionCapacityKg\":50000}")
                .when().post("/api/v1/sustainability-rules/evaluate")
                .then().statusCode(200).body("decision", is("ALLOWED"));
    }

    @Test
    void evaluateWithoutRuleIsDenied() {
        given().contentType("application/json")
                .body("{\"category\":\"PROCESS\",\"requiredMaxAuditFindings\":5,\"requiredReductionCapacityKg\":50000}")
                .when().post("/api/v1/sustainability-rules/evaluate")
                .then().statusCode(200).body("decision", is("DENIED"));
    }
}
