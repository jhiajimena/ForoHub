package lescano.forohub.controller;

import lescano.forohub.dto.profilePermission.CreateProfilePermissionDTO;
import lescano.forohub.dto.profilePermission.DetailsResponseProfilePermissionDTO;
import lescano.forohub.service.profilePermission.ProfilePermissionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProfilePermissionController {

    private final ProfilePermissionService ppService;

    public ProfilePermissionController (ProfilePermissionService ppService){
        this.ppService=ppService;
    }

    @PostMapping("/profile-permission")
    public ResponseEntity<DetailsResponseProfilePermissionDTO> createProfilePermission (@RequestBody @Valid CreateProfilePermissionDTO data){
        DetailsResponseProfilePermissionDTO response = ppService.addPermissions(data);
        return ResponseEntity.ok(response);
    }


}
