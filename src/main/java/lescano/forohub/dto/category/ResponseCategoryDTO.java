package lescano.forohub.dto.category;

import lescano.forohub.entities.Category;

public record ResponseCategoryDTO(
        Long id,
        String name
) {
    public ResponseCategoryDTO(Category category) {
        this(category.getId(), category.getName());
    }

}
