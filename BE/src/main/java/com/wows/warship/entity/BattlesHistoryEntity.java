package com.wows.warship.entity;

import com.wows.warship.dto.BattleHistoryDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class BattlesHistoryEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long shipName;

    private int tier;

    private int ratingScore;

    private String accountId;

    private Long lastBattleTime;


    private int capturePoints;
    private int wins;
    private int losses;
    private int battles;
    private int maxDamageScouting;
    private int teamCapturePoints;
    private int frags;
    private int damageScouting;
    private int maxDamageDealt;
    private int maxTotalAgro;



    public static BattlesHistoryEntity from(BattleHistoryDto battleHistoryDto, String accountId){
        return BattlesHistoryEntity.builder()
                .shipName(battleHistoryDto.getShip_id())
                .accountId(accountId)
                .lastBattleTime(battleHistoryDto.getLast_battle_time())
                .capturePoints(battleHistoryDto.getPvp().getCapturePoints())
                .wins(battleHistoryDto.getPvp().getWins())
                .losses(battleHistoryDto.getPvp().getLosses())
                .battles(battleHistoryDto.getPvp().getBattles())
                .maxDamageScouting(battleHistoryDto.getPvp().getMaxDamageScouting())
                .teamCapturePoints(battleHistoryDto.getPvp().getTeamCapturePoints())
                .frags(battleHistoryDto.getPvp().getFrags())
                .damageScouting(battleHistoryDto.getPvp().getDamageScouting())
                .maxDamageDealt(battleHistoryDto.getPvp().getMaxDamageDealt())
                .maxTotalAgro(battleHistoryDto.getPvp().getMaxTotalAgro())
                .build();
    }
}
