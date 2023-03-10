package com.copring.springcampus.services;

import com.copring.springcampus.dto.DepartmentDTO;
import com.copring.springcampus.dto.FacultyDTO;
import com.copring.springcampus.dto.InstituteDTO;
import com.copring.springcampus.models.Department;
import com.copring.springcampus.models.Faculty;
import com.copring.springcampus.models.Institute;
import com.copring.springcampus.repos.DepartmentRepository;
import com.copring.springcampus.repos.FacultyRepository;
import com.copring.springcampus.utils.responses.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    private Department convertToEntity(DepartmentDTO departmentDTO) {
        return modelMapper.map(departmentDTO, Department.class);
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
        if (isFacultyExists(facultyDTO.getName())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Faculty already exists");
        }
        facultyDTO.setDepartments(new ArrayList<>());
        Faculty faculty = convertToEntity(facultyDTO);
        return convertToDTO(facultyRepository.save(faculty));
    }

    public Faculty getFacultyByName(String name) {
        return facultyRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));
    }

    public void deleteFaculty(Long facultyId) {
        Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));
        facultyRepository.delete(faculty);
    }

    public boolean isFacultyExists(String name) {
        return facultyRepository.existsByName(name);
    }


    public FacultyDTO updateFaculty(Long id, FacultyDTO facultyDTO) {
        Faculty faculty = facultyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));
        if (isFacultyExists(facultyDTO.getName())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Faculty already exists");
        }
        faculty.setName(facultyDTO.getName());


        if (arrayIsNotEmpty(faculty.getDepartments())) {
            faculty.setDepartments(new ArrayList<>());

            facultyDTO.getDepartments().forEach(departmentDTO -> {
                Department department = departmentRepository.findById(departmentDTO.getId()).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
                faculty.getDepartments().add(department);
            });
        }
        return convertToDTO(facultyRepository.save(faculty));
    }

    private boolean arrayIsNotEmpty(List<?> list) {
        return list != null && !list.isEmpty();
    }

}

