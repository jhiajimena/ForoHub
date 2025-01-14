package lescano.forohub.service.permission;

import lescano.forohub.dto.permission.CreatePermissionDTO;
import lescano.forohub.dto.permission.DetailsResponsePermissionDTO;
import lescano.forohub.dto.permission.ResponsePermissionDTO;
import lescano.forohub.dto.permission.MessageResponsePermissionDTO;
import lescano.forohub.entities.Permission;
import lescano.forohub.repository.PermissionRepository;
import lescano.forohub.utils.ConverterData;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final PermissionValidator permissionValidator;

    public PermissionService(PermissionRepository permissionRepository, PermissionValidator permissionValidator) {
        this.permissionRepository = permissionRepository;
        this.permissionValidator = permissionValidator;
    }

    // create permission
    public DetailsResponsePermissionDTO createPermission(CreatePermissionDTO data) {
        permissionValidator.validatePermissionExistsByName(data.name());
        Permission permission = new Permission(null, data.name(), data.description());
        permissionRepository.save(permission);
        return new DetailsResponsePermissionDTO(permission);
    }

    // get permission by id
    public ResponsePermissionDTO getPermissionById(Long id) {
        permissionValidator.validateExistsById(id);
        Permission permission = permissionRepository.getReferenceById(id);
        return new ResponsePermissionDTO(permission);
    }

    // update permission
    public MessageResponsePermissionDTO updatePermission(Long id, CreatePermissionDTO data) {
        permissionValidator.validateExistsById(id);
        permissionValidator.validatePermissionExistsByName(data.name());
        Permission permission = permissionRepository.getReferenceById(id);
        permission.updatePermission(data);
        DetailsResponsePermissionDTO response = new DetailsResponsePermissionDTO(permission);
        return new MessageResponsePermissionDTO("Permission updated successfully", response);
    }

    // delete permission
    public MessageResponsePermissionDTO deletePermission(Long id) {
        permissionValidator.validateExistsById(id);
        Permission permission = permissionRepository.getReferenceById(id);
        permissionRepository.delete(permission);
        DetailsResponsePermissionDTO response = new DetailsResponsePermissionDTO(permission);
        return new MessageResponsePermissionDTO("Permission deleted successfully", response);
    }

    // get all permissions
    public Page<ResponsePermissionDTO> getAllPermissions(Pageable pageable) {
        Page<Permission> permissions = permissionRepository.findAll(pageable);
        return permissions.map(ConverterData::convertToDTOPermission);
    }

    // get permissions by profile id
    public Page<ResponsePermissionDTO> getPermissionsByProfileId(@Valid Long id, Pageable pageable) {
        Page<Permission>permissions = permissionRepository.findPermissionsByProfileId(id,pageable);
        return permissions.map(ConverterData::convertToDTOPermission);
    }



}
