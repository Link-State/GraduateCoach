package com.webservice.graduate_coach.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class MajorDTO {
    private Integer UID;
    private String name;
    private Integer department;
}
