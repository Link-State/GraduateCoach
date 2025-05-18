package com.webservice.graduate_coach.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable

public class CourseTypeId {
    @Column(name="major")
    private Integer major;
    @Column(name="year")
    private Integer year;
    @Column(name="course")
    private Integer course;
}
