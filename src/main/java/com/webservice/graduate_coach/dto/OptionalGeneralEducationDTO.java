package com.webservice.graduate_coach.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class OptionalGeneralEducationDTO {
    private Integer department;
    private Integer year;
    private Integer number;
}
