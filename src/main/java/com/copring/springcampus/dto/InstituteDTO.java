package com.copring.springcampus.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class InstituteDTO {
    private Long id;
    private String name;
    private List<DepartmentDTO> departments;
}
