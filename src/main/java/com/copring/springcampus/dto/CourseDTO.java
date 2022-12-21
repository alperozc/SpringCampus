package com.copring.springcampus.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CourseDTO {

    private Long id;
    private String name;
    private String description;
    private DepartmentDTO department;
    private List<LessonDTO> lessons;

}
