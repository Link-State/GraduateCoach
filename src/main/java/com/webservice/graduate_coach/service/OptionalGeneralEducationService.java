package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.EssentialGeneralEducationEntity;
import com.webservice.graduate_coach.entity.OptionalGeneralEducationEntity;
import com.webservice.graduate_coach.entity.id.EssentialGeneralEducationId;
import com.webservice.graduate_coach.entity.id.OptionalGeneralEducationId;
import com.webservice.graduate_coach.repository.EssentialGeneralEducationRepository;
import com.webservice.graduate_coach.repository.OptionalGeneralEducationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class OptionalGeneralEducationService {
    private final OptionalGeneralEducationRepository optionalGeneralEducationRepository;
    private final EssentialGeneralEducationRepository essentialGeneralEducationRepository;

    public Boolean isSeonGyo(Integer department_uid, Integer year, Integer course_uid, Integer number) {
        if (number == null) {
            return false;
        }
        Optional<OptionalGeneralEducationEntity> seongyo = optionalGeneralEducationRepository.findById(new OptionalGeneralEducationId(department_uid, year, number));
        Optional<EssentialGeneralEducationEntity> daegyo = essentialGeneralEducationRepository.findById(new EssentialGeneralEducationId(department_uid, year, course_uid));

        if (daegyo.isPresent()) {
            return false;
        }

        return seongyo.isPresent();
    }

    public List<Integer> getNumbers(Integer department_uid, Integer year) {
        List<OptionalGeneralEducationEntity> seongyo = optionalGeneralEducationRepository.findByIdDepartmentAndIdYear(department_uid, year);
        List<Integer> numbers = new ArrayList<Integer>();
        for (OptionalGeneralEducationEntity e : seongyo) {
            numbers.add(e.getId().getNumber());
        }
        return numbers;
    }

    @Transactional
    public String editNumbers(Integer department, Integer year, List<Integer> numbers) {
        List<OptionalGeneralEducationEntity> seongyo = optionalGeneralEducationRepository.findByIdDepartmentAndIdYear(department, year);
        for (OptionalGeneralEducationEntity e : seongyo) {
            optionalGeneralEducationRepository.deleteById(e.getId());
        }

        for (Integer n : numbers) {
            OptionalGeneralEducationEntity entity = OptionalGeneralEducationEntity.builder()
                    .id(new OptionalGeneralEducationId(department, year, n))
                    .build();
            optionalGeneralEducationRepository.save(entity);
        }

        List<Integer> current_numbers = getNumbers(department, year);
        String complete_msg = "";
        for (Integer n : numbers) {
            if (!current_numbers.contains(n)) {
                complete_msg += ("이수영역 '" + n + "'이 변경되지 않았습니다.\n");
            }
        }

        if (complete_msg.isEmpty()) {
            complete_msg += "이수영역이 변경되었습니다.";
        }

        return complete_msg;
    }
}
