package com.webservice.graduate_coach.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class StudentsCourseDTO {
    private Integer student;
    private Integer course;
    private Float grade;
}
