package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.EarnMajorEntity;
import com.webservice.graduate_coach.entity.id.EarnMajorId;
import com.webservice.graduate_coach.repository.EarnMajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class EarnMajorService {
    private final EarnMajorRepository earnMajorRepository;

    public EarnMajorEntity getEarnMajor(Integer major_uid, Integer year) {
        Optional<EarnMajorEntity> earn_major = earnMajorRepository.findById(new EarnMajorId(major_uid, year));
        return earn_major.orElse(null);
    }

    @Transactional
    public String editCredit(Integer major, Integer year, Integer jeonpil_credit, Integer jeonseon_credit) {
        EarnMajorEntity earn_major = getEarnMajor(major, year);

        if (!earn_major.getReqMajorCredit().equals(jeonpil_credit.floatValue())) {
            earn_major.setReqMajorCredit(jeonpil_credit.floatValue());
        }

        if (!earn_major.getOptMajorCredit().equals(jeonseon_credit.floatValue())) {
            earn_major.setOptMajorCredit(jeonseon_credit.floatValue());
        }

        EarnMajorEntity result = earnMajorRepository.save(earn_major);
        String complete_msg = "";

        if (!result.getReqMajorCredit().equals(jeonpil_credit.floatValue())) {
            complete_msg += "전공필수 학점이 변경되지 않았습니다.\n";
        }

        if (!result.getOptMajorCredit().equals(jeonseon_credit.floatValue())) {
            complete_msg += "전공선택 학점이 변경되지 않았습니다.\n";
        }

        if (complete_msg.isEmpty()) {
            complete_msg += "전공학점이 변경되었습니다.\n";
        }

        return complete_msg;
    }
}
