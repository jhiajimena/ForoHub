package lescano.forohub.dto.subCategory;

import lescano.forohub.entities.SubCategory;

public record DetailsResponseSubCategoryDTO(
        Long id,
        String name,
        String category
) {
    public DetailsResponseSubCategoryDTO(SubCategory subCategory){
        this(subCategory.getId(),subCategory.getName(),subCategory.getCategory().getName());
    }

}
