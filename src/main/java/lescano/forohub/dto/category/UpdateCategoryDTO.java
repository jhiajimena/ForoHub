package lescano.forohub.dto.category;

import jakarta.validation.constraints.NotBlank;

public record UpdateCategoryDTO(
        @NotBlank
        String name
) {
}
