package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.dto.StudentDTO;
import com.webservice.graduate_coach.entity.*;
import com.webservice.graduate_coach.entity.id.*;
import com.webservice.graduate_coach.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class StudentService {
    private final UserRepository userRepository;
    private final UniversityRepository universityRepository;
    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final StudentsMajorRepository studentMajorRepository;
    private final MajorRepository majorRepository;
    private final StudentsCourseRepository studentCourseRepository;
    private final CourseRepository courseRepository;
    private final EarnMajorRepository earnMajorRepository;
    private final GraduateRepository graduateRepository;
    private final CourseTypeRepository courseTypeRepository;
    private final FoundationMajorRepository foundationMajorRepository;
    private final FoundationEducationRepository foundationEducationRepository;
    private final EssentialGeneralEducationRepository essentialGeneralEducationRepository;
    private final OptionalGeneralEducationRepository optionalGeneralEducationRepository;


    public Boolean getDashBoard(Integer user_id, Model model) {
        // 유저 정보 로드
        Optional<UserEntity> result_user = userRepository.findById(user_id);
        if (result_user.isEmpty()) {
            return false;
        }
        UserEntity user = result_user.get();

        // 대학 정보 로드
        Optional<UniversityEntity> result_univ = universityRepository.findById(user.getUID());
        if (result_univ.isEmpty()) {
            return false;
        }
        UniversityEntity univ = result_univ.get();

        // 학생 정보 로드
        StudentEntity student = studentRepository.findByUser(user.getUID());
        if (student == null) {
            return false;
        }

        // 학생 전공리스트 로드
        List<StudentsMajorEntity> studentMajors = studentMajorRepository.findByIdStudent(student.getUID());
        if (studentMajors.isEmpty()) {
            return false;
        }
        Integer majorId = studentMajors.getFirst().getMajor();

        // 전공 정보 로드
        Optional<MajorEntity> result_major = majorRepository.findById(majorId);
        if (result_major.isEmpty()) {
            return false;
        }
        MajorEntity major = result_major.get();

        // 학부 정보 로드
        Optional<DepartmentEntity> result_dept = departmentRepository.findById(major.getDepartment());
        if (result_dept.isEmpty()) {
            return false;
        }
        DepartmentEntity dept = result_dept.get();

        // 수강한 수업리스트 로드
        List<StudentsCourseEntity> coursed = studentCourseRepository.findByIdStudent(student.getUID());

        // 졸업 요건 로드
        Optional<GraduateEntity> result_graduate = graduateRepository.findById(new GraduateId(dept.getUID(), student.getYear()));
        if (result_graduate.isEmpty()) {
            return false;
        }
        GraduateEntity graduate = result_graduate.get();

        // 전공 이수 요건 로드
        Optional<EarnMajorEntity> result_earnmajor = earnMajorRepository.findById(new EarnMajorId(majorId, student.getYear()));
        if (result_earnmajor.isEmpty()) {
            return false;
        }
        EarnMajorEntity earn_major = result_earnmajor.get();

        // 전공필수 과목 로드
        List<CourseTypeEntity> courseType = courseTypeRepository.findByIdMajorAndIdYearAndType(majorId, student.getYear(), 3);
        Float req_jeonpil = 0f;
        for (CourseTypeEntity e : courseType) {
            Optional<CourseEntity> result_course = courseRepository.findById(e.getId().getCourse());
            if (result_course.isEmpty()) {
                continue;
            }
            CourseEntity course = result_course.get();
            req_jeonpil += course.getCredit();
        }

        Float total_credit = 0f;
        Float advanced_credit = 0f;
        Float jeontam_credit = 0f;
        Float pilgyo_credit = 0f;
        Float daegyo_credit = 0f;
        Float seongyo_credit = 0f;
        Float jeonseon_credit = 0f;
        Float jeonpil_credit = 0f;
        for (StudentsCourseEntity e : coursed) {
            Optional<CourseEntity> result_course = courseRepository.findById(e.getId().getCourse());
            if (result_course.isEmpty()) {
                return false;
            }
            CourseEntity course = result_course.get();

            // 전체 이수 학점
            total_credit += course.getCredit();

            // 3000단위 이상 학점
            if (course.getLevel() >= 3000) {
                advanced_credit += course.getCredit();
            }

            // 전공 학점
            Optional<CourseTypeEntity> result_coursetype = courseTypeRepository.findById(new CourseTypeId(majorId, student.getYear(), course.getUID()));
            if (result_coursetype.isPresent()) {
                CourseTypeEntity coursetype = result_coursetype.get();
                switch (coursetype.getType()) {
                    // 전공 선택 학점
                    case 2 :
                        jeonseon_credit += course.getCredit();
                        break;
                    // 전공 필수 학점
                    case 3 :
                        jeonpil_credit += course.getCredit();
                        break;
                }
            }

            // 전탐 이수학점
            Optional<FoundationMajorEntity> result_jeontam = foundationMajorRepository.findById(new FoundationMajorId(dept.getUID(), student.getYear(), course.getUID()));
            if (result_jeontam.isPresent()) {
                jeontam_credit += course.getCredit();
            }

            // 필교 이수학점
            Optional<FoundationEducationEntity> result_pilgyo = foundationEducationRepository.findById(new FoundationEducationId(dept.getUID(), student.getYear(), course.getUID()));
            if (result_pilgyo.isPresent()) {
                pilgyo_credit += course.getCredit();
            }

            // 대교(필수) 이수학점
            Optional<EssentialGeneralEducationEntity> result_daegyo = essentialGeneralEducationRepository.findById(new EssentialGeneralEducationId(dept.getUID(), student.getYear(), course.getUID()));
            if (result_daegyo.isPresent()) {
                daegyo_credit += course.getCredit();
            }

            // 대교(선택) 이수학점
            if (course.getNumber() != null) {
                Optional<OptionalGeneralEducationEntity> result_seongyo = optionalGeneralEducationRepository.findById(new OptionalGeneralEducationId(dept.getUID(), student.getYear(), course.getNumber()));
                if (result_seongyo.isPresent()) {
                    seongyo_credit += course.getCredit();
                }
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
        model.addAttribute("jeon_pil_req_credit", req_jeonpil);
        model.addAttribute("jeon_seon_credit", jeonseon_credit);
        model.addAttribute("jeon_seon_req_credit", earn_major.getTotalCredit() - req_jeonpil);

        return true;
    }
}
