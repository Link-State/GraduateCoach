package com.webservice.graduate_coach.entity;

import com.webservice.graduate_coach.entity.id.EarnMajorId;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
@Entity(name="Earnmajor")

public class EarnMajorEntity {
    @EmbeddedId
    private EarnMajorId id;
    private Float totalCredit;
}
