package com.copring.springcampus.services;

import com.copring.springcampus.dto.DepartmentDTO;
import com.copring.springcampus.models.Department;
import com.copring.springcampus.repos.DepartmentRepository;
import com.copring.springcampus.utils.responses.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        return convertToDTO(department);
    }

    public Department getDepartment(Long id) {
        return departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
    }

    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = convertToEntity(departmentDTO);
        return convertToDTO(departmentRepository.save(department));
    }

    public DepartmentDTO updateDepartment(Long id, DepartmentDTO departmentDTO) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        department.setName(departmentDTO.getName());
        return convertToDTO(departmentRepository.save(department));
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO departmentDTO = modelMapper.map(department, DepartmentDTO.class);
        return departmentDTO;
    }

    private Department convertToEntity(DepartmentDTO departmentDTO) {
        Department department = modelMapper.map(departmentDTO, Department.class);
        return department;
    }

}
