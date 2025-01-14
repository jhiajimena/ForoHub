package lescano.forohub.controller;


import lescano.forohub.dto.user.CreateUserDTO;
import lescano.forohub.dto.user.DetailsResponseUserDTO;
import lescano.forohub.dto.auth.AuthLoginRequest;
import lescano.forohub.dto.auth.AuthResponse;
import lescano.forohub.service.user.UserDetailsServiceImpl;
import lescano.forohub.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/api/auth")
@Tag(name="Authentication", description = "Controller for authentication")
public class AuthController {


    private final UserDetailsServiceImpl userDetailsService;
    private final UserService userService;

    public AuthController (UserDetailsServiceImpl userDetailsService, UserService userService){
        this.userDetailsService=userDetailsService;
        this.userService=userService;
    }

    @PostMapping("/login")
    @Operation(
            summary = "Login User",
            description = "Authenticate a user and return the authentication token along with user details",
            tags = {"Login,Authentication"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Authentication request with email and password",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AuthLoginRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "User logged successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponse.class)
                            )
                    )
            }
    )
    public ResponseEntity<AuthResponse> login (@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(userDetailsService.loginUser(userRequest), HttpStatus.OK);
    }


    @PostMapping("/signUp")
    @Operation(
            summary = "Sign up a new user",
            description = "Register a new user and return the user details.",
            tags = {"Sign Up,Authentication"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Request body with user details for registration",
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CreateUserDTO.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "User created successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = DetailsResponseUserDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid input data",
                            content = @Content(
                                    mediaType = "application/json"
                            )
                    )
            }
    )
    public ResponseEntity<DetailsResponseUserDTO> singUp(@RequestBody @Valid CreateUserDTO data,
                                                             UriComponentsBuilder uriComponentsBuilder) {
        DetailsResponseUserDTO user = userService.createUser(data);
        URI url = uriComponentsBuilder.path("/api/user/{id}").buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(url).body(user);
    }

}
