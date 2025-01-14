package lescano.forohub.service.profile;

import lescano.forohub.exception.ResourceAlreadyExistException;
import lescano.forohub.entities.Profile;
import lescano.forohub.repository.ProfileRepository;
import lescano.forohub.service.validations.AbstractEntityValidator;
import org.springframework.stereotype.Component;

@Component
public class ProfileValidator extends AbstractEntityValidator<Profile> {

    private final ProfileRepository profileRepository;

    public ProfileValidator(ProfileRepository profileRepository) {
        super(profileRepository);
        this.profileRepository = profileRepository;
    }

    public void validateProfileExistsByName(String name) {
        if (profileRepository.existsByName(name)) {
            throw new ResourceAlreadyExistException("A profile with the name " + name + " already exists.");
        }
    }
}
