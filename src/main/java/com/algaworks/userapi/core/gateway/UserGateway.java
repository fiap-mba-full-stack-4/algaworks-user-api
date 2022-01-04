package com.algaworks.userapi.core.gateway;

import java.util.Optional;

import com.algaworks.userapi.core.entity.User;


public interface UserGateway {

    User save(User user);

    Optional<User> findById(Long id);

    void inactivate(User user);
}
