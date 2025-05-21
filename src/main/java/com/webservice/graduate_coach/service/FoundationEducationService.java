package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.CourseEntity;
import com.webservice.graduate_coach.entity.FoundationEducationEntity;
import com.webservice.graduate_coach.entity.id.FoundationEducationId;
import com.webservice.graduate_coach.repository.FoundationEducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class FoundationEducationService {
    private final FoundationEducationRepository foundationEducationRepository;
    private final CourseService courseService;

    public Boolean isPilGyo(Integer department_uid, Integer year, Integer course_uid) {
        Optional<FoundationEducationEntity> pilgyo = foundationEducationRepository.findById(new FoundationEducationId(department_uid, year, course_uid));
        return pilgyo.isPresent();
    }

    public List<CourseEntity> getPilgyos(Integer department_uid, Integer year) {
        List<FoundationEducationEntity> pilgyos = foundationEducationRepository.findByIdDepartmentAndIdYear(department_uid, year);
        List<CourseEntity> courses = new ArrayList<CourseEntity>();
        for (FoundationEducationEntity e : pilgyos) {
            CourseEntity course = courseService.getCourse(e.getId().getCourse());
            if (course != null) {
                courses.add(course);
            }
        }
        return courses;
    }
}
