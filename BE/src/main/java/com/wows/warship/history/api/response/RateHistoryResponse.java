package com.wows.warship.history.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wows.warship.common.domain.ShipInfo;
import com.wows.warship.history.domain.BattlesHistory;
import com.wows.warship.rate.domain.Rating;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class RateHistoryResponse {

    /**
     * 배 이름
     * */
    public String shipName;

    /**
     * 승률
     * */
    public double winsRate;

    /**
     * 나의 데미지
     * */
    private double damage;
    /**
     * 킬 퍼센트
     * */
    private double killRate;

    /**
     * 나의 탱킹 점수
     * */
    private double tanking;

    private int rating;

    /**
     * 마지막 플레이 시간
     *
     * */
    private long lastPlayTime;

    private String battleType;


    public static RateHistoryResponse from(BattlesHistory battlesHistory, ShipInfo shipInfo, Rating rating){
        return RateHistoryResponse.builder()
                .rating(rating.getRatingScore())
                .shipName(shipInfo.getShipName())
                .winsRate(rating.getWinRate())
                .damage(rating.getDamage())
                .killRate(rating.getKillRate())
                .tanking(rating.getTanking())
                .lastPlayTime(battlesHistory.getLastPlayTime())
                .battleType(battlesHistory.getBattleType())
                .build();
    }
}
