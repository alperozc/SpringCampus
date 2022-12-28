package com.copring.springcampus.repos;

import com.copring.springcampus.models.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstituteRepository extends JpaRepository<Institute, Long> {
    Optional<Institute> findByName(String name);

    boolean existsByName(String name);
}

