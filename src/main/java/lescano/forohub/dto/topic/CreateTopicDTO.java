package lescano.forohub.dto.topic;

import lescano.forohub.entities.TopicStatus;
import lescano.forohub.entities.TopicType;
import lescano.forohub.utils.TopicTypeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public record CreateTopicDTO(
        @NotBlank
        String title,
        @NotNull
                @JsonDeserialize(using = TopicTypeDeserializer.class)
        TopicType type,
        @NotBlank
        String message,
        TopicStatus status,
        String idCourse
) {
}
