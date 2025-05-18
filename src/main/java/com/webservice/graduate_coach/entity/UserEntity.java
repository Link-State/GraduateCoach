package com.webservice.graduate_coach.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Entity(name="User")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer UID;

    @Column(name="id")
    private String userId;
    private String password;
    private String email;
    private Integer university;
}
