package com.wows.warship.account.event;

import com.wows.warship.account.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UpdatedUserEventHandler {

    private final UserAccountService userAccountService;

    @Async
    @EventListener
    public void updateRating(UpdatedUserRatingEvent updatedUserRatingEvent){
        log.info("event catch");
        userAccountService.uppateRate(updatedUserRatingEvent.getAccountId(), updatedUserRatingEvent.getRating());
    }
}
