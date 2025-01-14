package lescano.forohub.controller;

import lescano.forohub.dto.permission.ResponsePermissionDTO;
import lescano.forohub.dto.profile.CreateProfileDTO;
import lescano.forohub.dto.profile.DetailsResponseProfileDTO;
import lescano.forohub.dto.profile.ResponseProfileDTO;
import lescano.forohub.dto.profile.UpdateProfileDTO;
import lescano.forohub.service.profile.ProfileService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    // create profile
    @PostMapping
    public ResponseEntity<DetailsResponseProfileDTO> createProfile(@RequestBody @Valid CreateProfileDTO data,
                                                                   UriComponentsBuilder uriComponentsBuilder) {
        DetailsResponseProfileDTO profile = profileService.createProfile(data);
        URI url = uriComponentsBuilder.path("/api/profile/{id}").buildAndExpand(profile.id()).toUri();
        return ResponseEntity.created(url).body(profile);
    }

    // get profile by id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseProfileDTO> getProfileById(@PathVariable Long id) {
        ResponseProfileDTO profileDTO = profileService.getById(id);
        return ResponseEntity.ok().body(profileDTO);
    }

    // update profile
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UpdateProfileDTO> updateProfile(
            @PathVariable Long id, @RequestBody @Valid CreateProfileDTO data) {
        UpdateProfileDTO profileDTO = profileService.updateProfile(id, data);
        return ResponseEntity.ok().body(profileDTO);
    }

    // delete profile
    @DeleteMapping("/{id}")
    public ResponseEntity<UpdateProfileDTO> deleteProfile(@PathVariable Long id) {
        UpdateProfileDTO profileDTO = profileService.deleteProfile(id);
        return ResponseEntity.ok().body(profileDTO);
    }

    // get all profiles
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ResponseProfileDTO>>> getAllProfiles(
            @PageableDefault(sort = "name")
            Pageable pageable,
            PagedResourcesAssembler<ResponseProfileDTO> assembler) {
        Page<ResponseProfileDTO> profilesDTO = profileService.getAll(pageable);
        PagedModel<EntityModel<ResponseProfileDTO>> response = assembler.toModel(profilesDTO);
        return ResponseEntity.ok(response);
    }

    // get permission by profile id
    @GetMapping("/{id}/permissions")
    public ResponseEntity<PagedModel<EntityModel<ResponsePermissionDTO>>> getPermissionByProfileId(
            @Valid @PathVariable Long id, @PageableDefault(sort = "name")
            Pageable pageable,
            PagedResourcesAssembler<ResponsePermissionDTO> assembler) {
        Page<ResponsePermissionDTO> permissionDTOPage = profileService.getPermissionByProfileId(id, pageable);
        PagedModel<EntityModel<ResponsePermissionDTO>> pagedModel = assembler.toModel(permissionDTOPage);
        return ResponseEntity.ok(pagedModel);
    }

}
