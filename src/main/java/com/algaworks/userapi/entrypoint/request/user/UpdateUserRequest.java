package com.algaworks.userapi.entrypoint.request.user;

import java.time.LocalDateTime;

import com.algaworks.userapi.core.enums.UserRoleEnum;
import com.algaworks.userapi.core.enums.UserStatus;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String email;
    private UserRoleEnum profile;
    private LocalDateTime birthday;
    private String profession;
    private String phoneNumber;
    private UserStatus status;
}
