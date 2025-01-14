package lescano.forohub.dto.response;

import lescano.forohub.entities.Response;

import java.time.LocalDateTime;

public record ResponseDTO(
        Long id,
        String message,
        LocalDateTime creationDate,
        Long topicId,
        Long authorId
) {
    public ResponseDTO (Response response){
        this(response.getId(),response.getMessage(),response.getCreationDate(),
                response.getTopic().getId(),response.getAuthor().getId());
    }
}
