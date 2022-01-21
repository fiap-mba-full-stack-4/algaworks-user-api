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
                .birthday(createUserRequest.getBirthday())
                .phoneNumber(createUserRequest.getPhoneNumber())
                .status(UserStatus.ACTIVE)
                .build();
    }

    public User toUser(final UpdateUserRequest updateUserRequest) {
        return User.builder()
                .name(updateUserRequest.getName())
                .birthday(updateUserRequest.getBirthday())
                .phoneNumber(updateUserRequest.getPhoneNumber())
                .status(UserStatus.ACTIVE)
                .build();
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

    public User update(User currentUser, UpdateUserRequest updateUserRequest) {
        final var updateUser = toUser(updateUserRequest);
        updateUser.setId(currentUser.getId());
        return updateUser;
    }
}
