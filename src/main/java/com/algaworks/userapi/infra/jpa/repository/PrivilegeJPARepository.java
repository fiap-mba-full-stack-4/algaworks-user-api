package com.algaworks.userapi.infra.jpa.repository;

import java.util.Optional;

import com.algaworks.userapi.core.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeJPARepository extends JpaRepository<Privilege, Long> {

    Optional<Privilege> findByName(String name);
}
