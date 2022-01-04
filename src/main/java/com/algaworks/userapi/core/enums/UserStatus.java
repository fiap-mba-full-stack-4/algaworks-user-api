package com.algaworks.userapi.core.enums;

public enum UserStatus {

    ACTICE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String status;

    UserStatus(final String status) {
        this.status = status;
    }
}
