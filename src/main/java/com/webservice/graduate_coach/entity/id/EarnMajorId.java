package com.webservice.graduate_coach.entity.id;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Embeddable

public class EarnMajorId implements Serializable {
    @Column(name="")
    private Integer major;
    @Column(name="")
    private Integer year;
}
