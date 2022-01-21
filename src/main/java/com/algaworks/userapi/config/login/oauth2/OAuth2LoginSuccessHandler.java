package com.algaworks.userapi.config.login.oauth2;

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
import com.algaworks.userapi.core.usecase.EmailSender;
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
    private final EmailSender sendEmail;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response,
                                        final Authentication authentication)
            throws IOException, UsernameNotFoundException {
        final var oauth2User = (CustomOAuth2User) authentication.getPrincipal();
        final var user = registerOrUpdateUser(oauth2User);
        sendEmail.sendWelcomeEmail(user.getEmail(), user.getUsername());
        response.sendRedirect(String.format("/users/%d", user.getId()));
    }

    @Transactional
    protected User registerOrUpdateUser(final CustomOAuth2User oauth2User) {
        final var oauth2ClientName = oauth2User.getOauth2ClientName();
        final var userName = oauth2User.getName();
        final var userEmail = oauth2User.getEmail();
        final var authenticationType =
                AuthenticationProviderEnum.valueOf(oauth2ClientName.toUpperCase(
                        Locale.ROOT));

        return userGateway.findByEmail(userEmail)
                .map(user -> updateUser(user, authenticationType))
                .orElseGet(() -> createUser(userName, userEmail, authenticationType));
    }

    private User updateUser(User user, AuthenticationProviderEnum authenticationType) {
        user.setAuthenticationType(authenticationType);
        return userGateway.save(user);
    }

    private User createUser(String userName, String userEmail,
                          AuthenticationProviderEnum authenticationType) {

        final var userRole = roleGateway.findByName(ROLE_USER.getName())
                .orElseThrow();

        final var user = User.builder()
                .name(userName)
                .email(userEmail)
                .authenticationType(authenticationType)
                .status(ACTIVE)
                .roles(singletonList(userRole))
                .build();

        return userGateway.save(user);
    }
}
