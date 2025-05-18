package com.webservice.graduate_coach.entity;

import com.webservice.graduate_coach.entity.id.CommunicationCertId;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Entity(name="Communicationcert")

public class CommunicationCertEntity {
    @EmbeddedId
    private CommunicationCertId id;
    private Integer score;
}
