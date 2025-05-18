package com.webservice.graduate_coach.entity;

import com.webservice.graduate_coach.entity.id.StudentsMajorId;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Entity(name="Studentsmajor")

public class StudentsMajorEntity {
    @EmbeddedId
    private StudentsMajorId id;
    private Integer major;
}
