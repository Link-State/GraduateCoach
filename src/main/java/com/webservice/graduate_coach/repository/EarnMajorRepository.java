package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.EarnMajorEntity;
import com.webservice.graduate_coach.entity.id.EarnMajorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EarnMajorRepository extends JpaRepository<EarnMajorEntity, EarnMajorId> {
}
