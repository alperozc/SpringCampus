package com.copring.springcampus.controllers;

import com.copring.springcampus.dto.DepartmentDTO;
import com.copring.springcampus.models.Faculty;
import com.copring.springcampus.services.DepartmentService;
import com.copring.springcampus.services.FacultyService;
import com.copring.springcampus.services.InstituteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private FacultyService facultyService;

    @Autowired
    private InstituteService instituteService;

    @GetMapping
    public ResponseEntity<List<DepartmentDTO>> getAllDepartments() {
        List<DepartmentDTO> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable(value = "id") Long id) {
        DepartmentDTO department = departmentService.getDepartmentById(id);
        return ResponseEntity.ok(department);
    }

    @PostMapping
    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
        // Check if the faculty or institute exists
        Long facultyId = departmentDTO.getFacultyId();
        if (facultyId != null) {
            facultyService.getFacultyById(facultyId);
        }
        Long instituteId = departmentDTO.getInstituteId();
        if (instituteId != null) {
            instituteService.getInstituteById(instituteId);
        }
        // Create the department
        DepartmentDTO createdDepartment = departmentService.createDepartment(departmentDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdDepartment.getId()).toUri();
        return ResponseEntity.created(location).body(createdDepartment);
    }


    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable(value = "id") Long id, @Valid @RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO updatedDepartment = departmentService.updateDepartment(id, departmentDTO);
        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable(value = "id") Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.noContent().build();
    }

}
