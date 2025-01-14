package lescano.forohub.dto.subCategory;

import lescano.forohub.dto.course.CourseDTO;

import java.util.List;

public record SubCategoryDTO(
        Long id,
        String name,
        List<CourseDTO> courses
) {
}
