package com.copring.springcampus.repos;

import com.copring.springcampus.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByName(String name);

    Optional<Student> findByRegistrationDate(LocalDate registrationDate);

}
