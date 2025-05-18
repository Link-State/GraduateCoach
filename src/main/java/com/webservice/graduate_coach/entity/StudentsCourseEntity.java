package com.webservice.graduate_coach.entity;

import com.webservice.graduate_coach.entity.id.StudentsCourseId;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Entity(name="Studentscourse")

public class StudentsCourseEntity {
    @EmbeddedId
    private StudentsCourseId id;
    private Float grade;
}
