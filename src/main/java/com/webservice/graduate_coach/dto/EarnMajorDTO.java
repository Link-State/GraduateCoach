package com.webservice.graduate_coach.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class EarnMajorDTO {
    private Integer major;
    private Integer year;
    private Float totalCredit;
}
