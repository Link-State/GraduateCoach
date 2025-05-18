package com.webservice.graduate_coach.entity;

import com.webservice.graduate_coach.entity.id.EssentialGeneralEducationId;
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
@Entity(name="Essentialgeneraleducation")

public class EssentialGeneralEducationEntity {
    @EmbeddedId
    private EssentialGeneralEducationId id;
}
