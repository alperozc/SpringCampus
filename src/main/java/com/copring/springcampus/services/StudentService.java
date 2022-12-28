package com.copring.springcampus.services;

import com.copring.springcampus.dto.StudentDTO;
import com.copring.springcampus.models.Department;
import com.copring.springcampus.models.Student;
import com.copring.springcampus.repos.StudentRepository;
import com.copring.springcampus.utils.responses.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentService departmentService;
    private final ModelMapper modelMapper;

    public StudentService(StudentRepository studentRepository, DepartmentService departmentService, ModelMapper modelMapper) {
        this.studentRepository = studentRepository;
        this.departmentService = departmentService;
        this.modelMapper = modelMapper;
    }

    public StudentDTO addStudent(StudentDTO studentDTO) {
        Department department = departmentService.getDepartment(studentDTO.getDepartment().getId());
        Student student = modelMapper.map(studentDTO, Student.class);
        student.setDepartment(department);
        return modelMapper.map(studentRepository.save(student), StudentDTO.class);
    }

    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        //Department department = departmentService.getDepartment(studentDTO.getDepartmentId());
        modelMapper.map(studentDTO, student);
        //student.setDepartment(department);
        return modelMapper.map(studentRepository.save(student), StudentDTO.class);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<StudentDTO> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return students.stream().map(student -> modelMapper.map(student, StudentDTO.class)).collect(Collectors.toList());
    }

    public StudentDTO getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        return modelMapper.map(student, StudentDTO.class);
    }

}
