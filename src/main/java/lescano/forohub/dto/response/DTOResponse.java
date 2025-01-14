package lescano.forohub.dto.response;

import java.time.LocalDateTime;

public record DTOResponse(
        Long id,
        String message,
        boolean isSolution,
        LocalDateTime creationDate,
        Long authorId
) {
}
