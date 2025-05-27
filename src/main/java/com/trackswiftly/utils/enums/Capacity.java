package com.trackswiftly.utils.enums;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Capacity {
    

    private Double maxWeightKg;

    @Column(name = "max_volume_m3")
    private Double maxVolumeM3;
    private Integer maxBoxes;
    private Integer maxPallets;

}
