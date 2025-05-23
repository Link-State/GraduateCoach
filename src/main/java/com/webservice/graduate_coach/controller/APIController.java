package com.webservice.graduate_coach.controller;
import com.webservice.graduate_coach.entity.id.CourseTypeId;
import com.webservice.graduate_coach.entity.id.EssentialGeneralEducationId;
import com.webservice.graduate_coach.entity.id.FoundationEducationId;
import com.webservice.graduate_coach.entity.id.FoundationMajorId;
import com.webservice.graduate_coach.param.UserType;
import com.webservice.graduate_coach.dto.UserDTO;
import com.webservice.graduate_coach.repository.CourseTypeRepository;
import com.webservice.graduate_coach.repository.EssentialGeneralEducationRepository;
import com.webservice.graduate_coach.repository.FoundationEducationRepository;
import com.webservice.graduate_coach.repository.FoundationMajorRepository;
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

public class APIController {

    private final UserService userService;
    private final StudentService studentService;
    private final AcademyService academyService;

    private GraduateService graduateService;
    private EarnMajorService earnMajorService;
    private final FoundationEducationService foundationEducationService;
    private final EssentialGeneralEducationService essentialGeneralEducationService;
    private final OptionalGeneralEducationService optionalGeneralEducationService;
    private final FoundationMajorService foundationMajorService;
    private final CourseTypeService courseTypeService;

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
        session.removeAttribute("user");
        session.removeAttribute("user_type");
        return "welcome";
    }

    @PostMapping("/academy_dashboard")
    public String academyDashboardPage(
            @RequestParam(name="department") Integer dept,
            @RequestParam(name="year") Integer year,
            @RequestParam(name="major") Integer major,
            HttpSession session,
            RedirectAttributes model
    ) {
        // 로그인 세션 확인
        Integer user_uid = (Integer) session.getAttribute("user");
        UserType user_type = (UserType) session.getAttribute("user_type");

        if (user_uid == null || user_type == null) {
            model.addFlashAttribute("msg", "로그인이 필요한 서비스 입니다.");
            return "login";
        }

        academyService.getDashBoard(user_uid, dept, year, major, model);

        return "academy_dashboard";
    }

    @PostMapping("/exclude-course")
    public String excludeCourse(
            @RequestParam Integer type,
            @RequestParam Integer department,
            @RequestParam Integer year,
            @RequestParam Integer major,
            @RequestParam(required = false) List<Integer> courses,
            HttpSession session,
            RedirectAttributes model
    ) {
        Integer user_uid = (Integer) session.getAttribute("user");

        if (courses == null) {
            model.addFlashAttribute("exclude_course_msg", "삭제할 과목을 선택해주세요.");
            academyService.getDashBoard(user_uid, department, year, major, model);
            return "redirect:academy_dashboard";
        }

        String complete_msg = switch (type) {
            // 교양기초
            case 1 -> foundationEducationService.deleteCourses(department, year, courses);

            // 대학교양
            case 2 -> essentialGeneralEducationService.deleteCourses(department, year, courses);

            // 전공탐색
            case 3 -> foundationMajorService.deleteCourses(department, year, courses);

            // 전공필수
            case 4 -> courseTypeService.deleteCourses(major, year, courses);

            // 그 외
            default -> "존재하지 않는 type 입니다.";
        };

        model.addFlashAttribute("exclude_course_msg", complete_msg);
        Boolean result = academyService.getDashBoard(user_uid, department, year, major, model);

        return "redirect:academy_dashboard";
    }

    @PostMapping("/edit-credit")
    public String editCredit(
            @RequestParam Integer department,
            @RequestParam Integer year,
            @RequestParam Integer major,
            @RequestParam Integer pilgyo_credit,
            @RequestParam Integer daegyo_credit,
            @RequestParam Integer jeontam_credit,
            @RequestParam Integer advanced_credit,
            @RequestParam Integer jeonpil_credit,
            @RequestParam Integer jeonseon_credit,
            HttpSession session,
            RedirectAttributes model
            ) {
        Integer user_uid = (Integer) session.getAttribute("user");

        String complete_msg1 = graduateService.editCredit(department, year, pilgyo_credit, daegyo_credit, jeontam_credit, advanced_credit);
        String complete_msg2 = earnMajorService.editCredit(major, year, jeonpil_credit, jeonseon_credit);
        String complete_msg = complete_msg1 + complete_msg2;

        model.addFlashAttribute("edit_credit_msg", complete_msg);
        Boolean result = academyService.getDashBoard(user_uid, department, year, major, model);

        return "redirect:academy_dashboard";
    }

    @PostMapping("/edit-number")
    public String editNumber(
            @RequestParam Integer department,
            @RequestParam Integer year,
            @RequestParam Integer major,
            @RequestParam(required = false) List<Integer> numbers,
            HttpSession session,
            RedirectAttributes model
    ) {
        Integer user_uid = (Integer) session.getAttribute("user");

        if (numbers == null) {
            model.addFlashAttribute("edit_number_msg", "허용할 이수영역번호를 선택해주세요.");
            return "redirect:academy_dashboard";
        }

        String complete_msg = optionalGeneralEducationService.editNumbers(department, year, numbers);
        model.addFlashAttribute("edit_number_msg", complete_msg);
        Boolean result = academyService.getDashBoard(user_uid, department, year, major, model);

        return "redirect:academy_dashboard";
    }
}
