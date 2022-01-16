package com.algaworks.userapi.core.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {

    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");

    private final String name;

    UserRoleEnum(final String role) {
        this.name = role;
    }
}
