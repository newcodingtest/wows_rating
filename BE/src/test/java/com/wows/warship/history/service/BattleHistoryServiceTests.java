package com.wows.warship.history.service;


import com.wows.warship.history.domain.BattlesHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

@ConditionalOnProperty(name = "spring.redis.enabled", havingValue = "true")
@ActiveProfiles("test")
@SpringBootTest
public class BattleHistoryServiceTests {

    @Autowired
    private BattlesHistoryService battlesHistoryService;


    @DisplayName("유저 히스토리를 조회 할 수 있다.")
    @Test
    public void getBattleHistoryTests(){
        //given
        String accountId = "2020639284";
        List<BattlesHistory> created = List.of(BattlesHistory.builder()

                .battleType("test")
                .kill(1)
                .build());
        battlesHistoryService.save(created, accountId);

        //when
        List<BattlesHistory> find = battlesHistoryService.getBattleHistory(accountId);

        //then
        assertEquals(created.get(0).getBattleType(), find.get(0).getBattleType());
        assertEquals(created.get(0).getKill(), find.get(0).getKill());
    }
}
