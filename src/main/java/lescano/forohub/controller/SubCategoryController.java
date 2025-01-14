package lescano.forohub.controller;

import lescano.forohub.dto.course.ResponseCourseDTO;
import lescano.forohub.dto.subCategory.*;
import lescano.forohub.dto.subCategory.*;
import lescano.forohub.dto.topic.TopicDetailsDTO;
import lescano.forohub.entities.TopicStatus;
import lescano.forohub.entities.TopicType;
import lescano.forohub.service.subCategory.SubCategoryService;
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
@RequestMapping("/api/subcategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    //Create SubCategory
    @PostMapping
    @Transactional
    public ResponseEntity<DetailsResponseSubCategoryDTO> createSubCategory(@RequestBody @Valid CreateSubCategoryDTO data,
                                                                           UriComponentsBuilder uriComponentsBuilder) {
        DetailsResponseSubCategoryDTO detailsResponseSubCategoryDTO = subCategoryService.createSubcategory(data);
        URI url = uriComponentsBuilder.path("/sub_category/{id}").buildAndExpand(detailsResponseSubCategoryDTO.id()).toUri();
        return ResponseEntity.created(url).body(detailsResponseSubCategoryDTO);
    }

    //Update SubCategory
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseSubCategoryDTO> updateSubcategory (@PathVariable Long id,
                                                                     @Valid @RequestBody UpdateSubcategoryDTO data){
        ResponseSubCategoryDTO responseSubCategoryDTO = subCategoryService.updateSubcategory(id,data);
        return  ResponseEntity.ok().body(responseSubCategoryDTO);
    }

    //Delete SubCategory
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSubCategoryDTO> deleteSubCategory (@PathVariable Long id){
        DeleteSubCategoryDTO response = subCategoryService.deleteSubcategory(id);
        return ResponseEntity.ok().body(response);
    }

    //Get Subcategory by id
    @GetMapping("/{id}")
    public ResponseEntity<DetailsResponseSubCategoryDTO> getSubCategoryById (@PathVariable Long id){
        DetailsResponseSubCategoryDTO subCategoryDTO = subCategoryService.getById(id);
        return ResponseEntity.ok().body(subCategoryDTO);
    }


    //Get all subcategories
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ResponseSubCategoryDTO>>> listSubcategories(
            @PageableDefault(sort = "categoryId")
            Pageable pageable,
            PagedResourcesAssembler<ResponseSubCategoryDTO> assembler) {
        Page<ResponseSubCategoryDTO> subCategoryResponseDTOPage = subCategoryService.getAllSubcategories(pageable);
        PagedModel<EntityModel<ResponseSubCategoryDTO>> pagedModel = assembler.toModel(subCategoryResponseDTOPage);
        return ResponseEntity.ok(pagedModel);
    }

    //GET /api/subcategories/{subcategoryId}/courses
    @GetMapping("/{idSubCategory}/courses")
    public ResponseEntity<PagedModel<EntityModel<ResponseCourseDTO>>> getCourses(
            @PathVariable Long idSubCategory,
            @PageableDefault(sort = "subCategory.id")
            Pageable pageable,
            PagedResourcesAssembler<ResponseCourseDTO> assembler) {
    Page<ResponseCourseDTO> courseResponseDTOPage = subCategoryService.getCourses(idSubCategory,pageable);
    PagedModel<EntityModel<ResponseCourseDTO>> pagedModel = assembler.toModel(courseResponseDTOPage);
    return ResponseEntity.ok(pagedModel);
    }

    // Get topics by subcategory
    @GetMapping("/{id}/topics")
    public ResponseEntity<PagedModel<EntityModel<TopicDetailsDTO>>> getTopicsBySubcategoryId(
            @PageableDefault(sort = "creationDate")
            Pageable pageable,
            PagedResourcesAssembler<TopicDetailsDTO> assembler,
            @PathVariable Long id,
            @RequestParam(value = "type", required = false) TopicType type,
            @RequestParam(value = "status", required = false) TopicStatus status) {
        Page<TopicDetailsDTO> topicDetailsDTOS = subCategoryService.getTopicsBySubcategoryId(
                id, type, status, pageable);
        PagedModel<EntityModel<TopicDetailsDTO>> pagedModel = assembler.toModel(topicDetailsDTOS);
        return ResponseEntity.ok(pagedModel);
    }


}
