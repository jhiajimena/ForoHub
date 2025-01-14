package lescano.forohub.service.subCategory;

import lescano.forohub.dto.course.ResponseCourseDTO;
import lescano.forohub.dto.subCategory.*;
import lescano.forohub.dto.subCategory.*;
import lescano.forohub.dto.topic.TopicDetailsDTO;
import lescano.forohub.exception.ResourceNotFoundException;
import lescano.forohub.entities.Category;
import lescano.forohub.entities.SubCategory;
import lescano.forohub.entities.TopicStatus;
import lescano.forohub.entities.TopicType;
import lescano.forohub.repository.CategoryRepository;
import lescano.forohub.repository.SubCategoryRepository;
import lescano.forohub.service.category.CategoryValidator;
import lescano.forohub.service.course.CourseService;
import lescano.forohub.service.topic.TopicService;
import lescano.forohub.utils.ConverterData;
import lescano.forohub.utils.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;
    private final CategoryRepository categoryRepository;
    private final CourseService courseService;
    private final SubCategoryValidator subCategoryValidator;
    private final CategoryValidator categoryValidator;
    private final TopicService topicService;

    public SubCategoryService(SubCategoryRepository subCategoryRepository,
                              CategoryRepository categoryRepository,
                              CourseService courseService,
                              SubCategoryValidator subCategoryValidator,
                              CategoryValidator categoryValidator,
                              TopicService topicService
    ) {
        this.subCategoryRepository = subCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.courseService = courseService;
        this.subCategoryValidator = subCategoryValidator;
        this.categoryValidator = categoryValidator;
        this.topicService = topicService;
    }

    // Create subCategory
    public DetailsResponseSubCategoryDTO createSubcategory(CreateSubCategoryDTO data) {
        String formattedName = StringUtils.normalizeName(data.name());
        subCategoryValidator.validateSubCategoryExistsByName(formattedName);
        categoryValidator.validateExistsById(data.idCategory());
        Category category = categoryRepository.getReferenceById(data.idCategory());
        CreateSubCategoryDTO createSubCategoryDTO = new CreateSubCategoryDTO(formattedName, category.getId());
        SubCategory subCategory = new SubCategory(createSubCategoryDTO, category);
        subCategoryRepository.save(subCategory);
        return new DetailsResponseSubCategoryDTO(subCategory);
    }

    // Update Category
    public ResponseSubCategoryDTO updateSubcategory(Long id, UpdateSubcategoryDTO data) {
        String formattedName = StringUtils.normalizeName(data.name());
        subCategoryValidator.validateExistsById(id);
        subCategoryValidator.validateSubCategoryExistsByName(formattedName);
        UpdateSubcategoryDTO updateSubcategoryDTO = new UpdateSubcategoryDTO(formattedName);
        SubCategory subCategory = subCategoryRepository.getReferenceById(id);
        subCategory.updateSubCategory(updateSubcategoryDTO);
        return toResponseSubCategoryDTO(subCategory);
    }

    //Delete Category
    public DeleteSubCategoryDTO deleteSubcategory(Long id) {
        subCategoryValidator.validateExistsById(id);
        SubCategory subCategory = subCategoryRepository.getReferenceById(id);
        subCategoryRepository.delete(subCategory);
        ResponseSubCategoryDTO subCategoryDeleteDTO = toResponseSubCategoryDTO(subCategory);
        return new DeleteSubCategoryDTO("The category was successfully deleted.", subCategoryDeleteDTO);
    }


    // get all subCategories
    public Page<ResponseSubCategoryDTO> getAllSubcategories(Pageable pageable) {
        Page<SubCategory> subCategories = subCategoryRepository.findAll(pageable);
        return subCategories.map(ConverterData::convertToDTOSub);
    }

    //Get subCategories by category id
    public Page<ResponseSubCategoryDTO> getSubcategoriesByCategoryId(Long idCategory, Pageable pageable) {
        Page<SubCategory> subCategoryPage = subCategoryRepository.findSubCategoriesByCategoryId(idCategory, pageable);
        return subCategoryPage.map(ConverterData::convertToDTOSub);
    }

    //Get subcategory by id
    public DetailsResponseSubCategoryDTO getById(Long id) {
        Optional<SubCategory> findSubcategory = subCategoryRepository.findById(id);
        if (findSubcategory.isPresent()) {
            SubCategory subCategory = findSubcategory.get();
            return new DetailsResponseSubCategoryDTO(subCategory);
        } else {
            throw new ResourceNotFoundException("SubCategory with ID " + id + " was not found.");
        }
    }
    //Get courses by subCategory id
    public Page<ResponseCourseDTO> getCourses(Long idSubCategory, Pageable pageable) {
        subCategoryValidator.validateExistsById(idSubCategory);
        return courseService.getCoursesBySubCategoryId(idSubCategory, pageable);
    }

    //get topics by subCategoryId
    public Page<TopicDetailsDTO> getTopicsBySubcategoryId(
            Long subcategoryId, TopicType type, TopicStatus status, Pageable pageable) {
        subCategoryValidator.validateExistsById(subcategoryId);
        return topicService.findTopicsBySubcategoryId(subcategoryId, status, type, pageable);
    }

    //Converter to ResponseSubCategoryDTO
    private ResponseSubCategoryDTO toResponseSubCategoryDTO(SubCategory subCategory) {
        return new ResponseSubCategoryDTO(
                subCategory.getId(),
                subCategory.getName(),
                subCategory.getCategory().getId()
        );
    }
}
