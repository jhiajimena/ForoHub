package lescano.forohub.dto.profile;

import lescano.forohub.dto.permission.ResponsePermissionDTO;

import java.util.List;

public record ProfileDTO(
        Long id,
        String name,
        String description,
        List<ResponsePermissionDTO> permissionDTO
) {
}
