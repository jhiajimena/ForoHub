package lescano.forohub.dto.user;

public record UpdateUserDTO(
            String name,
            String email,
            Long idProfile
) {
}
