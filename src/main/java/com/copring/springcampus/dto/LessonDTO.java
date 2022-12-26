package com.copring.springcampus.dto;

import lombok.*;

import java.time.LocalTime;
@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class LessonDTO {

    private Long id;
    private String name;
    private String description;
    private LocalTime startTime;
    private LocalTime endTime;
    private CourseDTO course;
    private DepartmentDTO department;
}
