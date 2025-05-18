package com.webservice.graduate_coach.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class GraduateDTO {
    private Integer department;
    private Integer year;
    private Integer totalCredit;
    private Integer totalLevel;
    private Boolean secondMajor;
    private Integer foundationEdu;
    private Integer generalEdu;
    private Integer foundationMajor;
    private Integer optionalEdu;
    private Integer leastCut;
}
