package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.CourseEntity;
import com.webservice.graduate_coach.entity.StudentsCourseEntity;
import com.webservice.graduate_coach.repository.StudentsCourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class StudentsCourseService {
    private final StudentsCourseRepository studentsCourseRepository;

    private final CourseService courseService;

    // 학생-강의 관계 목록 로드
    public List<StudentsCourseEntity> getCourses(Integer student_uid) {
        List<StudentsCourseEntity> courses = studentsCourseRepository.findByIdStudent(student_uid);
        if (courses.isEmpty()) {
            return null;
        }
        return courses;
    }

    // 강의 정보 목록 로드
    public List<CourseEntity> getCoursesDetail(Integer student_uid) {
        List<StudentsCourseEntity> courses = getCourses(student_uid);
        if (courses == null) {
            return null;
        }
        List<CourseEntity> courses_detail = new ArrayList<CourseEntity>();
        for (StudentsCourseEntity e : courses) {
            CourseEntity course = courseService.getCourse(e.getId().getCourse());
            if (course == null) {
                continue;
            }

            courses_detail.add(course);
        }
        if (courses_detail.isEmpty()) {
            return null;
        }
        return courses_detail;
    }
}
