package lescano.forohub.service.category;

import lescano.forohub.service.validations.AbstractEntityValidator;
import lescano.forohub.exception.ResourceAlreadyExistException;
import lescano.forohub.entities.Category;
import lescano.forohub.repository.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidator extends AbstractEntityValidator<Category> {
    private final CategoryRepository categoryRepository;

    public CategoryValidator(CategoryRepository categoryRepository) {
       super(categoryRepository);
       this.categoryRepository=categoryRepository;
    }

    public void validateCategoryExistsByName(String name) {
        if (categoryRepository.existsByName(name)) {
            throw new ResourceAlreadyExistException("A category with the name " + name + " already exists.");
        }
    }
}
