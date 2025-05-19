package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.GraduateEntity;
import com.webservice.graduate_coach.entity.id.GraduateId;
import com.webservice.graduate_coach.repository.GraduateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class GraduateService {
    private final GraduateRepository graduateRepository;

    public GraduateEntity getGraduate(Integer department_uid, Integer year) {
        Optional<GraduateEntity> graduate =  graduateRepository.findById(new GraduateId(department_uid, year));
        return graduate.orElse(null);
    }
}
