package com.copring.springcampus.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class FacultyDTO {
    private Long id;
    private String name;
    private List<DepartmentDTO> departments;
}
