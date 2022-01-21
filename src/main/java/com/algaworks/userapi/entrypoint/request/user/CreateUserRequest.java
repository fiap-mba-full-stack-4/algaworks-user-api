package com.algaworks.userapi.entrypoint.request.user;

import java.time.LocalDateTime;

import com.algaworks.userapi.core.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserRequest {

    private String name;
    private String email;
    private LocalDateTime birthday;
    private String profession;
    private String phoneNumber;
}
