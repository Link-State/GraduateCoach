package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.ForeignCertEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ForeignCertRepository extends JpaRepository<ForeignCertEntity, Integer> {
    List<ForeignCertEntity> findByDepartmentAndYear(Integer department, Integer year);
    @Transactional
    void deleteAllByUIDIn(List<Integer> ids);
}
