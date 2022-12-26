package com.copring.springcampus.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class CourseDTO {

    private Long id;
    private String name;
    private String description;
    private DepartmentDTO department;
    private List<LessonDTO> lessons;

}
