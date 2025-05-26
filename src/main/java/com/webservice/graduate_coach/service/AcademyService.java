package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.*;
import com.webservice.graduate_coach.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AcademyService {
    private final UserRepository userRepository;
    private final AcademyRepository academyRepository;
    private final DepartmentRepository departmentRepository;
    private final MajorRepository majorRepository;

    private final GraduateService graduateService;
    private final UniversityService universityService;
    private final CourseTypeService courseTypeService;
    private final FoundationMajorService foundationMajorService;
    private final FoundationEducationService foundationEducationService;
    private final EssentialGeneralEducationService essentialGeneralEducationService;
    private final OptionalGeneralEducationService optionalGeneralEducationService;
    private final EarnMajorService earnMajorService;
    private final ForeignCertRepository foreignCertRepository;
    private final CommunicationCertRepository communicationCertRepository;

    public Boolean getDashBoard(Integer user_id, Model model) {
        return getDashBoard(user_id, 1, 2025, 1, model);
    }
    public Boolean getDashBoard(Integer user_id, Integer dp, Integer year, Integer mj, Model model) {
        // 유저 정보 로드
        Optional<UserEntity> result_user = userRepository.findById(user_id);
        if (result_user.isEmpty()) {
            return false;
        }
        UserEntity user = result_user.get();

        // 대학 정보 로드
        UniversityEntity univ = universityService.getUniversity(user.getUniversity());
        if (univ == null) {
            return false;
        }

        // 소속대학의 학부목록 로드
        List<DepartmentEntity> department_list = departmentRepository.findByUniversity(univ.getUID());
        if (department_list.isEmpty()) {
            return false;
        }

        // 선택한 학부가 해당 소속학교에 존재하지 않을 경우, 소속학교의 첫번째 학부선택
        Integer dept = dp;
        DepartmentEntity hasDepartment = departmentRepository.findByUIDAndUniversity(dept, univ.getUID());
        if (hasDepartment == null) {
            dept = department_list.getFirst().getUID();
            hasDepartment = departmentRepository.findById(dept).get();
        }

        // 선택한 학부의 전공목록 로드
        List<MajorEntity> major_list = majorRepository.findByDepartment(dept);

        // 선택한 전공이 해당 학부에 존재하지 않을 경우, 선택한 학부의 첫번째 전공선택
        Integer major = mj;
        MajorEntity hasMajor = majorRepository.findByUIDAndDepartment(major, dept);
        if (hasMajor == null) {
            major = major_list.getFirst().getUID();
            hasMajor = majorRepository.findById(major).get();
        }

        // 학부 졸업요건 로드
        GraduateEntity graduate = graduateService.getGraduate(dept, year);
        if (graduate == null) {
            return false;
        }

        // 전공학점 로드
        EarnMajorEntity earn_major = earnMajorService.getEarnMajor(major, year);

        // 교양기초 과목목록 로드
        List<CourseEntity> pilgyo = foundationEducationService.getPilgyos(dept, year);

        // 대학교양(필수) 과목목록 로드
        List<CourseEntity> daegyo = essentialGeneralEducationService.getDaegyos(dept, year);

        // 대학교양 이수영역 번호목록 로드
        List<Integer> seongyo = optionalGeneralEducationService.getNumbers(dept, year);

        // 전공탐색 과목목록 로드
        List<CourseEntity> jeontam = foundationMajorService.getJeontams(dept, year);

        // 전공 과목목록 로드
        List<CourseEntity> jeonpil = courseTypeService.getCoursesDetail(major, year, 3);
        List<CourseEntity> jeonseon = courseTypeService.getCoursesDetail(major, year, 2);

        // 외국어인증 목록
        List<ForeignCertEntity> foreign_certs = foreignCertRepository.findByDepartmentAndYear(dept, year);

        // 정보인증 목록
        List<CommunicationCertEntity> comm_certs = communicationCertRepository.findByDepartmentAndYear(dept, year);

        model.addAttribute("department_list", department_list);
        model.addAttribute("major_list", major_list);
        model.addAttribute("selected_dept", hasDepartment);
        model.addAttribute("selected_major", hasMajor);
        model.addAttribute("selected_year", year);
        model.addAttribute("pilgyo_list", pilgyo);
        model.addAttribute("daegyo_list", daegyo);
        model.addAttribute("seongyo_number_list", seongyo);
        model.addAttribute("jeontam_list", jeontam);
        model.addAttribute("jeonpil_list", jeonpil);
        model.addAttribute("jeonseon_list", jeonseon);
        model.addAttribute("pilgyo_req_credit", graduate.getFoundationEdu());
        model.addAttribute("daegyo_req_credit", graduate.getGeneralEdu());
        model.addAttribute("seongyo_req_credit", graduate.getOptionalEdu());
        model.addAttribute("jeontam_req_credit", graduate.getFoundationMajor());
        model.addAttribute("advanced_req_credit", graduate.getTotalLevel());
        model.addAttribute("jeonpil_req_credit", earn_major.getReqMajorCredit());
        model.addAttribute("jeonseon_req_credit", earn_major.getOptMajorCredit());
        model.addAttribute("foreign_cert_list", foreign_certs);
        model.addAttribute("comm_cert_list", comm_certs);

        return true;
    }
}
