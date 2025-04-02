package com.wows.warship.history.entity;

import com.wows.warship.common.domain.ShipInfo;
import com.wows.warship.common.entity.ShipInfoEntity;
import com.wows.warship.history.domain.BattlesHistory;
import com.wows.warship.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BattlesHistoryEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ship_id", referencedColumnName = "shipId")
    private ShipInfoEntity shipInfo;

    private String accountId;

    private Long lastBattleTime;

    private int wins;
    private int losses;
    private int battles;
    private int spotPoint;
    public int teamCapPoint;
    private int capPoint;
    private int kill;
    /**
     * 팀 전체 데미지
     * */
    public int teamTotalDamage;
    private int damage;
    private int tankingPoint;

    private String battleType;

    private int maxXp;

    public static BattlesHistoryEntity from(BattlesHistory battlesHistory, ShipInfo shipInfo, String accountId){
        return BattlesHistoryEntity.builder()
                .accountId(accountId)
                .shipInfo(ShipInfoEntity.from(shipInfo))
                .lastBattleTime(battlesHistory.getLastPlayTime())
                .wins(battlesHistory.getWins())
                .losses(battlesHistory.getLosses())
                .battles(battlesHistory.getBattles())
                .damage(battlesHistory.getDamage())
                .capPoint(battlesHistory.getCapPoint())
                .teamCapPoint(battlesHistory.getTeamCapPoint())
                .kill(battlesHistory.getKill())
                .damage(battlesHistory.getDamage())
                .tankingPoint(battlesHistory.getTankingPoint())
                .battleType(battlesHistory.getBattleType())
                .maxXp(battlesHistory.getMaxXp())
                .build();
    }

    public BattlesHistory toModel(){
        return BattlesHistory.builder()
                .shipNumber(shipInfo.toModel().getShipId())
                .lastPlayTime(lastBattleTime)
                .wins(wins)
                .losses(losses)
                .battles(battles)
                .damage(damage)
                .capPoint(capPoint)
                .teamCapPoint(teamCapPoint)
                .kill(kill)
                .damage(damage)
                .tankingPoint(tankingPoint)
                .maxXp(maxXp)
                .build();
    }
}
