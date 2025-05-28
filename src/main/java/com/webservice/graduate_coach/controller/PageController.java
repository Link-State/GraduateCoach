package com.webservice.graduate_coach.controller;

import com.webservice.graduate_coach.dto.CommunicationCertDTO;
import com.webservice.graduate_coach.dto.ForeignCertDTO;
import com.webservice.graduate_coach.entity.MajorEntity;
import com.webservice.graduate_coach.entity.UniversityEntity;
import com.webservice.graduate_coach.param.UserType;
import com.webservice.graduate_coach.repository.MajorRepository;
import com.webservice.graduate_coach.repository.UniversityRepository;
import com.webservice.graduate_coach.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor

public class PageController {

    private final UserService userService;
    private final StudentService studentService;
    private final AcademyService academyService;
    private final UniversityRepository universityRepository;
    private final MajorService majorService;
    private final ForeignCertService foreignCertService;
    private final CommunicationCertService communicationCertService;


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
        List<UniversityEntity> university_list = universityRepository.findAll();
        List<MajorEntity> major_list = majorService.getMajorsByUniv(1);

        model.addAttribute("university_list", university_list);
        model.addAttribute("major_list", major_list);
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

        // 1) 새로운 대시보드 데이터
        Boolean result = academyService.getDashBoard(user_uid, model);

//         // 2) 외국어/정보 인증 리스트
//        List<ForeignCertDTO> foreignList = foreignCertService.getForeignCerts(department, year);
//        List<CommunicationCertDTO> commList  = communicationCertService.getCommunicationCerts(department, year);
//        model.addAttribute("ForeignCertList", foreignList);
//        model.addAttribute("CommunicationCertList"   , commList);
//
//        // 3) 폼 바인딩용 빈 DTO
//        model.addAttribute("ForeignCertDTO"   , new ForeignCertDTO());
//        model.addAttribute("CommunicationCertDTO", new CommunicationCertDTO());

        return "academy_dashboard";
    }
}
