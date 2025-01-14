package lescano.forohub.dto.user;

import lescano.forohub.entities.ForumUser;

public record DetailsResponseUserDTO(
        Long id,
        String name,
        String email,
        String profile
) {
    public DetailsResponseUserDTO (ForumUser user){
        this(user.getId(), user.getName(), user.getEmail(), user.getProfile().getName());
    }
}
