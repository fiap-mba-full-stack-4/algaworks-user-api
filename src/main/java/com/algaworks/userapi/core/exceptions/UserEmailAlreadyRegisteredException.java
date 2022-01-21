package com.algaworks.userapi.core.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UserEmailAlreadyRegisteredException extends ResponseStatusException {
    public UserEmailAlreadyRegisteredException(final String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
