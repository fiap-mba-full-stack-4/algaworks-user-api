package com.algaworks.userapi.infra.jpa.provider;

import java.util.Optional;

import com.algaworks.userapi.core.entity.Role;
import com.algaworks.userapi.core.gateway.RoleGateway;
import com.algaworks.userapi.infra.jpa.repository.RoleJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleJPAProvider implements RoleGateway {

    private final RoleJPARepository roleJPARepository;

    @Override
    public Optional<Role> findByName(final String name) {
        return roleJPARepository.findByName(name);
    }

    @Override
    public Role save(Role role) {
        return roleJPARepository.save(role);
    }
}
