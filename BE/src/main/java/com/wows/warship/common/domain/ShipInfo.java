package com.wows.warship.common.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipInfo {
    private long shipId;
    private String shipName;
    private float averageDmg;
    private float averageKill;
    private float averageWinRate;
    private int tier;
    private int health;
}
