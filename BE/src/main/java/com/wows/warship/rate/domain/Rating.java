package com.wows.warship.rate.domain;

import com.wows.warship.history.domain.BattlesHistory;
import lombok.Builder;
import lombok.Getter;
import org.bouncycastle.asn1.x500.style.RFC4519Style;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Getter
@Builder
public class Rating {
    private int score;

    public static long calculate(BattlesHistory battlesHistory, Map<String, String> expected){
       boolean isExistShip = expected.containsKey(expected.get(battlesHistory.getShipNumber()));
       if (!isExistShip){
           throw new NoSuchElementException("배를 찾을 수 없음");
       }

        double capScore = (battlesHistory.getTeamCapPoint() != 0)
                ? (battlesHistory.getCapPoint() / (double) battlesHistory.getTeamCapPoint()) * 0.3
                : 0.0;

        double spotScore = (battlesHistory.getTeamSpotPoint() != 0)
                ? (battlesHistory.getTeamSpotPoint()  / (double) battlesHistory.getSpotPoint()) * 0.3
                : 0.0;

        double tankScore = (battlesHistory.getTankingPoint()/Double.parseDouble(expected.get("fullHp")))*0.2;

        /**
         * 팀 기여도
         * */
        double teamContributionScore = capScore+spotScore+tankScore;

        double expectedKill = 0.5;
        if (expected.containsKey("average_frags")){
            expectedKill = Double.parseDouble(expected.get("average_frags"));
        }

        double killScore = (battlesHistory.getBattles() != 0)
                ? (battlesHistory.getKill()/battlesHistory.getBattles())/expectedKill
                : battlesHistory.getBattles()/expectedKill;

        double expectedDMG = Double.parseDouble(expected.get("average_damage_dealt"));
        double dmgScore = battlesHistory.getDamage()/expectedDMG;

        double winScore = (battlesHistory.getBattles() != 0)
                ? ((double)battlesHistory.getWins()/(double)battlesHistory.getBattles())/Double.parseDouble(expected.get("win_rate"))*0.01
                : 0;

        /**
         * 가중치 작업
         * */
        dmgScore = Math.max(((dmgScore-0.4)/(1-0.4)),0)*300;
        killScore = Math.max(((killScore-0.1)/(1-0.1)),0)*200;
        teamContributionScore=Math.max(((teamContributionScore-0.1)/(1-0.1)),0)*50;

        double rating = dmgScore+killScore+winScore+teamContributionScore;
        return (long)rating;
    }

    public static long calculate(List<BattlesHistory> battlesHistory, Map<String, String> expected, int untilDays){
        int totalRating = 0;
        int cnt = 0;
        for (int i=0; i<battlesHistory.size(); i++){
            if (diffTimeStampDays(battlesHistory.get(i).getLastPlayTime())<untilDays){
                totalRating += calculate(battlesHistory.get(i), expected);
                cnt++;
            } else {
                break;
            }
        }

        return totalRating/cnt;
    }

    private static long diffTimeStampDays(long passTime){
        long nowTime = Instant.now().getEpochSecond();
        long diffTime = nowTime-passTime;

        long diffDays = TimeUnit.SECONDS.toDays(diffTime);

        return diffDays;
    }
}
