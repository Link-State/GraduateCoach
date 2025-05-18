package com.webservice.graduate_coach.entity;

import com.webservice.graduate_coach.entity.id.ForeignCertId;
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
@Entity(name="Foreigncert")

public class ForeignCertEntity {
    @EmbeddedId
    private ForeignCertId id;
    private Integer score;
}
