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
    private double winRate;
    private double killRate;
    private double damage;
    private double tanking;
    private double spot;


    public static Rating calculate(BattlesHistory battlesHistory, Map<Long, ShipInfo> expected){
       boolean isExistShip = expected.containsKey(battlesHistory.getShipNumber());
       if (!isExistShip){
           throw new NoSuchElementException("배를 찾을 수 없음");
       }

       ShipInfo shipInfo = expected.get(battlesHistory.getShipNumber());

        return calculate(battlesHistory, shipInfo);
    }

    public static Rating calculate(BattlesHistory battlesHistory, ShipInfo shipInfo){

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
        if (shipInfo.getAverageKill()!=0){
            expectedKill = shipInfo.getAverageKill();
        }

        double killScore = (battlesHistory.getBattles() != 0)
                ? (battlesHistory.getKill()/battlesHistory.getBattles())/expectedKill
                : 0;

        double expectedDMG = shipInfo.getAverageDmg();
        double dmgScore = battlesHistory.getDamage()/expectedDMG;

        double winning = (battlesHistory.getBattles() != 0)
                ? ((double)battlesHistory.getWins()/(double)battlesHistory.getBattles())
                : 0;
        double calWinning = (battlesHistory.getBattles() != 0)
                ? ((double)battlesHistory.getWins()/(double)battlesHistory.getBattles())/shipInfo.getAverageWinRate()
                : 0;

        double winScore = calWinning*0.01;

        /**
         * 가중치 작업
         * */
        dmgScore = Math.max(((dmgScore-0.4)/(1-0.4)),0)*300;
        killScore = Math.max(((killScore-0.1)/(1-0.1)),0)*200;
        teamContributionScore=Math.max(((teamContributionScore-0.1)/(1-0.1)),0)*50;

        double rating = dmgScore+killScore+winScore+teamContributionScore;

        return Rating.builder()
                .ratingScore((int)rating)
                .winRate(winning)
                .killRate(killScore)
                .build();
    }

    public static Rating calculatesAverage(List<BattlesHistory> battlesHistory, Map<Long, ShipInfo> expected, int untilDays){
        int totalRating = 0;
        int cnt = 0;
        double wins = 0;
        double killRate = 0;
        for (int i=0; i<battlesHistory.size(); i++){
            if (diffTimeStampDays(battlesHistory.get(i).getLastPlayTime())<untilDays){
                try {
                    Rating result = calculate(battlesHistory.get(i), expected);
                    totalRating+=result.getRatingScore();
                    wins+=result.getWinRate();
                    killRate+=result.getKillRate();
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
            killRate/=cnt;
        }
        return Rating.builder()
                .ratingScore(totalRating)
                .battleCount(cnt)
                .winRate(wins*100)
                .killRate(killRate)
                .build();
    }

    private static long diffTimeStampDays(long passTime){
        long nowTime = Instant.now().getEpochSecond();
        long diffTime = nowTime-passTime;

        long diffDays = TimeUnit.SECONDS.toDays(diffTime);

        return diffDays;
    }
}
