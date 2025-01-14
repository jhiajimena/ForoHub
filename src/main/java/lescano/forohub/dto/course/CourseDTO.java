package lescano.forohub.dto.course;

import lescano.forohub.dto.topic.TopicDTO;

import java.util.List;

public record CourseDTO(
        Long id,
        String name,
        String description,
        List<TopicDTO> topics
) {
}
