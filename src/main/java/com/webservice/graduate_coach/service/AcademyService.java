package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.*;
import com.webservice.graduate_coach.param.CourseNumber;
import com.webservice.graduate_coach.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.HashMap;
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
        return getDashBoard(user_id, 1, 2022, 1, model);
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
        List<Integer> seongyo_numbers = optionalGeneralEducationService.getNumbers(dept, year);
        List<CourseNumber> seongyo = new ArrayList<CourseNumber>();
        for (Integer n : seongyo_numbers) {
            switch (n) {
                case 1 :
                    seongyo.add(new CourseNumber(n, "문학과예술"));
                    break;
                case 2 :
                    seongyo.add(new CourseNumber(n, "인간과역사"));
                    break;
                case 3 :
                    seongyo.add(new CourseNumber(n, "가치와윤리"));
                    break;
                case 4 :
                    seongyo.add(new CourseNumber(n, "언어와표현"));
                    break;
                case 5 :
                    seongyo.add(new CourseNumber(n, "국가와사회"));
                    break;
                case 6 :
                    seongyo.add(new CourseNumber(n, "지역과세계"));
                    break;
                case 7 :
                    seongyo.add(new CourseNumber(n, "논리와수리"));
                    break;
                case 8 :
                    seongyo.add(new CourseNumber(n, "자연과우주"));
                    break;
                case 9 :
                    seongyo.add(new CourseNumber(n, "생명과환경"));
                    break;
                case 10 :
                    seongyo.add(new CourseNumber(n, "정보와기술"));
                    break;
                case 11 :
                    seongyo.add(new CourseNumber(n, "체육과건강"));
                    break;
            }
        }

        // 전공탐색 과목목록 로드
        List<CourseEntity> jeontam = foundationMajorService.getJeontams(dept, year);

        // 전공 과목목록 로드
        List<CourseEntity> jeonpil = courseTypeService.getCoursesDetail(major, year, 3);
        List<CourseEntity> jeonseon = courseTypeService.getCoursesDetail(major, year, 2);

        // 외국어인증 목록
        List<ForeignCertEntity> foreign_certs = foreignCertRepository.findByDepartmentAndYear(dept, year);

        // 정보인증 목록
        List<CommunicationCertEntity> comm_certs = communicationCertRepository.findByDepartmentAndYear(dept, year);

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("department_list", department_list);
        params.put("major_list", major_list);
        params.put("selected_dept", hasDepartment);
        params.put("selected_major", hasMajor);
        params.put("selected_year", year);
        params.put("pilgyo_list", pilgyo);
        params.put("daegyo_list", daegyo);
        params.put("seongyo_number_list", seongyo);
        params.put("jeontam_list", jeontam);
        params.put("jeonpil_list", jeonpil);
        params.put("jeonseon_list", jeonseon);
        params.put("pilgyo_req_credit", graduate.getFoundationEdu());
        params.put("daegyo_req_credit", graduate.getGeneralEdu());
        params.put("seongyo_req_credit", graduate.getOptionalEdu());
        params.put("jeontam_req_credit", graduate.getFoundationMajor());
        params.put("advanced_req_credit", graduate.getTotalLevel());
        params.put("jeonpil_req_credit", earn_major.getReqMajorCredit());
        params.put("jeonseon_req_credit", earn_major.getOptMajorCredit());
        params.put("foreign_cert_list", foreign_certs);
        params.put("comm_cert_list", comm_certs);

        if (model instanceof RedirectAttributes attr) {
            for (String key : params.keySet()) {
                attr.addFlashAttribute(key, params.get(key));
            }
        } else {
            for (String key : params.keySet()) {
                model.addAttribute(key, params.get(key));
            }
        }

        return true;
    }
}
