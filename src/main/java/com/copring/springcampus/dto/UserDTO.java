package com.copring.springcampus.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private Set<String> roles;
}
