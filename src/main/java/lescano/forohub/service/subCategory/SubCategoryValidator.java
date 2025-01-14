package lescano.forohub.service.subCategory;

import lescano.forohub.service.validations.AbstractEntityValidator;
import lescano.forohub.exception.ResourceAlreadyExistException;
import lescano.forohub.entities.SubCategory;
import lescano.forohub.repository.SubCategoryRepository;
import org.springframework.stereotype.Component;

@Component
public class SubCategoryValidator extends AbstractEntityValidator<SubCategory> {

    private final SubCategoryRepository subCategoryRepository;

    public SubCategoryValidator(SubCategoryRepository subCategoryRepository) {
        super(subCategoryRepository);
        this.subCategoryRepository=subCategoryRepository;
    }
    public void validateSubCategoryExistsByName(String name) {
        if (subCategoryRepository.existsByName(name)) {
            throw new ResourceAlreadyExistException("A category with the name " + name + " already exists.");
        }
    }
}
