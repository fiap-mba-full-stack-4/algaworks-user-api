package com.algaworks.userapi.core.gateway;

import java.util.List;
import java.util.Optional;

import com.algaworks.userapi.core.entity.User;

public interface UserGateway {

    Optional<User> findById(Long id);

    List<User> findAll();

    Optional<User> findByEmail(String email);

    User save(User user);

    void inactivate(User user);
}
