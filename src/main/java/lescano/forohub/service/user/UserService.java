package lescano.forohub.service.user;

import lescano.forohub.dto.topic.TopicDetailsDTO;
import lescano.forohub.dto.user.*;
import lescano.forohub.dto.user.*;
import lescano.forohub.entities.ForumUser;
import lescano.forohub.entities.Profile;
import lescano.forohub.exception.ResourceNotFoundException;
import lescano.forohub.repository.ProfileRepository;
import lescano.forohub.repository.UserRepository;
import lescano.forohub.service.profile.ProfileValidator;
import lescano.forohub.service.topic.TopicService;
import lescano.forohub.utils.ConverterData;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final ProfileValidator profileValidator;
    private final ProfileRepository profileRepository;
    private final TopicService topicService;

    public UserService(UserRepository userRepository, UserValidator userValidator,
                       ProfileValidator profileValidator, ProfileRepository profileRepository,
                       TopicService topicService) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.profileValidator = profileValidator;
        this.profileRepository = profileRepository;
        this.topicService = topicService;
    }


    // Get user by id
    public ResponseUserDTO getById(@Valid Long id) {
        userValidator.validateExistsById(id);
        ForumUser user = userRepository.getReferenceById(id);
        return new ResponseUserDTO(user);
    }

    // Create user
    public DetailsResponseUserDTO createUser(@Valid CreateUserDTO data) {
        userValidator.validateUserExistsByEmail(data.email());
        Profile profile = null;
        if(data.idProfile() != null){
            profileValidator.validateExistsById(data.idProfile());
            profile = profileRepository.getReferenceById(data.idProfile());
        }else {
            profile = profileRepository.findByName("USER")
                    .orElseThrow(()-> new ResourceNotFoundException("Error creating user"));
        }
        String passwordEncode = new BCryptPasswordEncoder().encode(data.password());
        ForumUser user = new ForumUser(null, data.name(), data.email(), passwordEncode, profile);
        userRepository.save(user);
        return new DetailsResponseUserDTO(user);

    }


    // Update user
    public MessageResponseUserDTO updateUser(@Valid Long id, UpdateUserDTO data) {
        userValidator.validateExistsById(id);
        userValidator.validateUserExistsByEmail(data.email());
        ForumUser user = userRepository.getReferenceById(id);
        if (data.idProfile() != null) {
            profileValidator.validateExistsById(data.idProfile());
            Profile profile = profileRepository.getReferenceById(data.idProfile());
            user.updateProfile(profile);
        }
        user.updateBasicInfo(data);
        DetailsResponseUserDTO response = new DetailsResponseUserDTO(user);
        return new MessageResponseUserDTO("User updated successfully", response);
    }


    // Delete user
    public MessageResponseUserDTO deleteUser(@Valid Long id) {
        userValidator.validateExistsById(id);
        ForumUser user = userRepository.getReferenceById(id);
        userRepository.delete(user);
        DetailsResponseUserDTO response = new DetailsResponseUserDTO(user);
        return new MessageResponseUserDTO("User deleted successfully", response);
    }

    // Get all users
    public Page<ResponseUserDTO> getAllUsers(Pageable pageable) {
        Page<ForumUser> userPage = userRepository.findAll(pageable);
        return userPage.map(ConverterData::convertToDTOUser);
    }

    // Get profile-permission by user id
    public UserProfilePermissionDTO getUserProfileAndPermissions(Long id) {
        userValidator.validateExistsById(id);
        ForumUser user = userRepository.findUserProfileAndPermissions(id);
        return new UserProfilePermissionDTO(user);
    }

    public Page<TopicDetailsDTO> getTopicsByUser(Long id, Pageable pageable) {
        userValidator.validateExistsById(id);
        return topicService.getTopicsByUser(id, pageable);
    }

    public Page<TopicDetailsDTO> getTopicsByUserResponse(Long id, Pageable pageable) {
        userValidator.validateExistsById(id);
        return topicService.getTopicsByUserResponse(id, pageable);
    }
}
