package com.webservice.graduate_coach.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class StudentsMajorDTO {
    private Integer student;
    private Integer majorOrder;
    private Integer major;
}
