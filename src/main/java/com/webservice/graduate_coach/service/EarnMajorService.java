package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.EarnMajorEntity;
import com.webservice.graduate_coach.entity.id.EarnMajorId;
import com.webservice.graduate_coach.repository.EarnMajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class EarnMajorService {
    private final EarnMajorRepository earnMajorRepository;

    public EarnMajorEntity getEarnMajor(Integer major_uid, Integer year) {
        Optional<EarnMajorEntity> earn_major = earnMajorRepository.findById(new EarnMajorId(major_uid, year));
        return earn_major.orElse(null);
    }
}
