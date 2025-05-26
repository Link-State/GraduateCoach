package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.DepartmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<DepartmentEntity, Integer> {
    List<DepartmentEntity> findByUniversity(Integer university_uid);
    DepartmentEntity findByUIDAndUniversity(Integer id, Integer university_uid);
}
