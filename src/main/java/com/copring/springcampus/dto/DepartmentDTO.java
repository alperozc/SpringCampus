package com.copring.springcampus.dto;

import com.copring.springcampus.models.Student;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {

    private Long id;
    private String name;
    private Long facultyId;
    private Long instituteId;
    public Long getFacultyId() {
        return facultyId;
    }
    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public List<Student> students;
}
