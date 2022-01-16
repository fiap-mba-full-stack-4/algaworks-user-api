package com.algaworks.userapi.config.initializer;

import javax.transaction.Transactional;

import java.util.List;

import static com.algaworks.userapi.core.enums.AuthenticationProviderEnum.*;
import static com.algaworks.userapi.core.enums.UserRoleEnum.ROLE_ADMIN;
import static com.algaworks.userapi.core.enums.UserRoleEnum.ROLE_USER;
import static com.algaworks.userapi.core.enums.UserStatus.ACTIVE;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import com.algaworks.userapi.core.entity.Privilege;
import com.algaworks.userapi.core.entity.Role;
import com.algaworks.userapi.core.entity.User;
import com.algaworks.userapi.core.gateway.PrivilegeGateway;
import com.algaworks.userapi.core.gateway.RoleGateway;
import com.algaworks.userapi.core.gateway.UserGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {

    private final UserGateway userGateway;
    private final RoleGateway roleGateway;
    private final PrivilegeGateway privilegeGateway;
    private final PasswordEncoder passwordEncoder;

    private boolean alreadySetup = false;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup) return;

        createRoles();
        final var optAdminRole = roleGateway.findByName("ROLE_ADMIN");

        optAdminRole.ifPresentOrElse(
                adminRole -> {
                    final var user = User.builder()
                            .email("admin@admin.com")
                            .name("admin")
                            .password(passwordEncoder.encode("admin"))
                            .roles(singletonList(adminRole))
                            .status(ACTIVE)
                            .authenticationType(LOCAL)
                            .build();

                    userGateway.save(user);
                },
                () -> {
                    throw new RuntimeException("Initial load failed...");
                }
        );

        alreadySetup = true;
    }

    private void createRoles() {
        final var createUserPrivilege
                = createPrivilegeIfNotFound("CREATE_USER_PRIVILEGE");

        final var readUserPrivilege
                = createPrivilegeIfNotFound("READ_USER_PRIVILEGE");

        final var readAllUsersPrivilege
                = createPrivilegeIfNotFound("READ_ALL_USERS_PRIVILEGE");

        final var updateUserPrivilege
                = createPrivilegeIfNotFound("UPDATE_USER_PRIVILEGE");

        final var excludeUserPrivilege
                = createPrivilegeIfNotFound("UPDATE_USER_PRIVILEGE");

        final var adminPrivileges = asList(
                createUserPrivilege, readUserPrivilege, readAllUsersPrivilege,
                updateUserPrivilege, excludeUserPrivilege);

        final var userPrivileges = singletonList(readUserPrivilege);

        createRoleIfNotFound(ROLE_ADMIN.getName(), adminPrivileges);
        createRoleIfNotFound(ROLE_USER.getName(), userPrivileges);
    }

    Privilege createPrivilegeIfNotFound(final String name) {
        return privilegeGateway.findByName(name)
                .orElseGet(() -> {
                    final var privilege = Privilege.builder()
                            .name(name)
                            .build();

                    return privilegeGateway.save(privilege);
                });
    }

    Role createRoleIfNotFound(final String name, final List<Privilege> privileges) {
        return roleGateway.findByName(name)
                .orElseGet(() -> {
                    final var role = Role.builder()
                            .name(name)
                            .privileges(privileges)
                            .build();

                    return roleGateway.save(role);
                });
    }
}
