package com.webservice.graduate_coach.service;

import com.webservice.graduate_coach.entity.DepartmentEntity;
import com.webservice.graduate_coach.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentEntity getDepartment(Integer uid) {
        Optional<DepartmentEntity> dept = departmentRepository.findById(uid);
        return dept.orElse(null);
    }
}
