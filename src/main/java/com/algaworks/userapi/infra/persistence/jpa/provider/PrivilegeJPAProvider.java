package com.algaworks.userapi.infra.persistence.jpa.provider;

import java.util.Optional;

import com.algaworks.userapi.core.entity.Privilege;
import com.algaworks.userapi.core.gateway.PrivilegeGateway;
import com.algaworks.userapi.infra.persistence.jpa.repository.PrivilegeJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrivilegeJPAProvider implements PrivilegeGateway {

    private final PrivilegeJPARepository privilegeRepository;

    @Override
    public Optional<Privilege> findByName(final String name) {
        return privilegeRepository.findByName(name);
    }

    @Override
    public Privilege save(final Privilege privilege) {
        return privilegeRepository.save(privilege);
    }
}
