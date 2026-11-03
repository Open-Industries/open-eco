package org.openindustries.eco.sustainability;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record EvaluateRequest(
        @NotBlank @Pattern(regexp = "ENERGY|TRANSPORT|PROCESS|WASTE") String category,
        @Positive int requiredMaxAuditFindings,
        @Positive long requiredReductionCapacityKg) {
}
