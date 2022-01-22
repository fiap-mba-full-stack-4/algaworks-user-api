package com.algaworks.userapi.core.mapper;

import java.util.List;
import java.util.stream.Collectors;

import com.algaworks.userapi.core.entity.User;
import com.algaworks.userapi.core.enums.UserStatus;
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
                .password(createUserRequest.getPassword())
                .status(UserStatus.ACTIVE)
                .build();
    }

    public User updateUser(final User user, final UpdateUserRequest updateUserRequest) {
        user.setName(updateUserRequest.getName());
        user.setPassword(updateUserRequest.getPassword());
        return user;
    }


    public UserResponse toResponse(final User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .status(user.getStatus())
                .build();

    }

    public List<UserResponse> toResponse(final List<User> userList) {
        return userList.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

    }
}
