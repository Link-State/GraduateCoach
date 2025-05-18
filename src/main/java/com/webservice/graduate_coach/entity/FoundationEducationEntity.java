package com.webservice.graduate_coach.entity;

import com.webservice.graduate_coach.entity.id.FoundationEducationId;
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
@Entity(name="Foundationeducation")

public class FoundationEducationEntity {
    @EmbeddedId
    private FoundationEducationId id;
}
