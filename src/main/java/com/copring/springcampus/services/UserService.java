package com.copring.springcampus.services;

import com.copring.springcampus.dto.UserDTO;
import com.copring.springcampus.models.User;
import com.copring.springcampus.repos.UserRepository;
import com.copring.springcampus.utils.JWTUtils;
import com.copring.springcampus.utils.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    public User register(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        user.setRoles(userDTO.getRoles());
        return userRepository.save(user);
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return jwtUtils.generateToken(user.getId());
        }
        throw new BadCredentialsException("Incorrect password");
    }

    public String refreshToken(String oldToken) {
        String subject = jwtUtils.getSubjectFromToken(oldToken);
        if (jwtUtils.isTokenExpired(oldToken)) {
            Long userId = Long.parseLong(subject);
            User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            return jwtUtils.generateToken(userId);
        }
        throw new BadCredentialsException("Token is not expired");
    }


}
