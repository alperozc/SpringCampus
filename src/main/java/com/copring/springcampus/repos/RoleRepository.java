package com.copring.springcampus.repos;

import com.copring.springcampus.enums.RoleEnums;
import com.copring.springcampus.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnums name);
}
