package org.openindustries.eco.functions.risk;

import io.quarkus.funqy.Funq;

/**
 * Serverless function (Knative / OpenShift Serverless).
 * Scores facility risk signals (intensity, leaks, reporting gaps) into a transparent score with rationale.
 * Transparent, auditable rules: NORMAL (< 35), WARNING (35-69), CRITICAL (>= 70);
 * the rationale is returned with the score for audit (ISO/IEC 27002 A.8.15).
 * Stateless by design (12-Factor VI): all state arrives in the request.
 */
public class Function {

    public record Event(String facilityId, int intensityIndex, int leakIndex, int gapIndex) {
    }

    public record Score(String facilityId, int score, String decision, String rationale) {
    }

    @Funq
    public Score emissionRiskScorer(Event event) {
        int score = 0;
        StringBuilder why = new StringBuilder();
        if (event.intensityIndex() >= 70) {
            score += 40;
            why.append("intensityIndex >= 70; ");
        }
        if (event.leakIndex() >= 60) {
            score += 30;
            why.append("leakIndex >= 60; ");
        }
        if (event.gapIndex() >= 50) {
            score += 30;
            why.append("gapIndex >= 50; ");
        }
        String decision = score >= 70 ? "CRITICAL" : score >= 35 ? "WARNING" : "NORMAL";
        return new Score(event.facilityId(), score, decision,
                why.length() == 0 ? "no signals" : why.toString().trim());
    }
}
