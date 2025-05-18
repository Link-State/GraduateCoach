package com.webservice.graduate_coach.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class CourseTypeDTO {
    private Integer major;
    private Integer year;
    private Integer type;
    private Integer course;
}
