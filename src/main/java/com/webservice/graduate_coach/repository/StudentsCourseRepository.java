package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.StudentsCourseEntity;
import com.webservice.graduate_coach.entity.id.StudentsCourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentsCourseRepository extends JpaRepository<StudentsCourseEntity, StudentsCourseId> {
    List<StudentsCourseEntity> findByIdStudent(Integer student);
}
