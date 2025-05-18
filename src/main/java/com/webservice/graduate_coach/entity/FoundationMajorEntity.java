package com.webservice.graduate_coach.entity;

import com.webservice.graduate_coach.entity.id.FoundationMajorId;
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
@Entity(name="Foundationmajor")

public class FoundationMajorEntity {
    @EmbeddedId
    private FoundationMajorId id;
}
