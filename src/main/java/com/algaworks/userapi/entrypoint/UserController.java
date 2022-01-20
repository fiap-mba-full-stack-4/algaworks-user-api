package com.algaworks.userapi.entrypoint;

import java.util.List;

import com.algaworks.userapi.core.mapper.UserMapper;
import com.algaworks.userapi.core.usecase.CreateUser;
import com.algaworks.userapi.core.usecase.InactivateUser;
import com.algaworks.userapi.core.usecase.LoginUser;
import com.algaworks.userapi.core.usecase.SearchUser;
import com.algaworks.userapi.core.usecase.UpdateUser;
import com.algaworks.userapi.entrypoint.request.user.CreateUserRequest;
import com.algaworks.userapi.entrypoint.request.user.LoginRequest;
import com.algaworks.userapi.entrypoint.request.user.UpdateUserRequest;
import com.algaworks.userapi.entrypoint.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final LoginUser loginUser;
    private final SearchUser searchUser;
    private final CreateUser createUser;
    private final UpdateUser updateUser;
    private final InactivateUser deleteUser;
    private final UserMapper userMapper;

    private final String ROLE_ADMIN = "ROLE_ADMIN";
    private final String ROLE_USER = "ROLE_USER";

    @GetMapping("/login")
    public String login(
            @RequestBody final LoginRequest loginRequest
    ) {
        final var email = loginRequest.getEmail();
        final var password = loginRequest.getPassword();
        loginUser.process(email, password);
        return "/login";
    }

    @GetMapping
    @Secured({ROLE_ADMIN, ROLE_USER})
    public ResponseEntity<List<UserResponse>> findAll() {
        final var userList = searchUser.findAll();
        final var userListResponse =  userMapper.toResponse(userList);
        return ResponseEntity.ok(userListResponse);
    }

    @GetMapping("{id}")
    @Secured({ROLE_ADMIN, ROLE_USER})
    public ResponseEntity<UserResponse> findById(@PathVariable final Long id) {
        final var userEntity = searchUser.byId(id);
        final var userResponse =  userMapper.toResponse(userEntity);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Secured({ROLE_ADMIN, ROLE_USER})
    public ResponseEntity<UserResponse> create(
            @RequestBody final CreateUserRequest createUserRequest
    ) {
        final var userEntity = createUser.process(createUserRequest);
        final var userResponse =  userMapper.toResponse(userEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PutMapping("{id}")
    @Secured({ROLE_ADMIN, ROLE_USER})
    public ResponseEntity<UserResponse> update(
            @PathVariable final Long id,
            @RequestBody final UpdateUserRequest updateUserRequest) {
        final var updatedUserEntity = updateUser.processById(id, updateUserRequest);
        final var userResponse =  userMapper.toResponse(updatedUserEntity);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("{id}")
    @Secured({ROLE_ADMIN, ROLE_USER})
    public ResponseEntity<UserResponse> deleteById(
            @PathVariable(name = "id") final Long id) {
        deleteUser.processById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
