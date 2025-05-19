package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.EssentialGeneralEducationEntity;
import com.webservice.graduate_coach.entity.id.EssentialGeneralEducationId;
import com.webservice.graduate_coach.repository.EssentialGeneralEducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class EssentialGeneralEducationService {
    private final EssentialGeneralEducationRepository essentialGeneralEducationRepository;

    public Boolean isDaeGyo(Integer department_uid, Integer year, Integer course_uid) {
        Optional<EssentialGeneralEducationEntity> daegyo = essentialGeneralEducationRepository.findById(new EssentialGeneralEducationId(department_uid, year, course_uid));
        return daegyo.isPresent();
    }
}
