package org.openindustries.eco.sustainability;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateSustainabilityRuleRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "ENERGY|TRANSPORT|PROCESS|WASTE") String category,
        @Positive long reductionCapacityKg,
        @Positive int maxAuditFindings,
        @Min(1) @Max(100) int priority) {
}
