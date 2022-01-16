package com.algaworks.userapi.jpa.repository;

import java.util.Optional;

import com.algaworks.userapi.core.entity.User;
import com.algaworks.userapi.core.enums.AuthenticationProviderEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJPARepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
