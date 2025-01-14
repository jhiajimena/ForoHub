package lescano.forohub.dto.profile;

import lescano.forohub.entities.Profile;

public record DetailsResponseProfileDTO (
        Long id,
        String name,
        String description
) {
    public DetailsResponseProfileDTO (Profile profile){
        this(profile.getId(),profile.getName(),profile.getDescription());
    }

}
