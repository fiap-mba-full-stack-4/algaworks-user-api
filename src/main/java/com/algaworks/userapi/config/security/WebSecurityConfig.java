package com.algaworks.userapi.config.security;

import javax.sql.DataSource;

import com.algaworks.userapi.config.login.local.LocalLoginSuccessHandler;
import com.algaworks.userapi.config.login.oauth2.OAuth2LoginSuccessHandler;
import com.algaworks.userapi.core.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final LocalLoginSuccessHandler localLoginSuccessHandler;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    private static final String LOGIN_URI = "/users/login";
    private static final String REDIRECT_URI = "/";
    private static final String LOGOUT_URI = "/users/logout";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(LOGIN_URI, "/oauth2/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                    .usernameParameter("email")
                    .passwordParameter("password")
                    .successHandler(localLoginSuccessHandler)
                    .permitAll()
                .and()
                .oauth2Login()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                    .and()
                    .successHandler(oAuth2LoginSuccessHandler)
                .defaultSuccessUrl(REDIRECT_URI)
                .and()
                .logout()
                    .logoutUrl(LOGOUT_URI)
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/").permitAll()
                .and().rememberMe().tokenRepository(persistentTokenRepository())
                .and().exceptionHandling().accessDeniedPage("/403");
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        final var tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
