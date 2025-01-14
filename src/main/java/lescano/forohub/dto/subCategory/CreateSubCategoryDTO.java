package lescano.forohub.dto.subCategory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateSubCategoryDTO(
        @NotBlank
        String name,
        @NotNull
        Long idCategory
) {

}
