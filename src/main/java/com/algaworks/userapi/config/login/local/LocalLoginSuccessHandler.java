package com.algaworks.userapi.config.login.local;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.algaworks.userapi.core.gateway.UserGateway;
import com.algaworks.userapi.core.usecase.CreateUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private CreateUser createUser;
    private UserGateway userGateway;
    private MyUserDetailService myUserDetailService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws IOException {
        final var userEmail = authentication.getName();
        final var user = userGateway.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("The informed e-mail: [%s] was not found"));
        response.sendRedirect(String.format("/users/%d", user.getId()));
    }
}
