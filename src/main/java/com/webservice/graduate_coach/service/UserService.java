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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class UserService implements UserDetailsService {
    private final StudentService studentService;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final AcademyRepository academyRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final AcademyService academyService;

    public UserEntity addUser(UserDTO user) {
        var entity = UserEntity.builder()
                .userid(user.getUserId())
                .password(user.getPassword())
                .email(user.getEmail())
                .university(user.getUniversity())
                .build();
        return userRepository.save(entity);
    }

    public boolean register(UserDTO dto) {
        // 1) 아이디 중복 체크
        if (userRepository.existsByUserid(dto.getUserId())) {
            return false;
        }

        // 2) 비밀번호 암호화 후 UserEntity 저장
        UserEntity user = UserEntity.builder()
                .userid(dto.getUserId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .university(dto.getUniversity())
                .build();
        user = userRepository.save(user);

        // 3) 학생/학사팀 프로필 생성
        if (dto.getType() == UserType.STUDENT) {
            StudentEntity student = StudentEntity.builder()
                    .user(user.getUID())
                    .studentNumber(dto.getUserId())
                    .communicationCert(null)  // 기본값 false 또는 0
                    .foreignCert(null)        // 기본값 false 또는 0
                    .year(LocalDate.now().getYear())  // 예: 현재 연도
                    .build();
            studentRepository.save(student);
        } else if (dto.getType() == UserType.ACADEMY) {
            AcademyEntity academy = AcademyEntity.builder()
                    .user(user.getUID())
                    .build();
            academyRepository.save(academy);
        }

        return true;
    }

    public String login(String userId, HttpSession session) {
        UserEntity user = userRepository.findByUserid(userId);

        // 유저 종류 판별 (학생 or 학사팀)
        UserType userType = checkUserType(user.getUID());

        // 로그인 상태 유지를 위한 세션정보 저장
        session.setAttribute("user", user.getUID());
        session.setAttribute("user_type", userType);

        // 로그인 시도한 유저가 학생 유저일 경우,
        if (userType == UserType.STUDENT) {
            return "/student_dashboard";
        }

        // 로그인 시도한 유저가 학사팀 유저일 경우,
        if (userType == UserType.ACADEMY) {
            return "/academy_dashboard";
        }

        // 나머지는 로그인 페이지로 리다이렉트
        return "/login";
    }

    // 유저 타입 판별
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

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUserid(userId);
        if (user == null) {
            throw new UsernameNotFoundException("해당 유저를 찾을 수 없습니다: " + userId);
        }

        return User.builder()
                .username(user.getUserid())
                .password(user.getPassword()) // 이미 암호화된 비밀번호
                .roles("USER") // 필요시 "STUDENT", "ACADEMY"로 구분 가능
                .build();
    }
}
