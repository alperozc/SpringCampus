package com.copring.springcampus.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String name;
    private String surname;
    private Set<String> roles;
}


