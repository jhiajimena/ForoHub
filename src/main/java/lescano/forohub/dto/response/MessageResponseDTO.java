package lescano.forohub.dto.response;

public record MessageResponseDTO(
        String message,
        DetailsResponseDTO response
) {
}
