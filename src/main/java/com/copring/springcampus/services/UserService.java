package com.copring.springcampus.services;

import com.copring.springcampus.dto.DepartmentDTO;
import com.copring.springcampus.dto.RegisterDTO;
import com.copring.springcampus.dto.UserDTO;
import com.copring.springcampus.enums.RoleEnums;
import com.copring.springcampus.models.Role;
import com.copring.springcampus.models.User;
import com.copring.springcampus.repos.RoleRepository;
import com.copring.springcampus.repos.UserRepository;
import com.copring.springcampus.utils.JWTUtils;
import com.copring.springcampus.utils.responses.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
    @Autowired
    private ModelMapper modelMapper;


    public UserDTO register(RegisterDTO userDTO) {
        // Check if the user already exists
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));

        var saved_user = userRepository.save(user);
        return modelMapper.map(saved_user, UserDTO.class);
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if (bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return jwtUtils.generateToken(user.getId());
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
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

    public UserDTO getUserDTOById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return modelMapper.map(user, UserDTO.class);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> modelMapper.map(user, UserDTO.class)).collect(Collectors.toList());
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        // Check if the user already exists
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getUsername()));
        User saved = userRepository.save(user);
        return modelMapper.map(saved, UserDTO.class);
    }

    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setUsername(userDTO.getUsername());
        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }




}
