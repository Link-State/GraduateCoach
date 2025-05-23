package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.CourseEntity;
import com.webservice.graduate_coach.entity.CourseTypeEntity;
import com.webservice.graduate_coach.entity.id.CourseTypeId;
import com.webservice.graduate_coach.repository.CourseTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public String deleteCourses(Integer major, Integer year, List<Integer> courses) {
        if (courses.isEmpty()) {
            return "삭제할 과목을 선택해주세요.";
        }

        for (Integer id : courses) {
            courseTypeRepository.deleteById(new CourseTypeId(major, year, id));
        }

        return courses.size() + "개의 전공과목이 삭제되었습니다.";
    }
}
