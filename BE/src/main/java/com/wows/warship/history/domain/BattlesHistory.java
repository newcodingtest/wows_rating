package com.wows.warship.history.domain;

import com.wows.warship.history.dto.BattleHistoryApiResponse;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class BattlesHistory {
    /**
     * 승리 수
     * */
    public int wins;
    /**
     * 패배 수
     * */
    public int losses;
    /**
     * 판수
     * */
    public int battles;
    /**
     * 배 번호
     * */
    public long shipNumber;

    /**
     * 나의 스팟 점수
     * */
    public int spotPoint;

    /**
     * 나의 점령 점수
     * */
    public int capPoint;
    /**
     * 팀 점령 점수
     * */
    public int teamCapPoint;

    /**
     * 팀 스팟 점수
     * */
    public int teamSpotPoint;

    /**
     * 나의 데미지
     * */
    private int damage;
    /**
     * 나의 킬
     * */
    private int kill;

    /**
     * 나의 탱킹 점수
     * */
    private int tankingPoint;
    
    /**
     * 마지막 플레이 시간
     * 
     * */

    private long lastPlayTime;


    public static BattlesHistory create(BattleHistoryApiResponse battleHistoryApiResponse){
        return BattlesHistory.builder()
                .wins(battleHistoryApiResponse.getPvp().getWins())
                .losses(battleHistoryApiResponse.getPvp().getLosses())
                .battles(battleHistoryApiResponse.getPvp().getBattles())
                .shipNumber(battleHistoryApiResponse.getShip_id())
                .spotPoint(battleHistoryApiResponse.getPvp().getDamageScouting())
                .capPoint(battleHistoryApiResponse.getPvp().getCapturePoints())
                .teamCapPoint(battleHistoryApiResponse.getPvp().getTeamCapturePoints())
                .teamSpotPoint(battleHistoryApiResponse.getPvp().getDamageScouting())
                .damage(battleHistoryApiResponse.getPvp().getMaxDamageDealt())
                .kill(battleHistoryApiResponse.getPvp().getFrags())
                .tankingPoint(battleHistoryApiResponse.getPvp().getMaxTotalAgro())
                .lastPlayTime(battleHistoryApiResponse.getLast_battle_time())
                .build();
    }


}
