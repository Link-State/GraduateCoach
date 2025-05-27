package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.MajorEntity;
import com.webservice.graduate_coach.repository.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class MajorService {
    private final MajorRepository majorRepository;

    public MajorEntity getMajor(Integer uid) {
        Optional<MajorEntity> major = majorRepository.findById(uid);
        return major.orElse(null);
    }

    public List<MajorEntity> getMajorsByUniv(Integer univ_uid) {
        List<MajorEntity> result = new ArrayList<MajorEntity>();

        // 하드코딩
        MajorEntity m = getMajor(1);
        // 소프트웨어 기본전공
        if (m != null) {
            result.add(m);
        }

        // 소프트웨어 심화전공
        m = getMajor(2);
        if (m != null) {
            result.add(m);
        }

        return result;
    }
}
