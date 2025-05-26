package com.webservice.graduate_coach.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class CommunicationCertDTO {
    private Integer UID;
    private String name;
    private String descript;
    private Float score;
    private Integer department;
    private Integer year;
}
