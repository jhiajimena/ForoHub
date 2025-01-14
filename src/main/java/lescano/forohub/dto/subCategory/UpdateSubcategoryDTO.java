package lescano.forohub.dto.subCategory;

import jakarta.validation.constraints.NotBlank;

public record UpdateSubcategoryDTO (
        @NotBlank
        String name
){
}
