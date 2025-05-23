package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.GraduateEntity;
import com.webservice.graduate_coach.entity.id.GraduateId;
import com.webservice.graduate_coach.repository.GraduateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class GraduateService {
    private final GraduateRepository graduateRepository;

    public GraduateEntity getGraduate(Integer department_uid, Integer year) {
        Optional<GraduateEntity> graduate =  graduateRepository.findById(new GraduateId(department_uid, year));
        return graduate.orElse(null);
    }

    @Transactional
    public String editCredit(Integer department, Integer year, Integer pilgyo_credit, Integer daegyo_credit, Integer jeontam_creidt, Integer advanced_credit) {
        GraduateEntity graduate = getGraduate(department, year);

        if (!graduate.getFoundationEdu().equals(pilgyo_credit)) {
            graduate.setFoundationEdu(pilgyo_credit);
        }

        if (!graduate.getGeneralEdu().equals(daegyo_credit)) {
            graduate.setGeneralEdu(daegyo_credit);
        }

        if (!graduate.getFoundationMajor().equals(jeontam_creidt)) {
            graduate.setFoundationMajor(jeontam_creidt);
        }

        if (!graduate.getTotalLevel().equals(advanced_credit)) {
            graduate.setTotalLevel(advanced_credit);
        }

        GraduateEntity result = graduateRepository.save(graduate);
        String complete_msg = "";

        if (!result.getFoundationEdu().equals(pilgyo_credit)) {
            complete_msg += "교양필수 학점이 변경되지 않았습니다.\n";
        }

        if (!result.getGeneralEdu().equals(daegyo_credit)) {
            complete_msg += "대학교양 학점이 변경되지 않았습니다.\n";
        }

        if (!result.getFoundationMajor().equals(jeontam_creidt)) {
            complete_msg += "전공탐색 학점이 변경되지 않았습니다.\n";
        }

        if (!result.getTotalLevel().equals(advanced_credit)) {
            complete_msg += "3000단위이상 학점이 변경되지 않았습니다.\n";
        }

        if (complete_msg.isEmpty()) {
            complete_msg += "교양학점이 변경되었습니다.\n";
        }

        return complete_msg;
    }
}
