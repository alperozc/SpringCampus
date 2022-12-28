package com.copring.springcampus.controllers;

import com.copring.springcampus.dto.DepartmentDTO;
import com.copring.springcampus.dto.FacultyDTO;
import com.copring.springcampus.models.Department;
import com.copring.springcampus.models.Faculty;
import com.copring.springcampus.services.DepartmentService;
import com.copring.springcampus.services.FacultyService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/faculties")
public class FacultyController {

    private final FacultyService facultyService;
    private final DepartmentService departmentService;
    private final ModelMapper modelMapper;

    @Autowired
    public FacultyController(FacultyService facultyService, ModelMapper modelMapper, DepartmentService departmentService) {
        this.facultyService = facultyService;
        this.modelMapper = modelMapper;
        this.departmentService = departmentService;
    }

    @GetMapping("/{facultyId}")
    public ResponseEntity<FacultyDTO> getFacultyById(@PathVariable Long facultyId) {
        FacultyDTO facultyDTO = facultyService.getFacultyById(facultyId);
        return ResponseEntity.ok(facultyDTO);
    }

    @GetMapping("/name/{facultyName}")
    public ResponseEntity<FacultyDTO> getFacultyByName(@PathVariable String facultyName) {
        Faculty faculty = facultyService.getFacultyByName(facultyName);
        FacultyDTO facultyDTO = convertToDTO(faculty);
        return ResponseEntity.ok(facultyDTO);
    }

    @GetMapping()
    public ResponseEntity<List<FacultyDTO>> getAllFaculties() {
        List<FacultyDTO> faculties = facultyService.getAllFaculties();
        return ResponseEntity.ok(faculties);
    }

    @PostMapping()
    public ResponseEntity<FacultyDTO> createFaculty(@Valid @RequestBody FacultyDTO facultyDTO) {
        FacultyDTO createdFaculty = facultyService.createFaculty(facultyDTO);
        return ResponseEntity.ok(createdFaculty);
    }

    @PutMapping("/{facultyId}")
    public ResponseEntity<FacultyDTO> updateFaculty(@PathVariable Long facultyId, @Valid @RequestBody FacultyDTO facultyDTO) {
        FacultyDTO updatedFaculty = facultyService.updateFaculty(facultyId, facultyDTO);
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("/{facultyId}")
    public ResponseEntity<?> deleteFaculty(@PathVariable Long facultyId) {
        facultyService.deleteFaculty(facultyId);
        return ResponseEntity.ok("Faculty deleted");
    }

    // Department section

    @PostMapping("/{facultyId}/departments")
    public ResponseEntity<DepartmentDTO> addDepartmentToFaculty(@PathVariable Long facultyId, @Valid @RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO department = departmentService.addDepartmentToFaculty(facultyId, departmentDTO);
        return ResponseEntity.ok(department);
    }

    @GetMapping("/{facultyId}/departments")
    public ResponseEntity<List<DepartmentDTO>> getAllDepartmentsByFacultyId(@PathVariable Long facultyId) {
        List<DepartmentDTO> departments = departmentService.getAllDepartmentsByFacultyId(facultyId);
        return ResponseEntity.ok(departments);
    }

    private FacultyDTO convertToDTO(Faculty faculty) {
        return modelMapper.map(faculty, FacultyDTO.class);
    }
    private List<FacultyDTO> convertToDTOs(List<Faculty> faculties) {
        return faculties.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private Faculty convertToEntity(FacultyDTO facultyDTO) {
        return modelMapper.map(facultyDTO, Faculty.class);
    }


}