package com.wows.warship.common.feign;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wows.warship.extract.ShipDataDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ConditionalOnProperty(name = "spring.redis.enabled", havingValue = "true")
//@ImportAutoConfiguration(exclude = {
//        DataSourceAutoConfiguration.class,
//        HibernateJpaAutoConfiguration.class
//})
@ActiveProfiles("test")
@SpringBootTest
public class WowsApiClientRealTests {

    @Autowired
    private WowsApiClient wowsApiClient;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("실제 API Test: 닉네임이 존재하는지 확인 할 수 있다.")
    @Test
    void getAccountList_should_be_return_userAccount() {
        //given
        String startWithNickname = "nocap";

        //when
        Map<String, Object>  response = wowsApiClient.getAccountList("4a5f774ca91614ec9e42bdb76474af15",
                startWithNickname);

        ArrayList<Map<String, String>>actual = (ArrayList)response.get("data");
        //then
        assertEquals("noCap_noSpot_noHelp_Fxxk", actual.get(4).get("nickname"));
    }

    @DisplayName("실제 API Test: 배 이름을 확인 할 수 있다.")
    @Test
    void getShipInfo_should_be_return_shipInfo() {
        //given
        String shipId = "4179506480";

        //when
        Map<String, Object>  response = wowsApiClient.getShipInfo("4a5f774ca91614ec9e42bdb76474af15",
                "en",
                shipId);

        Map<String, Map<String, String>> actual = (Map<String, Map<String, String>>)response.get("data");

        //then
        assertEquals("Halland", actual.get(shipId).get("name"));
    }


    @DisplayName("[배 ID:배 이름] 매핑 테스트")
    @Test
    public void collectShipList() throws IOException {
        InputStream is = new FileInputStream("src/sample/expected/test.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jnode = objectMapper.readTree(is);

        Map<String, Map<String,String>> mockResponse = objectMapper.readValue(jnode.get("data").toString(),
                new TypeReference< Map<String, Map<String,String>> >() {
                });
        System.out.println("INSERT INTO SHIP_INFO_ENTITY (SHIP_ID, TIER, SHIP_NAME, HEALTH, AVERAGE_DMG, AVERAGE_KILL, AVERAGE_WIN_RATE) VALUES");
        for (Map.Entry entry : mockResponse.entrySet()){
            String shipId = entry.getKey().toString();
            Map<String, Object>  response = wowsApiClient.getShipInfo("4a5f774ca91614ec9e42bdb76474af15",
                    "en",
                    shipId);

            String averageDmg = mockResponse.get(shipId).get("average_damage_dealt");
            String averageKill = mockResponse.get(shipId).get("average_frags");
            String averageWin = mockResponse.get(shipId).get("win_rate");

            Map<String, Map<String, Object>> actual = (Map<String, Map<String, Object>>)response.get("data");
            String shipName = "";
            String tier = "";
            int health = 0;
            String json = objectMapper.writeValueAsString(actual.get(shipId));
            ShipDetailedDto shipDetailedDto = objectMapper.readValue(json, ShipDetailedDto.class);

            try {
                shipName = shipDetailedDto.getName();
                tier = shipDetailedDto.getTier();
                health = shipDetailedDto.getDefaultProfile().getArmour().getHealth();

            }catch (Exception e){
                System.out.println("데이터 없음");
            }
            System.out.println("("+shipId+", "+tier+",'"+shipName+"',"+health+","+averageDmg+","+averageKill+","+averageWin+"),");
        }
    }


    @DisplayName("유저 전적 히스토리를 가져올 수 있다.")
    @Test
    public void should_be_battle_history() throws JsonProcessingException {
        //given
        String applicationId = "4a5f774ca91614ec9e42bdb76474af15";
        String userId = "2020639284";

        //when
        Map<String, Object> expected = wowsApiClient.getBattleHistory(applicationId, userId);
        caculate(expected);
        //then
    }

    private void caculate(Map<String, Object> history) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.valueToTree(history);
        List<ShipDataDto> dtos = objectMapper.readValue(jsonNode.get("data").get("2020639284").toString(),
                new TypeReference<List<ShipDataDto>>() {
                });

        Collections.sort(dtos);

        List<ShipDataDto> todayHistory = new ArrayList<>();
        for (ShipDataDto dto : dtos){
            if (diffTimeStamp(dto.getUpdated_at())>0){
                break;
            } else {
                System.out.println(dto);
                whatKindBattles(dto);
                todayHistory.add(dto);
            }
        }

        System.out.println("size: "+todayHistory.size());
    }

    public long diffTimeStamp(long passTime){
        long nowTime = Instant.now().getEpochSecond();
        long diffTime = nowTime-passTime;

        long diffDays = TimeUnit.SECONDS.toDays(diffTime);
        return diffDays;
    }

    public void whatKindBattles(ShipDataDto shipDataDto){
        int maxXp = shipDataDto.getPvp().getMax_xp();
        System.out.println("경험치: " + maxXp);
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

}