package org.openindustries.eco.functions.compliance;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class FunctionTest {

    @Test
    void compliant() {
        given().contentType("application/json")
                .body("{\"facilityId\":\"f-1\",\"measuredExcessKgCo2e\":0,\"targetExcessKgCo2e\":0,\"measuredVerifiedPct\":98.0,\"targetVerifiedPct\":95.0}")
                .when().post("/carbonComplianceEvaluator")
                .then().statusCode(200).body("compliant", is(true)).body("findings.size()", is(0));
    }

    @Test
    void breachIsReported() {
        given().contentType("application/json")
                .body("{\"facilityId\":\"f-2\",\"measuredExcessKgCo2e\":1200,\"targetExcessKgCo2e\":0,\"measuredVerifiedPct\":98.0,\"targetVerifiedPct\":95.0}")
                .when().post("/carbonComplianceEvaluator")
                .then().statusCode(200).body("compliant", is(false)).body("findings.size()", is(1));
    }
}
