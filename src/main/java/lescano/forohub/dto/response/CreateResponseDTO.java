package lescano.forohub.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateResponseDTO(
        @NotBlank
        String message,
        @NotNull
        Long topicId
) {
}
