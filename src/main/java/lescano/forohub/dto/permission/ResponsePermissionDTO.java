package lescano.forohub.dto.permission;

import lescano.forohub.entities.Permission;

public record ResponsePermissionDTO(
        Long id,
        String name,
        String description
) {
    public ResponsePermissionDTO(Permission permission) {
        this(permission.getId(), permission.getName(), permission.getDescription());
    }
}
