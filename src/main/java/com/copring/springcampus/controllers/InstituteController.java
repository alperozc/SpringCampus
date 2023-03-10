package com.copring.springcampus.controllers;

import com.copring.springcampus.dto.DepartmentDTO;
import com.copring.springcampus.dto.InstituteDTO;
import com.copring.springcampus.models.Department;
import com.copring.springcampus.models.Institute;
import com.copring.springcampus.services.DepartmentService;
import com.copring.springcampus.services.InstituteService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/institutes")
public class InstituteController {

    private final InstituteService instituteService;
    private final DepartmentService departmentService;
    private final ModelMapper modelMapper;

    @Autowired
    public InstituteController(InstituteService instituteService, ModelMapper modelMapper, DepartmentService departmentService) {
        this.instituteService = instituteService;
        this.modelMapper = modelMapper;
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<InstituteDTO> getAllInstitutes() {
        List<InstituteDTO> institutes = instituteService.getAllInstitutes();
        return institutes;
    }

    @GetMapping("/{instituteId}")
    public InstituteDTO getInstituteById(@PathVariable Long instituteId) {
        Institute institute = instituteService.getInstituteById(instituteId);
        return convertToDTO(institute);
    }

    @GetMapping("/name/{name}")
    public InstituteDTO getInstituteByName(@PathVariable String name) {
        Institute institute = instituteService.getInstituteByName(name);
        return convertToDTO(institute);
    }

    @PostMapping
    public InstituteDTO createInstitute(@RequestBody InstituteDTO instituteDTO) {
        Institute institute = instituteService.createInstitute(instituteDTO);
        return convertToDTO(institute);
    }

    @PutMapping("/{instituteId}")
    public InstituteDTO updateInstitute(@PathVariable Long instituteId, @RequestBody InstituteDTO instituteDTO) {
        Institute institute = instituteService.updateInstitute(instituteId,instituteDTO);
        return convertToDTO(institute);
    }

    @DeleteMapping("/{instituteId}")
    public ResponseEntity<?> deleteInstitute(@PathVariable Long instituteId) {
        instituteService.deleteInstitute(instituteId);
        return ResponseEntity.ok("Institute deleted");
    }

    @PostMapping("/{instituteId}/departments")
    public ResponseEntity<DepartmentDTO> addDepartmentToInstitute(@PathVariable Long instituteId, @RequestBody @Valid DepartmentDTO departmentDTO) {
        DepartmentDTO department = departmentService.addDepartmentToInstitute(instituteId, departmentDTO);
        return ResponseEntity.ok(department);
    }

    @GetMapping("/{instituteId}/departments")
    public List<DepartmentDTO> getDepartmentsByInstituteId(@PathVariable Long instituteId) {
        List<DepartmentDTO> departments = departmentService.getAllDepartmentsByInstituteId(instituteId);
        return departments;
    }

    private InstituteDTO convertToDTO(Institute institute) {
        return modelMapper.map(institute, InstituteDTO.class);
    }

    private Institute convertToEntity(InstituteDTO instituteDTO) {
        return modelMapper.map(instituteDTO, Institute.class);
    }

    private List<InstituteDTO> convertToDTOs(List<Institute> institutes) {
        return institutes.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

}
