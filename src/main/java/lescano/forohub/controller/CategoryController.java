package lescano.forohub.controller;

import lescano.forohub.dto.category.*;
import lescano.forohub.dto.category.*;
import lescano.forohub.dto.subCategory.ResponseSubCategoryDTO;
import lescano.forohub.dto.topic.TopicDetailsDTO;
import lescano.forohub.entities.Category;
import lescano.forohub.entities.TopicStatus;
import lescano.forohub.entities.TopicType;
import lescano.forohub.service.category.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/api/categories")
@Tag(name="Category", description = "Controller for Categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //Create Category
    @PostMapping
    public ResponseEntity<ResponseCategoryDTO> createCategory(@RequestBody @Valid CreateCategoryDTO data,
                                                              UriComponentsBuilder uriComponentsBuilder) {
        Category category = categoryService.createCategory(data);
        ResponseCategoryDTO dataCategory = new ResponseCategoryDTO(category.getId(), category.getName());
        URI url = uriComponentsBuilder.path("/api/category/{id}").buildAndExpand(category.getId()).toUri();
        return ResponseEntity.created(url).body(dataCategory);
    }

    //Update category
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseCategoryDTO> updateCategory(
            @PathVariable long id, @RequestBody @Valid UpdateCategoryDTO updateCategoryDTO) {
        ResponseCategoryDTO responseCategoryDTO = categoryService.updateCategory(id, updateCategoryDTO);
        return ResponseEntity.ok().body(responseCategoryDTO);
    }

    //Delete Category
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteCategoryDTO> deleteCategory(@PathVariable Long id) {
        DeleteCategoryDTO response =  categoryService.deleteCategory(id);
        return ResponseEntity.ok().body(response);
    }

    //Get category by id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseCategoryDTO> getCategoryById(@PathVariable Long id) {
        ResponseCategoryDTO responseCategoryDTO = categoryService.getById(id);
        return ResponseEntity.ok().body(responseCategoryDTO);
    }

    //Get all Categories
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ResponseCategoryDTO>>> listCategories(
            @PageableDefault(sort = "name")
            Pageable pageable,
            PagedResourcesAssembler<ResponseCategoryDTO> assembler) {
        Page<ResponseCategoryDTO> categoryResponseDTOPage = categoryService.getCategoriesPaginated(pageable);
        PagedModel<EntityModel<ResponseCategoryDTO>> pagedModel = assembler.toModel(categoryResponseDTOPage);
        return ResponseEntity.ok(pagedModel);
    }

    //Get Category and associated subcategories by categoryId
    @GetMapping("/{idCategory}/subcategories")
    public ResponseEntity<PagedModel<EntityModel<ResponseSubCategoryDTO>>> getSubcategoriesByCategoryId(
            @PathVariable Long idCategory,
            @PageableDefault(sort = "name")
            Pageable pageable,
            PagedResourcesAssembler<ResponseSubCategoryDTO> assembler) {
        Page<ResponseSubCategoryDTO> categoryDTOPage = categoryService.getSubcategoriesByCategoryId(idCategory,
                pageable);
        PagedModel<EntityModel<ResponseSubCategoryDTO>> pagedModel = assembler.toModel(categoryDTOPage);
        return ResponseEntity.ok(pagedModel);
    }

    // get topics by category id
    @GetMapping("/{id}/topics")
    public ResponseEntity<PagedModel<EntityModel<TopicDetailsDTO>>> getTopicsByCategoryId(
            @PageableDefault(sort = "creationDate")
            Pageable pageable,
            PagedResourcesAssembler<TopicDetailsDTO> assembler,
            @PathVariable Long id,
            @RequestParam(value = "status", required = false) TopicStatus status,
            @RequestParam(value = "type", required = false) TopicType type) {
        Page<TopicDetailsDTO> response = categoryService.getTopicsByCategory(id, status, type, pageable);
        PagedModel<EntityModel<TopicDetailsDTO>> pagedModel = assembler.toModel(response);
        return ResponseEntity.ok(pagedModel);
    }

    // Get categories details
    @GetMapping("/{id}/details")
    public ResponseEntity<DetailsCategoriesDTO> getCategoriesDetails(@PathVariable Long id) {
        DetailsCategoriesDTO details = categoryService.getDetailsCategory(id);
        return ResponseEntity.ok(details);
    }

}
