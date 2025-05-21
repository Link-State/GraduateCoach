package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.CourseEntity;
import com.webservice.graduate_coach.entity.EssentialGeneralEducationEntity;
import com.webservice.graduate_coach.entity.id.EssentialGeneralEducationId;
import com.webservice.graduate_coach.repository.EssentialGeneralEducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class EssentialGeneralEducationService {
    private final EssentialGeneralEducationRepository essentialGeneralEducationRepository;
    private final CourseService courseService;

    public Boolean isDaeGyo(Integer department_uid, Integer year, Integer course_uid) {
        Optional<EssentialGeneralEducationEntity> daegyo = essentialGeneralEducationRepository.findById(new EssentialGeneralEducationId(department_uid, year, course_uid));
        return daegyo.isPresent();
    }

    public List<CourseEntity> getDaegyos(Integer department_uid, Integer year) {
        List<EssentialGeneralEducationEntity> daegyo = essentialGeneralEducationRepository.findByIdDepartmentAndIdYear(department_uid, year);
        List<CourseEntity> courses = new ArrayList<CourseEntity>();
        for (EssentialGeneralEducationEntity e : daegyo) {
            CourseEntity course = courseService.getCourse(e.getId().getCourse());
            if (course != null) {
                courses.add(course);
            }
        }
        return courses;
    }
}
