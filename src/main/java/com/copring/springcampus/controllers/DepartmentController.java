package com.copring.springcampus.controllers;

import com.copring.springcampus.dto.DepartmentDTO;
import com.copring.springcampus.dto.StudentDTO;
import com.copring.springcampus.services.DepartmentService;
import com.copring.springcampus.services.FacultyService;
import com.copring.springcampus.services.InstituteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;


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

//    @PostMapping
//    public ResponseEntity<DepartmentDTO> createDepartment(@Valid @RequestBody DepartmentDTO departmentDTO) {
//
//
//        // Check if the faculty or institute exists
//        Long facultyId = departmentDTO.getFacultyId();
//        if (facultyId != null) {
//            facultyService.getFacultyById(facultyId);
//        }
//        Long instituteId = departmentDTO.getInstituteId();
//        if (instituteId != null) {
//            instituteService.getInstituteById(instituteId);
//        }
//
//        if (facultyId == null && instituteId == null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department must have either a faculty or an institute");
//        }
//
//        if (instituteId != null && facultyId != null) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Department can't have both faculty and institute") {
//            };
//        }
//
//        // Create the department
//        DepartmentDTO createdDepartment = departmentService.createDepartment(departmentDTO);
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdDepartment.getId()).toUri();
//        return ResponseEntity.created(location).body(createdDepartment);
//    }


    @PutMapping("/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(@PathVariable(value = "id") Long id, @Valid @RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO updatedDepartment = departmentService.updateDepartment(id, departmentDTO);
        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteDepartment(@PathVariable(value = "id") Long id) {
        departmentService.deleteDepartment(id);
        return ResponseEntity.ok("Department deleted");
    }


    // Students Section

    @GetMapping("/{id}/students")
    public ResponseEntity<?> getStudentsByDepartmentId(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(departmentService.getStudentsByDepartment(id));
    }

    @PostMapping("/{id}/students")
    public ResponseEntity<?> addStudentToDepartment(@PathVariable(value = "id") Long id, @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(departmentService.addStudentToDepartment(id, studentDTO));
    }

}
