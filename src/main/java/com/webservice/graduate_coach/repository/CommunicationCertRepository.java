package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.CommunicationCertEntity;
import com.webservice.graduate_coach.entity.id.CommunicationCertId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunicationCertRepository extends JpaRepository<CommunicationCertEntity, CommunicationCertId> {
    List<CommunicationCertEntity> findByIdDepartmentAndIdYear(Integer department, Integer year);
}
