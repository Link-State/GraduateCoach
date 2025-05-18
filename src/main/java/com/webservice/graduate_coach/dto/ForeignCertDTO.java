package com.webservice.graduate_coach.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class ForeignCertDTO {
    private Integer department;
    private Integer year;
    private String name;
    private Integer score;
}
