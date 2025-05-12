package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
}
