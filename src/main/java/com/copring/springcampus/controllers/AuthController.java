package com.copring.springcampus.controllers;

import com.copring.springcampus.dto.LoginRequest;
import com.copring.springcampus.dto.RefreshRequest;
import com.copring.springcampus.dto.UserDTO;
import com.copring.springcampus.models.User;
import com.copring.springcampus.services.UserService;
import com.copring.springcampus.utils.responses.LoginResponse;
import com.copring.springcampus.utils.responses.RefreshResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO) {
        User user = userService.register(userDTO);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        String token = userService.login(loginRequest.getUsername(), loginRequest.getPassword());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@Valid @RequestBody RefreshRequest refreshRequest) {
        String token = userService.refreshToken(refreshRequest.getOldToken());
        return ResponseEntity.ok(new RefreshResponse(token));
    }
}
