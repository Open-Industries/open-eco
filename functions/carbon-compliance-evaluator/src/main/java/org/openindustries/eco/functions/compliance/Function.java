package org.openindustries.eco.functions.compliance;

import io.quarkus.funqy.Funq;

import java.util.ArrayList;
import java.util.List;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Compares excess emissions and verified-share against targets; one finding per breach.
 * Compares measured values with targets and returns a verdict with one finding per
 * breach — machine-checkable assurance evidence.
 */
public class Function {

    public record Kpi(String facilityId, int measuredExcessKgCo2e, int targetExcessKgCo2e,
                      double measuredVerifiedPct, double targetVerifiedPct) {
    }

    public record Verdict(String facilityId, boolean compliant, List<String> findings) {
    }

    @Funq
    public Verdict carbonComplianceEvaluator(Kpi kpi) {
        List<String> findings = new ArrayList<>();
        if (kpi.measuredExcessKgCo2e() > kpi.targetExcessKgCo2e()) {
            findings.add("excessKgCo2e " + kpi.measuredExcessKgCo2e() + " exceeds target " + kpi.targetExcessKgCo2e());
        }
        if (kpi.measuredVerifiedPct() < kpi.targetVerifiedPct()) {
            findings.add("verifiedPct " + kpi.measuredVerifiedPct() + " below target " + kpi.targetVerifiedPct());
        }
        return new Verdict(kpi.facilityId(), findings.isEmpty(), findings);
    }
}
