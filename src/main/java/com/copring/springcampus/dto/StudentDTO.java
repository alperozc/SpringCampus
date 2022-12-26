package com.copring.springcampus.dto;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Long id;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private Date registrationDate;
    private String termAddress;
    private Long departmentId;

}
