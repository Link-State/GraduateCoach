package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.MajorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepository extends JpaRepository<MajorEntity, Integer> {
}
