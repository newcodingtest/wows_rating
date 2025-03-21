package com.wows.warship.history.service.scheduler;

import com.wows.warship.account.domain.UserAccount;
import com.wows.warship.history.domain.BattlesHistory;
import com.wows.warship.history.service.BattlesHistoryService;
import com.wows.warship.common.service.UserAccountService;
import com.wows.warship.common.service.WowsApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RatingCollector {

    private final BattlesHistoryService battlesHistoryService;
    private final UserAccountService userAccountService;
    private final WowsApiService wowsApiService;

    /**
     * 유저 전적 히스토리 수집 스케쥴러(오전 6시마다)
     * */
    @Scheduled(cron = "0 0 6 * * *")
    public void getUserRating() {
        List<UserAccount> users = userAccountService.getAccounts();
        for (UserAccount user : users){
            List<BattlesHistory> create = wowsApiService.getBattleHistoryFromOneDay(user.getAccountId())
                            .stream().map(BattlesHistory::create)
                            .collect(Collectors.toList());
            battlesHistoryService.save(create, user.getAccountId());
        }
    }

}
