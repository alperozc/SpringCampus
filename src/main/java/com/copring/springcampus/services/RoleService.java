package com.copring.springcampus.services;

import com.copring.springcampus.dto.RoleDTO;
import com.copring.springcampus.enums.RoleEnums;
import com.copring.springcampus.models.Role;
import com.copring.springcampus.repos.RoleRepository;
import com.copring.springcampus.utils.responses.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleService(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    public RoleDTO getRoleById(Long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        return modelMapper.map(role, RoleDTO.class);
    }

    public RoleDTO getRoleByName(String roleName) {
        Role role = roleRepository.findByName(RoleEnums.valueOf(roleName)).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        return modelMapper.map(role, RoleDTO.class);
    }

    public List<RoleDTO> getAllRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(role -> modelMapper.map(role, RoleDTO.class)).collect(Collectors.toList());
    }

    public RoleDTO createRole(RoleDTO roleDTO) {
        Role role = modelMapper.map(roleDTO, Role.class);
        role = roleRepository.save(role);
        return modelMapper.map(role, RoleDTO.class);
    }

    public RoleDTO updateRole(Long roleId, RoleDTO roleDTO) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        role.setName(RoleEnums.valueOf(roleDTO.getName()));
        role = roleRepository.save(role);
        return modelMapper.map(role, RoleDTO.class);
    }

    public ResponseEntity<?> deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        roleRepository.delete(role);
        return ResponseEntity.ok().build();
    }

}
