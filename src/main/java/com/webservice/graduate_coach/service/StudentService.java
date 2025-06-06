package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.*;
import com.webservice.graduate_coach.entity.id.StudentsCourseId;
import com.webservice.graduate_coach.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class StudentService {
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final ForeignCertRepository foreignCertRepository;
    private final CommunicationCertRepository communicationCertRepository;
    private final StudentsCourseRepository studentsCourseRepository;

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

    // 회원가입한 학생에 대한 강의정보 임시 생성용 (MVP)
    public Boolean addTemporaryData(Integer user_uid) {
        // 유저 정보 로드
        Optional<UserEntity> result_user = userRepository.findById(user_uid);
        if (result_user.isEmpty()) {
            return false;
        }
        UserEntity user = result_user.get();
        int rand = 1;

        // 학생 정보 로드
        StudentEntity std = studentRepository.findByUser(user.getUID());

        // 전공 정보 로드
        MajorEntity mj = studentsMajorService.getFirstMajor(std.getUID());

        // 학부 정보 로드
        DepartmentEntity dept = departmentService.getDepartment(mj.getDepartment());

        // 외국어인증 여부 (50% 확률로 인증처리)
        rand = (int) ((Math.random()*100)+1);
        if (rand <= 50) {
            std.setForeignCert(1);
            std = studentRepository.save(std);
        }

        // 정보인증 여부 (50% 확률로 인증처리)
        rand = (int) ((Math.random()*100)+1);
        if (rand <= 50) {
            std.setCommunicationCert(1);
            std = studentRepository.save(std);
        }

        // 이수 필요한 필수교양 과목목록 (각 과목별 75% 확률로 이수처리)
        List<CourseEntity> pilgyo = foundationEducationService.getPilgyos(dept.getUID(), std.getYear());
        for (CourseEntity e : pilgyo) {
            rand = (int) ((Math.random()*100)+1);
            if (rand <= 75) {
                StudentsCourseEntity sce = StudentsCourseEntity.builder()
                        .id(new StudentsCourseId(std.getUID(), e.getUID()))
                        .grade(0.0f)
                        .state(0)
                        .build();
                sce = studentsCourseRepository.save(sce);
            }
        }

        // 이수 필요한 대학교양(필수) 과목목록 (75% 확률로 이수처리)
        List<CourseEntity> daegyo = essentialGeneralEducationService.getDaegyos(dept.getUID(), std.getYear());
        for (CourseEntity e : daegyo) {
            rand = (int) ((Math.random()*100)+1);
            if (rand <= 75) {
                StudentsCourseEntity sce = StudentsCourseEntity.builder()
                        .id(new StudentsCourseId(std.getUID(), e.getUID()))
                        .grade(0.0f)
                        .state(0)
                        .build();
                sce = studentsCourseRepository.save(sce);
            }
        }

        // 이수 필요한 전공탐색 과목목록 (75% 확률로 이수처리)
        List<CourseEntity> jeontam = foundationMajorService.getJeontams(dept.getUID(), std.getYear());
        for (CourseEntity e : jeontam) {
            rand = (int) ((Math.random()*100)+1);
            if (rand <= 75) {
                StudentsCourseEntity sce = StudentsCourseEntity.builder()
                        .id(new StudentsCourseId(std.getUID(), e.getUID()))
                        .grade(0.0f)
                        .state(0)
                        .build();
                sce = studentsCourseRepository.save(sce);
            }
        }

        // 이수 필요한 전공필수 과목목록 (75% 확률로 이수처리)
        List<CourseEntity> jeonpil = courseTypeService.getCoursesDetail(mj.getUID(), std.getYear(), 3);
        for (CourseEntity e : jeonpil) {
            rand = (int) ((Math.random()*100)+1);
            if (rand <= 75) {
                StudentsCourseEntity sce = StudentsCourseEntity.builder()
                        .id(new StudentsCourseId(std.getUID(), e.getUID()))
                        .grade(0.0f)
                        .state(0)
                        .build();
                sce = studentsCourseRepository.save(sce);
            }
        }

        // 전공선택 과목목록 (20% 확률로 이수처리)
        List<CourseEntity> jeonseon = courseTypeService.getCoursesDetail(mj.getUID(), std.getYear(), 2);
        for (CourseEntity e : jeonseon) {
            rand = (int) ((Math.random()*100)+1);
            if (rand <= 20) {
                StudentsCourseEntity sce = StudentsCourseEntity.builder()
                        .id(new StudentsCourseId(std.getUID(), e.getUID()))
                        .grade(0.0f)
                        .state(0)
                        .build();
                sce = studentsCourseRepository.save(sce);
            }
        }

        return true;
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

        // 외국어인증 로드
        ForeignCertEntity foreign = null;
        if (student.getForeignCert() != null) {
            foreign = foreignCertRepository.findById(student.getForeignCert()).orElse(null);
        }

        // 정보인증 로드
        CommunicationCertEntity comm = null;
        if (student.getCommunicationCert() != null) {
            comm = communicationCertRepository.findById(student.getCommunicationCert()).orElse(null);
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
        GraduateEntity graduate = graduateService.getGraduate(dept.getUID(), student.getYear());
        if (graduate == null) {
            return false;
        }

        // 전공 이수 요건 로드
        EarnMajorEntity earn_major = earnMajorService.getEarnMajor(major.getUID(), student.getYear());
        if (earn_major == null) {
            return false;
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

        List<CourseEntity> pilgyo_list = new ArrayList<>();
        List<CourseEntity> daegyo_list = new ArrayList<>();
        List<CourseEntity> seongyo_list = new ArrayList<>();
        List<CourseEntity> jeontam_list = new ArrayList<>();
        List<CourseEntity> advanced_list = new ArrayList<>();
        List<CourseEntity> jeonpil_list = new ArrayList<>();
        List<CourseEntity> jeonseon_list = new ArrayList<>();
        List<CourseEntity> recom_pilgyo_list = new ArrayList<>();
        List<CourseEntity> recom_daegyo_list = new ArrayList<>();
        List<CourseEntity> recom_jeontam_list = new ArrayList<>();
        List<CourseEntity> recom_jeonpil_list = new ArrayList<>();

        for (CourseEntity e : coursed) {

            // 전체 이수 학점
            total_credit += e.getCredit();

            // 3000단위 이상 과목 이수 학점
            if (e.getLevel() >= 3000) {
                advanced_credit += e.getCredit();
                advanced_list.add(e);
            }

            // 전공 학점
            CourseTypeEntity course_type = courseTypeService.getCourse(major.getUID(), student.getYear(), e.getUID());
            if (course_type != null) {
                switch (course_type.getType()) {
                    // 전공 선택 학점
                    case 2 :
                        jeonseon_credit += e.getCredit();
                        jeonseon_list.add(e);
                        break;
                    // 전공 필수 학점
                    case 3 :
                        jeonpil_credit += e.getCredit();
                        jeonpil_list.add(e);
                        break;
                }
            }

            // 전공탐색 이수학점
            Boolean isJeontam = foundationMajorService.isJeonTam(dept.getUID(), student.getYear(), e.getUID());
            if (isJeontam) {
                jeontam_credit += e.getCredit();
                jeontam_list.add(e);
            }

            // 필수교양 이수학점
            Boolean isPilgyo = foundationEducationService.isPilGyo(dept.getUID(), student.getYear(), e.getUID());
            if (isPilgyo) {
                pilgyo_credit += e.getCredit();
                pilgyo_list.add(e);
            }

            // 대학교양(필수) 이수학점
            Boolean isDaegyo = essentialGeneralEducationService.isDaeGyo(dept.getUID(), student.getYear(), e.getUID());
            if (isDaegyo) {
                daegyo_credit += e.getCredit();
                daegyo_list.add(e);
            }

            // 대학교양(선택) 이수학점
            Boolean isSeongyo = optionalGeneralEducationService.isSeonGyo(dept.getUID(), student.getYear(), e.getUID(), e.getNumber());
            if (isSeongyo) {
                seongyo_credit += e.getCredit();
                seongyo_list.add(e);
            }
        }

        // 이수 필요한 필수교양 과목목록
        List<CourseEntity> pilgyo = foundationEducationService.getPilgyos(dept.getUID(), student.getYear());
        for (CourseEntity e : pilgyo) {
            if (!pilgyo_list.contains(e)) {
                recom_pilgyo_list.add(e);
            }
        }

        // 이수 필요한 대학교양(필수) 과목목록
        List<CourseEntity> daegyo = essentialGeneralEducationService.getDaegyos(dept.getUID(), student.getYear());
        for (CourseEntity e : daegyo) {
            if (!pilgyo_list.contains(e)) {
                recom_daegyo_list.add(e);
            }
        }

        // 이수 필요한 전공탐색 과목목록
        List<CourseEntity> jeontam = foundationMajorService.getJeontams(dept.getUID(), student.getYear());
        for (CourseEntity e : jeontam) {
            if (!pilgyo_list.contains(e)) {
                recom_jeontam_list.add(e);
            }
        }

        // 이수 필요한 전공필수 과목목록
        List<CourseEntity> jeonpil = courseTypeService.getCoursesDetail(major.getUID(), student.getYear(), 3);
        for (CourseEntity e : jeonpil) {
            if (!pilgyo_list.contains(e)) {
                recom_jeonpil_list.add(e);
            }
        }

        model.addAttribute("univ", univ.getName());
        model.addAttribute("major", major.getName());
        model.addAttribute("id", student.getStudentNumber());
        model.addAttribute("foreign_cert", foreign);
        model.addAttribute("comm_cert", comm);
        model.addAttribute("total_credit", total_credit);
        model.addAttribute("total_req_credit", graduate.getTotalCredit());
        model.addAttribute("jeontam_credit", jeontam_credit);
        model.addAttribute("jeontam_req_credit", graduate.getFoundationMajor());
        model.addAttribute("pilgyo_credit", pilgyo_credit);
        model.addAttribute("pilgyo_req_credit", graduate.getFoundationEdu());
        model.addAttribute("daegyo_credit", daegyo_credit);
        model.addAttribute("daegyo_req_credit", graduate.getGeneralEdu());
        model.addAttribute("seongyo_credit", seongyo_credit);
        model.addAttribute("seongyo_req_credit", graduate.getOptionalEdu());
        model.addAttribute("advanced_credit", advanced_credit);
        model.addAttribute("advanced_req_credit", graduate.getTotalLevel());
        model.addAttribute("jeonpil_credit", jeonpil_credit);
        model.addAttribute("jeonpil_req_credit", earn_major.getReqMajorCredit());
        model.addAttribute("jeonseon_credit", jeonseon_credit);
        model.addAttribute("jeonseon_req_credit", earn_major.getOptMajorCredit());
        model.addAttribute("pilgyo_list", pilgyo_list);
        model.addAttribute("daegyo_list", daegyo_list);
        model.addAttribute("seongyo_list", seongyo_list);
        model.addAttribute("jeontam_list", jeontam_list);
        model.addAttribute("advanced_list", advanced_list);
        model.addAttribute("jeonpil_list", jeonpil_list);
        model.addAttribute("jeonseon_list", jeonseon_list);
        model.addAttribute("recom_pilgyo_list", recom_pilgyo_list);
        model.addAttribute("recom_daegyo_list", recom_daegyo_list);
        model.addAttribute("recom_jeontam_list", recom_jeontam_list);
        model.addAttribute("recom_jeonpil_list", recom_jeonpil_list);

        return true;
    }
}
