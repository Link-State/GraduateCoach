package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.FoundationMajorEntity;
import com.webservice.graduate_coach.entity.id.FoundationMajorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoundationMajorRepository extends JpaRepository<FoundationMajorEntity, FoundationMajorId> {
    List<FoundationMajorEntity> findByIdDepartmentAndIdYear(Integer department, Integer year);
}
