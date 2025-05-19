package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.MajorEntity;
import com.webservice.graduate_coach.repository.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class MajorService {
    private final MajorRepository majorRepository;

    public MajorEntity getMajor(Integer uid) {
        Optional<MajorEntity> major = majorRepository.findById(uid);
        return major.orElse(null);
    }
}
