package com.copring.springcampus.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class InstituteDTO {
    private Long id;
    private String name;
    private List<DepartmentDTO> departments;
}
