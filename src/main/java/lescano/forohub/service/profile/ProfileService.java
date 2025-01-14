package lescano.forohub.service.profile;

import lescano.forohub.dto.permission.ResponsePermissionDTO;
import lescano.forohub.dto.profile.CreateProfileDTO;
import lescano.forohub.dto.profile.DetailsResponseProfileDTO;
import lescano.forohub.dto.profile.ResponseProfileDTO;
import lescano.forohub.dto.profile.UpdateProfileDTO;
import lescano.forohub.entities.Profile;
import lescano.forohub.repository.ProfileRepository;
import lescano.forohub.service.permission.PermissionService;
import lescano.forohub.utils.ConverterData;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileValidator profileValidator;
    private final PermissionService permissionService;

    public ProfileService(ProfileRepository profileRepository, ProfileValidator profileValidator,
                          PermissionService permissionService) {
        this.profileRepository = profileRepository;
        this.profileValidator = profileValidator;
        this.permissionService=permissionService;
    }

    // Create profile
    public DetailsResponseProfileDTO createProfile(@Valid CreateProfileDTO data) {
        profileValidator.validateProfileExistsByName(data.name());
        Profile profile = new Profile(null, data.name(), data.description(),null);
        profileRepository.save(profile);
        return new DetailsResponseProfileDTO(profile);
    }

    // get profile by id
    public ResponseProfileDTO getById(Long id) {
        profileValidator.validateExistsById(id);
        Profile profile = profileRepository.getReferenceById(id);
        return new ResponseProfileDTO(profile);
    }

    // update profile
    public UpdateProfileDTO updateProfile(Long id, CreateProfileDTO data) {
        profileValidator.validateExistsById(id);
        profileValidator.validateProfileExistsByName(data.name());
        Profile profile = profileRepository.getReferenceById(id);
        profile.updateProfile(data);
        ResponseProfileDTO responseProfileDTO = new ResponseProfileDTO(profile);
        return new UpdateProfileDTO("Profile updated successfully", responseProfileDTO);
    }

    // delete profile
    public UpdateProfileDTO deleteProfile(Long id) {
        profileValidator.validateExistsById(id);
        Profile profile = profileRepository.getReferenceById(id);
        profileRepository.delete(profile);
        ResponseProfileDTO responseProfileDTO = new ResponseProfileDTO(profile);
        return new UpdateProfileDTO("Profile deleted successfully", responseProfileDTO);
    }

    // get all profiles
    public Page<ResponseProfileDTO> getAll(Pageable pageable) {
        Page<Profile> profiles = profileRepository.findAll(pageable);
        return profiles.map(ConverterData::convertToDTOProfile);
    }

    //Get permissions by profile id
    public Page<ResponsePermissionDTO> getPermissionByProfileId(@Valid Long id, Pageable pageable) {
        profileValidator.validateExistsById(id);
        return permissionService.getPermissionsByProfileId(id,pageable);
    }
}
