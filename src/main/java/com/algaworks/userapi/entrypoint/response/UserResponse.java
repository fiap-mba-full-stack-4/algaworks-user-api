package com.algaworks.userapi.entrypoint.response;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.algaworks.userapi.core.enums.UserRoleEnum;
import com.algaworks.userapi.core.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private UserRoleEnum profile;
    private String name;
    private LocalDateTime birthday;
    private String email;
    private String phoneNumber;
    private String profession;
    private UserStatus status;
}
