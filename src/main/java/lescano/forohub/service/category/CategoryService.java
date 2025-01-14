package lescano.forohub.service.category;


import lescano.forohub.dto.category.*;
import lescano.forohub.dto.category.*;
import lescano.forohub.dto.subCategory.ResponseSubCategoryDTO;
import lescano.forohub.dto.topic.TopicDetailsDTO;
import lescano.forohub.entities.Category;
import lescano.forohub.entities.TopicStatus;
import lescano.forohub.entities.TopicType;
import lescano.forohub.repository.CategoryRepository;
import lescano.forohub.service.subCategory.SubCategoryService;
import lescano.forohub.service.topic.TopicService;
import lescano.forohub.utils.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryService subCategoryService;
    private final CategoryValidator categoryValidator;
    private final TopicService topicService;

    public CategoryService(CategoryRepository categoryRepository,
                           SubCategoryService subCategoryService,
                           CategoryValidator categoryValidator,
                           TopicService topicService) {
        this.categoryRepository = categoryRepository;
        this.subCategoryService = subCategoryService;
        this.categoryValidator = categoryValidator;
        this.topicService = topicService;
    }


    //Create Category
    public Category createCategory(CreateCategoryDTO data) {
        String formattedName = StringUtils.normalizeName(data.name());
        categoryValidator.validateCategoryExistsByName(formattedName);
        CreateCategoryDTO createCategoryDTO = new CreateCategoryDTO(formattedName);
        Category category = new Category(createCategoryDTO);
        return categoryRepository.save(category);
    }

    //Update Category
    public ResponseCategoryDTO updateCategory(Long id, UpdateCategoryDTO data) {
        String formattedName = StringUtils.normalizeName(data.name());
        categoryValidator.validateCategoryExistsByName(formattedName);
        categoryValidator.validateExistsById(id);
        UpdateCategoryDTO categoryCreateRequest = new UpdateCategoryDTO(formattedName);
        Category category = categoryRepository.getReferenceById(id);
        category.updateCategory(categoryCreateRequest);
        return new ResponseCategoryDTO(category);
    }

    //delete category by id
    public DeleteCategoryDTO deleteCategory(Long id) {
        categoryValidator.validateExistsById(id);
        Category category = categoryRepository.getReferenceById(id);
        categoryRepository.delete(category);
        ResponseCategoryDTO categoryDeleteDTO = new ResponseCategoryDTO(category.getId(), category.getName());
        return new DeleteCategoryDTO("The category was successfully deleted.", categoryDeleteDTO);
    }


    //get category by id
    public ResponseCategoryDTO getById(Long id) {
        categoryValidator.validateExistsById(id);
        Category category = categoryRepository.getReferenceById(id);
            return new ResponseCategoryDTO(category);
    }

    //Get categories
    public Page<ResponseCategoryDTO> getCategoriesPaginated(Pageable pageable) {
        Page<Category> categoryPage = categoryRepository.findAll(pageable);
        return categoryPage.map(this::convertToDTO);
    }

    //Get subCategories by category id
    public Page<ResponseSubCategoryDTO> getSubcategoriesByCategoryId(Long idCategory, Pageable pageable) {
        categoryValidator.validateExistsById(idCategory);
        return subCategoryService.getSubcategoriesByCategoryId(idCategory, pageable);
    }

    // Get categories with details
    public DetailsCategoriesDTO getDetailsCategory(Long id) {
        categoryValidator.validateExistsById(id);
        Category category = categoryRepository.findCategoryWithDetails(id);
        return new DetailsCategoriesDTO(category);
    }

    // Get topics by Category
    public Page<TopicDetailsDTO> getTopicsByCategory(
            Long id, TopicStatus status, TopicType type, Pageable pageable) {
        categoryValidator.validateExistsById(id);
        return topicService.findTopicsByCategory(id, status, type, pageable);
    }

    //Converter Category to CategoryResponseDTO
    private ResponseCategoryDTO convertToDTO(Category category) {
        return new ResponseCategoryDTO(category.getId(), category.getName());
    }
}

