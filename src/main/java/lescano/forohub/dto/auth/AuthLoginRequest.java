package lescano.forohub.dto.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(
       @NotBlank String email,
       @NotBlank String password
) {
}
