package lescano.forohub.dto.topic;

import lescano.forohub.dto.category.CategoryDetailsDTO;
import lescano.forohub.dto.response.DTOResponse;
import lescano.forohub.entities.Topic;
import lescano.forohub.entities.TopicStatus;
import lescano.forohub.entities.TopicType;
import lescano.forohub.utils.ConverterData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ResponseTopicDTO(
        Long id,
        String title,
        TopicType type,
        String message,
        LocalDateTime creationDate,
        TopicStatus status,
        Long authorId,
        List<DTOResponse> responses,
        CategoryDetailsDTO categoryDetailsDTO


) {
    private static final String OFF_TOPIC = "off topic";
    public ResponseTopicDTO(Topic topic) {

        this(topic.getId(), topic.getTitle(), topic.getType(), topic.getMessage(),
                topic.getCreationDate(), topic.getStatus(), topic.getAuthor().getId(),
                topic.getResponses() != null ? topic.getResponses().stream().map(ConverterData::convertToDTOResponse)
                        .collect(Collectors.toList()) : null,
                (topic.getCourse() != null &&
                        topic.getCourse().getSubCategory() != null &&
                        topic.getCourse().getSubCategory().getCategory() != null)
                ? new CategoryDetailsDTO(
                        topic.getCourse().getSubCategory().getCategory().getId(),
                        topic.getCourse().getSubCategory().getCategory().getName(),
                        topic.getCourse().getSubCategory().getId(),
                        topic.getCourse().getSubCategory().getName(),
                        topic.getCourse().getId(),
                        (topic.getCourse().getName() != null)
                                ? topic.getCourse().getName() : OFF_TOPIC)
                        : new CategoryDetailsDTO(
                        null,
                        OFF_TOPIC,
                        null,
                        OFF_TOPIC,
                        null,
                        OFF_TOPIC
                )
        );

    }
}
