package lescano.forohub.dto.course;

import jakarta.validation.constraints.NotBlank;

public record UpdateCourseDTO(
        @NotBlank
        String name,
        @NotBlank
        String description
) {
}
