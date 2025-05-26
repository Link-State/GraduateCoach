package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.MajorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MajorRepository extends JpaRepository<MajorEntity, Integer> {
    List<MajorEntity> findByDepartment(Integer department_uid);
    MajorEntity findByUIDAndDepartment(Integer uid, Integer department_uid);
}
