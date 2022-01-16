package com.algaworks.userapi.config.login.oauth2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import java.io.IOException;
import java.util.Locale;

import static com.algaworks.userapi.core.enums.UserRoleEnum.ROLE_USER;
import static com.algaworks.userapi.core.enums.UserStatus.ACTIVE;
import static java.util.Collections.singletonList;

import com.algaworks.userapi.core.entity.User;
import com.algaworks.userapi.core.enums.AuthenticationProviderEnum;
import com.algaworks.userapi.core.gateway.RoleGateway;
import com.algaworks.userapi.core.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserGateway userGateway;
    private final RoleGateway roleGateway;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final Authentication authentication)
            throws ServletException, IOException, UsernameNotFoundException {
        final var oauth2User = (CustomOAuth2User) authentication.getPrincipal();
        registerOrUpdateUser(oauth2User);
        super.onAuthenticationSuccess(request, response, authentication);
    }

    @Transactional
    protected void registerOrUpdateUser(final CustomOAuth2User oauth2User) {
        final var oauth2ClientName = oauth2User.getOauth2ClientName();
        final var userName = oauth2User.getName();
        final var userEmail = oauth2User.getEmail();
        final var authenticationType =
                AuthenticationProviderEnum.valueOf(oauth2ClientName.toUpperCase(
                        Locale.ROOT));

        userGateway.findByEmail(userEmail)
                .ifPresentOrElse(
                        savedUser -> {
                            if (!authenticationType.equals(savedUser.getAuthenticationType())) {
                                savedUser.setAuthenticationType(authenticationType);
                                userGateway.save(savedUser);
                            }
                        }
                        , () -> roleGateway.findByName(ROLE_USER.getName()).ifPresent(savedRole -> {
                            final var user = User.builder()
                                    .name(userName)
                                    .email(userEmail)
                                    .authenticationType(authenticationType)
                                    .status(ACTIVE)
                                    .roles(singletonList(savedRole))
                                    .build();

                            userGateway.save(user);
                        })
                );
    }
}
