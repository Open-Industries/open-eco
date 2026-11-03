package org.openindustries.eco.carbon;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record OpenCarbonBudgetRequest(
        @NotBlank String facilityCode,
        @PositiveOrZero long budgetKgCo2e) {
}
