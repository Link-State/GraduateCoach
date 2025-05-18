package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.dto.AcademyDTO;
import com.webservice.graduate_coach.entity.AcademyEntity;
import com.webservice.graduate_coach.repository.AcademyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class AcademyService {
    private final AcademyRepository academyRepository;

}
