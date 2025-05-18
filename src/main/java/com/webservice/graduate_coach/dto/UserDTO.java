package com.webservice.graduate_coach.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString

public class UserDTO {
    private Integer UID;
    private String userId;
    private String password;
    private String email;
    private Integer university;
}
