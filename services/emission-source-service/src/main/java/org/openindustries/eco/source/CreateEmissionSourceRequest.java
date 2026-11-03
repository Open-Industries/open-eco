package org.openindustries.eco.source;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateEmissionSourceRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "ENERGY|TRANSPORT|PROCESS|WASTE") String sourceType,
        @NotBlank String facilityCode) {
}
