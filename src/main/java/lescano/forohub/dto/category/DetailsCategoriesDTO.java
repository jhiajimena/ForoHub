package lescano.forohub.dto.category;

import lescano.forohub.dto.course.CourseDTO;
import lescano.forohub.dto.response.DTOResponse;
import lescano.forohub.dto.subCategory.SubCategoryDTO;
import lescano.forohub.dto.topic.TopicDTO;
import lescano.forohub.entities.Category;

import java.util.List;

public record DetailsCategoriesDTO(
        Long id,
        String name,
        List<SubCategoryDTO> subCategories
) {
    public DetailsCategoriesDTO(Category category) {
        this(category.getId(), category.getName(),
                category.getSubCategories().stream().map(subCategory ->
                        new SubCategoryDTO(subCategory.getId(),
                                subCategory.getName(),
                                subCategory.getCourses().stream().map(course ->
                                        new CourseDTO(
                                                course.getId(),
                                                course.getName(),
                                                course.getDescription(),
                                                course.getTopics().stream().map(topic ->
                                                        new TopicDTO(
                                                                topic.getId(),
                                                                topic.getTitle(),
                                                                topic.getMessage(),
                                                                topic.getStatus(),
                                                                topic.getType(),
                                                                topic.getCreationDate(),
                                                                topic.getAuthor().getId(),
                                                                topic.getResponses().stream().map(response ->
                                                                        new DTOResponse(
                                                                                response.getId(),
                                                                                response.getMessage(),
                                                                                response.isSolution(),
                                                                                response.getCreationDate(),
                                                                                response.getAuthor().getId()
                                                                        )
                                                                ).toList()
                                                        )
                                                ).toList()
                                        )
                                ).toList()
                        )
                ).toList()
        );
    }
}
