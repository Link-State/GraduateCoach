package com.webservice.graduate_coach.controller;

import com.webservice.graduate_coach.param.UserType;
import com.webservice.graduate_coach.service.AcademyService;
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

public class PageController {

    private final UserService userService;
    private final StudentService studentService;
    private final AcademyService academyService;

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

    @GetMapping("/student_dashboard")
    public String studentDashboardPage(
            HttpSession session,
            Model model
    ) {
        // 로그인 세션 확인
        Integer user_uid = (Integer) session.getAttribute("user");
        UserType user_type = (UserType) session.getAttribute("user_type");

        if (user_uid == null || user_type == null) {
            model.addAttribute("msg", "로그인이 필요한 서비스 입니다.");
            return "login";
        }
        Boolean result = studentService.getDashBoard(user_uid, model);
        return "student_dashboard";
    }

    @GetMapping("/academy_dashboard")
    public String academyDashboardPage(
            HttpSession session,
            Model model
    ) {
        // 로그인 세션 확인
        Integer user_uid = (Integer) session.getAttribute("user");
        UserType user_type = (UserType) session.getAttribute("user_type");

        if (user_uid == null || user_type == null) {
            model.addAttribute("msg", "로그인이 필요한 서비스 입니다.");
            return "login";
        }

        Boolean result = academyService.getDashBoard(user_uid, model);

        return "academy_dashboard";
    }
}
