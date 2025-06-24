package com.trackswiftly.utils.enums;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * Represents the physical capacity constraints of a vehicle or container.
 *
 * <p>This embeddable entity can be used in JPA entities to define
 * weight, volume, and item count limits.
 */
@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Capacity {
    
    /**
     * The maximum allowed weight in kilograms.
     */
    private Double maxWeightKg;

    /**
     * The maximum allowed volume in cubic meters.
     */
    @Column(name = "max_volume_m3")
    private Double maxVolumeM3;

    /**
     * The maximum number of boxes that can be loaded.
     */
    private Integer maxBoxes;

    /**
     * The maximum number of pallets that can be loaded.
     */
    private Integer maxPallets;

}
