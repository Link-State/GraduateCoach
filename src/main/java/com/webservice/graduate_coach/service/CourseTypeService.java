package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.CourseEntity;
import com.webservice.graduate_coach.entity.CourseTypeEntity;
import com.webservice.graduate_coach.entity.id.CourseTypeId;
import com.webservice.graduate_coach.repository.CourseTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class CourseTypeService {
    private final CourseTypeRepository courseTypeRepository;

    private final CourseService courseService;

    //
    public CourseTypeEntity getCourse(Integer major_uid, Integer year, Integer course_uid) {
        Optional<CourseTypeEntity> course = courseTypeRepository.findById(new CourseTypeId(major_uid, year, course_uid));
        return course.orElse(null);
    }

    //
    public List<CourseTypeEntity> getCourses(Integer major_uid, Integer year) {
        List<CourseTypeEntity> courses = courseTypeRepository.findByIdMajorAndIdYear(major_uid, year);
        if (courses.isEmpty()) {
            return null;
        }
        return courses;
    }

    //
    public List<CourseTypeEntity> getCourses(Integer major_uid, Integer year, Integer type) {
        List<CourseTypeEntity> courses = courseTypeRepository.findByIdMajorAndIdYearAndType(major_uid, year, type);
        if (courses.isEmpty()) {
            return null;
        }
        return courses;
    }

    //
    public List<CourseEntity> getCoursesDetail(Integer major_uid, Integer year) {
        List<CourseTypeEntity> courses = getCourses(major_uid, year);
        if (courses == null) {
            return null;
        }
        List<CourseEntity> courses_detail = new ArrayList<CourseEntity>();
        for (CourseTypeEntity e : courses) {
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

    //
    public List<CourseEntity> getCoursesDetail(Integer major_uid, Integer year, Integer type) {
        List<CourseTypeEntity> courses = getCourses(major_uid, year, type);
        if (courses == null) {
            return null;
        }
        List<CourseEntity> courses_detail = new ArrayList<CourseEntity>();
        for (CourseTypeEntity e : courses) {
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
