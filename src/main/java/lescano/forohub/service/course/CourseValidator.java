package lescano.forohub.service.course;

import lescano.forohub.service.validations.AbstractEntityValidator;
import lescano.forohub.exception.ResourceAlreadyExistException;
import lescano.forohub.entities.Course;
import lescano.forohub.repository.CourseRepository;
import org.springframework.stereotype.Component;

@Component
public class CourseValidator extends AbstractEntityValidator<Course> {

    private final CourseRepository courseRepository;

    public CourseValidator(CourseRepository courseRepository){
        super(courseRepository);
        this.courseRepository=courseRepository;
    }

    public void validateCourseExistsByName (String name){
        if(courseRepository.existsByName(name)){
            throw new ResourceAlreadyExistException("A course with the name " + name  + " already exists.");
        }
    }
}
