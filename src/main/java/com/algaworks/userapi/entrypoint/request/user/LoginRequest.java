package com.algaworks.userapi.entrypoint.request.user;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("email")
    private String email;

    @JsonProperty("password")
    private String password;
}
