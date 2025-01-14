package lescano.forohub.service.course;


import lescano.forohub.dto.course.*;
import lescano.forohub.dto.course.*;
import lescano.forohub.entities.Course;
import lescano.forohub.entities.SubCategory;
import lescano.forohub.entities.TopicStatus;
import lescano.forohub.entities.TopicType;
import lescano.forohub.dto.topic.TopicDetailsDTO;
import lescano.forohub.repository.CourseRepository;
import lescano.forohub.repository.SubCategoryRepository;
import lescano.forohub.service.subCategory.SubCategoryValidator;
import lescano.forohub.service.topic.TopicService;
import lescano.forohub.utils.ConverterData;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final SubCategoryRepository subCategoryRepository;
    private final CourseValidator courseValidator;
    private final SubCategoryValidator subCategoryValidator;
    private final TopicService topicService;

    public CourseService(CourseRepository courseRepository, SubCategoryRepository subCategoryRepository,
                         CourseValidator courseValidator, SubCategoryValidator subCategoryValidator,
                         TopicService topicService) {
        this.courseRepository = courseRepository;
        this.subCategoryRepository = subCategoryRepository;
        this.courseValidator = courseValidator;
        this.subCategoryValidator= subCategoryValidator;
        this.topicService = topicService;
    }

    //Create Course
    public DetailsResponseCourseDTO createCourse(@Valid CreateCourseDTO data) {
        courseValidator.validateCourseExistsByName(data.name());
        subCategoryValidator.validateExistsById(data.idSubCategory());
        SubCategory subCategory = subCategoryRepository.getReferenceById(data.idSubCategory());
        Course course = new Course(null, data.name(), data.description(), subCategory, null);
        courseRepository.save(course);
        return new DetailsResponseCourseDTO(course);
    }

    //Get course by id
    public DetailsResponseCourseDTO getCourseById(Long id) {
        courseValidator.validateExistsById(id);
        Course course = courseRepository.getReferenceById(id);
        return new DetailsResponseCourseDTO(course);
    }

    //Delete course
    public DeleteCourseDTO deleteCourse(Long id) {
        courseValidator.validateExistsById(id);
        Course course = courseRepository.getReferenceById(id);
        courseRepository.delete(course);
        ResponseCourseDTO responseCourseDTO = toResponseCourseDTO(course);
        return new DeleteCourseDTO("The course was successfully deleted", responseCourseDTO);
    }

    //Update Course
    public ResponseCourseDTO updateCourse(Long id ,@Valid UpdateCourseDTO data) {
        courseValidator.validateExistsById(id);
        courseValidator.validateCourseExistsByName(data.name());
        Course course = courseRepository.getReferenceById(id);
        course.updateCourse(data);
        return  toResponseCourseDTO(course);
    }

    //Get courses by subCategory id
    public Page<ResponseCourseDTO> getCoursesBySubCategoryId(Long idSubCategory, Pageable pageable) {
        Page<Course> courses = courseRepository.findCoursesBySubCategoryId(idSubCategory, pageable);
        return courses.map(ConverterData::convertToDTOCourse);
    }

    //Get all courses
    public Page<ResponseCourseDTO> getAllCourse(Pageable pageable) {
        Page<Course> courses = courseRepository.findAll(pageable);
        return courses.map(ConverterData::convertToDTOCourse);
    }


    //Get topics by courseId
    public Page<TopicDetailsDTO> getTopicsByCourseId(
            @Valid Long courseId,
            TopicStatus status,
            TopicType type, Pageable pageable) {
        courseValidator.validateExistsById(courseId);
        return topicService.getTopics(courseId,status,type,pageable);
    }

    // Converter to ResponseCourseDTO
    private ResponseCourseDTO toResponseCourseDTO(Course course) {
        return new ResponseCourseDTO(course.getId(), course.getName(),
                course.getDescription(), course.getSubCategory().getCategory().getId(), course.getSubCategory().getId());
    }
}
