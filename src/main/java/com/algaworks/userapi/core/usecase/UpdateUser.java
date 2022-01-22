package com.algaworks.userapi.core.usecase;

import com.algaworks.userapi.core.entity.User;
import com.algaworks.userapi.core.exceptions.NotFoundException;
import com.algaworks.userapi.core.gateway.UserGateway;
import com.algaworks.userapi.core.mapper.UserMapper;
import com.algaworks.userapi.entrypoint.request.user.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateUser {

    private final UserMapper userMapper;
    private final UserGateway userGateway;

    public User processById(Long id, UpdateUserRequest updateUserRequest) {
        final var userEntity =
                userGateway.findById(id).orElseThrow(() -> new NotFoundException(
                        String.format("User {%d} not found", id)
                ));

        final var newUser = userMapper.updateUser(userEntity, updateUserRequest);
        return userGateway.save(newUser);
    }
}
