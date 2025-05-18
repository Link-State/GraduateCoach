package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.FoundationEducationEntity;
import com.webservice.graduate_coach.entity.id.FoundationEducationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoundationEducationRepository extends JpaRepository<FoundationEducationEntity, FoundationEducationId> {
    List<FoundationEducationEntity> findByIdDepartmentAndIdYear(Integer department, Integer year);
}
