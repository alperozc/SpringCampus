package com.copring.springcampus.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@Builder
public class StudentDTO {

    private Long id;
    private String name;
    private String surname;
    private Date dateOfBirth;
    private Date registrationDate;
    private String termAddress;
    private Long departmentId;

}
