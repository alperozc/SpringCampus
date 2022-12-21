package com.copring.springcampus.services;

import com.copring.springcampus.dto.FacultyDTO;
import com.copring.springcampus.models.Department;
import com.copring.springcampus.models.Faculty;
import com.copring.springcampus.repos.DepartmentRepository;
import com.copring.springcampus.repos.FacultyRepository;
import com.copring.springcampus.utils.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private FacultyDTO convertToDTO(Faculty faculty) {
        FacultyDTO facultyDTO = modelMapper.map(faculty, FacultyDTO.class);
        return facultyDTO;
    }

    private Faculty convertToEntity(FacultyDTO facultyDTO) {
        Faculty faculty = modelMapper.map(facultyDTO, Faculty.class);
        return faculty;
    }

    @Autowired
    private FacultyRepository facultyRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<FacultyDTO> getAllFaculties() {
        List<Faculty> faculties = facultyRepository.findAll();
        return faculties.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public FacultyDTO getFacultyById(Long id) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));
        return convertToDTO(faculty);
    }

    public FacultyDTO createFaculty(FacultyDTO facultyDTO) {
        Faculty faculty = convertToEntity(facultyDTO);
        return convertToDTO(facultyRepository.save(faculty));
    }

    public FacultyDTO updateFaculty(Long id, FacultyDTO facultyDTO) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));
        faculty.setName(facultyDTO.getName());
        faculty.getDepartments().clear();
        facultyDTO.getDepartments().forEach(departmentDTO -> {
            Department department = departmentRepository.findById(departmentDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
            faculty.getDepartments().add(department);
        });
        return convertToDTO(facultyRepository.save(faculty));
    }
}

