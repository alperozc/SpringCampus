package com.copring.springcampus.repos;

import com.copring.springcampus.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}