package com.copring.springcampus.dto;

import com.copring.springcampus.models.Department;
import com.copring.springcampus.models.User;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Long id;
    private Long year;
    private Long semester;
    private User user;
    private Department department;

}


