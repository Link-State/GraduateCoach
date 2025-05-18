package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.EssentialGeneralEducationEntity;
import com.webservice.graduate_coach.entity.id.EssentialGeneralEducationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EssentialGeneralEducationRepository extends JpaRepository<EssentialGeneralEducationEntity, EssentialGeneralEducationId> {
    List<EssentialGeneralEducationEntity> findByIdDepartmentAndIdYear(Integer department, Integer year);
}
