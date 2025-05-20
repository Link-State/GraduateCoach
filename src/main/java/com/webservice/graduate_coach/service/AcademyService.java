package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.AcademyEntity;
import com.webservice.graduate_coach.entity.UniversityEntity;
import com.webservice.graduate_coach.entity.UserEntity;
import com.webservice.graduate_coach.repository.AcademyRepository;
import com.webservice.graduate_coach.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AcademyService {
    private final UserRepository userRepository;
    private final AcademyRepository academyRepository;

    private final UniversityService universityService;

    public AcademyEntity getAcademyByUser(Integer user_uid) {
        return academyRepository.findByUser(user_uid);
    }

    public Boolean getDashBoard(Integer user_id, Model model) {
        // 유저 정보 로드
        Optional<UserEntity> result_user = userRepository.findById(user_id);
        if (result_user.isEmpty()) {
            return false;
        }
        UserEntity user = result_user.get();

        // 대학 정보 로드
        UniversityEntity univ = universityService.getUniversity(user.getUniversity());
        if (univ == null) {
            return false;
        }

//        model.addAttribute("", );

        return true;
    }
}
