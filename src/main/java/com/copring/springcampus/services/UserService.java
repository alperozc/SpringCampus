package com.copring.springcampus.services;

import com.copring.springcampus.dto.UserDTO;
import com.copring.springcampus.enums.RoleEnums;
import com.copring.springcampus.models.Role;
import com.copring.springcampus.models.User;
import com.copring.springcampus.repos.RoleRepository;
import com.copring.springcampus.repos.UserRepository;
import com.copring.springcampus.utils.JWTUtils;
import com.copring.springcampus.utils.responses.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public Set<String> getRolesByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Set<String> roles = user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet());
        return roles;
    }

    public void updateRoles(Long userId, Set<String> roleNames) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Set<Role> roles = roleNames.stream().map(roleName -> roleRepository.findByName(RoleEnums.valueOf(roleName))).map(Optional::get).collect(Collectors.toSet());
        user.setRoles(roles);
        userRepository.save(user);
    }


}
