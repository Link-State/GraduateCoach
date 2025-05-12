package com.webservice.graduate_coach.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class StudentDTO {
    private String name;
    private Integer major;
    private String id;
    private Float total_credit;
    private Float require_credit;
    private Integer foreign;
    private Integer communication;
}
