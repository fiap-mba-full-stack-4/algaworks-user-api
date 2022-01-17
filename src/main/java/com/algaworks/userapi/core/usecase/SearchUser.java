package com.algaworks.userapi.core.usecase;

import java.util.List;

import com.algaworks.userapi.core.entity.User;
import com.algaworks.userapi.core.exceptions.NotFoundException;
import com.algaworks.userapi.core.gateway.UserGateway;
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

    public User byEmail(final String email) {
        return userGateway.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(
                        String.format("User email [%s] not found", email)));
    }

    public List<User> findAll() {
        return userGateway.findAll();
    }
}
