package lescano.forohub.service.permission;

import lescano.forohub.exception.ResourceAlreadyExistException;
import lescano.forohub.entities.Permission;
import lescano.forohub.repository.PermissionRepository;
import lescano.forohub.service.validations.AbstractEntityValidator;
import org.springframework.stereotype.Component;

@Component
public class PermissionValidator extends AbstractEntityValidator <Permission> {

    private final PermissionRepository permissionRepository;

    public PermissionValidator(PermissionRepository permissionRepository) {
        super(permissionRepository);
        this.permissionRepository=permissionRepository;
    }

    public void validatePermissionExistsByName(String name){
        if(permissionRepository.existsByName(name)){
            throw  new ResourceAlreadyExistException("A Permission with the name " + name + "already exists.");
        }
    }
}
