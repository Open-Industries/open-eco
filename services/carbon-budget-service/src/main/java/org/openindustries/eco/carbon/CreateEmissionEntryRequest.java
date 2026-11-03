package org.openindustries.eco.carbon;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateEmissionEntryRequest(
        @NotBlank @Pattern(regexp = "SCOPE1|SCOPE2|SCOPE3") String scope,
        @Positive long kgCo2e) {
}
