package com.algaworks.userapi.entrypoint.request.user;

import java.time.LocalDateTime;

import com.algaworks.userapi.core.enums.UserProfile;
import com.algaworks.userapi.core.enums.UserStatus;
import lombok.Data;

@Data
public class CreateUserRequest {

    private String name;
    private UserProfile profile;
    private String email;
    private LocalDateTime birthday;
    private String profession;
    private String phoneNumber;
    private UserStatus status;
}
