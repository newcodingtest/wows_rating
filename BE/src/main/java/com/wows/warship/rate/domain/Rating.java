package com.wows.warship.rate.domain;

import com.wows.warship.common.domain.ShipInfo;
import com.wows.warship.history.domain.BattlesHistory;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

@Getter
@Builder
public class Rating {
    private int ratingScore;
    private int battleCount;
    private int wins;

    public static Rating calculate(BattlesHistory battlesHistory, Map<Long, ShipInfo> expected){
       boolean isExistShip = expected.containsKey(battlesHistory.getShipNumber());
       if (!isExistShip){
           throw new NoSuchElementException("배를 찾을 수 없음");
       }

       ShipInfo shipInfo = expected.get(battlesHistory.getShipNumber());

        double capScore = (battlesHistory.getTeamCapPoint() != 0)
                ? (battlesHistory.getCapPoint() / (double) battlesHistory.getTeamCapPoint()) * 0.3
                : 0.0;

        double spotScore = (battlesHistory.getTeamSpotPoint() != 0)
                ? (battlesHistory.getTeamSpotPoint()  / (double) battlesHistory.getSpotPoint()) * 0.3
                : 0.0;

        double tankScore = (battlesHistory.getTankingPoint()/shipInfo.getHealth())*0.2;

        /**
         * 팀 기여도
         * */
        double teamContributionScore = capScore+spotScore+tankScore;

        double expectedKill = 0.5;
        if (expected.containsKey("average_frags")){
            expectedKill = shipInfo.getAverageKill();
        }

        double killScore = (battlesHistory.getBattles() != 0)
                ? (battlesHistory.getKill()/battlesHistory.getBattles())/expectedKill
                : battlesHistory.getBattles()/expectedKill;

        double expectedDMG = shipInfo.getAverageDmg();
        double dmgScore = battlesHistory.getDamage()/expectedDMG;

        double winScore = (battlesHistory.getBattles() != 0)
                ? ((double)battlesHistory.getWins()/(double)battlesHistory.getBattles())/shipInfo.getAverageWinRate()*0.01
                : 0;

        /**
         * 가중치 작업
         * */
        dmgScore = Math.max(((dmgScore-0.4)/(1-0.4)),0)*300;
        killScore = Math.max(((killScore-0.1)/(1-0.1)),0)*200;
        teamContributionScore=Math.max(((teamContributionScore-0.1)/(1-0.1)),0)*50;

        double rating = dmgScore+killScore+winScore+teamContributionScore;

        return Rating.builder()
                .ratingScore((int)rating)
                .wins((int)winScore)
                .build();
    }

    public static Rating calculate(List<BattlesHistory> battlesHistory, Map<Long, ShipInfo> expected, int untilDays){
        int totalRating = 0;
        int cnt = 0;
        int wins = 0;
        for (int i=0; i<battlesHistory.size(); i++){
            if (diffTimeStampDays(battlesHistory.get(i).getLastPlayTime())<untilDays){
                try {
                    Rating result = calculate(battlesHistory.get(i), expected);
                    totalRating+=result.getRatingScore();
                    wins+=result.getWins();
                    cnt++;
                }catch (NoSuchElementException e){
                }
            } else {
                break;
            }
        }

        if (cnt>0){
            totalRating/=cnt;
            wins/=cnt;
        }
        return Rating.builder()
                .ratingScore(totalRating)
                .battleCount(cnt)
                .wins(wins)
                .build();
    }

    private static long diffTimeStampDays(long passTime){
        long nowTime = Instant.now().getEpochSecond();
        long diffTime = nowTime-passTime;

        long diffDays = TimeUnit.SECONDS.toDays(diffTime);

        return diffDays;
    }
}
