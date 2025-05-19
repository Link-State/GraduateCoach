package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.FoundationEducationEntity;
import com.webservice.graduate_coach.entity.id.FoundationEducationId;
import com.webservice.graduate_coach.repository.FoundationEducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class FoundationEducationService {
    private final FoundationEducationRepository foundationEducationRepository;

    public Boolean isPilGyo(Integer department_uid, Integer year, Integer course_uid) {
        Optional<FoundationEducationEntity> pilgyo = foundationEducationRepository.findById(new FoundationEducationId(department_uid, year, course_uid));
        return pilgyo.isPresent();
    }
}
