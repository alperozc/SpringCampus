package com.copring.springcampus.controllers;

import com.copring.springcampus.dto.StudentDTO;
import com.copring.springcampus.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable(value = "id") Long id, @RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDTO));
    }

    @PostMapping
    public ResponseEntity<?> createStudent(@RequestBody StudentDTO studentDTO) {
        return ResponseEntity.ok(studentService.addStudent(studentDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable(value = "id") Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok("Student deleted");
    }
}
