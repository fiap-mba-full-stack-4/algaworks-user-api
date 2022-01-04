package com.algaworks.userapi.core.usecase;

import static com.algaworks.userapi.core.enums.UserStatus.INACTIVE;

import com.algaworks.userapi.core.exceptions.NotFoundException;
import com.algaworks.userapi.core.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InactivateUser {

    private final UserGateway userGateway;

    public void processById(Long id) {
        final var userEntity = userGateway.findById(id).orElseThrow(() -> new NotFoundException("a"));
        userEntity.setStatus(INACTIVE);
        userGateway.inactivate(userEntity);
    }
}
