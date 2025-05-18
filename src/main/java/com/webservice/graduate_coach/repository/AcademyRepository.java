package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.AcademyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcademyRepository extends JpaRepository<AcademyEntity, Integer> {
    AcademyEntity findByUser(Integer user);
}
