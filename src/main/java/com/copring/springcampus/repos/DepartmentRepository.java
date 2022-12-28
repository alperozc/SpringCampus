package com.copring.springcampus.repos;

import com.copring.springcampus.models.Department;
import com.copring.springcampus.models.Faculty;
import com.copring.springcampus.models.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    List<Department> findAllByFaculty(Faculty faculty);
    Optional<Department> findByName(String name);

    boolean existsByName(String name);

    List<Department> findAllByInstitute(Institute institute);
}
