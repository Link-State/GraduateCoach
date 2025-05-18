package com.webservice.graduate_coach.repository;

import com.webservice.graduate_coach.entity.CourseTypeEntity;
import com.webservice.graduate_coach.entity.id.CourseTypeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseTypeRepository extends JpaRepository<CourseTypeEntity, CourseTypeId> {
    List<CourseTypeEntity> findByIdMajorAndIdYear(Integer major, Integer year);
    List<CourseTypeEntity> findByIdMajorAndIdYearAndType(Integer major, Integer year, Integer type);
}
