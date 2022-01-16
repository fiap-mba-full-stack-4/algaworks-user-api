package com.algaworks.userapi.core.gateway;

import java.util.Optional;

import com.algaworks.userapi.core.entity.Privilege;

public interface PrivilegeGateway {
    Optional<Privilege> findByName(String name);
    Privilege save(Privilege privilege);
}
