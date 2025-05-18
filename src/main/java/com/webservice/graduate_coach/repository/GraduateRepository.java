package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.GraduateEntity;
import com.webservice.graduate_coach.entity.id.GraduateId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GraduateRepository extends JpaRepository<GraduateEntity, GraduateId> {
}
