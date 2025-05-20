package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.OptionalGeneralEducationEntity;
import com.webservice.graduate_coach.entity.id.OptionalGeneralEducationId;
import com.webservice.graduate_coach.repository.OptionalGeneralEducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class OptionalGeneralEducationService {
    private final OptionalGeneralEducationRepository optionalGeneralEducationRepository;

    public Boolean isSeonGyo(Integer department_uid, Integer year, Integer number) {
        if (number == null) {
            return false;
        }
        Optional<OptionalGeneralEducationEntity> seongyo = optionalGeneralEducationRepository.findById(new OptionalGeneralEducationId(department_uid, year, number));
        return seongyo.isPresent();
    }
}
