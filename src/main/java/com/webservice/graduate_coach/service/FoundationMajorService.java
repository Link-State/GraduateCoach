package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.FoundationMajorEntity;
import com.webservice.graduate_coach.entity.id.FoundationMajorId;
import com.webservice.graduate_coach.repository.FoundationMajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class FoundationMajorService {
    private final FoundationMajorRepository foundationMajorRepository;

    public Boolean isJeonTam(Integer department_uid, Integer year, Integer course_uid) {
        Optional<FoundationMajorEntity> jeontam = foundationMajorRepository.findById(new FoundationMajorId(department_uid, year, course_uid));
        return jeontam.isPresent();
    }
}
