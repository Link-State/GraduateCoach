package com.webservice.graduate_coach.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable

public class FoundationEducationId implements Serializable {
    @Column(name="department")
    private Integer department;
    @Column(name="year")
    private Integer year;
    @Column(name="course")
    private Integer course;
}
