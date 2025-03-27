package com.wows.warship.history.domain;

import com.wows.warship.history.dto.BattleHistoryApiResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;


@ToString
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
    
    
    /**
     * 배틀 종류(솔로, 2인전대, 3인전대, 랭크)
     * 
     * */
    private String battleType;
    
    
    /**
     * 경험치
     * */
    private int maxXp;


    public static BattlesHistory from(BattleHistoryApiResponse battleHistoryApiResponse) {
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
                .battleType(whatKindBattlesType(battleHistoryApiResponse))
                .maxXp(battleHistoryApiResponse.getPvp().getMax_xp())
                .build();
    }

    private static String whatKindBattlesType(BattleHistoryApiResponse shipData){
        int maxXp = shipData.getPvp().getMax_xp();
        int rankXp = 0;
        int soloXp = 0;
        int div2Xp = 0;
        int div3Xp = 0;
        if (shipData.getRank_solo()!=null){
            rankXp = shipData.getRank_solo().getMax_xp();
        }
        if (shipData.getPvp_solo()!=null){
            soloXp = shipData.getPvp_solo().getMax_xp();
        }
        if (shipData.getPvp_div2()!=null){
            div2Xp = shipData.getPvp_div2().getMax_xp();
        }
        if (shipData.getPvp_div3()!=null) {
            div3Xp = shipData.getPvp_div3().getMax_xp();
        }

        if (maxXp==rankXp){
            return "rank";
        }
        if (maxXp==div3Xp){
            return "div3";
        }
        if (maxXp==div2Xp){
            return "div2";
        }
        if (maxXp==soloXp){
            return "solo";
        }
        return "none";
    }


}
