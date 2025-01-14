package lescano.forohub.dto.permission;

import lescano.forohub.entities.Permission;

public record DetailsResponsePermissionDTO(
        Long id,
        String name,
        String description
) {
    public DetailsResponsePermissionDTO (Permission permission){
        this(permission.getId(), permission.getName(), permission.getDescription());
    }
}
