package lescano.forohub.utils;

import lescano.forohub.dto.category.CategoryDetailsDTO;
import lescano.forohub.dto.course.ResponseCourseDTO;
import lescano.forohub.dto.permission.ResponsePermissionDTO;
import lescano.forohub.dto.profile.ResponseProfileDTO;
import lescano.forohub.dto.response.DTOResponse;
import lescano.forohub.dto.response.ResponseDTO;
import lescano.forohub.dto.subCategory.ResponseSubCategoryDTO;
import lescano.forohub.dto.topic.TopicDetailsDTO;
import lescano.forohub.dto.user.ResponseUserDTO;
import lescano.forohub.dto.user.UserDTO;
import lescano.forohub.entities.*;
import lescano.forohub.entities.*;

public class ConverterData {

    private ConverterData() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static ResponseSubCategoryDTO convertToDTOSub (SubCategory subCategory){
        return new ResponseSubCategoryDTO(subCategory.getId(),subCategory.getName(),subCategory.getCategory().getId());
    }
    public static ResponseCourseDTO convertToDTOCourse (Course course){
        return new ResponseCourseDTO(course.getId(),course.getName(),course.getDescription(),
                course.getSubCategory().getCategory().getId(),
                course.getSubCategory().getId());
    }

    public static ResponsePermissionDTO convertToDTOPermission(Permission permission) {
        return new ResponsePermissionDTO(permission.getId(), permission.getName(), permission.getDescription());
    }

    public static ResponseProfileDTO convertToDTOProfile(Profile profile) {
        return new ResponseProfileDTO(profile.getId(), profile.getName(), profile.getDescription());
    }

    public static ResponseUserDTO convertToDTOUser(ForumUser user) {
        return new ResponseUserDTO(user.getId(), user.getName(), user.getEmail(), user.getProfile().getId());
    }

    public static DTOResponse convertToDTOResponse(Response response) {
        return new DTOResponse(response.getId(), response.getMessage(),response.isSolution() ,response.getCreationDate(),
                response.getAuthor().getId());
    }

    public static ResponseDTO convertToResponseDTO(Response response) {
        return new ResponseDTO(response.getId(), response.getMessage(), response.getCreationDate(),
                response.getTopic().getId(), response.getAuthor().getId());
    }

    public static TopicDetailsDTO convertToTopicDetailsDTO(Topic topic) {
        String offTopic = "off topic";

        CategoryDetailsDTO categoryDetails = (topic.getCourse() != null &&
                topic.getCourse().getSubCategory() != null &&
                topic.getCourse().getSubCategory().getCategory() != null)
                ? new CategoryDetailsDTO(
                topic.getCourse().getSubCategory().getCategory().getId(),
                topic.getCourse().getSubCategory().getCategory().getName(),
                topic.getCourse().getSubCategory().getId(),
                topic.getCourse().getSubCategory().getName(),
                topic.getCourse().getId(),
                topic.getCourse().getName() != null ? topic.getCourse().getName() : offTopic)
                : new CategoryDetailsDTO(
                null,
                offTopic,
                null,
                offTopic,
                null,
                offTopic
        );
        return new TopicDetailsDTO(
                topic.getId(),
                topic.getTitle(),
                topic.getStatus(),
                topic.getCreationDate(),
                new UserDTO(topic.getAuthor().getId(), topic.getAuthor().getName()),
                topic.getResponses().size(),
                categoryDetails
        );
    }
}
