package lescano.forohub.controller;


import lescano.forohub.dto.topic.TopicDetailsDTO;
import lescano.forohub.dto.user.MessageResponseUserDTO;
import lescano.forohub.dto.user.ResponseUserDTO;
import lescano.forohub.dto.user.UpdateUserDTO;
import lescano.forohub.dto.user.UserProfilePermissionDTO;
import lescano.forohub.service.user.UserService;
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



@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    // Get user by id
    @GetMapping("/{id}")
    public ResponseEntity<ResponseUserDTO> getUserById(@Valid @PathVariable Long id) {
        ResponseUserDTO response = userService.getById(id);
        return ResponseEntity.ok(response);
    }

    // Update user
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<MessageResponseUserDTO> updateUser(@Valid @PathVariable Long id,
                                                             @RequestBody UpdateUserDTO data) {
        MessageResponseUserDTO response = userService.updateUser(id, data);
        return ResponseEntity.ok(response);
    }

    //Delete user
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseUserDTO> deleteUser(@Valid @PathVariable Long id) {
        MessageResponseUserDTO response = userService.deleteUser(id);
        return ResponseEntity.ok(response);
    }

    //Get all user
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ResponseUserDTO>>> getAllUsers(
            @PageableDefault(sort = "name")
            Pageable pageable,
            PagedResourcesAssembler<ResponseUserDTO> assembler) {
        Page<ResponseUserDTO> page = userService.getAllUsers(pageable);
        PagedModel<EntityModel<ResponseUserDTO>> pagedModel = assembler.toModel(page);
        return ResponseEntity.ok(pagedModel);
    }

    //Get permissions and profiles by user id
    @GetMapping("/{id}/profile")
    public ResponseEntity<UserProfilePermissionDTO> getProfileByUser(
            @PageableDefault(sort = "profile")
            @PathVariable Long id) {
        UserProfilePermissionDTO user = userService.getUserProfileAndPermissions(id);
        return ResponseEntity.ok().body(user);
    }

    // Get topics by user
    @GetMapping("/{id}/topics")
    public ResponseEntity<PagedModel<EntityModel<TopicDetailsDTO>>> getTopicsByUser(
            @PathVariable Long id,
            @PageableDefault(sort = "creationDate")
            Pageable pageable,
            PagedResourcesAssembler<TopicDetailsDTO> assembler) {
        Page<TopicDetailsDTO> topics = userService.getTopicsByUser(id, pageable);
        PagedModel<EntityModel<TopicDetailsDTO>> pagedModel = assembler.toModel(topics);
        return ResponseEntity.ok(pagedModel);
    }

    // Get topics by user response
    @GetMapping("/{id}/topics/participate")
    public ResponseEntity<PagedModel<EntityModel<TopicDetailsDTO>>> getTopicsByUserResponse(
            @PathVariable Long id,
            @PageableDefault(sort = "creationDate")
            Pageable pageable,
            PagedResourcesAssembler<TopicDetailsDTO> assembler) {
        Page<TopicDetailsDTO> topics = userService.getTopicsByUserResponse(id, pageable);
        PagedModel<EntityModel<TopicDetailsDTO>> pagedModel = assembler.toModel(topics);
        return ResponseEntity.ok(pagedModel);
    }

}
