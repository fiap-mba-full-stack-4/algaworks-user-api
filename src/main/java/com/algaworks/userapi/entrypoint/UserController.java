package com.algaworks.userapi.entrypoint;

import com.algaworks.userapi.core.mapper.UserMapper;
import com.algaworks.userapi.core.usecase.CreateUser;
import com.algaworks.userapi.core.usecase.InactivateUser;
import com.algaworks.userapi.core.usecase.SearchUser;
import com.algaworks.userapi.core.usecase.EmailSender;
import com.algaworks.userapi.core.usecase.UpdateUser;
import com.algaworks.userapi.entrypoint.request.user.CreateUserRequest;
import com.algaworks.userapi.entrypoint.request.user.UpdateUserRequest;
import com.algaworks.userapi.entrypoint.response.UserResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@Slf4j
@RequiredArgsConstructor
public class UserController {

    private final SearchUser searchUser;
    private final CreateUser createUser;
    private final UpdateUser updateUser;
    private final InactivateUser deleteUser;
    private final UserMapper userMapper;
    private final EmailSender sendEmail;

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        final var userList = searchUser.findAll();
        final var userListResponse =  userMapper.toResponse(userList);
        return ResponseEntity.ok(userListResponse);
    }

    @GetMapping("{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable final Long id) {
        final var userEntity = searchUser.byId(id);
        final var userResponse =  userMapper.toResponse(userEntity);
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> create(
            @RequestBody final CreateUserRequest createUserRequest
    ) {
        final var userEntity = createUser.process(createUserRequest);
        final var userResponse =  userMapper.toResponse(userEntity);
        sendEmail.sendWelcomeEmail(createUserRequest.getEmail(), createUserRequest.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<UserResponse> update(
            @PathVariable final Long id,
            @RequestBody final UpdateUserRequest updateUserRequest) {
        final var updatedUserEntity = updateUser.processById(id, updateUserRequest);
        final var userResponse =  userMapper.toResponse(updatedUserEntity);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<UserResponse> deleteById(
            @PathVariable(name = "id") final Long id) {
        deleteUser.processById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
