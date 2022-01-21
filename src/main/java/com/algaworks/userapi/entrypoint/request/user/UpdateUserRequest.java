package com.algaworks.userapi.entrypoint.request.user;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UpdateUserRequest {
    private String name;
    private LocalDateTime birthday;
    private String profession;
    private String phoneNumber;
}
