package com.algaworks.userapi.entrypoint.request.user;

import java.time.LocalDateTime;

import com.algaworks.userapi.core.enums.UserProfile;
import com.algaworks.userapi.core.enums.UserStatus;
import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private String email;
    private UserProfile profile;
    private LocalDateTime birthday;
    private String profession;
    private String phoneNumber;
    private UserStatus status;
}
