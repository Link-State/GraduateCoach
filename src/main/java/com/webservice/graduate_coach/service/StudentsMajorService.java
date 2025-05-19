package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.MajorEntity;
import com.webservice.graduate_coach.entity.StudentsMajorEntity;
import com.webservice.graduate_coach.repository.StudentsMajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class StudentsMajorService {
    private final StudentsMajorRepository studentsMajorRepository;

    private final MajorService majorService;

    // 전공 리스트 로드
    public List<StudentsMajorEntity> getStudentMajors(Integer student_uid) {
        List<StudentsMajorEntity> majors = studentsMajorRepository.findByIdStudent(student_uid);
        if (majors.isEmpty()) {
            return null;
        }
        return majors;
    }

    // 주 전공 로드
    public MajorEntity getFirstMajor(Integer student_uid) {
        StudentsMajorEntity studentmajor = studentsMajorRepository.findByIdStudentAndIdMajorOrder(student_uid, 1);
        return majorService.getMajor(studentmajor.getMajor());
    }
}
