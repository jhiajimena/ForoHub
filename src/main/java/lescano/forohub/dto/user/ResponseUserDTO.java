package lescano.forohub.dto.user;

import lescano.forohub.entities.ForumUser;

public record ResponseUserDTO(
        Long id,
        String name,
        String email,
        Long profileId
) {
    public ResponseUserDTO (ForumUser user){
        this(user.getId(),user.getName(),user.getEmail(),user.getProfile().getId());
    }
}
