package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.UniversityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UniversityRepository extends JpaRepository<UniversityEntity, Integer> {
}
