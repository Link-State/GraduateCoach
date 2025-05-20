package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.UniversityEntity;
import com.webservice.graduate_coach.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UniversityService {
    private final UniversityRepository universityRepository;

    public UniversityEntity getUniversity(Integer uid) {
        Optional<UniversityEntity> univ = universityRepository.findById(uid);
        return univ.orElse(null);
    }
}
