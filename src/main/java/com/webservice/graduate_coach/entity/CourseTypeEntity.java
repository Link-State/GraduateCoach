package com.webservice.graduate_coach.entity;

import com.webservice.graduate_coach.entity.id.CourseTypeId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Entity(name="Coursetype")

public class CourseTypeEntity {
    @EmbeddedId
    private CourseTypeId id;
    private Integer type;
}
