package com.algaworks.userapi.core.usecase;

import com.algaworks.userapi.core.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUser {

    private final SearchUser searchUser;

    public User process(final String email, final String password) {
        final var user = searchUser.byEmail(email);
        if (user.getPassword().equals(password)) return user;

        throw new RuntimeException("User email or password incorrect.");
    }
}
