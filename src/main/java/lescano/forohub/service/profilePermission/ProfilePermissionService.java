package lescano.forohub.service.profilePermission;

import lescano.forohub.dto.profilePermission.CreateProfilePermissionDTO;
import lescano.forohub.dto.profilePermission.DetailsResponseProfilePermissionDTO;
import lescano.forohub.dto.profilePermission.ResponseProfilePermissionDTO;
import lescano.forohub.entities.Permission;
import lescano.forohub.entities.Profile;
import lescano.forohub.entities.ProfilePermission;
import lescano.forohub.repository.PermissionRepository;
import lescano.forohub.repository.ProfilePermissionRepository;
import lescano.forohub.repository.ProfileRepository;
import lescano.forohub.service.permission.PermissionValidator;
import lescano.forohub.service.profile.ProfileValidator;
import org.springframework.stereotype.Service;

@Service
public class ProfilePermissionService {

    private final ProfilePermissionRepository ppRepository;
    private final ProfileValidator profileValidator;
    private final PermissionValidator permissionValidator;
    private final ProfileRepository profileRepository;
    private final PermissionRepository permissionRepository;

    public ProfilePermissionService (ProfilePermissionRepository ppRepository, ProfileValidator profileValidator,
                                     PermissionValidator permissionValidator, ProfileRepository profileRepository,
                                     PermissionRepository permissionRepository){
        this.ppRepository=ppRepository;
        this.profileValidator=profileValidator;
        this.permissionValidator=permissionValidator;
        this.profileRepository=profileRepository;
        this.permissionRepository=permissionRepository;
    }

    public DetailsResponseProfilePermissionDTO addPermissions(CreateProfilePermissionDTO data) {
        profileValidator.validateExistsById(data.profileId());
        permissionValidator.validateExistsById(data.permissionId());
        Profile profile = profileRepository.getReferenceById(data.profileId());
        Permission permission = permissionRepository.getReferenceById(data.permissionId());
        if(ppRepository.existsByProfileAndPermission(profile,permission)){
            return new DetailsResponseProfilePermissionDTO(
                    "Permission is already assigned to this profile", null);
        }
        ProfilePermission profilePermission = new ProfilePermission(profile,permission);
        ppRepository.save(profilePermission);
        return new DetailsResponseProfilePermissionDTO(
                "Permission assigned successfully",toResponseProfilePermissionDTO(profilePermission));
    }

    // Converter to ResponseProfilePermissionDTO
    private ResponseProfilePermissionDTO toResponseProfilePermissionDTO(ProfilePermission profilePermission) {
        return new ResponseProfilePermissionDTO(
                profilePermission.getProfile().getName(),
                profilePermission.getPermission().getName());
    }
}
