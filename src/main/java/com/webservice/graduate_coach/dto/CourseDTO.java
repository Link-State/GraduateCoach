package com.webservice.graduate_coach.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class CourseDTO {
    private Integer UID;
    private String name;
    private String code;
    private Integer level;
    private Float credit;
    private Integer number;
}
