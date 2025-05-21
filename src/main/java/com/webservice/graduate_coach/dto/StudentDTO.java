package com.webservice.graduate_coach.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class StudentDTO {
    private Integer UID;
    private String studentNumber;
    private Integer year;
    private Integer user;
    private Integer foreignCert;
    private Integer communicationCert;
}
