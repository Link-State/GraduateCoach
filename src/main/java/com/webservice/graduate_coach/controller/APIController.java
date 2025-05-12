package com.webservice.graduate_coach.controller;

import com.webservice.graduate_coach.service.StudentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor

public class APIController {

    @PostMapping("/login")
    public String loginRequest(
            @RequestParam(name="user_id") String user_id,
            @RequestParam(name="user_pwd") String user_pwd,
            HttpSession session,
            Model model
    ) {
        // 로그인 실패
        if (user_id.isEmpty() || user_pwd.isEmpty()) {
            model.addAttribute("msg", "아이디 또는 비밀번호 없음");
            return "login";
        }

        System.out.println(user_id);
        System.out.println(user_pwd);
        // 로그인 성공

        // 로그인 세션 추가
//        session.setAttribute("", );

        return "dashboard";
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
