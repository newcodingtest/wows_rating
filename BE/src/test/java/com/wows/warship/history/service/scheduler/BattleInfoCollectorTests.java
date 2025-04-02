package com.wows.warship.history.service.scheduler;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ConditionalOnProperty(name = "spring.redis.enabled", havingValue = "true")
@ActiveProfiles("test")
@SpringBootTest
public class BattleInfoCollectorTests {

    @Autowired
    private HistoryCollector historyCollector;

    @DisplayName("유저 전적정보를 수집 할 수 있다.")
    @Test
    public void should_be_return_battleHistory(){
        historyCollector.getUserRating();
    }
}
