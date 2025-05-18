package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.dto.UserDTO;
import com.webservice.graduate_coach.entity.AcademyEntity;
import com.webservice.graduate_coach.entity.StudentEntity;
import com.webservice.graduate_coach.entity.UserEntity;
import com.webservice.graduate_coach.param.UserType;
import com.webservice.graduate_coach.repository.AcademyRepository;
import com.webservice.graduate_coach.repository.StudentRepository;
import com.webservice.graduate_coach.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor

public class UserService {
    private final StudentService studentService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final AcademyRepository academyRepository;

    public UserEntity addUser(UserDTO user) {
        var entity = UserEntity.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .email(user.getEmail())
                .university(user.getUniversity())
                .build();
        return userRepository.save(entity);
    }

    public String loginUser(String userId, String password, HttpSession session, Model model) {
        UserEntity user = userRepository.findByUserIdAndPassword(userId, password);

        // 로그인 실패
        if (user == null) {
            model.addAttribute("msg", "아이디 또는 비밀번호가 다릅니다.");
            return "login";
        }

        // 유저 종류 판별 (학생 or 학사팀)
        UserType userType = this.checkUserType(user.getUID());

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
//            model.addAttribute("", "");

            return "academy_dashboard";
        }

        // 나머지는 로그인 페이지로 리다이렉트
        return "login";
    }

    public UserType checkUserType(Integer user) {
        StudentEntity student = studentRepository.findByUser(user);
        AcademyEntity academy = academyRepository.findByUser(user);

        if (student != null && academy == null) {
            return UserType.STUDENT;
        }

        if (student == null && academy != null) {
            return UserType.ACADEMY;
        }

        return UserType.UNKNOWN;
    }
}
