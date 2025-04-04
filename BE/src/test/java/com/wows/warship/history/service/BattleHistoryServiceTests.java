package com.wows.warship.history.service;


import com.wows.warship.common.entity.ShipInfoEntity;
import com.wows.warship.common.repository.ShipInfoRepository;
import com.wows.warship.history.domain.BattlesHistory;
import com.wows.warship.history.entity.BattlesHistoryEntity;
import com.wows.warship.history.repository.BattlesHistoryRepository;
import jakarta.transaction.Transactional;
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

    @Autowired
    private BattlesHistoryRepository battlesHistoryRepository;
    @Autowired
    private ShipInfoRepository shipInfoRepository;


    @Transactional
    @DisplayName("유저 히스토리를 조회 할 수 있다.")
    @Test
    public void getBattleHistoryTests(){
        //given
        String accountId = "2020639284";
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
                .capPoint(1000)
                .accountId(accountId)
                .shipInfo(shipInfo)
                .lastBattleTime(1L)
                .build();
        battlesHistoryRepository.save(created);

        //when
        List<BattlesHistory> find = battlesHistoryService.getBattleHistory(accountId);
        for (BattlesHistory bt : find){
            System.out.println(bt);
        }
        //then
        assertEquals(created.getMaxXp(), find.get(0).getMaxXp());
        assertEquals(created.getKill(), find.get(0).getKill());
        assertEquals(created.getLastBattleTime(), find.get(0).getLastPlayTime());
    }
}
