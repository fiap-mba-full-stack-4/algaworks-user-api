package com.algaworks.userapi.infra.persistence.jpa.repository;

import java.util.Optional;

import com.algaworks.userapi.core.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleJPARepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
