package lescano.forohub.dto.permission;

import jakarta.validation.constraints.NotBlank;

public record CreatePermissionDTO(
        @NotBlank
        String name,
        String description
) {
}
