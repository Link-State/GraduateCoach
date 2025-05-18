package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.OptionalGeneralEducationEntity;
import com.webservice.graduate_coach.entity.id.OptionalGeneralEducationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OptionalGeneralEducationRepository extends JpaRepository<OptionalGeneralEducationEntity, OptionalGeneralEducationId> {
    List<OptionalGeneralEducationEntity> findByIdDepartmentAndIdYear(Integer department, Integer year);
}
