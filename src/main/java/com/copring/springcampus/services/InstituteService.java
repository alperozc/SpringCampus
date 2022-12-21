package com.copring.springcampus.services;

import com.copring.springcampus.dto.InstituteDTO;
import com.copring.springcampus.models.Institute;
import com.copring.springcampus.repos.InstituteRepository;
import com.copring.springcampus.utils.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class InstituteService {

    @Autowired
    private InstituteRepository instituteRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Institute getInstituteById(Long id) {
        return instituteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Institute not found"));
    }

    public Institute createInstitute(InstituteDTO instituteDTO) {
        Institute institute = modelMapper.map(instituteDTO, Institute.class);
        return instituteRepository.save(institute);
    }

    public Institute updateInstitute(Long id, InstituteDTO instituteDTO) {
        Institute institute = getInstituteById(id);
        modelMapper.map(instituteDTO, institute);
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

}
