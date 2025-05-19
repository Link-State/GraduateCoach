package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.dto.UserDTO;
import com.webservice.graduate_coach.entity.AcademyEntity;
import com.webservice.graduate_coach.entity.StudentEntity;
import com.webservice.graduate_coach.entity.UserEntity;
import com.webservice.graduate_coach.param.UserType;
import com.webservice.graduate_coach.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;

    private final StudentService studentService;
    private final AcademyService academyService;

    public UserEntity addUser(UserDTO user) {
        var entity = UserEntity.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .email(user.getEmail())
                .university(user.getUniversity())
                .build();
        return userRepository.save(entity);
    }

    public UserEntity getUser(Integer uid) {
        Optional<UserEntity> user = userRepository.findById(uid);
        return user.orElse(null);
    }

    public String loginUser(String userId, String password, HttpSession session, Model model) {
        UserEntity user = userRepository.findByUserIdAndPassword(userId, password);

        // 로그인 실패
        if (user == null) {
            model.addAttribute("msg", "아이디 또는 비밀번호가 다릅니다.");
            return "login";
        }

        // 유저 종류 판별 (학생 or 학사팀)
        UserType userType = checkUserType(user.getUID());

        // 로그인 상태 유지를 위한 세션정보 저장
        session.setAttribute("user", user.getUID());
        session.setAttribute("user_type", userType);

        // 로그인 시도한 유저가 학생 유저일 경우,
        if (userType == UserType.STUDENT) {
            Boolean result = studentService.getDashBoard(user.getUID(), model);
            return "student_dashboard";
        }

        // 로그인 시도한 유저가 학사팀 유저일 경우,
        if (userType == UserType.ACADEMY) {
            Boolean result = academyService.getDashBoard(user.getUID(), model);
            return "academy_dashboard";
        }

        // 나머지는 로그인 페이지로 리다이렉트
        return "login";
    }

    // 유저 타입 판별
    public UserType checkUserType(Integer user) {
        StudentEntity student = studentService.getStudentByUser(user);
        AcademyEntity academy = academyService.getAcademyByUser(user);
        if (student != null && academy == null) {
            return UserType.STUDENT;
        }
        if (student == null && academy != null) {
            return UserType.ACADEMY;
        }
        return UserType.UNKNOWN;
    }
}
