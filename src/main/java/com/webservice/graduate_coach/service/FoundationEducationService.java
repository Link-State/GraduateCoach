package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.CourseEntity;
import com.webservice.graduate_coach.entity.FoundationEducationEntity;
import com.webservice.graduate_coach.entity.id.FoundationEducationId;
import com.webservice.graduate_coach.repository.FoundationEducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public String deleteCourses(Integer department, Integer year, List<Integer> courses) {
        if (courses.isEmpty()) {
            return "삭제할 과목을 선택해주세요.";
        }

        for (Integer id : courses) {
            foundationEducationRepository.deleteById(new FoundationEducationId(department, year, id));
        }
        return courses.size() + "개의 교양필수 과목이 삭제되었습니다.";
    }
}
