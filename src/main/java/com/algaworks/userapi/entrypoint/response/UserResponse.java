package com.algaworks.userapi.entrypoint.response;

import java.io.Serializable;

import com.algaworks.userapi.core.enums.UserRoleEnum;
import com.algaworks.userapi.core.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String email;
    private UserRoleEnum profile;
    private UserStatus status;
}
