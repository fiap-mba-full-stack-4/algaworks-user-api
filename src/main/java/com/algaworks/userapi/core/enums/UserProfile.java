package com.algaworks.userapi.core.enums;

import lombok.Getter;

@Getter
public enum UserProfile {

    STUDENT("STUDENT"),
    ADMINISTRATOR("ADMINISTRATOR");

    private final String profile;

    UserProfile(final String profile) {
        this.profile = profile;
    }
}
