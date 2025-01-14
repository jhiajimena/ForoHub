package lescano.forohub.dto.profilePermission;

import lescano.forohub.entities.ProfilePermission;

public record ResponseProfilePermissionDTO(
        String profile,
        String permission
) {
    public ResponseProfilePermissionDTO(ProfilePermission profilePermission){
        this(profilePermission.getProfile().getName(),profilePermission.getPermission().getName());
    }
}

