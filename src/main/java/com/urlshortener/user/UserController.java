package com.urlshortener.user;

import com.urlshortener.error.ErrorResponse;
import com.urlshortener.user.dto.RegisterUserRequest;
import com.urlshortener.user.dto.RegisterUserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller responsible for user authentication and registration endpoints.
 * <p>
 * This class handles the HTTP requests related to user authentication, including registering new users.
 * It also handles validation of user input and provides relevant responses, including success and error messages.
 * </p>
 */
@Tag(name = "1. Authentication", description = "Endpoints for user authentication and registration")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * Registers a new user with provided credentials.
     * <p>
     * This endpoint registers a new user by accepting a {@link RegisterUserRequest} in the request body,
     * and returns a {@link RegisterUserResponse} with the user's registration details if successful.
     * </p>
     *
     * @param request the user's registration request containing necessary details (login, email, password)
     * @return a {@link ResponseEntity} containing the registration response with status 201 (CREATED)
     */
    @Operation(
            summary = "Register a new user",
            description = "Registers a new user with provided credentials")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "User registered successfully",
                    content = @Content(schema = @Schema(implementation = RegisterUserResponse.class))),
            @ApiResponse(
                    responseCode = "400",
                    description = "Input data is not correct (e.g., incorrect login or email format)",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "BadRequestExample",
                                    ref = "#/components/examples/BadRequestExample"))),
            @ApiResponse(
                    responseCode = "409",
                    description = "User with login or email already exists",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponse.class),
                            examples = @ExampleObject(
                                    name = "ConflictExample",
                                    ref = "#/components/examples/ConflictExample")))
    })
    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> registerUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(schema = @Schema(implementation = RegisterUserRequest.class)))
            @RequestBody RegisterUserRequest request) {
        RegisterUserResponse response = userService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
