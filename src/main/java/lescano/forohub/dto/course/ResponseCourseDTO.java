package lescano.forohub.dto.course;

public record ResponseCourseDTO(
        Long id,
        String name,
        String description,
        Long categoryId,
        Long subCategoryId

) {
}
