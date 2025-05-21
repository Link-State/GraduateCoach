package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.CourseEntity;
import com.webservice.graduate_coach.entity.FoundationMajorEntity;
import com.webservice.graduate_coach.entity.id.FoundationMajorId;
import com.webservice.graduate_coach.repository.FoundationMajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class FoundationMajorService {
    private final FoundationMajorRepository foundationMajorRepository;
    private final CourseService courseService;

    public Boolean isJeonTam(Integer department_uid, Integer year, Integer course_uid) {
        Optional<FoundationMajorEntity> jeontam = foundationMajorRepository.findById(new FoundationMajorId(department_uid, year, course_uid));
        return jeontam.isPresent();
    }

    public List<CourseEntity> getJeontams(Integer department_uid, Integer year) {
        List<FoundationMajorEntity> jeontam = foundationMajorRepository.findByIdDepartmentAndIdYear(department_uid, year);
        List<CourseEntity> courses = new ArrayList<CourseEntity>();
        for (FoundationMajorEntity e : jeontam) {
            CourseEntity course = courseService.getCourse(e.getId().getCourse());
            if (course != null) {
                courses.add(course);
            }
        }
        return courses;
    }
}
