package com.copring.springcampus.services;

import com.copring.springcampus.dto.InstituteDTO;
import com.copring.springcampus.models.Department;
import com.copring.springcampus.models.Institute;
import com.copring.springcampus.repos.DepartmentRepository;
import com.copring.springcampus.repos.InstituteRepository;
import com.copring.springcampus.utils.responses.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
public class InstituteService {

    @Autowired
    private InstituteRepository instituteRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DepartmentRepository departmentRepository;

    public Institute getInstituteById(Long id) {
        return instituteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Institute not found"));
    }
    public Institute getInstituteByName(String name) {
        return instituteRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Institute not found"));
    }

    public Institute createInstitute(InstituteDTO instituteDTO) {
        Institute institute = modelMapper.map(instituteDTO, Institute.class);
        if (instituteRepository.existsByName(institute.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Institute already exists");
        }
        institute.setDepartments(new ArrayList<>());
        return instituteRepository.save(institute);
    }

    public Institute updateInstitute(Long id, InstituteDTO instituteDTO) {
        Institute institute = instituteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Institute not found"));
        institute.setName(instituteDTO.getName());

        if (instituteRepository.existsByName(institute.getName())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Institute already exists");
        }

        if (arrayIsNotEmpty(instituteDTO.getDepartments())) {
            institute.setDepartments(new ArrayList<>());
            instituteDTO.getDepartments().forEach(departmentDTO -> {
                Department dep = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Institute not found"));
                institute.getDepartments().add(dep);
            });
        }

        return instituteRepository.save(institute);
    }

    public void deleteInstitute(Long id) {
        Institute institute = getInstituteById(id);
        instituteRepository.delete(institute);
    }

    public List<InstituteDTO> getAllInstitutes() {
        List<Institute> institutes = instituteRepository.findAll();
        List<InstituteDTO> instituteDTOs = new ArrayList<>();
        for (Institute institute : institutes) {
            InstituteDTO instituteDTO = modelMapper.map(institute, InstituteDTO.class);
            instituteDTOs.add(instituteDTO);
        }
        return instituteDTOs;
    }
    private boolean arrayIsNotEmpty(List<?> list) {
        return list != null && !list.isEmpty();
    }

}
