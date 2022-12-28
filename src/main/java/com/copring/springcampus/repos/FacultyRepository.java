package com.copring.springcampus.repos;

import com.copring.springcampus.models.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Optional<Faculty> findByName(String name);

    boolean existsByName(String name);
}
