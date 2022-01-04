package com.algaworks.userapi.core.usecase;

import com.algaworks.userapi.core.entity.User;
import com.algaworks.userapi.core.gateway.UserGateway;
import com.algaworks.userapi.core.mapper.UserMapper;
import com.algaworks.userapi.entrypoint.request.user.CreateUserRequest;
import com.algaworks.userapi.entrypoint.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateUser {

    private final UserGateway userGateway;
    private final UserMapper userMapper;

    public User process(final CreateUserRequest createUserRequest) {
        final var userEntity = userMapper.toUser(createUserRequest);
        return userGateway.save(userEntity);
    }
}
