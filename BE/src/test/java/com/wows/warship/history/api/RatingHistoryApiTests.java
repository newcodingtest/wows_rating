package com.wows.warship.history.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wows.warship.account.service.UserAccountService;
import com.wows.warship.common.entity.ShipInfoEntity;
import com.wows.warship.common.exception.WowsErrorCode;
import com.wows.warship.common.repository.ShipInfoRepository;
import com.wows.warship.history.entity.BattlesHistoryEntity;
import com.wows.warship.history.repository.BattlesHistoryRepository;
import com.wows.warship.history.service.query.RatingHistoryService;
import com.wows.warship.rate.service.RatingStatsService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ActiveProfiles("local")
@SpringBootTest
public class RatingHistoryApiTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RatingHistoryService ratingHistoryService;

    @Autowired
    private BattlesHistoryRepository battlesHistoryRepository;
    @Autowired
    private ShipInfoRepository shipInfoRepository;

    @Transactional
    @DisplayName("레이팅 히스토리 조회")
    @Test
    public void getLatestHistoryTest() throws Exception {
        //given
        String accountId = "2017319756";
        testHistorySetup(accountId);

        //when-then
        mockMvc.perform(get("/history/"+accountId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].shipName").value("test ship"))
                .andExpect(jsonPath("$[0].winsRate").value(0.16666666666666666))
                .andExpect(jsonPath("$[0].damage").value(0.0))
                .andExpect(jsonPath("$[0].killRate").value(0.0))
                .andExpect(jsonPath("$[0].tanking").value(0.0))
                .andExpect(jsonPath("$[0].rating").value(4806))
                .andExpect(jsonPath("$[0].lastPlayTime").value(1));
    }


    void testHistorySetup(String accountId){
        ShipInfoEntity shipInfo = shipInfoRepository.save(ShipInfoEntity.builder()
                .shipId(1)
                .shipName("test ship")
                .averageKill(0.5f)
                .averageWinRate(0.2f)
                .health(10000)
                .averageDmg(10000)
                .build());

        BattlesHistoryEntity created = BattlesHistoryEntity.builder()
                .maxXp(1)
                .kill(1)
                .damage(100000)
                .wins(2)
                .losses(2)
                .battles(4)
                .spotPoint(100000)
                .tankingPoint(10000)
                .teamCapPoint(12000)
                .capPoint(1000)
                .accountId(accountId)
                .battles(12)
                .shipInfo(shipInfo)
                .lastBattleTime(1L)
                .build();
        battlesHistoryRepository.save(created);
    }

}
