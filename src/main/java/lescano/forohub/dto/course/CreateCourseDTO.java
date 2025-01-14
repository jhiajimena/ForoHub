package lescano.forohub.dto.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCourseDTO(
        @NotBlank
        String name,
        String description,
        @NotNull
        Long idSubCategory
){
}
