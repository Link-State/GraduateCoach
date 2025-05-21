package com.webservice.graduate_coach.dto;
import com.webservice.graduate_coach.param.UserType;

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
    @Getter
    private UserType type;
    @Getter
    private String major;
    @Getter
    private String Name;

}
