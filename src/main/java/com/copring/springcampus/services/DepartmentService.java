package com.copring.springcampus.services;

import com.copring.springcampus.dto.DepartmentDTO;
import com.copring.springcampus.dto.InstituteDTO;
import com.copring.springcampus.dto.StudentDTO;
import com.copring.springcampus.models.Department;
import com.copring.springcampus.models.Faculty;
import com.copring.springcampus.models.Institute;
import com.copring.springcampus.models.Student;
import com.copring.springcampus.repos.DepartmentRepository;
import com.copring.springcampus.repos.FacultyRepository;
import com.copring.springcampus.repos.InstituteRepository;
import com.copring.springcampus.repos.StudentRepository;
import com.copring.springcampus.utils.responses.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private InstituteRepository instituteRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private StudentRepository studentRepository;

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
        // Check if department exists
        if (departmentRepository.existsByName(departmentDTO.getName()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Department already exists");

        department.setName(departmentDTO.getName());
        return convertToDTO(departmentRepository.save(department));
    }

    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

    public List<StudentDTO> getStudentsByDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        return department.getStudents().stream().map(student -> modelMapper.map(student, StudentDTO.class)).collect(Collectors.toList());
    }

    public StudentDTO addStudentToDepartment(Long id, StudentDTO studentDTO) {
        Department department = departmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Department not found"));
        Student student = modelMapper.map(studentDTO, Student.class);
        student.setDepartment(department);
        return modelMapper.map(studentRepository.save(student), StudentDTO.class);
    }

    public DepartmentDTO addDepartmentToFaculty(Long facultyId, DepartmentDTO departmentDTO) {
        Department department = convertToEntity(departmentDTO);

        // Get faculty
        Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));

        // Check if department exists
        if (departmentRepository.existsByName(department.getName()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Department already exists");


        department.setFaculty(faculty);

        return convertToDTO(departmentRepository.save(department));
    }

    public DepartmentDTO addDepartmentToInstitute(Long instituteId, DepartmentDTO departmentDTO) {
        Department department = convertToEntity(departmentDTO);

        // Get institute
        Institute institute = instituteRepository.findById(instituteId).orElseThrow(() -> new ResourceNotFoundException("Institute not found"));

        // Check if department exists
        if (departmentRepository.existsByName(department.getName()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Department already exists");

        department.setInstitute(institute);

        return convertToDTO(departmentRepository.save(department));
    }

    public List<DepartmentDTO> getAllDepartmentsByFacultyId(Long facultyId) {
        // Check if faculty exists
        Faculty faculty = facultyRepository.findById(facultyId).orElseThrow(() -> new ResourceNotFoundException("Faculty not found"));

        List<Department> departments = departmentRepository.findAllByFaculty(faculty);
        return departments.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<DepartmentDTO> getAllDepartmentsByInstituteId(Long instituteId){
        // Check if institute exists
        Institute institute = instituteRepository.findById(instituteId).orElseThrow(() -> new ResourceNotFoundException("Institute not found"));

        List<Department> departments = departmentRepository.findAllByInstitute(institute);
        return departments.stream().map(this::convertToDTO).collect(Collectors.toList());
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
