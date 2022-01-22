package com.algaworks.userapi.core.usecase;

import java.util.Objects;

import com.algaworks.userapi.core.entity.User;
import com.algaworks.userapi.core.exceptions.LoginException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginUser {

    private final SearchUser searchUser;

    public User process(final String email, final String password) {
        final var user = searchUser.byEmail(email);

        if (Objects.equals(user.getPassword(), password))
            return user;

        throw new LoginException("User email or password incorrect.");
    }
}
