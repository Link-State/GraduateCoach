package com.webservice.graduate_coach.controller;
import com.webservice.graduate_coach.param.UserType;
import com.webservice.graduate_coach.dto.UserDTO;
import com.webservice.graduate_coach.entity.UserEntity;
import com.webservice.graduate_coach.service.StudentService;
import com.webservice.graduate_coach.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor

public class APIController {

    private final UserService userService;
    private final StudentService studentService;

    @PostMapping("/login")
    public String loginRequest(
            @RequestParam(name="user_id") String user_id,
            @RequestParam(name="user_pwd") String user_pwd,
            HttpSession session,
            Model model
    ) {
        // 로그인 실패 - 아이디 또는 비번 공백
        if (user_id.isEmpty() || user_pwd.isEmpty()) {
            return "login";
        }

        return userService.loginUser(user_id, user_pwd, session, model);
    }

    @PostMapping("/signup")
    public String signupRequest(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam("university") Integer university,
            @RequestParam String major,
            @RequestParam("id") String userId,
            @RequestParam("pwd") String pwd,
            @RequestParam("pwd_check") String pwdCheck,
            @RequestParam(value = "type", required = false, defaultValue = "STUDENT") String userTypeStr,
            HttpSession session,
            Model model
    ) {
        // 1) 비번 확인
        if (!pwd.equals(pwdCheck)) {
            model.addAttribute("msg", "비밀번호가 일치하지 않습니다.");
            return "signup";
        }

        UserType userType = UserType.valueOf(userTypeStr.toUpperCase()); // 기본값 STUDENT
        // 2) 회원가입 시도
        UserDTO dto = new UserDTO();
        dto.setName(name);
        dto.setUserId(userId);
        dto.setPassword(pwd);
        dto.setEmail(email);
        dto.setUniversity(university);
        dto.setMajor(major);
        dto.setType(userType); // STUDENT or ACADEMY

        boolean ok = userService.register(dto);
        if (!ok) {
            model.addAttribute("msg", "이미 존재하는 아이디입니다.");
            return "signup";
        }

        model.addAttribute("success_signup_msg", "회원가입 성공! 로그인 해주세요.");
        return "login";
    }

    @PostMapping("/logout")
    public String logoutRequest(
            HttpSession session,
            Model model
    ) {
        // 로그인 세션 삭제
//        session.removeAttribute("");
        return "welcome";
    }
}
