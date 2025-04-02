package com.wows.warship.history.event;

import com.wows.warship.history.service.BattlesHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CreateBattleHistoryEventHandler {

    private final BattlesHistoryService battlesHistoryService;

    @Async
    @EventListener
    public void updateRating(CreatedBattleHistoryEvent createdEvent){
        log.info("event catch");
        battlesHistoryService.save(createdEvent.getBattles(), createdEvent.getAccountId());
    }
}
