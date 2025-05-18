package com.webservice.graduate_coach.entity;

import com.webservice.graduate_coach.entity.id.GraduateId;
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
@Entity(name="Graduate")

public class GraduateEntity {
    @EmbeddedId
    private GraduateId id;
    private Integer totalCredit;
    private Integer totalLevel;
    private Boolean secondMajor;
    private Integer foundationEdu;
    private Integer generalEdu;
    private Integer foundationMajor;
    private Integer optionalEdu;
    private Integer leastCut;
}
