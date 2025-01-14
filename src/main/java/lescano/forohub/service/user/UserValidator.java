package lescano.forohub.service.user;

import lescano.forohub.exception.ResourceAlreadyExistException;
import lescano.forohub.entities.ForumUser;
import lescano.forohub.repository.UserRepository;
import lescano.forohub.service.validations.AbstractEntityValidator;
import org.springframework.stereotype.Component;

@Component
public class UserValidator extends AbstractEntityValidator<ForumUser> {

    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository){
        super(userRepository);
        this.userRepository=userRepository;
    }

    public void validateUserExistsByEmail(String email){
        if(userRepository.existsByEmail(email)){
            throw  new ResourceAlreadyExistException("A user with email " + email + " already exists.");
        }
    }
}
