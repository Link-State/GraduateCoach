package com.webservice.graduate_coach.controller;

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
            HttpSession session,
            Model model
    ) {
        // 회원가입 실패
        if (true) {
            model.addAttribute("msg", "회원가입 실패임");
            return "signup";
        }

        // 회원가입 성공


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
