package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.StudentsMajorEntity;
import com.webservice.graduate_coach.entity.id.StudentsMajorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentsMajorRepository extends JpaRepository<StudentsMajorEntity, StudentsMajorId> {
    List<StudentsMajorEntity> findByIdStudent(Integer student);
    StudentsMajorEntity findByIdStudentAndIdMajorOrder(Integer student, Integer order);
}