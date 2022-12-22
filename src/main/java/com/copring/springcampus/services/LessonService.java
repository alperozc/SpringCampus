package com.copring.springcampus.services;

import com.copring.springcampus.dto.LessonDTO;
import com.copring.springcampus.models.Department;
import com.copring.springcampus.models.Lesson;
import com.copring.springcampus.repos.DepartmentRepository;
import com.copring.springcampus.repos.LessonRepository;
import com.copring.springcampus.utils.responses.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LessonService(LessonRepository lessonRepository, DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.lessonRepository = lessonRepository;
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    public LessonDTO createLesson(LessonDTO lessonDTO) {
        Lesson lesson = modelMapper.map(lessonDTO, Lesson.class);
        lesson = lessonRepository.save(lesson);
        return modelMapper.map(lesson, LessonDTO.class);
    }

    public List<LessonDTO> getAllLessons() {
        List<Lesson> lessons = lessonRepository.findAll();
        List<LessonDTO> lessonDTOs = new ArrayList<>();
        for (Lesson lesson : lessons) {
            lessonDTOs.add(modelMapper.map(lesson, LessonDTO.class));
        }
        return lessonDTOs;
    }

    public LessonDTO getLessonById(Long lessonId) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
        if (!optionalLesson.isPresent()) {
            throw new ResourceNotFoundException("Lesson not found with id " + lessonId);
        }
        Lesson lesson = optionalLesson.get();
        return modelMapper.map(lesson, LessonDTO.class);
    }

    public LessonDTO updateLesson(Long lessonId, LessonDTO lessonDTO) {
        Optional<Department> optionalDepartment = departmentRepository.findById(lessonDTO.getDepartment().getId());
        if (!optionalDepartment.isPresent()) {
            throw new ResourceNotFoundException("Department not found");
        }
        Lesson lesson = modelMapper.map(lessonDTO, Lesson.class);
        lesson.setDepartment(optionalDepartment.get());
        lessonRepository.save(lesson);
        return modelMapper.map(lesson, LessonDTO.class);
    }

    public ResponseEntity<?> deleteLesson(Long lessonId) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(lessonId);
        if (!optionalLesson.isPresent()) {
            throw new ResourceNotFoundException("Lesson not found with id " + lessonId);
        }
        lessonRepository.delete(optionalLesson.get());
        return ResponseEntity.ok().build();
    }
}
