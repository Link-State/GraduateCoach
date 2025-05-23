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
@Entity(name="Foreigncert")

public class ForeignCertEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer UID;

    private String name;
    private String descript;
    private Float score;
    private Integer department;
    private Integer year;
}
