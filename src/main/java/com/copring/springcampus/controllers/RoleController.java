package com.copring.springcampus.controllers;

import com.copring.springcampus.dto.RoleDTO;
import com.copring.springcampus.services.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDTO> getRoleById(@PathVariable Long roleId) {
        RoleDTO roleDTO = roleService.getRoleById(roleId);
        return ResponseEntity.ok(roleDTO);
    }

    @GetMapping("/name/{roleName}")
    public ResponseEntity<RoleDTO> getRoleByName(@PathVariable String roleName) {
        RoleDTO roleDTO = roleService.getRoleByName(roleName);
        return ResponseEntity.ok(roleDTO);
    }

    @GetMapping
    public ResponseEntity<List<RoleDTO>> getAllRoles() {
        List<RoleDTO> roleDTOS = roleService.getAllRoles();
        return ResponseEntity.ok(roleDTOS);
    }

    @PostMapping
    public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        RoleDTO createdRoleDTO = roleService.createRole(roleDTO);
        return ResponseEntity.ok(createdRoleDTO);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<RoleDTO> updateRole(@PathVariable Long roleId, @Valid @RequestBody RoleDTO roleDTO) {
        RoleDTO updatedRoleDTO = roleService.updateRole(roleId, roleDTO);
        return ResponseEntity.ok(updatedRoleDTO);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<?> deleteRole(@PathVariable Long roleId) {
        roleService.deleteRole(roleId);
        return ResponseEntity.ok().build();
    }

}
