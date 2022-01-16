package com.algaworks.userapi.config.login.local;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import com.algaworks.userapi.core.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LocalLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private UserGateway userGateway;
    private MyUserDetailService myUserDetailService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication)
            throws ServletException, IOException {
        MyUserDetailService userDetails = (MyUserDetailService) authentication.getPrincipal();
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
