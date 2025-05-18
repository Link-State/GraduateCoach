package com.webservice.graduate_coach.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class DepartmentDTO {
    private Integer UID;
    private String name;
    private Integer university;
}
