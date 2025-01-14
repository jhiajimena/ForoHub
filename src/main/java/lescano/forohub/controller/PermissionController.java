package lescano.forohub.controller;


import lescano.forohub.dto.permission.CreatePermissionDTO;
import lescano.forohub.dto.permission.DetailsResponsePermissionDTO;
import lescano.forohub.dto.permission.ResponsePermissionDTO;
import lescano.forohub.dto.permission.MessageResponsePermissionDTO;
import lescano.forohub.service.permission.PermissionService;
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
@RequestMapping("/api/permissions")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    // Create permission
    @PostMapping
    public ResponseEntity<DetailsResponsePermissionDTO> createPermission(@RequestBody @Valid CreatePermissionDTO data,
                                                                         UriComponentsBuilder uriComponentsBuilder) {
        DetailsResponsePermissionDTO response = permissionService.createPermission(data);
        URI url = uriComponentsBuilder.path("/api/permissions/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(url).body(response);
    }

    // get permission by id
    @GetMapping("/{id}")
    public ResponseEntity<ResponsePermissionDTO> getPermissionById(@PathVariable Long id) {
        ResponsePermissionDTO response = permissionService.getPermissionById(id);
        return ResponseEntity.ok().body(response);
    }

    // update permission by id
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<MessageResponsePermissionDTO> updatePermission(@PathVariable Long id,
                                                                         @RequestBody @Valid CreatePermissionDTO data) {
        MessageResponsePermissionDTO response = permissionService.updatePermission(id, data);
        return ResponseEntity.ok().body(response);
    }

    // delete permission by id
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponsePermissionDTO> deletePermission(@PathVariable Long id) {
        MessageResponsePermissionDTO response = permissionService.deletePermission(id);
        return ResponseEntity.ok().body(response);
    }

    //get all permissions
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ResponsePermissionDTO>>> getAllPermissions(
            @PageableDefault(sort = "name") Pageable pageable,
            PagedResourcesAssembler<ResponsePermissionDTO> assembler) {
        Page<ResponsePermissionDTO> permissionDTOPage = permissionService.getAllPermissions(pageable);
        PagedModel<EntityModel<ResponsePermissionDTO>> response = assembler.toModel(permissionDTOPage);
        return ResponseEntity.ok(response);
    }
}
