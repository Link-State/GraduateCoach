package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.ForeignCertEntity;
import com.webservice.graduate_coach.entity.id.ForeignCertId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ForeignCertRepository extends JpaRepository<ForeignCertEntity, ForeignCertId> {
    List<ForeignCertEntity> findByIdDepartmentAndIdYear(Integer department, Integer year);
}
