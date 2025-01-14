package lescano.forohub.dto.topic;

import lescano.forohub.dto.category.CategoryDetailsDTO;
import lescano.forohub.dto.user.UserDTO;
import lescano.forohub.entities.TopicStatus;

import java.time.LocalDateTime;

public record TopicDetailsDTO(
        Long id,
        String title,
        TopicStatus status,
        LocalDateTime creationDate,
        UserDTO author,
        int numberOfResponses,
        CategoryDetailsDTO categoryDetailsDTO
) {
}
