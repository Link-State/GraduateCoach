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
@Entity(name="Student")

public class StudentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer UID;

    @Column(name="id")
    private String studentNumber;
    private Integer year;
    private Integer user;
    @Column(name="foreign_cert")
    private Integer foreignCert;
    @Column(name="comm_cert")
    private Integer communicationCert;
}
