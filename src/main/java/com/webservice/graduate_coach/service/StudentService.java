package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.*;
import com.webservice.graduate_coach.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class StudentService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    private final UniversityService universityService;
    private final DepartmentService departmentService;
    private final StudentsMajorService studentsMajorService;
    private final StudentsCourseService studentsCourseService;
    private final EarnMajorService earnMajorService;
    private final GraduateService graduateService;
    private final CourseTypeService courseTypeService;
    private final FoundationMajorService foundationMajorService;
    private final FoundationEducationService foundationEducationService;
    private final EssentialGeneralEducationService essentialGeneralEducationService;
    private final OptionalGeneralEducationService optionalGeneralEducationService;

    public StudentEntity getStudentByUser(Integer user_uid) {
        return studentRepository.findByUser(user_uid);
    }

    public Boolean getDashBoard(Integer user_uid, Model model) {
        // 유저 정보 로드
        Optional<UserEntity> result_user = userRepository.findById(user_uid);
        if (result_user.isEmpty()) {
            return false;
        }
        UserEntity user = result_user.get();

        // 대학 정보 로드
        UniversityEntity univ = universityService.getUniversity(user.getUniversity());
        if (univ == null) {
            return false;
        }

        // 학생 정보 로드
        StudentEntity student = studentRepository.findByUser(user.getUID());
        if (student == null) {
            return false;
        }

        // 제 1전공 정보 로드
        MajorEntity major = studentsMajorService.getFirstMajor(student.getUID());
        if (major == null) {
            return false;
        }

        // 학부 정보 로드
        DepartmentEntity dept = departmentService.getDepartment(major.getDepartment());
        if (dept == null) {
            return false;
        }

        // 졸업 요건 로드
        GraduateEntity graduate = graduateService.getGraduate(student.getUID(), student.getYear());
        if (graduate == null) {
            return false;
        }

        // 전공 이수 요건 로드
        EarnMajorEntity earn_major = earnMajorService.getEarnMajor(major.getUID(), student.getYear());
        if (earn_major == null) {
            return false;
        }

        // 전공필수 과목목록 로드
        List<CourseEntity> jeonpil_courses = courseTypeService.getCoursesDetail(major.getUID(), student.getYear(), 3);
        if (jeonpil_courses == null) {
            return false;
        }

        // -- 전공필수 과목 총 학점 계산
        Float req_jeonpil_credit = 0f;
        for (CourseEntity e : jeonpil_courses) {
            req_jeonpil_credit += e.getCredit();
        }

        // 수강한 수업목록 로드
        List<CourseEntity> coursed = studentsCourseService.getCoursesDetail(student.getUID());

        // 수강한 수업목록 순회 -> 각 이수학점 계산
        Float total_credit = 0f;
        Float advanced_credit = 0f;
        Float jeontam_credit = 0f;
        Float pilgyo_credit = 0f;
        Float daegyo_credit = 0f;
        Float seongyo_credit = 0f;
        Float jeonseon_credit = 0f;
        Float jeonpil_credit = 0f;
        for (CourseEntity e : coursed) {

            // 전체 이수 학점
            total_credit += e.getCredit();

            // 3000단위 이상 과목 이수 학점
            if (e.getLevel() >= 3000) {
                advanced_credit += e.getCredit();
            }

            // 전공 학점
            CourseTypeEntity course_type = courseTypeService.getCourse(major.getUID(), student.getYear(), e.getUID());
            if (course_type != null) {
                switch (course_type.getType()) {
                    // 전공 선택 학점
                    case 2 :
                        jeonseon_credit += e.getCredit();
                        break;
                    // 전공 필수 학점
                    case 3 :
                        jeonpil_credit += e.getCredit();
                        break;
                }
            }

            // 전공탐색 이수학점
            Boolean isJeontam = foundationMajorService.isJeonTam(dept.getUID(), student.getYear(), e.getUID());
            if (isJeontam) {
                jeontam_credit += e.getCredit();
            }

            // 필수교양 이수학점
            Boolean isPilgyo = foundationEducationService.isPilGyo(dept.getUID(), student.getYear(), e.getUID());
            if (isPilgyo) {
                pilgyo_credit += e.getCredit();
            }

            // 대학교양(필수) 이수학점
            Boolean isDaegyo = essentialGeneralEducationService.isDaeGyo(dept.getUID(), student.getYear(), e.getUID());
            if (isDaegyo) {
                daegyo_credit += e.getCredit();
            }

            // 대학교양(선택) 이수학점
            Boolean isSeongyo = optionalGeneralEducationService.isSeonGyo(dept.getUID(), student.getYear(), e.getNumber());
            if (isSeongyo) {
                seongyo_credit += e.getCredit();
            }
        }

        model.addAttribute("univ", univ.getName());
        model.addAttribute("major", major.getName());
        model.addAttribute("id", student.getStudentNumber());
        model.addAttribute("foreign_cert", student.getForeignCert());
        model.addAttribute("comm_cert", student.getCommunicationCert());
        model.addAttribute("total_credit", total_credit);
        model.addAttribute("total_req_credit", graduate.getTotalCredit());
        model.addAttribute("jeon_tam_credit", jeontam_credit);
        model.addAttribute("jeon_tam_req_credit", graduate.getFoundationMajor());
        model.addAttribute("pil_gyo_credit", pilgyo_credit);
        model.addAttribute("pil_gyo_req_credit", graduate.getFoundationEdu());
        model.addAttribute("dae_gyo_credit", daegyo_credit);
        model.addAttribute("dae_gyo_req_credit", graduate.getGeneralEdu());
        model.addAttribute("seon_gyo_credit", seongyo_credit);
        model.addAttribute("seon_gyo_req_credit", graduate.getOptionalEdu());
        model.addAttribute("advanced_credit", advanced_credit);
        model.addAttribute("advanced_req_credit", graduate.getTotalLevel());
        model.addAttribute("jeon_pil_credit", jeonpil_credit);
        model.addAttribute("jeon_pil_req_credit", req_jeonpil_credit);
        model.addAttribute("jeon_seon_credit", jeonseon_credit);
        model.addAttribute("jeon_seon_req_credit", earn_major.getTotalCredit() - req_jeonpil_credit);

        return true;
    }
}
