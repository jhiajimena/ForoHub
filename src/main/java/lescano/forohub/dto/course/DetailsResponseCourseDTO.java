package lescano.forohub.dto.course;

import lescano.forohub.entities.Course;

public record DetailsResponseCourseDTO(
        Long id,
        String name,
        String description,
        String category,
        String subcategory
) {
    public DetailsResponseCourseDTO(Course course){
        this(course.getId(),course.getName(),course.getDescription(),
                course.getSubCategory().getCategory().getName(),
                course.getSubCategory().getName());
    }
}
