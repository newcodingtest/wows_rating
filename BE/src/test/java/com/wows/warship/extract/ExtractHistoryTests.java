package com.wows.warship.extract;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ExtractHistoryTests {
    static long sum = 0;
    static int count = 0;
    static Map<String, Map<String,String>>expectedData;

    @BeforeEach
    public void setUp() throws IOException {
        InputStream is = new FileInputStream("src/sample/expected/test.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jnode = objectMapper.readTree(is);
        Map<String,Map<String,String>> expected = objectMapper.readValue(jnode.get("data").toString(),
                new TypeReference<Map<String,Map<String,String>>>() {
                });

        expectedData = expected;
    }
    @DisplayName("유저 레이팅 계산식")
    @Test
    public void 레이팅_계산하기() throws IOException {
        //InputStream is1 = new FileInputStream("src/sample/history/pvp_solo/0305/1114.json");
        InputStream is1 = new FileInputStream("src/sample/history/pvp_solo_div2_div3/0328/test.json");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jnode = objectMapper.readTree(is1);

        List<ShipDataDto> dtos = objectMapper.readValue(jnode.get("data").get("2020639284").toString(),
                new TypeReference<List<ShipDataDto>>() {
                });

        Collections.sort(dtos);
        dtos.remove(0);

        dtos.forEach(x -> {
            calculate(x);
        });

        System.out.println(sum/count);
    }

    private void calculate(ShipDataDto shipDataDto){
        whatKindBattles(shipDataDto);
        diffTimeStamp(shipDataDto.getUpdated_at());
        System.out.println("배 번호: "+ shipDataDto.getShip_id());

        double totalScore = 0;
        double teamContributionScore = 0;
        double spotScore = 0;
        double capScore = 0;
        double tankScore = 0;

        double killScore = 0;
        double dmgScore = 0;
        double winScore = 0;
        capScore = (shipDataDto.getPvp().getTeamCapturePoints() != 0)
                ? (shipDataDto.getPvp().getCapturePoints() / (double) shipDataDto.getPvp().getTeamCapturePoints()) * 0.3
                : 0.0;


        spotScore = (shipDataDto.getPvp().getMaxDamageScouting() != 0)
                ? (shipDataDto.getPvp().getMaxDamageScouting() / (double) shipDataDto.getPvp().getDamageScouting()) * 0.3
                : 0.0;

        /**
         * TODO: DB로 부터 SELECT
         * */
        int shipFullHpFromDB = 40000;
        tankScore = (shipDataDto.getPvp().getMaxTotalAgro()/shipFullHpFromDB)*0.2;


        teamContributionScore = capScore+spotScore+tankScore;

        if (!expectedData.containsKey(String.valueOf(shipDataDto.getShip_id()))){
            System.out.println("계산불가");
            return;
        }

        Map<String,String> data = expectedData.get(String.valueOf(shipDataDto.getShip_id()));

        /**
         * TODO: DB로 부터 SELECT
         * */
        double expectedKill = 0.5;
        if (data.containsKey("average_frags")){
            expectedKill = Double.parseDouble(data.get("average_frags"));
        }

        killScore = (shipDataDto.getPvp().getBattles() != 0)
                ? (shipDataDto.getPvp().getFrags()/shipDataDto.getPvp().getBattles())/expectedKill
                : shipDataDto.getPvp().getBattles()/expectedKill;

        /**
         * TODO: DB로 부터 SELECT
         * */
        double expectedDMG = Double.parseDouble(data.get("average_damage_dealt"));
        dmgScore = shipDataDto.getPvp().maxDamageDealt/expectedDMG;

        winScore = (shipDataDto.getPvp().getBattles() != 0)
                ? ((double)shipDataDto.getPvp().getWins()/(double)shipDataDto.getPvp().getBattles())/Double.parseDouble(data.get("win_rate"))*0.01
                : 0;

        dmgScore = Math.max(((dmgScore-0.4)/(1-0.4)),0)*300;
        killScore = Math.max(((killScore-0.1)/(1-0.1)),0)*200;
        teamContributionScore=Math.max(((teamContributionScore-0.1)/(1-0.1)),0)*50;

        totalScore = dmgScore+killScore+winScore+teamContributionScore;

        sum+=totalScore;
        count++;
        System.out.println("레이팅: "+ totalScore);
        //System.out.println(count);
    }

    public void whatKindBattles(ShipDataDto shipDataDto){
        System.out.println();
        int maxXp = shipDataDto.getPvp().getMax_xp();
        System.out.println("점수: " + maxXp);
        int rankXp = 0;
        int soloXp = 0;
        int div2Xp = 0;
        int div3Xp = 0;
        if (shipDataDto.getRank_solo()!=null){
            rankXp = shipDataDto.getRank_solo().getMax_xp();
        }
        if (shipDataDto.getPvp_solo()!=null){
            soloXp = shipDataDto.getPvp_solo().getMax_xp();
        }
        if (shipDataDto.getPvp_div2()!=null){
            div2Xp = shipDataDto.getPvp_div2().getMax_xp();
        }
        if (shipDataDto.getPvp_div3()!=null) {
            div3Xp = shipDataDto.getPvp_div3().getMax_xp();
        }
        
        if (maxXp==rankXp){
            System.out.println("랭크 게임");
        }
        if (maxXp==div3Xp){
            System.out.println("3인전대 게임");
        }
        if (maxXp==div2Xp){
            System.out.println("2인전대 게임");
        }
        if (maxXp==soloXp){
            System.out.println("솔로 게임");
        }
    }

    @DisplayName("현재_TIMESTAMP_구하기")
    @Test
    public void 현재_TIMESTAMP_구하기(){

        long nowTime = Instant.now().getEpochSecond();
        long kstEpochSecond = ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toEpochSecond();
        long utcEpochSecond = kstEpochSecond - (9*3600);

        System.out.println("utc: "+nowTime);
        System.out.println("kst: "+utcEpochSecond);
        long passTime = 1740320520;
        long diffTime = utcEpochSecond-passTime;

        long days = TimeUnit.SECONDS.toDays(diffTime);

        System.out.println(days+"일전 데이터");
    }

    public void diffTimeStamp(long passTime){
        long nowTime = Instant.now().getEpochSecond();
        long diffTime = nowTime-passTime;

        //long diffDays = TimeUnit.SECONDS.toDays(diffTime);
        long diffHours = TimeUnit.SECONDS.toHours(diffTime);
        //System.out.println(diffDays+"일전 데이터");
        System.out.println(diffHours+" 시간전 데이터");
    }
}
