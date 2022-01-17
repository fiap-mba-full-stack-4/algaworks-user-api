package com.algaworks.userapi.core.enums;

import lombok.Getter;

@Getter
public enum UserRoleEnum {

    ROLE_USER("ROLE_USER"),
    ROLE_ADMIN("ROLE_ADMIN");

    private final String name;

    UserRoleEnum(final String role) {
        this.name = role;
    }
}
