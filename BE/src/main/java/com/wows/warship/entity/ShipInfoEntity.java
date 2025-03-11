package com.wows.warship.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ShipInfoEntity {

    @Id
    private long shipId;
    @Column
    private String shipName;
    @Column
    private float averageDmg;
    @Column
    private float averageKill;
    @Column
    private float averageWinRate;
}
