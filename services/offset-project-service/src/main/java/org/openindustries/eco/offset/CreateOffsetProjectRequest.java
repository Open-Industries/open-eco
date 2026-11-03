package org.openindustries.eco.offset;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record CreateOffsetProjectRequest(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "REFORESTATION|RENEWABLES|METHANE_CAPTURE") String projectType,
        @Positive long creditsTotal) {
}
