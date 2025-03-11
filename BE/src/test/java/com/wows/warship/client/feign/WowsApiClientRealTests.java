package com.wows.warship.client.feign;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wows.warship.entity.ShipInfoEntity;
import com.wows.warship.repository.ShipInfoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

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
    private WowsApiClient userAccountService;
    @Autowired
    private ShipInfoRepository shipInfoRepository;

    @DisplayName("실제 API Test: 닉네임이 존재하는지 확인 할 수 있다.")
    @Test
    void getAccountList_should_be_return_userAccount() {
        //given
        String startWithNickname = "nocap";

        //when
        Map<String, Object>  response = userAccountService.getAccountList("4a5f774ca91614ec9e42bdb76474af15",
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
        Map<String, Object>  response = userAccountService.getShipInfo("4a5f774ca91614ec9e42bdb76474af15",
                "en",
                shipId);

        Map<String, Map<String, String>> actual = (Map<String, Map<String, String>>)response.get("data");

        //then
        assertEquals("Halland", actual.get(shipId).get("name"));
    }


    @Test
    public void collectShipList() throws IOException {
        InputStream is = new FileInputStream("src/sample/expected/test.json");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jnode = objectMapper.readTree(is);

        Map<String, Map<String,String>> mockResponse = objectMapper.readValue(jnode.get("data").toString(),
                new TypeReference< Map<String, Map<String,String>> >() {
                });
        System.out.println("INSERT INTO SHIP_INFO_ENTITY (SHIP_ID, SHIP_NAME, AVERAGE_DMG, AVERAGE_KILL, AVERAGE_WIN_RATE) VALUES");
        for (Map.Entry entry : mockResponse.entrySet()){
            String shipId = entry.getKey().toString();
            Map<String, Object>  response = userAccountService.getShipInfo("4a5f774ca91614ec9e42bdb76474af15",
                    "en",
                    shipId);

            String averageDmg = mockResponse.get(shipId).get("average_damage_dealt");
            String averageKill = mockResponse.get(shipId).get("average_frags");
            String averageWin = mockResponse.get(shipId).get("win_rate");
            Map<String, Map<String, String>> actual = (Map<String, Map<String, String>>)response.get("data");
            String shipName = "";
            try {
                shipName = actual.get(shipId).get("name");
            }catch (Exception e){
                System.out.println("데이터 없음");
            }
            System.out.println("("+shipId+", '"+shipName+"',"+averageDmg+","+averageKill+","+averageWin+"),");
        }
    }
}