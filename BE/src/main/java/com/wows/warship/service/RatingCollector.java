package com.wows.warship.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wows.warship.dto.BattleHistoryDto;
import com.wows.warship.dto.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RatingCollector {

    private final BattlesHistoryService battlesHistoryService;
    private final UserAccountService userAccountService;
    private final WowsApiService wowsApiService;
    /**
     * 매일 오전 6시
     * */
    //@Scheduled(cron = "0 0 6 * * *")
    @Scheduled(cron = "*/20 * * * * *")
    public void getUserRating() {
        List<UserAccount> users = userAccountService.getAccounts();
        for (UserAccount user : users){
            List<BattleHistoryDto> battles = wowsApiService.getBattleHistoryFromOneDay(user.getAccountId());
            battlesHistoryService.save(battles, user.getAccountId());
        }
    }

}
