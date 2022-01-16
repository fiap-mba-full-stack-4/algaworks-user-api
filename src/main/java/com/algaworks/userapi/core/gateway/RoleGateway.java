package com.algaworks.userapi.core.gateway;

import java.util.Optional;

import com.algaworks.userapi.core.entity.Role;

public interface RoleGateway {
    Optional<Role> findByName(String name);

    Role save(Role role);
}
