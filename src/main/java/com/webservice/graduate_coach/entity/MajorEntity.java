package com.webservice.graduate_coach.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Entity(name="Major")

public class MajorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer UID;
    private String name;
    private Integer department;
}
