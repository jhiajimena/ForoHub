package lescano.forohub.dto.profile;

import lescano.forohub.entities.Profile;

public record ResponseProfileDTO(
        Long id,
        String name,
        String description
) {
    public ResponseProfileDTO (Profile profile){
        this(profile.getId(), profile.getName(), profile.getDescription());
    }
}
