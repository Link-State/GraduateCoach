package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.dto.StudentDTO;
import com.webservice.graduate_coach.entity.StudentEntity;
import com.webservice.graduate_coach.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class StudentService {
    private final StudentRepository studentRepository;

    public StudentEntity test_student(StudentDTO dto) {
        var entity = StudentEntity.builder()
                .id(dto.getId())
                .build();

        return studentRepository.save(entity);
    }

    public Optional<StudentEntity> findByUser(Long uid) {
        return studentRepository.findById(uid);
    }
}
