package com.algaworks.userapi.jpa.provider;

import java.util.List;
import java.util.Optional;

import com.algaworks.userapi.core.entity.User;
import com.algaworks.userapi.core.enums.AuthenticationProviderEnum;
import com.algaworks.userapi.core.gateway.UserGateway;
import com.algaworks.userapi.jpa.repository.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserJPAProvider implements UserGateway {

    private final UserJPARepository userJPARepository;

    @Override
    public User save(final User user) {
        return userJPARepository.save(user);
    }

    @Override
    public Optional<User> findById(final Long id) {
        return userJPARepository.findById(id);
    }

    @Override
    public List<User> findAll() {
        return userJPARepository.findAll();
    }

    @Override
    public Optional<User> findByEmail(final String email) {
        return userJPARepository.findByEmail(email);
    }

    @Override
    public void inactivate(User user) {
        userJPARepository.save(user);
    }
}
