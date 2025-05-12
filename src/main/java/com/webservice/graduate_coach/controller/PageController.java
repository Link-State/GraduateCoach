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

public class PageController {

    @GetMapping("/welcome")
    public String welcomePage(
            HttpSession session,
            Model model
    ) {
        return "welcome";
    }

    @GetMapping("/login")
    public String loginPage(
            HttpSession session,
            Model model
    ) {
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(
            HttpSession session,
            Model model
    ) {
        return "signup";
    }

    @GetMapping("/dashboard")
    public String dashboardPage(
            HttpSession session,
            Model model
    ) {
        // 로그인 세션 확인
//        session.getAttribute("user");
        if (true) {
            model.addAttribute("msg", "로그인이 필요한 서비스 입니다.");
            return "login";
        }

        return "dashboard";
    }
}
