package com.wows.warship.common.entity;

import com.wows.warship.common.domain.ShipInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
    @Column
    private int tier;
    @Column
    private int health;

    public ShipInfo toModel(){
        return ShipInfo.builder()
                .shipId(shipId)
                .shipName(shipName)
                .averageDmg(averageDmg)
                .averageKill(averageKill)
                .averageWinRate(averageWinRate)
                .tier(tier)
                .health(health)
                .build();
    }

    public static ShipInfoEntity from(ShipInfo shipInfo){
        return ShipInfoEntity.builder()
                .shipId(shipInfo.getShipId())
                .shipName(shipInfo.getShipName())
                .averageDmg(shipInfo.getAverageDmg())
                .averageKill(shipInfo.getAverageKill())
                .averageWinRate(shipInfo.getAverageWinRate())
                .tier(shipInfo.getTier())
                .health(shipInfo.getHealth())
                .build();
    }

}
