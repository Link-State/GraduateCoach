package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Integer> {
    StudentEntity findByUser(Integer user);
}
