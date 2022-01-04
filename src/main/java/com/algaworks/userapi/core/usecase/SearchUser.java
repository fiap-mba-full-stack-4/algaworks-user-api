package com.algaworks.userapi.core.usecase;

import com.algaworks.userapi.core.entity.User;
import com.algaworks.userapi.core.exceptions.NotFoundException;
import com.algaworks.userapi.core.gateway.UserGateway;
import com.algaworks.userapi.entrypoint.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SearchUser {

    private final UserGateway userGateway;

    public User byId(final Long id) {
        return userGateway.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("User [%s] not found", id)));
    }
}
