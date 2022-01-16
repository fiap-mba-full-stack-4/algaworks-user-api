package com.algaworks.userapi.core.mapper;

import com.algaworks.userapi.core.entity.User;
import com.algaworks.userapi.entrypoint.request.user.CreateUserRequest;
import com.algaworks.userapi.entrypoint.request.user.UpdateUserRequest;
import com.algaworks.userapi.entrypoint.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserMapper {

    public User toUser(final CreateUserRequest createUserRequest) {
        return User.builder()
                .name(createUserRequest.getName())
                .email(createUserRequest.getEmail())
                .birthday(createUserRequest.getBirthday())
                .phoneNumber(createUserRequest.getPhoneNumber())
                .status(createUserRequest.getStatus())
                .build();
    }

    public User toUser(final UpdateUserRequest updateUserRequest) {
        return User.builder()
                .name(updateUserRequest.getName())
                .email(updateUserRequest.getEmail())
                .birthday(updateUserRequest.getBirthday())
                .phoneNumber(updateUserRequest.getPhoneNumber())
                .status(updateUserRequest.getStatus())
                .build();
    }


    public UserResponse toResponse(final User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .birthday(user.getBirthday())
                .phoneNumber(user.getPhoneNumber())
                .status(user.getStatus())
                .build();

    }

    public User update(User currentUser, UpdateUserRequest updateUserRequest) {
        final var updateUser = toUser(updateUserRequest);
        updateUser.setId(currentUser.getId());
        return updateUser;
    }
}
