package lescano.forohub.dto.profile;

import jakarta.validation.constraints.NotBlank;

public record CreateProfileDTO(
        @NotBlank
        String name,
        @NotBlank
        String description
) {
}
