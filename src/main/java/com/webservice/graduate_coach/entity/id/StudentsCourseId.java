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
public class StudentsCourseId implements Serializable {
    @Column(name="student")
    private Integer student;
    @Column(name="course")
    private Integer course;
}
