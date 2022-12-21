package com.copring.springcampus.repos;

import com.copring.springcampus.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
