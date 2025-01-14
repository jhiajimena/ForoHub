package lescano.forohub.controller;


import lescano.forohub.dto.course.*;
import lescano.forohub.dto.course.*;
import lescano.forohub.dto.topic.TopicDetailsDTO;
import lescano.forohub.entities.TopicStatus;
import lescano.forohub.entities.TopicType;
import lescano.forohub.service.course.CourseService;
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
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Create Course
    @PostMapping
    @Transactional
    public ResponseEntity<DetailsResponseCourseDTO> createCourse(@RequestBody @Valid CreateCourseDTO data,
                                                                 UriComponentsBuilder uriComponentsBuilder) {
        DetailsResponseCourseDTO detailsResponseCourseDTO = courseService.createCourse(data);
        URI url = uriComponentsBuilder.path("/courses/{id}").buildAndExpand(detailsResponseCourseDTO.id()).toUri();
        return ResponseEntity.created(url).body(detailsResponseCourseDTO);
    }

    //Update course
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseCourseDTO> updateCourse(
            @PathVariable Long id,
            @RequestBody @Valid UpdateCourseDTO data) {
        ResponseCourseDTO responseCourseDTO = courseService.updateCourse(id, data);
        return ResponseEntity.ok().body(responseCourseDTO);
    }

    //Delete course
    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteCourseDTO> deleteCourse(@PathVariable Long id) {
        return ResponseEntity.ok().body(courseService.deleteCourse(id));
    }

    //Get course by id
    @GetMapping("/{id}")
    public ResponseEntity<DetailsResponseCourseDTO> getCourseById(@PathVariable Long id) {
        DetailsResponseCourseDTO response = courseService.getCourseById(id);
        return ResponseEntity.ok().body(response);
    }

    // Get all courses
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ResponseCourseDTO>>> getAllCourses(
            @PageableDefault(sort = "subCategory.id")
            Pageable pageable, PagedResourcesAssembler<ResponseCourseDTO> assembler) {
        Page<ResponseCourseDTO> responseCourseDTOPage = courseService.getAllCourse(pageable);
        PagedModel<EntityModel<ResponseCourseDTO>> pagedModel = assembler.toModel(responseCourseDTOPage);
        return ResponseEntity.ok(pagedModel);
    }

    // Get topics by course id
    @GetMapping("/{id}/topics")
    public ResponseEntity<PagedModel<EntityModel<TopicDetailsDTO>>> getTopicsByCourseId(
            @Valid @PathVariable Long id,
            @RequestParam(value = "status", required = false) TopicStatus status,
            @RequestParam(value = "type", required = false) TopicType type,
            @PageableDefault(sort = "creationDate")
            Pageable pageable, PagedResourcesAssembler<TopicDetailsDTO> assembler) {
        Page<TopicDetailsDTO> coursePage = courseService.getTopicsByCourseId(id,status,type,pageable);
        PagedModel<EntityModel<TopicDetailsDTO>> pagedModel = assembler.toModel(coursePage);
        return ResponseEntity.ok(pagedModel);
    }

}
