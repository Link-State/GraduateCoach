package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.CourseEntity;
import com.webservice.graduate_coach.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CourseService {
    private final CourseRepository courseRepository;

    public CourseEntity getCourse(Integer uid) {
        Optional<CourseEntity> course = courseRepository.findById(uid);
        return course.orElse(null);
    }
}
